
<%--
- list.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not thread-message any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="entrepreneur.message.list.label.title" path="title" width="20%"/>
	<acme:list-column code="entrepreneur.message.list.label.creationMoment" path="creationMoment" width="20%"/>
	<acme:list-column code="entrepreneur.message.list.label.userName" path="userName" readonly="true"/>
</acme:list>

<acme:form-return code="entrepreneur.message.list.button.return"/>