package com.red.star.macalline.act.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.service.dto.MallDTO;
import com.red.star.macalline.act.admin.service.dto.MallQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
public interface MallService extends IService<Mall> {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Map<String, Object> queryAll(MallQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    List<MallDTO> queryAll(MallQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    MallDTO findById(Integer id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    MallDTO create(Mall resources);

    /**
     * 编辑
     *
     * @param resources
     */
    void update(List<Mall> resources);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Integer id);

    void syncMallInfo();

    ActResponse uploadMallinfo(String actCode, MultipartFile file);
}