package cn.topland.dto.converter;

import cn.topland.entity.IdEntity;
import cn.topland.entity.RecordEntity;
import cn.topland.entity.User;
import cn.topland.entity.UuidEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<T, R> {

    public List<R> toDTOs(List<T> ts) {

        return null;
    }

    public R toDTO(T t) {

        return null;
    }

    protected Long getId(IdEntity entity) {

        return entity != null
                ? entity.getId()
                : null;
    }

    protected String getId(UuidEntity entity) {

        return entity != null
                ? entity.getId()
                : null;
    }
}