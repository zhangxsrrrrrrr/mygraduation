package edu.ahau.graduationproject.service;

import java.io.File;
import java.util.List;

/**
 * Created on 2021/2/22.
 *
 * @author Xun Zhang
 */
public interface AnswerService {
    String findAnswerImage(int id);
    String findAnswerText(int id);
    String findAnserId(int id);
    List<String> findQuestionImage(int questionId);
    String findQuestionText(int questionId);
    String findQuestionUserId(int questionId);
    String findUserName(String id);
}
