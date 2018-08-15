package com.article.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截器基类
 * @author 张立伟
 * @date : 2016-1-29 上午8:48:23
 * @snice V1.0
 */  
public class BaseInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 拦截的请求,默认拦截所有请求
	 */
	private String[] mappingPath = new String[]{"/**"};
	
	/**
	 * 不拦截的请求,优先级比mappingPath高
	 */
	private String[] excludePath;
	
	/**
	 * 内部调用，匹配请求是否在规则内
	 * @param mapping 规则数组
	 * @param request HttpServletRequest
	 * @return 匹配请求是否在规则内
	 */
	public static boolean match(String[] mapping, HttpServletRequest request){
		if(mapping != null){	
        	String requestURI = request.getRequestURI();
        	//去掉请求里的分号，例如http://localhost:8880/sxcms/index.do;jsessionid=1213123 里的jsessionid
        	int index = requestURI.indexOf(";");
        	if(index != -1){
        		requestURI = requestURI.substring(0, index);
        	}
        	
        	String contextPath = request.getContextPath();
        	if(contextPath.endsWith("/")){
        		contextPath = contextPath.substring(0,contextPath.length() - 1);
    		}
        	//去掉requestURI中的ContextPath部分
        	requestURI = requestURI.substring(contextPath.length());
            AntPathMatcher matcher = new AntPathMatcher();
            for(String path : mapping){
            	if(matcher.match(path, requestURI)){	//匹配成功
                	return true;
                }
            }
        }
		return false;
	}
	
	/**
	 * 是否应该对此请求进行拦截
	 * @param request HttpServletRequest
	 * @return 是否应该对此请求进行拦截
	 */
	protected boolean shouldIntercept(HttpServletRequest request){
		String xString = request.getRequestURL().toString();
		if(match(mappingPath, request)){	//符合拦截的请求的匹配规则，拦截
			if(match(excludePath, request)){	//符合不拦截的请求的匹配规则，不拦截
				return false;
			}
			return true;
		}
		return false;
	}
	
 
	public String[] getMappingPath() {
		return mappingPath;
	}

	public void setMappingPath(String[] mappingPath) {
		this.mappingPath = mappingPath;
	}

	public String[] getExcludePath() {
		return excludePath;
	}

	public void setExcludePath(String[] excludePath) {
		this.excludePath = excludePath;
	}
}