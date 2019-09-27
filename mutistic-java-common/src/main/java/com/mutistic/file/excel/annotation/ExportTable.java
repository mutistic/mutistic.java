package com.mutistic.file.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Export Table 注解
 *
 * @author yinyc
 * @date 2019/8/21 9:45
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportTable {
  // 标签页
  String name() default "";
}
