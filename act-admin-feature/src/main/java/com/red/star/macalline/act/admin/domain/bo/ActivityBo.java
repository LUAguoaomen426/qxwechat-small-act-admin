package com.red.star.macalline.act.admin.domain.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @ProjectName: macalline-work-server
 * @Package: com.red.star.macalline.data.entity.bo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2018-11-27 16:56
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityBo {

    @JsonProperty("group_5")
    private int group5;
    @JsonProperty("group_4")
    private int group4;
    @JsonProperty("award_ticket_id_2")
    private int awardTicketId2;
    @JsonProperty("award_ticket_id_1")
    private int awardTicketId1;
    @JsonProperty("group_6")
    private int group6;
    @JsonProperty("group_1")
    private int group1;
    @JsonProperty("group_5_ticket_id")
    private int group5TicketId = 0;
    @JsonProperty("group_3")
    private int group3;
    @JsonProperty("group_2")
    private int group2;
    @JsonProperty("city_name")
    private String cityName;
    @JsonProperty("state_name")
    private String stateName;
    @JsonProperty("unit_code")
    private String unitCode;
    @JsonProperty("group_2_ticket_id")
    private int group2TicketId = 0;
    @JsonProperty("unit_id")
    private String unitId;
    @JsonProperty("group_4_ticket_id")
    private int group4TicketId = 0;
    @JsonProperty("group_6_ticket_id")
    private int group6TicketId = 0;
    @JsonProperty("ex_ticket_id1")
    private int exTicketId1;
    @JsonProperty("province_name")
    private String provinceName;
    @JsonProperty("unit_name")
    private String unitName;
    private List<SingleBo> single;
    @JsonProperty("award_ticket_id_4")
    private int awardTicketId4;
    @JsonProperty("award_ticket_id_3")
    private int awardTicketId3;
    @JsonProperty("package_ticket_id")
    private int packageTicketId;
    @JsonProperty("group_1_ticket_id")
    private int group1TicketId = 0;
    @JsonProperty("group_3_ticket_id")
    private int group3TicketId = 0;

    public void convert() {
        if (ObjectUtils.isEmpty(this.packageTicketId)) {
            this.packageTicketId = -1;
        }
    }
}
