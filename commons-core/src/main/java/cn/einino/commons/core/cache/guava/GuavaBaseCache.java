package cn.einino.commons.core.cache.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

import cn.einino.commons.core.cache.LocalCache;
import cn.einino.commons.core.exception.CacheException;
import cn.einino.commons.core.module.CacheKey;

public class GuavaBaseCache implements LocalCache {

	public static final int CACHE_TYPE_ACCESS = 1;
	public static final int CACHE_TYPE_WRITE = 2;
	protected final Logger Log = LoggerFactory.getLogger(getClass());
	protected LoadingCache<CacheKey, Object> cache;
	protected final long expireTime;
	protected final long cacheSize;
	protected final int capacity;
	protected final long refreshTime;
	protected final int cacheType;
	protected Executor executor;
	protected final RemovalListener<CacheKey, Object> removalListener;
	protected final CacheLoader<CacheKey, Object> loader = new CacheLoader<CacheKey, Object>() {

		@Override
		public Object load(CacheKey key) throws Exception {
			if (key.getListener() == null) {
				throw new Exception("Cache listener is null");
			}
			Object value = key.getListener().onLoad(key);
			if (value == null) {
				throw new Exception("Cache listener return value is null");
			}
			return value;
		}

		@Override
		public ListenableFuture<Object> reload(final CacheKey key, final Object oldValue) throws Exception {
			if (key.getListener() == null) {
				throw new Exception("Cache listener is null");
			}
			if (key.getListener().needRefresh(key, oldValue)) {
				Log.info(new StringBuilder("Refresh cache key:[").append(key.getKey()).append("] data").toString());
				Callable<Object> callable = new Callable<Object>() {

					@Override
					public Object call() throws Exception {
						Object value = key.getListener().onReload(key, oldValue);
						if (value == null) {
							throw new Exception("Cache listener return value is null");
						}
						return value;
					}
				};
				ListenableFutureTask<Object> task = ListenableFutureTask.create(callable);
				if (executor != null) {
					executor.execute(task);
				}
				return task;
			}
			return Futures.immediateFuture(oldValue);
		}
	};

	public GuavaBaseCache(long expireTime, long cacheSize, int capacity, long refreshTime, int cacheType,
			RemovalListener<CacheKey, Object> removalListener) {
		this.expireTime = expireTime;
		this.cacheSize = cacheSize;
		this.capacity = capacity;
		this.refreshTime = refreshTime;
		this.cacheType = cacheType;
		this.removalListener = removalListener;
	}

	public GuavaBaseCache(long expireTime, long cacheSize, int capacity, int cacheType) {
		this(expireTime, cacheSize, capacity, 0, cacheType, null);
	}

	public GuavaBaseCache(long expireTime, long cacheSize, int cacheType) {
		this(expireTime, cacheSize, 0, 0, cacheType, null);
	}

	public GuavaBaseCache(int cacheType) {
		this(3600, 100, cacheType);
	}

	@Override
	public void initCache() {
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
		switch (cacheType) {
		case CACHE_TYPE_ACCESS:
			cacheBuilder.expireAfterAccess(expireTime, TimeUnit.SECONDS);
			break;
		default:
			cacheBuilder.expireAfterWrite(expireTime, TimeUnit.SECONDS);
			break;
		}
		cacheBuilder.maximumSize(cacheSize);
		if (capacity > 0) {
			cacheBuilder.initialCapacity(capacity);
		}
		if (refreshTime > 0) {
			cacheBuilder.refreshAfterWrite(refreshTime, TimeUnit.SECONDS);
		}
		if (removalListener != null) {
			cacheBuilder.removalListener(removalListener);
		}
		cache = cacheBuilder.build(loader);
	}

	@Override
	public <T extends Object> T get(CacheKey key) throws CacheException {
		try {
			@SuppressWarnings("unchecked")
			T value = (T) cache.get(key);
			return value;
		} catch (ExecutionException ex) {
			String emsg = new StringBuilder("Local cache get key:[").append(key).append("] error").toString();
			throw new CacheException(emsg, ex);
		}
	}

	@Override
	public void put(CacheKey key, Object value) throws CacheException {
		cache.put(key, value);
	}

	@Override
	public void cleanUp() {
		cache.cleanUp();
	}

	public final void setExecutor(Executor executor) {
		this.executor = executor;
	}

}
