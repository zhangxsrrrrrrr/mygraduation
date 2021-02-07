package edu.ahau.graduationproject;



import com.alibaba.fastjson.JSON;
import edu.ahau.graduationproject.dto.CourseOfTeacherDTO;
import edu.ahau.graduationproject.dto.StudentGradesDTO;
import edu.ahau.graduationproject.dto.TeacherOfStudentInforDTO;
import edu.ahau.graduationproject.dto.UserDTO;
import edu.ahau.graduationproject.mapper.StudentMapper;
import edu.ahau.graduationproject.mapper.TeacherMapper;
import edu.ahau.graduationproject.mapper.UserMapper;
import org.apache.velocity.runtime.directive.Foreach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
class GraduationprojectApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Test
    void contextLoads() {
        List<TeacherOfStudentInforDTO> dtos = studentMapper.selectTeacherInforById("17112003");
        System.out.println(dtos.toString());
    }



    @Test
    public void test12(){
        List<StudentGradesDTO> studentGradesDTOS = studentMapper.selectStudentGradesByID("17112003");
        System.out.println(JSON.toJSONString(studentGradesDTOS));
    }

    @Test
    public void test(){
        UserDTO userDTO = userMapper.selectTeacherById("30010");
        System.out.println(userDTO);
    }
    @Autowired
    private TeacherMapper teacherMapper;
    @Test
    public void test113(){


    }
}
