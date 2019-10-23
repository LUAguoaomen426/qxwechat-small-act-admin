package com.red.star.macalline.act.admin.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ProjectName: macalline-work-server
 * @Package: com.red.star.macalline.data.entity.bo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-02-13 13:40
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionTicketBo {

    private String unit_code;

    private Integer promotion_ticket_id;

    private String item_name;

    private String item_brand;

    private String item_title;

    private Double list_price;

    private Double vip_price;

    private Double retail_price;

    private String use_remark;

    private String[] img_arr;

    private String list_img;

    private Date get_begin_time;

    private Date get_end_time;

    private String use_begin_time_desc;

    private String use_end_time_desc;

    private String get_img;

    private String item_category;

    private TrackBo track;

    private String tel;

    private List<String> img_list;


}
