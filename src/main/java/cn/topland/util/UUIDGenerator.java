package cn.topland.util;

import java.util.UUID;

/**
 * uuid生成
 */
public final class UUIDGenerator {

    public static String generate() {

        return UUID.randomUUID().toString().replace("-", "");
    }
}