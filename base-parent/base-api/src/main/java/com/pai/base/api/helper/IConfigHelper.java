package com.pai.base.api.helper;

import java.util.List;

public interface IConfigHelper {
	public void init();
	public String getParamValue(String paramName);
	public List<String> getParamToList(String paramName);
	public List<Integer> getParamToListInteger(String paramName);
	public boolean getBool(String paramName);
	public int getInt(String paramName);
	public List<String> getLikeToList(String likeName);
}
