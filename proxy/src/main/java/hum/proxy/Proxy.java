package hum.proxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hum
 */
public class Proxy {
    private static final String RT = "\r\n";

    public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) {
        String javaClassStr = getJavaStr(interfaces);

        String fileName = "C:\\Users\\hum\\IdeaProjects\\handwriting-frame\\proxy\\src\\main\\java\\hum\\proxy\\classtr\\$Proxy0.java";
        createJavaFile(javaClassStr, fileName);

        compileJava(fileName);

        Object instance = loadClass(h);
        return instance;
    }


    private static String getJavaStr(Class<?>[] interfaces) {
        // 假设只有一个接口
        Method[] methods = interfaces[0].getMethods();
        return "package hum.proxy;" + RT
                + "import java.lang.reflect.Method;" + RT
                + "public class $Proxy0 implements " + interfaces[0].getName() + "{" + RT
                + "InvocationHandler h;" + RT
                + "public $Proxy0(InvocationHandler h) {" + RT
                + "this.h=h;" + RT
                + "}" + getMethodString(methods, interfaces[0]) + RT
                + "}";
    }

    private static String getMethodString(Method[] methods, Class<?> anInterface) {
        String ret = "";
        for (Method method : methods) {
            ret += "public void " + method.getName() + "() throws Throwable {" + RT
                    + "Method md = " + anInterface.getName() + ".class.getMethod(\"" + method.getName() + "\",new Class[]{});" + RT
                    + "this.h.invoke(this,md,null);" + RT
                    + "}" + RT;
        }
        return ret;
    }

    private static void createJavaFile(String javaClassStr, String fileName) {
        File f = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write(javaClassStr);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compileJava(String fileName) {
        JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = systemJavaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(fileName);
        JavaCompiler.CompilationTask task = systemJavaCompiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
        task.call();
        try {
            standardFileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object loadClass(InvocationHandler h) {
        String path = "C:\\Users\\hum\\IdeaProjects\\handwriting-frame\\proxy\\src\\main\\java\\hum\\proxy\\classtr";
        HumClassLoader humClassLoader = new HumClassLoader(path);
        try {
            Class<?> proxyClass = humClassLoader.findClass("$Proxy0");
            Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
            return constructor.newInstance(h);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;

    }

}
