package xml.factory;

import common.Util;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import common.vo.BeanDefinition;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hum
 */
public class ClassPathXmlApplicationContext {

    private Map<String, BeanDefinition> beanMap = new HashMap<>();
    private Map<String, Object> instanceMap = new HashMap<>();

    public ClassPathXmlApplicationContext(String file) throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream(file);
        prase(in);
    }

    private void prase(InputStream in) throws Exception {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = documentBuilder.parse(in);
        processDocument(doc);
    }

    private void processDocument(Document doc) throws Exception {
        NodeList list = doc.getElementsByTagName("bean");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            NamedNodeMap attributes = node.getAttributes();
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(attributes.getNamedItem("id").getNodeValue());
            beanDefinition.setPkgClass(attributes.getNamedItem("class").getNodeValue());
            beanDefinition.setLazy(Boolean.valueOf(attributes.getNamedItem("lazy").getNodeValue()));
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
