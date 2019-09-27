package com.mutistic.utils.netjson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSONObject 构建类
 * <p> 使用JSONObject.element(key, value, config)进行添加数据
 * <p> 设置config为单例对象：config > JsonConfig > JSONConfigSingleton.JSON_CONFIG
 *
 * @author yinyc
 * @version 1.0 2019/9/26
 */
public class JSONObjectBuilder {

  /**
   * JSONObject 数据
   */
  private JSONObject json;

  /**
   * 私有构造器
   */
  private JSONObjectBuilder() {
    super();
  }

  /**
   * JSONObjectBuilder 初始化方法
   *
   * @return JSONObjectBuilder实例
   */
  public static JSONObjectBuilder builder() {
    return builder(null);
  }

  /**
   * JSONObjectBuilder 初始化方法
   *
   * @param jsonObject 源JSONObject
   * @return JSONObjectBuilder实例
   */
  public static JSONObjectBuilder builder(JSONObject jsonObject) {
    return builder(jsonObject, false);
  }

  /**
   * JSONObjectBuilder 初始化方法
   *
   * @param jsonObject 源JSONObject
   * @param isNull     JSONObject.value是否为空，false-非空，true-可以为空
   * @return JSONObjectBuilder实例
   */
  public static JSONObjectBuilder builder(JSONObject jsonObject, boolean isNull) {
    JSONObjectBuilder chained = new JSONObjectBuilder();
    if (jsonObject == null) {
      jsonObject = new JSONObject(isNull);
    }
    chained.json = jsonObject;
    return chained;
  }

  /**
   * 构建操作完毕后获取内部的JSONObject对象
   *
   * @return JSONObject
   */
  public JSONObject build() {
    return this.json;
  }

  /**
   * JSONObject新增数据（JSONObject.element(key, value, config)）
   *
   * @param data 数据
   * @return this
   */
  public JSONObjectBuilder put(String key, Object data) {
    if (data instanceof JSONArrayBuilder) {
      this.json.element(key, ((JSONArrayBuilder) data).build(), JSONConfigSingleton.JSON_CONFIG);
    } else {
      this.json.element(key, data, JSONConfigSingleton.JSON_CONFIG);
    }
    return this;
  }

  /**
   * JSONObject.getJSONArray(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param key  key
   * @return JSONArray
   */
  public JSONArray getJSONArray(String key) {
    return JSONUtil.getJSONArray(this.json, key);
  }

  /**
   * JSONObject.getJSONObject(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param key  key
   * @return JSONObject
   */
  public JSONObject getJSONObject(String key) {
    return JSONUtil.getJSONObject(this.json, key);
  }

  /**
   * JSONObject.getString(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param key  key
   * @return String
   */
  public String getString(String key) {
    return JSONUtil.getString(this.json, key);
  }
}
