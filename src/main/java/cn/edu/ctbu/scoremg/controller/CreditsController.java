package cn.edu.ctbu.scoremg.controller;

import cn.edu.ctbu.scoremg.service.CreditsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credits")
public class CreditsController {

    @Autowired
    private CreditsService creditsService;

    @GetMapping("/list3")
    public String getList(){
        return "/credits/list3";
    }
}
