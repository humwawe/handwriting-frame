package hum.proxy;

import hum.obj.Hum;
import hum.obj.Person;

/**
 * @author hum
 */
public class Client {
    public static void main(String[] args) {
        Proxy.newProxyInstance(Client.class.getClassLoader(), new Class<?>[]{Person.class}, new HumProxy(new Hum()));

    }
}
