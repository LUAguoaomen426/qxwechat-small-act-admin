package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.star.macalline.act.admin.domain.ActReportDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.mapper
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-23 12:34
 * @Version: 1.0
 */
public interface ActReportDictMybatisMapper extends BaseMapper<ActReportDict> {

    List<ActReportDict> findSonByParentIdAndSource(@Param("value") String value,@Param("source") String source);
}
