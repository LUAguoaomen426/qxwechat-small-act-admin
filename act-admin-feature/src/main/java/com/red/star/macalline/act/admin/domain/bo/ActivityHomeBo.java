package com.red.star.macalline.act.admin.domain.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * @ProjectName: macalline-work-server
 * @Package: com.red.star.macalline.data.entity.bo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2018-11-28 18:15
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityHomeBo {

    @JsonProperty("package_ticket_id")
    private int packageTicketId;

    @JsonProperty("single_1")
    private String single1;

    @JsonProperty("single_2")
    private String single2;

    @JsonProperty("single_3")
    private String single3;

    @JsonProperty("single_4")
    private String single4;

    @JsonProperty("single_5")
    private String single5;

    @JsonProperty("single_6")
    private String single6;

    @JsonProperty("single_7")
    private String single7;

    @JsonProperty("group_1_ticket_id")
    private Integer group1TicketId = 0;

    @JsonProperty("group_2_ticket_id")
    private Integer group2TicketId = 0;

    @JsonProperty("group_3_ticket_id")
    private Integer group3TicketId = 0;

    @JsonProperty("group_4_ticket_id")
    private Integer group4TicketId = 0;

    @JsonProperty("group_5_ticket_id")
    private Integer group5TicketId = 0;

    @JsonProperty("group_6_ticket_id")
    private Integer group6TicketId = 0;

    @JsonProperty("group_7_ticket_id")
    private Integer group7TicketId = 0;

    @JsonProperty("group_8_ticket_id")
    private Integer group8TicketId = 0;

    @JsonProperty("group_1")
    private int group1 = 0;

    @JsonProperty("group_2")
    private int group2 = 0;

    @JsonProperty("group_3")
    private int group3 = 0;

    @JsonProperty("group_4")
    private int group4 = 0;

    @JsonProperty("group_5")
    private int group5 = 0;

    @JsonProperty("group_6")
    private int group6 = 0;

    @JsonProperty("group_7")
    private int group7 = 0;

    @JsonProperty("group_8")
    private int group8 = 0;

    public void convert() {
        if (ObjectUtils.isEmpty(this.packageTicketId)) {
            this.packageTicketId = -1;
        }
        if (ObjectUtils.isEmpty(this.group1)) {
            this.group1TicketId = null;
        } else {
            if (ObjectUtils.isEmpty(this.group1TicketId)) {
                this.group1TicketId = -1;
            }
        }
        if (ObjectUtils.isEmpty(this.group2)) {
            this.group2TicketId = null;
        } else {
            if (ObjectUtils.isEmpty(this.group2TicketId)) {
                this.group2TicketId = -1;
            }
        }
        if (ObjectUtils.isEmpty(this.group3)) {
            this.group3TicketId = null;
        } else {
            if (ObjectUtils.isEmpty(this.group3TicketId)) {
                this.group3TicketId = -1;
            }
        }
        if (ObjectUtils.isEmpty(this.group4)) {
            this.group4TicketId = null;
        } else {
            if (ObjectUtils.isEmpty(this.group4TicketId)) {
                this.group4TicketId = -1;
            }
        }
        if (ObjectUtils.isEmpty(this.group5)) {
            this.group5TicketId = null;
        } else {
            if (ObjectUtils.isEmpty(this.group5TicketId)) {
                this.group5TicketId = -1;
            }
        }
        if (ObjectUtils.isEmpty(this.group6)) {
            this.group6TicketId = null;
        } else {
            if (ObjectUtils.isEmpty(this.group6TicketId)) {
                this.group6TicketId = -1;
            }
        }
        if (ObjectUtils.isEmpty(this.group7)) {
            this.group7TicketId = null;
        } else {
            if (ObjectUtils.isEmpty(this.group7TicketId)) {
                this.group7TicketId = -1;
            }
        }
        if (ObjectUtils.isEmpty(this.group8)) {
            this.group8TicketId = null;
        } else {
            if (ObjectUtils.isEmpty(this.group8TicketId)) {
                this.group8TicketId = -1;
            }
        }
    }
}
