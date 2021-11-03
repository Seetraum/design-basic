package com.test.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Transfer {
    public static void main(String[] args) {
        try (InputStream in = new URL("https://www.baidu.com").openStream()){
            in.transferTo(System.out);
            System.out.println(new String(in.readAllBytes(), StandardCharsets.UTF_8) + "...");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
