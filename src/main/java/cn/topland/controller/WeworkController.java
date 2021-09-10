package cn.topland.controller;

import cn.topland.config.WeworkConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wework")
public class WeworkController {

    @Autowired
    private WeworkConfig wework;

    @RequestMapping("/login")
    public String login(Model model) {

        model.addAttribute("wework", wework);
        return "login";
    }
}