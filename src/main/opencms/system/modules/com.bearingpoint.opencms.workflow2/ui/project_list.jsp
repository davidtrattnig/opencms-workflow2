<%@ page import="com.bearingpoint.opencms.workflow2.cms.ui.ProjectList"%>
<%
	// initialize the workplace class
	ProjectList wp = new ProjectList(pageContext, request, response);
    wp.displayDialog();
%>
