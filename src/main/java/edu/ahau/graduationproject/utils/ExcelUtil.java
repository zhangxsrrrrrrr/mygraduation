package edu.ahau.graduationproject.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import edu.ahau.graduationproject.config.StudentDataListener;
import edu.ahau.graduationproject.dto.StuInforExcelDTO;
import edu.ahau.graduationproject.mapper.ImportFileMapper;

/**
 * Created on 2021/2/17.
 *
 * @author Xun Zhang
 */
public class ExcelUtil {
    public static void simpleRead(ImportFileMapper fileMapper,String fileName) {

        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

        // 写法2：
//        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = null;

        try {
            excelReader = EasyExcel.read(fileName, StuInforExcelDTO.class, new StudentDataListener(fileMapper)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }
}
