package edu.javacourse.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(25225, 2000);

        Map<String, Greetable> handlers = loadHandlers();

        System.out.println("Server started");


        while (true){
            Socket client = socket.accept();
            new SimpleServer(client, handlers).start();
        }
    }

    private static Map<String, Greetable> loadHandlers() {
        Map<String, Greetable> result = new HashMap<>();

        try(InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("server.properties")) {

            Properties properties = new Properties();
            properties.load(inputStream);
            for (Object command : properties.keySet()) {
                String className = properties.getProperty(command.toString());
                Class<Greetable> clazz = (Class<Greetable>) Class.forName(className);

                Greetable handler = clazz.getConstructor().newInstance();
                result.put(command.toString(), handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }

}

class SimpleServer extends Thread{

    private Socket client;
    private Map<String, Greetable> handlers;

    public SimpleServer(Socket client, Map<String, Greetable> handlers){
        this.client = client;
        this.handlers = handlers;
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
        Greetable handler = handlers.get(command);
        if(handler != null){
            return handler.buildResponse(userName);
        }
        return "Hello " + userName;
    }
}