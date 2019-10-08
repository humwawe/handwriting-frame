package hum.proxy;

import hum.interceptor.Interceptor;

import java.lang.reflect.Proxy;

/**
 * @author hum
 */

public class TargetProxyFactory {
    public static Object newProxy(Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new TargetProxyHandler(target, interceptor));


    }
}
