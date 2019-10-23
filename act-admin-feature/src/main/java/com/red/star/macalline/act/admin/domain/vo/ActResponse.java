package com.red.star.macalline.act.admin.domain.vo;

import com.alibaba.fastjson.JSONObject;
import com.red.star.macalline.act.admin.constant.ResponseConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: macalline-work-server
 * @Package: com.red.star.macalline.wx.entity
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2018-12-05 13:59
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActResponse<T> {

    /**
     * 响应码
     **/
    private Integer code;
    /**
     * 响应消息（错误消息）
     **/
    private String message;

    /**
     * 是否成功
     **/
    private boolean success;

    /**
     * 扩展map
     */
    private T dataMap;

    public static ActResponse buildErrorResponse() {
        return new Builder()
                .code(ResponseConstant.RESPONSE_CODE_ERROR)
                .message(ResponseConstant.RESPONSE_MESSAGE_ERROR)
                .success(false)
                .build();
    }

    public static ActResponse buildErrorResponse(String message) {
        return new ActResponse.Builder()
                .code(ResponseConstant.RESPONSE_CODE_ERROR)
                .message(message)
                .success(false)
                .build();
    }

    public static ActResponse buildSuccessResponse() {
        return new ActResponse.Builder()
                .code(200)
                .message(ResponseConstant.RESPONSE_MESSAGE_SUCCESS)
                .success(true)
                .build();
    }

    public static ActResponse buildSuccessResponse(Object dataMap) {
        //转换微信接口出参结构
        if (!ObjectUtils.isEmpty(dataMap) && dataMap instanceof JSONObject) {
            JSONObject dataMapObject = (JSONObject) dataMap;
            Integer code = dataMapObject.getInteger("code");
            Object dataMap1 = dataMapObject.get("dataMap");
            String message = dataMapObject.getString("message");
            boolean success = dataMapObject.getBooleanValue("success");
            if (null != code) {
                code = (0 == code.intValue()) ? 200 : code;
                success = (200 == code) ? true : false;
                return new ActResponse.Builder()
                        .code(code)
                        .message(message)
                        .success(success)
                        .dataMap(dataMap1)
                        .build();
            }
        }
        return new ActResponse.Builder()
                .code(200)
                .message(ResponseConstant.RESPONSE_MESSAGE_SUCCESS)
                .success(true)
                .dataMap(dataMap)
                .build();
    }

    public static ActResponse buildSuccessResponse(String key, Object dataMap) {
        return new ActResponse.Builder()
                .code(200)
                .message(ResponseConstant.RESPONSE_MESSAGE_SUCCESS)
                .success(true)
                .dataMap(key, dataMap)
                .build();
    }

    public static ActResponse buildParamEmptyError(String param) {
        return new ActResponse.Builder()
                .code(ResponseConstant.RESPONSE_CODE_PARAM_EMPTY)
                .message(ResponseConstant.RESPONSE_MESSAGE_PARAM_EMPTY + param)
                .success(false)
                .build();
    }

    public static ActResponse buildCustomerParamEmptyError(String param) {
        return new ActResponse.Builder()
                .code(ResponseConstant.RESPONSE_CODE_CUSTOM)
                .message(param)
                .success(false)
                .build();
    }

    public static ActResponse buildParamFormatError(String param) {
        return new ActResponse.Builder()
                .code(ResponseConstant.RESPONSE_CODE_PARAM_FORMAT_ERROR)
                .message(ResponseConstant.RESPONSE_MESSAGE_PARAM_FORMAT_ERROR + param)
                .success(false)
                .build();
    }

    /**
     * token为空或者从token中拿数据失败，一定要返回这个Response
     *
     * @return
     */
    public static ActResponse buildAuthError() {
        return new ActResponse.Builder()
                .code(ResponseConstant.RESPONSE_CODE_AUTH_ERROR)
                .message(ResponseConstant.RESPONSE_MESSAGE_AUTH_ERROR)
                .success(false)
                .build();
    }

    public static ActResponse buildNoResourceError(String resource) {
        return new ActResponse.Builder()
                .code(ResponseConstant.RESPONSE_CODE_NO_RESOURCE)
                .message(ResponseConstant.RESPONSE_MESSAGE_NO_RESOURCE + resource)
                .success(false)
                .build();
    }

    public static ActResponse buildParamError(String defaultMessage) {
        return new ActResponse.Builder()
                .code(ResponseConstant.RESPONSE_CODE_PARAM_ERROR)
                .message(ResponseConstant.RESPONSE_MESSAGE_PARAM_ERROR + defaultMessage)
                .success(false)
                .build();
    }

    public static class Builder {

        private Integer code;
        private String message;
        private Map dataMap;
        private boolean success;

        public ActResponse.Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public ActResponse.Builder message(String message) {
            this.message = message;
            return this;
        }

        public ActResponse.Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public ActResponse.Builder dataMap(String key, Object dataMap) {
            if (this.dataMap == null) {
                this.dataMap = new HashMap<>();
            }
            this.dataMap.put(key, dataMap);
            return this;
        }

        public ActResponse.Builder dataMap(Object dataMap) {
            if (ObjectUtils.isEmpty(dataMap)) {
                return this;
            }

            if (this.dataMap == null) {
                this.dataMap = new HashMap<>();
            }

            if (dataMap instanceof Map) {
                this.dataMap.putAll((Map) dataMap);
                return this;
            }
            String key = "";
            if (dataMap instanceof List) {
                key = getSimpleName(((List) dataMap).get(0).getClass());
            } else {
                key = getSimpleName(dataMap.getClass());
            }
            if (!ObjectUtils.isEmpty(key)) {
                this.dataMap.put(key, dataMap);
            }
            return this;
        }

        public ActResponse build() {
            ActResponse actResponse = new ActResponse();
            actResponse.code = this.code;
            actResponse.message = this.message;
            actResponse.dataMap = this.dataMap;
            actResponse.success = this.success;
            return actResponse;
        }
    }

    private static String getSimpleName(Class clazz) {
        return clazz.getSimpleName().toLowerCase();
    }
}
