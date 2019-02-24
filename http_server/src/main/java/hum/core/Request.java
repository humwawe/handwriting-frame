package hum.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hum
 */
public class Request {
    //    private StringBuilder requestInfo = new StringBuilder();
    private String requestInfo;
    private String method;
    private String url;
    private String queryStr;
    private Map<String, List<String>> parameterMap;
    private static final String RT = "\r\n";
    private static final String POST = "post";

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    public Request(InputStream is) throws IOException {
        parameterMap = new HashMap<>();
        byte[] data = new byte[1024 * 1024 * 1024];
        int len;
        try {
            len = is.read(data);
            this.requestInfo = new String(data, 0, len).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        parseRequestInfo();
    }


    private void parseRequestInfo() {
        this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).toLowerCase().trim();

        int startIdx = this.requestInfo.indexOf("/") + 1;
        int endIdx = this.requestInfo.indexOf("HTTP/");
        this.url = this.requestInfo.substring(startIdx, endIdx).trim();

        int queryIdx = this.url.indexOf("?");
        if (queryIdx >= 0) {
            String[] urlArray = this.url.split("\\?");
            this.url = urlArray[0];
            queryStr = urlArray[1];
        }

        if (POST.equals(method)) {
            String qStr = this.requestInfo.substring(this.requestInfo.lastIndexOf(RT)).trim();
            if (null == queryStr) {
                queryStr = qStr;
            } else {
                queryStr += "&" + qStr;
            }
        }
        queryStr = null == queryStr ? "" : queryStr;

        //fav=1&fav=2&name=hum&age=18&others=
        convertMap();
    }

    private void convertMap() {
        String[] keyValues = this.queryStr.split("&");
        for (String queryStr : keyValues) {
            String[] kv = queryStr.split("=");
            kv = Arrays.copyOf(kv, 2);
            String key = kv[0];
            String value = kv[1] == null ? null : decode(kv[1], "utf-8");
            if (!parameterMap.containsKey(key)) {
                parameterMap.put(key, new ArrayList<String>());
            }
            parameterMap.get(key).add(value);
        }
    }

    private String decode(String value, String enc) {
        try {
            return java.net.URLDecoder.decode(value, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getParameterValues(String key) {
        List<String> values = this.parameterMap.get(key);
        if (null == values || values.size() < 1) {
            return null;
        }
        return values.toArray(new String[0]);
    }


    public String getParameter(String key) {
        String[] values = getParameterValues(key);
        return values == null ? null : values[0];
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getQueryStr() {
        return queryStr;
    }


}

