package edu.ahau.graduationproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ahau.graduationproject.dto.UserDTO;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created on 2021/2/4.
 *
 * @author Xun Zhang
 *
 * 查询老师和学生的id，以及password封装到UserDto
 */
@Repository

public interface UserMapper extends BaseMapper<UserDTO> {
     /**
      * 查询老师
      */
     UserDTO selectTeacherById(@Param("id") String id);

     /**
      * 查询学生
      */
     UserDTO selectStudentById(@Param("id") String id);
}
