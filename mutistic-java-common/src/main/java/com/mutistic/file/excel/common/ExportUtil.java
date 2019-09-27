package com.mutistic.file.excel.common;

import com.mutistic.file.excel.annotation.ExportColumn;
import com.mutistic.file.excel.annotation.ExportTable;
import com.mutistic.file.excel.enums.ExportModuleEnum;
import com.mutistic.file.excel.enums.WorkTypeEnum;
import com.mutistic.utils.common.CommonUtil;
import com.mutistic.utils.common.DateUtil;
import com.mutistic.utils.common.PatternEnum;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * 导出工具类
 *
 * @author yinyc
 * @date 2019/8/21 14:04
 */
public class ExportUtil {

  public static List<ExportDataVO> testData2(int num) {
    List<ExportDataVO> list = new ArrayList<>();
    ExportDataVO user;
    for (int i = 1; i <= num; i++) {
      user = new ExportDataVO();
      user.setUserStr(DateUtil.dateToString(new Date(), PatternEnum.YYYYMMDDHHMMSS) + i);
      user.setUserInt(i * 10);
      user.setUserDouble(55.55 * i);
      user.setUserDate(new Date());
      user.setUserLong(System.currentTimeMillis() + i);
      list.add(user);
    }
    return list;
  }

  public static List<ExportDataVO> testData(int num) {
    List<ExportDataVO> list = new ArrayList<>();
    ExportDataVO user;
    for (int i = 1; i <= num; i++) {
      user = new ExportDataVO();
      user.setUserStr(DateUtil.dateToString(new Date(), PatternEnum.YYYYMMDDHHMMSS) + i);
      user.setUserInt(i);
      user.setUserDouble(11.22 + i);
      user.setUserDate(new Date());
      user.setUserLong(System.currentTimeMillis() + i);
      list.add(user);
    }
    return list;
  }

  public static String disposeFileName(HttpServletRequest request, String fileName)
      throws UnsupportedEncodingException {
    String userAgent = request.getHeader("User-Agent");
    //针对IE或者以IE为内核的浏览器
    if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
      fileName = URLEncoder.encode(fileName, "UTF-8");
    }
    //非IE浏览器的处理：
    else {
      fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
    }

