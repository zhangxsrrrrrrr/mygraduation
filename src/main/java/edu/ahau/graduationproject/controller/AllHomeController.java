package edu.ahau.graduationproject.controller;

import edu.ahau.graduationproject.domain.Answer;
import edu.ahau.graduationproject.domain.Question;
import edu.ahau.graduationproject.dto.Answer1;
import edu.ahau.graduationproject.dto.AnswerDTO;
import edu.ahau.graduationproject.mapper.AnswerMapper;
import edu.ahau.graduationproject.mapper.QuestionMapper;
import edu.ahau.graduationproject.mapper.TopMapper;
import edu.ahau.graduationproject.service.AnswerService;
import edu.ahau.graduationproject.utils.IDUtil;
import edu.ahau.graduationproject.vo.FileInfor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * Created on 2021/2/4.
 *
 * @author Xun Zhang
 */
@Slf4j
@Controller
@Secured({"ROLE_teacher","ROLE_student"})
@RequestMapping(value = "all",method = RequestMethod.POST)
public class AllHomeController {
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private TeacherController teacherController;
    @Autowired
    private StudentController studentController;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerService answerService;
    @GetMapping("/homePage")
    public String homePage(){
        return "all/home";
    }

    /**
     * 根据用户角色跳转到后台管理页面
     * @param request
     * @return
     */
    @GetMapping("/basic")
    public String basic(HttpServletRequest request, Model model){
        String author = IDUtil.getAuthor(request);
        String information = "/all/home";
        if ("teacher".equals(author)) {
            request.getSession().setAttribute("courseid","-1");
            information = teacherController.information(request, model);
        }else if ("student".equals(author)){
            information = studentController.information(request,model);
        }

        return "redirect:/"+information;
    }
    @GetMapping("/{url}")
    public String goToURL(@PathVariable String url){
        return "all/"+url;
    }

//    @ResponseBody
    @PostMapping("/raiseque")
    public String raiseQuestion(HttpServletRequest request,
                                             @RequestParam(value = "text") String text,
                                             @RequestPart("photos") MultipartFile[] files) throws ParseException, IOException {

        HashMap<String, String> map = new HashMap<>();
        boolean isSave = false;
        Question question = new Question();
        if (files.length==0&&text.length()==0){
            map.put("msg","不要输入空的问题");
            return "/all/raisequestion";
        }
        StringBuilder allFileName = new StringBuilder();
        if (files.length!=0){
            StringBuilder pathValue = new StringBuilder("G:\\graduationQuestion");
            //将所有文件名合成一个字符串存储到数据库中

            String[] filePaths = new String[9];
            ArrayList<String> images = new ArrayList<>();
            images.add("jpg");
            images.add("png");
            int fileIndex = 0;
            for (MultipartFile file: files
            ) {
                String originalFilename = file.getOriginalFilename();
                String prefix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

                if (originalFilename.contains("@")||!images.contains(prefix)){
                    map.put("msg","文件名不可以包含@或文件格式不支持（仅支持jpg和png）");
                }
                //用@分割文件名
                allFileName = allFileName.append(originalFilename).append("@");
                String userId = IDUtil.getID(request);
                String  name = null;
                String studentName = questionMapper.selectStudentName(userId);
                String teacherName = questionMapper.selectTeachertName(userId);
                if (studentName==null){
                    name = teacherName;
                }else {
                    name = studentName;
                }
                question = new Question();
                question.setFileName(allFileName.toString());
                Date now = new Date();
                question.setCreationDate(now);
                question.setUserId(IDUtil.getID(request));
                question.setTextArea(text);
                question.setUserName(name);
                isSave = questionMapper.insertQuestion(question);
                int questionid = questionMapper.selectQuestionid(now);
                //获取存储路径
                pathValue = pathValue.append("\\").append(IDUtil.getID(request))
                        .append("\\").append(questionid).append("\\").append(originalFilename);
                String path = pathValue.toString();
                File file1 = new File(path);
                if (!file1.getParentFile().exists()) {
                    boolean mkdirs = file1.mkdirs();
                }
                    try {

                        file.transferTo(file1);
                        pathValue = new StringBuilder("G:\\graduationQuestion");;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }

        if (isSave){
            map.put("msg","提问成功");
        }
        return "/all/raisequestion";
    }
    @ResponseBody
    @GetMapping("/file")
    public Map<String, Object> file(HttpServletRequest request,HttpSession session){
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg","");
        map.put("code","0");
        List<FileInfor> list = new ArrayList<>();
        if (session.getAttribute("selFileById") == null) {
            String path = "G:\\graduation";
            File pathFile = new File(path);
            //获取父文件夹的子文件夹
            File[] files = pathFile.listFiles();
            //遍历所有父文件夹的子文件夹下的所有文件
            for (File file : files) {
                String absolutePath = file.getAbsolutePath();
                File file1 = new File(absolutePath);
                File[] files1 = file1.listFiles();
                for (File file3:files1
                ) {
                    FileInfor fileInfor = new FileInfor();
                    fileInfor.setFileName(file3.getName());
                    String parent = file3.getParent();
                    int lastIndexOf = parent.lastIndexOf(File.separator);
                    String upper = parent.substring(lastIndexOf, parent.length());
                    String replace = upper.replace("\\", "");
                    fileInfor.setUpper(replace);

                    list.add(fileInfor);
                }
            }
        }else{
            String path = "G:\\graduation\\"+session.getAttribute("selFileById");
            File file = new File(path);
            File[] files = file.listFiles();
            if (files==null){
                List<Object> emptyList = Collections.emptyList();
                map.put("data",emptyList);
                return map;
            }
            for (File file3:files
            ) {
                FileInfor fileInfor = new FileInfor();
                fileInfor.setFileName(file3.getName());
                fileInfor.setUpper(IDUtil.getID(request));
                list.add(fileInfor);
            }
        }

        map.put("data",list);
        return map;
    }
    @ResponseBody
    @GetMapping("/down")
    public Map<String, String> down(FileInfor infor,HttpSession session){
        HashMap<String, String> map = new HashMap<>();
        String path = "G:\\graduation" + "\\" + infor.getUpper() + "\\" + infor.getFileName();

        map.put("status","0");
        //需要下载的文件
        File srcFile = new File(path);
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        String[] s = infor.getFileName().split("_");

        String dest = fileSystemView.getHomeDirectory().getAbsolutePath()+"\\downFileFromAHAU\\";
        StringBuilder des = new StringBuilder();
        des.append(dest);
        for (int i = 1; i < s.length; i++) {
            des.append(s[i]+"_");
        }


        File dstFile = new File(des.toString());
        if (!dstFile.getParentFile().exists()){
            dstFile.getParentFile().mkdirs();
        }
        //copy到本地
        try (FileInputStream fis = new FileInputStream(srcFile);
           FileOutputStream fos = new FileOutputStream(dstFile)) {
            int len;
            byte[] buffer = new byte[4096];
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            // ... handle IO exception
        }
        map.put("status","1");

        return map;
    }
    @PostMapping("/selectfile")
    public String selectFileByTeacherId(String id,HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("selFileById",id);
        return "redirect:/all/viewfiles";
    }
    @GetMapping("/restSession")
    public String restSession(HttpSession session){
        session.removeAttribute("selFileById");
        return "redirect:/all/viewfiles";
    }
    @GetMapping("/viewquestion")
    public String viewque(HttpServletRequest request,
                          Model model){
        List<AnswerDTO> dtoList = new ArrayList<>();
        List<Integer> allQuestionIds = questionMapper.findAllQuestionIds();
        Iterator<Integer> iterator = allQuestionIds.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            String questionUserId = answerService.findQuestionUserId(next);
            String  name = null;
            String studentName = questionMapper.selectStudentName(questionUserId);
            String teacherName = questionMapper.selectTeachertName(questionUserId);
            if (studentName==null){
                name = teacherName;
            }else {
                name = studentName;
            }
            String allName = questionUserId+"-"+name;
            String questionText = answerService.findQuestionText(next);
            List<String> questionImage = answerService.findQuestionImage(next);
            AnswerDTO answerDTO = new AnswerDTO(allName,questionText,questionImage,next);
            dtoList.add(answerDTO);
        }
        model.addAttribute("answers",dtoList);
//        answerService.findQuestionText();
//        answerService.findQuestionUserId();
//        answerService.findQuestionImage();
        return "/all/viewquestion";
    }
    @GetMapping("/answer")
    // id为questionID
    public String answer(String text,String nameid,Integer id,HttpSession session){
        // 序列显示
        Vector<Answer1> answer1Vector = new Vector<>();

        session.setAttribute("zhangtext",text);
        session.setAttribute("zhangnameid",nameid);
        session.setAttribute("zhangid", id);
        List<Integer> idList = new ArrayList<>();

        List<AnswerDTO> questionDTO = new ArrayList<>();
        List<String> images = answerService.findQuestionImage(id);
        AnswerDTO answerDTO = new AnswerDTO(nameid, text, images, id);
        questionDTO.add(answerDTO);
        List<Integer> ids = answerMapper.selectaLLAnswerId(id);

        //找出置顶的回答， 非一个。
        for (Integer id1 : ids) {
            Integer flag = answerService.findFlag(id1);
            if (flag == null || flag == 0 || flag == 1){
                continue;
            }
            String answerId = answerService.findAnserId(id1);
            String answerImage = answerService.findAnswerImage(id1);
            String webImage = "/files";
            String viewImage = null;

            if (answerImage.endsWith(".jpg")) {
                viewImage = answerImage.replace("G:/graduationQuestion",webImage);
            }
            String answerText = answerService.findAnswerText(id1);

            Answer1 answer1 = new Answer1(answerId, answerText, viewImage, id1);
            if (flag == 1){
                answer1Vector.add(answer1);
                idList.add(id1);
            }
        }
        // 添加剩下的
        Iterator<Integer> iterator = ids.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            String answerId = answerService.findAnserId(next);
            String answerImage = answerService.findAnswerImage(next);
            String webImage = "/files";
            String viewImage = null;

            if (idList.contains(next)){
                continue;
            }
            if (answerImage.endsWith(".jpg")) {
               viewImage = answerImage.replace("G:/graduationQuestion",webImage);
            }
            String answerText = answerService.findAnswerText(next);

            Answer1 answer1 = new Answer1(answerId, answerText, viewImage, next);
            answer1Vector.add(answer1);
        }

        session.setAttribute("ques", answerDTO);
        session.setAttribute("answers",answer1Vector);


        return "all/answersandquestion";
    }
    @PostMapping("/raiseanswer")
    public String raiseanswer(HttpServletRequest request,@RequestParam("text") String text,
                              @RequestParam("id") Integer id,@RequestPart("photo") MultipartFile file,
                              @RequestParam("upper") String upper){

        String userId = IDUtil.getID(request);
        String name = answerService.findUserName(IDUtil.getID(request));
        String[] split = upper.split("-");
        String upperId = split[0];
        //设置answer上传图片的路径
        String originalFilename = file.getOriginalFilename();   //获取上传文件名
        String pathname = "G:/graduationQuestion/"+upperId+"/"+id+"/answer/"+originalFilename;
        File dest = new File(pathname);
        if (!dest.getParentFile().exists()){
            boolean mkdirs = dest.getParentFile().mkdirs();
        }
        //上传时间
        Date date = new Date();

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int flag = 0;
        Answer answer = new Answer(id,text,pathname,date,userId,name,flag);

        boolean b = answerMapper.insertAnswerText(answer);
        return "all/success";
    }

    @Autowired
    private TopMapper topMapper;
    @GetMapping("/top")
    public String getTop(@RequestParam("isTop") String isTop, @RequestParam("id") int id,HttpSession session) {

        if ("true".equals(isTop)){
            topMapper.updateAnswerTop(id);
        }


        String zhangtext = (String)session.getAttribute("zhangtext");
        String zhangnameid = (String)session.getAttribute("zhangnameid");
        Integer zhangid = (Integer) session.getAttribute("zhangid");
        return answer(zhangtext, zhangnameid, zhangid, session);

    }
}
