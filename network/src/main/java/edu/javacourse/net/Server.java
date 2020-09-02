package edu.javacourse.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(25225, 2000);

        System.out.println("Server started");

        while (true){
            Socket client = socket.accept();
            new SimpleServer(client).start();
        }
    }

}

class SimpleServer extends Thread{

    private Socket client;

    public SimpleServer(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        handleRequest();
    }

    private void handleRequest() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()))) {

            String request = reader.readLine();
            String[] words = request.split("\\s+");
            String command = words[0];
            String userName = words[1];

            System.out.println("Server got string 1: " + command);
            System.out.println("Server got string 2: " + userName);
//            Thread.sleep(2000);

            String response = buildResponse(command, userName);
            writer.write(response);
            writer.newLine();
            writer.flush();

            client.close();

        } catch (Exception e){
            e.printStackTrace(System.out);
        }
    }

    private String buildResponse(String command, String userName){

        return switch (command) {
            case "HELLO" -> "Hello " + userName;
            case "MORNING" -> "Good morning " + userName;
            case "DAY" -> "Good day " + userName;
            case "EVENING" -> "Good evening " + userName;
            default -> "Hi " + userName;
        };
    }
}