package com.article.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("login")
public class LoginController {

	@RequestMapping(value = "toLogin", method = RequestMethod.GET)
	public String toLogin(HttpServletRequest request) {
		request.setAttribute("test", "123");
		return "login";
	}
	
	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	@ResponseBody
	public String doLogin(HttpServletRequest request) {
		request.setAttribute("test", "123");
		Map<Object,Object> map = new HashMap<>();
		map.put("test", "test");
		JSONObject fromObject = JSONObject.fromObject(map);
		return "index";
	}

}
