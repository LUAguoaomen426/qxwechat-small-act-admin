package com.red.star.macalline.act.admin.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.entity.bo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-09-20 15:10
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourcePvUvBo {


    /**
     * 活动标识
     */
    @NotBlank
    private String source;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 是否实时查询
     */
    private boolean actualFlag;

    public SourcePvUvBo(String source) {
        this.source = source;
    }
}
