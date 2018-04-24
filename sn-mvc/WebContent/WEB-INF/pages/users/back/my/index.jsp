<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
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
	<%
		List<String> list = Arrays.asList("text0", "text1", "text2", "text3");
		session.setAttribute("list", list);
	%>
	<jsp:include page="/WEB-INF/pages/MyController/1.jsp" />
	<jsp:for var="item" index="i" items="${list}" start="0" end="5" step="2">
		<div>El 结果集 this is div ${item}</div>
		<div>El 结果集 this is div ${i}</div>
	</jsp:for>
	<hr />
	<jsp:if test="${list.size() > 1}">
		this is one if tag  by el.
	</jsp:if>
	<jsp:if test="<%= list.size() > 1 %>">
		this is one if tag  by java.
	</jsp:if>
	<hr />
	<jsp:if-else>
		<jsp:if test="${list.size() > 1}">
			if-else if 内容 
		</jsp:if>
		<jsp:else>
			else 内容
		</jsp:else>
	</jsp:if-else>
</body>
</html>