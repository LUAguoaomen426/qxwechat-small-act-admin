package com.red.star.macalline.act.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.red.star.macalline.act.admin.constant.CacheConstant;
import com.red.star.macalline.act.admin.constant.RabbitConstant;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.ActSpecLink;
import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.exception.EntityExistException;
import com.red.star.macalline.act.admin.mapper.ActModuleMybatisMapper;
import com.red.star.macalline.act.admin.mapper.ActSpecLinkMybatisMapper;
import com.red.star.macalline.act.admin.mapper.MallMybatisMapper;
import com.red.star.macalline.act.admin.rabbitmq.RabbitForwardService;
import com.red.star.macalline.act.admin.repository.ActModuleRepository;
import com.red.star.macalline.act.admin.service.ActModuleService;
import com.red.star.macalline.act.admin.service.MallService;
import com.red.star.macalline.act.admin.service.dto.ActModuleDTO;
import com.red.star.macalline.act.admin.service.dto.ActModuleQueryCriteria;
import com.red.star.macalline.act.admin.service.mapper.ActModuleMapper;
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
import java.util.*;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ActModuleServiceImpl implements ActModuleService {

    @Autowired
    private ActModuleRepository actModuleRepository;

    @Autowired
    private ActModuleMapper actModuleMapper;

    @Resource
    private MallService mallService;

    @Resource
    private ActModuleMybatisMapper actModuleMybatisMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MallMybatisMapper mallMybatisMapper;

    @Resource
    private RabbitForwardService rabbitForwardService;

    @Resource
    private ActSpecLinkMybatisMapper actSpecLinkMybatisMapper;

    @Override
    public Map<String, Object> queryAll(ActModuleQueryCriteria criteria, Pageable pageable) {
        Page<ActModule> page = actModuleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(actModuleMapper::toDto));
    }

    @Override
    public List<ActModuleDTO> queryAll(ActModuleQueryCriteria criteria) {
        return actModuleMapper.toDto(actModuleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public ActModuleDTO findById(Integer id) {
        Optional<ActModule> tbWapActModule = actModuleRepository.findById(id);
        ValidationUtil.isNull(tbWapActModule, "ActModule", "id", id);
        return actModuleMapper.toDto(tbWapActModule.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActModuleDTO create(ActModule resources) {
        if (actModuleRepository.findByActCode(resources.getActCode()) != null) {
            throw new EntityExistException(ActModule.class, "act_code", resources.getActCode());
        }
        return actModuleMapper.toDto(actModuleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ActModule resources) {
        Optional<ActModule> optionalTbWapActModule = actModuleRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalTbWapActModule, "ActModule", "id", resources.getId());
        ActModule actModule = optionalTbWapActModule.get();
        ActModule actModule1 = null;
        actModule1 = actModuleRepository.findByActCode(resources.getActCode());
        if (actModule1 != null && !actModule1.getId().equals(actModule.getId())) {
            throw new EntityExistException(ActModule.class, "act_code", resources.getActCode());
        }
        actModule.copy(resources);
        actModuleRepository.save(actModule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        actModuleRepository.deleteById(id);
    }

    @Override
    public ActResponse saveActInfo(ActModule actInfo) {
        if (ObjectUtils.isEmpty(actInfo)) {
            return ActResponse.buildErrorResponse("参数有误");
        }
        String res = mallService.checkActCode(actInfo.getActCode());
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        ActModule queryCondition = new ActModule();
        queryCondition.setActCode(actInfo.getActCode());
        ActModule oldActModule = actModuleMybatisMapper.selectOne(new QueryWrapper<ActModule>().eq("actCode", queryCondition.getActCode()));
        if (actInfo.getModuleType() == 0) {
            actInfo.setLinkUrl(null);
            actInfo.setShowImage(null);
        }
        actInfo.setUpdateTime(new Date());
        actInfo.setShowImage(removeImageWatermark(actInfo.getShowImage()));

        actModuleMybatisMapper.update(actInfo, new UpdateWrapper<ActModule>()
                .eq("actCode", actInfo.getActCode()));


        //刷新缓存
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actInfo.getActCode() + CacheConstant.CACHE_KEY_HOME_LINK);
        redisTemplate.delete(CacheConstant.CACHE_KEY_ACT_LIST);
        if (actInfo.getSubType().equals(1)) {
            redisTemplate.delete(CacheConstant.CACHE_KEY_ACT_MODULE + actInfo.getActCode());
            redisTemplate.delete(CacheConstant.CACHE_KEY_ACT_MODULE_POSTER_ID + actInfo.getPosterId());
            redisTemplate.delete(CacheConstant.CACHE_KEY_ACT_MODULE_POSTER_ID + oldActModule.getPosterId());
        }

        //发送MQ通知实例刷新缓存
        rabbitForwardService.sendMsgToFanoutExchange(RabbitConstant.FANOUT_ACT_REFRESH, actInfo.getActCode());
        return ActResponse.buildSuccessResponse("SUCCESS");
    }

    /**
     * 添加!用于移除上传图片中的水印
     */
    public String removeImageWatermark(String imageUrl) {
        if (!ObjectUtils.isEmpty(imageUrl) && imageUrl.endsWith("!")) {
            return imageUrl;
        }
        if (!ObjectUtils.isEmpty(imageUrl) && !imageUrl.endsWith("!")) {
            return imageUrl + "!";
        }

        return null;
    }

    /**
     * 查询所有活动信息
     *
     * @return
     */
    @Override
    public List<ActModule> findActInfo() {
        List<ActModule> actModules = actModuleMybatisMapper.selectList(new QueryWrapper<ActModule>().orderByAsc("is_delete", "order_level"));
        return actModules;
    }

    /**
     * 新建活动信息
     *
     * @param actInfo
     */
    @Override
    @Transactional(readOnly = false)
    public ActResponse addActInfo(ActModule actInfo) {
        //数据合法性检测
        if (ObjectUtils.isEmpty(actInfo)) {
            return ActResponse.buildErrorResponse("提交参数有误");
        }
        ActModule selectInfo = new ActModule();
        selectInfo.setActCode(actInfo.getActCode());
        if (actModuleMybatisMapper.selectCount(new QueryWrapper<ActModule>().eq("act_code", actInfo.getActCode())) > 0) {
            //活动actCode已经存在
            return ActResponse.buildErrorResponse("actCode已存在");
        }
        if (actInfo.getModuleType() != 0) {
            //当前类型不为0
            if (ObjectUtils.isEmpty(actInfo.getShowImage()) || ObjectUtils.isEmpty(actInfo.getLinkUrl())) {
                return ActResponse.buildErrorResponse("图片链接或活动链接必填");
            }
        }
        Integer maxLevel = actModuleMybatisMapper.findMaxLevel();
        if (ObjectUtils.isEmpty(maxLevel)) {
            maxLevel = 0;
        }
        Date now = new Date();
        actInfo.setCreateTime(now);
        actInfo.setUpdateTime(now);
        actInfo.setOrderLevel(maxLevel + 1);
        actInfo.setIsDelete(false);

        if (actInfo.getModuleType() == 0) {
            actInfo.setShowImage(null);
            actInfo.setLinkUrl(null);
        }
        //上传图片地址去水印
        actInfo.setShowImage(removeImageWatermark(actInfo.getShowImage()));

        this.actModuleMybatisMapper.insert(actInfo);
        //初始化商场数据
        List<Mall> defultInfo = mallMybatisMapper.findMallDefultInfo();
        mallMybatisMapper.insertActMallMerge(actInfo.getActCode(), defultInfo);

        //刷新缓存
        redisTemplate.delete(CacheConstant.CACHE_KEY_ACT_LIST);
        return ActResponse.buildSuccessResponse("actInfo", actInfo);
    }

    /**
     * 活动级别改变
     *
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public ActResponse changActInfoLeveL(Boolean isDown, String actCode) {
        String res = mallService.checkActCode(actCode);
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        ActModule selectInfo = new ActModule();
        selectInfo.setActCode(actCode);
        ActModule changeInfo = actModuleMybatisMapper.selectOne(new QueryWrapper<ActModule>().eq("act_code", actCode));
        Integer originalLevel = changeInfo.getOrderLevel();
        ActModule beChangeInfo = actModuleMybatisMapper.findNextLevelInfo(originalLevel, isDown);
        if (ObjectUtils.isEmpty(beChangeInfo)) {
            return ActResponse.buildErrorResponse("活动已经置顶或置底");
        }
        changeInfo.setOrderLevel(beChangeInfo.getOrderLevel());
        changeInfo.setUpdateTime(new Date());
        beChangeInfo.setOrderLevel(originalLevel);
        beChangeInfo.setUpdateTime(new Date());
        actModuleMybatisMapper.updateById(changeInfo);
        actModuleMybatisMapper.updateById(beChangeInfo);
        //刷新缓存
        redisTemplate.delete(CacheConstant.CACHE_KEY_ACT_LIST);
        return ActResponse.buildSuccessResponse();
    }

    /**
     * 通过活动信息查询当前活动下的所有特殊链接信息
     *
     * @param actCode
     * @return
     */
    @Override
    public ActResponse findSpecLink(String actCode) {
        String res = mallService.checkActCode(actCode);
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        List<ActSpecLink> actSpecLinks = actSpecLinkMybatisMapper.listFindSpecLinkByActCode(actCode);
        actSpecLinks.forEach(actSpecLink -> actSpecLink.setHaveTL(ObjectUtils.isEmpty(actSpecLink.getTimeLimit()) ? "F" : "T"));
        Collections.sort(actSpecLinks, new Comparator<ActSpecLink>() {
            @Override
            public int compare(ActSpecLink o1, ActSpecLink o2) {
                if (null == o1.getSort()) {
                    return 1;
                }
                if (null == o2.getSort()) {
                    return -1;
                }
                if (o1.getSort() > o2.getSort()) {
                    return 1;
                }
                if (o1.getSort().equals(o2.getSort())) {
                    return 0;
                }
                return -1;
            }
        });

        return ActResponse.buildSuccessResponse("specLinks", actSpecLinks);
    }

    /**
     * 添加特殊链接信息
     *
     * @param actCode
     * @param actSpecLink
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public ActResponse addSpecLink(String actCode, ActSpecLink actSpecLink) {
        String res = mallService.checkActCode(actCode);
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        if (ObjectUtils.isEmpty(actSpecLink)) {
            return ActResponse.buildErrorResponse("参数有误");
        }

        Date now = new Date();
        actSpecLink.setCreateTime(now);
        actSpecLink.setUpdateTime(now);

        Integer maxSort = actSpecLinkMybatisMapper.selectMaxSort("");
        maxSort = ObjectUtils.isEmpty(maxSort) ? 0 : maxSort;
        actSpecLink.setSort(maxSort + 1);
        if (actSpecLink.getType().equals(0)) {
            actSpecLink.setShowImage(removeImageWatermark(actSpecLink.getShowImage()));
        } else {
            //特殊链接不需要图片
            actSpecLink.setShowImage(null);
        }
        if (!"T".equals(actSpecLink.getHaveTL())) {
            //不需要时间限制
            actSpecLink.setTimeLimit(null);
        }

        int i = actSpecLinkMybatisMapper.insert(actSpecLink);
        //更新关联的act_mall_sepc__merge表
        //查询出所有商场信息
        List<Mall> malls = mallMybatisMapper.selectList(null);
        malls.stream().forEach(m -> m.setLinkShow(false));
        actSpecLinkMybatisMapper.insertSpecLinkMergeByList(actCode, actSpecLink.getSpecCode(), malls);

        //清理缓存
        for (Mall m : mallMybatisMapper.selectList(null))
            redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK + m.getOmsCode());
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK_ALLOWED_MALL);
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_DEFAULT_MALL + actSpecLink.getSpecCode());

        return ActResponse.buildSuccessResponse("SUCCESS");
    }

    /**
     * 特殊链接修改
     *
     * @param actCode
     * @param actSpecLink
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public ActResponse saveSpecLink(String actCode, ActSpecLink actSpecLink) {

        String res = mallService.checkActCode(actCode);
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        if (ObjectUtils.isEmpty(actSpecLink)) {
            return ActResponse.buildErrorResponse("参数有误");
        }
        actSpecLink.setShowImage(removeImageWatermark(actSpecLink.getShowImage()));
        ActSpecLink oldInfo = actSpecLinkMybatisMapper.selectOne(new QueryWrapper<ActSpecLink>().eq("spec_code", actSpecLink.getSpecCode()));

        if (actSpecLink.getType().equals(1)) {
            //当前为特殊链接，不存储图片
            actSpecLink.setShowImage(null);
        }
        if (!"T".equals(actSpecLink.getHaveTL())) {
            actSpecLink.setTimeLimit(null);
        }
        if (actSpecLink.getMallList().size() > 0) {
            actSpecLinkMybatisMapper.updateSpecLinkMergerByList(actCode, actSpecLink.getSpecCode(), actSpecLink.getMallList());
        }
        if ((!ObjectUtils.isEmpty(actSpecLink.getTimeLimit()) && !actSpecLink.getTimeLimit().equals(oldInfo.getTimeLimit())) || !oldInfo.getName().equals(actSpecLink.getName()) || !oldInfo.getUrl().equals(actSpecLink.getUrl()) || (!ObjectUtils.isEmpty(actSpecLink.getShowImage()) && !actSpecLink.getShowImage().equals(oldInfo.getShowImage()))) {
            //修改了名称或者url，需要清除所有缓存
            actSpecLinkMybatisMapper.update(actSpecLink, new UpdateWrapper<ActSpecLink>().eq("spec_code",actSpecLink.getSpecCode()));

            for (Mall m : mallMybatisMapper.selectList(null))
                redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK + m.getOmsCode());
            redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK_ALLOWED_MALL);
            redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_DEFAULT_MALL + actSpecLink.getSpecCode());
            return ActResponse.buildSuccessResponse("SUCCESS");
        }
        //清除部分修改的缓存信息
        for (Mall m : actSpecLink.getMallList()) {
            redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK + m.getOmsCode());
        }
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK_ALLOWED_MALL);
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_DEFAULT_MALL + actSpecLink.getSpecCode());
        return ActResponse.buildSuccessResponse("SUCCESS");
    }

    /**
     * 特殊链接删除
     *
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public ActResponse deleteSpecLink(String actCode, ActSpecLink actSpecLink) {
        String res = mallService.checkActCode(actCode);
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        if (ObjectUtils.isEmpty(actSpecLink)) {
            return ActResponse.buildErrorResponse("参数有误");
        }
        actSpecLinkMybatisMapper.delete(new QueryWrapper<ActSpecLink>().eq("spec_code",actSpecLink.getSpecCode()));
        actSpecLinkMybatisMapper.deleteSpecLinkMergerByList(actCode, actSpecLink.getSpecCode());

        for (Mall m : mallMybatisMapper.selectList(null))
            redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK + m.getOmsCode());
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK_ALLOWED_MALL);
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_DEFAULT_MALL + actSpecLink.getSpecCode());
        return ActResponse.buildSuccessResponse();
    }

}