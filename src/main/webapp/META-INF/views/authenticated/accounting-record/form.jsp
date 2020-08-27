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

<acme:form readonly="true">
	<h4><acme:message code="authenticated.accounting-record.investment-round-ticker"/> <acme:print value="${investmentRoundTicker}"/></h4><br>
	<acme:form-textbox code="authenticated.accounting-record.form.label.title" path="title"/>
	<acme:form-textbox code="authenticated.accounting-record.form.label.status" path="status"/>
	<acme:form-textbox readonly="true" code="authenticated.accounting-record.form.label.status" path="statusl"/>
	<acme:form-moment code="authenticated.accounting-record.form.label.creationMoment" path="creationMoment"/>
	<acme:form-textarea code="authenticated.accounting-record.form.label.body" path="body"/>
	
  	<acme:form-return code="authenticated.accounting-record.form.button.return"/>
</acme:form>
