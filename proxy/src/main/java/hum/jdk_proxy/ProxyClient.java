package hum.jdk_proxy;

import hum.obj.Hum;
import hum.obj.Person;

import java.lang.reflect.Proxy;

/**
 * @author hum
 */
public class ProxyClient {
    public static void main(String[] args) throws Throwable {
        Person personProxy = (Person) Proxy.newProxyInstance(ProxyClient.class.getClassLoader(), new Class<?>[]{Person.class}, new HumProxy(new Hum()));
        personProxy.eat2();
    }
}
