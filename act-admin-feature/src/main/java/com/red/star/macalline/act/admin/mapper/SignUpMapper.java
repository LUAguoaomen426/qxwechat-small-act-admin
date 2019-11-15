package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.star.macalline.act.admin.domain.SignUp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description TODO
 * @Date 2019/11/14 14:12
 * @Created by Akari
 */
public interface SignUpMapper extends BaseMapper<SignUp> {
    Page<SignUp> findListByPage(Page page,@Param("ew") QueryWrapper<SignUp> wrapper);
}
