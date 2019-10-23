package com.red.star.macalline.act.admin.repository;

import com.red.star.macalline.act.admin.domain.Mall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
public interface MallRepository extends JpaRepository<Mall, Integer>, JpaSpecificationExecutor {

    /**
     * findByOmsCode
     *
     * @param oms_code
     * @return
     */
    Mall findByOmsCode(String oms_code);

    /**
     * findByMallCode
     *
     * @param mall_code
     * @return
     */
    Mall findByMallCode(String mall_code);

}