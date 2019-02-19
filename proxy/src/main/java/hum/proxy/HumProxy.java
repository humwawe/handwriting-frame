package hum.proxy;

import hum.obj.Person;

import java.lang.reflect.Method;

/**
 * @author hum
 */
public class HumProxy implements InvocationHandler {
    private Person person;

    public HumProxy(Person person) {
        this.person = person;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(person, args);
        after();
        return null;
    }

    public void before() {
        System.out.println("before eat.........");
    }

    public void after() {
        System.out.println("after eat.........");
    }
}
