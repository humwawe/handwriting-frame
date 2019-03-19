package hum.provider;


import hum.framework.Url;
import hum.http.HttpServer;
import hum.provider.api.HelloService;
import hum.provider.impl.HelloServiceImpl;
import hum.register.MapRegister;

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
