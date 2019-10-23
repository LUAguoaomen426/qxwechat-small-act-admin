package com.red.star.macalline.act.admin.constant;


/**
 * 圣诞&元旦活动相关常量
 *
 * @author nofish.yan@gmail.com
 * @date 2018/11/27.
 */
public class ActConstant {

    /**
     * 打开方式(渠道): 微信内
     */
    public static final int CHANNEL_TYPE_WX = 1;

    /**
     * 打开方式(渠道): 小程序
     */
    public static final int CHANNEL_TYPE_XCX = 2;

    /**
     * 打开方式(渠道): 外部H5
     */
    public static final int CHANNEL_TYPE_SA = 3;

    /**
     * 参数: 小程序标识
     */
    public static final String REQUEST_PARAM_KEY_XCX = "xcx";

    /**
     * 参数: 用户标识
     */
    public static final String REQUEST_PARAM_KEY_UID = "uid";

    /**
     * 参数: 用户昵称
     */
    public static final String REQUEST_PARAM_KEY_NICKNAME = "nickname";

    /**
     * 参数: 用户头像
     */
    public static final String REQUEST_PARAM_KEY_AVATAR = "headimgurl";

    /**
     * 参数: 微信公众号unionId
     */
    public static final String REQUEST_PARAM_KEY_UNION_ID = "unionid";

    /**
     * 参数: openId
     */
    public static final String REQUEST_PARAM_KEY_OPENID = "openId";

    /**
     * 参数: 转发人openId
     */
    public static final String REQUEST_PARAM_KEY_FROM_OPENID = "fromOpenId";

    /**
     * 参数: 转发人unionId
     */
    public static final String REQUEST_PARAM_KEY_FROM_UNION_ID = "fromUnionId";

    /**
     * 参数: 转发源头UnionId
     */
    public static final String REQUEST_PARAM_KEY_SOURCE_UNION_ID = "sourceUnionid";

    /**
     * 参数:  转发源头OpenId
     */
    public static final String REQUEST_PARAM_KEY_SOURCE_OPEN_ID = "sourceId";

    /**
     * 参数: 场景值
     */
    public static final String REQUEST_PARAM_KEY_SCENE = "scene";

    /**
     * 参数: 团id
     */
    public static final String REQUEST_PARAM_KEY_GROUP = "groupId";

    /**
     * 集团专供
     */
    public static final String REQUEST_PARAM_KEY_ITEM_TITLE = "item_title";

    /**
     * 参数: 微信支付openId
     */
    public static final String REQUEST_PARAM_KEY_WX_OPEN_ID2 = "wxopenid_2";

    /**
     * 埋点subChannel
     */
    public static final String REQUEST_PARAM_KEY_SUB_CHANNEL = "subChannel";

    /**
     * 双旦活动一元券
     */
    public static final int SD_PACKAGE_TICKET = 1;

    /**
     * 双旦活动单品券
     */
    public static final int SD_SINGLE_TICKET = 2;

    /**
     * 双旦团品类券
     */
    public static final int SD_GROUP_TICKET = 3;


    /**
     * 双旦活动礼品券
     */
    public static final int SD_GIFT_TICKET = 4;

    /**
     * 抽奖券
     */
    public static final int SD_DRAW_TICKET = 5;

    /**
     * 参数: 商场编码
     */
    public static final String REQUEST_PARAM_KEY_OMS_CODE = "omsCode";

    /**
     * 参数: 券ID
     */
    public static final String REQUEST_PARAM_KEY_TICKET = "ticketId";

    /**
     * 参数: 微信转发来源
     */
    public static final String REQUEST_PARAM_KEY_FROM = "from";

    /**
     * 参数: vipOpenId
     */
    public static final String REQUEST_PARAM_KEY_VIP_OPEN_ID = "vipOpenId";

    public static final String REQUEST_PARAM_KEY_VIP_ID = "vipId";

    public static final String REQUEST_PARAM_KEY_VIP_CELL = "vipCell";

    public static final String REQUEST_PARAM_KEY_VIP_NAME = "vipName";

    public static final String REQUEST_PARAM_KEY_TAR_FOID = "tar_foid";

    public static final String REQUEST_PARAM_KEY_CITY = "city";

