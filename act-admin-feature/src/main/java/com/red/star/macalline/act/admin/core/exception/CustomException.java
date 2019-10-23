package com.red.star.macalline.act.admin.core.exception;

import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.core.exception
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-08-21 15:09
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private ActResponse actResponse;


}
