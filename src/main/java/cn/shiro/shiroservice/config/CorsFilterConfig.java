package cn.shiro.shiroservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


/**
 * 跨域配置扩展
 */

@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsFilterConfig {
    /**
     * 源
     */
    protected List<String> originUrl;
    /**
     * 跨域开关
     */
    private Boolean enable;
 
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
 
    public void setOriginUrl(List<String> originUrl) {
        this.originUrl = originUrl;
    }
 
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        if (enable){
            /*是否允许请求带有验证信息*/
            configuration.setAllowCredentials(true);
            /*允许访问的客户端域名*/
            configuration.setAllowedOrigins(originUrl);
        }else{
            /*是否允许请求带有验证信息*/
            configuration.setAllowCredentials(false);
            /*允许访问的客户端域名*/
            configuration.addAllowedOrigin("*");
        }
        /*允许服务端访问的客户端请求头*/
        configuration.addAllowedHeader("*");
        /*允许访问的方法名,GET POST等*/
        configuration.addAllowedMethod("*");
        configuration.setMaxAge(3600L);
        configurationSource.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(configurationSource);
    }
}