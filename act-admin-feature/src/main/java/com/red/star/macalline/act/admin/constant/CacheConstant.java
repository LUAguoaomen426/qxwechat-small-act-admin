package com.red.star.macalline.act.admin.constant;

/**
 * 缓存相关常量
 *
 * @author nofish.yan@gmail.com
 * @date 2019/5/22.
 */
public class CacheConstant {

    /**
     * 时间单位: 分钟
     */
    public static final Integer MINUTE = 60;

    /**
     * 时间单位: 小时
     */
    public static final Integer HOUR = 60 * MINUTE;

    /**
     * 时间单位: 天
     */
    public static final Integer DAY = 24 * HOUR;

    /**
     * H5用户缓存KEY(token)
     */
    public static final String CACHE_KEY_USER_TOKEN = "imp:wap:user:token:";

    /**
     * 用户信息缓存: openId
     */
    public static final String CACHE_KEY_USER_OPEN_ID = "imp:wap:fans:openId:";

    /**
     * H5用户信息缓存: openId
     */
    public static final String CACHE_KEY_WAP_USER_OPEN_ID = "imp:wap:user:openId:";

    /**
     * H5用户信息缓存: uid
     */
    public static final String CACHE_KEY_WAP_USER_UID = "imp:wap:user:uid:";

    /**
     * 活动列表商场缓存
     */
    public static final String CACHE_KEY_MALL_LIST_ENTRANCE = "imp:wap:mall:entrance";

    /**
     * 主会场商场缓存
     */
    public static final String CACHE_KEY_MALL_LIST_HOME = "imp:wap:mall:home:";

    /**
     * 活动商场列表
     */
    public static final String CACHE_KEY_MALL_LIST_ACT = "imp:wap:mall:";

    /**
     * 用户缓存过期时间: Token
     */
    public static final Integer CACHE_EXPIRE_USER_TOKEN = 3 * DAY;

    /**
     * 用户缓存过期时间: Token 新
     */
    public static final Integer CACHE_EXPIRE_USER_TOKEN_31 = 31 * DAY;

    /**
     * 用户缓存过期时间: OpenId
     */
    public static final Integer CACHE_EXPIRE_USER_OPEN_ID = 8 * HOUR;

    /**
     * 活动列表商场缓存过期时间
     */
    public static final Integer CACHE_EXPIRE_MALL_LIST = 1 * HOUR;

    /**
     * 爆款列表缓存key
     */
    public static final String CACHE_KEY_HOT_LIST = "m_b_p_i:";

    /**
     * 爆款详情缓存key
     */
    public static final Object CACHE_KEY_HOT_DETAIL = "m_p_i:";

    /**
     * 商场缓存: omsCode
     */
    public static final String CACHE_KEY_MALL_OMS_CODE = "imp:wap:mall:omsCode:";

    /**
     * 商场缓存: mallCode
     */
    public static final String CACHE_KEY_MALL_CODE = "imp:wap:mall:mallCode:";

    /**
     * 商场缓存过期时间
     */
    public static final Integer CACHE_EXPIRE_MALL = 3 * DAY;

    /**
     * 城市默认商场
     */
    public static final String CACHE_KEY_MALL_CITY_DEFAULT = "imp:wap:mall:city:";

    /**
     * 城市默认商场缓存过期时间
     */
    public static final Integer CACHE_EXPIRE_MALL_CITY = 3 * DAY;

    /**
     * 超级品牌日活动缓存
     */
    public static final String CACHE_KEY_ACT_SUPER_BRAND = "imp:wap:act:superBrand:";

    /**
     * 超级省购会活动缓存
     */
    public static final String CACHE_KEY_ACT_SUPER_PROVINCE = "imp:wap:act:superProvince:";
    /**
     * 超级品类日缓存
     */
    public static final String CACHE_KEY_ACT_SUPER_CATEGORY = "imp:wap:act:superCategory:";

