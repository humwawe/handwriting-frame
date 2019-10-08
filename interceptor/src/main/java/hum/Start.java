package hum;

import hum.executor.DefaultExecutor;
import hum.executor.Executor;
import hum.interceptor.Interceptor;
import hum.interceptor.InterceptorChain;
import hum.interceptor.LogInterceptor;
import hum.interceptor.TransactionInterceptor;

/**
 * @author hum
 */
public class Start {
    public static void main(String[] args) {
        Executor target = new DefaultExecutor();
        Interceptor logInterceptor = new LogInterceptor();
        Interceptor transactionInterceptor = new TransactionInterceptor();
        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(logInterceptor);
        interceptorChain.addInterceptor(transactionInterceptor);

        Executor proxy = (Executor) interceptorChain.pluginAll(target);
        proxy.execute("select");
    }
}
