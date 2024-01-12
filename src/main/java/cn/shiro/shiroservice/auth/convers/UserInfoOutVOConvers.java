package cn.shiro.shiroservice.auth.convers;

import cn.shiro.shiroservice.auth.pojo.User;
import cn.shiro.shiroservice.auth.pojo.vo.out.UserInfoOutVo;
import cn.shiro.shiroservice.common.enums.SexEnum;
import cn.shiro.shiroservice.common.service.MapperStructService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

/**
 * &#064;Time 2024 一月 星期四 13:01
 *
 * @author ShangGuan
 */
@Mapper(componentModel = "spring")
public interface UserInfoOutVOConvers extends MapperStructService<User, UserInfoOutVo> {


    @Override
    @Mappings({
            @Mapping(target = "sex", source = "sex", qualifiedByName = "mapSexEnumToString"),
    })
    UserInfoOutVo toEntity(User param);

    @Named("mapSexEnumToString")
    default String mapSexEnumToString(SexEnum sexEnum){
        return sexEnum.getTypeName();
    };

    @Override
    public List<UserInfoOutVo> toList(List<User> param);
}
