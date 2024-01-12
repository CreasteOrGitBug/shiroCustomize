package cn.shiro.shiroservice.auth.mapper;

import cn.shiro.shiroservice.auth.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * &#064;Time 2024 一月 星期四 10:31
 *
 * @author ShangGuan
 */
@Mapper
public interface UserMapper {


    /**
     * 按用户名查询
     *
     * @param userName 用户名
     * @return {@link User}
     */

    User queryByUserName(@Param("userName") String userName);

    /**
     * 保存
     *
     * @param user 使用者
     * @return int
     */

    int save(@Param("data")User user);
}
