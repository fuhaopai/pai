package com.pai.base.core.util.string;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils{
	public static String upperFirst(String str){
		String result = str.substring(0, 1).toUpperCase() + str.substring(1);
		return result;
	}
	
	public static String lowerFirst(String str){
		String result = str.substring(0, 1).toLowerCase() + str.substring(1);
		return result;
	}
	
	public  static String[] getBySplit(String content,String splitTag)
	{
		String[] aryRtn=new String[2];
		int pos=content.lastIndexOf(splitTag);
		aryRtn[0]=content.substring(0,pos);
		aryRtn[1]=content.substring(pos+splitTag.length());
		return aryRtn;
	}
	
	/**
	 * 替换标量。<br>
	 * <pre>
	 * 使用方法如下：
	 * String template="com/hotent/{path}/model/{class}";
	 * Map<String,String> map=new HashMap<String,String>();
	 * map.put("path","platform");
	 * map.put("class","Role");
	 * String tmp=replaceVariable(template,map);
	 * </pre>
	 * @param template
	 * @param map
	 * @return
	 * @throws CodegenException 
	 */
	public static String replaceVariable(String template,Map<String,String> map)
	{
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(template);
		while (regexMatcher.find()) {
			String key=regexMatcher.group(1);
			String toReplace=regexMatcher.group(0);
			String value=(String)map.get(key);
			if(value!=null){
				template=template.replace(toReplace, value);
			}
		}  
		
		return template;
	}
	
	/**
	 * 去掉前面的字符
	 * @param toTrim
	 * @param trimStr
	 * @return
	 */
	public static String trimPrefix(String toTrim,String trimStr)
	{
		while(toTrim.startsWith(trimStr))
		{
			toTrim=toTrim.substring(trimStr.length());
		}
		return toTrim;
	}
	
	/**
	 * 删除后面的字符
	 * @param toTrim
	 * @param trimStr
	 * @return
	 */
	public static String trimSufffix(String toTrim,String trimStr)
	{
		while(toTrim.endsWith(trimStr))
		{
			toTrim=toTrim.substring(0,toTrim.length()-trimStr.length());
		}
		return toTrim;
	}
	
	/**
	 * 删除指定的字符
	 * @param toTrim
	 * @param trimStr
	 * @return
	 */
	public static String trim(String toTrim,String trimStr)
	{
		return trimSufffix(trimPrefix(toTrim, trimStr), trimStr);
	}
	
	public static String combilePath(String baseDir,String dir)
	{
		baseDir=trimSufffix(baseDir, File.separator) ;
		dir=trimPrefix(dir,File.separator);
		
		return baseDir + File.separator + dir;
	}
	
	/**
	 * 使用字符换替换内容
	 * @param content 内容
	 * @param startTag 开始标签
	 * @param endTag 结束标签
	 * @param repalceWith 使用替换
	 * @return
	 */
	public static String replace(String content,String startTag,String endTag,String repalceWith)
	{
		String tmp=content.toLowerCase();
		String tmpStartTag=startTag.toLowerCase();
		String tmpEndTag=endTag.toLowerCase();
		
		
		StringBuffer sb=new StringBuffer();
		int beginIndex=tmp.indexOf(tmpStartTag);
		int endIndex=tmp.indexOf(tmpEndTag);
		while(beginIndex!=-1 && endIndex!=-1 && beginIndex<endIndex)
		{
			String pre=content.substring(0,tmp.indexOf(tmpStartTag)+tmpStartTag.length());
			String suffix=content.substring(tmp.indexOf(tmpEndTag));
			
			tmp=suffix.toLowerCase();
			content=suffix.substring(endTag.length());
			
			beginIndex=tmp.indexOf(tmpStartTag);
			endIndex=tmp.indexOf(tmpEndTag);
			String replaced=pre + "\r\n" +  repalceWith  +"\r\n" + endTag;
			sb.append(replaced);
		}
		sb.append(content);
		return sb.toString();
	}
	
	/**
	 * 判断指定的内容是否存在
	 * @param content 内容
	 * @param begin 开始标签
	 * @param end 结束标签
	 * @return
	 */
	public static boolean isExist(String content,String begin,String end)
	{
		String tmp=content.toLowerCase();
		begin=begin.toLowerCase();
		end=end.toLowerCase();
		int beginIndex=tmp.indexOf(begin);
		int endIndex=tmp.indexOf(end);
		if(beginIndex!=-1  && endIndex!=-1 && beginIndex<endIndex)
			return true;
		return false;
	}
	
	/**
	 * 截取字符串中的 指定两字串 中间的 字符串
	 * @param start
	 * @param end
	 * @return
	 */
	public static String subString(String content,String start,String end){
		String str=content;
		if(content.indexOf(start)!=-1){
			if(content.indexOf(end)!=-1){
				str=content.substring(content.indexOf(start)+start.length(),content.lastIndexOf(end));
			}else{
				str=content.substring(content.indexOf(start)+start.length());
			}
		}
		
		return str;
	}
	
	public static String getFileExt(String fileName){
		if(fileName.lastIndexOf(".")>-1){
			return fileName.substring(fileName.lastIndexOf(".")+1);	
		}
		return "";
	}
	
	public static String appendNamePostfix(String fileName,String namePostfix){
		if(fileName.lastIndexOf(".")>0){
			return fileName.substring(0, fileName.lastIndexOf(".")) + "_md" + fileName.substring(fileName.lastIndexOf("."));	
		}else{
			return fileName + "_md";
		}		
	}
	
	public static String getNameByEmail(String email) {
		int idx = email.indexOf("@");
		if (idx >= -1) {
			return StringUtils.capitalize(email.substring(0, idx));
		} else {
			return email;
		}
	}
	
	public static String removeNumber(String str){
		String regEx = "[0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        //替换与模式匹配的所有字符（即非数字的字符将被""替换）
        return m.replaceAll("").trim();		
	}
	
	public static String getNumber(String str)  
    {  
		if(StringUtils.isEmpty(str)){
			return "";
		}
		String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        //替换与模式匹配的所有字符（即非数字的字符将被""替换）
        return m.replaceAll("").trim();
    }  
	/**
	 * 将null 字符串 返回""
	 * @param str
	 * @return
	 */
	public static String get(String str){
		if(str==null){
			return "";
		}
		return str;
	}
	public static String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
		 if(text == null || text.trim().length() == 0)
			  return null;
		 StringBuffer buffer = new StringBuffer();
		 
		 for(int i=0;i<text.length();i++){
			 if((text.codePointAt(i) & 0xF8) == 0xF0)
			 {
				 i +=3;
				 continue;
			 }
			 buffer.append(text.charAt(i));
		 }		 		 
		 return buffer.toString();
	}	
	/**
	* 过滤掉超过3个字节的UTF8字符
	* @param text
	* @return
	* @throws UnsupportedEncodingException
	*/
	public static String filterOffUtf8Mb4_bak(String text) throws UnsupportedEncodingException {
	        byte[] bytes = text.getBytes("utf-8");
	        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
	        int i = 0;
	        while (i < bytes.length) {
	            short b = bytes[i];
	            if (b > 0) {
	                buffer.put(bytes[i++]);
	                continue;
	            }


	            b += 256; // 去掉符号位


	            if (((b >> 5) ^ 0x6) == 0) {
	                buffer.put(bytes, i, 2);
	                i += 2;
	            } else if (((b >> 4) ^ 0xE) == 0) {
	            buffer.put(bytes, i, 3);
	                i += 3;
	            } else if (((b >> 3) ^ 0x1E) == 0) {
	                i += 4;
	            } else if (((b >> 2) ^ 0x3E) == 0) {
	                i += 5;
	            } else if (((b >> 1) ^ 0x7E) == 0) {
	                i += 6;
	            } else {
	                buffer.put(bytes[i++]);
	            }
	        }
	        buffer.flip();
	        return new String(buffer.array(), "utf-8");
	}	
}
