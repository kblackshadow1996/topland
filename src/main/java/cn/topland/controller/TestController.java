package cn.topland.controller;

import cn.topland.config.WeworkConfig;
import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    private WeworkConfig weworkConfig;

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("wework", weworkConfig);
        return "login";
    }
}