package edu.ahau.graduationproject.mapper;

import edu.ahau.graduationproject.domain.Answer;
import edu.ahau.graduationproject.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/2/25.
 *
 * @author Xun Zhang
 */
@Repository
public interface MyProblemMapper {
    List<Integer> selectQuestionIds(String id);
    List<Question> selectQuestions(String id);
    List<Answer> selectAnswers(int id);
}
