package com.red.star.macalline.act.admin.core.act;

import com.red.star.macalline.act.admin.constant.ResponseConstant;
import com.red.star.macalline.act.admin.core.exception.CustomException;
import com.red.star.macalline.act.admin.domain.vo.ActResponse;
import com.red.star.macalline.act.admin.domain.vo.SysParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @ProjectName: qxwechat-small-act
 * @Package: com.red.star.macalline.act.core.act
 * @Description:
 * @Author: AMGuo
 * @CreateDate: 2019-06-03 15:53
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@Component
public class ActFactory implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActFactory.class);
    private static ApplicationContext applicationContext;

    /**
     * 通过活动标识返回当前活动
     *
     * @param source
     * @return
     */
    public static Act create(String source) {
        if (ObjectUtils.isEmpty(source)) {
            LOGGER.error("source为空");
            ActResponse response = new ActResponse.Builder().code(ResponseConstant.RESPONSE_CODE_TRANSACTION).message("source为空").build();
            throw new CustomException(response);
        }
        switch (source.trim()) {
            case JulyAct.sourceJuly:
                return new JulyAct();
            case HotAct.sourceHot:
                return new HotAct();
            case HotProductAct.sourceJuly:
                return new HotProductAct();
            case QmxyTestAct.sourceJuly:
                return new QmxyTestAct();
            case AugustAct.sourceAugust:
                return new AugustAct();
            case GuideAct.source:
                return new GuideAct();
            case XzbdAct.source:
                return new XzbdAct();
            case SepAct.sourceSep:
                return new SepAct();
            default:
                return ((ActConfigUtil) ActFactory.applicationContext.getBean("actConfigUtil")).getConfiguredActBySource(source);
        }
    }


    /**
     * 根据微信活动标识返回当前活动
     *
     * @param posterId
     * @return
     */
    public static Act deCreate(String posterId) {
        if (ObjectUtils.isEmpty(posterId)) {
            LOGGER.error("posterId为空");
            ActResponse response = new ActResponse.Builder().code(ResponseConstant.RESPONSE_CODE_TRANSACTION).message("source为空").build();
            throw new CustomException(response);
        }
        if (SysParam.ACT_CODE_JULY.equals(posterId.trim())) {
            //七月活动
            return new JulyAct();
        } else if (SysParam.ACT_CODE.equals(posterId.trim())) {
            //更多爆品活动
            return new HotAct();
        } else if (SysParam.ACT_CODE_HOT_PRODUCT.equals(posterId.trim())) {
            //爆品详情活动
            return new HotProductAct();
        } else if ("92".equals(posterId.trim())) {
            //全民营销测试页面  SysParam.ACT_CODE_QMXY_TEST
            return new QmxyTestAct();
        } else if (SysParam.ACT_CODE_AUGUST.equals(posterId.trim())) {
            //八月
            return new AugustAct();
        } else if (SysParam.ACT_CODE_GUIDE.equals(posterId.trim())) {
            //套房指南
            return new GuideAct();
        } else if (SysParam.ACT_CODE_XZBD.equals(posterId.trim())) {
            //星造宝典
            return new XzbdAct();
        } else if (SysParam.ACT_CODE_SEP.equals(posterId.trim())) {
            return new SepAct();
        }
        return ((ActConfigUtil) ActFactory.applicationContext.getBean("actConfigUtil")).getConfiguredActByPosterId(posterId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ObjectUtils.isEmpty(ActFactory.applicationContext)) {
            ActFactory.applicationContext = applicationContext;
        }
    }
}