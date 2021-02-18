package edu.ahau.graduationproject;



import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import edu.ahau.graduationproject.config.StudentDataListener;
import edu.ahau.graduationproject.domain.Student;
import edu.ahau.graduationproject.dto.*;
import edu.ahau.graduationproject.mapper.ImportFileMapper;
import edu.ahau.graduationproject.mapper.StudentMapper;
import edu.ahau.graduationproject.mapper.TeacherMapper;
import edu.ahau.graduationproject.mapper.UserMapper;
import edu.ahau.graduationproject.utils.IDUtil;
import org.apache.velocity.runtime.directive.Foreach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class GraduationprojectApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Test
    void contextLoads() {
        List<TeacherOfStudentInforDTO> dtos = studentMapper.selectTeacherInforById("17112003");
        System.out.println(dtos.toString());
    }



    @Test
    public void test12(){
        List<StudentGradesDTO> studentGradesDTOS = studentMapper.selectStudentGradesByID("17112003");
        System.out.println(JSON.toJSONString(studentGradesDTOS));
    }

    @Test
    public void test(){
        UserDTO userDTO = userMapper.selectTeacherById("30010");
        System.out.println(userDTO);
    }
    @Autowired
    private TeacherMapper teacherMapper;
    @Test
    public void test113(){

        List<AllStudentGradesDTO> allStudentGradesDTOS = teacherMapper.selectAllStudentGrades("1");
        System.out.println(allStudentGradesDTOS.toString());
    }
    @Test
    public void test123(){
        int i = teacherMapper.updateGradesById("17112003", "45", "1");
        System.out.println(i);
    }
    @Autowired
    private ImportFileMapper fileMapper;
    @Test
    public void simpleRead() {

        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName =  "C:\\Users\\moximoxi\\Desktop\\demo1.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

        // 写法2：
//        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = null;

        try {
            excelReader = EasyExcel.read(fileName, Student.class, new StudentDataListener(fileMapper)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    @Test
    public void test143(){
        StuInforExcelDTO dto = new StuInforExcelDTO("zhangsan","1234","1");
        StuInforExcelDTO dto1 = new StuInforExcelDTO("zhangsan","1234","1");
        ArrayList<StuInforExcelDTO> list = new ArrayList<>();
        list.add(dto);
        list.add(dto1);
        fileMapper.saveCourseToStudent(list);
    }

    @Test
    public void testFile(){
        File  personalFload = new File("G:/graduation/" + "123"+ "/");
        if (!personalFload.exists()){
            boolean mkdirs = personalFload.mkdirs();
        }
    }

}
