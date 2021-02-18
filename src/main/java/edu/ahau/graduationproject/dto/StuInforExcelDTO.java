package edu.ahau.graduationproject.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2021/2/17.
 *
 * @author Xun Zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuInforExcelDTO {
    @ExcelProperty("姓名")
    private String stuName;
    @ExcelProperty("学号")
    private String stuId;
    @ExcelProperty("课程号")
    private String courseId;

    private static final Double grade;

    static {
        grade = -1.0;
    }

}