    /**
     * 用户动态轮播缓存
     */
    public static final String CACHE_KEY_DYNAMIC_BANNER = ":ldynamic";

    /**
     * 完成打卡记录发券
     */
    public static final String CACHE_KEY_SEND_TICKET = ":send:ticket:";

    /**
     * 活动基础数据redis前缀
     */
    public static final String CACHE_KEY_PREFIX = "imp:wap:";

    /**
     * 券类型加50区分原来type
     */
    public static final int CACHE_KEY_50 = 50;

    /**
     * redis缓存key(商场扩展信息)
     */
    public static final String CACHE_KEY_CMEI = ":c_m_e_i:";

    /**
     * redis缓存key(首页定位到商场，显示券，团的信息)
     */
    public static final String CACHE_KEY_CMHI = ":c_m_h_i:";

    /**
     * redis缓存key(显示团下所有的券信息)
     */
    public static final String CACHE_KEY_MGTI = ":m_g_t_i:";

    /**
     * redis缓存key(券详情)
     */
    public static final String CACHE_KEY_MTI = ":m_t_i:";

    /**
     * 用户动态轮播数
     */
    public static final String KEY_DYNAMIC_INCR = ":idynamic";

    /**
     * 有龙券详情
     */
    public static final String CACHE_KEY_YLTI = ":y_l_t_i:";

    /**
     * redis缓存key(打卡)
     */
    public static final String CACHE_KEY_HISCARD = ":hiscard:";

    /**
     * 虚拟参团人数
     */
    public static final String KEY_EXTRA_NUMBER = ":extra_number";

    /**
     * 真实参团人数
     */
    public static final String KEY_GROUP_NUMBER = ":group_number";

    /**
     * 活动主会场地址
     */
    public static final String CACHE_KEY_HOME_LINK = ":homeLink";

    /**
     * redis缓存key(领券)
     */
    public static final String CACHE_KEY_HISTICKET = ":histicket:";

    /**
     * 领券缓存过期时间
     */
    public static final Long CACHE_EXPIRE_TICKET = 31 * DAY * 1000L;

    /**
     * 券购买数量（真实）
     */
    public static final String KEY_T_R_N = ":ht_r_n";

    public static final String KEY_T_S_N = ":ht_s_n";

    /**
     * 缓存时间: 1小时
     */
    public static final Integer CACHE_EXPIRE_ONE_HOUR = 1 * HOUR;
    /**
     * 活动列表缓存
     */
    public static final String CACHE_KEY_ACT_LIST = "imp:wap:act:list";

    /**
     * 活动&商场配置
     */
    public static final String CACHE_KEY_ACT_MALL_MERGE = "imp:wap:mall:merge:";

    /**
     * redis缓存key(广告位)
     */
    public static final String CACHE_KEY_ACT_SPEC_LINK = ":speclink:";

    /**
     * 特殊链接CODE反查允许的所有商场信息
     */
    public static final String CACHE_KEY_ACT_SPEC_LINK_ALLOWED_MALL = ":speclink:allowedmall";

    /**
     * 雅居乐免费劵领取数量
     */
    public static final String CACHE_KEY_ACT_YJL_FREE_SINGLE_NUM = ":yjl_free_num:";

    /**
     * 重复领取免费劵领取数量
     */
    public static final String CACHE_KEY_ACT_REPEAT_COUPON_NUM = ":repeat_coupon_num:";

    /**
     * 雅居乐发送短信限制
     */
    public static final String CACHE_KEY_ACT_YJL_SMS = ":yjl_sms";

    public static final String CACHE_KEY_ACT_SPEC_DEFAULT_MALL = ":speclink:def_mall:";

    public static final String CACHE_KEY_ACT_MALL_OMS_CODE = ":mall:omsCode";


    /**
     * 活动基础数据redis前缀
     */
    public static final String CACHE_SEND_SMS_KEY = "SMS_";

    /**
     * 抽奖信息
     */
    public static final String CACHE_KEY_ACT_DRAW = ":draw:";

