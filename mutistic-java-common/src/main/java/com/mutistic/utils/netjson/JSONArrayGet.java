package com.mutistic.utils.netjson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSONArray GET 操作
 * @author yinyc
 * @version 1.0 2019/9/26
 */
public class JSONArrayGet {

  private JSONArray array;

  private JSONArrayGet() {
    super();
  }

  public static JSONArrayGet setArray(JSONArray array) {
    JSONArrayGet chained = new JSONArrayGet();
    if (array == null) {
      array = new JSONArray();
    }
    chained.array = array;
    return chained;
  }

  /**
   * JSONArray.getJSONArray(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param index index
   * @return JSONObject.getJSONArray(key)
   */
  public JSONArray getJSONArray(int index) {
    return JSONUtil.getJSONArray(this.array, index);
  }

  /**
   * JSONArray.getJSONObject(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param index index
   * @return JSONObject.getJSONObject(key)
   */
  public JSONObject getJSONObject(int index) {
    return JSONUtil.getJSONObject(this.array, index);
  }

  /**
   * JSONArray.getString(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param index index
   * @return JSONObject.getString(key)
   */
  public String getString(int index) {
    return JSONUtil.getString(this.array, index);
  }
}
