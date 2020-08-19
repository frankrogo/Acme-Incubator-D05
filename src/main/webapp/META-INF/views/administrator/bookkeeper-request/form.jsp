<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<h4><acme:message code="administrator.bookkeeper-request.form.label.userName"/> <acme:print value="${userName}"/></h4><br>
	<acme:form-textbox code="administrator.bookkeeper-request.form.label.username" path="userName" readonly="true"/>
	<acme:form-textbox code="administrator.bookkeeper-request.form.label.firm" path="firm" readonly="true"/>
	<acme:form-textarea code="administrator.bookkeeper-request.form.label.responsibilityStatement" path="responsabilityStatement" readonly="true"/>
	<acme:form-textbox code="administrator.bookkeeper-request.form.label.status" path="status" readonly="true"/>
	<jstl:if test="${status == 'pending'}">
		<acme:form-submit code="administrator.bookkeeper-request.form.button.accept" action="/administrator/bookkeeper-request/accept"/>
		<acme:form-submit code="administrator.bookkeeper-request.form.button.reject" action="/administrator/bookkeeper-request/reject"/>
	</jstl:if>
	<acme:form-return code="administrator.bookkeeper-request.form.button.return"/>
</acme:form>
