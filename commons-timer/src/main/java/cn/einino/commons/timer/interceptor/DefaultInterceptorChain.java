package cn.einino.commons.timer.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.einino.commons.timer.exception.InterceptorException;
import cn.einino.commons.timer.model.Context;

public class DefaultInterceptorChain implements InterceptorChain {

	private final Logger Log = LoggerFactory.getLogger(getClass());
	protected Entry head;
	protected Entry tail;

	public void process(Context context) throws InterceptorException {
		if (head == null) {
			throw new InterceptorException(new StringBuilder("Context:[")
					.append(context.getContextName()).append("] chain is null")
					.toString());
		}
		getLog().debug(
				new StringBuilder("Context:[").append(context.getContextName())
						.append("] start process").toString());
		Entry entry = head;
		try {
			while (entry != null) {
				if (!entry.interceptor.handle(context) || entry.next == null) {
					break;
				}
				entry = entry.next;
			}
		} catch (Exception ex) {
			getLog().error(
					new StringBuilder("Context:[")
							.append(context.getContextName())
							.append("] interceptor:[").append(entry.name)
							.append("] preHandle error").toString(), ex);
			context.setException(ex);
		}
		try {
			do {
				entry.interceptor.postHanle(context);
				entry = entry.prev;
			} while (entry != null);
		} catch (Exception ex) {
			getLog().error(
					new StringBuilder("Context:[")
							.append(context.getContextName())
							.append("] interceptor:[").append(entry.name)
							.append("] postHanle error").toString(), ex);
			context.setException(ex);
		}
		getLog().debug(
				new StringBuilder("Context:[").append(context.getContextName())
						.append("] finish process").toString());
	}

	public void addInterceptor(String name, Interceptor interceptor) {
		Entry entry = new Entry();
		entry.name = name;
		entry.interceptor = interceptor;
		if (head == null) {
			head = entry;
			tail = entry;
		} else {
			tail.next = entry;
			entry.prev = tail;
			tail = entry;
		}
	}

	public Logger getLog() {
		return Log;
	}

	class Entry {

		public String name;
		public Interceptor interceptor;
		public Entry prev;
		public Entry next;

	}
}
