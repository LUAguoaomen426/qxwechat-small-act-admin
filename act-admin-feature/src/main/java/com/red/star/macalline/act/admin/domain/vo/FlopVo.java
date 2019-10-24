package com.red.star.macalline.act.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.entity.vo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-09-04 16:11
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlopVo {

    private String date;

    private Integer totalNum;

    private Integer peopleNum;

    private Integer luckyNum;
}
