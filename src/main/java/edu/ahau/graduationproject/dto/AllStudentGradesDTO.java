package edu.ahau.graduationproject.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2021/2/8.
 *
 * @author Xun Zhang
 *
 * 查询某一门课所有学生成绩
 */
@Data
public class AllStudentGradesDTO implements Serializable {
    private static final long serialVersionUID = -7986323315109647110L;
    private String id;
    private String username;
    private String course;
    private String grade;
    private String courseId;
}
