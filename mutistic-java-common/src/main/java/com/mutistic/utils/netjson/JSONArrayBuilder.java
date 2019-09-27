package com.mutistic.utils.netjson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSONArray 构建类
 * <p> 使用JSONArray.element(value, config)进行添加数据
 * <p> 设置config为单例对象：config > JsonConfig > JSONConfigSingleton.JSON_CONFIG
 *
 * @author yinyc
 * @version 1.0 2019/9/26
 */
public class JSONArrayBuilder {

  /**
   * JSONArray 数据
   */
  private JSONArray array;

  /**
   * 私有构造器
   */
  private JSONArrayBuilder() {
    super();
  }

  /**
   * JSONArrayBuilder 初始化方法
   *
   * @return JSONArrayBuilder实例
   */
  public static JSONArrayBuilder builder() {
    return builder(null);
  }

  /**
   * JSONArrayBuilder 初始化方法
   *
   * @param jsonArray 源JSONArray
   * @return JSONArrayBuilder实例
   */
  public static JSONArrayBuilder builder(JSONArray jsonArray) {
    JSONArrayBuilder chained = new JSONArrayBuilder();
    if (jsonArray == null) {
      jsonArray = new JSONArray();
    }
    chained.array = jsonArray;
    return chained;
  }

  /**
   * 构建操作完毕后获取内部的JSONArray对象
   *
   * @return JSONArray
   */
  public JSONArray build() {
    return this.array;
  }

  /**
   * JSONArray新增数据（JSONArray.element(value, config)）
   *
   * @param data 数据
   * @return this
   */
  public JSONArrayBuilder add(Object data) {
    if (data instanceof JSONObjectBuilder) {
      this.array.element(((JSONObjectBuilder) data).build(), JSONConfigSingleton.JSON_CONFIG);
    } else {
      this.array.element(data, JSONConfigSingleton.JSON_CONFIG);
    }
    return this;
  }

  /**
   * JSONArray.getJSONArray(index)
   * <p> tips：不抛出JSONException异常
   *
   * @param index index
   * @return JSONArray
   */
  public JSONArray getJSONArray(int index) {
    return JSONUtil.getJSONArray(this.array, index);
  }

  /**
   * JSONArray.getJSONObject(index)
   * <p> tips：不抛出JSONException异常
   *
   * @param index index
   * @return JSONObject
   */
  public JSONObject getJSONObject(int index) {
    return JSONUtil.getJSONObject(this.array, index);
  }

  /**
   * JSONArray.getString(index)
   * <p> tips：不抛出JSONException异常
   *
   * @param index index
   * @return String
   */
  public String getString(int index) {
    return JSONUtil.getString(this.array, index);
  }
}
