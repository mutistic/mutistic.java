package com.mutistic.utils.netjson;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON util
 *
 * @author yinyc
 * @version 1.0 2019/9/26
 */
public class JSONUtil {

  private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

  public static boolean isBlank(JSONObject data) {
    return data == null || data.size() == 0;
  }

  public static boolean isBlank(JSONArray data) {
    return data == null || data.size() == 0;
  }

  public static boolean isBlank(JSONObjectBuilder builder) {
    return builder == null || isBlank(builder.build());
  }

  public static boolean isBlank(JSONArrayBuilder builder) {
    return builder == null || isBlank(builder.build());
  }

  /**
   * 转换成 JSONArray对象
   * <p> tips：不抛出JSONException异常
   *
   * @param data Object对象
   * @return JSONArray对象
   */
  public static JSONArray fromJSONArray(Object data) {
    try {
      if (data instanceof JSONArray) {
        return (JSONArray) data;
      }
      return JSONArray.fromObject(data, JSONConfigSingleton.JSON_CONFIG);
    } catch (JSONException e) {
    }
    return null;
  }

  /**
   * 转换成 JSONObject 对象
   * <p> tips：不抛出JSONException异常
   *
   * @param data Object对象
   * @return JSONObject 对象
   */
  public static JSONObject fromJSONObject(Object data) {
    try {
      if (data instanceof JSONObject) {
        return (JSONObject) data;
      }
      return JSONObject.fromObject(data, JSONConfigSingleton.JSON_CONFIG);
    } catch (JSONException e) {
    }
    return null;
  }

  /**
   * JSONObject.getJSONArray(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param json JSONObject
   * @param key  key
   * @return JSONArray
   */
  public static JSONArray getJSONArray(JSONObject json, String key) {
    if (isBlank(json) || StringUtils.isBlank(key)) {
      return null;
    }

    try {
      return json.getJSONArray(key);
    } catch (JSONException e) {
    }
    return null;
  }

  /**
   * JSONObject.getJSONObject(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param json JSONObject
   * @param key  key
   * @return JSONObject
   */
  public static JSONObject getJSONObject(JSONObject json, String key) {
    if (isBlank(json) || StringUtils.isBlank(key)) {
      return null;
    }

    try {
      return json.getJSONObject(key);
    } catch (JSONException e) {
    }
    return null;
  }

  /**
   * JSONObject.getString(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param json JSONObject
   * @param key  key
   * @return String
   */
  public static String getString(JSONObject json, String key) {
    if (isBlank(json) || StringUtils.isBlank(key)) {
      return null;
    }

    try {
      return json.getString(key);
    } catch (JSONException e) {
    }
    return null;
  }

  /**
   * JSONArray.getJSONArray(index)
   * <p> tips：不抛出JSONException异常
   *
   * @param array JSONArray
   * @param index index
   * @return JSONArray
   */
  public static JSONArray getJSONArray(JSONArray array, int index) {
    if (isBlank(array)) {
      return null;
    }

    try {
      return array.getJSONArray(index);
    } catch (JSONException e) {
    }
    return null;
  }

  /**
   * JSONArray.getJSONObject(index)
   * <p> tips：不抛出JSONException异常
   *
   * @param array JSONArray
   * @param index index
   * @return JSONObject
   */
  public static JSONObject getJSONObject(JSONArray array, int index) {
    if (isBlank(array)) {
      return null;
    }

    try {
      return array.getJSONObject(index);
    } catch (JSONException e) {
    }
    return null;
  }

  /**
   * JSONArray.getString(index)
   * <p> tips：不抛出JSONException异常
   *
   * @param array JSONArray
   * @param index index
   * @return String
   */
  public static String getString(JSONArray array, int index) {
    if (isBlank(array)) {
      return null;
    }

    try {
      return array.getString(index);
    } catch (JSONException e) {
    }
    return null;
  }

  /**
   * JSONObjectBuilder.build().getString(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param builder JSONObjectBuilder
   * @param key     key
   * @return JSONArray
   */
  public static JSONArray getJSONArray(JSONObjectBuilder builder, String key) {
    if (builder == null) {
      return null;
    }
    return getJSONArray(builder.build(), key);
  }

  /**
   * JSONObjectBuilder.build().getJSONObject(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param builder JSONObjectBuilder
   * @param key     key
   * @return JSONObject
   */
  public static JSONObject getJSONObject(JSONObjectBuilder builder, String key) {
    if (builder == null) {
      return null;
    }

    return getJSONObject(builder.build(), key);
  }

  /**
   * JSONObjectBuilder.build().getString(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param builder JSONObjectBuilder
   * @param key     key
   * @return String
   */
  public static String getString(JSONObjectBuilder builder, String key) {
    if (builder == null) {
      return null;
    }

    return getString(builder.build(), key);
  }

  /**
   * JSONArrayBuilder.build().getJSONArray(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param builder JSONArrayBuilder
   * @param index   index
   * @return JSONArray
   */
  public static JSONArray getJSONArray(JSONArrayBuilder builder, int index) {
    if (builder == null) {
      return null;
    }
    return getJSONArray(builder.build(), index);
  }

  /**
   * JSONArrayBuilder.build().getJSONObject(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param builder JSONArrayBuilder
   * @param index   index
   * @return JSONObject
   */
  public static JSONObject getJSONObject(JSONArrayBuilder builder, int index) {
    if (builder == null) {
      return null;
    }

    return getJSONObject(builder.build(), index);
  }

  /**
   * JSONArrayBuilder.build().getString(key)
   * <p> tips：不抛出JSONException异常
   *
   * @param builder JSONArrayBuilder
   * @param index   index
   * @return String
   */
  public static String getString(JSONArrayBuilder builder, int index) {
    if (builder == null) {
      return null;
    }

    return getString(builder.build(), index);
  }
}
