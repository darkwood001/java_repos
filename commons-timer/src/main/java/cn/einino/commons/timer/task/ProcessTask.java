package cn.einino.commons.timer.task;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.einino.commons.timer.exception.InterceptorException;
import cn.einino.commons.timer.interceptor.DefaultInterceptorChain;
import cn.einino.commons.timer.interceptor.InterceptorChain;
import cn.einino.commons.timer.model.Context;
import cn.einino.commons.timer.model.InterceptorConf;

public class ProcessTask implements Runnable, Delayed {

	protected final Logger Log = LoggerFactory.getLogger(getClass());
	private final String contextName;
	private final List<InterceptorConf> interceptorConfs;
	private final BlockingQueue<ProcessTask> queue;
	private Context context;
	private long nextTime;
	private int interval = 60;
	private InterceptorChain chain;

	public ProcessTask(String contextName,
			List<InterceptorConf> interceptorConfs,
			BlockingQueue<ProcessTask> queue) {
		this.contextName = contextName;
		this.interceptorConfs = interceptorConfs;
		this.queue = queue;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public final String getContextName() {
		return contextName;
	}

	public void initTask() {
		context = new Context();
		context.setContextName(contextName);
		chain = new DefaultInterceptorChain();
		for (InterceptorConf conf : interceptorConfs) {
			chain.addInterceptor(conf.getName(), conf.getInterceptor());
		}
	}

	public int compareTo(Delayed o) {
		ProcessTask t = (ProcessTask) o;
		return nextTime > t.nextTime ? 1 : (nextTime < t.nextTime ? -1 : 0);
	}

	public long getDelay(TimeUnit unit) {
		return nextTime - System.currentTimeMillis();
	}

	@Override
	public void run() {
		Thread.currentThread().setName(contextName);
		Log.debug(new StringBuilder("Context:[").append(contextName)
				.append("] task start run ...").toString());
		context.setException(null);
		try {
			chain.process(context);
			if (context.getException() != null) {
				Log.info(new StringBuilder("Context:[").append(contextName)
						.append("] exception:[")
						.append(context.getException().getMessage())
						.append("]").toString());
			} else {
				Log.info(new StringBuilder("Context:[").append(contextName)
						.append("] finish").toString());
			}
		} catch (InterceptorException ex) {
			Log.error(new StringBuilder("Context:[").append(contextName)
					.append("] run error").toString(), ex);
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, interval);
		nextTime = cal.getTimeInMillis();
		while (!queue.offer(this)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Log.error(new StringBuilder("Context:[").append(contextName)
						.append("] sleep interrupted").toString(), ex);
			}
		}
		Log.debug(new StringBuilder("Context:[").append(contextName)
				.append("] task finish run").toString());
	}
}
