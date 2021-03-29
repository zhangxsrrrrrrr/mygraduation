package edu.ahau.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created on 2021/2/23.
 *
 * @author Xun Zhang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer1 {
    private String userNameAndId;
    private String answerText;
    private String answerImage;
    private Integer answerId;
}
