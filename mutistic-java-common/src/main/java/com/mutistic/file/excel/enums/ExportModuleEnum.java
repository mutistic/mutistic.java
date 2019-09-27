package com.mutistic.file.excel.enums;

/**
 * 导出模块枚举
 *
 * @version <pre>
 * Author  Version   Date	 	Changes
 * yinyc    1.0     2019/8/23   Created
 * </pre>
 */
public enum ExportModuleEnum {
  /**
   * riskcontrol
   */
  RISKCONTROL("riskcontrol"),
  /**
   * 风控扫描：risk
   */
  RISK("risk"),
  ;

  ExportModuleEnum(String name) {
    this.name = name;
  }

  private String name;

  public String getName() {
    return name;
  }
}