    public static final String CACHE_KEY_ACT_DRAW_INFO = "info";

    public static final String CACHE_KEY_ACT_DRAW_MARTIX = "martix";

    public static final String CACHE_KEY_ACT_DRAW_TICKET_NUMS = "ticketNums";

    public static final String CACHE_KEY_ACT_DRAW_TICKET_NUM = ":ticketNum:";

    public static final String CACHE_KEY_ACT_LOCK = ":lock:";

    /**
     * 用户资格
     */
    public static final String CACHE_KEY_USERSENIORITY = ":seniority:";

    public static final String CACHE_KEY_COLON = ":";

    /**
     * setnx key
     */
    public static final String LOCK_KEY = "lock";

    /**
     * 助力人信息
     */
    public static final String CACHE_KEY_ACT_BOOST_HELPER = ":helper:";

    /**
     * 助力
     */
    public static final String CACHE_KEY_BOOST = ":boost:";

    /**
     * 助力——奖项
     */
    public static final String CACHE_KEY_BOOST_AWARD = ":boost:award:";

    /**
     * 翻牌抽奖-中奖纪录
     */
    public static final String CACHE_KEY_LOTTERY_LIST = ":lottery:list:";

    public static final String CACHE_KEY_ACT_MODULE = "imp:wap:act_module:";

    /**
     * posterId 转换 source
     */
    public static final String CACHE_KEY_ACT_MODULE_POSTER_ID = "imp:wap:act_module:poster_id:";

    /**
     * 抽奖机会——龙翼订单
     */
    public static final String CACHE_KEY_ORDER_LONGYI = ":longyi:orderNo:";

    /**
     * 默认loading底图
     */
    public static final String DEFAULT_LOADINGBASEMAP = "https://img3.mklimg.com/g3/M00/0B/D5/rBBrEVyQsPWAPJb6AAxvyC2ezNI089.png!";

    /**
     * 默认分享图片地址
     */
    public static final String DEFAULT_SHAREIMAGEURL = "https://img1.mklimg.com/g3/M00/0B/CD/rBBrEFyQouqAawAXAAPSh69ChIQ161.jpg!";

    /**
     * 默认分享文案
     */
    public static final String DEFAULT_SHARETITLE = "【红星美凯龙·团尖货】甄选全球设计尖货！";

    /**
     * 默认分享图片地址（微信）
     */
    public static final String DEFAULT_WX_SHAREIMAGEURL = "https://img1.mklimg.com/g3/M00/0B/C6/rBBrEVyQmWCAAwrpAASacipdOBU279.jpg!";

    /**
     * 默认分享文案（微信）
     */
    public static final String DEFAULT_WX_SHARETITLE = "【红星美凯龙·团尖货】";

    /**
     * 默认分享详情（微信）
     */
    public static final String DEFAULT_WX_DESC = "甄选全球设计尖货！";

    /**
     * 默认loading底图（iPhoneX）
     */
    public static final String DEFAULT_LOADINGBASEMAPX = "https://img1.mklimg.com/g3/M00/0B/C6/rBBrEFyQmCuAfbxoAApp70YOIGc359.png!";

    /**
     * 默认loading按钮图
     */
    public static final String DEFAULT_LOADINGBUTTON = "https://img2.mklimg.com/g3/M00/0B/CB/rBBrEFyQntyAYoM7AABECM4Lo_Y990.png!";

    /**
     * 默认loading 颜色
     */
    public static final String DEFAULT_LOADINGCOLOR = "#3378E4";
    public static final String DEFAULT_LOADING_FONT_COLOR = "#3378E4";

    /**
     * 默认loading 标题
     */
    public static final String DEFAULT_LOADINGTITLE = "红星美凯龙团尖货";

    /**
     * 默认loading背景颜色
     */
    public static final String DEFAULT_LOADINGBGCOLOR = "#0C254C";

    /**
     * PVUV
     */
    public static final String CACHE_KEY_PVUV = "PVUV";
}
