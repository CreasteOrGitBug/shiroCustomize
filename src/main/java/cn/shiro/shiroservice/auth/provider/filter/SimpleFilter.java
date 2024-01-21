package cn.shiro.shiroservice.auth.provider.filter;

import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * &#064;Time 2024 一月 星期五 17:59
 *
 * @author ShangGuan
 */
public class SimpleFilter  extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

    }
}
