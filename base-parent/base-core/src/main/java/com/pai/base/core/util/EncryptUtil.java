package com.pai.base.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import com.pai.base.core.encrypt.Base64;

/**
 * 对象功能:加密算法工具类
 * 开发公司:pai.COM
 * 开发人员:DaFuHao
 * 创建时间:2014-12-10 17:21:14
 */
public class EncryptUtil {  
	
	public static final String key="wieuwdmsdugasamsoiasafexsds";
	
	/**
	 * md5加密  
	 * @param inputText 待加密明文
	 * @return
	 */
    public static String md5(String inputText) {  
        return encrypt(inputText, "md5");  
    }  
  
    /**
     * sha加密  
     * @param inputText 待加密明文
     * @return
     */
    public static String sha(String inputText) {  
        return encrypt(inputText, "sha-1");  
    }  
  
    /**
     * EcStore的md5加密方式，用于对EcStore进行数据移植等。
     * @param pw
     * @param ccount
     * @param timestamp
     * @return
     */
    public static String md5EcStore(String pw,String ccount,String timestamp){
    	String md5_1 = md5(pw);
    	String encString = md5(md5_1 + ccount + timestamp);
    	encString = "s" + encString.substring(0, 31);
        //System.out.println(encString);
    	return encString;
    }
    
    /** 
     * md5或者sha-1加密 
     *  
     * @param inputText 
     *            要加密的内容 
     * @param algorithmName 
     *            加密算法名称：md5或者sha-1，不区分大小写 
     * @return 
     */  
    private static String encrypt(String inputText, String algorithmName) {  
        if (inputText == null || "".equals(inputText.trim())) {  
            throw new IllegalArgumentException("请输入要加密的内容");  
        }  
        if (algorithmName == null || "".equals(algorithmName.trim())) {  
            algorithmName = "md5";  
        }  
        String encryptText = null;  
        try {  
            MessageDigest m = MessageDigest.getInstance(algorithmName);  
            m.update(inputText.getBytes("UTF8"));  
            byte s[] = m.digest();  
            // m.digest(inputText.getBytes("UTF8"));  
            return hex(s);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return encryptText;  
    }  
  
    // 返回十六进制字符串  
    private static String hex(byte[] arr) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < arr.length; ++i) {  
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,  
                    3));  
        }  
        return sb.toString();  
    }  
    
    /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        if(System.getProperties().getProperty("os.name").equals("Linux"))
        {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            kgen.init(128,secureRandom);
        }else{
            kgen.init(128,new SecureRandom(key.getBytes()));
        }
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
          
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
     
    /** 
     * base 64 encode 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    public static String base64Encode(byte[] bytes){  
        return new Base64().encode(bytes);  
    }  
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content) throws Exception {  
        return base64Encode(aesEncryptToBytes(content));  
    }
    
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes) throws Exception {  
    	KeyGenerator kgen = KeyGenerator.getInstance("AES");
           
       if(System.getProperties().getProperty("os.name").equals("Linux"))
       {
           SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
           secureRandom.setSeed(key.getBytes());
           kgen.init(128,secureRandom);
       }else{
           kgen.init(128,new SecureRandom(key.getBytes()));
       }
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
          
        return new String(decryptBytes);  
    }  
      
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr) throws Exception {  
        return encryptStr==null?null : aesDecryptByBytes(base64Decode(encryptStr));  
    }  
    /** 
     * base 64 decode 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
    public static byte[] base64Decode(String base64Code) throws Exception{  
        return  base64Code==null ? null : new Base64().decode(base64Code);  
    }  
    public static void main(String[] args) throws Exception {
    	String memberCooike="480000460000191:490000109711024";//480000460000139:490000109711024
    	String s=aesEncrypt(memberCooike);
    	System.out.println(s);
		System.out.println(aesDecrypt(s));
	}
}
