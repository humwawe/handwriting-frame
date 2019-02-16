package hum.controller;

import hum.annotation.Autowired;
import hum.annotation.Controller;
import hum.annotation.RequestMapping;
import hum.service.HumService;

/**
 * @author hum
 */
@Controller
@RequestMapping("/hum")
public class HumController {

    @Autowired("humServiceImpl")
    HumService humService;

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hum controller...");
        String result = humService.hello();
        System.out.println(result);
        return "ok";
    }
}
