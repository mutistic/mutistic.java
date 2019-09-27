package com.mutistic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * tips 线程不安全
 *
 * @author mutistic
 * @version 1.0 2019/9/27
 */
@Target({ElementType.TYPE, ElementType.METHOD}) // 可以声明在类、方法上
@Retention(RetentionPolicy.SOURCE) // 不编译
public @interface ThreadUnSafe {

}
