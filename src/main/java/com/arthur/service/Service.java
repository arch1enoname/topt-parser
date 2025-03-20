package com.arthur.service;

import com.arthur.config.Config;
import com.arthur.config.ConfigLoader;
import com.arthur.exception.support.Exception4Support;
import com.arthur.exception.support.ForbiddenException;
import com.arthur.exception.support.ImageDownloadException;
import com.arthur.exception.user.*;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Service {
    private final Config config = ConfigLoader.loadConfig();
    private final String USER_AGENT = config.getUserAgent();
    private final String REFERRER = config.getReferrer();
    private final String HTML_TAG = config.getHtmlTag();
    private final String ATTRIBUTE = config.getAttribute();

    public void parseImages(String siteUrl, String path){
        try(ExecutorService executor = Executors.newFixedThreadPool(10)) {

            Document doc = Jsoup.connect(siteUrl)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .get();

            Elements images = doc.select(HTML_TAG);
            images = filterUniqueElements(images);

            for(Element image : images) {
                String imageUrl = image.attr(ATTRIBUTE);
                executor.submit(() -> downloadImage(imageUrl, path));
            }
            executor.shutdown();

            try {
                if (!executor.awaitTermination(30, TimeUnit.MINUTES)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
                throw new Exception4Support("Ошибка. " + e.getMessage());
            }
        } catch (HttpStatusException e) {
            handleHttpStatusException(e);
        } catch (IOException e) {
            throw new ImageDownloadException("Ошибка: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new Exception4User("Неверное значение аргументов. " + e.getMessage());
        }
    }

    private void handleHttpStatusException(HttpStatusException e) {
        int statusCode = e.getStatusCode();
        String errorMessage = statusCode + " url: " + e.getUrl();

        switch (statusCode) {
            case 401 -> throw new UnauthorizedException("Пользователь не авторизован. " + errorMessage);
            case 403 -> throw new ForbiddenException("Доступ к запрашиваемому ресурсу запрещен. " + errorMessage);
            case 404 -> throw new ResourceNotFoundException("Невозможно найти запрашиваемый ресурс. " + errorMessage);
            default -> throw new ImageDownloadException("Ошибка. " + errorMessage);
        }
    }

    private String removeAdditionalParameters(String fileName) {
        if (fileName.contains("?")) {
            return fileName.substring(0, fileName.indexOf("?"));
        }
        return fileName;
    }

    private void downloadImage(String imageUrl, String path) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        fileName = removeAdditionalParameters(fileName);
        Path target = Paths.get(path, fileName);

        try {
            URL url = new URL(imageUrl);
            InputStream in = url.openStream();
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (NoSuchFileException e) {
            throw new DirectoryException("Такой директории не существует: " + path);
        } catch (AccessDeniedException e) {
            throw new ForbiddenException("Доступ к запрашиваемому ресурсу запрещен. " + imageUrl);
        } catch (FileAlreadyExistsException e) {
            throw new DirectoryException("Целевой путь является директорией: " + target);
        } catch (MalformedURLException e) {
            throw new InvalidUrlException("Некорректный url. " + imageUrl);
        } catch (IOException e) {
            throw new Exception4Support("Ошибка. " + imageUrl + " " + path);
        }
    }

    private Elements filterUniqueElements(Elements elements) {
        Set<String> uniqueSrc = new HashSet<>();
        Elements uniqueImages = new Elements();

        for (Element image : elements) {
            String src = image.attr("src");
            if (uniqueSrc.add(src)) {
                uniqueImages.add(image);
            }
        }
        return uniqueImages;
    }
}
