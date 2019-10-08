package hum.service;

import hum.annotation.Service;

/**
 * @author hum
 */
@Service("humServiceImpl")
public class HumServiceImpl implements HumService {
    @Override
    public String hello() {
        return "service hello";
    }
}