    return fileName;
  }

  public static void initResponse(HttpServletResponse response, String fileName) {
    response.setContentType(ExportConst.CONTENT_TYPE);
    response.setHeader(ExportConst.CONTENT_DISPOSITION,
        ExportConst.CONTENT_DISPOSITION_VALUE + fileName);
//    resp.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
//    resp.setHeader("Pragma", "no-cache");
//    resp.setHeader("Cache-Control", "no-cache");
//    resp.setDateHeader("Expires", 0);
  }

  /**
   * 获取 文件名
   *
   * @param filePrefix 文件名前缀
   * @return
   */
  public static StringBuilder getFileName(String filePrefix) {
    if (StringUtils.isEmpty(filePrefix)) {
      filePrefix = ExportConst.FILE_PREFIX_DEFAULT;
    }
    int capacity = 25 + filePrefix.length();
    StringBuilder fileName = new StringBuilder(capacity);
    fileName.append(filePrefix);
    fileName.append(ExportConst.FILE_CONCAT);
    fileName.append(DateUtil.dateToString(new Date(), PatternEnum.YYYYMMDDHHMMSS.getValue()));
    fileName.append(ExportConst.DECIMAL_FORMAT.format(Math.rint((Math.random() * 1000))));
    return fileName;
  }

  /**
   * 获取 excel Name
   *
   * @param filePrefix   文件名前缀
   * @param workTypeEnum 文件类型（非空）
   */
  public static String getExcelName(String filePrefix, WorkTypeEnum workTypeEnum) {
    return getFileName(filePrefix).append(workTypeEnum.getName()).toString();
  }

  /**
   * 获取oss path (默认：template/excel 下)
   *
   * @param moduleEnum 模块（非空）
   * @param excelName  excel文件名（非空）
   * @return
   */
  public static String getOssFilePath(ExportModuleEnum moduleEnum, String excelName) {
    return getOssFilePath(moduleEnum, ExportConst.OSS_TEMP_EXCEL_PATH, excelName);
  }

  /**
   * 获取 oss file path
   *
   * @param moduleEnum 模块（非空）
   * @param pathPrefix 路径前缀（非空）
   * @param excelName  excel文件名（非空）
   * @return
   */
  public static String getOssFilePath(ExportModuleEnum moduleEnum, String pathPrefix,
      String excelName) {
    int capacity = moduleEnum.getName().length() + pathPrefix.length() + excelName.length();
    StringBuilder path = new StringBuilder(capacity);
    path.append(moduleEnum.getName());
    path.append(pathPrefix);
    path.append(excelName);
    return path.toString();
  }

  /**
   * 获取本地文件 path
   *
   * @param filePrefix   文件名前缀
   * @param workTypeEnum 文件类型
   * @return
   */
  public static String getLocalFilePath(String filePrefix, WorkTypeEnum workTypeEnum) {
    return ExportConst.LOCAL_PATH + getExcelName(filePrefix, workTypeEnum);
  }

  /**
   * 获取页签名
   */
  public static <T> String getSheetName(Class<T> clazz) {
    if (clazz == null) {
      return null;
    }

    ExportTable table = clazz.getAnnotation(ExportTable.class);
    if (table == null && StringUtils.isEmpty(table.name())) {
      return null;
    }
    return table.name();
  }

  /**
   * 获取 字段、标题 关系集合
   *
   * @param clazz
   * @param <T>
   * @return
   * @throws Exception
   */
  public static <T> Map<ExportColumn, Field> getRelation(Class<T> clazz) throws Exception {
    Map<ExportColumn, Field> columnMap = null;
    if (clazz != null) {
      Field[] fields = clazz.getDeclaredFields();
      columnMap = new HashMap<>(fields.length);

      List<ExportColumn> columnList = new ArrayList<>();
      ExportColumn excelField;
      for (Field field : fields) {
        excelField = field.getAnnotation(ExportColumn.class);
        if (excelField != null) {
          columnMap.put(excelField, field);
          columnList.add(excelField);
        }
      }
      Collections.sort(columnList, ExportConst.COLUMN_COMP);

      Map<ExportColumn, Field> filedMap = new LinkedHashMap<>(columnList.size());
      for (ExportColumn column : columnList) {
        filedMap.put(column, columnMap.get(column));
      }
    }
    if (CommonUtil.isEmpty(columnMap)) {
      throw new Exception("导出实体类，未配置导出注解！");
    }

    return columnMap;
  }

  public static <T> String[] getRecord(T data, Map<ExportColumn, Field> columnMap)
      throws Exception {
    String[] records = new String[columnMap.size()];
    Field field;
    Method method;
    Object value;

    try {
      int i = 0;
      for (ExportColumn column : columnMap.keySet()) {
        field = columnMap.get(column);
        method = new PropertyDescriptor(field.getName(), data.getClass()).getReadMethod();
        value = method.invoke(data);
        if (value == null) {
          value = "";
        } else if (value instanceof Date) {
          value = dateFormat(value, column.format());
        }
        records[i] = "" + value;
        i++;
      }
    } catch (Exception e) {
      throw e;
    }

    return records;
  }

  public static String dateFormat(Object data, String format) {
    return DateUtil.dateToString((Date) data,
        StringUtils.isEmpty(format) ? PatternEnum.YYYY_MM_DD_HH_MM_SS.getValue() : format);
  }

  /**
   * 获取总页数
   * @param pageSize 每页数量
   * @param totalSize 总数量
   * @return
   */
  public static int getTotalPage(int pageSize, int totalSize) {
    return totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
  }

  public static <T> List<List<T>> getPageList(List<T> rowData, int pageSize) {
    List<List<T>> pageList = new ArrayList<>();

    int totalPage = getTotalPage(pageSize, rowData.size());
    int from = 0;
    int to = 0;
    for (int i = 0; i < totalPage; i++) {
      from = pageSize * i;
      to = (from + pageSize) > rowData.size() ? rowData.size() : from + pageSize;
      pageList.add(rowData.subList(from, to));
    }

    return pageList;
  }

}
