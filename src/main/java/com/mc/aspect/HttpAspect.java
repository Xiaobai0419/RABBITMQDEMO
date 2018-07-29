package com.mc.aspect;

import com.mc.annotation.Log;
import com.mc.util.WebTokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Aop拦截Controller记录日志
 * 
 * @author klaus
 *
 */
@Aspect
@Component
public class HttpAspect {
	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);


	@Pointcut("execution(public * com.mc.service.*.*(..))")
	public void log() {
	}

	@Before("log()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// url
		logger.info("url={}", request.getRequestURL());

		// method
		logger.info("method={}", request.getMethod());

		// ip
		logger.info("ip={}", request.getRemoteAddr());

		// 类方法

		logger.info("class_method={}",
				joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

		// 参数
		logger.info("args={}", joinPoint.getArgs());
	}
	@After("log()")
	public void doAfter(JoinPoint joinPoint)  throws  ClassNotFoundException {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 拿到切点的类名、方法名、方法参数
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		// 反射加载切点类，遍历类中所有的方法
		Class<?> targetClass = Class.forName(className);
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			// 如果遍历到类中的方法名与切点的方法名一致，并且参数个数也一致，就说明切点找到了
			if (method.getName().equalsIgnoreCase(methodName)) {
				Class<?>[] clazzs = method.getParameterTypes();
				if (clazzs.length == args.length) {
					// 获取到切点上的注解
					Log logAnnotation = method.getAnnotation(Log.class);
					if (logAnnotation != null) {
						String idstr= WebTokenUtil.parseWebToken(request.getHeader("webtoken"), "user").toString();

						//获取参数
						StringBuffer str=new StringBuffer();
						for (int i = 0; i <args.length ; i++) {
							if(i!=0){
							str.append(",");
							}
							str.append(args[i]);
						}
						// 获取注解中的日志信息，并输出
						String logStr = logAnnotation.logStr();
						logger.info("获取日志：" + logStr);
						// 数据库记录操作...

						break;
					}
				}
			}
		}

	}

}
