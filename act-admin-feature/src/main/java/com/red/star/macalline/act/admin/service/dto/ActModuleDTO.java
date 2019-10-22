package com.red.star.macalline.act.admin.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author AMGuo
* @date 2019-10-22
*/
@Data
public class ActModuleDTO implements Serializable {

    // 主键ID
    private Integer id;

    // 活动code
    private String actCode;

    // 活动结束时间
    private Timestamp endTime;

    // 模块名称
    private String moduleName;

    // 模块英文名称
    private String secondModuleName;

    // 模块类型 1:H5链接 2:小程序链接 0 不可变  3：其它（非红星美凯龙小程序）小程序链接
    private Integer moduleType;

    // 展示图片
    private String showImage;

    // 模块链接地址
    private String linkUrl;

    // 排序
    private Integer orderLevel;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    // 是否逻辑删除
    private Integer isDelete;

    // module_type为3时，不为空
    private String programConfig;

    // 活动类型：1：大促活动  0：其它
    private Integer subType;

    // 新老全民营销标识
    private Integer tarFlag;

    // 全民营销标识
    private String event;

    // 全民营销标识
    private String tarToken;

    // 全民营销标识
    private String werenwuCode;

    // 活动结束时间
    private Timestamp actEndTime;

    // 电子海报id
    private String posterId;

    // 部分配置数据
    private String configData;

    // 渠道id
    private String channelId;
}