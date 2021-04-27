package edu.ahau.graduationproject.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author zhangxun_a
 * @date 2021/4/26
 * @Description TODO
 */
@Repository
public interface TopMapper {
    boolean updateAnswerTop(@Param("id") int id);
    Integer selectAnswerFlag(@Param("id") int id);
}
