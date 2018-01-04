/**
 * Created the sn.mini.web.model.AbstrctModelData.java
 * @created 2017年10月30日 下午5:36:54
 * @version 1.0.0
 */
package sn.mini.web.model;

/**
 * sn.mini.web.model.AbstrctModelData.java
 * @author XChao
 */
public abstract class AbstrctModelData<T> implements IModelData<T> {
	private int error = 0;
	private String message = "";
	private T data;

	@Override
	public int getError() {
		return this.error;
	}

	@Override
	public void setError(int error) {
		this.error = error;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public T getData() {
		return this.data;
	}

	@Override
	public void setDate(T data) {
		this.data = data;
	}
}
