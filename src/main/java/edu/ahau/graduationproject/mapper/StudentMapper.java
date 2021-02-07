package edu.ahau.graduationproject.mapper;

import edu.ahau.graduationproject.domain.Student;
import edu.ahau.graduationproject.dto.StudentGradesDTO;
import edu.ahau.graduationproject.dto.TeacherOfStudentInforDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/2/5.
 *
 * @author Xun Zhang
 */
@Repository
public interface StudentMapper {
    /**
     * 根据学号查询学生信息
     * @param id
     * @return
     */
    Student selectStudentByID(String id);

    /**
     * 根据学号绑定QQ或者手机号码
     * @param phone
     * @param QQ
     * @param id
     */
    void bindPhoneNumberOrQQ(@Param("phone") String phone, @Param("QQ") String QQ, @Param("id") String id);

    /**
     * 根据学号查询所有科目成绩
     */
    List<StudentGradesDTO> selectStudentGradesByID(@Param("id") String id);

    /**
     * 更新密码密码
     */
    void updatePassword(@Param("id") String id, @Param("password") String password);

    /**
     * 查询到老师的信息
     */
    List<TeacherOfStudentInforDTO> selectTeacherInforById(@Param("id") String id);
}
