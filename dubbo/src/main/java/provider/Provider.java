package provider;


import framework.Url;
import protocol.http.HttpServer;
import provider.api.HelloService;
import provider.impl.HelloServiceImpl;
import register.MapRegister;

/**
 * @author hum
 */
public class Provider {
    public static void main(String[] args) {
        Url url = new Url("localhost", 8080);
        MapRegister.regist(HelloService.class.getName(), url, HelloServiceImpl.class);

        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(), url.getPort());

    }
}
