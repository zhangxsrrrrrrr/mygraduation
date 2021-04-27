package edu.ahau.graduationproject.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

/**
 * @author zhangxun_a
 * @date 2021/4/27
 * @Description 成绩工具类
 */
@Slf4j
public class GradeUtil {
    final static double examinationPercent = 0.7;
    public static double calUsualGrade(long answerNum, long questionNum) {
        long answerPoint = Math.min(answerNum, 20); // 1.5
        long questionPoint = Math.min(questionNum, 10); // 1
        double i =  (answerPoint + questionPoint)*0.5;
        String format = String.format("%.1f", i);
        return Double.parseDouble(format);
    }

    public static double calExaminationGrades(Double examination){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String format = decimalFormat.format(examination * examinationPercent);
        return Double.parseDouble(format);
    }
}
