/**
 * Created the sn.mini.jsp.reader.Mark.java
 * @created 2017年11月28日 下午2:15:27
 * @version 1.0.0
 */
package sn.mini.jsp.compiler;

/**
 * sn.mini.jsp.reader.Mark.java
 * @author XChao
 */
public final class Mark {
	protected int cursor = 0, line = 1, column = 1;

	protected Mark() {
	}

	protected Mark(Mark mark) {
		this(mark.cursor, mark.line, mark.column);
	}

	protected Mark(int cursor, int line, int column) {
		this.update(cursor, line, column);
	}

	protected void update(Mark mark) {
		this.update(mark.cursor, mark.line, mark.column);
	}

	protected void update(int cursor, int line, int column) {
		this.cursor = cursor;
		this.column = column;
		this.line = line;
	}

	public int cursor() {
		return this.cursor;
	}

	public int line() {
		return this.line;
	}

	public int column() {
		return this.column;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Mark && ((Mark) obj).cursor == cursor && ((Mark) obj).line == line
			&& ((Mark) obj).column == column;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d, %d)", cursor, line, column);
	}

	public String toError(String fileName) {
		return String.format("%s(%d, %d)", fileName, line, column);
	}
}
