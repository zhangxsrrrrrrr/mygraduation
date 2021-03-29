package edu.ahau.graduationproject.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created on 2021/2/24.
 *
 * @author Xun Zhang
 */
@Data

@NoArgsConstructor
@TableName("answer")
public class Answer {
    @TableField(exist = false)
    private Integer answerId;
    private Integer questionId;
    private String textArea;
    private String photoName;
    private Date answerTime;
    private String userId;
    private String userName;

    public Answer(Integer questionId, String textArea, String photoName, Date answerTime, String userId, String userName) {
        this.questionId = questionId;
        this.textArea = textArea;
        this.photoName = photoName;
        this.answerTime = answerTime;
        this.userId = userId;
        this.userName = userName;
    }
}
