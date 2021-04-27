package edu.ahau.graduationproject.service.impl;

import edu.ahau.graduationproject.domain.Student;
import edu.ahau.graduationproject.domain.Teacher;
import edu.ahau.graduationproject.mapper.*;
import edu.ahau.graduationproject.service.AnswerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/2/22.
 *
 * @author Xun Zhang
 */
@Slf4j
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private TopMapper topMapper;
    @Override
    public String findAnswerImage(int answerId) {
        String fileString = answerMapper.selectAnswerImage(answerId);

        return fileString;

    }

    @Override
    public String findAnswerText(int id) {
        return answerMapper.selectAnswerText(id);
    }

    @Override
    public String findAnserId(int id) {
        String s = answerMapper.selectName(id);
        String s1 = answerMapper.selectId(id);
        return s+"-"+s1;
    }

    @Override
    public List<String> findQuestionImage(int questionId) {
        String fileString = questionMapper.selectQuestionImage(questionId);
        String path = null;
        String[] split = fileString.split("@");
        List<String> splitFiles = new ArrayList<>(9);

        for (String s: split
             ) {
            String pathValue = "file:///G:/graduationQuestion";
            path = "/files"+"/"+questionMapper.selectQuestionUpper(questionId)+"/"+questionId+"/"+s;
            splitFiles.add(path);
        }
        return splitFiles;
    }

    @Override
    public String findQuestionText(int questionId) {
        String userId = questionMapper.selectQuestionText(questionId);
        return userId;
    }

    @Override
    public String findQuestionUserId(int questionId) {
        return questionMapper.selectQuestionUpper(questionId);
    }

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public String findUserName(String id) {
        Student student = studentMapper.selectStudentByID(id);
        Teacher teacher = teacherMapper.selectTeacherInfor(id);
        if (student==null){
            return teacher.getTchName();
        } else {
            return student.getStuName();
        }

    }

    @Override
    public Integer findFlag(int id) {

        return topMapper.selectAnswerFlag(id);
    }


}
