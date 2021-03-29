package edu.ahau.graduationproject.mapper;

import edu.ahau.graduationproject.domain.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created on 2021/2/20.
 *
 * @author Xun Zhang
 */
@Repository
public interface QuestionMapper {
    /**
     * 根据id获取到老师或者学生的姓名
     */
    String selectStudentName(@Param("id") String id);

    /**
     * 根据id获取到老师或者学生的姓名
     */
    String selectTeachertName(@Param("id") String id);

    /*
    保存提出的问题
     */
    boolean insertQuestion(@Param("question") Question question);

    /**
     * 根据插入时间找到question的唯一id
     * @param date
     * @return
     */
    int selectQuestionid(@Param("date") Date date);
    /**
     * 查到所有的questionid
     */
    List<Integer> findAllQuestionIds();
    /**
     * 根据questionid查找父question的图片
     */
    String selectQuestionImage(@Param("questionId") int  questionId);

    /**
     * 根据questionid查找父question的内容
     */
    String selectQuestionText(@Param("questionId") int questionId);

    /**
     * 根据questionid查找父question的提问者
     */
    String selectQuestionUpper(@Param("questionId") int questionId);
}
