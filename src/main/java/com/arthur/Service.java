package com.arthur;

import com.arthur.config.Config;
import com.arthur.config.ConfigLoader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Service {
    private final Config config = ConfigLoader.loadConfig();
    private final String USER_AGENT = config.getUserAgent();
    private final String REFERRER = config.getReferrer();
    private final String HTML_TAG = config.getHtmlTag();
    private final String ATTRIBUTE = config.getAttribute();

    public void parseImages(String siteUrl, String path) throws IOException {
        Document doc = Jsoup.connect(siteUrl)
                .userAgent(USER_AGENT)
                .referrer(REFERRER)
                .get();

        Elements images = doc.select(HTML_TAG);

        for (Element image : images) {
            String imageUrl = image.attr(ATTRIBUTE);

            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            fileName = removeAdditionalParameters(fileName);

            URL url = new URL(imageUrl);

            InputStream in = url.openStream();
                Files.copy(
                        in,
                        Paths.get(path, fileName),
                        StandardCopyOption.REPLACE_EXISTING
                );

        }
    }

    private String removeAdditionalParameters(String fileName) {
        if (fileName.contains("?")) {
            return fileName.substring(0, fileName.indexOf("?"));
        }
        return fileName;
    }
}
