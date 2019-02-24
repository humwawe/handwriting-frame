package hum.core;

import hum.context.WebContext;
import hum.servlet.Servlet;
import hum.util.WebHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.lang.reflect.InvocationTargetException;

/**
 * @author hum
 */
public class WebApp {
    private static WebContext webContext;

    static {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            WebHandler handler = new WebHandler();
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"), handler);
            webContext = new WebContext(handler.getEntities(), handler.getMappings());
        } catch (Exception e) {
            System.out.println("解析配置文件错误");
        }
    }

    public static Servlet getServletFromUrl(String url) {
        String className = webContext.getClz("/" + url);
        try {
            Class clz = Class.forName(className);
            return (Servlet) clz.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}

