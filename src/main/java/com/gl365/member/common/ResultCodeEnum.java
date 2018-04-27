package com.gl365.member.common;

/**
 * api接口请求结果枚举 规则： 长度6位 |0000|00 前四位表示模块|后两位递增
 * 
 * 列如 系统类异常：0000** 用户类异常：1000**
 * 
 * @author dfs_519 2017年4月27日下午2:02:21
 */
public class ResultCodeEnum {

	public enum System {
		/**
		 * 系统保留100以下的错误码
		 */
		SUCCESS("000000", "成功"),

		PARAM_NULL("000002", "参数为空"),

		PARAM_ERROR("000003", "参数非法"),

		VERIFY_SIGN_FAIL("000004", "验签失败"),

		TOKEN_TIMEOUT("000005", "Token失效"),

		REQUEST_IS_NULL("000006", "错误请求"),

		NO_PERMISSION("000007", "您没有执行此操作的权限，请重新登录"),

		SYSTEM_DATA_EXECEPTION("000008", "系统数据异常"),

		SYSTEM_TIME_OUT("000098", "请求频繁"),

		SYSTEM_ERROR("000099", "服务器错误，请稍后重试"),;

		private String code;

		private String msg;

		private System(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	public enum User {
		ID_PWD_MATCHING_ERROR("100001", "用户名或密码不正确"),
		LOG_COUNT_LIMIT_ERROR("100002", "设备超过当天使用数"),
		DEVICE_UNUSER_ERROR("100003", "设备被禁用"),
		NO_DEVICE_UNUSER_ERROR("100004", "设备不常用"),
		AUTH_CODE_ERROR("100005", "验证码错误"),
		PWD_MATCHING_ERROR("100006", "原登录密码错误，请重新输入"),
		PWD_SAME_ERROR("100007", "新旧密码不能相同"),
		PHONE_SAME_ERROR("100008", "手机号不能相同"),
		OLD_PHONE_ERROR("100009", "原号不正确"),
		NO_MERCHANT_INFO_ERROR("100010", "没有商户信息"),
		NO_USER_INFO_ERROR("100011", "手机号不存在,请重新输入"),
		IMAGE_CODE_TIME_OUT("100012", "图片验证超时"),
		IMAGE_CODE_ERROR("100013", "图片验证不正确"),
		PASSWORD_COUNT_ERROR("100014", "输错次数过多，账号今日已被冻结，如有疑问，请拨打客服热线：4000200365"),
		USER_STATUS_ERROR("100015", "用户状态异常,可能被禁用或者已注销"),
		NO_ADD_USER_ERROR("100016", "该用户未关联商家,请联系商家管理员"),
		NO_MEMBER_INFO_ERROR("100017", "没有用户信息"),
		IDCARD_LIMIT_ERROR("100018", "该身份证已验证,不可重复认证"),
		IDCARD_VALIDATER_ERROR("100019", "身份证号不匹配，请重新输入"),
		IDCARD_UNVALIDATER_ERROR("100020", "您未实名认证,请先使用验证码登录"),
		;
		private String code;

		private String msg;

		private User(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	public enum Sms {

		PHONENO_HAS_REGISTER("200001", "手机号已注册，请重新输入"), PHONENO_HAS_NOT_REGISTER("200002", "手机号未注册，无法发送短信验证码"), SMS_CODE_EXPIRED("200003", "验证码已过期，请重新发送"), SMS_CODE_ERROR("200004", "验证码错误，请重新输入"), SEND_SMS_TRANSFINITE("200005", "发送验证码次数已超过限制，请明天再试"), SEND_SMS_LIMIT_PERMINU("200006", "发送验证码操作过于频繁，1分钟只能发送一次，请稍后重试"),;

		private String code;

		private String msg;

		private Sms(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	public enum Payment {
		/**
		 * 101及以上错误码由接口自行定义
		 */
		NEED_PAY_PWD("101", "支付需要支付密码"),;

		private String code;

		private String msg;

		private Payment(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	public enum Merchant {
		REAL_COMMENT("400001", "该评论已提交,请勿重复评论");
		private String code;

		private String msg;

		private Merchant(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	public enum Customize {

		;
		private String code;

		private String msg;

		private Customize(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
		
	public enum Advertisment{
			
			AD_NAME_HAS_EXIST("600001","广告名称已存在"),
			
			ADD_AD_FAIL("600002","新增广告失败"),
			
			UPDATE_AD_FAIL("600003","更新广告失败"),
			
			QUERY_AD_LIST_FAIL("600004","查询广告列表失败"),
			
			PUT_AD_FAIL("600005","新增投放广告失败"),
			
			QUERY_PUTAD_LIST_FAIL("600006","查询投放广告列表失败"),
			
			UPDATE_PUTAD_FAIL("600007","更新投放广告失败"),
			
			AD_DETAIL_LIST_FAIL("600008","查询当前广告失败"),
			;
			
			private String code;
	
			private String msg;
	
			private Advertisment(String code, String msg) {
				this.code = code;
				this.msg = msg;
			}
	
			public String getCode() {
				return code;
			}
	
			public void setCode(String code) {
				this.code = code;
			}
	
			public String getMsg() {
				return msg;
			}
	
			public void setMsg(String msg) {
				this.msg = msg;
			}
		}

}
