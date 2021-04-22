package edu.ahau.graduationproject.utils;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 2021/2/18.
 *
 * @author Xun Zhang
 */
public class FileUtil {
    public static boolean deleteFile(HttpServletRequest request,String fileName){
        String pathValues = "G:/graduation/" + IDUtil.getID(request)+ "/" +fileName;
        System.out.println(pathValues);
        File file = new File(pathValues);
        if (file.exists()){
            return file.delete();
        }
        return false;
    }
    /*
    根据用户id返回文件名
     */
    public static List<String> findFiles(String id){
        String pathValues = "G:/graduation/" + id + "/";
        File fload = new File(pathValues);
        File[] files = fload.listFiles();
        if (files==null){
            return Collections.emptyList();
        }
        List<String> fileList = new ArrayList<>();
        for (File file:files
             ) {
            fileList.add(file.getName());
        }
        return fileList;
    }
}

