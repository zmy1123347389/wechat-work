package com.article.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * Cookie 辅助类
 * @author 张立伟
 * @date : 2016-1-29 上午9:38:29
 * @snice V1.0
 */  

public class CookieUtil {
	/**
	 * 获得cookie
	 * @param request HttpServletRequest
	 * @param name cookie的name
	 * @return 若此cookie存在则返回cookie，否则返回null.
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * 根据cookie name获得值
	 * @param request HttpServletRequest
	 * @param name cookie的name
	 * @return cookie的value
	 */
	public static String getCookieValue(HttpServletRequest request, String name)  {
		Assert.notNull(request);
		Cookie cookie =  getCookie(request, name);
		if(cookie != null){
			try {
				return URLDecoder.decode(cookie.getValue(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return cookie.getValue();
			}
		}

		return null;
	}

	/**
	 * 根据经过加密后的cookie的name获得值
	 * @param request HttpServletRequest
	 * @param name cookie的name
	 * @param cipherKey cookie的加密密钥
	 * @return cookie的解密后的value
	 */
	public static String getCookieValue(HttpServletRequest request, String name, String cipherKey) {
		String value = getCookieValue(request, name);
		if(value != null){
			return AESCipher.decrypt(value, cipherKey);
		}
		return null;
	}
	
	/**
	 * 添加cookie
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name 待加入的cookie的name
	 * @param value 待加入的cookie的value
	 * @param expiry 待加入的cookie的失效时间
	 * @param domain 待加入的cookie的域
	 * @return 加入的cookie
	 */
	public static Cookie addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			Integer expiry, String domain)   {
		Cookie cookie;
		try {
			cookie = new Cookie(name, URLEncoder.encode(value,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			cookie = new Cookie(name, value);
		}

		if (expiry != null) {
			cookie.setMaxAge(expiry);
		}
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		String ctx = request.getContextPath();
//		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx + "/");
		cookie.setPath(ctx.equals("/") ? "/" : ctx + "/");//注释掉是因为默认设置的Path在不同浏览器下会有不同
		//例如火狐为/sncms/而谷歌为/sncms，这里如果设死了则前台取值会出现浏览器不兼容的问题
		response.addCookie(cookie);
		return cookie;
	}
	
	/**
	 * 添加经过加密的cookie，有加密需求的需要调用此方法
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name 待加入的cookie的name
	 * @param value 待加入的cookie的value
	 * @param expiry 待加入的cookie的失效时间
	 * @param domain 待加入的cookie的域
	 * @param cipherKey cookie的加密密钥
	 * @return 加入的cookie
	 */
	public static Cookie addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			Integer expiry, String domain, String cipherKey) {
		return addCookie(request, response, name, AESCipher.encrypt(value, cipherKey), expiry, domain);	
	}

	/**
	 * 删除cookie
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name 待加入的cookie的name
	 * @param domain 待加入的cookie的域
	 */
	public static void delCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String domain) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		String ctx = request.getContextPath();
//		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		cookie.setPath(ctx.equals("/") ? "/" : ctx + "/");
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
		
	
	}
	
	/**
	 * 判断当前用户会话是否超时
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isTimeout(HttpServletRequest request){
		//访问URI
		String requestURI = request.getRequestURI();
		//是否是上传请求，单独出来是因为swfupload发送请求无法携带cookie,需要单独处理
		boolean isUploadRequest = false;
		if(requestURI.indexOf("/upload.") != -1){	
			isUploadRequest = true;
		}
		/*String userid  = isUploadRequest ? request.getParameter("userid") : getCookieValue(request, "userid");
		System.out.println("cookie-userid:"+userid);
		if(StringUtil.isEmpty(userid)){
			return true;	//超时
		}
		String cvString = isUploadRequest ? request.getParameter("requestInfo") : getCookieValue(request, "requestInfo");
		System.out.println("cookie-requestInfo:"+cvString);
		if(StringUtil.isEmpty(cvString)){
			return true;
		}
		String requestInfo  = CaesarCipher.decrypt(cvString);
		System.out.println("cookie-requestInfo2:"+requestInfo);
		if(StringUtil.isEmpty(requestInfo)){
			return true;
		}
		String[] requestInfos = requestInfo.split("_");
		//如果上次登录时间 + 超时所需要的时间 >= 当前系统时间,则验证通过,否则超时
		System.out.println("cookie-time:"+(Long.parseLong(requestInfos[0]) +Long.parseLong(requestInfos[1])));
		System.out.println("cookie-time2:"+System.currentTimeMillis());
		if(Long.parseLong(requestInfos[0]) + Long.parseLong(requestInfos[1]) >= System.currentTimeMillis()){
			return false;
		}*/
		return true;
	}
}
