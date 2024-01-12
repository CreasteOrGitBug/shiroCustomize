package cn.shiro.shiroservice.auth.controller;

import cn.shiro.shiroservice.common.enums.SexEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * &#064;Time 2024 一月 星期四 13:16
 *
 * @author ShangGuan
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


    @GetMapping("/login")
    public void login(SexEnum sexEnum){
        System.out.println(sexEnum);
    }
}
