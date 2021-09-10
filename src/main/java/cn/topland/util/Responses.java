package cn.topland.util;

import static cn.topland.util.Response.OK;

public final class Responses {

    private Responses() {
    }

    public static Response success(Object data) {

        return new Response(OK, data);
    }

    public static Response fail(int code, String message) {

        return new Response(code, message);
    }
}