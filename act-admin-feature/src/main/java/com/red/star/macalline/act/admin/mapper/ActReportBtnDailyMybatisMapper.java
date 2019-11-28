package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.star.macalline.act.admin.domain.ActReportBtnDaily;
import com.red.star.macalline.act.admin.service.dto.BtnDailyReportDTO;
import org.apache.ibatis.annotations.Param;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.mapper
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-23 12:34
 * @Version: 1.0
 */
public interface ActReportBtnDailyMybatisMapper extends BaseMapper<ActReportBtnDaily> {


    /**
     * 每日按钮点击记录
     *
     * @param page
     * @param wrapper
     * @return
     */
    Page<BtnDailyReportDTO> queryListByPage(Page page, @Param("ew") Wrapper<ActReportBtnDaily> wrapper);

    /**
     * 汇总按钮点击记录
     *
     * @param page
     * @param qryWrapper
     * @return
     */
    Page<BtnDailyReportDTO> querySummaryListByPage(Page page, @Param("ew") QueryWrapper<ActReportBtnDaily> qryWrapper);
}
