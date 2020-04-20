package com.red.star.macalline.act.admin.service;

import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.ActSpecLink;
import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.domain.bo.SourcePvUvBo;
import com.red.star.macalline.act.admin.domain.vo.*;
import com.red.star.macalline.act.admin.service.dto.ActModuleDTO;
import com.red.star.macalline.act.admin.service.dto.ActModuleQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 对活动进行逻辑删除
     *
     * @param actCode
     * @return
     */
    ActResponse deleteAct(String actCode);

    /**
     * 判断当前actCode是否可用
     *
     * @param actCode
     * @return
     */
    String checkActCode(String actCode);

    /**
     * 重新启用活动
     *
     * @param actCode
     * @return
     */
    ActResponse enableAct(String actCode);

    /**
     * 根据上传文件批量更新对应的活动特殊链接某商场是否启用
     *
     * @param actCode
     * @param specCode
     * @param file
     * @return
     */
    ActResponse uploadActSpecLinkInfo(String actCode, String specCode, MultipartFile file);

    ActResponse findSpecLink(String actCode);

    ActResponse addSpecLink(String actCode, ActSpecLink actSpecLink);

    ActResponse saveSpecLink(String actCode, ActSpecLink actSpecLink);

    ActResponse deleteSpecLink(String actCode, ActSpecLink actSpecLink);

    ActResponse number(String source);

    void addGroupNumber(String source, Integer addGroupNumber);

    void changeTicketNumber(String source, ActExtraNumber actExtraNumber);

    List<Map> findGroupCountBySource(String source);

    List<Map<String, String>> findAllActNameAndSource();

    List<SourcePvUvVo> analysisPVUVData(@Valid SourcePvUvBo sourcePvUvBo);

    void clearSpecLink(String actCode, ActSpecLink actSpecLink);

    void addTopList(String source, @Valid TopListVo topListVo);

    List<GoodsVo> findGoodsByBillboard(String source, String billboardNo);


}