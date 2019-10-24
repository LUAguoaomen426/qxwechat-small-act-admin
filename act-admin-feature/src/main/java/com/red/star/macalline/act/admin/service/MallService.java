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

    /**
     * 通过活动code查询此活动下的所有商场信息
     *
     * @param actCode
     * @return
     */
    ActResponse findMallByActCode(String actCode);

    /**
     * 活动下商场信息修改保存
     *
     * @param actCode
     * @param mallList
     * @return
     */
    ActResponse saveMallInfo(String actCode, List<Mall> mallList);

    /**
     * 修改活动默认商场
     *
     * @param omsCode
     * @return
     */
    ActResponse changeDefultMall(String omsCode);

    void syncMallInfo();


    ActResponse uploadMallinfo(String actCode, MultipartFile file);
}