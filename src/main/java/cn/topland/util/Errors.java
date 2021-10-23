package cn.topland.util;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Extensions {

        private String code;

        private String collection;

        private String field;

        private String invalid;

        public Extensions(String code) {

            this.code = code;
        }

        public Extensions(String code, String collection, String field, String invalid) {

            this.code = code;
            this.collection = collection;
            this.field = field;
            this.invalid = invalid;
        }
    }
}