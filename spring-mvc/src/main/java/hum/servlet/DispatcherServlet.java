package hum.servlet;

import hum.annotation.Autowired;
import hum.annotation.Controller;
import hum.annotation.RequestMapping;
import hum.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hum
 */
public class DispatcherServlet extends HttpServlet {

    private List<String> classNames = new ArrayList<>();
    private Map<String, Object> beans = new HashMap<>();
    private Map<String, Method> handlerMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String context = req.getContextPath();
        System.out.println(uri);
        System.out.println(context);
        String path = uri.replace(context, "");
        Method method = handlerMap.get(path);
        Object obj = beans.get("/" + path.split("/")[1]);
        Object result = null;
        try {
            result = method.invoke(obj, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        PrintWriter out = resp.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("scan package...................");
        scanPackage("hum");
        for (String className : classNames) {
            System.out.println(className);
        }

        System.out.println("instance...................");
        instance();
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        ioc();

        System.out.println("handler mapping...................");
        handlerMapping();
        for (Map.Entry<String, Method> entry : handlerMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private void scanPackage(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(replaceTo(packageName));
        String fileStr = url.getFile();
        System.out.println(fileStr);

        File file = new File(fileStr);
        String[] list = file.list();

        for (String path : list) {
            File filePath = new File(fileStr + path);
            if (filePath.isDirectory()) {
                scanPackage(packageName + "." + path);
            } else {
                classNames.add(packageName + "." + path);
            }
        }


    }

    private String replaceTo(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }

    private void instance() {
        if (classNames.size() <= 0) {
            return;
        }
        for (String className : classNames) {
            String cnName = className.replace(".class", "");
            try {
                Class<?> clazz = Class.forName(cnName);

                if (clazz.isAnnotationPresent(Controller.class)) {
                    Object o = clazz.newInstance();
                    RequestMapping requestMappingAnnotation = clazz.getAnnotation(RequestMapping.class);
                    String value = requestMappingAnnotation.value();
                    beans.put(value, o);
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Object o = clazz.newInstance();
                    Service serviceAnnotation = clazz.getAnnotation(Service.class);
                    String value = serviceAnnotation.value();
                    beans.put(value, o);
                }

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void ioc() {
        if (beans.size() <= 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
                    String value = autowiredAnnotation.value();
                    Object o = beans.get(value);

                    field.setAccessible(true);
                    try {
                        field.set(instance, o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void handlerMapping() {
        if (beans.size() <= 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();

            if (clazz.isAnnotationPresent(Controller.class)) {
                RequestMapping requestMappingAnnotation = clazz.getAnnotation(RequestMapping.class);
                String value = requestMappingAnnotation.value();

                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String methordUrl = requestMapping.value();
                        handlerMap.put(value + methordUrl, method);
                    }
                }
            }
        }
    }
}