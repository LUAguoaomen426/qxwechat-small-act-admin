package com.red.star.macalline.act.admin.modules.system.service;

import com.red.star.macalline.act.admin.modules.system.service.dto.JobDTO;
import com.red.star.macalline.act.admin.modules.system.service.dto.JobQueryCriteria;
import com.red.star.macalline.act.admin.modules.system.domain.Job;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@CacheConfig(cacheNames = "imp:act:admin:job")
public interface JobService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    JobDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    JobDTO create(Job resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Job resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * queryAll
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAll(JobQueryCriteria criteria, Pageable pageable);
}