package com.plmm.cryptography;

public interface Cryptography {
	
	/**
	 * 加密
	 * @param srcCrypt 原文
	 * @return 密文
	 */
	String encrypt(String srcCrypt);
	/**
	 * 解密
	 * @param encrypt 密文
	 * @return 解密后的原文
	 */
	String decrypt(String encrypt);
}
