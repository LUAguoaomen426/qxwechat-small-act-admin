package com.red.star.macalline.act.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.red.star.macalline.act.admin.constant.CacheConstant;
import com.red.star.macalline.act.admin.core.act.ActFactory;
import com.red.star.macalline.act.admin.domain.WapActDraw;
import com.red.star.macalline.act.admin.domain.bo.DrawElement;
import com.red.star.macalline.act.admin.domain.bo.DrawInfoBO;
import com.red.star.macalline.act.admin.domain.bo.FlopBo;
import com.red.star.macalline.act.admin.domain.bo.LuckyBo;
import com.red.star.macalline.act.admin.domain.dto.BoostAwardDTO;
import com.red.star.macalline.act.admin.domain.vo.*;
import com.red.star.macalline.act.admin.mapper.ComMybatisMapper;
import com.red.star.macalline.act.admin.mapper.WapActDrawMapper;
import com.red.star.macalline.act.admin.util.DateNewUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: qxwechat-small-act-admin
 * @Package: com.red.star.macalline.act.admin.service
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-10-24 11:00
 * @Version: 1.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DrawService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private WapActDrawMapper wapActDrawMapper;

    @Resource
    private ComMybatisMapper comMybatisMapper;

    @Resource
    private ComService comService;

    /**
     * 获取抽奖信息详细信息
     *
     * @param actCode
     * @param drawID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public DrawVO drawInfo(String actCode, String drawID) {
        DrawInfoBO drawInfoBO = getDrawInfoWithMartix(actCode, drawID);
        if (ObjectUtils.isEmpty(drawInfoBO)) {
            return null;
        }
        Integer dayNum = (int) ((drawInfoBO.getDrawEndTime().getTime() - drawInfoBO.getDrawStartTime().getTime()) / (24 * 60 * 60 * 1000)) + 1;
        ArrayList<Map<String, Object>> tableDraw = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < dayNum; i++) {
            HashMap<String, Object> row = new HashMap<>();
            Calendar c = Calendar.getInstance();
            c.setTime(drawInfoBO.getDrawStartTime());
            c.add(Calendar.DAY_OF_MONTH, i);
            row.put("date", format.format(c.getTime()));
            for (int j = 0; j < drawInfoBO.getPrizeCount(); j++) {
                row.put(String.valueOf(j + 1), drawInfoBO.getMartix()[i][j]);
            }
            tableDraw.add(row);
        }
        DrawVO drawVO = new DrawVO(drawID, drawInfoBO.getDrawStartTime(), drawInfoBO.getDrawEndTime(), drawInfoBO.getPrizeCount(), drawInfoBO.getPrizeCode(), tableDraw);
        return drawVO;
    }

    /**
     * 查询奖项
     *
     * @param source
     * @param drawID
     * @return
     */
    public DrawInfoBO getDrawInfoWithMartix(String source, String drawID) {
        String drawKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_ACT_DRAW + drawID;
        String drawInfo = String.valueOf(stringRedisTemplate.opsForHash().get(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_INFO));
        DrawInfoBO drawInfoBO = JSONObject.parseObject(drawInfo, DrawInfoBO.class);
        if (ObjectUtils.isEmpty(drawInfo)) {
            drawInfoBO = getDrawInfoByMapper(source, drawID);
        }
        String hget = String.valueOf(stringRedisTemplate.opsForHash().get(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_MARTIX));
        DrawElement[][] martis = JSONObject.parseObject(hget, DrawElement[][].class);
        drawInfoBO.setMartix(martis);
        return drawInfoBO;
    }

    /**
     * 数据库中查询奖项
     *
     * @param source
     * @param drawID
     * @return
     */
    private DrawInfoBO getDrawInfoByMapper(String source, String drawID) {
        WapActDraw selectCondition = new WapActDraw();
        selectCondition.setActCode(source);
        selectCondition.setDrawId(drawID);
        WapActDraw actDraw = wapActDrawMapper.selectOne(new QueryWrapper<WapActDraw>().lambda().eq(WapActDraw::getActCode, selectCondition.getActCode()).eq(WapActDraw::getDrawId, selectCondition.getDrawId()));
        if (ObjectUtils.isEmpty(actDraw)) {
            return null;
        }
        DrawInfoBO drawInfoBO = new DrawInfoBO(actDraw);
        DrawElement[][] martix = drawInfoBO.getMartix();
        List<Map<String, String>> ticketNumList = new ArrayList<>();

        for (int r = 0; r < martix.length; r++) {
            //处理每行数据
            Calendar c = Calendar.getInstance();
            c.setTime(drawInfoBO.getDrawStartTime());
            c.add(Calendar.DAY_OF_MONTH, r);
            for (int l = 0; l < martix[r].length; l++) {
                //处理列数据
                //设置劵对应的每天的库存
                if (ticketNumList.size() < l + 1) {
                    ticketNumList.add(l, new HashMap<>());
                }
                Map<String, String> ticketDayNum = ticketNumList.get(l);
                ticketDayNum.put(DateNewUtil.formatDate(c.getTime()), String.valueOf(martix[r][l].getPrizeCount()));
            }
        }
        //redis分开存储减少查询量
        drawInfoBO.setMartix(null);
        String drawKey = CacheConstant.CACHE_KEY_PREFIX + source + CacheConstant.CACHE_KEY_ACT_DRAW + drawID;
        stringRedisTemplate.opsForHash().put(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_INFO, JSON.toJSONString(drawInfoBO));
        stringRedisTemplate.opsForHash().put(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_MARTIX, JSON.toJSONString(martix));
        stringRedisTemplate.opsForHash().put(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_TICKET_NUMS, JSON.toJSONString(ticketNumList));
        stringRedisTemplate.expire(drawKey, CacheConstant.CACHE_EXPIRE_TICKET, TimeUnit.MILLISECONDS);
        return drawInfoBO;
    }

    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public ActResponse newlyDrawInfo(String actCode, DrawVO drawVo) {
        WapActDraw selectInfo = new WapActDraw();
        selectInfo.setDrawId(drawVo.getId());
        selectInfo.setActCode(actCode);
        int i = wapActDrawMapper.selectCount(new QueryWrapper<WapActDraw>().lambda().eq(WapActDraw::getDrawId, drawVo.getId()).eq(WapActDraw::getActCode, selectInfo.getActCode()));
        if (i > 0) {
            return ActResponse.buildErrorResponse("已存在相同抽奖数据");
        }
        Map map = parseDrawVO(drawVo);
        DrawInfoBO drawInfoBO = (DrawInfoBO) map.get(CacheConstant.CACHE_KEY_ACT_DRAW_INFO);
        DrawElement[][] martix = (DrawElement[][]) map.get(CacheConstant.CACHE_KEY_ACT_DRAW_MARTIX);
        List<Map<String, String>> ticketNumList = (List<Map<String, String>>) map.get("ticketNums");

        String drawKey = CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_DRAW + drawVo.getId();
        stringRedisTemplate.opsForHash().put(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_INFO, JSON.toJSONString(drawInfoBO));
        stringRedisTemplate.opsForHash().put(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_MARTIX, JSON.toJSONString(martix));
        stringRedisTemplate.opsForHash().put(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_TICKET_NUMS, JSON.toJSONString(ticketNumList));
        stringRedisTemplate.expire(drawKey, CacheConstant.CACHE_EXPIRE_TICKET, TimeUnit.MILLISECONDS);
        for (int j = 0; j < drawVo.getPrizeCount(); j++) {
            stringRedisTemplate.opsForHash().putAll(drawKey + CacheConstant.CACHE_KEY_ACT_DRAW_TICKET_NUM + drawVo.getPrizeCode().get(j), ticketNumList.get(j));
            stringRedisTemplate.expire(drawKey, CacheConstant.CACHE_EXPIRE_TICKET, TimeUnit.MILLISECONDS);
        }
        drawInfoBO.setMartix(martix);
        wapActDrawMapper.insert(drawInfoBO.convert2WapActDraw(actCode));
        return ActResponse.buildSuccessResponse();
    }

    /**
     * 转换抽奖vo为bo
     *
     * @param drawVO
     * @return
     */
    private Map parseDrawVO(DrawVO drawVO) {
        Map res = new HashMap<String, Object>();
        //活动天数
        Integer dayNum = (int) ((drawVO.getDrawEndTime().getTime() - drawVO.getDrawStartTime().getTime()) / (24 * 60 * 60 * 1000)) + 1;
        DrawElement[][] martix = new DrawElement[dayNum][drawVO.getPrizeCount()];
        List<Map<String, Object>> tableDraw = drawVO.getTableDraw();
        BigDecimal hundred = new BigDecimal("100");

        List<Map<String, String>> ticketNumList = new ArrayList<>();

        for (int i = 0; i < dayNum; i++) {
            int beforeNum = 0;
            Calendar c = Calendar.getInstance();
            c.setTime(drawVO.getDrawStartTime());
            c.add(Calendar.DAY_OF_MONTH, i);
            //取得行数据
            Map<String, Object> row = tableDraw.get(i);
            for (int j = 0; j < drawVO.getPrizeCount(); j++) {
                //生成列数据
                DrawElement element = new DrawElement();
                Map eDate = (Map) row.get(String.valueOf(j + 1));
                element.setPrizeCount(Integer.parseInt(eDate.get("prizeCount").toString()));
                element.setPrizeProbability(eDate.get("prizeProbability").toString());
                //生成抽奖随机数范围
                Integer end = new BigDecimal(element.getPrizeProbability()).multiply(hundred).intValue();
                element.setDrawValueEnd(beforeNum + end);
                element.setDrawValueHead(beforeNum + 1);
                beforeNum += end;

                martix[i][j] = element;
                //设置劵对应的每天的库存
                Map<String, String> ticketDayNum = null;
                if (ticketNumList.size() < j + 1) {
                    ticketDayNum = new HashMap<>();
                    ticketNumList.add(j, ticketDayNum);
                }
                ticketDayNum = ticketNumList.get(j);
                ticketDayNum.put(DateNewUtil.formatDate(c.getTime()), String.valueOf(element.getPrizeCount()));
            }
        }

        DrawInfoBO drawInfoBO = new DrawInfoBO(drawVO.getId(), drawVO.getDrawStartTime(), drawVO.getDrawEndTime(), drawVO.getPrizeCount(), drawVO.getPrizeCode());
        res.put("info", drawInfoBO);
        res.put("martix", martix);
        res.put("ticketNums", ticketNumList);
        return res;
    }

    public ActResponse saveDrawInfo(String actCode, DrawVO drawVo) throws ParseException {
        DrawInfoBO oldDrawInfo = getDrawInfoWithMartix(actCode, drawVo.getId());
        if (ObjectUtils.isEmpty(oldDrawInfo)) {
            return ActResponse.buildErrorResponse("查询不到对应抽奖信息");
        }
        Date now = new Date();
        String drawKey = CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_DRAW + drawVo.getId();

        Map map = parseDrawVO(drawVo);
        DrawInfoBO drawInfoBO = (DrawInfoBO) map.get(CacheConstant.CACHE_KEY_ACT_DRAW_INFO);
        DrawElement[][] martix = (DrawElement[][]) map.get(CacheConstant.CACHE_KEY_ACT_DRAW_MARTIX);
        DrawElement[][] oldMartix = oldDrawInfo.getMartix();
        DrawElement zeroElement = newZeroElement();
        Date newStartDate = drawInfoBO.getDrawStartTime();
        Integer dayDiff = (int) (now.getTime() - newStartDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
        Integer startDiff = (int) (newStartDate.getTime() - oldDrawInfo.getDrawStartTime().getTime()) / (24 * 60 * 60 * 1000);
        //处理已经进行过的矩阵值，还原原值
        for (int r = 0; r < dayDiff; r++) {
            for (int l = 0; l < martix[r].length; l++) {
                martix[r][l] = zeroElement;
                if ((r + startDiff >= 0) && (r + startDiff) < oldMartix.length) {
                    martix[r][l] = oldMartix[r + startDiff][l];
                }
            }
        }
        List<Map<String, String>> newTicketNumList = (List<Map<String, String>>) map.get("ticketNums");
        for (int i = 0; i < drawInfoBO.getPrizeCount(); i++) {
            //对每一个奖品排除掉已经进行过或正在进行的每天数量
            Map<String, String> res = newTicketNumList.get(i);
            HashMap<String, String> needChange = new HashMap<>();
            for (Map.Entry<String, String> e : res.entrySet()) {
                String date = e.getKey();
                SimpleDateFormat drawDateFormate = new SimpleDateFormat("yyy-MM-dd");
                Date drawDate = drawDateFormate.parse(date);
                if (now.compareTo(drawDate) == -1) {
                    //当前时间还未开始抽奖，每天库存数量保留
                    needChange.put(e.getKey(), e.getValue());
                }
            }
            stringRedisTemplate.opsForHash().putAll(drawKey + CacheConstant.CACHE_KEY_ACT_DRAW_TICKET_NUM + drawVo.getPrizeCode().get(i), needChange);
        }
        stringRedisTemplate.opsForHash().put(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_INFO, JSON.toJSONString(drawInfoBO));
        stringRedisTemplate.opsForHash().put(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_MARTIX, JSON.toJSONString(martix));
        stringRedisTemplate.opsForHash().put(drawKey, CacheConstant.CACHE_KEY_ACT_DRAW_TICKET_NUMS, JSON.toJSONString(newTicketNumList));

        drawInfoBO.setMartix(martix);
        WapActDraw wapActDraw = drawInfoBO.convert2WapActDraw(actCode);
        wapActDrawMapper.update(wapActDraw, new UpdateWrapper<WapActDraw>().lambda().eq(WapActDraw::getDrawId, wapActDraw.getDrawId()).eq(WapActDraw::getActCode, wapActDraw.getActCode()));
        return ActResponse.buildSuccessResponse();
    }

    private DrawElement newZeroElement() {
        DrawElement drawElement = new DrawElement();
        drawElement.setPrizeProbability("0");
        drawElement.setPrizeCount(0);
        drawElement.setDrawValueHead(1);
        drawElement.setDrawValueEnd(0);
        return drawElement;
    }

    public ActResponse getDrawList(String actCode) {
        if (ObjectUtils.isEmpty(actCode)) {
            return ActResponse.buildErrorResponse("actCode is empty");
        }
        WapActDraw selectInfo = new WapActDraw();
        selectInfo.setActCode(actCode);
        List<WapActDraw> select = wapActDrawMapper.selectList(new QueryWrapper<WapActDraw>().lambda().eq(WapActDraw::getActCode, selectInfo.getActCode()));
        return ActResponse.buildSuccessResponse(select);
    }

    @Transactional(readOnly = false)
    public ActResponse deleteDraw(String actCode, String drawId) {
        WapActDraw selectInfo = new WapActDraw();
        selectInfo.setActCode(actCode);
        selectInfo.setDrawId(drawId);
        WapActDraw actDraw = wapActDrawMapper.selectOne(new QueryWrapper<WapActDraw>().lambda().eq(WapActDraw::getActCode, selectInfo.getActCode()).eq(WapActDraw::getDrawId, selectInfo.getDrawId()));
        if (ObjectUtils.isEmpty(actDraw)) {
            return ActResponse.buildErrorResponse("draw数据不存在");
        }
        DrawInfoBO drawInfoBO = new DrawInfoBO(actDraw);
        String drawKey = CacheConstant.CACHE_KEY_PREFIX + actCode + CacheConstant.CACHE_KEY_ACT_DRAW + drawId;
        stringRedisTemplate.delete(drawKey);
        for (int j = 0; j < actDraw.getPrizeCount(); j++) {
            stringRedisTemplate.delete(drawKey + CacheConstant.CACHE_KEY_ACT_DRAW_TICKET_NUM + drawInfoBO.getPrizeCode().get(j));
        }
        wapActDrawMapper.delete(new QueryWrapper<WapActDraw>().lambda().eq(WapActDraw::getId, actDraw.getId()));
        return ActResponse.buildSuccessResponse("成功");
    }

    /**
     * 返回翻牌抽奖数据
     *
     * @param flopBo
     * @return
     */
    public List<FlopVo> analysisFlopData(FlopBo flopBo) {
        List<FlopVo> flopVos = Lists.newArrayList();
        FlopVo flopVo = new FlopVo();
        flopVos.add(flopVo);
        List<FlopVo> flopVoData = comMybatisMapper.analysisFlopData(flopBo);
        for (FlopVo entity : flopVoData) {
            flopVo.setTotalNum((ObjectUtils.isEmpty(entity.getTotalNum()) ? 0 : entity.getTotalNum()) + (ObjectUtils.isEmpty(flopVo.getTotalNum()) ? 0 : flopVo.getTotalNum()));
            flopVo.setLuckyNum((ObjectUtils.isEmpty(entity.getLuckyNum()) ? 0 : entity.getLuckyNum()) + (ObjectUtils.isEmpty(flopVo.getLuckyNum()) ? 0 : flopVo.getLuckyNum()));
            flopVo.setPeopleNum((ObjectUtils.isEmpty(entity.getPeopleNum()) ? 0 : entity.getPeopleNum()) + (ObjectUtils.isEmpty(flopVo.getPeopleNum()) ? 0 : flopVo.getPeopleNum()));
        }
        flopVo.setDate("总计");
        flopVos.addAll(flopVoData);
        return flopVos;
    }

    /**
     * 中奖数据
     *
     * @param luckyBo
     * @return
     */
    public LuckyData analysisLuckyDataOctober(LuckyBo luckyBo) {
        List<LuckyVo> luckyVoList = comMybatisMapper.analysisLuckyData(luckyBo);
        List<String> gradeMap = Lists.newArrayList();
        List<LuckyVo> luckyList = Lists.newArrayList();
        String awardKey1 = CacheConstant.CACHE_KEY_PREFIX + luckyBo.getSource() + CacheConstant.CACHE_KEY_BOOST_AWARD + "1009" + CacheConstant.CACHE_KEY_COLON + 1;
        String awardKey2 = CacheConstant.CACHE_KEY_PREFIX + luckyBo.getSource() + CacheConstant.CACHE_KEY_BOOST_AWARD + "1009" + CacheConstant.CACHE_KEY_COLON + 2;
        String awardKey3 = CacheConstant.CACHE_KEY_PREFIX + luckyBo.getSource() + CacheConstant.CACHE_KEY_BOOST_AWARD + "1009" + CacheConstant.CACHE_KEY_COLON + 3;
        String awardKey4 = CacheConstant.CACHE_KEY_PREFIX + luckyBo.getSource() + CacheConstant.CACHE_KEY_BOOST_AWARD + "1009" + CacheConstant.CACHE_KEY_COLON + 4;
        String awardKey5 = CacheConstant.CACHE_KEY_PREFIX + luckyBo.getSource() + CacheConstant.CACHE_KEY_BOOST_AWARD + "1009" + CacheConstant.CACHE_KEY_COLON + 5;
        String awardKey6 = CacheConstant.CACHE_KEY_PREFIX + luckyBo.getSource() + CacheConstant.CACHE_KEY_BOOST_AWARD + "1009" + CacheConstant.CACHE_KEY_COLON + 6;
        String awardKey7 = CacheConstant.CACHE_KEY_PREFIX + luckyBo.getSource() + CacheConstant.CACHE_KEY_BOOST_AWARD + "1009" + CacheConstant.CACHE_KEY_COLON + 7;
        BoostAwardDTO boostAwardDTO1 = (BoostAwardDTO) redisTemplate.opsForValue().get(awardKey1);
        BoostAwardDTO boostAwardDTO2 = (BoostAwardDTO) redisTemplate.opsForValue().get(awardKey2);
        BoostAwardDTO boostAwardDTO3 = (BoostAwardDTO) redisTemplate.opsForValue().get(awardKey3);
        BoostAwardDTO boostAwardDTO4 = (BoostAwardDTO) redisTemplate.opsForValue().get(awardKey4);
        BoostAwardDTO boostAwardDTO5 = (BoostAwardDTO) redisTemplate.opsForValue().get(awardKey5);
        BoostAwardDTO boostAwardDTO6 = (BoostAwardDTO) redisTemplate.opsForValue().get(awardKey6);
        BoostAwardDTO boostAwardDTO7 = (BoostAwardDTO) redisTemplate.opsForValue().get(awardKey7);
        //奖品
        gradeMap.add(ObjectUtils.isEmpty(boostAwardDTO1) ? "1" : boostAwardDTO1.getName());
        gradeMap.add(ObjectUtils.isEmpty(boostAwardDTO2) ? "2" : boostAwardDTO2.getName());
        gradeMap.add(ObjectUtils.isEmpty(boostAwardDTO3) ? "3" : boostAwardDTO3.getName());
        gradeMap.add(ObjectUtils.isEmpty(boostAwardDTO4) ? "4" : boostAwardDTO4.getName());
        gradeMap.add(ObjectUtils.isEmpty(boostAwardDTO5) ? "5" : boostAwardDTO5.getName());
        gradeMap.add(ObjectUtils.isEmpty(boostAwardDTO6) ? "6" : boostAwardDTO6.getName());
        gradeMap.add(ObjectUtils.isEmpty(boostAwardDTO7) ? "7" : boostAwardDTO7.getName());
        luckyVoList.forEach(entity -> {
            Boolean mallFlag = judgeMall(entity.getOmsCode());
            entity.setMallFlag(mallFlag);
            switch (entity.getGrade()) {
                case 1:
                    entity.setGradeName(ObjectUtils.isEmpty(boostAwardDTO1) ? String.valueOf(entity.getGrade()) : boostAwardDTO1.getName());
                    break;
                case 2:
                    entity.setGradeName(ObjectUtils.isEmpty(boostAwardDTO2) ? String.valueOf(entity.getGrade()) : boostAwardDTO2.getName());
                    break;
                case 3:
                    entity.setGradeName(ObjectUtils.isEmpty(boostAwardDTO3) ? String.valueOf(entity.getGrade()) : boostAwardDTO3.getName());
                    break;
                case 4:
                    entity.setGradeName(ObjectUtils.isEmpty(boostAwardDTO4) ? String.valueOf(entity.getGrade()) : boostAwardDTO4.getName());
                    break;
                case 5:
                    entity.setGradeName(ObjectUtils.isEmpty(boostAwardDTO5) ? String.valueOf(entity.getGrade()) : boostAwardDTO5.getName());
                    break;
                case 6:
                    entity.setGradeName(ObjectUtils.isEmpty(boostAwardDTO6) ? String.valueOf(entity.getGrade()) : boostAwardDTO6.getName());
                    break;
                case 7:
                    entity.setGradeName(ObjectUtils.isEmpty(boostAwardDTO7) ? String.valueOf(entity.getGrade()) : boostAwardDTO7.getName());
                    break;
            }
            if (null != luckyBo.getMallFlag()) {
                if (mallFlag.equals(luckyBo.getMallFlag())) {
                    luckyList.add(entity);
                }
            } else {
                luckyList.add(entity);
            }
        });
        return new LuckyData(luckyList, gradeMap);
    }

    /**
     * 区分当前商场是否参与excel中数量设置
     * true 参与
     *
     * @param omsCode
     * @return
     */
    public Boolean judgeMall(String omsCode) {
        String malls = "1245,1072,1073,1074,1071,1101,1206,1246,1128,1202,1207,1201,1205,1077,1215,1227,1063,1138,1271,1244,1037,1011,1283,1126,1026,1294,1025,1022,1217,1322,1118,1115,1061,1209,1062,1052,1141,1230,1048,1287,1043,1174,1187,1164,1001,1009,1005,1119,1169,1088,3014,1067,1272,1190,1084,1097,1010,1015,1099,1098,1121,1330,1263,1275,4897,1220,1252,1075,1065,1066,1308";
        List<String> mallList = Lists.newArrayList(malls.split(","));
        Boolean flag = false;
        if (!ObjectUtils.isEmpty(omsCode)) {
            flag = mallList.contains(omsCode);
        }
        return flag;
    }

    public LuckyData analysisLuckyData(LuckyBo luckyBo) {
        if (luckyBo.getSource().equals("october")) {
            //十月份大促-查询不同
            return analysisLuckyDataOctober(luckyBo);
        }
        if (luckyBo.getSource().equals("november")) {
            return analysisLuckyDataNovember(luckyBo);
        }

        List<WapActDraw> select = wapActDrawMapper.selectList(new QueryWrapper<WapActDraw>().lambda().eq(WapActDraw::getActCode, luckyBo.getSource()));
        if (select.size() < 1) {
            return new LuckyData();
        }
        List<String> gradeMap = Lists.newArrayList();
        gradeMap.add("100元免单券");
        gradeMap.add("200元免单券");
        gradeMap.add("666元免单券");
        gradeMap.add("9999元免单券");
        //修改查询
        if (!ObjectUtils.isEmpty(luckyBo.getGrade())) {
            switch (luckyBo.getGrade()) {
                case 1:
                    luckyBo.setGrade(100);
                    break;
                case 2:
                    luckyBo.setGrade(200);
                    break;
                case 3:
                    luckyBo.setGrade(666);
                    break;
                case 4:
                    luckyBo.setGrade(9999);
                    break;
            }
        }
        List<LuckyVo> luckyList = Lists.newArrayList();
        List<LuckyVo> luckyVoList = comMybatisMapper.analysisLuckyData(luckyBo);
        luckyVoList.forEach(e -> {
            Boolean mallFlag = judgeMall(e.getOmsCode());
            e.setMallFlag(mallFlag);
            e.setGradeName(e.getGrade()+"元免单券");
            if (null != luckyBo.getMallFlag()) {
                if (mallFlag.equals(luckyBo.getMallFlag())) {
                    luckyList.add(e);
                }
            } else {
                luckyList.add(e);
            }
        });
        return new LuckyData(luckyList, gradeMap);
    }

    private LuckyData analysisLuckyDataNovember(LuckyBo luckyBo) {
        List<LuckyVo> luckyList = Lists.newArrayList();
        //十一月大促抽奖数据处理
        //获取所有券信息
        Object drawOmsCode1 = ActFactory.create(luckyBo.getSource()).getConfig("DRAW_OMS_CODE");
        String drawOmsCode = ObjectUtils.isEmpty(drawOmsCode1) ? "" : drawOmsCode1.toString();
        List<String> gradeMap = Lists.newArrayList();
        gradeMap.add("普通红包-100");
        gradeMap.add("普通红包-200");
        gradeMap.add("普通红包-300");
        gradeMap.add("普通红包-500");
        gradeMap.add("大额红包-511");
        gradeMap.add("大额红包-1111");
        gradeMap.add("超额红包-777");
        gradeMap.add("超额红包-11111");
        gradeMap.add("巨额红包-2111");
        gradeMap.add("巨额红包-49999");
        //修改查询
        if (!ObjectUtils.isEmpty(luckyBo.getGrade())) {
            String s = gradeMap.get(luckyBo.getGrade() - 1);
            if (s.startsWith("普通")) {
                luckyBo.setType("lucky1");
                luckyBo.setGrade(luckyBo.getGrade() - 1);
            }
            if (s.startsWith("大额")) {
                luckyBo.setType("lucky2");
                luckyBo.setGrade(luckyBo.getGrade() - 5);
            }
            if (s.startsWith("超额")) {
                luckyBo.setType("lucky3");
                luckyBo.setGrade(luckyBo.getGrade() - 7);
            }
            if (s.startsWith("巨额")) {
                luckyBo.setType("lucky4");
                luckyBo.setGrade(luckyBo.getGrade() - 9);
            }
        }
        //得到查询结果
        List<LuckyVo> luckyVoList = comMybatisMapper.analysisLuckyData(luckyBo);
        luckyVoList.forEach(e -> {
            Boolean mallFlag = judgeMall(e.getOmsCode());
            e.setMallFlag(mallFlag);
            Integer deviationVal = 1;
            if (e.getType().length() > 5) {
                Integer groupId = Integer.valueOf(e.getType().substring(5));
                deviationVal = groupId == 1 ? 0 : (groupId - 2) * 2 + 4;
            }
            e.setGradeName(gradeMap.get(deviationVal + e.getGrade()));
            if (null != luckyBo.getMallFlag()) {
                if (mallFlag.equals(luckyBo.getMallFlag())) {
                    luckyList.add(e);
                }
            } else {
                luckyList.add(e);
            }
        });
        return new LuckyData(luckyList, gradeMap);
    }


}
