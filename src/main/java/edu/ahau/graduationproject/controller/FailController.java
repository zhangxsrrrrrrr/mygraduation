package edu.ahau.graduationproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangxun_a
 * @date 2021/3/31
 * @Description TODO
 */
@Controller
public class FailController {
    @RequestMapping(value = "/fail")
    public String fail(){
        return "fail.html";
    }
}
