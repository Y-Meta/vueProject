package com.plmm.cryptography;

import java.util.Base64;

public class Base64Cryptography implements Cryptography {

	@Override
	public String encrypt(String srcCrypt) {
		return Base64.getEncoder().encodeToString(srcCrypt.getBytes());
	}

	@Override
	public String decrypt(String encrypt) {
		return new String(Base64.getDecoder().decode(encrypt.getBytes()));
	}
	
//	public static void main(String[] args) {
//		System.out.println(new Base64Cryptography().encrypt("123qwe"));
//	}
}
