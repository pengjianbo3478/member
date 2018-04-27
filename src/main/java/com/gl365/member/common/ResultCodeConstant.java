package com.gl365.member.common;
/**
 * < 响应的结果码 >
 *   	
 * 规则：
 * 100以下的错误码,系统保留,例如： 0-成功;1-签名失败;2-Token超时或无效
 * 101及以上错误码由接口自行定义。
 * 
 * @author hui.li 2017年4月12日 - 下午1:41:42
 * @Since  1.0
 */
public class ResultCodeConstant {
	
	public static final Integer SUCCESS 						= 0; 	// 成功
	public static final Integer INPUT_PARAM_ERROR				= 2;	// 输入参数错误
	public static final Integer SYSTEM_ERROR					= 3;	// 系统异常
	public static final Integer SYSTEM_TIME_OUT					= 4;	// 服务器繁忙
	public static final Integer SIGN_ERROR 						= 5;	// 签名失败
	public static final Integer TOKEN_TIMEOUT					= 6;	// Token超时
	public static final Integer PARAM_NULL						= 7;	// 参数为空
	
	public static final Integer PHONENO_HAS_REGISTER			= 201;	//手机号已注册，无法发送短信验证码
	public static final Integer PHONENO_HAS_NOT_REGISTER		= 202;	//手机号未注册，无法发送短信验证码
	public static final Integer SMS_CODE_EXPIRED				= 203;	//验证码已过期
	public static final Integer SMS_CODE_ERROR					= 204;  //验证码错误
	public static final Integer UNKNOWN 						= 999;	// 未知错误
}
