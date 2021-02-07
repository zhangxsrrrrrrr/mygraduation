package edu.ahau.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2021/2/7.
 *
 * @author Xun Zhang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseOfTeacherDTO implements Serializable {
    private static final long serialVersionUID = -5787672682972533433L;
    private String courseId;
    private String courseName;
    private int studentNumber;
}
