package cn.einino.commons.timer.interceptor;

import cn.einino.commons.timer.model.Context;

public interface Interceptor {

	boolean handle(Context context) throws Exception;

	void postHanle(Context context) throws Exception;
}
