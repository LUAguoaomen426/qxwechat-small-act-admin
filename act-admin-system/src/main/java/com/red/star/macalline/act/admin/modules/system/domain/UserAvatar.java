package com.red.star.macalline.act.admin.modules.system.domain;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Zheng Jie
 * @date 2019年9月7日 16:16:59
 */
@Entity
@Table(name = "tb_act_admin_user_avatar")
@Data
@NoArgsConstructor
public class UserAvatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "real_name")
    private String realName;

    private String path;

    private String size;

    public UserAvatar(UserAvatar userAvatar, String realName, String path, String size) {
        this.id = ObjectUtil.isNotEmpty(userAvatar) ? userAvatar.getId() : null;
        this.realName = realName;
        this.path = path;
        this.size = size;
    }
}
