<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.technology-record.form.label.stars" path="stars" width="20%"/>
	<acme:list-column code="authenticated.technology-record.list.label.title" path="title" width="50%"/>
	<acme:list-column code="authenticated.technology-record.list.label.inventorName" path="inventorName" width="30%"/>
</acme:list>