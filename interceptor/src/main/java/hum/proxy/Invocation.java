package hum.proxy;

import java.lang.reflect.Method;

/**
 * @author hum
 */
public class Invocation {
    private Object target;
    private Method method;
    private Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object process() throws Exception {
        return method.invoke(target, args);
    }
}
