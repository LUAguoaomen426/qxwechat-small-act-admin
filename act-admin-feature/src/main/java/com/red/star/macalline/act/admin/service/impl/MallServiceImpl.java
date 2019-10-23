package com.red.star.macalline.act.admin.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.red.star.macalline.act.admin.constant.CacheConstant;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.ActSpecLink;
import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.entity.MallMsgBO;
import com.red.star.macalline.act.admin.exception.EntityExistException;
import com.red.star.macalline.act.admin.mapper.ActModuleMybatisMapper;
import com.red.star.macalline.act.admin.mapper.ActSpecLinkMybatisMapper;
import com.red.star.macalline.act.admin.mapper.MallMybatisMapper;
import com.red.star.macalline.act.admin.repository.MallRepository;
import com.red.star.macalline.act.admin.service.MallService;
import com.red.star.macalline.act.admin.service.dto.MallDTO;
import com.red.star.macalline.act.admin.service.dto.MallQueryCriteria;
import com.red.star.macalline.act.admin.service.mapper.MallMapper;
import com.red.star.macalline.act.admin.util.WxInfoUtil;
import com.red.star.macalline.act.admin.utils.PageUtil;
import com.red.star.macalline.act.admin.utils.QueryHelp;
import com.red.star.macalline.act.admin.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author AMGuo
 * @date 2019-10-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MallServiceImpl extends ServiceImpl<MallMybatisMapper, Mall> implements MallService {

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

    @Resource
    private ActSpecLinkMybatisMapper actSpecLinkMybatisMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(MallServiceImpl.class);
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

    /**
     * 同步商场数据
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncMallInfo() {
        HashMap<String, String> params = Maps.newHashMap();
        params.put("noteId", "NOTE1480473939499");
        params.put("queryType", "all");
        String mallMessageApi = WxInfoUtil.getMallMessageApi(null, JSON.toJSONString(params));
        if (ObjectUtils.isEmpty(mallMessageApi)) {
            //获取商场数据失败
            return;
        }
        JSONObject jsonObject = JSON.parseObject(mallMessageApi);
        String results = jsonObject.getString("results");
        List<MallMsgBO> mallMsgBOSList = JSON.parseArray(results, MallMsgBO.class);
        Map<String, Mall> allOmsCode = mallMybatisMapper.findAllMallWithKeyOmsCode();
        List<Mall> newMalls = new ArrayList<>();
        List<Mall> updateMall = new ArrayList<>();
        for (MallMsgBO mallBo : mallMsgBOSList) {
            if ("1248".equals(mallBo.getOmsCode())) {
                mallBo.setCity("巢湖市");
            }
            Mall mall = new Mall();
            mall.setOmsCode(mallBo.getOmsCode());
            mall.setMallCode(mallBo.getMallCode());
            mall.setMallName(mallBo.getMallName());
            mall.setProvince(mallBo.getProvince());
            mall.setCity("市辖区".equals(mallBo.getCity()) ? mallBo.getProvince() : mallBo.getCity());
            mall.setSelfFlag("自营".equals(mallBo.getMallType()));
            mall.setDetailAddress(mallBo.getAddress());
            Mall oldMallInfo = allOmsCode.get(mallBo.getOmsCode());
            if (ObjectUtils.isEmpty(oldMallInfo)) {
                //为新增
                mall.setDefaultEnable(false);
                mall.setEntranceEnable(false);
                mall.setDefaultEnable(false);
                mall.setDefultJoin(false);
                mall.setLinkShow(false);
                mall.setIsMl(false);
                newMalls.add(mall);
            } else {
                if (!oldMallInfo.equals(mall)) {
                    //为更新商场信息
                    mall.setEntranceEnable(oldMallInfo.getEntranceEnable());
                    updateMall.add(mall);
                }
            }
        }
        List<ActModule> allAct = actModuleMybatisMapper.selectList(null);
        //插入wap_mall表
        if (!ObjectUtils.isEmpty(newMalls)) {
            for (Mall mall:newMalls){
                mallMybatisMapper.insert(mall);
            }
            for (ActModule actModule : allAct) {
                //插入wap_act_mall_merge
                mallMybatisMapper.insertActMallMerge(actModule.getActCode(), newMalls);
                //插入wap_act_mall_spec_merge
                List<ActSpecLink> actSpecLinks = actSpecLinkMybatisMapper.listFindSpecLinkByActCode(actModule.getActCode());
                for (ActSpecLink actSpecLink : actSpecLinks) {
                    actSpecLinkMybatisMapper.insertSpecLinkMergeByList(actModule.getActCode(), actSpecLink.getSpecCode(), newMalls);
                }
            }
        }

        //更新商场信息
        if (!ObjectUtils.isEmpty(updateMall)) {
            mallMybatisMapper.updateWapMall(updateMall);
        }

        //删除商场基本数据缓存  活动及特殊链接内商场默认不打开，不用清除缓存
        redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_LIST_ENTRANCE);
        for (ActModule actModule : actModuleMybatisMapper.listEnableAct()) {
            redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_LIST_HOME+actModule.getActCode());
        };
        for (Mall mall : updateMall) {
            redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_CODE + mall.getMallCode());
            redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_OMS_CODE + mall.getOmsCode());
        }

    }


    /**
     * 上传文件批量修改商场信息
     *
     * @param file
     */
    @Transactional(readOnly = false)
    public void uploadMallinfo(String actCode, MultipartFile file) {
        if (ObjectUtils.isEmpty(file)) {
           // return ActResponse.buildErrorResponse("文件上传错误");
        }
        String originalFilename = file.getOriginalFilename();
        String fileSuffix = originalFilename.split("\\.")[1];
        if (!"xls".equalsIgnoreCase(fileSuffix) && !"xlsx".equalsIgnoreCase(fileSuffix)) {
          //  return ActResponse.buildErrorResponse("文件必须为excel");
        }
        List<Mall> omsCodes = new ArrayList<>();
        try {
            ExcelReader reader = EasyExcelFactory.getReader(new BufferedInputStream(file.getInputStream()), new AnalysisEventListener<List<String>>() {

                @Override
                public void invoke(List<String> o, AnalysisContext analysisContext) {
                    Mall mall = new Mall();
                    mall.setOmsCode(o.get(0));
                    mall.setIsJoin(true);
                    omsCodes.add(mall);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            });
            reader.read();
        } catch (IOException e) {
            LOGGER.error("excel上传失败 e:{}", e);
           // return ActResponse.buildErrorResponse("更新失败");
        }
        if (omsCodes.size() < 1) {
           // return ActResponse.buildErrorResponse("读取文件失败");
        }
        //批量更新对应活动的omsCode
        mallMybatisMapper.updateActMergeIsJoinByActCode(actCode, false);
        mallMybatisMapper.updateActMallMerge(actCode, omsCodes);

        //清除一下缓存
        redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_LIST_ACT + actCode);
        redisTemplate.delete(CacheConstant.CACHE_KEY_MALL_LIST_HOME+actCode);
        redisTemplate.delete(CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_MALL_OMS_CODE);


       // return ActResponse.buildSuccessResponse();
    }


}