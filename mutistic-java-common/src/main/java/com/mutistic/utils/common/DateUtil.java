package com.mutistic.utils.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class DateUtil {

  public static final String Default_DATE_PATTERN = "yyyy-MM-dd";

  private static DateFormat dateFormat = new SimpleDateFormat(
      Default_DATE_PATTERN);

  private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal();
  private static final Object object = new Object();


  public static String formatDate(Date date) {
    return dateFormat.format(date);
  }

  public static String formatDate(Date date, String format) {
    if (date != null) {
      DateFormat dateFormat = new SimpleDateFormat(format);
      return dateFormat.format(date);
    }
    return null;
  }

  public static Date parseDate(String dateStr) throws ParseException {
    return dateFormat.parse(dateStr);
  }

  public static Date parseDate(String dateStr, String format) throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.parse(dateStr);
  }

  public static int getYear(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(Calendar.YEAR);
  }

  public static int getMonth(Date date) {
    if (date != null) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      return c.get(Calendar.MONTH);
    } else {
      return 0;
    }
  }

  public static Date addYear(Date date, Float period) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.MONTH, Math.round(12 * period));

    return c.getTime();
  }

  public static Date parse(String date, String parttern) {
    Date myDate = null;
    if (date != null) {
      try {
        myDate = getDateFormat(parttern).parse(date);
      } catch (Exception var4) {
        ;
      }
    }

    return myDate;
  }

  private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
    if (StringUtils.isBlank(pattern)) {
      pattern = "yyyy年MM月dd日 HH:mm:ss";
    }

    SimpleDateFormat dateFormat = (SimpleDateFormat) threadLocal.get();
    if (dateFormat == null) {
      Object var2 = object;
      synchronized (object) {
        dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false);
        threadLocal.set(dateFormat);
      }
    }

    dateFormat.applyPattern(pattern);
    return dateFormat;
  }

  private static Date getAccurateDate(List<Long> timestamps) {
    Date date = null;
    long timestamp = 0L;
    Map<Long, long[]> map = new HashMap();
    List<Long> absoluteValues = new ArrayList();
    if (timestamps != null && timestamps.size() > 0) {
      if (timestamps.size() > 1) {
        for (int i = 0; i < timestamps.size(); ++i) {
          for (int j = i + 1; j < timestamps.size(); ++j) {
            long absoluteValue = Math.abs((Long) timestamps.get(i) - (Long) timestamps.get(j));
            absoluteValues.add(absoluteValue);
            long[] timestampTmp = new long[]{(Long) timestamps.get(i), (Long) timestamps.get(j)};
            map.put(absoluteValue, timestampTmp);
          }
        }

        long minAbsoluteValue = -1L;
        if (!absoluteValues.isEmpty()) {
          minAbsoluteValue = (Long) absoluteValues.get(0);

          for (int i = 1; i < absoluteValues.size(); ++i) {
            if (minAbsoluteValue > (Long) absoluteValues.get(i)) {
              minAbsoluteValue = (Long) absoluteValues.get(i);
            }
          }
        }

        if (minAbsoluteValue != -1L) {
          long[] timestampsLastTmp = (long[]) map.get(minAbsoluteValue);
          long dateOne = timestampsLastTmp[0];
          long dateTwo = timestampsLastTmp[1];
          if (absoluteValues.size() == 1) {
            timestamp = (Long) absoluteValues.get(0);
          }

          if (absoluteValues.size() > 1) {
            timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne : dateTwo;
          }
        }
      } else {
        timestamp = (Long) timestamps.get(0);
      }
    }

    if (timestamp != 0L) {
      date = new Date(timestamp);
    }

    return date;
  }

  public static Date stringToDate(String date, String pattern) {
    Date myDate = null;
    if (StringUtils.isNotBlank(pattern)) {
      myDate = parse(date, pattern);
      return myDate;
    } else {
      List<Long> timestamps = new ArrayList();
      PatternEnum[] var4 = PatternEnum.values();
      int var5 = var4.length;

      for (int var6 = 0; var6 < var5; ++var6) {
        PatternEnum style = var4[var6];
        Date dateTmp = parse(date, style.getValue());
        if (dateTmp != null) {
          timestamps.add(dateTmp.getTime());
        }
      }

      myDate = getAccurateDate(timestamps);
      return myDate;
    }
  }

  public static Date stringToDate(String date, PatternEnum pattern) {
    return stringToDate(date, pattern.getValue());
  }

  public static String dateToString(Date date, String pattern) {
    String dateString = null;
    if (date != null) {
      try {
        dateString = getDateFormat(pattern).format(date);
      } catch (Exception var4) {
        ;
      }
    }

    return dateString;
  }

  public static String dateToString(Date date, PatternEnum PatternEnum) {
    String dateString = null;
    if (PatternEnum != null) {
      dateString = getDate(date, PatternEnum.getValue());
    }

    return dateString;
  }

  public static String stringToString(String date, String parttern) {
    return format(date, (String) null, (String) parttern);
  }

  public static String getDate(String pattern) {
    return getDate(new Date(), pattern);
  }

  public static String getDate(Date date) {
    return dateToString(date, PatternEnum.YYYY_MM_DD);
  }

  public static String getDate(Date date, String parttern) {
    return dateToString(date, parttern);
  }

  public static String format(Calendar c, String pattern) {
    Calendar calendar = getCalendar(c);
    return getDateFormat(pattern).format(calendar.getTime());
  }

  public static String format(String date) {
    return format(date, PatternEnum.YYYY_MM_DD);
  }

  public static String format(Date date) {
    Locale locale = new Locale("zh", "CN");
    return format(date, locale);
  }

  public static String format(String date, PatternEnum PatternEnum) {
    return format(date, (PatternEnum) null, (PatternEnum) PatternEnum);
  }

  public static String format(String date, String olddParttern, String newParttern) {
    String dateString = null;
    if (olddParttern == null) {
      PatternEnum style = getDatePattern(date);
      if (style != null) {
        Date myDate = parse(date, style.getValue());
        dateString = getDate(myDate, newParttern);
      }
    } else {
      Date myDate = parse(date, olddParttern);
      dateString = getDate(myDate, newParttern);
    }

    return dateString;
  }

  public static String format(String date, PatternEnum olddDteStyle, PatternEnum newPatternEnum) {
    String dateString = null;
    if (olddDteStyle == null) {
      PatternEnum style = getDatePattern(date);
      dateString = format(date, style.getValue(), newPatternEnum.getValue());
    } else {
      dateString = format(date, olddDteStyle.getValue(), newPatternEnum.getValue());
    }

    return dateString;
  }

  public static String format(Date date, Locale locale) {
    DateFormat df = DateFormat.getDateInstance(2, locale);
    return df.format(date);
  }

  public static PatternEnum getDatePattern(String date) {
    PatternEnum PatternEnum = null;
    Map<Long, PatternEnum> map = new HashMap();
    List<Long> timestamps = new ArrayList();
    PatternEnum[] var4 = PatternEnum.values();
    int var5 = var4.length;

    for (int var6 = 0; var6 < var5; ++var6) {
      PatternEnum pattern = var4[var6];
      Date dateTmp = parse(date, pattern.getValue());
      if (dateTmp != null) {
        timestamps.add(dateTmp.getTime());
        map.put(dateTmp.getTime(), pattern);
      }
    }

    PatternEnum = (PatternEnum) map.get(getAccurateDate(timestamps).getTime());
    return PatternEnum;
  }


  private static Calendar getCalendar(Calendar c) {
    if (c == null) {
      c = getCalendar();
    }

    return c;
  }

  public static Calendar getCalendar() {
    return Calendar.getInstance();
  }

  //现在到今天结束的毫秒数
  public static Long getCurrent2TodayEndMillisTime() {
    Calendar todayEnd = Calendar.getInstance();
    // Calendar.HOUR 12小时制
    // HOUR_OF_DAY 24小时制
    todayEnd.set(Calendar.HOUR_OF_DAY, 23);
    todayEnd.set(Calendar.MINUTE, 59);
    todayEnd.set(Calendar.SECOND, 59);
    todayEnd.set(Calendar.MILLISECOND, 999);
    return todayEnd.getTimeInMillis() - System.currentTimeMillis();
  }

  /**
   * 返回 相隔多少天
   *
   * @param date1 yyyy-MM-dd
   * @param date2 yyyy-MM-dd
   */
  public static int differentDays(String date1, String date2) throws ParseException {
    Date temp1 = parseDate(date1);
    Date temp2 = parseDate(date2);
    int days = (int) Math.abs((temp1.getTime() - temp2.getTime()) / (1000 * 3600 * 24));
    return days;
  }

  /**
   * 时间戳转换成日期格式字符串
   *
   * @param seconds 精确到秒的字符串
   */
  public static String timeStamp2DateStr(Long seconds, String parttern) {
    if (seconds == null || seconds.equals(0)) {
      return "";
    }
    if (parttern == null || parttern.isEmpty()) {
      parttern = "yyyy-MM-dd HH:mm:ss";
    }
    SimpleDateFormat sdf = new SimpleDateFormat(parttern);
    return sdf.format(new Date(seconds));
  }

  public static Date timeStamp2Date(Long seconds, String parttern) {
    String dateStr = timeStamp2DateStr(seconds, parttern);
    if (dateStr != null && !dateStr.equals("")) {
      return stringToDate(dateStr, parttern);
    }
    return null;
  }

  /**
   * 日期格式字符串转换成时间戳
   *
   * @param date_str 字符串日期
   * @param parttern 如：yyyy-MM-dd HH:mm:ss
   */
  public static String date2TimeStamp(String date_str, String parttern) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(parttern);
      return String.valueOf(sdf.parse(date_str).getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

}
