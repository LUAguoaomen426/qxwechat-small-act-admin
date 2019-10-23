package com.red.star.macalline.act.admin.util;

import com.red.star.macalline.act.admin.constant.ActConstant;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @ProjectName: macalline-wx-server
 * @Package: com.red.star.macalline.wx.utils
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2018-11-27 16:22
 * @Version: 1.0
 */
public class WxInfoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxInfoUtil.class);
    /**
     * 查询所有商场
     *
     * @param path
     * @param params
     * @return
     * @throws IOException
     */
    public static String getMallMessageApi(String path, String params) {
        Request request = Request.Post(ActConstant.MALL_API_URL_STAG);
        request.setHeader("Content-Type", "application/json");
        String fetch = null;
        try {
            fetch = HttpUtil.fetch(request, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fetch;
    }



}
