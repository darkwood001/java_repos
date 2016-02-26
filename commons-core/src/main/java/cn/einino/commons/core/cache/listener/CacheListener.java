package cn.einino.commons.core.cache.listener;

import cn.einino.commons.core.exception.CacheException;
import cn.einino.commons.core.module.CacheKey;

public interface CacheListener {

	Object onLoad(CacheKey key) throws CacheException;

	Object onReload(CacheKey key, Object oldValue) throws CacheException;

	boolean needRefresh(CacheKey key, Object oldValue) throws CacheException;
}
