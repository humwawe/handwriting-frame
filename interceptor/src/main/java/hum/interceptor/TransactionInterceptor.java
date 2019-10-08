package hum.interceptor;

import hum.proxy.Invocation;
import hum.proxy.TargetProxyFactory;

/**
 * @author hum
 */
public class TransactionInterceptor implements Interceptor {
    @Override
    public Object interceptor(Invocation invocation) throws Exception {
        System.out.println("transaction start time " + System.nanoTime());
        Object result = invocation.process();
        System.out.println("transaction end time " + System.nanoTime());
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return TargetProxyFactory.newProxy(target, this);
    }
}
