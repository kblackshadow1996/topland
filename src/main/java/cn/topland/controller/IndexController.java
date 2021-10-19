package cn.topland.controller;

import cn.topland.config.WeworkConfig;
import cn.topland.util.Response;
import cn.topland.util.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private WeworkConfig weworkConfig;

    @GetMapping("/heartbeat")
    public Response heartbeat() {

        return Responses.success("hello world!");
    }

    @GetMapping("/test/login")
    public String heartbeat(Model model) {

        model.addAttribute("wework", weworkConfig);
        return "/login";
    }
}