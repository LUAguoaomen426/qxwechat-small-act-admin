package com.red.star.macalline.act.admin.repository;

import com.red.star.macalline.act.admin.domain.ActModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author AMGuo
* @date 2019-10-22
*/
public interface ActModuleRepository extends JpaRepository<ActModule, Integer>, JpaSpecificationExecutor {

    /**
     * findByActCode
     * @param act_code
     * @return
     */
    ActModule findByActCode(String act_code);
}