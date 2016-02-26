package cn.einino.commons.timer.interceptor;

import cn.einino.commons.timer.exception.InterceptorException;
import cn.einino.commons.timer.model.Context;

public interface InterceptorChain {

	void process(Context context) throws InterceptorException;

	void addInterceptor(String name, Interceptor interceptor);
}
