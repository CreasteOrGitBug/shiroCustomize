package cn.shiro.shiroservice.config;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Map;

/**
 * &#064;Time 2024 一月 星期五 18:24
 *
 * @author ShangGuan
 */
@Configuration
public class FilterConfig implements CommandLineRunner {

    @Value("${shiro.println.is-enable:true}")
    private boolean isEnable;
    private static final Logger logger = LoggerFactory.getLogger(FilterConfig.class);

    /**
     * 打印filter的class信息
     */

    public void postProcessPrintlnClassFilterInfo(){
        DefaultFilterChainManager filterChainManager = new DefaultFilterChainManager();
        Map<String, Filter> filters = filterChainManager.getFilters();
        logger.info("shiro拦截器打印----------->开始");
        for (Map.Entry<String, Filter> entry : filters.entrySet()) {
            String name = entry.getKey();
            Filter filter = entry.getValue();
            logger.info("Filter Name:{} , Class: {}" ,name,filter.getClass().getName());
        }
        logger.info("shiro打印---------------->结束");

    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if(isEnable){
                postProcessPrintlnClassFilterInfo();
            }else{
                logger.info("shiro拦截器打印---------------->关闭");
            }
        }catch (Exception exception){
            throw new Exception("打印shiro拦截器异常");
        }
    }

    public static void main(String[] args) {
        FilterConfig filterConfig = new FilterConfig();

        System.out.println(filterConfig.isEnable);
    }
}
