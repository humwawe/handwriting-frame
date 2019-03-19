package hum.framework;

import hum.http.HttpClient;
import hum.provider.api.HelloService;
import hum.register.MapRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hum
 */
public class ProxyFactory {

    public static <T> T getProxy(final Class interfaceClass) {
        @SuppressWarnings("unchecked")
        T o = (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                HttpClient httpClient = new HttpClient();
                Invocation invocation = new Invocation(HelloService.class.getName(), method.getName(), args, method.getParameterTypes());
                Url url = MapRegister.firstBalanceLoader(interfaceClass.getName());
                return httpClient.post(url.getHostname(), url.getPort(), invocation);
            }
        });
        return o;
    }
}
