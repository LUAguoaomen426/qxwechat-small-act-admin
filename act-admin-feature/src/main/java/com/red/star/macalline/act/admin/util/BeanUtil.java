package com.red.star.macalline.act.admin.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: macalline-work-server
 * @Package: com.red.star.macalline.data.utils
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2018-11-27 17:37
 * @Version: 1.0
 */
public class BeanUtil {

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, String> beanToMap(T bean) {
        Map<String, String> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                Object o = beanMap.get(key);
                if (!ObjectUtils.isEmpty(o)) {
                    if (o instanceof List) {
                        map.put(key + "", JSONObject.toJSONString(o));
                    } else {
                        map.put(key + "", o.toString());
                    }
                }
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapStrToBean(Map<String, String> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static Map<String, Object> convertMap(Map<String, String> beanMap) {
        Map<String, Object> result = Maps.newHashMap();
        beanMap.entrySet()
                .forEach(entity -> {
                    if (entity.getValue().equalsIgnoreCase("true") || entity.getValue().equalsIgnoreCase("false")) {
                        result.put(entity.getKey(), Boolean.valueOf(entity.getValue()));
                    } else if (entity.getKey().equalsIgnoreCase("extendValue1")) {
                        result.put(entity.getKey(), Integer.valueOf(entity.getValue()));
                    } else {
                        result.put(entity.getKey(), entity.getValue());
                    }
                });
        return result;
    }
}
