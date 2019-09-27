package com.mutistic.utils.netjson;

import net.sf.json.JsonConfig;

/**
 * JsonConfig 单例对象
 * @author yinyc
 * @version 1.0 2019/9/26
 */
public class JSONConfigSingleton {

  public final static JsonConfig JSON_CONFIG = Singleton.INSTANCE.getInstance();

  private enum Singleton {
    INSTANCE;
    private JsonConfig instance;

    Singleton() {
      instance = new JsonConfig();
    }

    public JsonConfig getInstance() {
      return instance;
    }
  }
}
