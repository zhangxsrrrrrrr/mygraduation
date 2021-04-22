package edu.ahau.graduationproject.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.VariableLengthInstruction;
import com.sun.org.apache.regexp.internal.RE;
import edu.ahau.graduationproject.domain.Answer;
import edu.ahau.graduationproject.domain.Question;
import edu.ahau.graduationproject.domain.Student;
import edu.ahau.graduationproject.dto.*;
import edu.ahau.graduationproject.mapper.StudentMapper;
import edu.ahau.graduationproject.service.AnswerService;
import edu.ahau.graduationproject.service.QuestionsOfStudent;
import edu.ahau.graduationproject.utils.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2021/2/4.
 *
 * @author Xun Zhang
 */
@Slf4j
@Secured("ROLE_student")
@RequestMapping(value = "student", method = {RequestMethod.POST})
@Controller
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionsOfStudent questionsOfStudent;

    @ResponseBody
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    /**
     * 获取学生信息
     * @param model,request
     * @return
     */
    @GetMapping("/information")
    public String information(HttpServletRequest request, Model model){
        Student student = studentMapper.selectStudentByID(IDUtil.getID(request));
        model.addAttribute("information",student);
        return "student/information";
    }

    @GetMapping("/bind")
    public String bindHtml(){
        return "student/bind";
    }
    /**
     * 绑定手机号和QQ
     * @param phonenumber
     * @param qq
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/doBind")
    public String bind(@RequestParam(value = "phonenumber", required = false)String phonenumber,
                       @RequestParam(value = "qq", required = false)String qq,
                       HttpServletRequest request,
                       Model model){
        if (phonenumber==null&&qq==null){
            model.addAttribute("message","QQ和电话号码不可同时为空");
        }
        studentMapper.bindPhoneNumberOrQQ(phonenumber,qq,IDUtil.getID(request));
        model.addAttribute("message","完成绑定");
        return "student/bind";
    }

    /**
     * 查询学生的所有成绩
     * @param request
     * @return
     */
    @ResponseBody
    @ResponseJSONP(callback = "callback")
    @GetMapping("/getGrade")
    public Object toGrade(HttpServletRequest request){

        List<StudentGradesDTO> studentGradesDTOS = studentMapper.selectStudentGradesByID(IDUtil.getID(request));
//        List<StudentGradesDTO> studentGradesDTOS = studentMapper.selectStudentGradesByID("17112003");
        HashMap<String, Object> map = new HashMap<>();
        map.put("data",studentGradesDTOS);
        map.put("code",0);
        map.put("msg","");
        map.put("count",2);
        Object parse = JSONObject.parse(JSON.toJSONString(map));
        log.info("{}",parse);
        return parse;
    }

    //跳转到grade页面
    @GetMapping("/grade")
    public String grade(){
        return "/student/grade";
    }

    //修改密码
    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("newPassword") String password
                                ,Model model
                                ,HttpServletRequest request){
        studentMapper.updatePassword(IDUtil.getID(request),password);
        model.addAttribute("msg","修改成功");
        return "student/password";
    }

    //跳转到修改密码的页面
    @GetMapping("/password")
    public String password(){
        return "student/password";
    }

    //跳转到teacherinfor页面
    @GetMapping("/teacherinfor")
    public String teacherInfor(){
        return "student/teacherinfor";
    }

    //获取老师的信息
    @ResponseBody
    @ResponseJSONP
    @GetMapping("/teacherInfor")
    public Object obtainTeacherInFor(HttpServletRequest request){
        HashMap<String, Object> map = new HashMap();

        List<TeacherOfStudentInforDTO> teacherInfor = studentMapper.selectTeacherInforById(IDUtil.getID(request));
        map.put("code",0);
        map.put("msg","");
        map.put("data",teacherInfor);
        map.put("count",2);

        //返回给前端老师信息
        Object parse = JSONObject.parse(JSON.toJSONString(map));
        log.info("老师信息{}",parse);
        return parse;
    }

    @GetMapping("/myproblem")
    public String viewMyProblem(HttpServletRequest request,Model model){
        String id = IDUtil.getID(request);
        List<Question> questions = questionsOfStudent.viewQuestions(id);
        List<OwnQuestionAndAnswer> viewQuestions = new ArrayList<>();

        Student student = studentMapper.selectStudentByID(id);
        String viewName = id+"-"+student.getStuName();
        Iterator<Question> iterator = questions.iterator();

        while (iterator.hasNext()){
            List<String> images = new ArrayList<>();
            Question question = iterator.next();
            String imagePath = question.getFileName();
            String[] files = imagePath.split("@");
            if (files.length != 0) {
                String webPrefix = "/files/"+id+"/"+question.getQuestionId()+"/";

                for (int i = 0; i < files.length; i++) {
                    String path = webPrefix + files[i];
                    images.add(path);
                }
            }
            //问题
            AnswerDTO answerDTO = new AnswerDTO();
            answerDTO.setUserNameAndId(viewName);
            answerDTO.setQuestionImage(images);
            answerDTO.setQuestionText(question.getTextArea());
            //回复
            ArrayList<Answer1> answersList = new ArrayList<>();
            int questionId = question.getQuestionId();
            List<Answer> answers = questionsOfStudent.viewAnswers(question.getQuestionId());
            for (Answer answer:
            answers) {
                String photoName = answer.getPhotoName().replace("G:/graduationQuestion/", "/files/");
                Answer1 answer1 = new Answer1();
                if (photoName.endsWith(".jpg")) {
                    answer1.setAnswerImage(photoName);
                }
                answer1.setAnswerText(answer.getTextArea());
                String name = answer.getUserId() + answer.getUserName();
                answer1.setUserNameAndId(name);
                answersList.add(answer1);
            }


            OwnQuestionAndAnswer ownQuestionAndAnswer = new OwnQuestionAndAnswer();
            ownQuestionAndAnswer.setQuestion(answerDTO);
            ownQuestionAndAnswer.setAnswer(answersList);
            viewQuestions.add(ownQuestionAndAnswer);
        }
        model.addAttribute("questions",viewQuestions);
        return "student/myproblem";
    }
}
