package com.mutistic.mock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 模拟执行并发操作
 *
 * @author mutistic
 * @version 1.0 2019/9/29
 */
@Slf4j
public class MockConcurrent {

  /**
   * 线程并发数
   */
  private static int THREAD_TOTAL = 200;
  /**
   * 访问总次数
   */
  private static int CLIENT_TOTAL = 5000;

  /**
   * 通过信号量方式 > 模拟执行并发操作
   *
   * @param operation 操作类型
   */
  public static void mockSemaphore(@NonNull IOperation operation) {
    log.info("================ 通过信号量方式 > 模拟执行并发操作 > 开始 ====================");
    log.info("Step-1-创建ExecutorService线程池");
    ExecutorService exec = Executors.newCachedThreadPool();
    log.info("Step-2-创建信号量：控制线程的并发数量(permits：{})，默认为非公平", THREAD_TOTAL);
    final Semaphore semaphore = new Semaphore(THREAD_TOTAL);
    log.info("Step-3-循环访问总次数：{}", CLIENT_TOTAL);
    log.info("Step-4-通过线程池执行创建线程，然后执行操作{}.operation()操作", operation.getClass().getSimpleName());
    for (int index = 0; index < CLIENT_TOTAL; index++) {
      // Step-4-创建线程并发访问
      // JDK1.8流模式：() -> {}
      // JDK1.8之前：new Runnable() {  @Override  public void run() {} }
      int finalIndex = index;
      exec.execute(() -> {
        try {
          // Step_4.1-控制只能访问一次：Semaphore.acquire() 和 Semaphore.release()之间的代码，同一时刻只允许制定个数的线程进入
          semaphore.acquire();
          // Step_4.2-执行操作
          operation.operation(finalIndex);
          // Step_4.3-控制只能访问一次
          semaphore.release();
        } catch (Exception e) {
          log.error(" exception", e);
        }
      });
    }
    exec.shutdown();
    log.info("Step-5-执行结束，关闭线程池");
    log.info("================ 通过信号量方式 > 模拟执行并发操作 > 结束 ====================");  }

  public static int getThreadTotal() {
    return THREAD_TOTAL;
  }

  public static void setThreadTotal(int threadTotal) {
    THREAD_TOTAL = threadTotal;
  }

  public static int getClientTotal() {
    return CLIENT_TOTAL;
  }

  public static void setClientTotal(int clientTotal) {
    CLIENT_TOTAL = clientTotal;
  }

}
