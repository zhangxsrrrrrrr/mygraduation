package edu.ahau.graduationproject.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created on 2021/2/5.
 *
 * @author Xun Zhang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGradesDTO implements Serializable {
    private static final long serialVersionUID = -4532301146203825637L;
//    @JSONField(name = "teacher")
    public String teacher;
//    @JSONField(name = "course")
    public String course;
//    @JSONField(name = "grade")
    public String grade;
}
