package cn.topland.dto.converter;

import cn.topland.dto.UserDTO;
import cn.topland.entity.Department;
import cn.topland.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter extends BaseConverter<User, UserDTO> {

    @Override
    public List<UserDTO> toDTOs(List<User> users) {

        return CollectionUtils.isEmpty(users)
                ? List.of()
                : users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO toDTO(User user) {

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
        userDTO.setCreator(getId(user));
        userDTO.setCreateTime(user.getCreateTime());

        userDTO.setExternalPosition(user.getExternalPosition());
        userDTO.setInternalPosition(user.getInternalPosition());
        userDTO.setDepartments(listDepartmentIds(user.getDepartments()));
        userDTO.setLeadDepartments(user.getLeadDepartments());

        // directus登录信息, 用于创建及登录
        userDTO.setDirectusUserId(getId(user.getDirectusUser()));
        userDTO.setDirectusEmail(user.getDirectusEmail());
        userDTO.setDirectusPassword(user.getDirectusPassword());

        return userDTO;
    }

    private List<Long> listDepartmentIds(List<Department> departments) {

        return CollectionUtils.isEmpty(departments)
                ? List.of()
                : departments.stream().map(this::getId).collect(Collectors.toList());
    }
}