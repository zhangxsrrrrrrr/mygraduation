package edu.ahau.graduationproject.mapper;

import edu.ahau.graduationproject.domain.Student;
import edu.ahau.graduationproject.dto.StuInforExcelDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Property;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/2/15.
 *
 * @author Xun Zhang
 */
@Repository
public interface ImportFileMapper {
   void saveGrades(@Param("students") List<StuInforExcelDTO> students);
   void save(@Param("students") List<StuInforExcelDTO> students);

   void saveCourseToStudent(@Param("students") List<StuInforExcelDTO> students);
}
