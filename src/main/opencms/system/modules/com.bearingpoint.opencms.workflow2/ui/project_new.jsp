<%@ page import="com.bearingpoint.opencms.workflow2.cms.ui.ProjectEdit"%>
<%
	ProjectEdit wp = new ProjectEdit(pageContext, request, response);
	wp.displayDialog();
%>
