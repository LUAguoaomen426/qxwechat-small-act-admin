package com.red.star.macalline.act.admin.core.act;


import com.red.star.macalline.act.admin.constant.Constant;
import com.red.star.macalline.act.admin.util.DateNewUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.core.act
 * @Description: 全民营销测试页
 * @Author: AMGuo
 * @CreateDate: 2019-06-03 15:35
 * @Version: 1.0
 */
public class QmxyTestAct implements Act {

//    @Resource
//    private RedisRunner redisRunner;

    public static final String sourceJuly = "qmyxTest";

    public String endTimeStr = "2999-01-01";

    @Override
    public String getPosterId() {
        String postId = "92";
//        String key = "QmyxTestPostId";
//        if(!redisRunner.cacheExists(key)){
//            if(!CheckUtil.isEmpty(SysParam.ACT_CODE_QMYX_TEST)){
//                postId = SysParam.ACT_CODE_QMYX_TEST;
//            }else {
//                postId = "92";
//            }
//            redisRunner.cacheAddUpdate(key,postId,-1);
//        }else {
//            postId = redisRunner.cacheGet(key);
//        }
        return postId;
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
