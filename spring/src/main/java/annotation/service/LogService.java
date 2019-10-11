package annotation.service;

import annotation.annotation.Lazy;
import annotation.annotation.Service;

/**
 * @author hum
 */

@Service("logService")
@Lazy
public class LogService {
    public LogService() {
        System.out.println("logService create");
    }
}
