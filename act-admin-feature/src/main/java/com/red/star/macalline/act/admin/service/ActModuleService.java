package com.red.star.macalline.act.admin.service;

import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.service.dto.ActModuleDTO;
import com.red.star.macalline.act.admin.service.dto.ActModuleQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;

/**
* @author AMGuo
* @date 2019-10-22
*/
//@CacheConfig(cacheNames = "tbWapActModule")
public interface ActModuleService {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(ActModuleQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<ActModuleDTO> queryAll(ActModuleQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ActModuleDTO findById(Integer id);

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ActModuleDTO create(ActModule resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ActModule resources);

    /**
     * 删除
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);
}