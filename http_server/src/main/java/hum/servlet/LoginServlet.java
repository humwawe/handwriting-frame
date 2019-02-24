package hum.servlet;

import hum.core.Request;
import hum.core.Response;

/**
 * @author hum
 */
public class LoginServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        response.print("<html>");
        response.print("<head>");
        response.print("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">");
        response.print("<title>");
        response.print("the first servlet");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("welcome back:" + request.getParameter("name"));
        response.print("</body>");
        response.print("</html>");
    }
}
