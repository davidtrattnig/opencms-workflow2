<%@page buffer="none"%>
<%@page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%-- 
	The Template must store a com.bearingpoint.opencms.commons.templatehelper.CommonsTemplateHandler - Object
	into the request with key="cms"
	Example:
	
	<c:if test="${empty cms}">
		<%
			CommonsTemplateHandler cms = new CommonsTemplateHandler(pageContext, request, response); 
			request.setAttribute("cms", cms);
		%>
	</c:if>
--%>
<cms:include property="template" element="head" />
<cms:include property="template" element="navigation" />
  
	<div id="whitebg"><!--beginn whitebg-->
		<%-- The "cms:contentload"-TAG is only necessary for the direct-Edit-Button --%>
		<cms:contentload collector="singleFile" param="${'${'}opencms.uri}" editable="true">
			<div id="lefthome"><!--beginn lefthome-->
				<div id="leftpadder"><!--beginn leftpadder -->
	
					<h1>${cms.content.Title}</h1>
					
					<div id="lefttext"><div id="redline"><img height="1px" width="531px" src="img/redpit.gif" alt="pit" /></div>
						<p>${cms.content.Description}</p>
						
						<h2>${cms.content.FormTitle}</h2>
					</div>
					<div class="somepixheight4"></div>
					<div class="clearing">&nbsp;</div>
					
					<form>
						<div class="productbox">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<c:forEach var="inputFieldBlock" items="${cms.content.InputFieldBlock}">
									<tr>
										<th colspan="3"><label for="textfield">${inputFieldBlock.Headline}</label></th>
									</tr>
									<c:forEach var="inputField" items="${inputFieldBlock.InputField}" >
										<c:set var="inputField" value="${inputField}" scope="request"/>
										<cms:include file="../elements/defaultInputFieldRow.jsp" />
									</c:forEach>
								</c:forEach>
							</table>
						</div>
					</form>
				</div><!--end leftpadder -->
			</div><!--end lefthome-->
		</cms:contentload>
	</div><!--end whitebg-->
<cms:include property="template" element="foot" />

