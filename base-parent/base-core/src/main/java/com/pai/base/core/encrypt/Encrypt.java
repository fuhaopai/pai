package com.pai.base.core.encrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
*  AES加密和解密算法
*  @author Suoron.Zhu
*
*/
public class Encrypt {
   
   private static String g_strstr_2014 = null;
   private static int decode_count = 0;
   
   public Encrypt(){
	   if(g_strstr_2014 == null){		   
		   g_strstr_2014 = "01010011011000010110011001100101010011010110010101101101011000110100100110111001001001011000110110010100110000001101100011000000110111001100010011010000100001";		   
	   }	   
   }
   private String test_strEncode2(String hex) {
       String digital = "0123456789abcdef";
       hex = test_Encode1(hex);
       char[] hex2char = hex.toCharArray();
       byte[] bytes = new byte[hex.length() / 2];
       int temp;
       for (int i = 0; i < bytes.length; i++) {
           temp = digital.indexOf(hex2char[2 * i]) * 16;
           temp += digital.indexOf(hex2char[2 * i + 1]);
           bytes[i] = (byte) (temp & 0xff);
       }
       return new String(bytes);
   }   
   private String test_Encode1(String bString)  
   {  
       if (bString == null || bString.equals("") || bString.length() % 8 != 0)  
           return null;  
       StringBuffer tmp = new StringBuffer();  
       int iTmp = 0;  
       for (int i = 0; i < bString.length(); i += 4)  
       {  
           iTmp = 0;  
           for (int j = 0; j < 4; j++)  
           {  
               iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);  
           }  
           tmp.append(Integer.toHexString(iTmp));  
       }  
       return tmp.toString();  
   }   
  
   /*
    *  加密指定的字符串
    * */
   public String getEncryptStr(String content){
	   return new Algorithm().GetAESCode(content,test_strEncode2(g_strstr_2014.toLowerCase()));
   }
   
   /*
    *  解密指定的字符串
    * */   
   public String getDecryptStr(String content){
	   return new Algorithm().RevertAESCode(content,test_strEncode2(g_strstr_2014.toLowerCase()));
   }
   public static String genMacSHAKey(String timestamp,String token,String appkey){
	   String signatureCal = null;
       try{
    	
	       Mac sha256HMAC = Mac.getInstance("HmacSHA256");
	       SecretKeySpec secretKey = new SecretKeySpec(appkey.getBytes(),"HmacSHA256");
		   sha256HMAC.init(secretKey);
		   StringBuffer buf = new StringBuffer();
		   buf.append(timestamp).append(token);
	
		   signatureCal = new String(AESEncrypt.toHex(sha256HMAC.doFinal(buf.toString().getBytes())));
       }catch(Exception ex){
    	   
       }
	   return signatureCal; 
   }
}
