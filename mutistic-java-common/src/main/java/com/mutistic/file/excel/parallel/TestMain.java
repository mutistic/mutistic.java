package com.mutistic.file.excel.parallel;

import com.mutistic.file.excel.common.ExportDataVO;
import com.mutistic.file.excel.common.ExportUtil;
import com.mutistic.file.excel.simple.SimpleExcelExport;
import com.mutistic.utils.common.CommonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.poi.ss.formula.functions.T;

/**
 * @version <pre>
 * Author  Version   Date	 	Changes
 * yinyc    1.0     2019/8/26   Created
 * </pre>
 */
public class TestMain {

  public static void main(String[] args) {
    try {
//      System.out.println("====================================第一次 num = 10");
//      simple(10);
//      parallel(10);
//
//      System.out.println("====================================第二次 num = 40");
//      simple(40);
//      parallel(40);


      System.out.println("====================================第三次 num = 100");
//      simple(100);
      parallel(101);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void test(int num, int pageSize) throws Exception {
    int beginRowNum = 1;
    List rowData = ExportUtil.testData(num);

    List<List<T>> pageList = ExportUtil.getPageList(rowData, pageSize);
    if (CommonUtil.isEmpty(pageList)) {
      throw new Exception("初始化分页数据出现异常！");
    }
    List<String> pageInfo = new ArrayList<>();
    List<String> rowInfo = new ArrayList<>();

    System.out.println("init num=" + beginRowNum);
    // 根据分页集合 构建row
    IntStream.rangeClosed(0, pageList.size() - 1).parallel().forEach(index -> {
      int rowNum = index == 0 ? beginRowNum : index * pageSize + 1;
      // 分页信息
      pageInfo.add("page index =" + index + ", begin row num=" + rowNum);

      for (int i = 0; i < pageList.get(index).size(); i++) {
        rowInfo.add("row info " + (index + "_"+ i) + " row num =" + (rowNum + i));
      }
      System.out.println("index ="+ index);
    });

    System.out.println("\npage info");
    for (String s : pageInfo) {
      System.out.println(s);
    }

    System.out.println("\nrow info");
    for (String s : rowInfo) {
      System.out.println(s);
    }

  }


  public static void parallel(int num) {
    System.out.println("\n并行开始导出");
    try {
      Long begin = System.currentTimeMillis();
      System.out.println("begin=" + begin);
      List<ExportDataVO> list = ExportUtil.testData(num);
      List<ExportDataVO> list2 = null;//ExportUtil.testData2(num);
      AbstractExcelExport excelExport = ParallelExcelExport.getInstance();
      String excelPath = excelExport.export("test", list, list2);
      System.out.println(excelPath);
      System.out.println("end=" + (System.currentTimeMillis() - begin));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void simple(int num) {
    System.out.println("\n串行开始导出");
    try {
      Long begin = System.currentTimeMillis();
      System.out.println("begin=" + begin);
      List<ExportDataVO> list = ExportUtil.testData(num);
      List<ExportDataVO> list2 = ExportUtil.testData2(num);
      com.mutistic.file.excel.simple.AbstractExcelExport excelExport = SimpleExcelExport
          .getInstance();
      String excelPath = excelExport.export("test", list, list2);
      System.out.println(excelPath);

      System.out.println("end=" + (System.currentTimeMillis() - begin));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
