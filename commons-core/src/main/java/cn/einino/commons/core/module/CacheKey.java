package cn.einino.commons.core.module;

import java.util.HashMap;
import java.util.Map;

import cn.einino.commons.core.cache.listener.CacheListener;

public class CacheKey {

	private final String key;
	private final CacheListener listener;
	private final Map<String, Object> params = new HashMap<>();

	public CacheKey(String key, CacheListener listener) {
		this.key = key;
		this.listener = listener;
	}

	public final Map<String, Object> getParams() {
		return params;
	}

	@SuppressWarnings("unchecked")
	public final <T extends Object> T getParam(String key) {
		Object obj = params.get(key);
		if (obj == null) {
			return null;
		}
		return (T) obj;
	}

	public final String getKey() {
		return key;
	}

	public final CacheListener getListener() {
		return listener;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CacheKey [key=" + key + "]";
	}
}
