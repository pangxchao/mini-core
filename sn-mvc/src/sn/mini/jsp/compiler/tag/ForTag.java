/**
 * Created the sn.mini.jsp.compiler.tag.ForTag.java
 * @created 2017年12月1日 下午12:12:24
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.tag;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

import sn.mini.jsp.compiler.JspWriter;
import sn.mini.util.json.JSON;
import sn.mini.util.json.JSONArray;
import sn.mini.util.lang.StringUtil;
import sn.mini.util.lang.TypeUtil;

/**
 * sn.mini.jsp.compiler.tag.ForTag.java
 * @author XChao
 */
public final class ForTag extends JspTag {

	private String var = "var", index = "index";
	private int start = 0, end = 0, step = 1;
	private Object items = null;

	public void setVar(String var) {
		this.var = var;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public void doTag(JspWriter out) throws Exception {
		if(this.items == null) {
			return;
		}
		if(this.items instanceof Collection) {
			if(start == 0 && end == 0 && (step == 0 || step == 1)) {
				int i = 0;
				for (Object item : (Collection<?>) this.items) {
					this.context().put(this.var, item);
					this.context().put(this.index, i);
					this.writerBody(out);
					i++;
				}
			} else if(this.items instanceof List) {
				List<?> items = (List<?>) this.items;
				for (int i = start; i < items.size() && i < end; i += Math.abs(step)) {
					Object item = items.get(i);
					this.context().put(this.var, item);
					this.context().put(this.index, i);
					this.writerBody(out);
				}
			} else {
				int i = 0;
				for (Object item : (Collection<?>) items) {
					if(i >= this.start && i < this.end && i % this.step == 0) {
						this.context().put(this.var, item);
						this.context().put(this.index, i);
						this.writerBody(out);
					}
					i++;
				}
			}
		} else if(this.items instanceof Iterable) {
			if(start == 0 && end == 0 && (step == 0 || step == 1)) {
				int i = 0;
				for (Object item : (Collection<?>) this.items) {
					this.context().put(this.var, item);
					this.context().put(this.index, i);
					this.writerBody(out);
					i++;
				}
			} else {
				int i = 0;
				for (Object item : (Collection<?>) this.items) {
					if(i >= this.start && i < this.end && i % this.step == 0) {
						this.context().put(this.var, item);
						this.context().put(this.index, i);
						this.writerBody(out);
					}
					i++;
				}
			}
		} else if(this.items.getClass().isArray()) {
			int length = Array.getLength(this.items);
			for (int i = start; i < length && i < end; i += Math.abs(step)) {
				Object item = Array.get(items, i);
				this.context().put(this.var, item);
				this.context().put(this.index, i);
				this.writerBody(out);
			}
		} else if(this.items instanceof String) {
			String items = (String) this.items;
			String reg = "\\[((-)?\\d+)\\.\\.((-)?\\d+)\\]";
			if(items.matches(reg)) {
				int[] values = new int[] { 0, 0 };
				StringUtil.replaceAll((String) this.items, reg, (m) -> {
					values[0] = TypeUtil.castToIntValue(m.group(1));
					values[1] = TypeUtil.castToIntValue(m.group(2));
					return "";
				});
				for (int i = values[0]; i < values[1]; i++) {
					this.context().put(this.var, values[0]);
					this.context().put(this.index, i);
					this.writerBody(out);
				}
			} else {
				JSONArray array = JSON.parseArray(items);
				int i = 0, length = array.size();
				for (i = start; i < length && i < end; i += Math.abs(step)) {
					Object item = Array.get(items, i);
					this.context().put(this.var, item);
					this.context().put(this.index, i);
					this.writerBody(out);
				}
			}
		}
	}
}
