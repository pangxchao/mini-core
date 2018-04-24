/**
 * Created the sn.mini.java.web.task.ITask.java
 * @created 2017年11月1日 上午10:59:00
 * @version 1.0.0
 */
package sn.mini.java.web.task;

/**
 * sn.mini.java.web.task.ITask.java
 * @author XChao
 */
public interface ITask {
	public abstract void init();

	public abstract void execute();

	public abstract void destroy();
}