    public static final String REQUEST_PARAM_KEY_MALL_CODE = "mallCode";

    /**
     * 参数: 是否已经授权
     */
    public static final String REQUEST_PARAM_KEY_AUTH = "forceAuth";

    /**
     * 判断微信浏览器
     */
    public static final String WX_USER_AGENT = "micromessenger";

    /**
     * H5页面前缀
     */
    public static final CharSequence URL_PREFIX_ACT_H5 = "/wap/act/h5/";

    /**
     * m_g_t_i 下劵信息前缀 ,
     * group_1 '团1'
     * group_2  '团2'
     * group_3  '团3'
     * group_4  '团4'
     * group_5  '团5'
     * group_6  '团6'
     * group_7  '集团S级爆款'
     * group_8  '集团A级爆款'
     * group_9  '秒杀券'
     * group_10  '打卡券'
     * group_11  '商场集团免费券'
     * group_12  '商场集团单品券'
     * group_13  '商场单品券'
     */
    public static final String KEY_GROUP = "group_";

    /**
     * Redis缓存key前缀: 通过uid查寻活动用户
     */
    public static final String REDIS_KEY_PREFIX_SD_USER_UID = "act-uid:";

    /**
     * Redis缓存key前缀: 通过openId查寻活动用户
     */
    public static final String REDIS_KEY_PREFIX_SD_USER_OPENID = ActConstant.KEY_ACT_OPEN_ID;

    /**
     * Redis缓存key前缀: 城市首页数据
     */
    public static final String REDIS_KEY_PREFIX_SD_CITY = "c_e_i:";
    /**
     * 添加参团人数
     */
    public static final String SD_ADD_GROUP = "group";

    /**
     * 添加单品券人数
     */
    public static final String SD_ADD_TICKET = "ticket";

    /**
     * 双旦增加人数密码
     */
    public static final String SD_EXTRA_NUMBER_PASSWORD = "redstar";

    public static final String SD_COOKIE_KEY_UID = "actuid";

    /**
     * 缓存过期时间: 用户
     */
    public static final Integer REDIS_EXPIRY_SECOND_SD_USER = 60 * 60 * 12;

    /**
     * COOKIE过期时间: 用户
     */
    public static final Integer COOKIE_EXPIRY_SECOND_SD_USER = 60 * 60 * 24 * 3;

    /**
     * 活动ID
     */
    public static final String WX_REQUEST_PARAM_KEY_ACT = "act_id";

    /**
     * 渠道(1:微信;2:小程序 3:H5)
     */
    public static final String WX_REQUEST_PARAM_KEY_SHARE_CHANNEL = "share_channel";

    /**
     * 小程序Loading=0 ，H5为团号 = 1-6 ，H5首页 = 7
     */
    public static final String WX_REQUEST_PARAM_KEY_SHARE_SUB_CHANNEL = "share_sub_channel";

    /**
     * 分享openid
     */
    public static final String WX_REQUEST_PARAM_KEY_SHARE_OPENID = "share_uid";

    /**
     * 分享人union_id
     */
    public static final String WX_REQUEST_PARAM_KEY_SHARE_UNION_ID = "share_unionid";

    /**
     * 0.公众号(外部H5) 1.小程序（包含小程序H5）
     */
    public static final String WX_REQUEST_PARAM_KEY_NEED_CONVERT = "need_convert";

    /**
     * 分享openid
     */
    public static final String WX_REQUEST_PARAM_KEY_VIEW_FROM_OPEN_ID = "view_from";

    /**
     * 分享人union_id
     */
    public static final String WX_REQUEST_PARAM_KEY_VIEW_FROM_UNION_ID = "view_from_unionid";

    /**
     * 阅读openid
     */
    public static final String WX_REQUEST_PARAM_KEY_VIEW_OPEN_ID = "view_uid";

    /**
     * 阅读人uion_id
     */
    public static final String WX_REQUEST_PARAM_KEY_VIEW_UNION_ID = "view_unionid";

    /**
     * 渠道(1:微信;2:小程序 3:H5)
     */
    public static final String WX_REQUEST_PARAM_KEY_VIEW_CHANNEL = "view_channel";

    /**
     * 小程序Loading=0 ，H5为团号 = 1-6 ，H5首页 = 7
     */
    public static final String WX_REQUEST_PARAM_VIEW_SUB_CHANNEL = "view_sub_channel";

