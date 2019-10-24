package com.red.star.macalline.act.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.red.star.macalline.act.admin.constant.ActConstant;
import com.red.star.macalline.act.admin.constant.CacheConstant;
import com.red.star.macalline.act.admin.constant.Constant;
import com.red.star.macalline.act.admin.constant.RedisConstant;
import com.red.star.macalline.act.admin.core.act.Act;
import com.red.star.macalline.act.admin.core.act.ActFactory;
import com.red.star.macalline.act.admin.domain.Mall;
import com.red.star.macalline.act.admin.domain.bo.ActivityBo;
import com.red.star.macalline.act.admin.domain.bo.ActivityHomeBo;
import com.red.star.macalline.act.admin.domain.bo.HomeSingleBo;
import com.red.star.macalline.act.admin.domain.bo.PromotionTicketBo;
import com.red.star.macalline.act.admin.domain.dto.BoostAwardDTO;
import com.red.star.macalline.act.admin.domain.dto.TicketInfoDTO;
import com.red.star.macalline.act.admin.domain.vo.ActGroupTicketV2;
import com.red.star.macalline.act.admin.mapper.ActModuleMybatisMapper;
import com.red.star.macalline.act.admin.mapper.MallMybatisMapper;
import com.red.star.macalline.act.admin.util.BeanUtil;
import com.red.star.macalline.act.admin.util.JsonUtil;
import com.red.star.macalline.act.admin.util.PriceUtil;
import com.red.star.macalline.act.admin.util.WxInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.site.common
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-06-04 15:18
 * @Version: 1.0
 */
