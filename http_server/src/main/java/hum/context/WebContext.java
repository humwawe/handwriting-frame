package hum.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hum
 */
public class WebContext {
    private List<Entity> entities;
    private List<Mapping> mappings;

    private Map<String, String> entityMap = new HashMap<String, String>();
    private Map<String, String> mappingMap = new HashMap<String, String>();

    public WebContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;

        for (Entity entity : entities) {
            entityMap.put(entity.getName(), entity.getClz());
        }

        for (Mapping mapping : mappings) {
            for (String pattern : mapping.getPatterns()) {
                mappingMap.put(pattern, mapping.getName());
            }
        }
    }

    public String getClz(String pattern) {
        String name = mappingMap.get(pattern);
        return entityMap.get(name);
    }

}

