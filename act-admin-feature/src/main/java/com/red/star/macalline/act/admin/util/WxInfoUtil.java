package com.red.star.macalline.act.admin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.red.star.macalline.act.admin.constant.ActConstant;
import com.red.star.macalline.act.admin.constant.Constant;
import com.red.star.macalline.act.admin.constant.ResponseConstant;
import com.red.star.macalline.act.admin.constant.WxUriConstant;
import com.red.star.macalline.act.admin.domain.dto.TicketInfoDTO;
import com.red.star.macalline.act.admin.domain.vo.SysParam;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        LOGGER.info("同步商场：" + fetch);
        return fetch;
    }


    /**
     * 查看活动信息（列表）
     *
     * @param unitCode omsCode
     * @param actId    活动id
     * @return
     * @throws IOException
     */
    public static JSONObject getActList(String unitCode, String actId) throws IOException {
        Map<String, String> param = new HashMap<String, String>();
        String sign = SignUtil.fetchSign(param);
        param.put("unit_code", unitCode);
        param.put("act_id", actId);
        Request request = Request.Post(SysParam.MACALLINE_URL + "activity/GetActList");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("redstar-call-app-id", SysParam.MACALLINE_APP_ID);
        request.setHeader("redstar-sign", sign);
        String resp = HttpUtil.fetch(request, param);
        LOGGER.info(Constant.MSG_INFO_GETACTLIST, JSONObject.toJSONString(param), "");
        if (!ObjectUtils.isEmpty(resp)) {
            JSONObject jsonObject = JSONObject.parseObject(resp);
            if (!ObjectUtils.isEmpty(jsonObject)) {
                return jsonObject;
            }
        }
        return null;
    }


    /**
     * 发起post请求
     *
     * @param url    请求全路径(带host)
     * @param params 请求参数
     * @return 响应内容
     */
    public static String postRequest(String url, Map<String, String> params) {
        String resp = null;
        if (params == null) {
            params = Maps.newHashMap();
        }
        // 签名使用参数
        Map<String, String> signParams = Maps.newHashMap();
        signParams.put("call_app_id", SysParam.MACALLINE_APP_ID);
        signParams.put("nonce", SignUtil.createNonceStr());
        signParams.put("time_stamp", SignUtil.createTimestamp());
        // 根据参数生成签名
        String sign = SignUtil.sign(signParams);
        // 将签名参数放入参数列表中
        params.putAll(signParams);
        Request request = Request.Post(url);
        // 设置请求头(固定值)
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("redstar-call-app-id", SysParam.MACALLINE_APP_ID);
        request.setHeader("redstar-sign", sign);
        request.socketTimeout(5 * 1000);
        try {
            // 请求接口
            resp = HttpUtil.fetch(request, params);
            LOGGER.info(ActConstant.MSG_INFO_MACALLINE_REST, url, JSONObject.toJSONString(params), resp);
        } catch (IOException e) {
            LOGGER.info("红星接口请求——url:{}", url, e);
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 请求接口, 解析数据
     *
     * @param uri    方法路径
     * @param params 参数
     * @param clazz  返回对象class
     * @param <T>    返回类型
     * @return 请求对象
     */
    public static <T> T postObject(String uri, Map<String, String> params, Class<T> clazz) {
        String url = SysParam.MACALLINE_URL + uri;
        String resp = postRequest(url, params);
        if (ObjectUtils.isEmpty(resp)) {
            return null;
        }
        JSONObject respJson = JSONObject.parseObject(resp);
        if (ObjectUtils.isEmpty(respJson)) {
            return null;
        }
        if (!respJson.containsKey("code") || (!respJson.getInteger("code").equals(200) && !respJson.getInteger("code").equals(0))) {
            String message = respJson.getString("message");
            LOGGER.error(message);
            return null;
        }
        String body = respJson.getString("dataMap");
        if (!ObjectUtils.isEmpty(body) && clazz != null) {
            if (body.startsWith("[")) {
                // 解析数组
                LOGGER.error(ResponseConstant.RESPONSE_MESSAGE_ERROR_ANALYSIS + body);
                return null;
            } else {
                // 解析对象
                return JSON.parseObject(body, clazz);
            }
        }
        return null;
    }

    /**
     * 获取券详情
     *
     * @param ticketId 券id
     * @return
     */
    public static TicketInfoDTO getTicketInfo(String ticketId) {
        Map<String, String> param = Maps.newHashMap();
        param.put("promotion_ticket_id", ticketId);
        TicketInfoDTO ticketInfoDTO = postObject(WxUriConstant.WX_URI_TICKET_INFO, param, TicketInfoDTO.class);
        return ticketInfoDTO;
    }

    /**
     * 商场爆款（列表）
     *
     * @param unitCode omsCode
     * @return
     * @throws IOException
     */
    public static JSONObject getPromotionTicketList(String unitCode) throws IOException {
        Map<String, String> param = new HashMap<String, String>();
        String sign = SignUtil.fetchSign(param);
        param.put("unit_code", unitCode);
        Request request = Request.Post(SysParam.MACALLINE_URL + "activity/GetPromotionTicketItemList");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("redstar-call-app-id", SysParam.MACALLINE_APP_ID);
        request.setHeader("redstar-sign", sign);
        String resp = HttpUtil.fetch(request, param);
        LOGGER.info(Constant.MSG_INFO_GETPROMOTIONTICKETLIST, JSONObject.toJSONString(param), resp);
        if (!ObjectUtils.isEmpty(resp)) {
            JSONObject jsonObject = JSONObject.parseObject(resp);
            if (!ObjectUtils.isEmpty(jsonObject)) {
                return jsonObject;
            }
        }
        return null;
    }

    /**
     * 团单品券信息(列表)
     *
     * @param unitCode
     * @param groupId
     * @param actId
     * @param type     0 单品券  1 免费券
     * @return
     * @throws IOException
     */
    public static JSONObject getGroupList(String unitCode, String groupId, String actId, Integer type) throws IOException {
        String typeStr = ObjectUtils.isEmpty(type) ? "0" : String.valueOf(type);
        Map<String, String> param = new HashMap<String, String>();
        String sign = SignUtil.fetchSign(param);
        param.put("unit_code", unitCode);
        param.put("group_id", groupId);
        param.put("act_id", actId);
        param.put("type", typeStr);
        Request request = Request.Post(SysParam.MACALLINE_URL + "activity/GetGroupList");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("redstar-call-app-id", SysParam.MACALLINE_APP_ID);
        request.setHeader("redstar-sign", sign);
        String resp = HttpUtil.fetch(request, param);
        LOGGER.info(Constant.MSG_INFO_GETGROUPLIST, JSONObject.toJSONString(param), "");
        if (!ObjectUtils.isEmpty(resp)) {
            JSONObject jsonObject = JSONObject.parseObject(resp);
            if (!ObjectUtils.isEmpty(jsonObject)) {
                return jsonObject.getJSONObject("dataMap");
            }
        }
        return null;
    }


    /**
     * 有龙券详情
     *
     * @param ticketId
     * @return
     * @throws IOException
     */
    public static String getSkuContent(String ticketId) {
        Request request = Request.Get(SysParam.YOULONG_URL + "/IMP/api/sku/getContent?type=2&id=" + ticketId);
        request.setHeader("rs-nonce", SysParam.YOULONG_NONCE);
        request.setHeader("rs-sign", SysParam.YOULONG_SIGN);
        String resp = HttpUtil.fetch(request);
//        LOGGER.info(Constant.MSG_INFO_GETSKUCONTENT, JSONObject.toJSONString(request), resp);
        return resp;
    }

    /**
     * 团单品券信息(列表)
     *
     * @param unitCode
     * @param groupId
     * @param actId
     * @param type      0 单品券  1 免费券
     * @param groupList 团Id集合 以,分割，返回的对象中会多出img_arr ,生成的缓存为groupId -1
     * @return
     * @throws IOException
     */
    public static JSONObject getGroupListV2(String unitCode, String groupId, String actId, Integer type, String groupList) {

        String typeStr = ObjectUtils.isEmpty(type) ? "0" : String.valueOf(type);
        Map<String, String> param = new HashMap<String, String>();
        String sign = SignUtil.fetchSign(param);
        param.put("unit_code", unitCode);
        param.put("group_id", groupId);
        param.put("act_id", actId);
        param.put("type", typeStr);
        if (!ObjectUtils.isEmpty(groupList)) {
            param.put("group_list", groupList);
        }
        Request request = Request.Post(SysParam.MACALLINE_URL + "activity/GetGroupListV2");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("redstar-call-app-id", SysParam.MACALLINE_APP_ID);
        request.setHeader("redstar-sign", sign);
        String resp = "";
        try {
            resp = HttpUtil.fetch(request, param);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info(Constant.MSG_INFO_GETGROUPLIST, JSONObject.toJSONString(param), "");
        if (!ObjectUtils.isEmpty(resp)) {
            JSONObject jsonObject = JSONObject.parseObject(resp);
            if (!ObjectUtils.isEmpty(jsonObject)) {
                return jsonObject.getJSONObject("dataMap");
            }
        }
        return null;
    }


}
