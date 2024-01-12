package cn.shiro.shiroservice;

import cn.shiro.shiroservice.auth.convers.UserInfoOutVOConvers;
import cn.shiro.shiroservice.auth.mapper.ISubjectDaoMapper;
import cn.shiro.shiroservice.auth.mapper.UserMapper;
import cn.shiro.shiroservice.auth.pojo.User;
import cn.shiro.shiroservice.auth.pojo.vo.out.UserInfoOutVo;
import cn.shiro.shiroservice.common.enums.SexEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class ShiroServiceApplicationTests {

    @Resource

    private SecurityManager securityManager;

    @Test
    void contextLoads() {

    }

    /**
     * 登录
     */
    @Test
    public void login() {
        ///1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("sda", "sad");
        subject.login(usernamePasswordToken);
    }

    @Test
    public void auth(){
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("sda", "sad");
        subject.login(usernamePasswordToken);
        subject.logout();
        boolean admin = subject.hasRole("admin");
        System.out.println(admin);
    }

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserInfoOutVOConvers userInfoOutVOConvers;

    @Test
    public void userMapper() throws SQLException {

        User user = userMapper.queryByUserName("admin");
        UserInfoOutVo entity = userInfoOutVOConvers.toEntity(user);
        System.out.println(entity);
    }


}
