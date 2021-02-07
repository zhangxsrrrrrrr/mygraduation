package edu.ahau.graduationproject.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import edu.ahau.graduationproject.domain.Teacher;
import edu.ahau.graduationproject.dto.CourseOfTeacherDTO;
import edu.ahau.graduationproject.mapper.TeacherMapper;
import edu.ahau.graduationproject.utils.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 2021/2/6.
 *
 * @author Xun Zhang
 */
@Slf4j
@Secured("ROLE_teacher")
@Controller
@RequestMapping(value = "teacher", method = RequestMethod.POST)
public class TeacherController {
    @Autowired
    private TeacherMapper teacherMapper;
    @GetMapping("/information")
    public String information(HttpServletRequest request,
                              Model model){
        Teacher teacher = teacherMapper.selectTeacherInfor(IDUtil.getID(request));
        model.addAttribute("teacher",teacher);
        return "teacher/information";
    }

    /**
     * 跳转到password页面
     * @return
     */
    @GetMapping("/password")
    public String password(){
        return "teacher/password";
    }
    //修改密码
    @PostMapping("/updatepassword")
    public String updatePassword(@RequestParam("newPassword")String password,
                                 HttpServletRequest request,
                                 Model model){
        if (password==null){
            model.addAttribute("msg","密码不可为空");
            return "teacher/password";
        }
        teacherMapper.updatePassword(IDUtil.getID(request),password);
        model.addAttribute("msg","修改密码成功");
        return "teacher/password";
    }

    /**
     * 跳转到课程页面
     */
    @GetMapping("/course")
    public String course(){
        return "teacher/courses";
    }

    @ResponseJSONP
    @ResponseBody
    @GetMapping("/viewCourses")
    public Object viewCourse(HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        List<CourseOfTeacherDTO> dtos = new ArrayList<>();
        //查询课程的信息
        List<String> ids = teacherMapper.selectCourseIDById(IDUtil.getID(request));

        for (String id:
             ids) {
            dtos.add(teacherMapper.selectStudentNumber(id));
        }

        map.put("code",0);
        map.put("msg","");
        map.put("data",dtos);
        map.put("count",2);
        log.info("{}",JSONObject.parse(JSON.toJSONString(map)));
        Object result =  JSONObject.parse(JSON.toJSONString(map));


        return result;
    }
}
