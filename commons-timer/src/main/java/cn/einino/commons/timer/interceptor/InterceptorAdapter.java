package cn.einino.commons.timer.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.einino.commons.timer.model.Context;

public abstract class InterceptorAdapter implements Interceptor {

	protected final Logger Log = LoggerFactory.getLogger(getClass());

	@Override
	public void postHanle(Context context) throws Exception {
	}
}
