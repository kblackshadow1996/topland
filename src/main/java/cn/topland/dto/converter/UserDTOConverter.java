package cn.topland.dto.converter;

import cn.topland.dto.UserDTO;
import cn.topland.entity.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class UserDTOConverter {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public UserDTO toUserDTO(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setEmployeeId(user.getEmployeeId());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setMobile(user.getMobile());
        userDTO.setActive(user.getActive());
        userDTO.setRemark(user.getRemark());
        userDTO.setCreateTime(DateTimeFormatter.ofPattern(DATE_FORMAT).format(user.getCreateTime()));
        userDTO.setCreator(user.getCreator());

        userDTO.setDataAuth(user.getAuth().name());
        userDTO.setRoles(user.roles());
        userDTO.setAuthorities(user.authorities());

        return userDTO;
    }
}