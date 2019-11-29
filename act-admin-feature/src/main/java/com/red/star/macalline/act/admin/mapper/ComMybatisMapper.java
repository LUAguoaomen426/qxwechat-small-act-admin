package com.red.star.macalline.act.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.star.macalline.act.admin.domain.bo.FlopBo;
import com.red.star.macalline.act.admin.domain.bo.LuckyBo;
import com.red.star.macalline.act.admin.domain.bo.SourcePvUvBo;
import com.red.star.macalline.act.admin.domain.vo.FlopVo;
import com.red.star.macalline.act.admin.domain.vo.LuckyVo;
import com.red.star.macalline.act.admin.domain.vo.SourcePvUvVo;
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

    /**
     * 中奖数据
     *
     * @param luckyBo
     * @return
     */
    List<LuckyVo> analysisLuckyData(@Param("luckyBo") LuckyBo luckyBo);

    /**
     *
     *大转盘抽奖记录
     * @param luckyBo
     * @return
     */
    List<LuckyVo> analysisLuckyWheelData(@Param("luckyBo") LuckyBo luckyBo);

    /**
     * 根据团id和活动来源获取打卡次数
     *
     * @param groupId
     * @param source
     * @return
     */
    int findRecordByGroupIdAndSource(@Param("groupId") Integer groupId, @Param("source") String source);


    /**
     * 活动pv、uv（按时间分组）
     *
     * @param sourcePvUvBo
     * @return
     */
    List<SourcePvUvVo> analysisPVUVData(@Param("sourcePvUvBo") SourcePvUvBo sourcePvUvBo);

    /**
     * 活动pv、uv
     *
     * @param sourcePvUvBo1
     * @return
     */
    SourcePvUvVo analysisPVUV(@Param("sourcePvUvBo") SourcePvUvBo sourcePvUvBo1);

    /**
     * 日期内总pv、uv
     *
     * @param sourcePvUvBo
     * @return
     */
    SourcePvUvVo analysisPVUVDataTotal(@Param("sourcePvUvBo") SourcePvUvBo sourcePvUvBo);
}
