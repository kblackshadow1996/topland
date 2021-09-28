package cn.topland.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class Errors implements Serializable {

    private String message;

    private Extensions extensions;

    @Getter
    @Setter
    public static class Extensions {

        private String code;

        public Extensions(String code) {

            this.code = code;
        }
    }
}