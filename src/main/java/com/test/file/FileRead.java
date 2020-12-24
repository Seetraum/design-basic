package com.test.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.springframework.util.StringUtils;

public class FileRead {
    public static void main(String[] args) {
        //fileRead("D:/project/com.test/account.csv");
        System.out.println(Path.of("D:","test","dev","start").toString());
        Object o = null;
        System.out.println(StringUtils.isEmpty(String.valueOf(o)));
    }

    private static void fileRead(String path) {
        try {
            Files.readAllLines(Path.of(path)).stream()
                    .map(line -> line.split(",")).forEach(arr -> {
                        System.out.println(Arrays.toString(arr));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
