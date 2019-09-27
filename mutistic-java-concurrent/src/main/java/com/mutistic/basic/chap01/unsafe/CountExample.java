package com.mutistic.basic.chap01.unsafe;

import com.mutistic.annotation.ThreadUnSafe;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

/**
 * 示例01-并发执行计数功能：count++实现
 *
 * @author mutistic
 * @version 1.0 2019/9/27
 */
@Slf4j
@ThreadUnSafe
public class CountExample {

  /**
   * 线程并发数
   */
  private static int THREAD_TOTAL = 200;
  /**
   * 访问总次数
   */
  private static int CLIENT_TOTAL = 5000;
  /**
   * 计数
   */
  private static long count = 0;

  public static void main(String[] args) {
    // Step_1-创建线程池
    ExecutorService exec = Executors.newCachedThreadPool();
    // Step_2-创建信号量：控制线程的并发数量(permits)，默认为非公平
    final Semaphore semaphore = new Semaphore(THREAD_TOTAL);
    // Step_3-循环CLIENT_TOTAL
    for (int index = 0; index < CLIENT_TOTAL; index++) {
      // Step_4-创建线程并发访问
      // JDK1.8流模式：() -> {}
      // JDK1.8之前：new Runnable() {  @Override  public void run() {} }
      exec.execute(() -> {
        try {
          // Step_4.1-控制只能访问一次：Semaphore.acquire() 和 Semaphore.release()之间的代码，同一时刻只允许制定个数的线程进入
          semaphore.acquire();
          // Step_4.2-执行count++操作（线程不安全）
          add();
          // Step_4.3-控制只能访问一次
          semaphore.release();
        } catch (Exception e) {
          log.error(" exception", e);
        }
      });
    }

    // Step_5-访问完毕关闭线程池
    exec.shutdown();
    // Step_6-查看并发执行计数结果
    log.info("查看并发执行计数结果：count = {}", count);
    if(count != CLIENT_TOTAL){
      log.info("示例结果：【线程不安全】并发对类的成员变量进行操作不做相关处理时会导致结果不是预期结果！");
    }
  }

  /**
   * count++操作
   */
  @ThreadUnSafe
  private static void add() {
    count++;
  }
}
