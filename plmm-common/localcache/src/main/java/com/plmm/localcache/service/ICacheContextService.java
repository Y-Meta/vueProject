package com.plmm.localcache.service;

public interface ICacheContextService {

	String get(String key);

	void del(String key);

	public void set(String key, String value);

	public void expire(String key, int seconds);

	byte[] get(byte[] bytes);

	void set(byte[] key, byte[] value);

	void expire(byte[] key, int seconds);

	void del(byte[] key);
	
}
