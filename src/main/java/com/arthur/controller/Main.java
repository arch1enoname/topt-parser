package com.arthur.controller;

import com.arthur.service.Service;

public class Main {

    private final static Service service = new Service();
    private final static String URL = "https://ru.freepik.com/free-photos-vectors/%D0%B0%D0%BD%D0%B8%D0%BC%D0%B5-%D0%BE%D0%B1%D0%BE%D0%B8-1920x1080";
    private final static String PATH = "C:\\Users\\79053\\Desktop\\image";

    public static void main(String[] args) {
        service.parseImages(URL, PATH);
    }
}