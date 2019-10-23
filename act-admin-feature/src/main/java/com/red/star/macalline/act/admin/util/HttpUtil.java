package com.red.star.macalline.act.admin.util;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author 徐龙龙 on 2017/3/6.
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);



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

}
