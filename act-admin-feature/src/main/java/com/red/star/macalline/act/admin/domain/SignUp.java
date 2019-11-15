package com.red.star.macalline.act.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description tb_wap_sign_up 表实体类
 * @Date 2019/11/14 14:03
 * @Created by Akari
 */
@Data
@TableName("tb_wap_sign_up")
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {

    private Integer id;

    private String name;
    private String mobile;
    private String omsCode;
    private String Type;
    private Date createTime;
    private Date updateTime;
    private String source;
    private String subChannel;
    private String pageUrl;
    private Byte cliType;
    private String fromOpenId;
    private String fromUnionId;
    private String scene;

    @TableField(exist = false)
    private String mallName;
}
