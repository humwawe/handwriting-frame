package annotation;

import annotation.config.SpringConfig;
import annotation.factory.AnnotationConfigApplicationContext;
import annotation.service.LogService;
import annotation.service.UserService;


/**
 * @author hum
 */
public class Start {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService = ctx.getBean("userService", UserService.class);
        System.out.println(userService);
        LogService logService = ctx.getBean("logService", LogService.class);
        System.out.println(logService);
    }
}
