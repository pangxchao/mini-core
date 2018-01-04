<%@page import="sn.mini.web.SNInitializer"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=SNInitializer.getWebRootPath()%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%!//
	private String s = new String("abc");
	//
	%>
	<%
		String name = "b";
		session.setAttribute("name", name);
		System.out.println(s);
	%>
	<%
		String bb = "wq vb kcg ";
		System.out.print(bb);
	%>
	Index.jsp
	<br /> a${name}c
	<jsp:include page="pages/MyController/1.jsp" />
	<jsp:for var="item" index="i" items="${users}" start="0" end="5" step="2">
		<div>this is div ${item.id}</div>
		<div>
			this is div
			<%=session.getAttribute("name")%></div>
	</jsp:for>
	<jsp:if-else>
		<jsp:if test="<%=s%>">
			if 内容 
		</jsp:if>
		<jsp:else>
			else 内容
		</jsp:else>
	</jsp:if-else>
</body>
</html>