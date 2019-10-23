package com.red.star.macalline.act.admin.service.impl;

import com.red.star.macalline.act.admin.constant.CacheConstant;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.exception.EntityExistException;
import com.red.star.macalline.act.admin.mapper.ActModuleMybatisMapper;
import com.red.star.macalline.act.admin.mapper.MallMybatisMapper;
import com.red.star.macalline.act.admin.repository.MallRepository;
import com.red.star.macalline.act.admin.service.MallService;
import com.red.star.macalline.act.admin.service.dto.MallDTO;
import com.red.star.macalline.act.admin.service.dto.MallQueryCriteria;
import com.red.star.macalline.act.admin.service.mapper.MallMapper;
import com.red.star.macalline.act.admin.utils.PageUtil;
import com.red.star.macalline.act.admin.utils.QueryHelp;
import com.red.star.macalline.act.admin.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MallServiceImpl implements MallService {

    @Autowired
    private MallRepository mallRepository;

    @Resource
    private MallMapper mallMapper;

    @Resource
    private MallMybatisMapper mallMybatisMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ActModuleMybatisMapper actModuleMybatisMapper;

    @Override
    public Map<String, Object> queryAll(MallQueryCriteria criteria, Pageable pageable) {
        Page<Mall> page = mallRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(mallMapper::toDto));
    }

    @Override
    public List<MallDTO> queryAll(MallQueryCriteria criteria) {
        return mallMapper.toDto(mallRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public MallDTO findById(Integer id) {
        Optional<Mall> tbWapMall = mallRepository.findById(id);
        ValidationUtil.isNull(tbWapMall, "Mall", "id", id);
        return mallMapper.toDto(tbWapMall.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MallDTO create(Mall resources) {
        if (mallRepository.findByOmsCode(resources.getOmsCode()) != null) {
            throw new EntityExistException(Mall.class, "oms_code", resources.getOmsCode());
        }
        if (mallRepository.findByMallCode(resources.getMallCode()) != null) {
            throw new EntityExistException(Mall.class, "mall_code", resources.getMallCode());
        }
        return mallMapper.toDto(mallRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<Mall> resources) {
        if (ObjectUtils.isEmpty(resources)) {
            return;
        }
        mallMybatisMapper.updateWapMall(resources);
        redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_LIST_ENTRANCE);
        for (Mall mall : resources) {
            redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_CODE + mall.getMallCode());
            redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_OMS_CODE + mall.getOmsCode());
        }
        //清除大促缓存
        for (ActModule actModule :
                actModuleMybatisMapper.listEnableAct()) {
            redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_LIST_HOME + actModule.getActCode());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        mallRepository.deleteById(id);
    }

}