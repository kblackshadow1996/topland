package cn.topland.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UniqueException extends RuntimeException {

    public UniqueException(String message) {
        super(message);
    }
}