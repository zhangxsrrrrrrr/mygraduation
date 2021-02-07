package edu.ahau.graduationproject.mapper;

import edu.ahau.graduationproject.domain.Teacher;
import edu.ahau.graduationproject.dto.CourseOfTeacherDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/2/6.
 *
 * @author Xun Zhang
 */
@Repository
public interface TeacherMapper {
    /**
     * 查询老师的信息
     */
    Teacher selectTeacherInfor(@Param("id") String id);

    /**
     * 更新密码密码
     */
    void updatePassword(@Param("id") String id, @Param("password") String password);

    /**
     * 查询老师所有的课程
     */
    List<String> selectCourseIDById(@Param("id") String teacherID);
    CourseOfTeacherDTO selectStudentNumber(@Param("id") String id);

}
