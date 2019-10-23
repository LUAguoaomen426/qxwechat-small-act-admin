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
public class DefaultAct implements Act {


    @Override
    public String getPosterId() {
        return SysParam.ACT_CODE_SEP;
    }

    @Override
    public String getChannelId() {
        return String.valueOf(Constant.ACT_CHANNELID_17);
    }

    @Override
    public String getSource() {
        return null;
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
