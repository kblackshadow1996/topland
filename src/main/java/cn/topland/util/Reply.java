package cn.topland.util;

import javax.ws.rs.core.Response;

/**
 * web方法返回
 */
public final class Reply {

    private Response.Status status;

    private String content;

    public Reply(Response.Status status, String content) {
        this.status = status;
        this.content = content;
    }

    public Response.Status getStatus() {
        return this.status;
    }

    public String getContent() {
        return this.content;
    }
}