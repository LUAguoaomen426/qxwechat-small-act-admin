package com.red.star.macalline.act.admin.modules.system.service;

import com.red.star.macalline.act.admin.modules.system.service.dto.DictDetailDTO;
import com.red.star.macalline.act.admin.modules.system.service.dto.DictDetailQueryCriteria;
import com.red.star.macalline.act.admin.modules.system.domain.DictDetail;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@CacheConfig(cacheNames = "imp:act:admin:dictDetail")
public interface DictDetailService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DictDetailDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DictDetailDTO create(DictDetail resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(DictDetail resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    @Cacheable
    Map queryAll(DictDetailQueryCriteria criteria, Pageable pageable);
}