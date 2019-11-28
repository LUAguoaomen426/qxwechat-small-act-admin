package com.red.star.macalline.act.admin.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页工具
 *
 * @author Zheng Jie
 * @date 2018-12-10
 */
public class PageMybatisUtil extends cn.hutool.core.util.PageUtil {

    /**
     * List 分页
     *
     * @param page
     * @param size
     * @param list
     * @return
     */
    public static List toPage(int page, int size, List list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;

        if (fromIndex > list.size()) {
            return new ArrayList();
        } else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        } else {
            return list.subList(fromIndex, toIndex);
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     *
     * @param page
     * @return
     */
    public static Map toPage(IPage page) {
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", page.getRecords());
        map.put("totalElements", page.getTotal());
        return map;
    }

    /**
     * @param object
     * @param totalElements
     * @return
     */
    public static Map toPage(Object object, Object totalElements) {
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", object);
        map.put("totalElements", totalElements);

        return map;
    }

}
