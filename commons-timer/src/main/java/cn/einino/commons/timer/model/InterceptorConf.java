package cn.einino.commons.timer.model;

import cn.einino.commons.timer.interceptor.Interceptor;

public class InterceptorConf {

	private final String name;
	private final Interceptor interceptor;

	public InterceptorConf(String name, Interceptor interceptor) {
		this.name = name;
		this.interceptor = interceptor;
	}

	public String getName() {
		return name;
	}

	public Interceptor getInterceptor() {
		return interceptor;
	}
}
