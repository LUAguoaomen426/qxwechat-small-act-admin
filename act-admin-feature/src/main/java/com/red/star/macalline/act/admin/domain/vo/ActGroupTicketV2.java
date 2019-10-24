package com.red.star.macalline.act.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tx_li
 * @date 2019/9/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActGroupTicketV2 {

    @JsonProperty("sub_title")
    private String subTitle;

    /**
     * 现金券面额
     */
    @JsonProperty("cash_amt")
    private BigDecimal cashAmt;

    /**
     * 满降券满，折扣券满
     */
    @JsonProperty("fullcut_full_amt")
    private Double fullcutFullAmt;

    /**
     * 满降券减，折扣券会员价
     */
    @JsonProperty("fullcut_cut_amt")
    private Double fullcutCutAmt;

    @JsonProperty("item_name")
    private String itemName;

    @JsonProperty("item_brand")
    private String itemBrand;

    @JsonProperty("list_price")
    private BigDecimal listPrice;

    /**
     * 0:免费优惠券;1:免费单品券;2:付费优惠券;3:礼品券;4:付费单品券;5:免费券包;6:付费券包;
     * / 7:家装券;8:设计师券;9:集团付费单品券;10:集团免费券包;11:集团免费优惠券;12:集团免费单品券
     */
    @JsonProperty("ticket_type2")
    private Integer ticketType2;

    @JsonProperty("sort")
    private Integer sort;

    @JsonProperty("single_ticket_id")
    private Integer singleTicketId;

    /**
     * 销售价(付费单品券)
     */
    @JsonProperty("retail_price")
    private BigDecimal retailPrice;

    /**
     * 1现金券 2满降券 3折扣券
     */
    @JsonProperty("ticket_type_id")
    private Integer ticketTypeId;

    @JsonProperty("ticket_name")
    private String ticketName;


    @JsonProperty("group_id")
    private String groupId;

    @JsonProperty("grade")
    private String grade;

    @JsonProperty("unit_code")
    private String unitCode;

    @JsonProperty("list_img")
    private String listImg;

    @JsonProperty("vip_price")
    private BigDecimal vipPrice;

    @JsonProperty("item_title")
    private String itemTitle;

    @JsonProperty("grade_name")
    private String gradeName;

    /**
     * OMS类型(0:OMS,1:龙翼,3.喵零券)
     */
    @JsonProperty("oms_type")
    private Integer omsType;

    /**
     * OMS类型(52：满减 53：每满减)
     */
    @JsonProperty("ly_ticket_type")
    private Integer lyTicketType;


    private String viewListPrice;
    private String viewVipPrice;
    private String viewRetailPrice;

    private boolean isShow;
    private boolean hasGet;

    private Integer groupNumber;

    /** 循环图片 */
    @JsonProperty("img_arr")
    private List<String> imgArr;
    @JsonProperty("parent_id")
    private String parentId;

}
