package com.arthur.controller;

import com.arthur.exception.support.Exception4Support;
import com.arthur.exception.support.ForbiddenException;
import com.arthur.exception.support.ImageDownloadException;
import com.arthur.exception.user.*;
import com.arthur.service.Service;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private final static Service service = new Service();
    static String url = "https://ru.wikipedia.org/wiki/%D0%9A%D1%80%D0%B8%D1%88%D1%82%D0%B8%D0%B0%D0%BD%D1%83_%D0%A0%D0%BE%D0%BD%D0%B0%D0%BB%D0%B4%D1%83";
    static String path = "C:\\Users\\79053\\Desktop\\image";

    public static void main(String[] args) {
        try {
//            Scanner scanner = new Scanner(System.in);
//
//            String url = scanner.nextLine();
//            String path = scanner.nextLine();

            service.parseImages(url, path);
        } catch (UnauthorizedException e) {
            System.out.println("Вы не авторизованны. " + e.getMessage());
        } catch (ForbiddenException e) {
            System.out.println("Доступ к запрашиваемому ресурсу запрещен. " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            System.out.println("Невозможно найти запрашиваемый ресурс. " + e.getMessage());
        } catch (DirectoryException e) {
            System.out.println("Директория не найдена. " + e.getMessage());
        } catch (InvalidUrlException e) {
            System.out.println("Некорректный url. " + e.getMessage());
        } catch (Exception4User e) {
            int id = generateId();
            System.err.println("Ошибка. id - "+ id + " " + e.getMessage());
            System.out.println("Обратитесь в тех поддержку, id ошибки - " + id);
        } catch (Exception4Support e) {
            int id = generateId();
            System.err.println("Ошибка. id - " + id + " " + e.getMessage());
        }
    }

    public static int generateId() {
        Random random = new Random();
        return random.nextInt();
    }
}