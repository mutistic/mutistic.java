package com.mutistic.file.excel.common;

import com.mutistic.file.excel.annotation.ExportColumn;
import com.mutistic.file.excel.annotation.ExportTable;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@ExportTable(name = "扫描测试VO")
public class ExportDataVO implements Serializable {

  @ExportColumn(title = "字符串", sort = 1)
  private String userStr;

  @ExportColumn(title = "短整型",  sort = 2)
  private Integer userInt;

  @ExportColumn(title = "短整型", sort = 3)
  private Long userLong;

  @ExportColumn(title = "双精度", sort = 4)
  private Double userDouble;

  @ExportColumn(title = "日期",sort = 5)
  private Date userDate;
}
