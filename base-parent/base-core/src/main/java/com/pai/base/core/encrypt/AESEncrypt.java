package com.pai.base.core.encrypt;

import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.security.SecureRandom;  

/**
*  AES加密和解密算法主类
*  @author Suoron.Zhu
*
*/

public class AESEncrypt {
	
    public static final String TAG = "AESEncrypt";
    public static final String VIPARA = "0102030405060708";  
    public static final String bm = "utf-8";      
    public static byte[] token1 = {94,-38,60,-22,-61,40,-75,-61,-107,-80,27,-8,-91,79,84,-41};    

    public static String encrypt(String dataPassword, String cleartext)  
            throws Exception {  
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
        SecretKeySpec key = null;
        if(dataPassword == null)
           key = new SecretKeySpec(token1, "AES");
        else
           key = new SecretKeySpec(getRawKey(dataPassword.getBytes()), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);  
        byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));  
      
        return toHex(Base64.encode(encryptedData));
    }  
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        if(System.getProperties().getProperty("os.name").equals("Linux"))
        {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed);
            kgen.init(128,secureRandom);
        }else{
            kgen.init(128,new SecureRandom(seed));
        }
        
        SecretKey sKey = kgen.generateKey();
        byte[] raw = sKey.getEncoded();     
        return raw;
    }      
    public static String decrypt(String dataPassword, String encrypted)  
            throws Exception {  
        byte[] byteMi = Base64.decode(fromHex(encrypted)); 
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());          
        SecretKeySpec key = null;
        if(dataPassword == null)
           key = new SecretKeySpec(token1, "AES");
        else
           key = new SecretKeySpec(getRawKey(dataPassword.getBytes()), "AES");
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);  
        byte[] decryptedData = cipher.doFinal(byteMi);  
      
        return new String(decryptedData,bm);  
    }  
    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        final String HEX = "0123456789ABCDEF";
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }        
}
