package com.pai.base.core.encrypt;

public class FieldEncrypt {
	
	private static final char[] legalChars = "8HBF5IKL+MOW3PQR0TV22Y6abcZdefW3ghi NEjklmAn22oGpqrSsXtuDJvwxy7z1U4C9/ FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".toCharArray();  
	
	public String encode(String str){
		String base64_str = new Base64().encode(str.getBytes());
		char array[] = base64_str.toCharArray();
		byte pos[] = new byte[array.length];
		
		for(int i=0;i<array.length;i++){
			for(int j=0;j<legalChars.length;j++){
				 if(array[i] == legalChars[j]){
				    pos[i] = (byte)(j^8);
					break;
				 }
			}
		}
			
		for(int i=0;i<pos.length;i++){
			if(pos[i] < 33)
				pos[i] = (byte)((int)pos[i]+78);			    
		}		
		return new String(pos);		
	}
	
	public String decode(String str){
		byte pos[] = str.getBytes();
		
		for(int i=0;i<pos.length;i++){
			if(pos[i] > 77)
				pos[i] = (byte)((int)pos[i]-78);	
			pos[i] = (byte)(pos[i]^8);
		}

		char array[] = new char[pos.length];
		for(int i=0;i<array.length;i++){
			array[i] = legalChars[pos[i]];		
		}
		return new String(new Base64().decode(new String(array)));	
	}	
}
