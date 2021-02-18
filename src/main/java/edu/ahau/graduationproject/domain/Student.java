package edu.ahau.graduationproject.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants.Exclude;

/**
 * Created on 2021/2/4.
 *
 * @author Xun Zhang
 */
@TableName("stu_infor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String stuId;
    private String stuName;
    private String stuPassword;
    @TableField(exist = false)
    private String stuQQ;
    @TableField(exist = false)
    private String stuPhoneNumber;
    @TableField(exist = false)
    private String stuMajor;


}
