package com.red.star.macalline.act.admin.core.act;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.red.star.macalline.act.admin.domain.ActModule;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2019/8/26 17:47
 * @Created by Akari
 */
@Data
@NoArgsConstructor
public class ConfiguratedAct implements Act {

    private String posterId;
    private String channelId;
    private String source;
    private Map<String, Object> configData;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date actEndTime;
    private String homeUrl;

    public ConfiguratedAct(ActModule actModule) {
        this.posterId = actModule.getPosterId();
        this.channelId = actModule.getChannelId();
        this.source = actModule.getActCode();
        if (!ObjectUtils.isEmpty(actModule.getConfigData())) {
            this.configData = JSONObject.parseObject(actModule.getConfigData(), Map.class);
        }
        this.endTime = actModule.getEndTime();
        this.actEndTime = actModule.getActEndTime();
        this.homeUrl = actModule.getLinkUrl();
    }

    @Override
    public String getPosterId() {
        return this.posterId;
    }

    @Override
    public String getChannelId() {
        return this.channelId;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public String findNameByGroupId(Integer groupId) {
        Object groupName = getConfig("GROUP_NAME_" + groupId);
        if (ObjectUtils.isEmpty(groupName)) {
            return null;
        }
        return groupName.toString();
    }

    @Override
    public Date getEndTime() {
        return this.endTime;
    }

    @Override
    public Integer getLuckyMaximumNum() {
        Object luckMaximumNum = getConfig("lucky_maximumnum");
        if (ObjectUtils.isEmpty(luckMaximumNum)) {
            return null;
        }
        if (!(luckMaximumNum instanceof Integer)) {
            throw new RuntimeException("luckMaximumNum 类型不为Integer");
        }
        return (Integer) luckMaximumNum;
    }

    @Override
    public Integer getBoostMaximumNum() {
        Object boostMaximumNum = getConfig("boost_maximumnum");
        if (ObjectUtils.isEmpty(boostMaximumNum)) {
            return null;
        }
        if (!(boostMaximumNum instanceof Integer)) {
            throw new RuntimeException("boostMaximumNum 类型不为Integer");
        }
        return (Integer) boostMaximumNum;
    }

    @Override
    public Object getConfig(String configKey) {
        if (ObjectUtils.isEmpty(this.configData)) {
            return null;
        }
        return this.configData.get(configKey);
    }

}
