package hum.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @author hum
 */
public class Response {
    private BufferedWriter bw;
    private StringBuilder content;
    private StringBuilder headInfo;
    private int len;

    private static final String BLANK = " ";
    private static final String RT = "\r\n";

    private Response() {
        content = new StringBuilder();
        headInfo = new StringBuilder();
        len = 0;
    }

    public Response(Socket client) {
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            headInfo = null;
        }
    }

    public Response(OutputStream os) {
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    public Response print(String info) {
        content.append(info);
        len += info.getBytes().length;
        return this;
    }

    public Response println(String info) {
        content.append(info).append(RT);
        len += (info + RT).getBytes().length;
        return this;
    }

    public void pushToBrowser(int code) throws IOException {
        if (null == headInfo) {
            code = 505;
        }
        createHeadInfo(code);
        bw.append(headInfo);
        bw.append(content);
        bw.flush();
    }

    private void createHeadInfo(int code) {
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(code).append(BLANK);
        switch (code) {
            case 200:
                headInfo.append("OK").append(RT);
                break;
            case 404:
                headInfo.append("NOT FOUND").append(RT);
                break;
            case 505:
                headInfo.append("SERVER ERROR").append(RT);
                break;
            default:
                break;
        }

        headInfo.append("Date:").append(new Date()).append(RT);
        headInfo.append("Server:").append("hum Server/0.0.1;charset=GBK").append(RT);
        headInfo.append("Content-type:text/html").append(RT);
        headInfo.append("Content-length:").append(len).append(RT);
        headInfo.append(RT);
    }

}
