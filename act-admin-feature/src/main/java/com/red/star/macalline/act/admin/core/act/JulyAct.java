package com.red.star.macalline.act.admin.core.act;


import com.red.star.macalline.act.admin.constant.Constant;
import com.red.star.macalline.act.admin.domain.vo.SysParam;
import com.red.star.macalline.act.admin.util.DateNewUtil;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.core.act
 * @Description: 七月活动标识转换
 * @Author: AMGuo
 * @CreateDate: 2019-06-03 15:35
 * @Version: 1.0
 */
public class JulyAct implements Act {

    /**
     * 七月大促活动标识
     */
    public static final String sourceJuly = "july";

    /**
     * 七月大促结束时间
     */
    public String endTimeStr = "2019-07-15";

    /**
     * 团名称
     */
    public static String GROUP_NAME_1 = "瓷砖团";

    public static String GROUP_NAME_2 = "智能家居团";

    public static String GROUP_NAME_3 = "地板团";

    public static String GROUP_NAME_4 = "床垫/沙发团";

    public static String GROUP_NAME_5 = "卫浴团";

    public static String GROUP_NAME_6 = "衣柜/橱柜团";


    @Override
    public String getPosterId() {
        return SysParam.ACT_CODE_JULY;
    }

    @Override
    public String getChannelId() {
        Integer channelId = Constant.ACT_CHANNELID_16;
        return String.valueOf(channelId);
    }

    @Override
    public String getSource() {
        return sourceJuly;
    }

    /**
     * 通过id查询团名称
     *
     * @param groupId
     * @return
     */
    @Override
    public String findNameByGroupId(Integer groupId) {
        if (ObjectUtils.isEmpty(groupId)) {
            return null;
        }
        String groupName = "";
        if (groupId.equals(Constant.INTEGER_1)) {
            groupName = GROUP_NAME_1;
        } else if (groupId.equals(Constant.INTEGER_2)) {
            groupName = GROUP_NAME_2;
        } else if (groupId.equals(Constant.INTEGER_3)) {
            groupName = GROUP_NAME_3;
        } else if (groupId.equals(Constant.INTEGER_4)) {
            groupName = GROUP_NAME_4;
        } else if (groupId.equals(Constant.INTEGER_5)) {
            groupName = GROUP_NAME_5;
        } else if (groupId.equals(Constant.INTEGER_6)) {
            groupName = GROUP_NAME_6;
        } else if (groupId.equals(Constant.INTEGER_7)) {
            groupName = "活动主页";
        }
        return groupName;
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
