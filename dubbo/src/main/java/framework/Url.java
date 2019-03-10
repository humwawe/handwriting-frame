package framework;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author hum
 */
public class Url implements Serializable {
    private String hostname;
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Url(String hostname, Integer port) {

        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Url url = (Url) o;
        return Objects.equals(hostname, url.hostname) &&
                Objects.equals(port, url.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname, port);
    }
}
