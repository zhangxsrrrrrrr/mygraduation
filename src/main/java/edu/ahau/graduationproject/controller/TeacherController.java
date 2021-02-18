package edu.ahau.graduationproject.controller;

import com.alibaba.excel.ExcelReader;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import com.sun.javafx.collections.MappingChange;
import edu.ahau.graduationproject.domain.Teacher;
import edu.ahau.graduationproject.dto.AllStudentGradesDTO;
import edu.ahau.graduationproject.dto.CourseOfTeacherDTO;
import edu.ahau.graduationproject.mapper.ImportFileMapper;
import edu.ahau.graduationproject.mapper.TeacherMapper;
import edu.ahau.graduationproject.utils.ExcelUtil;
import edu.ahau.graduationproject.utils.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

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

    @Autowired
    private ImportFileMapper fileMapper;

    @GetMapping("/addgrades")
    public String jumpToHtml() {

        return "teacher/addgrades";
    }

    @GetMapping("/information")
    public String information(HttpServletRequest request,
                              Model model) {
        Teacher teacher = teacherMapper.selectTeacherInfor(IDUtil.getID(request));
        model.addAttribute("teacher", teacher);
        return "teacher/information";
    }

    /**
     * 跳转到password页面
     *
     * @return
     */
    @GetMapping("/password")
    public String password() {
        return "teacher/password";
    }

    /**
     * 修改密码
     * @param password
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/updatepassword")
    public String updatePassword(@RequestParam("newPassword") String password,
                                 HttpServletRequest request,
                                 Model model) {
        if (password == null) {
            model.addAttribute("msg", "密码不可为空");
            return "teacher/password";
        }
        teacherMapper.updatePassword(IDUtil.getID(request), password);
        model.addAttribute("msg", "修改密码成功");
        return "teacher/password";
    }

    /**
     * 跳转到课程页面
     */
    @GetMapping("/course")
    public String course() {
        return "teacher/courses";
    }

    @ResponseJSONP
    @ResponseBody
    @GetMapping("/viewCourses")
    public Object viewCourse(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        List<CourseOfTeacherDTO> dtos = new ArrayList<>();
        //查询课程的信息
        List<String> ids = teacherMapper.selectCourseIDById(IDUtil.getID(request));

        for (String id :
                ids) {
            dtos.add(teacherMapper.selectStudentNumber(id));
        }

        map.put("code", 0);
        map.put("msg", "");
        map.put("data", dtos);
        map.put("count", 2);
        log.info("{}", JSONObject.parse(JSON.toJSONString(map)));
        Object result = JSONObject.parse(JSON.toJSONString(map));


        return result;
    }

    @GetMapping("/grades")
    public String grades() {
        return "teacher/viewgrades";
    }

    @GetMapping("/selectid")
    public String selectCourse(HttpServletRequest request,
                               Model model,
                               @RequestParam(value = "courseid") String courseid) {
        HttpSession session = request.getSession();
        HashMap<String, Object> hashMap = new HashMap<>();
        List<CourseOfTeacherDTO> dtos = new ArrayList<>();
        //查询课程的信息
        List<String> ids = teacherMapper.selectCourseIDById(IDUtil.getID(request));

        log.info(courseid);
        if (!ids.contains(courseid)) {
            hashMap.put("msg", "输入的课程号不正确，请重新输入");
            hashMap.put("isRemove", "1");
            model.addAllAttributes(hashMap);
        } else {
            if (session.getAttribute("courseid") != null) {
                session.removeAttribute("courseid");
                session.setAttribute("courseid", courseid);
            } else {
                session.setAttribute("courseid", courseid);
            }
        }
        return "teacher/viewgrades";
    }

    @ResponseJSONP
    @ResponseBody
    @GetMapping("/viewgrades")
    public Object allStudentGrade(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> map = new HashMap<>();


        String courseid = (String) request.getSession().getAttribute("courseid");

        //查询课程的信息
        List<String> ids = teacherMapper.selectCourseIDById(IDUtil.getID(request));
        if (!ids.contains(courseid)) {

            request.getRequestDispatcher("teacher/grades").forward(request, response);
            return null;
        }
        List<AllStudentGradesDTO> allStudentGradesDTOS = teacherMapper.selectAllStudentGrades(courseid);
        map.put("code", 0);
        map.put("msg", "");
        map.put("data", allStudentGradesDTOS);
        map.put("count", 2);
        log.info("{}", JSONObject.parse(JSON.toJSONString(map)));
        return JSONObject.parse(JSON.toJSONString(map));
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Map<String, Object> updateGrades(@RequestBody AllStudentGradesDTO student ){
        log.info("修改的学生{}",student);
        HashMap<String, Object> map = new HashMap<>();
        int result = teacherMapper.updateGradesById(student.getId(),student.getGrade(),student.getCourseId());
        if (result > 0) {
            map.put("status", 1);
        } else {
            map.put("status", 0);
        }
        return map;
    }

    @GetMapping("/add")
    public String add(){
        return "teacher/addStudent";
    }
    @ResponseBody
    @PostMapping("/authorization")
    public Object authorization(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String  pathString = null;

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        //获取上传文件
        MultipartFile multipartFile = multipartRequest.getFile("file");
        assert multipartFile != null;
        String filename = multipartFile.getOriginalFilename();

        //保存的文件名
        pathString = "E:/upload/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +filename;


        File files=new File(pathString);

        //暂时存放excel表格的文件夹
        File cacheFile = new File("E:/upload");
        if (!cacheFile.exists()){
            boolean mkdir = cacheFile.mkdir();
        }

        try {
            multipartFile.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelUtil.simpleRead(fileMapper,pathString);
        if (cacheFile.exists()){
            boolean delete = files.delete();
        }
        return JSONObject.parse(JSON.toJSONString(map));
    }

    @GetMapping("/up")
    public String up(){
        return "teacher/up";
    }

    @ResponseBody
    @GetMapping("/upFiles")
    public Object upFiles(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        HashMap<String, Object> map = new HashMap<>();
        String pathString = null;

        map.put("code",0);
        map.put("msg","");

        //获取上传文件
        MultipartFile multipartFile = multipartRequest.getFile("file");
        //获取文件上传名
        String originalFilename = multipartFile.getOriginalFilename();
        pathString = "G:/graduation/" + IDUtil.getID(request)+ "/" +IDUtil.getID(request) +
                     new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +
                     "_" + originalFilename;
        File file = new File(pathString);

        //个人的专属文件
        String personalFloadPath = "G:/graduation/" + IDUtil.getID(request)+ "/";
        File  personalFload = new File(personalFloadPath);
        if (!personalFload.exists()){
            boolean mkdirs = personalFload.mkdirs();
        }
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parse(JSON.toJSONString(map));
    }

}
