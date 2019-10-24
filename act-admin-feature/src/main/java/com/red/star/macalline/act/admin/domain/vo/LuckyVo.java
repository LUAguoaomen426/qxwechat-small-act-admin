package com.red.star.macalline.act.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.entity.vo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-09-04 16:23
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LuckyVo {

    private String province;

    private String city;

    private String mallName;

    private String omsCode;

    /**
     * 是否参与excel数量限制
     */
    private Boolean mallFlag;

    private String mobile;

    private String time;

    private Integer grade;

    private String gradeName;

    private String type;
}
