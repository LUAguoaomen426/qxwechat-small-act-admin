package com.red.star.macalline.act.admin.util;


import com.red.star.macalline.act.admin.constant.Constant;
import com.red.star.macalline.act.admin.domain.vo.SysParam;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulonglong on 2017/11/13.
 */
public class SignUtil {

    public static String fetchSign(Map<String, String> param) {
        param.put("call_app_id", SysParam.MACALLINE_APP_ID);
        param.put("nonce", createNonceStr());
        param.put("time_stamp", createTimestamp());
        String sign = sign(param);
        return sign;
    }


    /**
     * 获取到签名值
     *
     * @param param
     * @return
     */
    public static String sign(Map<String, String> param) {
        String[] keyArray = param.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray) {
            stringBuilder.append(key).append("=").append(param.get(key)).append("&");
        }
        stringBuilder.append("call_app_secret=").append(SysParam.MACALLINE_APP_SECRET);
        return MD5Util.encode(stringBuilder.toString()).toLowerCase();
    }


    /**
     * 获取到签名值
     *
     * @param param
     * @return
     */
    public static String fetchRedStarSign(Map<String, String> param) {
        String[] keyArray = param.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray) {
            stringBuilder.append(key).append("=").append(param.get(key)).append("&");
        }
        stringBuilder.append("api_secret=").append(SysParam.REDSTAR_API_SIGN_SECRET);
        return MD5Util.encode(stringBuilder.toString()).toLowerCase();
    }


    /**
     * 生成随机值
     *
     * @return
     */
    public static String createNonceStr() {
        Integer charLength = 32;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int maxPos = chars.length();
        String noceStr = "";
        for (int i = 0; i < charLength; i++) {
            noceStr += chars.charAt((int) Math.floor(Math.random() * maxPos));
        }
        return noceStr;
    }


    /**
     * @param args
     * @throws IOException
     */
    public static void main1(String args[]) throws IOException {
        Map<String, String> param = new HashMap<String, String>(16);
        param.put("cardId", "79");
        param.put("groupId", "156");
        param.put("employeeId", "30");
        String nocestr = createNonceStr();
        String timestamp = createTimestamp();
        param.put("redstar-nocestr", nocestr);
        param.put("redstar-timestamp", timestamp);
        String redstarSign = fetchRedStarSign(param);
        Request request = Request.Post("https://wxxcx-api.chinaredstar.com/redstar/api/findByCardAGroup.json");
        request.setHeader("redstar-nocestr", nocestr);
        request.setHeader("redstar-timestamp", timestamp);
        request.setHeader("redstar-sign", redstarSign);
        String body = HttpUtil.fetch(request, param);
        System.out.println(body);
    }


    /**
     * 小程序二维码
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        Map<String, String> param = new HashMap<String, String>(16);
        param.put("vipOpenId", "as21sas21341d123e132xsx3");
        String nocestr = createNonceStr();
        String timestamp = createTimestamp();
        param.put("redstar-nocestr", nocestr);
        param.put("redstar-timestamp", timestamp);
        String redstarSign = fetchRedStarSign(param);
        Request request = Request.Post("http://127.0.0.1:8089/redstar/api/emptyVipOpenId");
        request.setHeader("redstar-nocestr", nocestr);
        request.setHeader("redstar-timestamp", timestamp);
        request.setHeader("redstar-sign", redstarSign);
        String body = HttpUtil.fetch(request, param);
        System.out.println(body);
    }

    /**
     * 生成系统时间p
     *
     * @return
     */
    public static String createTimestamp() {
        Calendar calendar = Calendar.getInstance();
        return "" + calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取到签名值——仟传
     *
     * @param param
     * @return
     */
    public static String fetchQCStarSign(Map<String, String> param) {
        String[] keyArray = param.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray) {
            stringBuilder.append(key).append("=").append(param.get(key)).append("&");
        }
        stringBuilder.append(Constant.CALL_SECRET_QIAN_CHUAN).append("=").append(SysParam.QIAN_CHUAN_CALL_SECRET);
        return MD5Util.encode(stringBuilder.toString()).toLowerCase();
    }


}
