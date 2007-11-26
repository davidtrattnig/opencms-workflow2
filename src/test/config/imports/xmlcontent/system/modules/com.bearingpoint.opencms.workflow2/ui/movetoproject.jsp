<%@ page import="com.bearingpoint.opencms.workflow2.cms.ui.WorkflowMoveToProject" %><%// initialize the workplace class
	WorkflowMoveToProject wp = new WorkflowMoveToProject(pageContext, request, response);
	
//////////////////// start of switch statement 
	
switch (wp.getAction()) {

case WorkflowMoveToProject.ACTION_CANCEL:
//////////////////// ACTION: cancel button pressed

	wp.actionCloseDialog();

break;


case WorkflowMoveToProject.ACTION_COPYTOPROJECT:
//////////////////// ACTION: main copy to project action

	wp.actionMoveToProject();

break;


case WorkflowMoveToProject.ACTION_DEFAULT:
default:

//////////////////// ACTION: show copy to project dialog (default)

	wp.setParamAction(wp.DIALOG_TYPE);%><%= wp.htmlStart() %>
<%= wp.bodyStart("dialog") %>

<%= wp.dialogStart() %>
<%= wp.dialogContentStart(wp.getParamTitle()) %>

<%= wp.dialogSpacer() %>

<form name="main" action="<%= wp.getDialogUri() %>" method="post" class="nomargin" onsubmit="return submitAction('<%= wp.DIALOG_OK %>', null, 'main');">
<%= wp.paramsAsHidden() %>
<input type="hidden" name="<%= wp.PARAM_FRAMENAME %>" value="">

<%= wp.buildProjectInformation() %>

<%= wp.dialogContentEnd() %>
<%= wp.dialogButtonsOkCancel() %>

</form>

<%= wp.dialogEnd() %>
<%= wp.bodyEnd() %>
<%= wp.htmlEnd() %>
<%
} 
//////////////////// end of switch statement 
%>