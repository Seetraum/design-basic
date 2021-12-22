package com.test.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.springframework.util.StringUtils;

public class FileRead {
    public static void main(String[] args) {
        //fileRead("D:/project/com.test/account.csv");
//        System.out.println(Path.of("D:","test","dev","start").toString());
//        Object o = null;
//        System.out.println(StringUtils.isEmpty(String.valueOf(o)));
        range r = new range(1,"zhang");
        System.out.println(String.format("%s,%s",r.i,r.name));

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

    public record range(int i,String name){}
}
