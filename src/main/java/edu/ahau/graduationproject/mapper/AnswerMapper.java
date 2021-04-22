package edu.ahau.graduationproject.mapper;

import edu.ahau.graduationproject.domain.Answer;
import edu.ahau.graduationproject.dto.Answer1;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created on 2021/2/23.
 *
 * @author Xun Zhang
 */
@Repository
public interface AnswerMapper {
    /**
     * 得到评论人的name
     */
    String selectName(@Param("id") Integer id);

    /**
     * 获取评论人id
     */
    String selectId(@Param("id") Integer id);
    /**
     * 获取评论文字信息
     */
    String selectAnswerText(@Param("id") Integer id);

    /**
     * 获取评论图片
     */
    String selectAnswerImage(@Param("id") Integer id);

    /**
     * 一个问题下的所有回答id
     */
    List<Integer> selectaLLAnswerId(@Param("id") Integer id);

    /**
     * 插入评论信息
     */
    boolean insertAnswerText(@Param("answer") Answer answer);

    /**
     * 根据上传日期获取answerId
     */
    Integer selectAnswerId(Date date);

    /**
    * @author: zhangxun_a
    * @date: 2021/4/21 11:42
    * @Description: 获取questionid
    */
    Integer selectQuestionId(@Param("id") Integer id);
}
