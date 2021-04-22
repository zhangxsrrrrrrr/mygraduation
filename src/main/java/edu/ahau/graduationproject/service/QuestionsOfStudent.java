package edu.ahau.graduationproject.service;

import edu.ahau.graduationproject.domain.Answer;
import edu.ahau.graduationproject.domain.Question;
import edu.ahau.graduationproject.dto.AnswerDTO;

import java.util.List;

/**
 * Created on 2021/2/25.
 *
 * @author Xun Zhang
 */
public interface QuestionsOfStudent {
    List<Question> viewQuestions(String userId);
    List<Answer> viewAnswers(int id);
}
