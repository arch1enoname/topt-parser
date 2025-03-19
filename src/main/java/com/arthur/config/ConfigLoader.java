package com.arthur.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ConfigLoader {
    public static Config loadConfig() {
        Yaml yaml = new Yaml();

        InputStream inputStream = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream("config.yml");

        if (inputStream == null) {
            throw new RuntimeException("Файл config.yml не найден!");
        }

        return yaml.loadAs(inputStream, Config.class);
    }
}
