package com.mc;


import com.mc.aspect.HTTPBearerAuthorizeAttribute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


/**
 * @author lc
 * @date  2018/5/7
 */
@SpringBootApplication
public class Application {

    /**
     * @功能描述 注册拦截器
     */
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new HTTPBearerAuthorizeAttribute());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}