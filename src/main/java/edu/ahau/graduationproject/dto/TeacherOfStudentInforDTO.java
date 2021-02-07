package edu.ahau.graduationproject.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created on 2021/2/6.
 *
 * @author Xun Zhang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherOfStudentInforDTO implements Serializable {
    private static final long serialVersionUID = -1192458617625400384L;
    private String id;
    private String course;
    private String teacherName;
    private String QQ;
    private String phoneNumber;
}
