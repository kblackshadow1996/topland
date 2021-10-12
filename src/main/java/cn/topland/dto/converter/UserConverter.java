package cn.topland.dto.converter;

import cn.topland.dto.UserDTO;
import cn.topland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private DepartmentConverter departmentConverter;

    public List<UserDTO> toUsersDTOs(List<User> users) {

        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    public UserDTO toUserDTO(User user) {

        return user != null
                ? composeUserDTO(user)
                : null;
    }

    private UserDTO composeUserDTO(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUserId(user.getUserId());
        userDTO.setEmployeeId(user.getEmployeeId());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setMobile(user.getMobile());
        userDTO.setActive(user.getActive());
        userDTO.setRemark(user.getRemark());
        userDTO.setSource(user.getSource());
        userDTO.setCreatorId(userDTO.getCreatorId());
        userDTO.setCreateTime(DateTimeFormatter.ofPattern(DATE_FORMAT).format(user.getCreateTime()));

        userDTO.setExternalPosition(user.getExternalPosition());
        userDTO.setInternalPosition(user.getInternalPosition());
        userDTO.setDepartments(departmentConverter.toDepartmentDTOs(user.getDepartments()));
        userDTO.setLeadDepartments(user.getLeadDepartments());

        // directus登录信息, 用于创建及登录
        userDTO.setDirectusUserId(user.getDirectusId());
        userDTO.setDirectusEmail(user.getDirectusEmail());
        userDTO.setDirectusPassword(user.getDirectusPassword());

        return userDTO;
    }
}