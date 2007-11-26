<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>

<tr>
	<td>
		<label for="${inputField.FieldKey}">${inputField.FieldLabel}</label>
	</td>
	<td>
		<cms:include file="./inputField_${inputField.FieldType}.jsp" />
	</td>
	<td >${inputField.FieldComments}</td>
</tr>