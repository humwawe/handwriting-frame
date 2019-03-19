package hum.http;

import hum.framework.Invocation;
import hum.framework.Url;
import org.apache.commons.io.IOUtils;
import hum.register.MapRegister;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

/**
 * @author hum
 */
public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        try {
            InputStream inputStream = req.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Invocation invocation = (Invocation) ois.readObject();

            Url url = new Url("localhost", 8080);
            Class implClass = MapRegister.get(invocation.getInterfaceName(), url);
            Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            String result = (String) method.invoke(implClass.getDeclaredConstructor().newInstance(), invocation.getParams());

            IOUtils.write(result, resp.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
