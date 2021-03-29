package edu.ahau.graduationproject;



import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import edu.ahau.graduationproject.config.StudentDataListener;
import edu.ahau.graduationproject.domain.Question;
import edu.ahau.graduationproject.domain.Student;
import edu.ahau.graduationproject.dto.*;
import edu.ahau.graduationproject.mapper.*;
import edu.ahau.graduationproject.service.AnswerService;
import edu.ahau.graduationproject.utils.FileUtil;
import edu.ahau.graduationproject.utils.IDUtil;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import org.apache.velocity.runtime.directive.Foreach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
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
    @Test
    public void testFile1(){
        List<String> files = FileUtil.findFiles("30010");
        System.out.println(files.size());
    }
    @Test
    public void deleteFile(){
        File file = new File("G:/graduation/" + "30010" + "/" + "密码.txt");
        file.delete();


    }
    @Test
    public void date1() throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Calendar calendar = Calendar.getInstance();
        String dateName = format.format(calendar.getTime());
        Date parse = format.parse(dateName);

        System.out.println(parse);
    }
    @Autowired
    private QuestionMapper questionMapper;
    @Test
    public void insertDate() throws ParseException {
        Question question = new Question();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Calendar calendar = Calendar.getInstance();
        String dateName = format.format(calendar.getTime());
        Date parse = new Date();
        question.setCreationDate(parse);
        boolean b = questionMapper.insertQuestion(question);
        System.out.println(b);
    }
    @Test
    public void file(){
        String path = "G:\\graduation";
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            System.out.println("path:"+files[i].getAbsolutePath());
            System.out.println("name:"+files[i].getName());
            for (File file2:files[0].listFiles()
                 ) {
                System.out.println(file2.getName());
            }

        }
    }
    @Test
    public void systemView(){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        System.out.println(fsv.getHomeDirectory().getAbsolutePath());
    }
    @Autowired
    private AnswerService answerService;
    @Test
    public void questionTest(){
//        List<File> questionImage = answerService.findQuestionImage(17);
//        System.out.println(questionImage.toString());
        List<Integer> allQuestionIds = questionMapper.findAllQuestionIds();
        System.out.println(allQuestionIds);
    }

    @Test
    public void mapperTest(){
        System.out.println(answerService.findAnserId(1));
        String answerImage = answerService.findAnswerImage(1);
        System.out.println(answerImage);

    }
}
