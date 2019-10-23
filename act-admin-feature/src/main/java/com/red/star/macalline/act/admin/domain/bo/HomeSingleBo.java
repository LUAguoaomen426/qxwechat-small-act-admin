package com.red.star.macalline.act.admin.domain.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: macalline-work-server
 * @Package: com.red.star.macalline.data.entity.act.bo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-04-02 17:01
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeSingleBo {

    @JSONField(name = "is_yiulong")
    @JsonProperty("is_yiulong")
    private int is_yiulong;

    @JSONField(name = "sort")
    @JsonProperty("sort")
    private int sort;

    @JSONField(name = "promotion_ticket_id")
    @JsonProperty("promotion_ticket_id")
    private int promotionTicketId;

    @JSONField(name = "item_sub_title")
    @JsonProperty("item_sub_title")
    private String itemSubTitle;

    @JSONField(name = "group_id")
    @JsonProperty("group_id")
    private int groupId;

    @JSONField(name = "extend_value")
    @JsonProperty("extend_value")
    private String extendValue;

    @JSONField(name = "unit_code")
    @JsonProperty("unit_code")
    private String unitCode;

    @JSONField(name = "item_name")
    @JsonProperty("item_name")
    private String itemName;

    @JSONField(name = "vip_price")
    @JsonProperty("vip_price")
    private Integer vipPrice;

    @JSONField(name = "list_price")
    @JsonProperty("list_price")
    private Integer listPrice;

    @JSONField(name = "item_title")
    @JsonProperty("item_title")
    private String itemTitle;

    @JSONField(name = "item_brand")
    @JsonProperty("item_brand")
    private String itemBrand;

    @JSONField(name = "retail_price")
    @JsonProperty("retail_price")
    private Integer retailPrice;

}
