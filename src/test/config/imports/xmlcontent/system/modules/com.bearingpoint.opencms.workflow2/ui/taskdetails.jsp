<%@ page import="com.bearingpoint.opencms.workflow2.engine.jbpm.Messages" %>
<%@ page import="com.bearingpoint.opencms.workflow2.engine.jbpm.WorkflowView" %>
<%@ page import="com.bearingpoint.opencms.workflow2.engine.jbpm.I_Task" %>
<%@ page import="com.bearingpoint.opencms.workflow2.engine.jbpm.TaskComment" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%
	//initialize the workplace class
	WorkflowView wp = new WorkflowView(pageContext, request, response);		
	boolean bValidComment=true;	
%>
<% switch (wp.getAction()) { 
	 case WorkflowView.ACTION_DETAIL_ADDCOMMENT:	 		
		 //wp.setParamAction(WorkflowView.DIALOG_DETAIL_ADDCOMMENT); 
		 bValidComment=wp.actionAddComment();	
	default:
	    I_Task task = wp.getTask();
		List<TaskComment> lComments = wp.getCommentsOfTask(task.getId());
		TaskComment tc;	
%>
<%= wp.htmlStart() %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/opencms/opencms/system/workplace/scripts/opencms.js"></SCRIPT>
<script type="text/javascript">
<!--
	function transferComment() {
	
		ca = document.getElementById("commentarea");
		ci = document.getElementById("comment");
		ci.value = ca.value;
		document.main.submit();
	}
//-->
</script>
<link rel="stylesheet" type="text/css" href="<%= WorkflowView.getStyleUri(wp.getJsp(),"workflowview.css") %>">

<%= wp.bodyStart(null) %>

<form action="<%=wp.getDialogUri()%>?<%=WorkflowView.PARAM_TASKID%>=<%=task.getId()%>" id="main" name="main">
<% wp.setParamAction(WorkflowView.DIALOG_DETAIL_ADDCOMMENT); %>
<%= wp.paramsAsHidden() %>
<input type="hidden" id="comment" name="comment" />
<input type="hidden" name="task" value="<%= task.getId() %>" />
<input type="hidden" name="<%= wp.PARAM_FRAMENAME %>" value="" />

<% if (wp.getAction()==WorkflowView.ACTION_DETAIL_ADDCOMMENT && bValidComment==false) { %><div class="errorbar"><%=wp.key(Messages.GUI_TASK_ADD_COMMENT_ERROR)%></div><%
}
%>

<div>
	<h3><%=wp.key(Messages.GUI_TASK_DETAILS_FOR_TASK)%> "<b><%=task.getTaskName()%></b>"</h3>
	<hr />
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td>Resource</td>
			<td><%=wp.resolveResourceId(task.getResourceId())%></td>	
		</tr>
		<tr>
			<td width="150"><%=wp.key(Messages.GUI_TASK_USER_0)%></td>
			<td><%=wp.resolveUser(task.getPreviousActorId())%></td>				
		</tr>
		<tr>
			<td><%=wp.key(Messages.GUI_TASK_DESTUSER_0)%>/<%=wp.key(Messages.GUI_TASK_DESTROLE_0)%></td>
			<td><%
			if (task.getActorId()==null) {
			%><%=task.getPooledGroupId()%><%
			} else {
			%><%=task.getActorId()%><%
			}
			%></td>				
		</tr>
		<tr>
			<td><%=wp.key(Messages.GUI_TASK_DATE)%></td>
			<td><%=wp.formatDate(task.getCreateDate())%></td>
		</tr>
		<tr>
			<td><%=wp.key(Messages.GUI_TASK_DUEDATE)%></td>
			<td><%=wp.formatDate(task.getDueDate())%></td>				
		</tr>
		<tr><td colspan="2"><br/></td></tr>
		<tr>
		<td valign="top"><%=wp.key(Messages.GUI_TASK_COMMENTS)%></td>
		<td><%
			Iterator it = lComments.iterator();
			while (it.hasNext()) {	

				tc= ((TaskComment) it.next());
		%>
			<br/>
			<strong><%=wp.formatDate(tc.getDate())%> : <%=wp.resolveUser(tc.getActorId())%></strong>
			<br/>
			<%=tc.getMessage()%>
			<hr />
		<%
		}
		%></td>
		</tr>
		<tr>
		<td></td>
		<td><textarea id="commentarea" name="commentarea" rows="3" cols="23"></textarea><br />
		<input type="button" value="<%= wp.key(Messages.GUI_TASK_ADD_COMMENT) %>" onclick="transferComment()" /></td>
		</tr>		
	</table>
	<br />
	
</div>
</form>

<div class="bar">
	<input type="button" onclick="document.location.href='index.jsp'" value="<%= wp.key(Messages.GUI_TASK_CLOSE_DETAILS) %>" />	
</div>	

<%= wp.bodyEnd() %>
<%= wp.htmlEnd() %>
<% 
	} 
%>
