package com.red.star.macalline.act.admin.core.act;


import com.red.star.macalline.act.admin.constant.Constant;
import com.red.star.macalline.act.admin.domain.vo.SysParam;
import com.red.star.macalline.act.admin.util.DateNewUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * 套房指南活动
 *
 * @author vincce
 */
public class GuideAct implements Act {

    /**
     * 活动标识
     */
    public static final String source = "guide";

    /**
     * 结束时间
     */
    public String endTimeStr = "2999-01-01";

    @Override
    public String getPosterId() {
        return SysParam.ACT_CODE_GUIDE;
    }

    @Override
    public String getChannelId() {
        Integer channelId = Constant.ACT_CHANNELID_16;
        return String.valueOf(channelId);
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String findNameByGroupId(Integer groupId) {
        return null;
    }

    @Override
    public Date getEndTime() {
        Date date = new Date();
        try {
            date = DateNewUtil.parseAnyString(endTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public Integer getLuckyMaximumNum() {
        return null;
    }

    @Override
    public Integer getBoostMaximumNum() {
        return null;
    }
}
