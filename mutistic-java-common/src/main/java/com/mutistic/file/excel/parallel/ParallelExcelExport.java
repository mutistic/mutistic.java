package com.mutistic.file.excel.parallel;

import com.mutistic.file.excel.annotation.ExportColumn;
import com.mutistic.file.excel.common.ExportConst;
import com.mutistic.file.excel.common.ExportUtil;
import com.mutistic.utils.common.CommonUtil;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Excel 导出
 *
 * @author yinyc
 * @date 2019/8/22 9:52
 */
public class ParallelExcelExport extends AbstractExcelExport {

  /**
   * 获取 ParallelExcelExport 实例
   *
   * @return
   */
  public static ParallelExcelExport getInstance() {
    ParallelExcelExport export = new ParallelExcelExport();
    export.setRowPageSize(ExportConst.MIN_ROW_PAGE_SIZE);
    export.setCellPageSize(ExportConst.MIN_CELL_PAGE_SIZE);
    return export;
  }

  /**
   * 获取 ParallelExcelExport 实例
   *
   * @param rowPageSize Row每页内容数 > 默认:ExportConst.MIN_ROW_PAGE_SIZE
   * @return
   */
  public static ParallelExcelExport getInstance(int rowPageSize) {
    ParallelExcelExport export = new ParallelExcelExport();
    export.setRowPageSize(rowPageSize);
    return export;
  }

  /**
   * 获取 ParallelExcelExport 实例
   *
   * @param rowPageSize  Row每页内容数 > 默认:ExportConst.MIN_ROW_PAGE_SIZE
   * @param cellPageSize Cell每页内容数 > 默认:ExportConst.MIN_CELL_PAGE_SIZE
   * @return
   */
  public static ParallelExcelExport getInstance(Integer rowPageSize, Integer cellPageSize) {
    ParallelExcelExport export = new ParallelExcelExport();
    export.setRowPageSize(rowPageSize);
    export.setCellPageSize(cellPageSize);
    return export;
  }


  @Override
  public <T> OutputStream export(OutputStream outputStream, List<T>... rowDataList)
      throws Exception {
    if (outputStream == null || rowDataList == null || rowDataList.length == 0) {
      return null;
    }

    initWorkbook(getWorkType());
    buildExcel(rowDataList);
    getWorkbook().write(outputStream);
    outputStream.flush();
    return outputStream;
  }

  @Override
  public <T> String export(String filePrefix, List<T>... rowDataList)
      throws Exception {
    if (rowDataList == null || rowDataList.length == 0) {
      return null;
    }

    String excelPath = ExportUtil.getLocalFilePath(filePrefix, getWorkType());
    OutputStream outputStream = new FileOutputStream(excelPath);
    export(outputStream, rowDataList);
    close(outputStream);
    return excelPath;
  }

  @Override
  public <T> void buildExcel(List<T>... rowDataList) throws Exception {
    if (rowDataList.length == 1) {
      System.out.println("parallelExport > buildExcel >> 直接构建sheet");
      buildSheet(rowDataList[0]);
    } else {
      System.out.println("parallelExport > buildExcel >> 并行模式sheet");
      buildSheetByParallel(rowDataList);
    }
  }

  @Override
  public void buildTop(Sheet sheet) {
    // 根据 group 构建
//    System.out.println("top row index:" + nextRowNum);
//    Row row = sheet.createRow(0);
//    Cell cell = row.createCell(0);
//    cell.setCellStyle(getTopStyle());
//    cell.setCellValue("test");
//    setNextRowNum(1);
  }

  @Override
  public void buildHeader(Sheet sheet, Set<ExportColumn> keySet) {
    Row row = sheet.createRow(0);
    System.out.println("header row index:" + sheet.getLastRowNum());

    Cell cell;
    int index = 0;
    for (ExportColumn column : keySet) {
      sheet.setColumnWidth(index,
          column.length() > 0 ? column.length() * 256 : ExportConst.CELL_WIDTH);
      cell = row.createCell(index);
      cell.setCellStyle(getHeaderStyle());
      cell.setCellValue(column.title());
      index++;
    }
  }

  @Override
  public <T> void buildContent(Sheet sheet, List<T> rowData, Map<ExportColumn, Field> columnMap)
      throws Exception {
    int beginRowNum = sheet.getLastRowNum() + 1;
    // 内容数量小于每页内容数 > 直接构建row
    if (rowData.size() <= getRowPageSize()) {
      System.out.println("parallelExport > buildContent >> 直接构建row");
      System.out.println(this.getClass() + "直接构建row");
      buildRow(sheet, rowData, beginRowNum, columnMap);
    }
    // 内容数量小于采用分页最小内容数 > 并行构建row
    else if (rowData.size() <= (getRowPageSize() * ExportConst.MIN_ROW_TOTAL_PAGE)) {
      System.out.println("parallelExport > buildContent >> 并行构建row");
      buildRowByParallel(sheet, rowData, beginRowNum, columnMap);
    }
    // 内容数量大于采用分页最小内容数 > 分页并行构建row
    else {
      System.out.println("parallelExport > buildContent >> 分页并行构建row");
      buildRowByPage(sheet, rowData, beginRowNum, columnMap);
    }
  }

