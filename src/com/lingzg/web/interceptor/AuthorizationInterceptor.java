package com.lingzg.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.lingzg.web.annotation.Authorization;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	private final Logger log = Logger.getLogger(getClass().getName());

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token");
		response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		if (method.getAnnotation(Authorization.class) != null) {
			// 注解拦截 token
			try {
				HttpSession session = request.getSession();
				Object obj = session.getAttribute("user");
				if (obj != null) {
					return true;
				} else {
					String requestType = request.getHeader("X-Requested-With");
					if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
						response.addHeader("loginStatus", "accessDenied");
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					}else{
					    response.sendRedirect(request.getContextPath()+"/login.jsp");
					}
				}
				return false;
			} catch (Exception e) {
				log.error(e.toString());
				responseJson(response);
				return false;
			}
		}
		return true;
	}

	public void responseJson(HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		map.put("msg", "未登陆");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(strTojson(map));
		writer.close();
		response.flushBuffer();
	}

	/**
	 * 对象转json字符串
	 * 
	 */
	public static String strTojson(Object obj) {
		String json = JSONObject.toJSONString(obj);
		return json;
	}

}