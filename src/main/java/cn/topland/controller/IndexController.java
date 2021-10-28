package cn.topland.controller;

import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 */
@RestController
public class IndexController {

    /**
     * 心跳
     *
     * @return
     */
    @GetMapping("/heartbeat")
    public Response<String> heartbeat() {

        return Responses.success("hello world!");
    }
}