  /**
   * 构建 sheet（普通循环模式）
   *
   * @param rowData 多数据集合
   * @param <T>     数据类型
   * @throws Exception
   */
  private <T> void buildSheet(List<T> rowData) throws Exception {
    Class clazz = rowData.get(0).getClass();
    Map<ExportColumn, Field> columnMap = ExportUtil.getRelation(clazz);
    if (columnMap == null || columnMap.isEmpty()) {
      throw new Exception("导出实体类，未配置导出注解！");
    }

    Sheet sheet = initSheet(ExportUtil.getSheetName(clazz));
    buildTop(sheet);
    buildHeader(sheet, columnMap.keySet());
    buildContent(sheet, rowData, columnMap);
  }

  /**
   * 构建 sheet（并行模式）
   *
   * @param rowDataList 多数据集合
   * @param <T>         数据类型
   * @throws Exception
   */
  private <T> void buildSheetByParallel(List<T>... rowDataList) throws Exception {
    List<Exception> messageList = new ArrayList<>();
    IntStream.range(0, rowDataList.length - 1).parallel().forEach(index -> {
      try {
        List<T> rowData = rowDataList[index];
        Sheet sheet = initSheet(ExportUtil.getSheetName(rowData.get(0).getClass()));
        Map<ExportColumn, Field> columnMap = ExportUtil.getRelation(rowData.get(0).getClass());
        buildTop(sheet);
        buildHeader(sheet, columnMap.keySet());
        buildContent(sheet, rowData, columnMap);
      } catch (Exception e) {
        messageList.add(e);
        return;
      }
    });

    if (!CommonUtil.isEmpty(messageList)) {
      throw messageList.get(0);
    }
  }

  /**
   * 构建 row（普通循环模式）
   *
   * @param sheet       页签对象
   * @param dataList    数据集合
   * @param beginRowNum row开始行号
   * @param columnMap   映射关系Map
   * @param <T>         数据类型
   * @throws Exception
   */
  private <T> void buildRow(Sheet sheet, List<T> dataList, int beginRowNum,
      Map<ExportColumn, Field> columnMap)
      throws Exception {
    for (int i = 0; i < dataList.size(); i++) {
      System.out.println("getLastRowNum=" + sheet.getLastRowNum());
      buildCell(sheet.createRow(beginRowNum), ExportUtil.getRecord(dataList.get(i), columnMap));
    }
  }

  /**
   * 构建 row（并行模式）
   *
   * @param sheet       页签对象
   * @param dataList    数据集合
   * @param beginRowNum row开始行号
   * @param columnMap   映射关系Map
   * @param <T>         数据类型
   * @throws Exception
   */
  private <T> void buildRowByParallel(Sheet sheet, List<T> dataList, int beginRowNum,
      Map<ExportColumn, Field> columnMap)
      throws Exception {
    // 并发有bug
    List<Exception> messageList = new ArrayList<>();

    IntStream.rangeClosed(0, dataList.size() - 1).parallel().forEach(index -> {
      try {
        buildCell(sheet.createRow(beginRowNum + index),
            ExportUtil.getRecord(dataList.get(index), columnMap));
      } catch (Exception e) {
        messageList.add(e);
        return;
      }
    });

    if (!CommonUtil.isEmpty(messageList)) {
      System.out.println("buildRowByParallel exception beginRowNum=" + beginRowNum);
      throw messageList.get(0);
    }
  }

  /**
   * 构建 row（分页并行模式）
   *
   * @param sheet       页签对象
   * @param rowData     数据集合
   * @param beginRowNum row开始行号
   * @param columnMap   映射关系Map
   * @param <T>         数据类型
   * @throws Exception
   */
  private <T> void buildRowByPage(Sheet sheet, List<T> rowData, int beginRowNum,
      Map<ExportColumn, Field> columnMap)
      throws Exception {
    List<Exception> messageList = new ArrayList<>();

    List<List<T>> pageList = ExportUtil.getPageList(rowData, getRowPageSize());
    if (CommonUtil.isEmpty(pageList)) {
      throw new Exception("初始化分页数据出现异常！");
    }

    // 根据分页集合 构建row
    IntStream.rangeClosed(0, pageList.size() - 1).parallel().forEach(index -> {
      try {
        int rowNum = index == 0 ? beginRowNum : index * getRowPageSize() + 1;
        List<T> dataList = pageList.get(index);
        System.out.println("parallelExport > buildRowByPage >> 直接构建row=" + index);
        // 每页内容数小于最小每页内容数 > 直接构建row
        if (dataList.size() < ExportConst.MIN_ROW_PAGE_SIZE) {
          System.out.println("parallelExport > buildRowByPage >> 直接构建row=" + index);
          buildRow(sheet, dataList, rowNum, columnMap);
        }
        // 每页内容数大于最小每页内容数 > 并行构建row
        else {
          System.out.println("parallelExport > buildRowByPage >> 并行构建row=" + index);
          buildRowByParallel(sheet, dataList, rowNum, columnMap);
        }
      } catch (Exception e) {
        System.out.println("buildRowByPage exception");
        messageList.add(e);
        return;
      }
    });

    if (!CommonUtil.isEmpty(messageList)) {
      throw messageList.get(0);
    }
  }

  /**
   * 构建 cell
   *
   * @param row    行
   * @param values 数据
   * @param <T>    数据类型
   */
  private <T> void buildCell(Row row, String[] values) {
    // 可通过cellPageSize参数进行优化
    Cell cell;
    for (int i = 0; i < values.length; i++) {
      cell = row.createCell(i);
      cell.setCellStyle(getContentStyle());
      cell.setCellValue(values[i]);
    }
  }

}
