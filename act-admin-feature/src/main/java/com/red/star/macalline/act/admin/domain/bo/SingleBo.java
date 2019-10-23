package com.red.star.macalline.act.admin.domain.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: macalline-work-server
 * @Package: com.red.star.macalline.data.entity.bo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2018-11-27 16:39
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleBo {

    @JsonProperty("promotion_ticket_id")
    private int promotionTicketId;
    @JsonProperty("item_sub_title")
    private String itemSubTitle;
    @JsonProperty("group_id")
    private int groupId;
    @JsonProperty("extend_value")
    private String extendValue;
    @JsonProperty("unit_code")
    private String unitCode;
    @JsonProperty("item_name")
    private String itemName;
    @JsonProperty("vip_price")
    private Integer vipPrice;
    @JsonProperty("list_price")
    private Integer listPrice;
    @JsonProperty("item_title")
    private String itemTitle;
    @JsonProperty("item_brand")
    private String itemBrand;
    @JsonProperty("retail_price")
    private Integer retailPrice;
}
