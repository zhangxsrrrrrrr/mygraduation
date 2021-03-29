package edu.ahau.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 2021/2/25.
 *
 * @author Xun Zhang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnQuestionAndAnswer {
    private List<Answer1> answer;
    private AnswerDTO question;
}
