/**
 * Created the sn.mini.jsp.compiler.tag.IfElseTag.java
 * @created 2017年12月12日 下午12:46:36
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.tag;

import sn.mini.jsp.compiler.JspWriter;
import sn.mini.util.lang.StringUtil;

/**
 * sn.mini.jsp.compiler.tag.IfElseTag.java
 * @author XChao
 */
public class IfElseTag extends JspTag {

	@Override
	public void doTag(JspWriter out) throws Exception {

		int start = 0, middle = 0, end = 0, bool = 0;
		for (JspTag tag : this.children()) {
			if(tag instanceof IfTag) {
				if(middle == 1 || end == 1) {
					throw new RuntimeException("The first child of if-else must be if.");
				}
				if(((IfTag) tag).doTag()) {
					tag.writerBody(out);
					bool = 1;
				}
				start = 1;

			} else if(tag instanceof ElseIfTag) {
				if(start == 0 || end == 1) {
					throw new RuntimeException("Elseif must be before else, after if.");
				}
				if(((ElseIfTag) tag).doTag() && bool == 0) {
					tag.writerBody(out);
					bool = 1;
				}
				middle = 1;
			} else if(tag instanceof ElseTag) {
				if(start == 0) {
					throw new RuntimeException("Else must be the last child.");
				}
				if(bool == 0) {
					((ElseTag) tag).doElseTag(out);
				}
				end = 1;
			} else {
				if(!(tag instanceof TextTag && (tag.text() == null || //
					StringUtil.isBlank(tag.text().toString())))) {
					throw new RuntimeException("The children of if-else must be if, elseif and else.");
				}
			}
		}
	}
}
