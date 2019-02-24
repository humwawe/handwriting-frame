package hum.core;

import hum.servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author hum
 */
public class Dispatcher implements Runnable {
    private Socket client;
    private Request request;
    private Response response;

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            request = new Request(client);
            response = new Response(client);
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
    }

    @Override
    public void run() {
        try {
            if (null == request.getUrl() || "".equals(request.getUrl())) {
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
                response.print((new String(is.readAllBytes())));
                response.pushToBrowser(200);
                is.close();
                return;
            }
            Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
            if (null != servlet) {
                servlet.service(request, response);
                response.pushToBrowser(200);
            } else {
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                response.print((new String(is.readAllBytes())));
                response.pushToBrowser(404);
                is.close();
            }
        } catch (Exception e) {
            try {
                response.pushToBrowser(500);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        release();
    }

    private void release() {
        try {
            client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}

