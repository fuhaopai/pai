package com.pai.app.web.core.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pai.base.core.util.string.StringUtils;

public class AttrNameOpUtil {
	public static String getSkuSpec(String attrNames,String attrOplabels){
		StringBuffer sku=new StringBuffer();
		if(StringUtils.isNotBlank(attrNames)&&StringUtils.isNotBlank(attrOplabels)){
			String[] ArrayName=attrNames.split(",");
			String[] ArrayOpt=attrOplabels.split(",");
			Map<String,List<String>> map=new HashMap<String,List<String>>();
			List<String> list=null;
			for (int i = 0; i < ArrayName.length; i++)
			{
				if(map.get(ArrayName[i])==null){
					list=new ArrayList<String>();
					list.add(ArrayOpt[i]);
					map.put(ArrayName[i],list);
				}else{
					list=map.get(ArrayName[i]);
					list.add(ArrayOpt[i]);
					map.put(ArrayName[i],list);
				}
				
			}
			
			for (Entry<String, List<String>> m : map.entrySet())
			{
				sku.append(m.getKey()).append(":").append(StringUtils.join(m.getValue(), ",")).append(";");
			}
		}
		if(StringUtils.isEmpty(sku.toString())){
			return "";
		}else{
			return sku.substring(0, sku.length()-1).toString();
		}
	}
}
