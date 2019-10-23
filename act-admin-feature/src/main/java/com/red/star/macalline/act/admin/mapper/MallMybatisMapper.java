package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.star.macalline.act.admin.domain.Mall;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.mapper
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-23 12:34
 * @Version: 1.0
 */
public interface MallMybatisMapper extends BaseMapper<Mall> {

    /**
     * 更新tb_wap_mall
     *
     * @param mallList
     */
    void updateWapMall(@Param("mallList") List<Mall> mallList);

    @MapKey("omsCode")
    Map<String,Mall> findAllMallWithKeyOmsCode();



    /**
     * 新增tb_act_mall_merge
     *
     * @param actCode
     * @param mallList
     */
    void insertActMallMerge(@Param("actCode") String actCode, @Param("mallList") List<Mall> mallList);

    /**
     * 通过活动ID查询所有商场信息
     *
     * @param actCode
     * @return
     */
    List<Mall> findMalInfoByActCode(@Param("actCode") String actCode);


    /**
     * 更新 tb_act_mall_merge,is_join 字段取list中的值
     *
     * @param actCode
     * @param mallList
     */
    void updateActMallMerge(@Param("actCode") String actCode, @Param("mallList") List<Mall> mallList);

    /**
     * 根据city将的defultEnable设置为0
     *
     * @param city
     */
    void updateActMallDefultEnableByCity(@Param("city") String city);

    /**
     * 查询活动下商场列表
     *
     * @param actCode 活动编码
     * @return 商场列表
     */
    List<Mall> listMallByAct(String actCode);

    void updateActMergeIsJoinByActCode(String actCode, boolean b);

    List<Mall> findMallDefultInfo();
}