@Service
public class ComService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComService.class);

    @Resource
    private MallService mallService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private MallMybatisMapper mallMybatisMapper;

    /**
     * 预热活动下的基础数据
     *
     * @param source 活动标识
     */
    public void preheat(String source) {
        StopWatch sw = new StopWatch(source + "大促活动——集团缓存刷新");
        sw.start();
        List<Mall> mallList = listMallByAct(source);
        mallList.forEach(entity -> {
            //商场扩展信息预热
            preheatMallExtendInfo(source, entity.getOmsCode());
        });
        sw.stop();
        LOGGER.info(sw.toString());
    }

    /**
     * 商场扩展信息预热（券，专供，团）
     *
     * @param source  活动标识
     * @param omsCode
     * @throws IOException
     */
    public void preheatMallExtendInfo(String source, String omsCode) {
        StopWatch sw = new StopWatch(source + "大促活动——" + omsCode + "商场缓存刷新");
        sw.start();
        String cmeiKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_CMEI + omsCode;
        String cmhiKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_CMHI + omsCode;
        String mgtiKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_MGTI + omsCode;
        String mtiKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_MTI + omsCode;
        String yltiKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_YLTI + omsCode;
        //翻牌抽奖券
        String awardKey1 = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_BOOST_AWARD + omsCode + CacheConstant.CACHE_KEY_COLON + 1;
        String awardKey2 = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_BOOST_AWARD + omsCode + CacheConstant.CACHE_KEY_COLON + 2;
        String awardKey3 = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_BOOST_AWARD + omsCode + CacheConstant.CACHE_KEY_COLON + 3;
        String awardKey4 = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_BOOST_AWARD + omsCode + CacheConstant.CACHE_KEY_COLON + 4;
        String awardKey5 = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_BOOST_AWARD + omsCode + CacheConstant.CACHE_KEY_COLON + 5;
        String awardKey6 = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_BOOST_AWARD + omsCode + CacheConstant.CACHE_KEY_COLON + 6;
        String awardKey7 = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_BOOST_AWARD + omsCode + CacheConstant.CACHE_KEY_COLON + 7;
        String oldSingle1 = (String) redisTemplate.opsForHash().get(cmhiKey, ActConstant.FIELD_SINGLE_1);
        String oldSingle2 = (String) redisTemplate.opsForHash().get(cmhiKey, ActConstant.FIELD_SINGLE_2);
        String oldSingle3 = (String) redisTemplate.opsForHash().get(cmhiKey, ActConstant.FIELD_SINGLE_3);
        JSONObject oldSingleObject1 = ObjectUtils.isEmpty(oldSingle1) ? new JSONObject() : JSON.parseObject(oldSingle1);
        JSONObject oldSingleObject2 = ObjectUtils.isEmpty(oldSingle2) ? new JSONObject() : JSON.parseObject(oldSingle2);
        JSONObject oldSingleObject3 = ObjectUtils.isEmpty(oldSingle3) ? new JSONObject() : JSON.parseObject(oldSingle3);
        Map<String, String> mgtiResult = redisTemplate.opsForHash().entries(mgtiKey);
        //获取本商场券的真实&虚假参团人数
        Map<String, String> result = redisTemplate.opsForHash().entries(mtiKey);
        //删
        redisTemplate.delete(cmeiKey);
        //删
        redisTemplate.delete(cmhiKey);
        //删——商场&团&券详情
        redisTemplate.delete(mgtiKey);
        //删——商场&券
        redisTemplate.delete(mtiKey);
        //删——有龙券详情
        redisTemplate.delete(yltiKey);
        //删——抽奖券
        redisTemplate.delete(awardKey1);
        redisTemplate.delete(awardKey2);
        redisTemplate.delete(awardKey3);
        redisTemplate.delete(awardKey4);
        redisTemplate.delete(awardKey5);
        redisTemplate.delete(awardKey6);
        redisTemplate.delete(awardKey7);
        JSONObject actList = null;
        Act act = ActFactory.create(source);
        try {
            actList = WxInfoUtil.getActList(omsCode, act.getPosterId());
            LOGGER.info("商场扩展信息预热-ActList source:{} actList:{} omsCode:{}", act.getSource(), actList, omsCode);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
        }
        if (!ObjectUtils.isEmpty(actList)) {
            JSONArray dataMap = actList.getJSONArray("dataMap");
            if (!ObjectUtils.isEmpty(dataMap)) {
                JSONObject jsonObject = dataMap.getJSONObject(0);
                if (!ObjectUtils.isEmpty(jsonObject)) {
                    ActivityBo activityBo = JSONObject.toJavaObject(jsonObject, ActivityBo.class);
                    activityBo.convert();
                    Map<String, String> activityBoMap = BeanUtil.beanToMap(activityBo);
                    redisTemplate.opsForHash().putAll(cmeiKey, activityBoMap);
                    redisTemplate.expire(cmeiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                    //设置券包的详情
                    Integer packageTicketId = jsonObject.getInteger("package_ticket_id");
                    if (!ObjectUtils.isEmpty(packageTicketId)) {
                        TicketInfoDTO ticketInfo = WxInfoUtil.getTicketInfo(String.valueOf(packageTicketId));
                        if (!ObjectUtils.isEmpty(ticketInfo)) {
                            redisTemplate.opsForHash().put(mtiKey, String.valueOf(packageTicketId), JSON.toJSONString(ticketInfo));
                            redisTemplate.expire(mtiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                        }
                        // ticketList  通过券id 查询券详情（com.red.star.macalline.act.util.WxInfoUtil.getTicketInfo） 填充到imp:wap:source:m_t_i
                        List<TicketInfoDTO> ticketList = ticketInfo.getTicketList();
                        if (ticketList != null && ticketList.size() > 0) {
                            for (TicketInfoDTO ticketInfoDTO : ticketList) {
                                redisTemplate.opsForHash().put(mtiKey, String.valueOf(ticketInfoDTO.getPromotionTicketId()), JSON.toJSONString(ticketInfo));
                                redisTemplate.expire(mtiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                            }
                        }
                    }
                    //商场（券，专供，团）首页
                    preheatMallHomeInfo(omsCode, jsonObject, cmhiKey, oldSingleObject1, oldSingleObject2, oldSingleObject3);
                    //商场&团&券详情
                    try {
                        prehearMallGroupTicketInfo(omsCode, mgtiKey, mtiKey, yltiKey, mgtiResult, result, act.getPosterId(), awardKey1, awardKey2, awardKey3, awardKey4, awardKey5, awardKey6, awardKey7);
                    } catch (IOException e) {
                        LOGGER.error(e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
        sw.stop();
        LOGGER.info(sw.toString());
    }


    /**
     * 商场爆款信息预热
     *
     * @param omsCode
     */
    public void preheatPromotionTicket(String omsCode) {
        StopWatch sw = new StopWatch("爆品列表缓存刷新");
        sw.start();
        String mpiKey = ActConstant.KEY_MPI + omsCode;
        String mbpiKey = ActConstant.KEY_MBPI + omsCode;
        JSONObject actList = null;
        try {
            actList = WxInfoUtil.getPromotionTicketList(omsCode);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
        }
        preheatMPI(mpiKey, actList);
        preheatMBPI(mbpiKey, actList);
        sw.stop();
        LOGGER.info(sw.toString());
    }

    /**
     * 同步商场-券（爆款）
     *
     * @param mpiKey
     * @param actList
     */
    private void preheatMPI(String mpiKey, JSONObject actList) {
        redisTemplate.delete(mpiKey);
        if (!ObjectUtils.isEmpty(actList)) {
            JSONArray dataMap = actList.getJSONArray("dataMap");
            if (!ObjectUtils.isEmpty(dataMap)) {
                dataMap.forEach(item -> {
                    JSONObject singleObject = (JSONObject) item;
                    if (!ObjectUtils.isEmpty(singleObject)) {
                        Integer singleTicketId = singleObject.getInteger("promotion_ticket_id");
                        if (!ObjectUtils.isEmpty(singleTicketId)) {
                            redisTemplate.opsForHash().put(mpiKey, String.valueOf(singleTicketId), singleObject.toJSONString());
                            redisTemplate.expire(mpiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                        }
                    }
                });
            }
        } else {
            redisTemplate.delete(mpiKey);
        }
    }

    /**
     * 同步商场-品牌-券（爆款）
     *
     * @param mbpiKey
     * @param actList
     */
    private void preheatMBPI(String mbpiKey, JSONObject actList) {
        redisTemplate.delete(mbpiKey);
        if (!ObjectUtils.isEmpty(actList)) {
            JSONArray dataMap = actList.getJSONArray("dataMap");
            if (!ObjectUtils.isEmpty(dataMap)) {
                List<PromotionTicketBo> ticketBoList = JSON.parseArray(dataMap.toJSONString(), PromotionTicketBo.class);
                ticketBoList.stream()
                        .filter(p -> !ObjectUtils.isEmpty(p.getItem_category()))
                        .collect(Collectors.groupingBy(PromotionTicketBo::getItem_category))
                        .entrySet()
                        .forEach(item -> {
                            redisTemplate.opsForHash().put(mbpiKey, item.getKey(), JSON.toJSONString(item.getValue()));
                            redisTemplate.expire(mbpiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                        });
            }
        } else {
            redisTemplate.delete(mbpiKey);
        }
    }

    /**
     * 商场（券，专供，团）首页
     *
     * @param omsCode
     * @param jsonObject
     * @param cmhiKey
     * @param oldSingleObject1
     * @param oldSingleObject2
     * @param oldSingleObject3
     */
    private void preheatMallHomeInfo(String omsCode,
                                     JSONObject jsonObject, String cmhiKey, JSONObject oldSingleObject1,
                                     JSONObject oldSingleObject2, JSONObject oldSingleObject3) {
        ActivityHomeBo activityHomeBo = JSONObject.toJavaObject(jsonObject, ActivityHomeBo.class);
        activityHomeBo.convert();
        JSONArray single = jsonObject.getJSONArray("single");
        List<HomeSingleBo> homeSingleBos = single.toJavaList(HomeSingleBo.class);
        homeSingleBos.sort(Comparator.comparing(HomeSingleBo::getSort));
        JSONArray singleNew = JSONArray.parseArray(JSON.toJSONString(homeSingleBos));
        //2个集团+5个商场
        for (Object singleEntity : singleNew) {
            JSONObject singleObject = (JSONObject) singleEntity;
            String itemTitle = singleObject.getString("item_title");
            Integer promotionTicketId = singleObject.getInteger("promotion_ticket_id");
            if (Constant.ITEM_TITLE_GROUP.equals(itemTitle)) {
                //集团专供
                String single1 = activityHomeBo.getSingle1();
                if (ObjectUtils.isEmpty(single1)) {
                    initIndexNumber(oldSingleObject1, oldSingleObject2, oldSingleObject3, promotionTicketId, singleObject);
                    activityHomeBo.setSingle1(JSONObject.toJSONString(singleObject));
                }
                String single2 = activityHomeBo.getSingle2();
                if (!ObjectUtils.isEmpty(single1) && ObjectUtils.isEmpty(single2)) {
                    initIndexNumber(oldSingleObject1, oldSingleObject2, oldSingleObject3, promotionTicketId, singleObject);
                    activityHomeBo.setSingle2(JSONObject.toJSONString(singleObject));
                }
                String single3 = activityHomeBo.getSingle3();
                if (!ObjectUtils.isEmpty(single2) && ObjectUtils.isEmpty(single3)) {
                    initIndexNumber(oldSingleObject1, oldSingleObject2, oldSingleObject3, promotionTicketId, singleObject);
                    activityHomeBo.setSingle3(JSONObject.toJSONString(singleObject));
                }
            } else {

            }
        }
        Map<String, String> activityHomeBoMap = BeanUtil.beanToMap(activityHomeBo);
        redisTemplate.opsForHash().putAll(cmhiKey, activityHomeBoMap);
        redisTemplate.expire(cmhiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置主会场首页的两个商场专供的参团数
     *
     * @param oldSingleObject1
     * @param oldSingleObject2
     * @param oldSingleObject3
     * @param promotionTicketId
     * @param singleObject
     */
    private void initIndexNumber(JSONObject oldSingleObject1, JSONObject oldSingleObject2, JSONObject oldSingleObject3, Integer promotionTicketId, JSONObject singleObject) {
        Integer realNumber = Constant.INTEGER_0;
        Integer shamNumber = Constant.INTEGER_0;
        Integer promotionTicketId1 = ObjectUtils.isEmpty(oldSingleObject1.getInteger("promotion_ticket_id")) ? Constant.INTEGER_0 : oldSingleObject1.getInteger("promotion_ticket_id");
        Integer promotionTicketId2 = ObjectUtils.isEmpty(oldSingleObject2.getInteger("promotion_ticket_id")) ? Constant.INTEGER_0 : oldSingleObject2.getInteger("promotion_ticket_id");
        Integer promotionTicketId3 = ObjectUtils.isEmpty(oldSingleObject3.getInteger("promotion_ticket_id")) ? Constant.INTEGER_0 : oldSingleObject3.getInteger("promotion_ticket_id");
        if (!ObjectUtils.isEmpty(promotionTicketId) && promotionTicketId.equals(promotionTicketId1)) {
            realNumber = ObjectUtils.isEmpty(oldSingleObject1.getInteger(ActConstant.KEY_REAL_NUMBER)) ? Constant.INTEGER_0 : oldSingleObject1.getInteger(ActConstant.KEY_REAL_NUMBER);
            shamNumber = ObjectUtils.isEmpty(oldSingleObject1.getInteger(ActConstant.KEY_SHAM_NUMBER)) ? Constant.INTEGER_10 : oldSingleObject1.getInteger(ActConstant.KEY_SHAM_NUMBER);
        } else if (!ObjectUtils.isEmpty(promotionTicketId) && promotionTicketId.equals(promotionTicketId2)) {
            realNumber = ObjectUtils.isEmpty(oldSingleObject2.getInteger(ActConstant.KEY_REAL_NUMBER)) ? Constant.INTEGER_0 : oldSingleObject2.getInteger(ActConstant.KEY_REAL_NUMBER);
            shamNumber = ObjectUtils.isEmpty(oldSingleObject2.getInteger(ActConstant.KEY_SHAM_NUMBER)) ? Constant.INTEGER_10 : oldSingleObject2.getInteger(ActConstant.KEY_SHAM_NUMBER);
        } else if (!ObjectUtils.isEmpty(promotionTicketId) && promotionTicketId.equals(promotionTicketId3)) {
            realNumber = ObjectUtils.isEmpty(oldSingleObject3.getInteger(ActConstant.KEY_REAL_NUMBER)) ? Constant.INTEGER_0 : oldSingleObject3.getInteger(ActConstant.KEY_REAL_NUMBER);
            shamNumber = ObjectUtils.isEmpty(oldSingleObject3.getInteger(ActConstant.KEY_SHAM_NUMBER)) ? Constant.INTEGER_10 : oldSingleObject3.getInteger(ActConstant.KEY_SHAM_NUMBER);
        }
        //真实参团人数&虚假参团人数
        singleObject.put(ActConstant.KEY_REAL_NUMBER, realNumber);
        //虚假参团人数
        singleObject.put(ActConstant.KEY_SHAM_NUMBER, shamNumber);
    }

    /**
     * 商场&团&券详情
     *
     * @param
     * @param omsCode
     * @param mgtiKey
     * @param mtiKey
     * @param yltiKey
     * @param actId
     * @param awardKey1
     * @throws IOException
     */
    private void prehearMallGroupTicketInfo(String omsCode, String mgtiKey, String mtiKey, String yltiKey,
                                            Map<String, String> mgtiResult, Map<String, String> result, String actId,
                                            String awardKey1, String awardKey2, String awardKey3, String awardKey4, String awardKey5, String awardKey6, String awardKey7) throws IOException {
        for (int i = 1; i <= Constant.INTEGER_13; i++) {
            JSONObject ticketInfo = WxInfoUtil.getGroupList(omsCode, String.valueOf(i), actId, null);
            LOGGER.info("商场扩展信息预热-GroupInfo PosterId:{} groupId:{} ticketInfo:{} omsCode:{}", actId, i, ticketInfo, omsCode);
            if (!ObjectUtils.isEmpty(ticketInfo)) {
                // 只有单品券
                JSONArray single = ticketInfo.getJSONArray("single");
                //打卡券
                JSONArray coupon = ticketInfo.getJSONArray("coupon");
                ticketInfo = initNumber(ticketInfo, ActConstant.KEY_GROUP + i, mgtiResult);
                if (!ObjectUtils.isEmpty(single)) {
                    redisTemplate.opsForHash().put(mgtiKey, ActConstant.KEY_GROUP + i, ticketInfo.toJSONString());
                    redisTemplate.expire(mgtiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                    //商场&券
                    prehearMallTicketInfo(omsCode, single, mtiKey, result, yltiKey);
                }
                if (!ObjectUtils.isEmpty(coupon)) {
                    redisTemplate.opsForHash().put(mgtiKey, ActConstant.KEY_GROUP + i, ticketInfo.toJSONString());
                    redisTemplate.expire(mgtiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                    //打卡券
                    prehearCouponInfo(coupon, mtiKey);
                }

            }
        }

        for (int i = 20; i <= 21; i++) {
            //雅居乐专场劵信息预热
            JSONObject ticketInfo = WxInfoUtil.getGroupList(omsCode, String.valueOf(i), actId, null);
            LOGGER.info("商场扩展信息预热-GroupInfo PosterId:{} groupId:{} ticketInfo:{} omsCode:{}", actId, i, ticketInfo, omsCode);
            redisTemplate.opsForHash().put(mgtiKey, ActConstant.KEY_GROUP + i, ticketInfo.toJSONString());
            redisTemplate.expire(mgtiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
            JSONArray single = ticketInfo.getJSONArray("single");
            if (!ObjectUtils.isEmpty(single)) {
                //商场&券
                prehearMallTicketInfo(omsCode, single, mtiKey, result, yltiKey);
            }
            JSONArray coupon = ticketInfo.getJSONArray("coupon");
            if (!ObjectUtils.isEmpty(coupon)) {
                //商场&券
                prehearMallTicketInfo(omsCode, coupon, mtiKey, result, yltiKey);
            }
        }

        //30,31 尖选王牌
        for (int i = 30; i <= 31; i++) {
            JSONObject ticketInfo = WxInfoUtil.getGroupList(omsCode, String.valueOf(i), actId, null);
            LOGGER.info("商场扩展信息预热-尖选王牌-GroupInfo PosterId:{} groupId:{} ticketInfo:{} omsCode:{}", actId, i, ticketInfo, omsCode);
            JSONArray single = ticketInfo.getJSONArray("single");
            redisTemplate.opsForHash().put(mgtiKey, ActConstant.KEY_GROUP + i, ticketInfo.toJSONString());
            redisTemplate.expire(mgtiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
            if (!ObjectUtils.isEmpty(single)) {
                prehearMallTicketInfo(omsCode, single, mtiKey, result, yltiKey);
            }
        }
        // 22.翻牌奖项券        1-7  根据价格|券名称查询各个档次的券
        JSONObject ticketInfo22 = WxInfoUtil.getGroupList(omsCode, "22", actId, 1);
        if (!ObjectUtils.isEmpty(ticketInfo22)) {
            JSONArray coupon = ticketInfo22.getJSONArray("coupon");
            if (!ObjectUtils.isEmpty(coupon) && !coupon.isEmpty()) {
                if (!ObjectUtils.isEmpty(coupon)) {
                    //商场&券
                    prehearMallTicketInfo(omsCode, coupon, mtiKey, result, yltiKey);
                }
                coupon.forEach(entity -> {
                    JSONObject entityObject = (JSONObject) entity;
                    String itemName = entityObject.getString("item_name");
                    Integer cashAmt = entityObject.getInteger("cash_amt");
                    Integer singleTicketId = entityObject.getInteger("single_ticket_id");
                    BoostAwardDTO boostAwardDTO = new BoostAwardDTO(singleTicketId, itemName, null, null, "商场");
                    if (cashAmt.equals(300)) {
                        stringRedisTemplate.opsForValue().set(awardKey1, JSON.toJSONString(boostAwardDTO), 31 * CacheConstant.DAY);
                    } else if (cashAmt.equals(500)) {
                        stringRedisTemplate.opsForValue().set(awardKey2, JSON.toJSONString(boostAwardDTO), 31 * CacheConstant.DAY);
                    } else if (cashAmt.equals(1000)) {
                        stringRedisTemplate.opsForValue().set(awardKey3, JSON.toJSONString(boostAwardDTO), 31 * CacheConstant.DAY);
                    } else if (cashAmt.equals(2000)) {
                        stringRedisTemplate.opsForValue().set(awardKey4, JSON.toJSONString(boostAwardDTO), 31 * CacheConstant.DAY);
                    } else if (cashAmt.equals(5000)) {
                        stringRedisTemplate.opsForValue().set(awardKey5, JSON.toJSONString(boostAwardDTO), 31 * CacheConstant.DAY);
                    } else if (cashAmt.equals(10000)) {
                        stringRedisTemplate.opsForValue().set(awardKey6, JSON.toJSONString(boostAwardDTO), 31 * CacheConstant.DAY);
                    } else if (cashAmt.equals(49999)) {
                        stringRedisTemplate.opsForValue().set(awardKey7, JSON.toJSONString(boostAwardDTO), 31 * CacheConstant.DAY);
                    }
                });
            }
        }
    }

    /**
     * 增加参团人数
     *
     * @param ticketInfo
     * @param field
     * @param mgtiResult
     * @return
     */
    private JSONObject initNumber(JSONObject ticketInfo, String field, Map<String, String> mgtiResult) {
        // 同步之前的真实&虚假参团人数
        if (!ObjectUtils.isEmpty(ticketInfo)) {
            JSONArray single = ticketInfo.getJSONArray("single");
            if (!ObjectUtils.isEmpty(single)) {
                single.forEach(entity -> {
                    JSONObject singleObject = (JSONObject) entity;
                    if (!ObjectUtils.isEmpty(singleObject)) {
                        Integer single_ticket_id = singleObject.getInteger("single_ticket_id");
                        if (!ObjectUtils.isEmpty(single_ticket_id)) {
                            Integer real_number = getNumberByMgtiField(mgtiResult, field, "real_number", single_ticket_id);
                            Integer sham_number = getNumberByMgtiField(mgtiResult, field, "sham_number", single_ticket_id);
                            Integer praiseNumber = getNumberByMgtiField(mgtiResult, field, "praiseNumber", single_ticket_id);
                            real_number = ObjectUtils.isEmpty(real_number) ? Constant.INTEGER_0 : real_number;
                            sham_number = ObjectUtils.isEmpty(sham_number) ? Constant.INTEGER_10 : sham_number;
                            praiseNumber = ObjectUtils.isEmpty(praiseNumber) ? Constant.INTEGER_0 : praiseNumber;
                            //真实参团人数&虚假参团人数
                            singleObject.put("real_number", real_number);
                            // 虚假参团人数
                            singleObject.put("sham_number", sham_number);
                            singleObject.put("praiseNumber", praiseNumber);
                        }
                    }
                });
            }
        }
        return ticketInfo;
    }

    /**
     * 根据field获取value
     *
     * @param mgtiResult
     * @param field
     * @param fieldKey
     * @param single_ticket_id
     * @return
     */
    private Integer getNumberByMgtiField(Map<String, String> mgtiResult, String field,
                                         String fieldKey, Integer single_ticket_id) {
        AtomicReference<Integer> num = new AtomicReference<>();
        for (Map.Entry<String, String> entity : mgtiResult.entrySet()) {
            String key = entity.getKey();
            if (!ObjectUtils.isEmpty(key) && key.equals(String.valueOf(field))) {
                String value = entity.getValue();
                if (!ObjectUtils.isEmpty(value) && JsonUtil.isJSON(value)) {
                    JSONObject jsonObject = JSONObject.parseObject(value);
                    if (!ObjectUtils.isEmpty(jsonObject)) {
                        JSONArray single = jsonObject.getJSONArray("single");
                        single.forEach(singleEntity -> {
                            if (!ObjectUtils.isEmpty(singleEntity)) {
                                JSONObject singleObject = (JSONObject) singleEntity;
                                if (!ObjectUtils.isEmpty(singleObject)) {
                                    Integer singleTicketId = singleObject.getInteger("single_ticket_id");
                                    if (!ObjectUtils.isEmpty(singleTicketId) && singleTicketId.equals(single_ticket_id)) {
                                        String number = singleObject.getString(fieldKey);
                                        if (!ObjectUtils.isEmpty(number)) {
                                            num.set(Integer.valueOf(number));
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
        return num.get();
    }


    /**
     * 商场&券
     *
     * @param
     * @param omsCode
     * @param single
     * @param mtiKey
     * @param result
     * @param yltiKey
     */
    private void prehearMallTicketInfo(String omsCode,
                                       JSONArray single, String mtiKey,
                                       Map<String, String> result, String yltiKey) {
        //增
        single.forEach(entity -> {
            JSONObject singleObject = (JSONObject) entity;
            if (!ObjectUtils.isEmpty(singleObject)) {
                Integer singleTicketId = singleObject.getInteger("single_ticket_id");
                Integer realNumber = ObjectUtils.isEmpty(getNumberByField(result, singleTicketId, "real_number")) ? Constant.INTEGER_0 : getNumberByField(result, singleTicketId, "real_number");
                Integer shamNumber = ObjectUtils.isEmpty(getNumberByField(result, singleTicketId, "sham_number")) ? Constant.INTEGER_10 : getNumberByField(result, singleTicketId, "sham_number");
                Integer praiseNumber = ObjectUtils.isEmpty(getNumberByField(result, singleTicketId, "praiseNumber")) ? Constant.INTEGER_0 : getNumberByField(result, singleTicketId, "praiseNumber");
                //真实参团人数&虚假参团人数
                singleObject.put("real_number", realNumber);
                //虚假参团人数
                singleObject.put("sham_number", shamNumber);
                //点赞数
                singleObject.put("praiseNumber", praiseNumber);
                if (!ObjectUtils.isEmpty(singleTicketId)) {
                    redisTemplate.opsForHash().put(mtiKey, String.valueOf(singleTicketId), singleObject.toJSONString());
                    redisTemplate.expire(mtiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                }
                //缓存有龙券详情
                Integer isYiulong = singleObject.getInteger("is_yiulong");
                if (!ObjectUtils.isEmpty(isYiulong) && Constant.INTEGER_1.equals(isYiulong)) {
                    String skuContent = WxInfoUtil.getSkuContent(String.valueOf(singleTicketId));
                    if (!ObjectUtils.isEmpty(skuContent)) {
                        redisTemplate.opsForHash().put(yltiKey, String.valueOf(singleTicketId), skuContent);
                        redisTemplate.expire(yltiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                    }
                }
            }
        });
    }

    /**
     * 获取参团人数
     *
     * @param result
     * @param singleTicketId
     * @param field
     * @return
     */
    private Integer getNumberByField(Map<String, String> result, Integer singleTicketId, String field) {
        AtomicReference<Integer> num = new AtomicReference<>();
        result.entrySet().forEach(entity -> {
            String key = entity.getKey();
            if (!ObjectUtils.isEmpty(key) && key.equals(String.valueOf(singleTicketId))) {
                String value = entity.getValue();
                if (!ObjectUtils.isEmpty(value) && JsonUtil.isJSON(value)) {
                    JSONObject jsonObject = JSONObject.parseObject(value);
                    String oldValue = jsonObject.getString(field);
                    if (!ObjectUtils.isEmpty(jsonObject) && !ObjectUtils.isEmpty(oldValue)) {
                        num.set(Integer.valueOf(oldValue));
                    }
                }
            }
        });
        return num.get();
    }

    /**
     * 打卡券
     *
     * @param coupon
     * @param mtiKey
     */
    private void prehearCouponInfo(JSONArray coupon, String mtiKey) {
        //增
        coupon.forEach(entity -> {
            JSONObject singleObject = (JSONObject) entity;
            if (!ObjectUtils.isEmpty(singleObject)) {
                Integer singleTicketId = singleObject.getInteger("single_ticket_id");
                if (!ObjectUtils.isEmpty(singleTicketId)) {
                    redisTemplate.opsForHash().put(mtiKey, String.valueOf(singleTicketId), singleObject.toJSONString());
                    redisTemplate.expire(mtiKey, RedisConstant.TOKEN_EXPIRY_JUNE, TimeUnit.MILLISECONDS);
                }
            }
        });
    }


    /**
     * 爆品列表数据更新
     */
    public void preheatAllPromotionTickets() {
        StopWatch sw = new StopWatch("爆品列表——集团缓存刷新");
        sw.start();
        List<Mall> mallList = mallService.list();
        mallList.forEach(entity -> {
            //商场爆款信息预热
            preheatPromotionTicket(entity.getOmsCode());
        });
        sw.stop();
        LOGGER.info(sw.toString());
    }

    /**
     * 获取券列表
     * --单个团获取
     *
     * @param omsCode
     * @param source
     * @param groupId
     * @return
     */
    public List<ActGroupTicketV2> getGroupTickets(String omsCode, String source, String groupId) {
        return getGroupTickets(omsCode, source, groupId, null);
    }

    /**
     * 获取券列表
     * --批量团获取
     *
     * @param omsCode
     * @param source
     * @param groupId
     * @param groupList 团Id集合 返回的对象中会多出img_arr,生成的缓存为groupId -1
     * @return
     */
    public List<ActGroupTicketV2> getGroupTickets(String omsCode, String source, String groupId, List<Integer> groupList) {
        Act act = ActFactory.create(source);
        String actId = act.getPosterId();
        String actKey = ActConstant.IMP_WX + actId + ActConstant.ACT_GROUP + omsCode;
        String groupIds = null;
        if (!ObjectUtils.isEmpty(groupList)) {
            groupId = "-1";
            StringBuilder builder = new StringBuilder();
            builder.append(groupList.get(0));
            for (int i = 1; i < groupList.size(); i++) {
                builder.append(",").append(groupList.get(i));
            }
            groupIds = builder.toString();
        }
        String result = (String) redisTemplate.opsForHash().get(actKey, groupId);
        List<ActGroupTicketV2> actGroupTicketV2s = new ArrayList<>();
        JSONObject actList;
        if (ObjectUtils.isEmpty(result)) {
            actList = WxInfoUtil.getGroupListV2(omsCode, groupId, actId, null, groupIds);
        } else {
            actList = JSONObject.parseObject(result);
        }

        if (!ObjectUtils.isEmpty(actList)) {
            String dataMap = actList.getString("single");
            if (!ObjectUtils.isEmpty(dataMap)) {
                actGroupTicketV2s = JSONObject.parseArray(dataMap, ActGroupTicketV2.class);
            }
        }
        for (ActGroupTicketV2 actGroupTicketV2 : actGroupTicketV2s) {
            //转换价格
            actGroupTicketV2.setViewListPrice(PriceUtil.formatPrice(actGroupTicketV2.getListPrice() + ""));
            actGroupTicketV2.setViewVipPrice(PriceUtil.formatPrice(actGroupTicketV2.getVipPrice() + ""));
            actGroupTicketV2.setViewRetailPrice(PriceUtil.formatPrice(actGroupTicketV2.getRetailPrice() + ""));
            //设置参团人数
            actGroupTicketV2.setGroupNumber(getGroupNumber(String.valueOf(actGroupTicketV2.getSingleTicketId()), source));
        }

        return actGroupTicketV2s;
    }

    /**
     * 获取参团人数
     *
     * @param ticketId
     * @param source
     * @return
     */
    public int getGroupNumber(String ticketId, String source) {
        String trnKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.KEY_T_R_N;
        String realField = ticketId.toString();
        String realNumber = (String) redisTemplate.opsForHash().get(trnKey, realField);
        String tsnKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.KEY_T_S_N;
        String shameField = ticketId.toString();
        String shameNumber = (String) redisTemplate.opsForHash().get(tsnKey, shameField);
        int realNum = 0;
        int shameNum = 0;
        if (!ObjectUtils.isEmpty(realNumber)) {
            realNum = Integer.parseInt(realNumber);
        }
        if (!ObjectUtils.isEmpty(shameNumber)) {
            shameNum = Integer.parseInt(shameNumber);
        }
        return realNum + shameNum;
    }

    public List<Mall> listMallByAct(String act) {
        String key = CacheConstant.CACHE_KEY_MALL_LIST_ACT + act;
        String body = stringRedisTemplate.opsForValue().get(key);
        List<Mall> result = Lists.newArrayList();
        if (!ObjectUtils.isEmpty(body)) {
            result = JSON.parseArray(body, Mall.class);
        }
        if (result == null || result.size() == 0) {
            // 从数据库中获取商场
            result = mallMybatisMapper.listMallByAct(act);
            if (result != null && result.size() > 0) {
                stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(result), CacheConstant.CACHE_EXPIRE_MALL_LIST);
            }
        }
        return result;
    }


    public Map<String, List<ActGroupTicketV2>> getAllDrawTicketInfo(String omsCode, String source, String groupId) {
        List<ActGroupTicketV2> actAllTicketV2s = getGroupTickets(omsCode, source, groupId);
        Map<String, List<ActGroupTicketV2>> res = new HashMap<>();
        for (ActGroupTicketV2 e : actAllTicketV2s) {
            List<ActGroupTicketV2> gradeList = res.get(e.getGrade());
            if (ObjectUtils.isEmpty(gradeList)) {
                gradeList = new ArrayList<>();
                res.put(e.getGrade(), gradeList);
            }
            gradeList.add(e);
        }

        res.forEach((k, v) -> {
            v.sort((e1, e2) -> {
                BigDecimal e1SortFlag = e1.getTicketTypeId().equals(1) ? e1.getCashAmt() : new BigDecimal(e1.getFullcutCutAmt());
                BigDecimal e2SortFlag = e2.getTicketTypeId().equals(1) ? e2.getCashAmt() : new BigDecimal(e2.getFullcutCutAmt());
                return e1SortFlag.subtract(e2SortFlag).intValue();
            });
        });
        return res;
    }


}

