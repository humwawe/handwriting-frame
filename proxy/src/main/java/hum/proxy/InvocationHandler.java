package hum.proxy;

import java.lang.reflect.Method;

/**
 * @author hum
 */
public interface InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
