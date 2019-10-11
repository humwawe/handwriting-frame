package annotation.factory;

import annotation.annotation.ComponentScan;
import annotation.annotation.Lazy;
import annotation.annotation.Service;
import common.Util;
import common.vo.BeanDefinition;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hum
 */
public class AnnotationConfigApplicationContext {
    private Map<String, BeanDefinition> beanMap = new HashMap<>();
    private Map<String, Object> instanceMap = new HashMap<>();

    public AnnotationConfigApplicationContext(Class<?> configCls) throws Exception {
        ComponentScan cs = configCls.getDeclaredAnnotation(ComponentScan.class);
        String pkg = cs.value();
        String classPath = pkg.replace(".", "/");
        URL url = configCls.getClassLoader().getResource(classPath);
        File fileDir = new File(url.getPath());
        File[] classFiles = fileDir.listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith(".class"));
        processClassFiles(classFiles, pkg);
    }

    private void processClassFiles(File[] classFiles, String pkg) throws Exception {
        for (File f : classFiles) {
            BeanDefinition beanDefinition = new BeanDefinition();
            String pkgCls = pkg + "." + f.getName().substring(0, f.getName().lastIndexOf("."));
            Class<?> targeCls = Class.forName(pkgCls);
            Service service = targeCls.getDeclaredAnnotation(Service.class);
            if (service == null) {
                continue;
            }
            beanDefinition.setId(service.value());
            beanDefinition.setPkgClass(pkgCls);
            Lazy lazy = targeCls.getDeclaredAnnotation(Lazy.class);
            if (lazy != null) {
                beanDefinition.setLazy(lazy.value());
            }
            beanMap.put(beanDefinition.getId(), beanDefinition);
            if (!beanDefinition.isLazy()) {
                Object object = Util.newBeanInstance(beanDefinition.getPkgClass());
                instanceMap.put(beanDefinition.getId(), object);
            }
        }
        System.out.println(beanMap);
        System.out.println(instanceMap);
    }

    public <T> T getBean(String key, Class<T> t) throws Exception {
        return Util.getBean(beanMap, instanceMap, key, t);
    }
}
