package com.red.star.macalline.act.admin.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.red.star.macalline.act.admin.domain.Mall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.entity.dto
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-05-29 11:10
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfoDTO {

    /**
     * 支付金额
     */
    @JsonProperty("retail_price")
    private BigDecimal retailPrice;

    /**
     * 开始使用时间（字符串：精确到天）
     */
    @JsonProperty("use_begin_time_desc")
    private String useBeginTimeDesc;

    /**
     * 结束使用时间（字符串：精确到天）
     */
    @JsonProperty("use_end_time_desc")
    private String useEndTimeDesc;

    /**
     * 开始使用时间
     */
    @JsonProperty("use_begin_time")
    private Date useBeginTime;

    /**
     * 结束使用时间
     */
    @JsonProperty("use_end_time")
    private Date useEndTime;

    /**
     * 券名称（龙翼券类型不为1、4、9时）
     */
    @JsonProperty("ticket_name")
    private String ticketName;

    /**
     * 使用说明
     */
    @JsonProperty("use_remark")
    private String useRemark;

    /**
     * 券包包含的券列表
     */
    @JsonProperty("ticket_list")
    private List<TicketInfoDTO> ticketList;

    /**
     * 龙翼券类型
     * 0:免费优惠券;1:免费单品券;2:付费优惠券;3:礼品券;4:付费单品券;5:免费券包;6:付费券包;7:家装券;8:设计师券;9:集团付费单品券;10:集团免费券包;11:集团免费优惠券
     */
    @JsonProperty("ticket_type2")
    private Integer ticketType2;

    /**
     * 券名称（龙翼券类型为1、4、9时）
     */
    @JsonProperty("item_name")
    private String itemName;

    /**
     * 图片
     */
    @JsonProperty("list_img")
    private String listImg;

    /**
     * 品牌名称
     */
    @JsonProperty("item_brand")
    private String itemBrand;

    /**
     * 标题名称
     */
    @JsonProperty("item_title")
    private String itemTitle;

    /**
     * 券id
     */
    @JsonProperty("promotion_ticket_id")
    private Integer promotionTicketId;

    /**
     * 券类型
     */
    @JsonProperty("ticket_type_id")
    private Integer ticketTypeId;

    /**
     * 所属商场
     */
    private String mallName;

    /**
     * 环境标识：1微信2小程序3微信外
     */
    private Integer subChannel;

    /**
     * 券类型
     * 1 券包  
     * 2 单品券  
     * 3 团专享券 （集赞送的券）
     * 4 礼品券   
     * 5 抽奖券  
     * 6 集赞券 
     * 7 签到无门槛券
     * 8 满减券
     * 9 红酒券
     * 10 抽奖无门槛券
     * 11 秒杀券
     * 12 打卡券
     * 13 小大促领券狂享
     */
    private Integer type;

    /**
     * 购券用户openid
     */
    private String openId;

    /**
     * 会员名称
     */
    private String vipName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 券所属团名称
     */
    private String groupName;

    /**
     * 券所属团id
     */
    private Integer groupId;

    /**
     * 券所属商场
     */
    private String omsCode;

    /**
     * 券id
     */
    private Integer ticketId;

    /**
     * 来源（活动标识）朝阳
     */
    private Integer actCode;

    /**
     * 来源（活动标识）
     */
    private String source;

    /**
     * 文字使用说明
     */
    private String instruction;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 券类型 0.oms 1.龙翼 3.喵零
     */
    private Integer omsType;

    /**
     * 集团劵类型
     * 0:免费优惠券;1:免费单品券;2:付费优惠券;3:礼品券;4:付费单品券;5:免费券包;6:付费券包;
     * 7:家装券;8:设计师券;9:集团付费单品券;10:集团免费券包;11:集团免费优惠券;12:集团免费单品券
     */
    private Integer subType;

    /**
     * 大促商场信息
     */
    private Mall actMallInfo;

    public TicketInfoDTO(BigDecimal retailPrice, Date useBeginTime, Date useEndTime, String mallName, Integer subChannel,
                         Integer type, String openId, String ticketName, String vipName, String avatar, String uid,
                         Integer ticketTypeId, String groupName, Integer groupId, String omsCode, Integer ticketId,
                         Integer actCode, String mobile) {
        this.retailPrice = retailPrice;
        this.useBeginTime = useBeginTime;
        this.useEndTime = useEndTime;
        this.mallName = mallName;
        this.subChannel = subChannel;
        this.type = type;
        this.openId = openId;
        this.ticketName = ticketName;
        this.vipName = vipName;
        this.avatar = avatar;
        this.uid = uid;
        this.ticketTypeId = ticketTypeId;
        this.groupName = groupName;
        this.groupId = groupId;
        this.omsCode = omsCode;
        this.ticketId = ticketId;
        this.actCode = actCode;
        this.mobile = mobile;
    }


}
