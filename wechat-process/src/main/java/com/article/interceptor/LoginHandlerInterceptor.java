package com.article.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	private String[] excludePath;

	public String[] getExcludePath() {
		return excludePath;
	}

	public void setExcludePath(String[] excludePath) {
		this.excludePath = excludePath;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String paths = request.getServletPath();
		String path = request.getContextPath();
		String ctx = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/resource";
		String basepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		request.setAttribute("ctx", ctx);
		request.setAttribute("path", path);
		request.setAttribute("basepath", basepath);
		// 不是应该拦截的请求 ，放行
		if (BaseInterceptor.match(excludePath, request)) {
			return true;
		}
		return true;
	}

}
