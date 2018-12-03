<%@ page language="java" import="org.apache.struts.taglib.TagUtils" %>
<%@ page language="java" import="org.apache.struts.util.RequestUtils" %>
<%
	String homeURL = RequestUtils.requestToServerStringBuffer(request).toString() + TagUtils.getInstance().computeURL(pageContext, "home", null, null, null, null, null, null, true);
%>
<meta http-equiv="refresh" content="8;url=<%=homeURL%>"/>
