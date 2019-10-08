package hum.interceptor;

import hum.proxy.Invocation;

/**
 * @author hum
 */
public interface Interceptor {
    Object interceptor(Invocation invocation) throws Exception;

    Object plugin(Object target);
}
