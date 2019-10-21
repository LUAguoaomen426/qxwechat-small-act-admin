package com.red.star.macalline.act.admin.repository;

import com.red.star.macalline.act.admin.domain.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
public interface EmailRepository extends JpaRepository<EmailConfig,Long> {
}
