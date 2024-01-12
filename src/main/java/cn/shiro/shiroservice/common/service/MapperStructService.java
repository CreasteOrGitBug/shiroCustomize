package cn.shiro.shiroservice.common.service;

import java.util.List;

/**
 * &#064;Time 2024 一月 星期四 12:59
 *
 * @author ShangGuan
 */

public interface MapperStructService<T,E> {

    E toEntity(T param);

    List<E> toList(List<T> param);
}
