package cn.shiro.shiroservice.common.service;

import cn.hutool.core.util.StrUtil;
import cn.shiro.shiroservice.config.FilterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * &#064;Time 2024 一月 星期四 15:22
 *
 * @author ShangGuan
 */
@Component
public class SimpleCache implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(FilterConfig.class);


    private String str;


    @Override
    public String getStr() {
        return str;
    }

    @Override
    public boolean setStr(String str) {
        if(StrUtil.isNotBlank(str)){
            this.str=str;
            return true;
        }
        logger.info("str is not null");
        return false;
    }
}
