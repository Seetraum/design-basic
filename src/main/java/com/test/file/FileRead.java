package com.test.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileRead {
    public static void main(String[] args) {
        new FileRead().fileRead("D:/project/com.test/account.csv");
    }

    private void fileRead(String path) {
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
