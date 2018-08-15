package com.article.config.page;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.article.util.Const;


/**
 * 返回对象的实体类封装
 * @author mei
 */
public class ResponseInfo {
	/**
	 * <p>
	 * Description:[0：正常，1：异常]
	 * </p>
	 */
	private Integer code;
	/**
	 * <p>
	 * Description:返回的消息
	 * </p>
	 */
	private String message;

	/**
	 * 构造方法：默认返回成功
	 */
	public ResponseInfo() {
		this.message = Const.SUCCESS_INFO;
		this.code = Const.SUCCESS;
	}

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String toJsonString(ResponseInfo responseInfo) {
	  JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("code", responseInfo.getCode());
      jsonObject.put("message", responseInfo.getMessage());
      Map<String, Object> data = responseInfo.getDataMap();
      if (data != null && data.size() > 0) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
          jsonObject.put(entry.getKey(), entry.getValue());
        }
      }
    } catch (JSONException e) {
      jsonObject = null;
      e.printStackTrace();
    }
		return jsonObject.toString();
	}
}
