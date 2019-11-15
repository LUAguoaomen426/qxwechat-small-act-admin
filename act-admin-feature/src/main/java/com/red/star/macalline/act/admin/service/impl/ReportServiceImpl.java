package com.red.star.macalline.act.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.red.star.macalline.act.admin.domain.ActReportBtnDaily;
import com.red.star.macalline.act.admin.domain.SignUp;
import com.red.star.macalline.act.admin.domain.ActReportDict;
import com.red.star.macalline.act.admin.mapper.ActReportBtnDailyMybatisMapper;
import com.red.star.macalline.act.admin.mapper.SignUpMapper;
import com.red.star.macalline.act.admin.mapper.ActReportDictMybatisMapper;
import com.red.star.macalline.act.admin.service.ActModuleService;
import com.red.star.macalline.act.admin.service.ReportService;
import com.red.star.macalline.act.admin.service.dto.BtnDailyReportDTO;
import com.red.star.macalline.act.admin.service.dto.BtnDailyReportQueryCriteria;
import com.red.star.macalline.act.admin.service.dto.SignUpQueryCriteria;
import com.red.star.macalline.act.admin.util.DateNewUtil;
import com.red.star.macalline.act.admin.utils.PageMybatisUtil;
import javafx.beans.binding.ObjectExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.text.ParseException;
import java.util.HashMap;
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
    @Resource
    private SignUpMapper signUpMapper;

    @Resource
    private ActReportDictMybatisMapper reportDictMybatisMapper;

    @Override
    public Map<String, Object> queryAll(BtnDailyReportQueryCriteria criteria, Page page) {
        QueryWrapper<ActReportBtnDaily> qryWrapper = new QueryWrapper<>();
        qryWrapper
                .eq("t.source", criteria.getSource())
                .orderBy(true, false, "t.id");
        if (!ObjectUtils.isEmpty(criteria.getDictId())) {
            qryWrapper.eq("tr.id", criteria.getDictId());
        }
        if (!ObjectUtils.isEmpty(criteria.getDataDateStart()) && !ObjectUtils.isEmpty(criteria.getDataDateEnd())) {
            qryWrapper.between("t.data_date", criteria.getDataDateStart(), criteria.getDataDateEnd());
        }
        Page<BtnDailyReportDTO> reportBtnDailyIPage = reportBtnDailyMybatisMapper.queryListByPage(page, qryWrapper);
        return PageMybatisUtil.toPage(reportBtnDailyIPage);
    }

    @Override
    public Map<String, Object> querySignUpReportData(SignUpQueryCriteria criteria, Page page) throws ParseException {
        QueryWrapper<SignUp> wrapper = new QueryWrapper<>();

        List<ActReportDict> decSuper = reportDictMybatisMapper.findSonByParentIdAndSource("action-sign-up", "decSuper");

        if(!ObjectUtils.isEmpty(criteria.getStartTime()) && !ObjectUtils.isEmpty(criteria.getEndTime()))
            wrapper.between("s.update_time",DateNewUtil.parseIsoString(criteria.getStartTime()),DateNewUtil.parseIsoString(criteria.getEndTime()));
        if(!ObjectUtils.isEmpty(criteria.getName()))
            wrapper.eq("s.name",criteria.getName());
        if (!ObjectUtils.isEmpty(criteria.getCliType()))
            wrapper.eq("s.cli_type",criteria.getCliType());
        if(!ObjectUtils.isEmpty(criteria.getType()))
            wrapper.eq("s.type",criteria.getType());
        if (!ObjectUtils.isEmpty(criteria.getMallCondition()))
            wrapper.like("m.mall_name",criteria.getMallCondition());
        if(!ObjectUtils.isEmpty(criteria.getMobile()))
            wrapper.like("s.mobile",criteria.getMobile());
        if(!ObjectUtils.isEmpty(criteria.getScene()))
            wrapper.like("s.scene",criteria.getScene());
        Page<SignUp> listByPage = signUpMapper.findListByPage(page,wrapper);

        return PageMybatisUtil.toPage(listByPage);
    }

    @Override
    public List<ActReportDict> findByPid(int pid) {
        List<ActReportDict> actReportDictList = reportDictMybatisMapper.selectList(new LambdaQueryWrapper<ActReportDict>()
                .eq(ActReportDict::getPid, pid));
        return actReportDictList;
    }

    @Override
    public Object getDictTree(List<ActReportDict> dataList) {
        List<Map<String, Object>> list = new LinkedList<>();
        dataList.forEach(entity -> {
                    if (entity != null) {
                        List<ActReportDict> permissionList = findByPid(entity.getId());
                        Map<String, Object> map = Maps.newHashMap();
                        map.put("id", entity.getId());
                        map.put("label", entity.getLabel());
                        if (permissionList != null && permissionList.size() != 0) {
                            map.put("children", getDictTree(permissionList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

}