package com.red.star.macalline.act.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.star.macalline.act.admin.domain.ActReportDict;
import com.red.star.macalline.act.admin.service.dto.BtnDailyReportQueryCriteria;
import com.red.star.macalline.act.admin.service.dto.SignUpQueryCriteria;
import org.springframework.cache.annotation.Cacheable;

import java.text.ParseException;
import java.util.List;
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
     * 留资表单参数
     * @return
     */
    @Cacheable(value = "imp:act:admin:report:signUp:form",key = "#source")
    Map<String,Object> getSignUpFormParam(String source);

    /**
     *  查询留资信息
     * @param criteria
     * @param page
     * @return
     */
    Map<String,Object> querySignUpReportData(String source,SignUpQueryCriteria criteria, Page page) throws ParseException;
    /**
     * findByPid
     *
     * @param pid
     * @param source
     * @return
     */
    @Cacheable(value = "imp:act:admin:report:dict", key = "'pid:'+#p0+'-source:'+#source")
    List<ActReportDict> findByPid(int pid, String source);

    /**
     * 查询字典树
     *
     * @param byPid
     * @param source
     * @return
     */
    @Cacheable(value = "imp:act:admin:report:dict", key = "'tree'+#source")
    Object getDictTree(List<ActReportDict> byPid, String source);
}