package com.red.star.macalline.act.admin.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.entity.bo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-09-04 16:16
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuckyBo {


    /**
     * lucky
     */
    @NotBlank
    private String type;

    /**
     * 活动标识
     */
    @NotBlank
    private String source;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 商场code
     */
    private String omsCode;

    /**
     * 商场名称
     */
    private String mallName;

    private Boolean mallFlag;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 奖品级别
     */
    private Integer grade;
}
