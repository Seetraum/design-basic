package com.test.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerFileReader {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream in = new FileInputStream("D:\\checkconditions.txt");
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext()){
            System.out.println("read line content : " + scanner.next());
        }
    }
}
