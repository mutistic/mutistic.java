package com.mutistic.file.tree;

import com.mutistic.utils.common.DateUtil;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @version <pre>
 * Author  Version   Date	 	Changes
 * yinyc    1.0     2019/9/9   Created
 * </pre>
 */
public class GenerateTreeUtil {

  public final static String CELL_CONCAT = "│";
  public final static String ROW = "─";

  public final static String CELL = "├─";
  public final static String ROW_CELL = "│  ├─";
  public final static String ROW_END ="└─";

  public static void main(String[] args) {
    StringBuilder builder = new StringBuilder();
    initFirst(builder, "周例会");
    initContent(builder, initXS());
    System.out.println(builder);
  }

  private static Map<String, List<String>> initXS() {
    Map<String, List<String>> map = new LinkedHashMap<>();
    return map;
  }

  private static void initFirst(StringBuilder builder, String title) {
    builder.append(CELL);
    builder.append(DateUtil.format(new Date()));
    builder.append(title);
  }

  private static void initContent(StringBuilder builder, Map<String, List<String>> initXS) {
  }
}
