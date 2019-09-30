package com.mutistic.basic.chap01.unsafe;

import com.mutistic.annotation.NotThreadSafe;
import com.mutistic.mock.IOperation;
import com.mutistic.mock.MockConcurrent;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * 示例02-并发执行计数功能：HashMap.put()实现
 * <p> result: HashMap是线程不安全类
 * @author mutistic
 * @version 1.0 2019/9/27
 */
@Slf4j
@NotThreadSafe
public class HashMapExample implements IOperation {

  /**
   * 计数
   */
  private static Map<Integer, Integer> countMap = new HashMap<>();

  public static void main(String[] args) {
    MockConcurrent.mockSemaphore(new HashMapExample());
    log.info("查看并发执行计数结果：【countMap.size()：{}】", countMap.size());
    if (countMap.size() != MockConcurrent.getClientTotal()) {
      log.info("示例结果：【线程不安全】");
      log.info("示例分析：【java.util.HashMap类本身是线程不安全的类，所以在多线程并发的情况下去进行put()，remove()等操作是线程不安全的】");
    }
  }

  /**
   * countMap.put()操作
   */
  @Override
  @NotThreadSafe
  public Integer operation(Object data) {
    countMap.put((int) data, (int) data);
    return countMap.size();
  }
}
