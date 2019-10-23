package com.red.star.macalline.act.admin.constant;

/**
 * 微信接口路径常量
 *
 * @author nofish.yan@gmail.com
 * @date 2018/11/29.
 */
public class WxUriConstant {

    /**
     * 获取用户信息
     */
    public static final String WX_URI_VIP_BY_OPENID = "user/GetVipByWxOpenid";

    /**
     * 埋点-页面访问
     */
    public static final String WX_URI_WB_ACT_VIEW = "h5/WBActView";

    /**
     * 埋点-分享记录
     */
    public static final String WX_URI_WB_ACT_SHARE = "h5/WBActShare";

    /**
     * 埋点-留资
     */
    public static final String WX_URI_WB_ACT_FORM = "h5/WBActForm";

    /**
     * 用户授权key值
     */
    public static final String PARAM_KEY_AUTH_CODE = "";

    /**
     * 获取微信内预支付参数地址
     */
    public static final String WX_URI_PAY_JS_TICKET = "WeiXin/GetPayJsTicket";

    /**
     * 获取微信内分享参数地址
     */
    public static final String WX_URI_JS_TICKET = "WeiXin/GetJsTicket";

    /**
     * 微信内预支付
     */
    public static final String WX_URI_WXPREPAY = "Pay/WxPrePay";

    /**
     * 微信外支付结果查询
     */
    public static final String WX_URI_H5PAYQUERYORDER = "Pay/H5PayQueryOrder";

    /**
     * 微信外预支付
     */
    public static final String WX_URI_H5PREPAY = "Pay/H5PrePay";

    /**
     * 图形验证码
     */
    public static final String WX_URI_GET_CAPTCHACODE = "user/GetCaptchaCode";

    /**
     * 短信验证码
     */
    public static final String WX_URI_SENDLOGINSMSCODE = "user/SendLoginSmsCode";

    /**
     * 微信支付成功
     */
    public static final String WX_URI_WXPAYSUCCESS = "Pay/WxPaySuccess";

    /**
     * 微信支付失败
     */
    public static final String WX_URI_WXPAYCANCEL = "Pay/WxPayCancel";

    /**
     * 券详情
     */
    public static final String WX_URI_TICKET_INFO = "activity/GetPromotionTicketInfo";

    /**
     * 获取券包下券的核销码
     */
    public static final String WX_URI_GETPACKAGETICKETBYORDERID = "activity/GetPackageTicketByOrderId";

    /**
     * 小程序预支付
     */
    public static final String WX_URI_XCXPREPAY = "pay/PrePay_1212";

    /**
     * 微信内授权地址
     */
    public static String WX_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc385b3ab85b13741&redirect_uri=http://rs-wx.sny7.com/wxopenid/oauth4/authUserInfo.ashx?jump_url=CURRENT_URL&response_type=code&scope=snsapi_userinfo&connect_redirect=1#wechat_redirect";

    /**
     * 微信内授权地址
     */
    public static String WX_PAY_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc385b3ab85b13741&redirect_uri=http://rs-wx.sny7.com/wxopenid/oauth4/authPay.ashx?jump_url=CURRENT_URL&response_type=code&scope=snsapi_base&connect_redirect=1#wechat_redirect";

}
