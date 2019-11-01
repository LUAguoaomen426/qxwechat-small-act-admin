package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.ActSpecLink;
import com.red.star.macalline.act.admin.domain.Mall;
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
public interface ActSpecLinkMybatisMapper extends BaseMapper<ActSpecLink> {


    List<ActSpecLink> listFindSpecLinkByActCode(String actCode);

    /**
     * 传入specLinkId 以及mall来设置act_mall_spec_merge表信息
     * @param actCode
     * @param mallList
     */
    void insertSpecLinkMergeByList(@Param("actCode")String actCode,@Param("specCode")String specCode, @Param("mallList") List<Mall> mallList);

    /**
     * 根据actCode、specCode将isShow字段更改
     * @param actCode
     * @param specCode
     * @param isShow
     */
    void updateSpecLinkMergerisShow(@Param("actCode")String actCode, @Param("specCode")String specCode, @Param("isShow")Boolean isShow);

    /**
     * 批量更新tb_act_mall_spec_merge
     * @param actCode
     * @param mallList
     */
    void updateSpecLinkMergerByList(@Param("actCode")String actCode,@Param("specCode")String specCode, @Param("mallList") List<Mall> mallList);

    Integer selectMaxSort(@Param("specCode") String specCode);

    /**
     * 批量删除tb_act_mall_spec_merge
     * @param actCode
     */
    void deleteSpecLinkMergerByList(@Param("actCode")String actCode,@Param("specCode")String specCode);



}
