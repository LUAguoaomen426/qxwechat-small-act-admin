package com.red.star.macalline.act.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.star.macalline.act.admin.service.dto.BtnDailyReportQueryCriteria;
import com.red.star.macalline.act.admin.service.dto.SignUpQueryCriteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.text.ParseException;
import java.util.Map;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
//@CacheConfig(cacheNames = "imp:act:admin:report")
public interface ReportService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param page
     * @return
     */
    @Cacheable(value = "imp:act:admin:report")
    Map<String, Object> queryAll(BtnDailyReportQueryCriteria criteria, Page page);

    /**
     *
     * @param criteria
     * @param page
     * @return
     */
    Map<String,Object> querySignUpReportData(SignUpQueryCriteria criteria, Page page) throws ParseException;
}