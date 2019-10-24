package com.red.star.macalline.act.admin.service;

import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.ActSpecLink;
import com.red.star.macalline.act.admin.domain.bo.SourcePvUvBo;
import com.red.star.macalline.act.admin.domain.vo.ActExtraNumber;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.domain.vo.SourcePvUvVo;
import com.red.star.macalline.act.admin.service.dto.ActModuleDTO;
import com.red.star.macalline.act.admin.service.dto.ActModuleQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
//@CacheConfig(cacheNames = "tbWapActModule")
public interface ActModuleService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(ActModuleQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<ActModuleDTO> queryAll(ActModuleQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ActModuleDTO findById(Integer id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ActModuleDTO create(ActModule resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ActModule resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);

    ActResponse addActInfo(ActModule actInfo);

    List<ActModule> findActInfo();

    /**
     * 活动信息保存
     *
     * @param actInfo
     * @return
     */
    ActResponse saveActInfo(ActModule actInfo);

    ActResponse changActInfoLeveL(Boolean isDown, String actCode);

    ActResponse findSpecLink(String actCode);

    ActResponse addSpecLink(String actCode, ActSpecLink actSpecLink);

    ActResponse saveSpecLink(String actCode, ActSpecLink actSpecLink);

    ActResponse deleteSpecLink(String actCode, ActSpecLink actSpecLink);

    ActResponse number(String source);

    void addGroupNumber(String source, Integer addGroupNumber);

    void changeTicketNumber(String source, ActExtraNumber actExtraNumber);

    List<Map> findGroupCountBySource(String source);

    List<Map<String,String>> findAllActNameAndSource();

    List<SourcePvUvVo> analysisPVUVData(@Valid SourcePvUvBo sourcePvUvBo);
}