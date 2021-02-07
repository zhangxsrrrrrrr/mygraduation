package edu.ahau.graduationproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2021/2/3.
 *
 * @author Xun Zhang
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(HttpServletRequest request){
        return "login";
    }
}
