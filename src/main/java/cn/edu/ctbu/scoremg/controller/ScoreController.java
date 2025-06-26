package cn.edu.ctbu.scoremg.controller;

import cn.edu.ctbu.scoremg.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService studentService;

    @GetMapping("/list2")
    public String getList() {

        return "/score/list2";
    }
}
