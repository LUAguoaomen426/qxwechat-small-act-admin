package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.star.macalline.act.admin.domain.ActModule;

import java.util.List;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.mapper
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-23 12:34
 * @Version: 1.0
 */
public interface ActModuleMybatisMapper extends BaseMapper<ActModule> {


    /**
     * 查询所有可见活动列表
     * @return 活动列表
     */
    List<ActModule> listEnableAct();

    Integer findMaxLevel();

}
