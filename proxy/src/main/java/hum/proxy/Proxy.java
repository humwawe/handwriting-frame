package hum.proxy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author hum
 */
public class Proxy {
    private static final String RT = "\r\n";

    public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) {
        String javaClassStr = getJavaStr(interfaces);

        String fileName = "C:\\Users\\hum\\IdeaProjects\\handwriting_frame\\proxy\\src\\main\\java\\hum\\proxy\\classtr";
        createJavaFile(javaClassStr, fileName);

        compileJava(fileName);

        return null;
    }


    private static String getJavaStr(Class<?>[] interfaces) {
        // 假设只有一个接口
        Method[] methods = interfaces[0].getMethods();
        String proxyClassStr = "package hum.proxy;" + RT
                + "import java.lang.reflect.Method;" + RT
                + "public class $Proxy0 implements" + interfaces[0].getName() + "{" + RT
                + "InvocationHandler h;" + RT
                + "public $Proxy0(InvocationHandler h) {" + RT
                + "this.h=h;" + RT
                + "}" + getMethodString(methods, interfaces[0]) + RT
                + "}";
        return proxyClassStr;
    }

    private static String getMethodString(Method[] methods, Class<?> anInterface) {
        String ret = "";
        for (Method method : methods) {
            ret += "public void " + method.getName() + "() {" + RT
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

    }

}
