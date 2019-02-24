package hum.core;

import hum.servlet.LoginServlet;
import hum.servlet.Servlet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author hum
 */
public class Server {
    private ServerSocket serverSocket;
    private boolean isRunning;


    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            isRunning = true;
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("service start error....");
            stop();
        }
    }

    public void receive() {
        while (isRunning) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("client.....");
                new Thread(new Dispatcher(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("client error");
            }
        }
    }

    public void stop() {
        isRunning = false;
        try {
            this.serverSocket.close();
            System.out.println("service stop");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

