package hum.proxy;

import hum.interceptor.Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author hum
 */
public class TargetProxyHandler implements InvocationHandler {
    private Object target;
    private Interceptor interceptor;

    public TargetProxyHandler(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new Invocation(target, method, args);
        return interceptor.interceptor(invocation);
    }
}
