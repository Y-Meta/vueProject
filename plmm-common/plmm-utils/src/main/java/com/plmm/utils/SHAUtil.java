package com.plmm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {

	public final static String SHA_512 = "SHA-512";
	public final static String SHA_256 = "SHA-256";

	public static String SHA(String strText, String strType) {
		String	strResult = "";
		if (strText != null && strText.length() > 0) {
			MessageDigest messageDigest;
			try {
				messageDigest = MessageDigest.getInstance(strType);
				messageDigest.update(strText.getBytes());
				byte byteBuffer[] = messageDigest.digest();
				
				StringBuffer strHexString = new StringBuffer();
				for (int i = 0; i < byteBuffer.length; i++) { 
					String hex = Integer.toHexString(0xff & byteBuffer[i]); 
					if (hex.length() == 1) {
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				strResult =strHexString.toString();
			} catch (NoSuchAlgorithmException e) {
			}
		}
		return strResult;
	}
}