    /**
     * 额外人数key
     */
    public static final String EXTRA_NUMBER = "EXTRA_NUMBER";

    /**
     * 参团人数Key
     */
    public static final String GROUP_NUMBER = "GROUP_NUMBER";

    /**
     * 团数量
     */
    public static final int GROUP_SIZE = 13;

    /**
     * 活动预热
     */
    public static final String ACT_PREHEAT = "活动预热";

    public static final String KEY_REAL_NUMBER = "real_number";

    public static final String KEY_SHAM_NUMBER = "sham_number";

    public static final String FIELD_SINGLE_1 = "single1";

    public static final String FIELD_SINGLE_2 = "single2";

    public static final String FIELD_SINGLE_3 = "single3";

    public static final int IS_YOULONG = 1;

    public static final int NO_YOULONG = 0;

    public static final CharSequence URL_PREFIX_JUNE = "/wap/act/h5/";

    /*           redis缓存key：五月大促        */

    public static final String KEY_MTIC = "imp:act:scount:";

    public static final String KEY_MTIUV = "imp:act:suv:";

    public static final String KEY_MS_SHAME_NUMBER = "june_ms_s_n:";

    public static final String KEY_CMBI = "june_c_m_b_i:";

    public static final String KEY_CMEI = "june_c_m_e_i:";

    public static final String KEY_CMHI = "june_c_m_h_i:";

    public static final String KEY_MBPI = "m_b_p_i:";

    public static final String KEY_MGTI = "june_m_g_t_i:";

    public static final String KEY_MPI = "m_p_i:";

    public static final String KEY_MTI = "june_m_t_i:";

    public static final String KEY_YLTI = "june_y_l_t_i:";

    public static final String KEY_EXTRA_NUMBER = "june_extra_number";

    public static final String KEY_GROUP_NUMBER = "june_group_number";

    public static final String SPECIAL_KV = "specialKV";

    public static final String SPECIAL_KV_INIT = "0187,0002,0004,0006,0008,0010,0012";

    public static final String KEY_A_C_M = "june_a_c_m";

    //大小促商场城市等信息
    public static final String KEY_A_C_M_P = "june_a_c_m_p";

    public static final String KEY_A_D_C_M = "june_a_d_c_m";

    public static final String KEY_A_S_D_C_M = "june_a_s_d_c_m";

    public static final String KEY_ACT_OPEN_ID = "imp:act:user:openid:";

    public static final String KEY_ACT_UID = "imp:act:user:uid:";

    public static final String KEY_T_R_N = "june_t_r_n:";

    public static final String KEY_T_S_N = "june_t_s_n:";

    public static final String KEY_FATI = "hticket:act:";

    public static final String KEY_SIGNTI = "hsign:act:";

    public static final String URL_SIGN = "activity/GetSignIn";

    public static final String ACT_ID = "424";

    public static final String MSG_INFO_MACALLINE_REST = "红星接口请求——url:{}——入参：{}——出参：{}";

    public static final String URL_GETTICKETCOUNT = "activity/GetTicketCount";

    public static final String START_DATE = "2019-04-27:";

    public static final String END_DATE = "2019-05-01";

    /**
     * 集赞券
     */
    public static final int SD_FABULOUS_TICKET = 6;

    /**
     * 无门槛券
     */
    public static final int SD_NODOOR_TICKET = 7;

    /**
     * 满减券
     */
    public static final int SD_FULLRETURN_TICKET = 8;

    /**
     * 秒杀券
     */
    public static final int SD_SECONDKILL_TICKET = 11;


    public static final String SECOND_KILL_BEGIN = "10点";

    public static final String INDEX_URL = "https://xcxchat.sny7.com/wap/act/h5/june-index";

    public static final String URL_PREFIX_ACT_H5_PAY_TICKET = "/wap/act/getPayJsTicket";

    public static final String URL_PREFIX_ACT_H5_SHARE_TICKET = "/wap/act/getJsTicket";

    public static final String URL_PREFIX_ACT_H5_WXPREPAY = "/wap/act/wxPrePay";

    public static final String URL_PREFIX_ACT_H5_H5PREPAY = "/wap/act/h5PrePay";

