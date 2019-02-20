package hum.proxy;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author hum
 */
public class HumClassLoader extends ClassLoader {
    private File dir;

    public HumClassLoader(String path) {
        super();
        this.dir = new File(path);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (dir != null) {
            File clazzFile = new File(dir, name + ".class");
            if (clazzFile.exists()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(clazzFile);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fileInputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, len);
                    }
                    return defineClass("hum.proxy." + name, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return super.findClass(name);
    }
}
