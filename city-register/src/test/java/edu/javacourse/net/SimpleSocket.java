package edu.javacourse.net;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleSocket {
    @Test
    public void simpleSocket() throws IOException {
        Socket socket = new Socket("java-course.ru", 80);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        String command = "Get /sitemap.xml HTTP/1.1\r\nHost:java-course.ru\r\n\r\n";
        outputStream.write(command.getBytes());
        outputStream.flush();

        int readByte = 0;
        while ((readByte = inputStream.read()) != -1){
            System.out.print((char) readByte);
        }

        socket.close();
    }
}
