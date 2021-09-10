package cn.topland.util.annotation.bind;

import java.lang.annotation.*;

/**
 * 自定义SessionUser注解
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionUser {
}