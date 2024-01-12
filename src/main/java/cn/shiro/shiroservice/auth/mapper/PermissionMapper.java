package cn.shiro.shiroservice.auth.mapper;

import cn.shiro.shiroservice.auth.pojo.dto.PermissionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * &#064;Time 2024 一月 星期五 10:41
 *
 * @author ShangGuan
 */
@Mapper
public interface PermissionMapper {


    /**
     * 获得权限信息
     *
     * @param username 用户名
     * @return {@link List}<{@link String}>
     */

    List<String> getPermission(@Param("username") String username);

    /**
     * 获取角色
     *
     * @param username 用户名
     * @return {@link List}<{@link String}>
     */

    List<String> getRole(@Param("username") String username);


    /**
     * 获取权限实体
     *
     * @param username 用户名
     * @return {@link List}<{@link PermissionDTO}>
     */

    List<PermissionDTO> getPermissionDTO(@Param("username") String username);
}
