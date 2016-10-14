package com.pai.base.core.util;

import java.util.UUID;

public class UUIDCompressUtil {
	public static String compressedUUID(UUID uuid) {
		byte[] byUuid = new byte[16];
		long least = uuid.getLeastSignificantBits();
		long most = uuid.getMostSignificantBits();
		long2bytes(most, byUuid, 0);
		long2bytes(least, byUuid, 8);
		String compressUUID = Base58.encode(byUuid);
		return compressUUID;
	}
	protected static void long2bytes(long value, byte[] bytes, int offset) {
		for (int i = 7; i > -1; i--) {
			bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
		}
	}
	
	public static void main(String[] args){
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		System.out.println(UUIDCompressUtil.compressedUUID(uuid));
	}
}
