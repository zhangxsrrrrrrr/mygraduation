package edu.ahau.graduationproject.service.impl;

import edu.ahau.graduationproject.domain.Answer;
import edu.ahau.graduationproject.domain.Question;
import edu.ahau.graduationproject.dto.AnswerDTO;
import edu.ahau.graduationproject.mapper.MyProblemMapper;
import edu.ahau.graduationproject.service.QuestionsOfStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2021/2/25.
 *
 * @author Xun Zhang
 */
@Service("questionsOfStudentService")
public class QuestionOfStudentImpl implements QuestionsOfStudent {
    @Autowired
    private MyProblemMapper myProblemMapper;
    @Override
    public List<Question> viewQuestions(String userID) {
        List<Question> questions = myProblemMapper.selectQuestions(userID);
        return questions;
    }

    @Override
    public List<Answer> viewAnswers(int id) {
        return myProblemMapper.selectAnswers(id);
    }

}
