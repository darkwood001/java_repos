package cn.einino.commons.core.cache;

import cn.einino.commons.core.exception.CacheException;
import cn.einino.commons.core.module.CacheKey;

public interface LocalCache {

	void initCache();

	<T extends Object> T get(CacheKey key) throws CacheException;

	void put(CacheKey key, Object value) throws CacheException;

	void cleanUp();
}
