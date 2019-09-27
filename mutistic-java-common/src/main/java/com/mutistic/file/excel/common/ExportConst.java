package com.mutistic.file.excel.common;

import com.mutistic.file.excel.compar.ExportColumnCompar;
import java.text.DecimalFormat;

/**
 * Export 常量
 *
 * @version <pre>
 * Author  Version   Date	 	Changes
 * yinyc    1.0     2019/8/22   Created
 * </pre>
 */
public class ExportConst {

  // file
  /**
   * 临时导出文件 路径
   */
  public static final String OSS_TEMP_EXCEL_PATH = "/temp/excel/";
  /**
   * 服务器导出文件 路径
   */
  public static final String LOCAL_PATH = System.getProperty("user.dir") + "/";
  /**
   * 路径链接符：/
   */
  public static final String PATH_CONCAT = "/";
  /**
   * 文件默认前缀：0
   */
  public static final String FILE_PREFIX_DEFAULT = "0";
  /**
   * 文件链接符：_
   */
  public static final String FILE_CONCAT = "_";

  // fomat
  public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("000");
  public static final ExportColumnCompar COLUMN_COMP = new ExportColumnCompar();

  // content application/json,application/octet-stream
  public static final String CONTENT_TYPE = "application/octet-stream;charset=UTF-8";
  public static final String CONTENT_DISPOSITION = "Content-Disposition";
  public static final String CONTENT_DISPOSITION_VALUE = "attachment;fileName=";

  // style
  /**
   * header 字体默认：等线
   */
  public static final String HEADER_FONT_NAME = "等线";
  /**
   * header 字体默认大小：12
   */
  public static final short HEADER_FONT_SIZE = 12;
  /**
   * 列宽：默认20字符
   */
  public static final int CELL_WIDTH = 20 * 256;

  /**
   * Row 最小总分页数
   */
  public static final int MIN_ROW_TOTAL_PAGE = 2;
  /**
   * Row 最小每页内容数
   */
  public static final int MIN_ROW_PAGE_SIZE = 20;

  /**
   * Cell 最小总分页数(暂不启用)
   */
  public static final int MIN_CELL_TOTAL_PAGE = 1;
  /**
   * Cell 最小每页内容数
   */
  public static final int MIN_CELL_PAGE_SIZE = 5;
}
