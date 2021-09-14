package cn.topland.controller;

import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/heartbeat")
    public Response heartbeat() {

        return Responses.success("hello world!");
    }
}