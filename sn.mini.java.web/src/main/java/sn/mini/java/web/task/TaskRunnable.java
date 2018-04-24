/**
 * Created the sn.mini.java.web.task.TaskRunnable.java
 * @created 2017年11月1日 上午11:06:09
 * @version 1.0.0
 */
package sn.mini.java.web.task;

import java.util.List;

import sn.mini.java.util.logger.Log;
import sn.mini.java.web.SNInitializer;
import sn.mini.java.web.task.parser.Parser;

/**
 * sn.mini.java.web.task.TaskRunnable.java
 * @author XChao
 */
public final class TaskRunnable implements Runnable {

	private long millis = 0;
	private final ITask task;
	private final List<Parser> parsers;
	private boolean running = false;

	public TaskRunnable(Class<? extends ITask> clazz, String parser) {
		this.millis = System.currentTimeMillis();
		this.task = SNInitializer.getTask(clazz);
		this.parsers = Parser.build(parser);
		this.task.init(); // 初始化任务执行器
	}

	private final boolean match() {
		millis += 60000;
		return this.running == false && this.parsers.stream().anyMatch(v -> v.match(millis));
	}

	@Override
	public void run() {
		if (this.match()) {
			try {
				this.running = true;
				this.task.execute();
			} catch (Throwable e) {
				Log.error("Execute task " + this.getClass().getName() + " error. ", e);
			} finally {
				this.running = false;
			}
		}
	}

	public void destroy() {
		this.task.destroy();
	}
}
