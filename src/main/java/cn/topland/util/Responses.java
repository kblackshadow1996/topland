package cn.topland.util;

public final class Responses {

    private Responses() {
    }

    public static <T> Response<T> success(T data) {

        return new Response<>(data);
    }

    public static <T> Response<T> fail(int code, String message) {

        return new Response<>(code, message);
    }
}