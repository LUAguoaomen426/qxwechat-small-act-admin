package com.red.star.macalline.act.admin.repository;

import com.red.star.macalline.act.admin.domain.ActReportBtnDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
public interface ActReportBtnDailyRepository extends JpaRepository<ActReportBtnDaily, Integer>, JpaSpecificationExecutor {

}