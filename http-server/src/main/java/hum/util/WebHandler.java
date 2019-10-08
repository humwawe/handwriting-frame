package hum.util;

import hum.context.Entity;
import hum.context.Mapping;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hum
 */
public class WebHandler extends DefaultHandler {
    private List<Entity> entities = new ArrayList<Entity>();
    private List<Mapping> mappings = new ArrayList<Mapping>();
    private Entity entity;
    private Mapping mapping;
    private String tag;
    private boolean isMapping = false;
    private static final String SERVLET_TAG = "servlet";
    private static final String SERVLET_MAPPING_TAG = "servlet-mapping";
    private static final String URL_PATTERN_TAG = "url-pattern";
    private static final String SERVLET_CLASS_TAG = "servlet-class";
    private static final String SERVLET_NAME_TAG = "servlet-name";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (null != qName) {
            tag = qName;
            if (SERVLET_TAG.equals(tag)) {
                entity = new Entity();
                isMapping = false;
            } else if (SERVLET_MAPPING_TAG.equals(tag)) {
                mapping = new Mapping();
                isMapping = true;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String contents = new String(ch, start, length).trim();
        if (null != tag) {
            if (isMapping) {
                if (SERVLET_NAME_TAG.equals(tag)) {
                    mapping.setName(contents);
                } else if (URL_PATTERN_TAG.equals(tag)) {
                    mapping.addPattern(contents);
                }
            } else {
                if (SERVLET_NAME_TAG.equals(tag)) {
                    entity.setName(contents);
                } else if (SERVLET_CLASS_TAG.equals(tag)) {
                    entity.setClz(contents);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (null != qName) {
            if (SERVLET_TAG.equals(qName)) {
                entities.add(entity);
            } else if (SERVLET_MAPPING_TAG.equals(qName)) {
                mappings.add(mapping);
            }
        }
        tag = null;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

}
