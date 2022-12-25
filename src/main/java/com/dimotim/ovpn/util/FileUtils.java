package com.dimotim.ovpn.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileUtils {
    public static String readFileAsString(String path) throws IOException {
        try (InputStream is= new FileInputStream(path)) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public static  <T> T readFile(String path, Class<T> type) throws IOException {
        try (FileReader fileReader = new FileReader(path, StandardCharsets.UTF_8)) {
            return GsonFactory.getGson().fromJson(fileReader, type);
        }
    }

    public static String resolveAbsPath(String path) throws Exception {
        File file = new File(path);
        String abs = file.getAbsolutePath();
        if (!new File(abs).exists()) {
            String message = "file not exists: " + abs;
            log.error(message);
            throw new Exception(message);
        }
        return abs;
    }
}
