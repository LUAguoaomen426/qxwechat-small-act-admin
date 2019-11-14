package com.red.star.macalline.act.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.star.macalline.act.admin.domain.ActReportBtnDaily;
import com.red.star.macalline.act.admin.mapper.ActReportBtnDailyMybatisMapper;
import com.red.star.macalline.act.admin.service.ActModuleService;
import com.red.star.macalline.act.admin.service.ReportService;
import com.red.star.macalline.act.admin.service.dto.BtnDailyReportDTO;
import com.red.star.macalline.act.admin.service.dto.BtnDailyReportQueryCriteria;
import com.red.star.macalline.act.admin.utils.PageMybatisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActModuleService.class);

    @Resource
    private ActReportBtnDailyMybatisMapper reportBtnDailyMybatisMapper;

    @Override
    public Map<String, Object> queryAll(BtnDailyReportQueryCriteria criteria, Page page) {
        QueryWrapper<ActReportBtnDaily> qryWrapper = new QueryWrapper<>();
        qryWrapper
                .eq("t.source", criteria.getSource())
                .orderBy(true, false, "t.id");
        if (!ObjectUtils.isEmpty(criteria.getModuleName())) {
            qryWrapper.like("t.module_name", criteria.getModuleName());
        }
        if (!ObjectUtils.isEmpty(criteria.getDataDateStart()) && !ObjectUtils.isEmpty(criteria.getDataDateEnd())) {
            qryWrapper.between("t.data_date", criteria.getDataDateStart(), criteria.getDataDateEnd());
        }
        Page<BtnDailyReportDTO> reportBtnDailyIPage = reportBtnDailyMybatisMapper.queryListByPage(page, qryWrapper);
        return PageMybatisUtil.toPage(reportBtnDailyIPage);
    }

}