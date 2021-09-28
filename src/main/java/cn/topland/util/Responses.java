package cn.topland.util;

public final class Responses {

    private Responses() {
    }

    public static Response success(Object data) {

        return new Response(data);
    }

    public static Response fail(String code, String message) {

        return new Response(code, message);
    }
}