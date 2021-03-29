package edu.ahau.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.List;

/**
 * Created on 2021/2/23.
 *
 * @author Xun Zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private String userNameAndId;
    private String questionText;
    private List<String> questionImage;
    private Integer id;
}
