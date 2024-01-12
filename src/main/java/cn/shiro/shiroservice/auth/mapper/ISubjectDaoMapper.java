package cn.shiro.shiroservice.auth.mapper;


import cn.shiro.shiroservice.auth.pojo.User;
import org.apache.ibatis.annotations.*;

/**
 * &#064;Time 2023 十二月 星期二 19:43
 *
 * @author ShangGuan
 */
@Mapper
public interface ISubjectDaoMapper {




    /**
     * 按用户名更新
     *
     * @param sessionId 会话id
     * @param userName  用户名
     * @return int
     */
    @Update("update sys_user set session_id=#{sessionId} where username=#{userName}")
    int updateByUserName(@Param("sessionId") String sessionId,@Param("userName") String userName);



}
