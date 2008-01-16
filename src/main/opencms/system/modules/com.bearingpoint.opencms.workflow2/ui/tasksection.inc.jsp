<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:length(tasks) == 0}">
	<th class="section">Other Tasks:</th><td colspan="7">No task available!</td>
</c:if>
			
<c:forEach var="task" items="${tasks}" varStatus="status">    		    				
	<tr class="task${status.index%2}">
		
		<c:if test="${status.first}">
		   	<th rowspan="${fn:length(result.otherTasks)}" class="section">${section}</th>
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
</c:forEach>