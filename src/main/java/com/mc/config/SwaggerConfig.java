package com.mc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author  yf
 * swagger-ui配置文件设置
 * 本机测试访问方式http://IP:port/swagger-ui.html
 * 正式部署tomcat访问方式http://IP:port/{项目名}/swagger-ui.html
 * @date 创建时间：2017年12月14日 上午10:28:39
 * @version 1.0
 *
 * @author lc
 * 修改整理注释
 * @date 2018/5/7
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${params.swagger.token-name}")
    private String name;
    @Value("${params.swagger.description}")
    private String description;
    @Value("${params.swagger.modelRef}")
    private String modelRef;
    @Value("${params.swagger.parameterType}")
    private String parameterType;
    @Value("${params.swagger.required}")
    private boolean required;
    @Value("${params.swagger.basePackage}")
    private String basePackage;

    @Bean
    public Docket api() {
        //配置全局变量
        ParameterBuilder tokenPar = new ParameterBuilder();
        //配置变量名
        tokenPar.name(name)
                //配置描述，将会在UI中显示
                .description(description)
                //设置参数类型
                .modelRef(new ModelRef(modelRef))
                //指定存放位置，可选header、query、path、body、form
                .parameterType(parameterType)
                //是否必须
                .required(required)
                .build();
        List<Parameter> pars = new ArrayList<Parameter>();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 设置监控路径
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                // 需要处理的基础URL规则，any为默认：/**
                .paths(PathSelectors.any())
                .build()
                //配置参数
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台接口文档与测试")
                .description("这是一个给app端人员调用server端接口的测试文档与平台")
                .version("1.0.0")
                .build();
    }

}

