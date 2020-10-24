package com.plmm.localcache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.plmm.localcache.service.ICacheContextService;

import redis.clients.jedis.JedisCluster;

public class RedisContextService implements ICacheContextService{
		
	@Autowired
	private JedisCluster cache;

	@Override
	public String get(String key) {
		return cache.get(key);
	}

	@Override
	public void del(String key) {
		cache.del(key);
	}

	@Override
	public void set(String key, String value) {
		cache.set(key, value);
	}

	@Override
	public void expire(String key, int seconds) {
		cache.expire(key, seconds);
	}
	
	public JedisCluster getCache() {
		return cache;
	}

	public void setCache(JedisCluster cache) {
		this.cache = cache;
	}

	@Override
	public byte[] get(byte[] key) {
		return cache.get(key);
	}

	@Override
	public void set(byte[] key, byte[] value) {
		cache.set(key, value);
	}

	@Override
	public void expire(byte[] key, int seconds) {
		cache.expire(key, seconds);
	}

	@Override
	public void del(byte[] key) {
		cache.del(key);
	}
}
