package test.sn.mini.inters; 

import java.util.Map; 
import java.util.HashMap; 
import sn.mini.jsp.JspSession; 
import sn.mini.jsp.compiler.JspWriter; 
import sn.mini.jsp.compiler.tag.JspTag; 
import sn.mini.web.SNInitializer;

public class SN_index2ejsp extends sn.mini.jsp.JspTemplate { 
	private final Map<String, Object> context = new HashMap<>();
	// 
 	private String s = new String("abc"); 
 	// 
 	 
 	public void service(final JspSession session, final JspWriter out)  throws Exception { 
		out.write(""); 
 		out.write("\r\n"); 
 		out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n"); 
 		out.write("<html>\r\n"); 
 		out.write("<head>\r\n"); 
 		out.write("<base href=\""); 
 		out.print(SNInitializer.getWebRootPath()); 
		out.write("\" />\r\n"); 
 		out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"); 
 		out.write("<title>Insert title here</title>\r\n"); 
 		out.write("</head>\r\n"); 
 		out.write("<body>\r\n"); 
 		out.write("	"); 
 		out.write("\r\n"); 
 		out.write("	"); 
 		 
		String name = "b"; 
		session.setAttribute("name", name); 
		System.out.println(s); 
		 
		out.write("\r\n"); 
 		out.write("	"); 
 		 
		String bb = "wq vb kcg "; 
		System.out.print(bb); 
		 
		out.write("\r\n"); 
 		out.write("	Index.jsp\r\n"); 
 		out.write("	<br /> a"); 
 		out.print(this.getELExpression("name", session, context)); 
		out.write("c\r\n"); 
 		out.write("	"); 
 		 
		// <jsp:tag 自定义标签开始 
		JspTag _jsp_tag_ix_1 = new sn.mini.jsp.compiler.tag.IncludeTag(); 
		_jsp_tag_ix_1.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_1.attribute("page", "pages/MyController/1.jsp"); 
		// 调用自定义标签的doTag方法 
		_jsp_tag_ix_1.doTag(out); 
		// <jsp:tag 自定义标签结束 
		 
		out.write("\r\n"); 
 		out.write("	"); 
 		 
		// <jsp:tag 自定义标签开始 
		JspTag _jsp_tag_ix_2 = new sn.mini.jsp.compiler.tag.ForTag(); 
		_jsp_tag_ix_2.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_2.attribute("var", "item"); 
		_jsp_tag_ix_2.attribute("start", "0"); 
		_jsp_tag_ix_2.attribute("index", "i"); 
		_jsp_tag_ix_2.attribute("end", "5"); 
		_jsp_tag_ix_2.attribute("step", "2"); 
		_jsp_tag_ix_2.attribute("items", this.getELExpression("users", session, context)); 
		 
		
		// 文本标签开始  
		StringBuilder _jsp_builder_3 = new StringBuilder(); 
		_jsp_builder_3.append("<div>this is div "); 
 		JspTag _jsp_tag_ix_3 = new sn.mini.jsp.compiler.tag.TextTag(); 
		_jsp_tag_ix_3.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_3.text(_jsp_builder_3.toString()); 
		// 文本标签结束  
		
		// 将子标签加入自定义标签的children 
		_jsp_tag_ix_2.addChild(_jsp_tag_ix_3); 
 		 
		 
		 
		// El 表达式标签开始 
		JspTag _jsp_tag_ix_4 = new sn.mini.jsp.compiler.tag.TextTag(); 
		_jsp_tag_ix_4.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_4.text(this.getELExpression("item.id", session, context)); 
		// El 表达式标签结束 
		 
		// 将子标签加入自定义标签的children 
		_jsp_tag_ix_2.addChild(_jsp_tag_ix_4); 
 		 
		 
		
		// 文本标签开始  
		StringBuilder _jsp_builder_5 = new StringBuilder(); 
		_jsp_builder_5.append("</div>\r\n"); 
 		_jsp_builder_5.append("		<div>\r\n"); 
 		_jsp_builder_5.append("			this is div\r\n"); 
 		_jsp_builder_5.append("			"); 
 		JspTag _jsp_tag_ix_5 = new sn.mini.jsp.compiler.tag.TextTag(); 
		_jsp_tag_ix_5.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_5.text(_jsp_builder_5.toString()); 
		// 文本标签结束  
		
		// 将子标签加入自定义标签的children 
		_jsp_tag_ix_2.addChild(_jsp_tag_ix_5); 
 		 
		 
		
		// <%= %> 输入标签开始 
		JspTag _jsp_tag_ix_6 = new sn.mini.jsp.compiler.tag.TextTag(); 
		_jsp_tag_ix_6.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_6.text(session.getAttribute("name")); 
		// <%= %> 输入标签结束
		
		// 将子标签加入自定义标签的children 
		_jsp_tag_ix_2.addChild(_jsp_tag_ix_6); 
 		 
		 
		
		// 文本标签开始  
		StringBuilder _jsp_builder_7 = new StringBuilder(); 
		_jsp_builder_7.append("</div>\r\n"); 
 		_jsp_builder_7.append("	"); 
 		JspTag _jsp_tag_ix_7 = new sn.mini.jsp.compiler.tag.TextTag(); 
		_jsp_tag_ix_7.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_7.text(_jsp_builder_7.toString()); 
		// 文本标签结束  
		
		// 将子标签加入自定义标签的children 
		_jsp_tag_ix_2.addChild(_jsp_tag_ix_7); 
 		 
		// 调用自定义标签的doTag方法 
		_jsp_tag_ix_2.doTag(out); 
		// <jsp:tag 自定义标签结束 
		 
		out.write("\r\n"); 
 		out.write("	"); 
 		 
		// <jsp:tag 自定义标签开始 
		JspTag _jsp_tag_ix_8 = new sn.mini.jsp.compiler.tag.IfElseTag(); 
		_jsp_tag_ix_8.engine(getEngine()).jspSession(session).context(context);
		 
		 
		// <jsp:tag 自定义标签开始 
		JspTag _jsp_tag_ix_9 = new sn.mini.jsp.compiler.tag.IfTag(); 
		_jsp_tag_ix_9.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_9.attribute("test", s); 
		
		// 文本标签开始  
		StringBuilder _jsp_builder_10 = new StringBuilder(); 
		_jsp_builder_10.append("if 内容 \r\n"); 
 		_jsp_builder_10.append("		"); 
 		JspTag _jsp_tag_ix_10 = new sn.mini.jsp.compiler.tag.TextTag(); 
		_jsp_tag_ix_10.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_10.text(_jsp_builder_10.toString()); 
		// 文本标签结束  
		
		_jsp_tag_ix_9.addChild(_jsp_tag_ix_10); 
 		// <jsp:tag 自定义标签结束 
		 
		// 将子标签加入自定义标签的children 
		_jsp_tag_ix_8.addChild(_jsp_tag_ix_9); 
 		 
		 
		
		// 文本标签开始  
		StringBuilder _jsp_builder_11 = new StringBuilder(); 
		_jsp_builder_11.append("\r\n"); 
 		_jsp_builder_11.append("		"); 
 		JspTag _jsp_tag_ix_11 = new sn.mini.jsp.compiler.tag.TextTag(); 
		_jsp_tag_ix_11.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_11.text(_jsp_builder_11.toString()); 
		// 文本标签结束  
		
		// 将子标签加入自定义标签的children 
		_jsp_tag_ix_8.addChild(_jsp_tag_ix_11); 
 		 
		 
		 
		// <jsp:tag 自定义标签开始 
		JspTag _jsp_tag_ix_12 = new sn.mini.jsp.compiler.tag.ElseTag(); 
		_jsp_tag_ix_12.engine(getEngine()).jspSession(session).context(context);
		
		// 文本标签开始  
		StringBuilder _jsp_builder_13 = new StringBuilder(); 
		_jsp_builder_13.append("else 内容\r\n"); 
 		_jsp_builder_13.append("		"); 
 		JspTag _jsp_tag_ix_13 = new sn.mini.jsp.compiler.tag.TextTag(); 
		_jsp_tag_ix_13.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_13.text(_jsp_builder_13.toString()); 
		// 文本标签结束  
		
		_jsp_tag_ix_12.addChild(_jsp_tag_ix_13); 
 		// <jsp:tag 自定义标签结束 
		 
		// 将子标签加入自定义标签的children 
		_jsp_tag_ix_8.addChild(_jsp_tag_ix_12); 
 		 
		 
		
		// 文本标签开始  
		StringBuilder _jsp_builder_14 = new StringBuilder(); 
		_jsp_builder_14.append("\r\n"); 
 		_jsp_builder_14.append("	"); 
 		JspTag _jsp_tag_ix_14 = new sn.mini.jsp.compiler.tag.TextTag(); 
		_jsp_tag_ix_14.engine(getEngine()).jspSession(session).context(context);
		_jsp_tag_ix_14.text(_jsp_builder_14.toString()); 
		// 文本标签结束  
		
		// 将子标签加入自定义标签的children 
		_jsp_tag_ix_8.addChild(_jsp_tag_ix_14); 
 		 
		// 调用自定义标签的doTag方法 
		_jsp_tag_ix_8.doTag(out); 
		// <jsp:tag 自定义标签结束 
		 
		out.write("\r\n"); 
 		out.write("</body>\r\n"); 
 		out.write("</html>"); 
 	}
}
