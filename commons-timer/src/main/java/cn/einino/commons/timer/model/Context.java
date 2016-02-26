package cn.einino.commons.timer.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.einino.commons.timer.interceptor.InterceptorChain;

public class Context {

	private String contextName;
	private InterceptorChain chain;
	private final Map<String, Object> configMap = new HashMap<String, Object>();
	private final Map<String, Object> paramMap = new HashMap<String, Object>();
	private Throwable exception;

	public Context() {
	}

	public InterceptorChain getChain() {
		return chain;
	}

	public String getContextName() {
		return contextName;
	}

	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	public void setChain(InterceptorChain chain) {
		this.chain = chain;
	}

	public void addParameter(String key, Object value) {
		paramMap.put(key, value);
	}

	public void removeParameter(String key) {
		paramMap.remove(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T getConfig(String key) {
		Object obj = configMap.get(key);
		return obj == null ? null : (T) obj;
	}

	public Map<String, Object> getConfigMap() {
		return configMap;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T getParameter(String key) {
		Object obj = paramMap.get(key);
		return (obj == null) ? null : (T) obj;
	}

	public Set<String> getKeys() {
		Set<String> keys = paramMap.keySet();
		return keys;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
