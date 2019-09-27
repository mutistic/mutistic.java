package com.mutistic.file.excel.simple;

import com.mutistic.file.excel.common.ExportDataVO;
import com.mutistic.file.excel.common.ExportUtil;
import java.util.List;

/**
 * @version <pre>
 * Author  Version   Date	 	Changes
 * yinyc    1.0     2019/8/26   Created
 * </pre>
 */
public class SimpleTestMain {
  public static void main(String[] args) {
    try {
      Long begin = System.currentTimeMillis();
      System.out.println("begin="+ begin);
      int num = 10;
      List<ExportDataVO> list = ExportUtil.testData(num);
      List<ExportDataVO> list2 = ExportUtil.testData(num);
      AbstractExcelExport excelExport = SimpleExcelExport.getInstance();
      String excelPath = excelExport.export("test", list, list2);
      System.out.println(excelPath);

      System.out.println("end="+ (System.currentTimeMillis() - begin));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
