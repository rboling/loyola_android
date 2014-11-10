package com.example.robert.loyaltyclub.TaskerHelpers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by robert on 11/9/14.
 */
public class WriteContentHelper {
    private static byte[] buff = new byte[1024];

    public static String writeContent(HttpResponse response){
        try {
            HttpEntity httpEntity = response.getEntity();
            InputStream inputStream = httpEntity.getContent();
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            int readContent = 0;
            while((readContent = inputStream.read(buff)) != -1){
                content.write(buff, 0, readContent);
            }
            return new String(content.toByteArray());
        }
        catch (IOException ioException){
            return "IOException";
        }
    }
}
