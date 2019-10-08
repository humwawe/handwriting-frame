package hum.servlet;

import hum.core.Request;
import hum.core.Response;

/**
 * @author hum
 */
public interface Servlet {
    void service(Request request, Response response);
}
