package com.red.star.macalline.act.admin.domain.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.red.star.macalline.act.admin.domain.WapActDraw;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Date 2019/8/9 14:18
 * @Created by Akari
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrawInfoBO {
    private String drawId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date drawStartTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date drawEndTime;
    private int prizeCount;
    private List<String> prizeCode;
    private DrawElement[][] martix;

    public DrawInfoBO(String drawId, Date drawStarTime, Date drawEndTime, int prizeCount, List<String> prizeCode) {
        this.drawId = drawId;
        this.drawStartTime = drawStarTime;
        this.drawEndTime = drawEndTime;
        this.prizeCount = prizeCount;
        this.prizeCode = prizeCode;
    }

    public DrawInfoBO(WapActDraw actDraw) {
        this.drawId = actDraw.getDrawId();
        this.drawStartTime = actDraw.getStartTime();
        this.drawEndTime = actDraw.getEndTime();
        this.prizeCount = actDraw.getPrizeCount();
        this.prizeCode = JSON.parseArray(actDraw.getTicketId(), String.class);
        this.martix = JSONObject.parseObject(actDraw.getMartix(), DrawElement[][].class);
    }

    public WapActDraw convert2WapActDraw(String actCode) {
        WapActDraw wapActDraw = new WapActDraw();
        wapActDraw.setDrawId(this.getDrawId());
        wapActDraw.setActCode(actCode);
        wapActDraw.setEndTime(this.getDrawEndTime());
        wapActDraw.setStartTime(this.getDrawStartTime());
        wapActDraw.setPrizeCount(this.getPrizeCount());
        wapActDraw.setTicketId(JSON.toJSONString(this.getPrizeCode()));
        wapActDraw.setMartix(JSON.toJSONString(this.martix));
        return wapActDraw;
    }
}
