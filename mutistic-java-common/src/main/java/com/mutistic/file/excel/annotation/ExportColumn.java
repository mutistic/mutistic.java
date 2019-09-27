package com.mutistic.file.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Export Filed 注解
 *
 * @author yinyc
 * @date 2019/8/21 9:45
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportColumn {
  // 标题
  String title();
  // 顺序
  int sort() default 0;
  // 格式化规则
  String format() default "";
  // 长度
  int length() default 0;
  // 所属组
  String group() default "";
}
