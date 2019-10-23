package com.red.star.macalline.act.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.entity
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-08-21 13:31
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoostAwardDTO {

    /**
     * 券id
     * 商场券为-1
     * 实物奖品为-2
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 需助力数
     */
    private Integer boostNum;

    /**
     * 商场、集团
     */
    private String type;

    /**
     * 券详情
     */
    private TicketInfoDTO ticketInfoDTO;

    public BoostAwardDTO(Integer id, String name, Integer quantity, Integer boostNum, String type) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.boostNum = boostNum;
        this.type = type;
    }
}
