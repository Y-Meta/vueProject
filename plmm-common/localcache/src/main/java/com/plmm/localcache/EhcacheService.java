package com.plmm.localcache;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

/**
 * ehcache缓存服务类
 *
 */
@Service
public class EhcacheService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	@Value("${cacheName:commonCache}")
	private String cacheName;
	
	public void flush() {
		Cache ehCache = cacheManager.getCache(cacheName);
		if (ehCache != null) {
			ehCache.clear();
			logger.warn("cache:" + cacheName + " is cleared");
		}
	}
	
	public void expire() {
		logger.warn("cache:" + cacheName + " not support expire!");
	}

	public void clearAll() {
		CacheManager ehcacheManager = cacheManager.getCacheManager();
		if (ehcacheManager != null) {
			ehcacheManager.clearAll();
			logger.warn("All caches is cleared");
		}
	}

	public void remove() {
		Cache cache = cacheManager.getCache(cacheName);
		net.sf.ehcache.Cache ehcache = getNativeCache(cache);
		if (ehcache != null) {
			ehcache.removeAll();
			logger.warn("cache:" + cacheName + " is removed");
		}
	}

	private net.sf.ehcache.Cache getNativeCache(Cache cache) {
		if (cache != null) {
			net.sf.ehcache.Cache ehcache = (net.sf.ehcache.Cache) cache
					.getNativeCache();
			return ehcache;
		}
		return null;
	}

	public void removeAll() {
		CacheManager ehcacheManager = cacheManager.getCacheManager();
		if (ehcacheManager != null) {
			ehcacheManager.removalAll();
			logger.warn("All caches is removed");
		}
	}

	public Serializable get(Serializable key) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			ValueWrapper e = cache.get(key);
			if (e != null) {
				return (Serializable) e.get();
			} else {
				logger.warn("cache:" + cacheName + "'s " + key + " is not gotten");
				return null;
			}
		} else {
			return null;
		}
	}

	public void put(Serializable key, Serializable element) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			cache.put(key, element);
			logger.warn("cache:" + cacheName + "'s " + key + " is put");
		}
	}

	public void remove(Serializable key) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			cache.evict(key);
			logger.warn("cache:" + cacheName + "'s " + key + " is removed");
		}
	}

	public List getCacheKeys() {
		Cache cache = cacheManager.getCache(cacheName);
		Ehcache ehcache = getNativeCache(cache);

		return ehcache.getKeys();
	}

	public List getCacheKeysWithExpiryCheck() {
		Cache cache = cacheManager.getCache(cacheName);
		Ehcache ehcache = getNativeCache(cache);

		return ehcache.getKeysWithExpiryCheck();
	}

	public Object get(Object key) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			ValueWrapper e = cache.get(key);
			if (e != null) {
				return e.get();
			} else {
				logger.warn("cache:" + cacheName + "'s " + key + " is not gotten");
				return null;
			}
		} else {
			return null;
		}
	}

	public void put(Object key, Object element) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			cache.put(key, element);
			logger.warn("cache:" + cacheName + "'s " + key + " is put");
		}
	}

	public void remove(Object key) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			cache.evict(key);
			logger.warn("cache:" + cacheName + "'s " + key + " is removed");
		}
	}
	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public void setCacheManager(EhCacheCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
}
