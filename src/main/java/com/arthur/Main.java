package com.arthur;

import com.arthur.exception.*;
import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.NoSuchFileException;

public class Main {

    private final static Service service = new Service();
    private final static String URL = "https://ru.freepik.com/free-photos-vectors/%D0%B0%D0%BD%D0%B8%D0%BC%D0%B5-%D0%BE%D0%B1%D0%BE%D0%B8-1920x1080";
    private final static String PATH = "C:\\Users\\79053\\Desktop\\image";

    public static void main(String[] args) {
        try {
            service.parseImages(URL, PATH);
        } catch (HttpStatusException e) {
            int statusCode = e.getStatusCode();
            String errorMessage = statusCode + " url: " + e.getUrl();

            switch (statusCode) {
                case 401 -> throw new UnauthorizedException("Пользователь не авторизован. " + errorMessage);
                case 403 -> throw new ForbiddenException("Доступ к запрашиваемому ресурсу запрещен. " + errorMessage);
                case 404 -> throw new ResourceNotFoundException("Невозможно найти запрашиваемый ресурс. " + errorMessage);
                default -> throw new ImageDownloadException("Ошибка HTTP-запроса: " + errorMessage);
            }
        } catch (NoSuchFileException e) {
            throw new ImageDownloadException("Такой директории не существует: " + PATH, e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Некорректный аргумент: " + e.getMessage());
        } catch (IOException e) {
            throw new ImageDownloadException("Ошибка: " + e.getMessage());
        }
    }
}