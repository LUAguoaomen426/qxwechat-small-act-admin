package com.red.star.macalline.act.admin.service.impl;

import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.exception.EntityExistException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private MallMapper mallMapper;

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
    public void update(Mall resources) {
        Optional<Mall> optionalTbWapMall = mallRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalTbWapMall, "Mall", "id", resources.getId());
        Mall tbWapMall = optionalTbWapMall.get();
        Mall tbWapMall1 = null;
        tbWapMall1 = mallRepository.findByOmsCode(resources.getOmsCode());
        if (tbWapMall1 != null && !tbWapMall1.getId().equals(tbWapMall.getId())) {
            throw new EntityExistException(Mall.class, "oms_code", resources.getOmsCode());
        }
        tbWapMall1 = mallRepository.findByMallCode(resources.getMallCode());
        if (tbWapMall1 != null && !tbWapMall1.getId().equals(tbWapMall.getId())) {
            throw new EntityExistException(Mall.class, "mall_code", resources.getMallCode());
        }
        tbWapMall.copy(resources);
        mallRepository.save(tbWapMall);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        mallRepository.deleteById(id);
    }
}