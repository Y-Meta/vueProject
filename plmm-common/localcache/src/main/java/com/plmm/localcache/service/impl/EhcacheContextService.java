package com.plmm.localcache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.plmm.utils.HexUtil;
import com.plmm.localcache.EhcacheService;
import com.plmm.localcache.service.ICacheContextService;

public class EhcacheContextService implements ICacheContextService{
		
	@Autowired
	private EhcacheService cache;

	@Override
	public String get(String key) {
		return (String)cache.get(key);
	}

	@Override
	public void del(String key) {
		cache.remove(key);
		return;
	}
	
	@Override
	public void set(String key, String value) {
		cache.put(key, value);
	}

	@Override
	public void expire(String key, int seconds) {
		cache.expire();
	}

	@Override
	public byte[] get(byte[] key) {
		if(key==null)
			return null;
		String keyVal = HexUtil.toHexString(key);
		if(keyVal==null)
			return null;
		String val = (String)cache.get(keyVal);
		if(val!=null)
			return HexUtil.toByteArray((String)cache.get(keyVal));
		else
			return null;
	}
	
	public EhcacheService getCache() {
		return cache;
	}

	public void setCache(EhcacheService cache) {
		this.cache = cache;
	}

	@Override
	public void set(byte[] key, byte[] value) {
		String keyVal = HexUtil.toHexString(key);
		String valueVal = HexUtil.toHexString(value);
		cache.put(keyVal, valueVal);
	}

	@Override
	public void expire(byte[] key, int maxInactiveInterval) {
		cache.expire();
	}

	@Override
	public void del(byte[] key) {
		cache.remove(key);
	}
}
