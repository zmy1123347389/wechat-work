package com.article.entity;

/**
 * SysParameter entity. @author MyEclipse Persistence Tools
 */
public class SysParameter implements java.io.Serializable {


    // Fields    

     private String parameterId;
     private String paramName;
     private String paramValue;
     private String description;
	public String getParameterId() {
		return parameterId;
	}
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
     
     

}