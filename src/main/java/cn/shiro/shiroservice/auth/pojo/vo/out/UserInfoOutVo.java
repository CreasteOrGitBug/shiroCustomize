package cn.shiro.shiroservice.auth.pojo.vo.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * &#064;Time 2023 十二月 星期四 15:05
 *
 * @author ShangGuan
 */
@Data
@ToString
public class UserInfoOutVo {

    private String userName;

    private String fallName;

    private String sex;

    /**
     * 过期帐户
     */
    private String accountToken;
    /**
     * 过期帐户时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireAccountTime;
}
