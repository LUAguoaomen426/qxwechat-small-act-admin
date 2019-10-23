package com.red.star.macalline.act.admin.constant;

/**
 * @author xulonglong
 * @date 2018/1/29
 */
public final class RedisConstant {

    /**
     * websocket
     */
    public static final String WEBSOCKET_LISTENER = "WebSocket_Listener";

    public static final String SAVE_MESSAGE_LISTENER = "SaveMessage_Listener";

    /**
     * weixin
     */
    public static final Integer REDIS_DB_USER_SESSION = 1;

    public static final String ACCESS_TOKEN = "Access_Token";
    /**
     * 秒为单位  1小时过期
     */
    public static final Integer TOKEN_EXPIRY = 3600;

    public static final String TICKET = "Ticket";

    public static final String CODEURL = "Code_Url";

    public static final String WXCODEURL = "Wx_Code_Url";

    public static final String WXMESSAGEURL = "Wx_Message_Url";

    public static final Integer REDIS_DB_0 = 0;

    public static final Integer REDIS_DB_3 = 3;

    public static final String KEY_LOGOUT_FANS = "logout_passive_fans";

    public static final String KEY_SYNC_EMPLOYEE = "sync_employee";

    public static final String KEY_UNBIND_EMPLOYEE = "unBind_employee";

    public static final String KEY_WXUNBIND_EMPLOYEE = "wxUnBind_employee";

    public static final String KEY_BIND_EMPLOYEE = "bind_employee";

    public static final String KEY_VOICE_VIRTUAL = "voice_virtual_numbers";

    public static final String KEY_CRM_CS = "crm_cs_";

    /**
     * 24小时
     */
    public static final Integer TOKEN_EXPIRY_86400 = 24 * 60 * 60;

    public static final String KEY_VIRTUAL_NUMBER_SET = "virtual_number_set";

    public static final String KEY_CITY = "city:";

    public static final String KEY_CITY_EXTEND = "c_e_i:";

    public static final String UNBIND_EMPLOYEE_MSG_SUCCESS = "您的导购身份已被店长解绑，如有问题请联系店长。";

    public static final Integer REDIS_DB_USER_SESSION_3 = 3;

    /**
     * 团券信息
     */


    public static final String KEY_GROUP_TICKET = "june_m_g_t_i:";


    public static final String KEY_GROUP = "group_";

    public static final String KEY_ORDER = "imp:act:sorder:";

    public static final String KEY_TICKET = "june_m_t_i:";

    public static final Long TOKEN_EXPIRY_JUNE = 31 * 24 * 60 * 60 * 1000L;

    public static final String KEY_SIGN = "hsign:";

    public static final String KEY_HTICKET = "hticket:";

    public static final String KEY_USE = "act:";

    /**
     * 红包雨奖品数量
     */
    public static final String KEY_PRIZE = "june_prize:";

    public static final String KEY_DRAW = "imp:act:h:june_draw";

    public static final Integer PRAISE_NUMBER51 = 51;

    public static final Integer PRAISE_NUMBER151 = 151;

    public static final Integer PRAISE_NUMBER351 = 351;

    public static final Integer PRAISE_NUMBER551 = 551;

    public static final Long TOKEN_EXPIRY_MAY = 31 * 24 * 60 * 60 * 1000L;

    public static final String KEY_TICKETCOUNT = "imp:wx:promotion_ticket_takecount_";

    public static final String KEY_TICKETOTAL = "imp:wx:promotion_ticket_detail_";

    public static final String KEY_PRIZE_TICKET_ID = "imp:act:h:june_draw_ticketid";

    public static final String KEY_PRIZE_TICKET_MONEY="imp:act:h:june_draw_ticketMoney";
    //是否打卡
    public static final String KEY_ISCARD = "imp:act:hiscard:";

    //是否领某券
    public static final String KEY_HASTICKET = "imp:act:shasticket:";

    //十一月key
    public static final String KEY_NOVEMBER_PRE = "imp:wap:november:";

    /**
     * Redis缓存key前缀: 会员信息更新
     */
    public static final String REDIS_KEY_PREFIX_SYNC_VIP = "sync-vip-open-id:";

    /**
     * Redis缓存key前缀: 在活动里注册
     */
    public static final String REDIS_KEY_PREFIX_REGIST = "regist-open-id:";
}
