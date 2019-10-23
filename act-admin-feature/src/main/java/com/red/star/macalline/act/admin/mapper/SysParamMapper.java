package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.star.macalline.act.admin.domain.Mall;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author 17324
 */
public interface SysParamMapper extends BaseMapper<Mall> {

    /**
     * 查询系统参数
     *
     * @return
     */
    List<Map<String, String>> findSysParam();

    @Update("UPDATE sys_param SET param_value = #{value} WHERE param_key = #{key}")
    void updateSysParam(@Param("key") String key, @Param("value") String value);

    @Insert("INSERT INTO sys_param(param_key, param_value) VALUES (#{key}, #{value})")
    void insertSysParam(@Param("key") String key, @Param("value") String value);
}
