package cn.topland.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class AuthVO implements Serializable {

    private Long role;

    private List<Long> auths;
}