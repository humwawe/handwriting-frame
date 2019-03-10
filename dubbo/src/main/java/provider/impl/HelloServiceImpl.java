package provider.impl;

import provider.api.HelloService;

/**
 * @author hum
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String username) {
        return "hello " + username;
    }
}
