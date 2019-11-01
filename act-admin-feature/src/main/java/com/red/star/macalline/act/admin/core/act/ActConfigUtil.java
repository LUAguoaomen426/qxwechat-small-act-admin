package com.red.star.macalline.act.admin.core.act;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.red.star.macalline.act.admin.constant.CacheConstant;
import com.red.star.macalline.act.admin.constant.ResponseConstant;
import com.red.star.macalline.act.admin.core.exception.CustomException;
import com.red.star.macalline.act.admin.domain.ActModule;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.mapper.ActModuleMybatisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Date 2019/8/26 16:22
 * @Created by Akari
 */
@Component
public class ActConfigUtil {
    private static final Cache<String, Act> ACT_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1L, TimeUnit.DAYS).build();

    private Logger logger = LoggerFactory.getLogger(ActConfigUtil.class);

    @Resource
    private ActModuleMybatisMapper actModuleMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 通过source获取Act
     *
     * @param source
     * @return
     */
    public Act getConfiguredActBySource(String source) {
        if (ObjectUtils.isEmpty(source)) {
            ActResponse response = new ActResponse.Builder().code(ResponseConstant.RESPONSE_CODE_TRANSACTION).message("source为空").build();
            throw new CustomException(response);
        }
        Act act = null;
        try {
            act = ACT_CACHE.get(source, () -> getAct(source));
        } catch (Exception e) {
            logger.error("actCache 获取活动信息异常，e:{} source:{}", e, source);
        }
        if (ObjectUtils.isEmpty(act)) {
            ActResponse response = new ActResponse.Builder().code(ResponseConstant.RESPONSE_CODE_TRANSACTION).message("不存在此活动！ source:" + source).build();
            throw new CustomException(response);
        }
        return act;
    }

    /**
     * 通过posterId获取Act
     *
     * @param posterId
     * @return
     */
    public Act getConfiguredActByPosterId(String posterId) {
        if (ObjectUtils.isEmpty(posterId)) {
            ActResponse response = new ActResponse.Builder().code(ResponseConstant.RESPONSE_CODE_TRANSACTION).message("posterId为空").build();
            throw new CustomException(response);
        }
        Act act = null;
        try {
            act = ACT_CACHE.get(posterId, () -> {
                String source = posterIdConvertToSource(posterId);
                return getAct(source);
            });
        } catch (Exception e) {
            logger.error("actCache 获取活动信息异常，e:{} posterId:{}", e, posterId);
        }
        return act;
    }

    /**
     * posterId 转换为 source
     *
     * @param posterId
     * @return
     */
    private String posterIdConvertToSource(String posterId) {
        String source = stringRedisTemplate.opsForValue().get(CacheConstant.CACHE_KEY_ACT_MODULE_POSTER_ID + posterId);
        if (ObjectUtils.isEmpty(source)) {
            source = actModuleMapper.posterIdConvertToActCode(posterId);
            if (ObjectUtils.isEmpty(source)) {
                logger.error("posterId Convert error , posterId:{}", posterId);
                return null;
            }
            stringRedisTemplate.opsForValue().set(CacheConstant.CACHE_KEY_ACT_MODULE_POSTER_ID + posterId, source, CacheConstant.DAY * 31, TimeUnit.SECONDS);
        }
        return source;
    }

    /**
     * 获取act
     *
     * @param key
     * @return
     */
    private Act getAct(String key) {
        if (ObjectUtils.isEmpty(key)) {
            return null;
        }
        String actStr = stringRedisTemplate.opsForValue().get(CacheConstant.CACHE_KEY_ACT_MODULE + key);
        Act act = JSON.parseObject(actStr, ConfiguratedAct.class);
        if (ObjectUtils.isEmpty(act)) {
            ActModule selectInfo = new ActModule();
            selectInfo.setActCode(key);
            ActModule actModule = actModuleMapper.selectOne(new QueryWrapper<ActModule>().lambda().eq(ActModule::getActCode, selectInfo.getActCode()));
            if (!ObjectUtils.isEmpty(actModule)) {
                ConfiguratedAct configuratedAct = new ConfiguratedAct(actModule);
                String string = JSONObject.toJSONString(configuratedAct);
                stringRedisTemplate.opsForValue().set(CacheConstant.CACHE_KEY_ACT_MODULE + key, string, CacheConstant.DAY * 31, TimeUnit.SECONDS);
                act = configuratedAct;
            }
        }
        return act;
    }

    /**
     * 根据source刷新缓存
     *
     * @param source
     */
    public void refreshActCache(String source) {
        if (ObjectUtils.isEmpty(source)) {
            logger.info("刷新缓存-source为空");
        }
        Act oldAct = null;
        try {
            oldAct = getConfiguredActBySource(source);
        } catch (CustomException e) {
            logger.info("刷新缓存-获取oldAct失败 source:{}", source);
            return;
        }
        ACT_CACHE.invalidate(oldAct.getSource());
        if (!ObjectUtils.isEmpty(oldAct.getPosterId())) {
            ACT_CACHE.invalidate(oldAct.getPosterId());
        }
    }

}
