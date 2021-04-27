package edu.ahau.graduationproject.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author zhangxun_a
 * @date 2021/4/26
 * @Description TODO
 */
@Repository
public interface QryAnswerAndQuestionNum {
    Long selecteAnswerNum(@Param("id") String id);
    Long selecteQuestionNum(@Param("id") String id);
}
