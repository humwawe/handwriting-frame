package hum.servlet;

import hum.core.Request;
import hum.core.Response;

/**
 * @author hum
 */
public class RegisterServlet implements Servlet {

    @Override
    public void service(Request request, Response response) {
        String name = request.getParameter("name");
        String[] types = request.getParameterValues("type");
        response.print("<html>");
        response.print("<head>");
        response.print("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">");
        response.print("<title>");
        response.print("hum.register success");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.println("name:" + name);
        response.println("你喜欢的类型为:");
        for (String v : types) {
            if ("0".equals(v)) {
                response.print("萝莉型");
            } else if ("1".equals(v)) {
                response.print("豪放型");
            } else if ("2".equals(v)) {
                response.print("经济节约型");
            }
        }
        response.print("</body>");
        response.print("</html>");
    }
}
