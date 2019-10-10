package xml;

import xml.factory.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @author hum
 */
public class Start {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        Object obj = ctx.getBean("obj", Object.class);
        Date date = ctx.getBean("date", Date.class);
        System.out.println(obj);
        System.out.println(date);
    }
}
