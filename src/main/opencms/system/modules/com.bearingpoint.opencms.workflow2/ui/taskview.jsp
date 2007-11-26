<%@ page import="com.bearingpoint.opencms.workflow2.cms.ui.WorkflowTaskView" %>
<%@ page import="com.bearingpoint.opencms.workflow2.task.I_Task" %>
<%@ page import="org.opencms.file.CmsObject" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%
	//initialize the workplace class
	WorkflowTaskView wp = new WorkflowTaskView(pageContext, request, response);


	boolean actionState;
	
	switch(wp.getAction()) {
	 	case WorkflowTaskView.ACTION_ACCEPT:  
			actionState=wp.actionAccept();
			break;
		case WorkflowTaskView.ACTION_POOL: 
			actionState=wp.actionPool();
			break;
		case WorkflowTaskView.ACTION_DELETETASK:
		    actionState=wp.actionDeleteTask();
		    break;
	} 

        request.setAttribute("result", wp.getResult());
%>
<%= wp.htmlStart() %>
<link rel="stylesheet" type="text/css" href="<%=wp.getStyleUri("workflowview.css") %>" />
<script type="text/javascript">
<!--
	function initDlg() {		

		var browser=navigator.appName;
		var actualheight, actualwidth;

	    if (browser.indexOf("Microsoft Internet Explorer") != -1) {
			actualheight = document.documentElement.clientHeight;				
			actualwidth = document.documentElement.clientWidth;
		}
		else {
			actualheight = window.innerHeight;
			actualwidth = window.innerWidth;
		}			
		actualheight = actualheight - 10 + "px;";
		actualwidth = actualwidth + "px;";

		var con = document.getElementById("scrollcontainer");
		con.style.cssText = "height:"+actualheight+"; width:"+actualwidth+";";
		
	}
        
        function deleteTask(id, resource) {
           val = confirm("Do you really want to delete the task for resource '"+resource+"'?");
           if (val==true)   {            
               document.main.<%=WorkflowTaskView.PARAM_DELETETASK %>.value = id;
               document.main.submit();
           }
        }
//-->
</script>
<%= wp.bodyStart("wfview", "onLoad=\"initDlg()\" onResize=\"initDlg()\"") %>
<script language="text/javascript">
<!--
	document.body.onResize = "initDlg";
//-->
</script>

<form name="main" action="<%= wp.getDialogUri() %>" method="post" class="nomargin" onsubmit="return submitAction('<%= wp.DIALOG_OK %>', null, 'main');">
<%= wp.paramsAsHidden() %>
<input type="hidden" id="<%=WorkflowTaskView.PARAM_DELETETASK %>" name="<%=WorkflowTaskView.PARAM_DELETETASK %>" value="" />
<div id="scrollcontainer">
	<input type="hidden" name="<%= wp.PARAM_FRAMENAME %>" value="" />

	<c:if test="${result == null}">
		<h1 style="color:red;">There is a problem within the workflow engine!</h1>
	</c:if>

	<c:if test="${result.isWorkflowEngineAttached == false}">
		<p><strong>Task functionality not available - no workflow engine attached!</strong></p>
	</c:if>

	<c:if test="${result.isWorkflowEngineAttached == true}">
	    <h1>${result.headline}</h1>
		<table width="100%" cellpadding="0" cellspacing="0">		
			<tr>
				<td>Date</td>
		                <td>Due Date</td>
				<td>From</td>
				<td>Title</td>
				<td>Resource</td>
				<td>-accept/pool-</td>
				<td>-delete(admin)-</td>
			</tr>
		</table>
		<hr />my tasks:
    	<c:forEach var="task" items="${result.userTasks}" varStatus="status">    				
			<div style="font-wight:bold;color:green;">${task.data.taskTitle}</div>
		</c:forEach>	
		<hr />pooled tasks:
    	<c:forEach var="task" items="${result.pooledTasks}" varStatus="status">    				
			<div style="font-wight:bold;color:green;">${task.data.taskTitle}</div>
		</c:forEach>			
		<hr />manager tasks:
    	         <c:forEach var="task" items="${result.managerTasks}" varStatus="status">    				
			<div style="font-wight:bold;color:green;">${task.data.taskTitle}</div>
		</c:forEach>	
		<hr />other tasks:
		<table width="100%" cellpadding="0" cellspacing="0">
    	            <c:forEach var="task" items="${result.otherTasks}" varStatus="status">    				

			<tr>
				<td>${task.createDate}</td>
				<td>${task.dueDate}</td>
				<td>${task.userName}</td>
				<td style="color:green;">${task.data.taskTitle}</td>
				<td></td>
				<td>${task.assignedUserName} / ${task.projectName}</td>
				<td></td>
			</tr>
		    </c:forEach>	
		</table>			
	</c:if>			

</div>
</form>
<%= wp.bodyEnd() %>
<%= wp.htmlEnd() %>
