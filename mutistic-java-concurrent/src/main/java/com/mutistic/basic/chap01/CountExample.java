package com.mutistic.basic.chap01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

/**
 * 示例01-并发计数
 *
 * @author mutistic
 * @version 1.0 2019/9/27
 */
@Slf4j
public class CountExample {

  /**
   * 线程总数
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
    // Step01-
    ExecutorService exec = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(THREAD_TOTAL);
    for (int index = 0; index < CLIENT_TOTAL; index++) {
      // JDK1.8流模式：() -> {}
      // JDK1.8之前：new Runnable() {  @Override  public void run() {} }
      exec.execute(() -> {
        try {
          semaphore.acquire();
          add();
          semaphore.release();
        } catch (Exception e) {
          log.error(" exception", e);
        }
      });
    }

    // 必须在执行完毕后关闭
    exec.shutdown();
    log.info("count：{}", count);
  }

  /**
   * count++操作
   */
  private static void add() {
    count++;
  }
}
