package xml.vo;

import java.io.Serializable;

/**
 * @author hum
 */
public class BeanDefinition implements Serializable {
    private static final long SERIAL_VERSION_UID = -22;

    private String id;
    private String pkgClass;
    private boolean lazy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPkgClass() {
        return pkgClass;
    }

    public void setPkgClass(String pkgClass) {
        this.pkgClass = pkgClass;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "id='" + id + '\'' +
                ", pkgClass='" + pkgClass + '\'' +
                ", lazy=" + lazy +
                '}';
    }
}
