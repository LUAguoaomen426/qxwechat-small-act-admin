package com.red.star.macalline.act.admin.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Date;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Entity
@Data
@Table(name = "tb_wap_act_module")
@TableName(value = "tb_wap_act_module")
public class ActModule implements Serializable {

    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 活动code
    @Column(name = "act_code", unique = true, nullable = false)
    private String actCode;

    // 活动结束时间
    @Column(name = "end_time")
    private Date endTime;

    // 模块名称
    @Column(name = "module_name", nullable = false)
    private String moduleName;

    // 模块英文名称
    @Column(name = "second_module_name", nullable = false)
    private String secondModuleName;

    // 模块类型 1:H5链接 2:小程序链接 0 不可变  3：其它（非红星美凯龙小程序）小程序链接
    @Column(name = "module_type", nullable = false)
    private Integer moduleType;

    // 展示图片
    @Column(name = "show_image")
    private String showImage;

    // 模块链接地址
    @Column(name = "link_url")
    private String linkUrl;

    // 排序
    @Column(name = "order_level")
    private Integer orderLevel;

    // 创建时间
    @Column(name = "create_time")
    private Date createTime;

    // 更新时间
    @Column(name = "update_time")
    private Date updateTime;

    // 是否逻辑删除
    @Column(name = "is_delete")
    private Boolean isDelete;

    // module_type为3时，不为空
    @Column(name = "program_config")
    private String programConfig;

    // 活动类型：1：大促活动  0：其它
    @Column(name = "sub_type")
    private Integer subType;

    // 新老全民营销标识
    @Column(name = "tar_flag")
    private Integer tarFlag;

    // 全民营销标识
    @Column(name = "event")
    private String event;

    // 全民营销标识
    @Column(name = "tar_token")
    private String tarToken;

    // 全民营销标识
    @Column(name = "werenwu_code")
    private String werenwuCode;

    // 活动结束时间
    @Column(name = "act_end_time")
    private Date actEndTime;

    // 电子海报id
    @Column(name = "poster_id")
    private String posterId;

    // 部分配置数据
    @Column(name = "config_data")
    private String configData;

    // 渠道id
    @Column(name = "channel_id")
    private String channelId;

    // 是否显示
    @Column(name = "show_flag")
    private Boolean showFlag;

    public void copy(ActModule source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}