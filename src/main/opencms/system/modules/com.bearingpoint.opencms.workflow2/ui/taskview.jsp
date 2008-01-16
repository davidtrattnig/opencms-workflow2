<%@ page import="com.bearingpoint.opencms.workflow2.cms.ui.WorkflowTaskView" %>
<%@ page import="com.bearingpoint.opencms.workflow2.task.I_Task" %>
<%@ page import="org.opencms.file.CmsObject" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link href="/opencms/export/system/workplace/commons/style/workplace.css" type="text/css" rel="stylesheet"/>
<style type="text/css">

.error {
	color:red;
}

body { 
	background-color:ThreedFace; 
}

.section, .column {
  background-color:ThreedFace; 
  border-right: 1px solid ThreedDarkShadow; 
  border-top: 1px solid ThreeDHighlight; 
  border-bottom: 1px solid ThreedDarkShadow; 
  border-left: 1px solid ThreeDHighlight; 
}

.task0 {
	background-color: white;
}

.task1 {
	background-color: #CCFFCC;
}

.taskdata, .firsttask {
	padding-left:5px;
	padding-right:5px;
}

.firsttask {
	border-top:1px dashed gray;
}

</style>
<form name="main" action="<%= wp.getDialogUri() %>" method="post" class="nomargin" onsubmit="return submitAction('<%= wp.DIALOG_OK %>', null, 'main');">
<%= wp.paramsAsHidden() %>
<input type="hidden" id="<%=WorkflowTaskView.PARAM_DELETETASK %>" name="<%=WorkflowTaskView.PARAM_DELETETASK %>" value="" />
<div id="scrollcontainer">
	<input type="hidden" name="<%= wp.PARAM_FRAMENAME %>" value="" />

	<c:if test="${result == null}">
		<h1 class="error">There is a problem within the workflow engine!</h1>
	</c:if>

	<c:if test="${result.isWorkflowEngineAttached == false}">
		<p><strong>Task functionality not available - no workflow engine attached!</strong></p>
	</c:if>

	<c:if test="${result.isWorkflowEngineAttached == true}">
	    <h1>${result.headline}</h1>
		<table width="100%" cellpadding="0" cellspacing="0">		
			<tr>
				<th class="column"><span class="starttab"><span style="width: 1px; height: 1px;"/></span></th>
				<th class="column">Date</th>
		        <th class="column">Due Date</th>
				<th class="column">From</th>
				<th class="column">Title</th>
				<th class="column">Resource</th>
				<th class="column">Assignment</th>
				<th class="column">-delete(admin)-</th>
			</tr>
				
				<!--		
			<c:forEach var="task" items="${result.userTasks}" varStatus="status">    		    				
			<tr class="task${status.index%2}">

				<c:if test="${status.first}">
			    	<th rowspan="${fn:length(result.userTasks)}" class="section">Your Tasks:</th>
			    </c:if>
			    
				<td class="taskdata">${task.createDate}</td>
				<td class="taskdata">${task.dueDate}</td>
				<td class="taskdata">${task.userName}</td>
				<td class="taskdata" style="color:green;">${task.data.taskTitle}</td>
				<td class="taskdata"></td>
				<td class="taskdata">
					<c:if test="${task.assignedUserName == null}">
						Pooled within ${task.projectName} - <a href="#">Take!</a>
					</c:if>
					<c:if test="${task.assignedUserName != null}">
						Assigned to ${task.projectName} - <a href="#">Pool!</a>
					</c:if>
				</td>
				<td class="taskdata"></td>
			</tr>
			</c:forEach>
						
			<c:forEach var="task" items="${result.pooledTasks}" varStatus="status">    		    				
			<tr class="task${status.index%2}">
				
				<c:if test="${status.first}">
			    	<th rowspan="${fn:length(result.pooledTasks)}" class="section">Pooled Tasks:</th>
			    </c:if>
			    
				<td class="taskdata">${task.createDate}</td>
				<td class="taskdata">${task.dueDate}</td>
				<td class="taskdata">${task.userName}</td>
				<td class="taskdata" style="color:green;">${task.data.taskTitle}</td>
				<td class="taskdata"></td>
				<td class="taskdata">
					<c:if test="${task.assignedUserName == null}">
						Pooled within ${task.projectName} - <a href="#">Take!</a>
					</c:if>
					<c:if test="${task.assignedUserName != null}">
						Assigned to ${task.projectName} - <a href="#">Pool!</a>
					</c:if>
				</td>
				<td class="taskdata"></td>
			</tr>
			</c:forEach>
					
			<c:if test="${fn:length(result.managerTasks) == 0}">
			<tr class="task0">
				<th class="section">Manager Tasks:</th><td colspan="7" style="border-top:1px solid black">No tasks available!</td>
			</tr>
			</c:if>
									
			<c:forEach var="task" items="${result.managerTasks}" varStatus="status">    		    				
			<tr class="task${status.index%2}">

				<c:if test="${status.first}">
			    	<th rowspan="${fn:length(result.managerTasks)}" class="section">Manager Tasks:</th>
			    </c:if>
			    
				<td class="taskdata" <c:if test="${status.first}"></c:if>>${task.createDate}</td>
				<td class="taskdata" <c:if test="${status.first}">style="border-top:1px solid black"</c:if>>${task.dueDate}</td>
				<td class="taskdata" <c:if test="${status.first}">style="border-top:1px solid black"</c:if>>${task.userName}</td>
				<td class="taskdata" style="color:green;">${task.data.taskTitle}</td>
				<td class="taskdata"></td>
				<td class="taskdata">
					<c:if test="${task.assignedUserName == null}">
						Pooled within ${task.projectName} - <a href="#">Take!</a>
					</c:if>
					<c:if test="${task.assignedUserName != null}">
						Assigned to ${task.projectName} - <a href="#">Pool!</a>
					</c:if>
				</td>
				<td class="taskdata"></td>
			</tr>
			</c:forEach>
			
			<c:if test="fn:length(result.otherTasks) == 0">
				<th class="section">Other Tasks:</th><td colspan="7">No task available!</td>
			</c:if>
			
			<c:forEach var="task" items="${result.otherTasks}" varStatus="status">    		    				
			<tr class="task${status.index%2}">
			
				<c:if test="${status.first}">
			    	<th rowspan="${fn:length(result.otherTasks)}" class="section">Other Tasks:</th>
			    </c:if>
			    			    
				<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>${task.createDate}</td>
				<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>${task.dueDate}</td>
				<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>${task.userName}</td>
				<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>${task.data.taskTitle}</td>
				<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>&nbsp;</td>
				<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>
					<c:if test="${task.assignedUserName == null}">
						Pooled within ${task.projectName} - <a href="#">Take!</a>
					</c:if>
					<c:if test="${task.assignedUserName != null}">
						Assigned to ${task.projectName} - <a href="#">Pool!</a>
					</c:if>
				</td>
				<td  <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>&nbsp;</td>
			</tr>
			</c:forEach>-->

			<c:set var="section" value="Other Tasks" scope="request" />
			<c:set var="tasks" value="${result.otherTasks}" scope="request" />
			<cms:include page="tasksection.inc.jsp" />				
		</table>			
	</c:if>	
	</div>
</form>
<%= wp.bodyEnd() %>
<%= wp.htmlEnd() %>