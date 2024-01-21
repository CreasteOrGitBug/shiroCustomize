package cn.shiro.shiroservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * &#064;Time 2024 一月 星期三 23:36
 *
 * @author ShangGuan
 */
@Configuration
@MapperScan("cn.shiro.**.mapper.**")
public class CoreConfig {



}
