<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="administrator.bookkeeper-request.list.label.firm" path="firm" width="30%"/>
	<%-- <acme:list-column code="administrator.bookkeeper-request.list.label.responsibilityStatement" path="responsabilityStatement" width="50%"/> --%>
	<acme:list-column code="administrator.bookkeeper-request.list.label.userName" path="userName" width="50%"/>
	<acme:list-column code="administrator.bookkeeper-request.list.label.status" path="status" width="20%"/>
</acme:list>