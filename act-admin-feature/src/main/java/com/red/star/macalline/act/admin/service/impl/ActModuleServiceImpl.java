package com.red.star.macalline.act.admin.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.red.star.macalline.act.admin.constant.CacheConstant;
import com.red.star.macalline.act.admin.constant.RabbitConstant;
import com.red.star.macalline.act.admin.constant.RedisConstant;
import com.red.star.macalline.act.admin.core.act.Act;
import com.red.star.macalline.act.admin.core.act.ActFactory;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.ActSpecLink;
import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.domain.bo.SourcePvUvBo;
import com.red.star.macalline.act.admin.domain.vo.ActExtraNumber;
import com.red.star.macalline.act.admin.domain.vo.ActGroupTicketV2;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.domain.vo.SourcePvUvVo;
import com.red.star.macalline.act.admin.exception.EntityExistException;
import com.red.star.macalline.act.admin.mapper.ActModuleMybatisMapper;
import com.red.star.macalline.act.admin.mapper.ActSpecLinkMybatisMapper;
import com.red.star.macalline.act.admin.mapper.ComMybatisMapper;
import com.red.star.macalline.act.admin.mapper.MallMybatisMapper;
import com.red.star.macalline.act.admin.rabbitmq.RabbitForwardService;
import com.red.star.macalline.act.admin.repository.ActModuleRepository;
import com.red.star.macalline.act.admin.service.ActModuleService;
import com.red.star.macalline.act.admin.service.ComService;
import com.red.star.macalline.act.admin.service.dto.ActModuleDTO;
import com.red.star.macalline.act.admin.service.dto.ActModuleQueryCriteria;
import com.red.star.macalline.act.admin.service.mapper.ActModuleMapper;
import com.red.star.macalline.act.admin.util.DateNewUtil;
import com.red.star.macalline.act.admin.util.JsonUtil;
import com.red.star.macalline.act.admin.utils.PageUtil;
import com.red.star.macalline.act.admin.utils.QueryHelp;
import com.red.star.macalline.act.admin.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ActModuleServiceImpl implements ActModuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActModuleService.class);

    @Autowired
    private ActModuleRepository actModuleRepository;

    @Resource
    private ActModuleMapper actModuleMapper;

    @Resource
    private ActModuleMybatisMapper actModuleMybatisMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private MallMybatisMapper mallMybatisMapper;

    @Resource
    private RabbitForwardService rabbitForwardService;

    @Resource
    private ActSpecLinkMybatisMapper actSpecLinkMybatisMapper;

    @Resource
    private ComMybatisMapper comMybatisMapper;

    @Resource
    private ComService comService;

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
        String res = checkActCode(actInfo.getActCode());
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        ActModule queryCondition = new ActModule();
        queryCondition.setActCode(actInfo.getActCode());
        ActModule oldActModule = actModuleMybatisMapper.selectOne(new QueryWrapper<ActModule>().lambda().eq(ActModule::getActCode, queryCondition.getActCode()));
        if (actInfo.getModuleType() == 0) {
            actInfo.setLinkUrl(null);
            actInfo.setShowImage(null);
        }
        actInfo.setUpdateTime(new Date());
        actInfo.setShowImage(removeImageWatermark(actInfo.getShowImage()));

        actModuleMybatisMapper.update(actInfo, new UpdateWrapper<ActModule>()
                .eq("act_code", actInfo.getActCode()));


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
    @Transactional(rollbackFor = Exception.class)
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
        actInfo.setProgramConfig(ObjectUtils.isEmpty(actInfo.getProgramConfig()) ? null : actInfo.getProgramConfig());
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
    @Transactional(rollbackFor = Exception.class)
    public ActResponse changActInfoLeveL(Boolean isDown, String actCode) {
        String res = checkActCode(actCode);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActResponse deleteAct(String actCode) {
        String res = checkActCode(actCode);
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        ActModule selectInfo = new ActModule();
        selectInfo.setActCode(actCode);
        ActModule actModule = actModuleMybatisMapper.selectOne(new QueryWrapper<ActModule>().lambda().eq(ActModule::getActCode, selectInfo.getActCode()));
        if (actModule.getIsDelete()) {
            return ActResponse.buildErrorResponse("此活动已下架");
        }
        actModule.setOrderLevel(-1);
        actModule.setIsDelete(true);
        actModule.setUpdateTime(new Date());
        actModuleMybatisMapper.update(actModule, new UpdateWrapper<ActModule>().eq("id", actModule.getId()));
        //刷新缓存
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_HOME_LINK);
        redisTemplate.delete(CacheConstant.CACHE_KEY_ACT_LIST);
        return ActResponse.buildSuccessResponse();
    }

    /**
     * 判断当前actCode是否可用
     *
     * @param actCode
     * @return
     */
    @Override
    public String checkActCode(String actCode) {
        if (ObjectUtils.isEmpty(actCode)) {
            return "参数有误";
        }
        ActModule actModule = new ActModule();
        actModule.setActCode(actCode);
        if (actModuleMybatisMapper.selectCount(new QueryWrapper<ActModule>().lambda().eq(ActModule::getActCode, actModule.getActCode())) != 1) {
            return "actCode不存在";
        }
        return "SUCCESS";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActResponse enableAct(String actCode) {
        String res = checkActCode(actCode);
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        ActModule selectInfo = new ActModule();
        selectInfo.setActCode(actCode);
        ActModule actModule = actModuleMybatisMapper.selectOne(new QueryWrapper<ActModule>().lambda().eq(ActModule::getActCode, selectInfo.getActCode()));
        if (!actModule.getIsDelete()) {
            return ActResponse.buildErrorResponse("此活动未下架");
        }
        Integer maxLevel = actModuleMybatisMapper.findMaxLevel();
        actModule.setIsDelete(false);
        actModule.setOrderLevel(maxLevel + 1);
        actModule.setUpdateTime(new Date());
        actModuleMybatisMapper.update(actModule, new UpdateWrapper<ActModule>().eq("id", actModule.getId()));
        //刷新缓存
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_HOME_LINK);
        redisTemplate.delete(CacheConstant.CACHE_KEY_ACT_LIST);
        return ActResponse.buildSuccessResponse();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActResponse uploadActSpecLinkInfo(String actCode, String specCode, MultipartFile file) {
        if (ObjectUtils.isEmpty(file)) {
            return ActResponse.buildErrorResponse("文件上传错误");
        }
        String originalFilename = file.getOriginalFilename();
        String fileSuffix = originalFilename.split("\\.")[1];
        if (!"xls".equalsIgnoreCase(fileSuffix) && !"xlsx".equalsIgnoreCase(fileSuffix)) {
            return ActResponse.buildErrorResponse("文件必须为excel");
        }

        ArrayList<Mall> malls = new ArrayList<>();
        try {
            ExcelReader reader = EasyExcelFactory.getReader(new BufferedInputStream(file.getInputStream()), new AnalysisEventListener<List<String>>() {
                @Override
                public void invoke(List<String> list, AnalysisContext analysisContext) {
                    Mall mall = new Mall();
                    mall.setOmsCode(list.get(0));
                    mall.setLinkShow(true);
                    malls.add(mall);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            });
            reader.read();
        } catch (IOException e) {
            LOGGER.error("excel上传失败 e:{}", e);
            return ActResponse.buildErrorResponse("更新失败");
        }
        if (malls.size() < 1) {
            return ActResponse.buildErrorResponse("读取文件失败");
        }
        actSpecLinkMybatisMapper.updateSpecLinkMergerisShow(actCode, specCode, false);
        actSpecLinkMybatisMapper.updateSpecLinkMergerByList(actCode, specCode, malls);
        //删除所有商场广告位缓存
        for (Mall m : mallMybatisMapper.selectList(null)) {
            redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK + m.getOmsCode());
        }
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
        String res = checkActCode(actCode);
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
        String res = checkActCode(actCode);
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
            actSpecLink.setTime(null);
        }
        List<String> time = actSpecLink.getTime();
        if (!ObjectUtils.isEmpty(time) && time.size() == 2) {
            HashMap<Object, Object> map = Maps.newHashMap();
            map.put("startTime", time.get(0));
            map.put("endTime", time.get(1));
            actSpecLink.setTimeLimit(JSON.toJSONString(map));
        }
        int i = actSpecLinkMybatisMapper.insert(actSpecLink);
        //更新关联的act_mall_sepc__merge表
        //查询出所有商场信息
        List<Mall> malls = mallMybatisMapper.selectList(null);
        malls.stream().forEach(m -> m.setLinkShow(false));
        actSpecLinkMybatisMapper.insertSpecLinkMergeByList(actCode, actSpecLink.getSpecCode(), malls);
        clearSpecLink(actCode, actSpecLink);
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

        String res = checkActCode(actCode);
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        if (ObjectUtils.isEmpty(actSpecLink)) {
            return ActResponse.buildErrorResponse("参数有误");
        }
        actSpecLink.setShowImage(removeImageWatermark(actSpecLink.getShowImage()));
        ActSpecLink oldInfo = actSpecLinkMybatisMapper.selectOne(new QueryWrapper<ActSpecLink>().eq("spec_code", actSpecLink.getSpecCode()));

        if (!"T".equals(actSpecLink.getHaveTL())) {
            actSpecLink.setTime(null);
            actSpecLink.setTimeLimit(null);
        }
        actSpecLink.setMallList(actSpecLink.getChangeMallList());
        if (actSpecLink.getMallList().size() > 0) {
            actSpecLinkMybatisMapper.updateSpecLinkMergerByList(actCode, actSpecLink.getSpecCode(), actSpecLink.getMallList());
        }
        //修改了名称或者url，需要清除所有缓存
        List<String> time = actSpecLink.getTime();
        if (!ObjectUtils.isEmpty(time) && time.size() == 2) {
            HashMap<Object, Object> map = Maps.newHashMap();
            map.put("startTime", time.get(0));
            map.put("endTime", time.get(1));
            actSpecLink.setTimeLimit(JSON.toJSONString(map));
        }
        actSpecLinkMybatisMapper.update_NullField(actSpecLink);
        clearSpecLink(actCode, actSpecLink);
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
        String res = checkActCode(actCode);
        if (!"SUCCESS".equals(res)) {
            return ActResponse.buildErrorResponse(res);
        }
        if (ObjectUtils.isEmpty(actSpecLink)) {
            return ActResponse.buildErrorResponse("参数有误");
        }
        actSpecLinkMybatisMapper.delete(new QueryWrapper<ActSpecLink>().eq("spec_code", actSpecLink.getSpecCode()));
        actSpecLinkMybatisMapper.deleteSpecLinkMergerByList(actCode, actSpecLink.getSpecCode());
        clearSpecLink(actCode, actSpecLink);
        return ActResponse.buildSuccessResponse();
    }

    @Override
    public ActResponse number(String source) {
        Map<String, Object> result = Maps.newHashMap();
        //菜单名称
        result.put("menuName", "数据报表");
        Integer groupNumber = findGroupNumber(source);
        Integer extraNumber = findExtraNumber(source);
        if (ObjectUtils.isEmpty(extraNumber)) {
            extraNumber = 0;
        }
        if (ObjectUtils.isEmpty(groupNumber)) {
            groupNumber = 0;
        }
        result.put("extraNumber", extraNumber);
        result.put("groupNumber", groupNumber);
        result.put("totalNumber", groupNumber + extraNumber);
        return ActResponse.buildSuccessResponse(result);
    }


    @Override
    public void addGroupNumber(String source, Integer addGroupNumber) {
        String key = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.KEY_EXTRA_NUMBER;
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, 0);
        }
        Integer extraNumber = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
        stringRedisTemplate.opsForValue().set(key, String.valueOf(extraNumber + addGroupNumber));
    }

    @Override
    public void changeTicketNumber(String source, ActExtraNumber actExtraNumber) {
        StopWatch sw = new StopWatch();
        sw.start("两天活动购买人数添加");

        //先查表里所有的商场omsCode
        List<Mall> mallList = comService.listMallByAct(source);
        List<Integer> groupIdArray = groupIdArray(source);
        HashMap<String, String> res = new HashMap<>();
        String tsnKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.KEY_T_S_N;
        for (Mall mall : mallList) {
            //遍历该商场的所有团
            for (int i = 0; i < groupIdArray.size(); i++) {
                Integer groupId = groupIdArray.get(i);
                List<ActGroupTicketV2> tickets = comService.getGroupTickets(mall.getOmsCode(), source, groupId.toString());
                if (!ObjectUtils.isEmpty(tickets)) {
                    //每个券Id取出
                    for (ActGroupTicketV2 actTicket : tickets) {
                        Integer ticketId = actTicket.getSingleTicketId();
                        Integer extraNumber = addTicketNumberV2(actTicket, actExtraNumber);
                        String shameNumber = (String) redisTemplate.opsForHash().get(tsnKey, ticketId.toString());
                        if (!ObjectUtils.isEmpty(shameNumber)) {
                            extraNumber += Integer.valueOf(shameNumber);
                        }
                        res.put(ticketId.toString(), extraNumber.toString());
                    }
                }
            }
        }
        redisTemplate.opsForHash().put(tsnKey, res, RedisConstant.TOKEN_EXPIRY_JUNE);

        sw.stop();
        LOGGER.info(sw.toString());
    }

    @Override
    public List<Map> findGroupCountBySource(String source) {

        List<Map> groupsCardingNum = new ArrayList<>();
        Act act = ActFactory.create(source);
        for (int i = 1; i < 7; i++) {
            HashMap<String, Object> group = new HashMap<>();
            String groupName = act.findNameByGroupId(i);
            int count = comMybatisMapper.findRecordByGroupIdAndSource(i, source);
            group.put("groupName", groupName);
            group.put("count", count);
            groupsCardingNum.add(group);
        }
        return groupsCardingNum;
    }

    @Override
    public List<Map<String, String>> findAllActNameAndSource() {
        ArrayList<Map<String, String>> res = new ArrayList<>();
        ActModule queryCondition = new ActModule();
        queryCondition.setSubType(1);
        List<ActModule> actModules = actModuleMybatisMapper.selectList(new QueryWrapper<ActModule>().eq("sub_type", 1));
        for (ActModule actModule : actModules) {
            HashMap<String, String> nameAndSource = new HashMap<>();
            nameAndSource.put("label", actModule.getModuleName());
            nameAndSource.put("value", actModule.getActCode());
            res.add(nameAndSource);
        }
        return res;
    }

    /**
     * 活动pv、uv
     *
     * @param sourcePvUvBo
     * @return
     */
    @Override
    public List<SourcePvUvVo> analysisPVUVData(SourcePvUvBo sourcePvUvBo) {
        String key = CacheConstant.CACHE_KEY_PREFIX + sourcePvUvBo.getSource() + CacheConstant.CACHE_KEY_PVUV;
        String s = (String) stringRedisTemplate.opsForValue().get(key);
        List<SourcePvUvVo> sourcePvUvVoList = Lists.newArrayList();
        if (ObjectUtils.isEmpty(s) || sourcePvUvBo.isActualFlag()) {
            SourcePvUvVo sourcePvUvVo = new SourcePvUvVo();
            SourcePvUvBo sourcePvUvBo1 = new SourcePvUvBo(sourcePvUvBo.getSource());
            SourcePvUvVo sourcePvUvVo1 = comMybatisMapper.analysisPVUV(sourcePvUvBo1);
            sourcePvUvVoList.add(sourcePvUvVo1);
            sourcePvUvVoList.add(sourcePvUvVo);
            List<SourcePvUvVo> sourcePvUvVoData = comMybatisMapper.analysisPVUVData(sourcePvUvBo);
            for (SourcePvUvVo entity : sourcePvUvVoData) {
                sourcePvUvVo.setPv((ObjectUtils.isEmpty(entity.getPv()) ? 0 : entity.getPv()) + (ObjectUtils.isEmpty(sourcePvUvVo.getPv()) ? 0 : sourcePvUvVo.getPv()));
                sourcePvUvVo.setUv((ObjectUtils.isEmpty(entity.getUv()) ? 0 : entity.getUv()) + (ObjectUtils.isEmpty(sourcePvUvVo.getUv()) ? 0 : sourcePvUvVo.getUv()));
            }
            sourcePvUvVo.setDate("日期总计");
            sourcePvUvVoList.addAll(sourcePvUvVoData);
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(sourcePvUvVoList), CacheConstant.DAY, TimeUnit.SECONDS);
        } else {
            List<SourcePvUvVo> sourcePvUvVoList2 = Lists.newArrayList();
            if (JsonUtil.isJSON(s)) {
                sourcePvUvVoList = JSON.parseArray(s, SourcePvUvVo.class);
                if (!ObjectUtils.isEmpty(sourcePvUvBo.getStartTime()) && !ObjectUtils.isEmpty(sourcePvUvBo.getEndTime())) {
                    AtomicInteger i = new AtomicInteger();
                    sourcePvUvVoList.forEach(e -> {
                        i.getAndIncrement();
                        if (i.get() > 2) {
                            try {
                                if (DateNewUtil.parseAnyString(e.getDate()).after(sourcePvUvBo.getStartTime()) && DateNewUtil.parseAnyString(e.getDate()).before(sourcePvUvBo.getEndTime())) {
                                    sourcePvUvVoList2.add(e);
                                }
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            sourcePvUvVoList2.add(e);
                        }
                    });

                }
            }
            sourcePvUvVoList = sourcePvUvVoList2;
        }
        return sourcePvUvVoList;
    }

    @Override
    public void clearSpecLink(String actCode, ActSpecLink actSpecLink) {
        for (Mall m : mallMybatisMapper.selectList(null)) {
            redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK + m.getOmsCode());
        }
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_LINK_ALLOWED_MALL);
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_SPEC_DEFAULT_MALL + actSpecLink.getSpecCode());
    }

    /**
     * 根据不同价格添加单品券人数
     *
     * @return
     */
    public Integer addTicketNumberV2(ActGroupTicketV2 actTicket, ActExtraNumber actExtraNumber) {
        BigDecimal a = new BigDecimal(actExtraNumber.getMinPrice());
        BigDecimal b = new BigDecimal(actExtraNumber.getMaxPrice());
        BigDecimal price = actTicket.getVipPrice();
//        BigDecimal price = new BigDecimal("2000.00");
        Integer extraNumber = 0;
        if (!ObjectUtils.isEmpty(price)) {
            if (price.compareTo(a) < 0) {
                extraNumber = countNumber(actExtraNumber.getLowMinNumber(), actExtraNumber.getLowMaxNumber());
            } else if (price.compareTo(a) >= 0 && price.compareTo(b) <= 0) {
                extraNumber = countNumber(actExtraNumber.getMidMinNumber(), actExtraNumber.getMidMaxNumber());
            } else if (price.compareTo(b) > 0) {
                extraNumber = countNumber(actExtraNumber.getHighMinNumber(), actExtraNumber.getHighMaxNumber());
            }
        }
        return extraNumber;
    }

    /**
     * 随机生成a-b之间的一个数字
     *
     * @param a
     * @param b
     * @return
     */
    public Integer countNumber(Integer a, Integer b) {
        Integer i = new Random().nextInt((b - a) + 1) + a;
        return i;
    }

    private List<Integer> groupIdArray(String source) {
        Act act = ActFactory.create(source);
        JSONArray jsonArray = (JSONArray) act.getConfig("GROUP_ID_LIST");
        if (ObjectUtils.isEmpty(jsonArray)) {
            return Lists.newArrayList(1, 2, 3, 4, 5, 6);
        }
        return jsonArray.toJavaList(Integer.class);
    }

    /**
     * 查真实参团人数
     *
     * @param source
     * @return
     */
    public Integer findGroupNumber(String source) {
        String key = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.KEY_GROUP_NUMBER;
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, 200);
        }
        Integer extraNumber = (Integer) redisTemplate.opsForValue().get(key);
        return extraNumber;
    }

    /**
     * 查添加参团人数
     *
     * @param source
     * @return
     */
    public Integer findExtraNumber(String source) {
        String key = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.KEY_EXTRA_NUMBER;
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, 0);
        }
        Integer extraNumber = (Integer) redisTemplate.opsForValue().get(key);
        return extraNumber;
    }


}