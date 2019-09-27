package com.mutistic.file.excel.simple;

import com.mutistic.file.excel.annotation.ExportColumn;
import com.mutistic.file.excel.common.ExportConst;
import com.mutistic.file.excel.common.ExportUtil;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Excel 导出
 *
 * @author yinyc
 * @date 2019/8/22 9:52
 */
public class SimpleExcelExport extends AbstractExcelExport {

  /**
   * 标识：防止 rowNum下标为0
   */
  private boolean isZeroRowNum = false;

  /**
   * 字段、标题 关系集合
   */
  private Map<ExportColumn, Field> columnMap;

  public static SimpleExcelExport getInstance() {
    return new SimpleExcelExport();
  }

  @Override
  public <T> OutputStream export(OutputStream outputStream, List<T>... rowDates) throws Exception {
    if (outputStream == null || rowDates == null || rowDates.length == 0) {
      return null;
    }

    initWorkbook(getWorkType());
    buildExcel(rowDates);
    getWorkbook().write(outputStream);
    outputStream.flush();
    return outputStream;
  }

  @Override
  public <T> String export(String filePrefix, List<T>... rowDates) throws Exception {
    if (rowDates == null || rowDates.length == 0) {
      return null;
    }

    String excelPath = ExportUtil.getLocalFilePath(filePrefix, getWorkType());
    OutputStream outputStream = new FileOutputStream(excelPath);
    export(outputStream, rowDates);
    close(outputStream);
    return excelPath;
  }

  @Override
  public <T> void buildExcel(List<T>... rowDates) throws Exception {
    for (List<T> rowData : rowDates) {
      // 初始化标识
      isZeroRowNum = false;
      Class clazz = rowData.get(0).getClass();
      columnMap = ExportUtil.getRelation(clazz);
      if (columnMap == null || columnMap.isEmpty()) {
        throw new Exception("导出实体类，未配置导出注解！");
      }

      initSheet(ExportUtil.getSheetName(clazz));
      buildTop();
      buildHeader();
      buildContent(rowData);
    }
  }

  @Override
  public void buildTop() {
    // 根据 group 构建
//    Row row = getSheet().createRow(0);
//    Cell cell = row.createCell(0);
//    cell.setCellStyle(getTopStyle());
//    cell.setCellValue("test");
  }

  @Override
  public void buildHeader() {
    Row row = getSheet().createRow(getNextRowNum());

    Cell cell;
    int index = 0;
    for (ExportColumn column : columnMap.keySet()) {
      getSheet().setColumnWidth(index,
          column.length() > 0 ? column.length() * 256 : ExportConst.CELL_WIDTH);
      cell = row.createCell(index);
      cell.setCellStyle(getHeaderStyle());
      cell.setCellValue(column.title());
      index++;
    }
  }

  private int getNextRowNum() {
    if (isZeroRowNum) {
      return getSheet().getLastRowNum() + 1;
    }

    isZeroRowNum = true;
    return getSheet().getLastRowNum();
  }

  @Override
  public <T> void buildContent(List<T> rowData) throws Exception {
    Row row;
    Cell cell;
    int rowNum = getNextRowNum();

    for (int r = 0; r < rowData.size(); r++) {
      row = getSheet().createRow(rowNum + r);

      String[] value = ExportUtil.getRecord(rowData.get(r), columnMap);
      for (int c = 0; c < value.length; c++) {
        cell = row.createCell(c);
        cell.setCellStyle(getContentStyle());
        cell.setCellValue(value[c]);
      }
    }
  }
}
