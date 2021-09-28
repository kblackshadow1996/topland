package cn.topland.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class AuthorityVO implements Serializable {

    private Long role;

    private List<Long> auths;
}