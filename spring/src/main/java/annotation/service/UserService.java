package annotation.service;

import annotation.annotation.Lazy;
import annotation.annotation.Service;

/**
 * @author hum
 */
@Service("userService")
@Lazy(false)
public class UserService {
    public UserService() {
        System.out.println("uesrService create");
    }
}
