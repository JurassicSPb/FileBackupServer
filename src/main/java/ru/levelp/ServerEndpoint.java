package ru.levelp;

/**
 * Created by Мария on 07.10.2016.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEndpoint {
    private static final int PORT = 8686;

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler handler = new ClientHandler(clientSocket);
                    handler.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}