    public static final String WINE_USE_REMARK = "<p>兑换流程规则：</p>\n" +
            "<p>1、下载注册酒仙网APP--我的酒仙—去兑换--输入兑换码--兑换；</p>\n" +
            "<p>2、兑换后葡萄酒自动加入购物车，在购物车提交订单，填写收货信息，支付19元运费结算，省运费小妙招—再买包邮区任意酒水，立省邮费；</p>\n" +
            "<p>3、每个用户可领取使用一次，兑换有效期至2019年5月31日；</p>\n" +
            "<p>4、如您在使用中，有问题咨询酒仙网客服400-617-9999。</p>";

    public static final String MAY_ACT_DATES = "2019-04-13,2019-04-14,2019-04-15,2019-04-16,2019-04-17,2019-04-18,2019-04-19,2019-04-20,2019-04-21,2019-04-22," +
            "2019-04-23,2019-04-24,2019-04-25,2019-04-26,2019-04-27,2019-04-28,2019-04-29,2019-04-30,2019-05-01,2019-05-02,2019-05-03,2019-05-04";

    public static final String WINE_SMS_1 = "恭喜你获得价值399元的西班牙安徒生夜莺干红葡萄酒一瓶，兑换码为：";

    public static final String WINE_SMS_2 = "，请在2019年5月31日前登陆酒仙网客户端进行兑换；终极开团，低价复仇，优质尖货尽在红星美凯龙，登陆小程序即抢49999元免单大奖。";


    /*******************************2019-04-29 新增枚举值****************************************/
    //公众号授权
    public static final String WX_AUTH = "wxAuthKey2";
    //对应微信信息
    public static String WX_AUTH_OPENID_INFO = "imp:wx:getWxAuthOpenid:";
    //对应微信用户信息
    public static String WX_AUTH_USER_INFO = "imp:wx:getWxAuthUserinfo:";

    //公众号支付授权
    public static final String WX_PAY_AUTH = "wxAuthKey3";
    //对应微信信息
    public static String WX_PAY_AUTH_OPENID_INFO = "imp:wx:getWxAuthPayOpenid:";


    public static final String KEY_DYNAMIC = "imp:act:ldynamic";

    public static final String KEY_DYNAMIC_INCR = "imp:act:idynamic";

    public static final String TYPE_MAY = "五一大促";

    public final static String MALL_API_URL_STAG = "https://mdm.oa.chinaredstar.com/mdQuery/query";

    public static final String KEY_ALL_MALL = "imp:act:small";

    //股东来了会员卡type  系统发放
    public static final Integer GDLL_CARD_TYPE_SYS = 1;

    //股东来了会员卡type  人工发放
    public static final Integer GDLL_CARD_TYPE_PERSON = 2;

    //股东来了会员卡issue  已发放
    public static final Integer GDLL_CARD_ISSUED = 1;

    //股东来了会员卡issue  未发放
    public static final Integer GDLL_CARD_TYPE_UN_ISSUE = 0;

    //无效
    public static final Integer GDLL_ACTIVE_INVALOD = 0;
    //有效
    public static final Integer GDLL_ACTIVE_IALOD = 1;

    //股东来了optype 系统
    public static final Integer GDLL_OPTYPE_SYS = 1;

    //股东来了optype 人工
    public static final Integer GDLL_OPTYPE_PERSON = 2;

    public static final String IMP_WX = "imp:wx:";

    public static final String ACT_HASH = ":act_hash";

    public static final String UNIT_IM = "unit_im";

    public static final String DRAINAGE = "drainage_";

    //活动任务类型 - 每日任务
    public static final Integer ACT_TASK_TYPE_DAY = 1;

    //活动任务类型 - 唯一任务
    public static final Integer ACT_TASK_TYPE_ONLY = 2;

    //抽奖红包类型 - 普通红包
    public static final Integer ACT_LUCKY_DRAW_TYPE_ORD = 1;

    //抽奖红包类型 - 大红包
    public static final Integer ACT_LUCKY_DRAW_TYPE_BIG = 2;

    public static final String ACT_TICKET_DETAIL = "promotion_ticket_detail_";

    public static final String ACT_GROUP = "_group:";

    public static final String ACT_NOVEMBER_SOURCE = "November";
}
