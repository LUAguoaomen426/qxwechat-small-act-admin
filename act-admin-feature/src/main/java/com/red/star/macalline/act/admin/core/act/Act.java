package com.red.star.macalline.act.admin.core.act;

import java.util.Date;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.core.act
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-06-03 15:28
 * @Version: 1.0
 */
public interface Act {

    /**
     * 获取活动编号：根据活动、环境改变
     *
     * @return
     */
    String getPosterId();

    /**
     * ChannelId：活动期间为16，非活动期间为17（不分环境）
     *
     * @return
     */
    String getChannelId();

    /**
     * 获取活动来源
     *
     * @return
     */
    String getSource();

    /**
     * 通过团id查询名称
     *
     * @param groupId
     * @return
     */
    String findNameByGroupId(Integer groupId);

    /**
     * 当前活动列表隐藏时间
     *
     * @return
     */
    Date getEndTime();

    /**
     * 当前活动抽奖次数上限
     *
     * @return
     */
    Integer getLuckyMaximumNum();

    /**
     * 当前活动助力次数上限
     *
     * @return
     */
    Integer getBoostMaximumNum();

    /**
     * 获取活动配置的内容
     *
     * @param configKey
     * @return
     */
    default Object getConfig(String configKey) {
        return null;
    }

    /**
     * 获取主会场链接
     *
     * @return
     */
    default String getHomeUrl() {
        return null;
    }

    default Date getActEndTime() {
        return getEndTime();
    }

}
