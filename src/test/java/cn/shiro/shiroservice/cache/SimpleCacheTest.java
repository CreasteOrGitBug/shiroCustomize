package cn.shiro.shiroservice.cache;

import cn.shiro.shiroservice.common.service.SimpleCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * &#064;Time 2024 一月 星期四 15:26
 *
 * @author ShangGuan
 */
@SpringBootTest
public class SimpleCacheTest {

    @Resource
    private SimpleCache simpleCache;
    @Test
    public void cacheTest(){
        simpleCache.setStr("asd");
    }
}
