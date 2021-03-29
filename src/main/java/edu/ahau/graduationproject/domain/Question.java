package edu.ahau.graduationproject.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created on 2021/2/20.
 *
 * @author Xun Zhang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("question")
public class Question {
    private String fileName;
    private String userId;
    private String textArea;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;
    @TableField(exist = false)
    private Integer questionId;
    private String userName;
}
