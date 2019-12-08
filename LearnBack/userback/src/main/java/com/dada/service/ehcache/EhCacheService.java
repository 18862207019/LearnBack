package com.dada.service.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EhCacheService {

    @Resource
    private EhCacheCacheManager ehCacheCacheManager;

    /**
     *     添加本地缓存 (相同的key 会直接覆盖)
     */
    public void put(String cacheName, String key, Object value) {
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

}
