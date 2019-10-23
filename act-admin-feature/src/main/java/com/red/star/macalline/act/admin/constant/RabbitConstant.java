package com.red.star.macalline.act.admin.constant;

/**
 * RabbitMQ队列配置
 *
 * @author nofish.yan@gmail.com
 * @date 2019/4/26.
 */
public class RabbitConstant {

    /**
     * 活动埋点队列
     */
    public static final String QUEUE_ACT_PAGE_VIEW = "view_queue_act_page";

    /**
     * H5埋点队列
     */
    public static final String QUEUE_WAP_RECORD = "queue_wap_record";

    /**
     * 活动券支付成功队列
     */
    public static final String QUEUE_ACT_TICKET_PAY_NOTICE = "wap_queue_act_ticket_pay";

    /**
     * 活动券支付成功交换机
     */
    public static final String EXCHANGE_ACT_TICKET_PAY_NOTICE = "wap_exchange_act_ticket_pay";

    /**
     * 活动券支付成功路由ø
     */
    public static final String ROUTINGKEY_TICKET_PAY = "direct.ticket.pay";


    /**
     * 配置活动刷新交换机
     */
    public static final String FANOUT_ACT_REFRESH = "fanout_act_refresh";
}
