package com.red.star.macalline.act.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.entity.vo
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-09-16 17:09
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LuckyData {

    private List<LuckyVo> list;

    private List gradeMap;

}
