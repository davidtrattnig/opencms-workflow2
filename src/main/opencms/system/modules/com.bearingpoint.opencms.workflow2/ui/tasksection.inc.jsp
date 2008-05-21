<%@ page import="com.bearingpoint.opencms.workflow2.cms.ui.WorkflowTaskView" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:length(tasks) == 0}">
	<tr><td class="section">${section}:</td><td colspan="7" class="firsttask">No task available!</td></tr>
</c:if>
			
<c:forEach var="task" items="${tasks}" varStatus="status">    		    				
	<tr class="task${status.index%2}">
		
		<c:if test="${status.first}">
		   	<td rowspan="${fn:length(tasks)}" class="section">${section}</td>
		</c:if>
					    			    
		<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>${task.createDate}</td>
		<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>${task.dueDate}</td>
		<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>${task.userName}</td>
		<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>${task.data.taskTitle}</td>
		<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>${task.resourceName}</td>
		<td <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>
		
			<c:if test="${task.pooled}">
				Pooled within ${task.projectName} - <a href="index.jsp?<%=WorkflowTaskView.PARAM_ACTION%>=<%=WorkflowTaskView.DIALOG_ACCEPT%>&task=${task.id}">Take!</a>
			</c:if>
			<c:if test="${!task.pooled}">									
				Assigned to ${task.userName} - <a href="index.jsp?<%=WorkflowTaskView.PARAM_ACTION%>=<%=WorkflowTaskView.DIALOG_POOL%>&task=${task.id}">Pool!</a>
			</c:if>
	
		</td>
		<c:if test="${isAdmin}">
			<td  <c:if test="${!status.first}">class="taskdata"</c:if><c:if test="${status.first}">class="firsttask"</c:if>>
				<a href="index.jsp?<%=WorkflowTaskView.PARAM_ACTION%>=<%=WorkflowTaskView.DIALOG_DELETETASK%>&task=${task.id}"">Delete</a>
			</td>
		</c:if>			
	</tr>
</c:forEach>