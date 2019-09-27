package com.mutistic.file.excel.enums;

/**
 * excel work 类型枚举
 *
 * @version <pre>
 * Author  Version   Date	 	Changes
 * yinyc    1.0     2019/8/23   Created
 * </pre>
 */
public enum WorkTypeEnum {
  HSSF(".xls"),
  XSSF(".xlsx"),
  SXSSF(".xlsx"),
//    CSV(".csv")
  ;

  WorkTypeEnum(String name) {
    this.name = name;
  }

  private String name;

  public String getName() {
    return name;
  }
}
