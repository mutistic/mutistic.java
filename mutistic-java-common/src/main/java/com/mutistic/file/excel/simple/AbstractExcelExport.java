package com.mutistic.file.excel.simple;

import com.mutistic.file.excel.common.ExportConst;
import com.mutistic.file.excel.enums.WorkTypeEnum;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel 导出 抽象类
 *
 * @author yinyc
 * @date 2019/8/21 15:34
 */
public abstract class AbstractExcelExport {

  /**
   * Workbook
   */
  private Workbook workbook;
  /**
   * 页签
   */
  private Sheet sheet;
  /**
   * 顶部样式
   */
  private CellStyle topStyle;
  /**
   * 标题样式
   */
  private CellStyle headerStyle;
  /**
   * 标题字体
   */
  private Font headerFont;
  /**
   * 数据样式
   */
  private CellStyle contentStyle;
  /**
   * 默认excel 文件类型
   */
  private WorkTypeEnum workType = WorkTypeEnum.XSSF;

  /**
   * 导出 excel（流模式）
   *
   * @param outputStream 输出流
   * @param rowDates     数据源
   * @param <T>          数据类型
   * @throws Exception
   */
  public abstract <T> OutputStream export(OutputStream outputStream, List<T>... rowDates) throws Exception;

  /**
   * 导出 excel（服务器）
   *
   * @param filePrefix       文件名前缀
   * @param rowDates 数据源
   * @param <T>      数据类型
   * @return 文件路径
   * @throws Exception
   */
  public abstract <T> String export(String filePrefix, List<T>... rowDates) throws Exception;

  /**
   * 关闭资源
   *
   * @param outputStream
   * @throws IOException
   */
  public void close(OutputStream outputStream) throws IOException {
    if (workbook != null) {
      workbook.close();
    }
    if (outputStream != null) {
      outputStream.close();
    }
  }

  /**
   * 构建 Excel
   *
   * @param rowDates 数据源
   * @param <T>     数据类型
   * @throws Exception
   */
  public abstract <T> void buildExcel(List<T>... rowDates) throws Exception;

  /**
   * 构建 顶部
   */
  public abstract void buildTop();

  /**
   * 构建 表头
   */
  public abstract void buildHeader();

  /**
   * 构建 内容
   *
   * @param rowData 数据源
   * @param <T>     数据类型
   * @throws Exception
   */
  public abstract <T> void buildContent(List<T> rowData) throws Exception;

  /**
   * 设置 excel 文件类型
   *
   * @param workType 文件类型枚举
   * @return
   */
  public AbstractExcelExport setWorkType(WorkTypeEnum workType) {
    this.workType = workType;
    return this;
  }

  /**
   * 初始化 workbook
   */
  public void initWorkbook(WorkTypeEnum workType) {
    if (WorkTypeEnum.XSSF == workType) {
      workbook = new XSSFWorkbook();
    } else if (WorkTypeEnum.HSSF == workType) {
      workbook = new HSSFWorkbook();
    } else if (WorkTypeEnum.SXSSF == workType) {
      workbook = new SXSSFWorkbook();
    }

    initTopStyle();
    initHeaderStyle();
    initContentStyle();
  }

  /**
   * 初始化 页签
   *
   * @param sheetName
   * @return
   */
  public void initSheet(String sheetName) {
    if (StringUtils.isEmpty(sheetName)) {
      sheetName = System.currentTimeMillis() + "" + workbook.getNumberOfSheets();
    }
    // 防止重复sheet
    if (workbook.getNumberOfSheets() > 0) {
      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
        if (workbook.getSheetAt(i).getSheetName().equals(sheetName)) {
          sheetName = sheetName + "_" + workbook.getNumberOfSheets();
          break;
        }
      }
    }

    sheet = workbook.createSheet(sheetName);
  }

  /**
   * 初始化 top style
   */
  public void initTopStyle() {
    topStyle = workbook.createCellStyle();
    topStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    topStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    topStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    setBorderStyle(topStyle, BorderStyle.THIN);
  }

  /**
   * 初始化 header style
   */
  public void initHeaderStyle() {
    headerStyle = workbook.createCellStyle();
    // 设置居中
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    // 设置背景色
    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    headerStyle.setFont(initHeaderFont());
    setBorderStyle(headerStyle, BorderStyle.THIN);
  }

  public Font initHeaderFont() {
    headerFont = workbook.createFont();
    headerFont.setFontName(ExportConst.HEADER_FONT_NAME);
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints(ExportConst.HEADER_FONT_SIZE);
    return headerFont;
  }

  /**
   * 初始化 content style
   */
  public void initContentStyle() {
    contentStyle = workbook.createCellStyle();
//    contentStyle.setWrapText(true); // 设置自动换行
    setBorderStyle(contentStyle, BorderStyle.THIN);
  }

  /**
   * 设置 border style
   *
   * @param cell
   * @param border
   */
  public void setBorderStyle(CellStyle cell, BorderStyle border) {
    if (cell == null) {
      return;
    }
    if (border == null) {
      // 默认实线边框
      border = BorderStyle.THIN;
    }
    cell.setBorderTop(border);
    cell.setBorderRight(border);
    cell.setBorderBottom(border);
    cell.setBorderLeft(border);
  }

  public Workbook getWorkbook() {
    return workbook;
  }

  public Sheet getSheet() {
    return sheet;
  }

  public CellStyle getTopStyle() {
    return topStyle;
  }

  public CellStyle getHeaderStyle() {
    return headerStyle;
  }

  public CellStyle getContentStyle() {
    return contentStyle;
  }

  public WorkTypeEnum getWorkType() {
    return workType;
  }
}
