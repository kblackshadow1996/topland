package cn.topland.dto.converter;

import cn.topland.dto.UserDTO;
import cn.topland.entity.User;
import cn.topland.entity.directus.UserDO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter extends BaseConverter<UserDO, UserDTO> {

    @Override
    public List<UserDTO> toDTOs(List<UserDO> users) {

        return CollectionUtils.isEmpty(users)
                ? List.of()
                : users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO toDTO(UserDO user) {

        return user != null
                ? composeUserDTO(user)
                : null;
    }

    private UserDTO composeUserDTO(UserDO user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUserId(user.getUserId());
        userDTO.setEmployeeId(user.getEmployeeId());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setMobile(user.getMobile());
        userDTO.setActive(user.getActive());
        userDTO.setRemark(user.getRemark());
        userDTO.setRole(user.getRole());
        userDTO.setAuth(user.getAuth());
        userDTO.setSource(user.getSource());
        userDTO.setCreator(user.getCreator());
        userDTO.setCreateTime(user.getCreateTime());

        userDTO.setExternalPosition(user.getExternalPosition());
        userDTO.setInternalPosition(user.getInternalPosition());
        userDTO.setDepartments(user.getDepartments());
        userDTO.setLeadDepartments(user.getLeadDepartments());

        // directus登录信息, 用于创建及登录
//        userDTO.setDirectusUser(user.getDirectusUser());
//        userDTO.setDirectusEmail(user.getDirectusEmail());
//        userDTO.setDirectusPassword(user.getDirectusPassword());
//        userDTO.setRefreshToken(user.getRefreshToken());
        userDTO.setAccessToken(user.getAccessToken());

        return userDTO;
    }
}