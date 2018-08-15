package com.article.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(weChatAuth)|(bindAccount)|(doRegisterOrUpdate)).*"; // 不对匹配该值的访问路径拦截（正则）

	/**
	 * 返回码：正常
	 */
	public static Integer SUCCESS = 0;

	/**
	 * 返回码：异常
	 */
	public static Integer FAILURE = 1;

	/**
	 * 正常返回信息
	 */
	public static String SUCCESS_INFO = "SUCCESS";

	public static final String FAIL = "FAIL";

	public static String ali_success = "10000";

	/**
	 * 缓存token
	 */
	public static Map<String, String> cacheTokenMap = new ConcurrentHashMap<String, String>();

	public static final String CANCELLATION_DATE = "cancellation_date";

}
