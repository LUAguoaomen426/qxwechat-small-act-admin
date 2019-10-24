package com.red.star.macalline.act.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.entity.bo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-09-04 15:52
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlopBo {

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
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
