package cn.shiro.shiroservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * &#064;Time 2024 一月 星期三 23:36
 *
 * @author ShangGuan
 */
@Configuration
@MapperScan("cn.shiro.**.mapper.**")
public class CoreConfig {
}
