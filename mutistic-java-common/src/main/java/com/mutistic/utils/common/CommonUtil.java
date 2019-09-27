package com.mutistic.utils.common;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 普通工具类
 *
 * @author yinyc
 * @date 2019/8/22 10:46
 */
public class CommonUtil {

  public final static String NULL_STR = "null";

  public static boolean isBlank(String data) {
    return data == null || data.trim().length() == 0 || data.toLowerCase().equals(NULL_STR);
  }

  public static boolean isEmpty(List data) {
    return data == null || data.size() == 0;
  }

  public static boolean isEmpty(Set data) {
    return data == null || data.size() == 0;
  }

  public static boolean isEmpty(Map data) {
    return data == null || data.size() == 0;
  }

}
