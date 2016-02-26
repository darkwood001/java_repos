package cn.einino.commons.timer.task;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlTask extends Thread {

	protected final Logger Log = LoggerFactory.getLogger(getClass());
	private final BlockingQueue<ProcessTask> queue;
	private final Executor executor;
	private final List<ProcessTask> processTasks;

	public ControlTask(BlockingQueue<ProcessTask> queue, Executor executor,
			List<ProcessTask> processTasks) {
		this.queue = queue;
		this.executor = executor;
		this.processTasks = processTasks;
		setName("ControlTask");
	}

	@Override
	public void run() {
		initTask();
		ProcessTask processTask;
		while (!interrupted()) {
			try {
				processTask = queue.take();
				Log.info(new StringBuilder("Process task:[")
						.append(processTask.getContextName())
						.append("] running...").toString());
				executor.execute(processTask);
			} catch (InterruptedException ex) {
				Log.error("Control Task take task from queue interrupted", ex);
			}
		}
	}

	private void initTask() {
		for (ProcessTask task : processTasks) {
			Log.info(new StringBuilder("Process task:[")
					.append(task.getContextName()).append("] running...")
					.toString());
			executor.execute(task);
		}
	}
}
