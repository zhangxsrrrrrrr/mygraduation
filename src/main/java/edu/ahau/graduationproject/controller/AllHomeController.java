package edu.ahau.graduationproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created on 2021/2/4.
 *
 * @author Xun Zhang
 */
@Controller
public class AllHomeController {
    @GetMapping("all/home")
    public String allHomePage(){
        return "login";
    }
}
