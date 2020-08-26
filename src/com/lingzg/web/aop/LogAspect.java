package com.lingzg.web.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.lingzg.web.annotation.Log;
import com.lingzg.web.common.WebScopeUtil;
import com.lingzg.web.model.SysLog;
import com.lingzg.web.model.SysUser;
import com.lingzg.web.service.SysLogService;
import com.lingzg.web.util.IpUtils;

@Aspect
@Component
public class LogAspect {

	@Autowired
	private SysLogService logService;
 
	@Pointcut("@annotation(com.lingzg.web.annotation.Log)")
	public void pointcut() {
	}
 
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint point) {
		Object result = null;
		long beginTime = System.currentTimeMillis();
		try {
			result = point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		long time = System.currentTimeMillis() - beginTime;
		saveLog(point, time);
		return result;
	}
 
	private void saveLog(ProceedingJoinPoint joinPoint, long time) {
		SysUser user = new SysUser();
		user.setUserName("admin");
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLog log = new SysLog();
		Log logAnnotation = method.getAnnotation(Log.class);
		if (logAnnotation != null) {
			log.setOperation(logAnnotation.value());
		}
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		log.setMethod(className + "." + methodName + "()");
		/*Object[] args = joinPoint.getArgs();
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paramNames = u.getParameterNames(method);
		if (args != null && paramNames != null) {
			JSONObject json = new JSONObject();
			for (int i = 0; i < args.length; i++) {
				json.put(paramNames[i], args[i]);
			}
		}*/
		HttpServletRequest request = WebScopeUtil.getRequest();
		log.setParams(WebScopeUtil.getParam(request));
		log.setIp(IpUtils.getIpAddress(request));
		log.setUsername(user.getUserName());
		log.setTime(time);
		log.setCreateTime(new Date());
		logService.save(log);
	}
}
