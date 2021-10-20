package cn.topland.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UniqueException extends RuntimeException {

    private String collection;

    private String field;

    private String invalid;

    @Override
    public String getMessage() {

        return "Field " + "'" + field + "'" + " has to be unique.";
    }
}