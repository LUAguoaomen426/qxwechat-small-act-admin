package com.red.star.macalline.act.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.entity.vo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-09-20 15:31
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourcePvUvVo {

    private String date;

    private Integer pv;

    private Integer uv;
}
