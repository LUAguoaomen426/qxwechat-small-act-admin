package com.red.star.macalline.act.admin.core.act;

import com.red.star.macalline.act.admin.constant.Constant;
import com.red.star.macalline.act.admin.domain.vo.SysParam;
import com.red.star.macalline.act.admin.util.DateNewUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.core.act
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-06-03 15:35
 * @Version: 1.0
 */
public class HotAct implements Act {

    /**
     * 爆款活动标识
     */
    public static final String sourceHot = "hot";

    /**
     * 七月大促结束时间
     */
    public String endTimeStr = "2019-07-15";

    public Boolean isEnd() {
        boolean before = false;
        try {
            Date endTime = DateNewUtil.parseAnyString(endTimeStr + " 00:00:00");
            before = endTime.before(DateNewUtil.getCurrentDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return before;
    }

    @Override
    public String getPosterId() {
        String actCode = SysParam.ACT_CODE;
        if (!isEnd()) {
            actCode = SysParam.ACT_CODE_JULY;
        }
        return actCode;
    }

    @Override
    public String getChannelId() {
        Integer channelId = Constant.ACT_CHANNELID_17;
        if (!isEnd()) {
            channelId = Constant.ACT_CHANNELID_16;
        }
        return String.valueOf(channelId);
    }

    @Override
    public String getSource() {
        return sourceHot;
    }

    @Override
    public String findNameByGroupId(Integer groupId) {
        return "爆品列表";
    }

    @Override
    public Date getEndTime() {
        Date date = new Date();
        try {
            date = DateNewUtil.parseAnyString("2099-01-01");
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
