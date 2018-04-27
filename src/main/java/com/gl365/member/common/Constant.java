package com.gl365.member.common;

public class Constant {

	/**
	 * 账户系统返回码：成功
	 */
	public static final String actRespSuccess = "000000";
	
	/**
	 * 账户系统查询账单标示，给乐豆 40001
	 */
	public static final String hcAgentID = "40001";
	
	/**
	 * 付费通标示
	 */
	public static final String fft = "FFT001";
	
	/**
	 * 支付网关接口版本号 1.0.0
	 */
	public static final String getwayVersion = "1.0.0";
	
	/**
	 * 支付网关返回成功应答码
	 */
	public static final String getwayRespCodeSuccess = "000000";
	
	/**
	 * 支付网关返回补单成功应答码
	 */
	public static final String getwayRespBDSuc = "000090";
	
	/**
	 * 快捷支付场景码
	 */
	public static final String quickPayScene = "0";
	
	/**
	 * 条码状态查询场景
	 */
	public static final String queryBarScene = "5";
	
	
	/**
	 * 密码支付场景码
	 */
	public static final String passwordPay = "8";
	
	/**
	 * 支付订单标题模板
	 */
	public static final String payTitle = "$@%s,%s,%s@$%s";
	
	public static final String fftChannelId = "10019999";
	
	/**
	 * 支付网关返回成功应答类型
	 */
	public static final String getwayRespTypeSuccess = "S";
	
	/**
	 * 支付网关返回不确定应答类型
	 */
	public static final String getwayRespTypeR = "R";
	
	/**
	 * 付费通组织机构代码
	 */
	public static final String fftOrganCode ="10001";
	
	
	/**
	 * 订单系统返回成功应答码
	 */
	public static final String orderRespCodeSuccess = "000000";
	
	
	/**********************************************
	 *****************付费通交易错误码 *****************
	 *********************************************/
	
	/**
	 * 订单总金额超过限额
	 */
	public static final String total_money_out = "200206";
	
	/**
	 * 支付网关需要支付密码返回码
	 */
	public static final String paymentNeedPayPwd = "200230";
	
	/**
	 * 用户银行卡余额不足
	 */
	public static final String member_bankCard_balance_not_enough = "200210";
	
	/**
	 * 买家状态非法
	 */
	public static final String member_status_exception = "200213";
	
	/**
	 * 买家付款日限额超限
	 */
	public static final String member_daily_quota_out = "200216";
	
	/**
	 * 商家收款额度超限
	 */
	public static final String merchant_gather_quota_out = "200217";
	
	/**
	 * 商家账号被冻结
	 */
	public static final String merchant_account_freeze = "200218";
	
	/**
	 * 买家未通过人行认证
	 */
	public static final String member_account_hasnt_RH_auth = "200219";
	
	/**
	 * 该卡不支持当前支付，请换卡支付
	 */
	public static final String bankCard_not_support_pay = "200229";
	
	/**
	 * 买卖家不能相同
	 */
	public static final String payee_is_same_payer = "200232";
	
	/**
	 * 有密支付付费通返回密码错误
	 */
	public static final String fftPayPwdErr = "200055";

	/**
	 * 支付密码错误次数超限，账户被锁定
	 */
	public static final String paypwd_error_count_outlimit = "200062";
	
	/**
	 * 付费通返回用户未绑卡
	 */
	public static final String memberHasNotBindCard = "42";
	
	/**
	 * 群主
	 */
	public static final String GROUPOWNER = "GROUPOWNER";

	/**
	 * 群成员
	 */
	public static final String GROUPMEMBER = "GROUPMEMBER";
}
