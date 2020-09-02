package edu.javacourse.net;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            SimpleClient client = new SimpleClient(i);
            client.start();
        }
    }
}

class SimpleClient extends Thread {

    private final static String[] COMMAND = {
            "HELLO", "MORNING", "DAY", "EVENING"
    };

    private int cmdNumber;

    public SimpleClient(int cmdNumber){
        this.cmdNumber = cmdNumber;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 25225);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                String command = COMMAND[cmdNumber%COMMAND.length];
                String string = command + " " + "Ara";

                writer.write(string);
                writer.newLine();
                writer.flush();

                String answer = reader.readLine();
                System.out.println("Client got string: " + answer);
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}