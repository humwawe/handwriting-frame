package hum.consumer;

import hum.framework.ProxyFactory;
import hum.provider.api.HelloService;

/**
 * @author hum
 */
public class Consumer {
    public static void main(String[] args) {
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("hum...");
        System.out.println(result);

    }


}
