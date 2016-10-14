package com.pai.app.web.core.framework.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

/**
 *   bbcode转换成HTML，在界面显示
 *   @author Suoron.Zhu
 */

public class Bbcode {
	
     public static String bbcode2html(String str){
    	 
    	 if(str == null || str.indexOf("[/") == -1)  /* 不包含bbcode */
    		 return str;
    	 
    	 str = StringEscapeUtils.escapeHtml(str);
    	 
         Map<String,String> bbMap = new HashMap<String , String>();

         bbMap.put("(\r\n|\r|\n|\n\r)", "<br/>");
         bbMap.put("\\[b\\](.+?)\\[/b\\]", "<b>$1</b>");
         bbMap.put("\\[u\\](.+?)\\[/u\\]", "<u>$1</u>");
         bbMap.put("\\[i\\](.+?)\\[/i\\]", "<i>$1</i>");
         bbMap.put("\\[h1\\](.+?)\\[/h1\\]", "<h1>$1</h1>");
         bbMap.put("\\[h2\\](.+?)\\[/h2\\]", "<h2>$1</h2>");
         bbMap.put("\\[h3\\](.+?)\\[/h3\\]", "<h3>$1</h3>");
         bbMap.put("\\[h4\\](.+?)\\[/h4\\]", "<h4>$1</h4>");
         bbMap.put("\\[h5\\](.+?)\\[/h5\\]", "<h5>$1</h5>");
         bbMap.put("\\[h6\\](.+?)\\[/h6\\]", "<h6>$1</h6>");
         bbMap.put("\\[quote\\](.+?)\\[/quote\\]", "<blockquote>$1</blockquote>");
         bbMap.put("\\[p\\](.+?)\\[/p\\]", "<p>$1</p>");
         bbMap.put("\\[p=(.+?),(.+?)\\](.+?)\\[/p\\]", "<p style='text-indent:$1px;line-height:$2%;'>$3</p>");
         bbMap.put("\\[center\\](.+?)\\[/center\\]", "<div align='center'>$1");
         bbMap.put("\\[align=(.+?)\\](.+?)\\[/align\\]", "<div style='text-align: $1;'>$2</div>");
         bbMap.put("\\[color=(.+?)\\](.+?)\\[/color\\]", "<font color='$1'>$2</font>");
         bbMap.put("\\[size=(.+?)\\](.+?)\\[/size\\]", "<font size='$1'>$2</font>");
         bbMap.put("\\[font=(.+?)\\](.+?)\\[/font\\]", "<font face='$1'>$2</font>");      
         bbMap.put("\\[img\\](.+?)\\[/img\\]", "<img src='$1' />");
         bbMap.put("\\[img=(.+?),(.+?)\\](.+?)\\[/img\\]", "<img width='$1' height='$2' src='$3' />");
         bbMap.put("\\[email\\](.+?)\\[/email\\]", "<a href='mailto:$1'>$1</a>");
         bbMap.put("\\[email=(.+?)\\](.+?)\\[/email\\]", "<a href='mailto:$1'>$2</a>");
         bbMap.put("\\[url\\](.+?)\\[/url\\]", "<a href='$1'>$1</a>");
         bbMap.put("\\[url=(.+?)\\](.+?)\\[/url\\]", "<a href='$1'>$2</a>");
         bbMap.put("\\[youtube\\](.+?)\\[/youtube\\]", "<object width='640' height='380'><param name='movie' value='http://www.youtube.com/v/$1'></param><embed src='http://www.youtube.com/v/$1' type='application/x-shockwave-flash' width='640' height='380'></embed></object>");
         bbMap.put("\\[video\\](.+?)\\[/video\\]", "<video src='$1' />");
         bbMap.put("\\[embed=(.+?),(.+?)\\](.+?)\\[/embed\\]", "<embed width='$1' height='$2' src='$3' allowfullscreen='true' quality='high' wmode='Opaque' align='middle' allowscriptaccess='always' type='application/x-shockwave-flash'/>");

         for (Map.Entry entry: bbMap.entrySet()) {
             str = str.replaceAll(entry.getKey().toString(), entry.getValue().toString());
         }

    	 return str;
     }
}
