package com.red.star.macalline.act.admin.utils;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 徐龙龙 on 2017/3/6.
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 获取请求所有参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParam(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>(16);
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            } else if (paramValues.length > 1) {
                String paramValue = paramValues[paramValues.length - 1];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }


    public static String fetch(Request request, Map<String, String> params) throws IOException {
        return fetch(request, params, "UTF-8");
    }

    public static String fetch(Request request, Map<String, String> params, String charset) throws IOException {
        Form form = Form.form();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            form.add(entry.getKey(), entry.getValue());
        }
        return fetch(request.bodyForm(form.build(), Charset.forName(charset)));
    }

    public static String fetch(Request request, Form bodyForm) throws IOException {
        return fetch(request.bodyForm(bodyForm.build(), Charset.forName("UTF-8")));
    }

    public static String fetch(Request request, String bodyString) throws IOException {
        return fetch(request.bodyString(bodyString, ContentType.create("text/plain", "UTF-8")));
    }

    public static byte[] fetchByte(Request request, String bodyString) throws IOException {
        return fetchByte(request.bodyString(bodyString, ContentType.create("text/plain", "UTF-8")));
    }

    public static String fetch(Request request) {
        String result = null;
        try {
            Executor executor = Executor.newInstance();
            result = executor.execute(request).returnContent().asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] fetchByte(Request request) {
        byte[] result = null;
        try {
            Executor executor = Executor.newInstance();
            result = executor.execute(request).returnContent().asBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static InputStream fetchInputStream(Request request) throws IOException {
        Executor executor = Executor.newInstance();
        InputStream is = executor.execute(request).returnContent().asStream();
        return is;
    }


    /**
     * 获取IP 方法
     *
     * @param request 从请求对象中获取IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String unknown = "unknown";
        String ip = "";
        String remoteAddr1 = request.getHeader("remote_addr1");
        LOGGER.info("红星获取客户端真实ip1：{}", remoteAddr1);
        ip = request.getHeader("X-Forwarded-For");
        if (!ObjectUtils.isEmpty(ip) && !unknown.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                LOGGER.info("红星获取客户端真实ip2：{}", ip.substring(0, index));
                return ip.substring(0, index);
            } else {
                LOGGER.info("红星获取客户端真实ip3：{}", ip);
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!ObjectUtils.isEmpty(ip) && !unknown.equalsIgnoreCase(ip)) {
            LOGGER.info("红星获取客户端真实ip4：{}", ip);
            return ip;
        } else {
            LOGGER.info("红星获取客户端真实ip5：{}", request.getRemoteAddr());
            return request.getRemoteAddr();
        }
    }


}
