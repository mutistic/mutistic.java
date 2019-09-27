package com.mutistic.basic.chap01.unsafe;

import com.mutistic.annotation.ThreadUnSafe;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

/**
 * 示例02-并发执行计数功能：Map.put()实现
 *
 * @author mutistic
 * @version 1.0 2019/9/27
 */
@Slf4j
@ThreadUnSafe
public class MapExample {

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
  private static Map<Integer, Integer> countMap = new HashMap<>();

  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(THREAD_TOTAL);
    for (int index = 0; index < CLIENT_TOTAL; index++) {
      int finalIndex = index;
      exec.execute(() -> {
        try {
          semaphore.acquire();
          add(finalIndex);
          semaphore.release();
        } catch (Exception e) {
          log.error(" exception", e);
        }
      });
    }

    exec.shutdown();
    log.info("查看并发执行计数结果：countMap.size() = {}", countMap.size());
    if (countMap.size() != CLIENT_TOTAL) {
      log.info("示例结果：【线程不安全】并发对类的成员变量进行操作不做相关处理时会导致结果不是预期结果！");
    }
  }

  /**
   * countMap.put()操作
   */
  @ThreadUnSafe
  private static void add(int index) {
    countMap.put(index, index);
  }
}
