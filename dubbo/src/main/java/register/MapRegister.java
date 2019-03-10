package register;

import framework.Url;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hum
 */
public class MapRegister {
    private static Map<String, Map<Url, Class>> REGISTER = new HashMap<>();
    private static final String INFO_PATH = "C:\\Users\\hum\\IdeaProjects\\handwriting_frame\\dubbo\\src\\main\\java\\register\\info\\register.text";

    public static void regist(String interfaceName, Url url, Class implClass) {
        Map<Url, Class> map = new HashMap<>();
        map.put(url, implClass);

        REGISTER.put(interfaceName, map);
        saveFile();
    }

    public static Class get(String interfaceName, Url url) {
        REGISTER = getFile();
        return REGISTER != null ? REGISTER.get(interfaceName).get(url) : null;
    }

    public static Url firstBalanceLoader(String interfaceName) {
        REGISTER = getFile();
        return REGISTER != null ? REGISTER.get(interfaceName).keySet().iterator().next() : null;
    }

    public static void saveFile() {
        try {
            FileOutputStream fos = new FileOutputStream(INFO_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(REGISTER);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Map<Url, Class>> getFile() {
        try {
            FileInputStream fis = new FileInputStream(INFO_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Map<String, Map<Url, Class>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
