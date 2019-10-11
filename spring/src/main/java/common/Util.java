package common;

import common.vo.BeanDefinition;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * @author hum
 */
public class Util {
    public static Object newBeanInstance(String pkgClass) throws Exception {
        Class<?> cls = Class.forName(pkgClass);
        Constructor<?> con = cls.getDeclaredConstructor();
        con.setAccessible(true);
        return con.newInstance();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Map<String, BeanDefinition> beanMap, Map<String, Object> instanceMap, String key, Class<T> t) throws Exception {
        if (!beanMap.containsKey(key)) {
            return null;
        }
        if (instanceMap.containsKey(key)) {
            return (T) instanceMap.get(key);
        } else {
            Object object = newBeanInstance(t.getName());
            instanceMap.put(key, object);
            return (T) object;
        }
    }
}
