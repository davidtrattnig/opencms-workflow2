<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<!-- 

		type: ${inputField.FieldType} <br/>
		label: ${inputField.FieldLabel} <br/>
		key: ${inputField.FieldKey} <br/>

 -->
<c:forEach var="fieldItem" items="${inputField.InputFieldItem}">
	<!-- 
	
			fieldItem.value: ${fieldItem.FieldItemValue} <br/>
			fieldItem.text: ${fieldItem.FieldItemText} <br/>
	
	 -->
	<input type="checkbox" name="${inputField.FieldKey}" value="${fieldItem.FieldItemValue}" id="${inputField.FieldKey}_${fieldItem.FieldItemValue}" />${fieldItem.FieldItemText}
</c:forEach>