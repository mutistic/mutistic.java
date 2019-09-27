package com.mutistic.file.excel.compar;

import com.mutistic.file.excel.annotation.ExportColumn;
import java.util.Comparator;

/**
 * Export Column Comparator
 * @author yinyc
 * @date 2019/8/21 10:50
 */
public class ExportColumnCompar implements Comparator<ExportColumn> {

  @Override
  public int compare(ExportColumn column1, ExportColumn column2) {
    return column1.sort() - column2.sort();
  }
}
