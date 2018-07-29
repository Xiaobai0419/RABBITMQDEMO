package com.mc.aspect;

import com.mc.ReturnCode;
import com.mc.redis.RedisObjectService;
import com.mc.util.WebTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 拦截器
 * 
 * @author yf Http拦截器，处理webtoken
 * @author 修改对jwt自动刷新
 * @date 创建时间：2017年12月1日 下午6:55:55
 * @version 1.0
 */
public class HTTPBearerAuthorizeAttribute implements Filter {


	private RedisObjectService<String> redisObjectService;

	@Autowired
	public void setRedisObjectService(RedisObjectService<String> redisObjectService ){
		this.redisObjectService = redisObjectService;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		// 登录和测试接口放行，其余接口全部监听
		boolean isLogin = httpRequest.getRequestURL().toString().endsWith("login");
		boolean isExit = httpRequest.getRequestURL().toString().endsWith("exit");
		boolean isUpload = httpRequest.getRequestURL().toString().contains("uploadDeviceImage");
		boolean isDownLoad = httpRequest.getRequestURL().toString().contains("download");

		if (isLogin || isExit ||  isUpload || isDownLoad  ) {
			chain.doFilter(request, response);
			return;
		}

		// 从请求header中拿出webtoken
		String webtoken =  httpRequest.getHeader("webtoken");
		String userId = WebTokenUtil.parseWebToken(webtoken , "user").toString();
		if ((userId != null) && redisObjectService.exist(userId + "online") && webtoken.equals(redisObjectService.get(userId + "online"))) {
				redisObjectService.set(userId + "online", webtoken, 10, TimeUnit.MINUTES);
				chain.doFilter(request, response);
				return;
		} 

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ObjectMapper mapper = new ObjectMapper();
		httpResponse.getWriter()
				.write(mapper.writeValueAsString(new ReturnCode(false,"未登录或登录超时",null)));
		return;
	}

	@Override
	public void destroy() {
	}
}
