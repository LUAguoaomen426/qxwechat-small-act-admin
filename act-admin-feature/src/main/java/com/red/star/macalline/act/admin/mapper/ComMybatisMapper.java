package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.star.macalline.act.admin.domain.vo.FlopBo;
import com.red.star.macalline.act.admin.domain.vo.FlopVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.mapper
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-24 14:02
 * @Version: 1.0
 */
public interface ComMybatisMapper extends BaseMapper {

    /**
     * 返回翻牌抽奖数据
     *
     * @param flopBo
     * @return
     */
    List<FlopVo> analysisFlopData(@Param("flopBo") FlopBo flopBo);
}
