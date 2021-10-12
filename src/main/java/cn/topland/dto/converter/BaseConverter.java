package cn.topland.dto.converter;

import cn.topland.entity.User;

import java.util.List;

public abstract class BaseConverter<T, R> {

    public List<R> toDTOs(List<T> ts) {

        return null;
    }

    public R toDTO(T t) {

        return null;
    }

    protected Long getUserId(User user) {

        return user != null
                ? user.getId()
                : null;
    }
}