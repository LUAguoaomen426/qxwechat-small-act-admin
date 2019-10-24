package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.star.macalline.act.admin.domain.ActModule;
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
public interface ActModuleMybatisMapper extends BaseMapper<ActModule> {


    /**
     * 查询所有可见活动列表
     * @return 活动列表
     */
    List<ActModule> listEnableAct();


    /**
     * 通过posterId查询source
     * @param posterId
     * @return
     */
    String posterIdConvertToActCode(@Param("posterId")String posterId);

    Integer findMaxLevel();
    /**
     * 查询要移动的下一个级别的信息
     * @param level
     * @param isDown
     * @return
     */
    ActModule findNextLevelInfo(@Param("level") Integer level, @Param("isDown") Boolean isDown);

}
