<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.inquiry.form.label.title" path="title"/>
	<acme:form-moment code="authenticated.inquiry.form.label.creationMoment" path="creationMoment"/>
	<acme:form-moment code="authenticated.inquiry.form.label.deadline" path="deadline"/>
	<acme:form-textarea code="authenticated.inquiry.form.label.description" path="description"/>
	<acme:form-money code="authenticated.inquiry.form.label.minMoney" path="minMoney"/>
	<acme:form-money code="authenticated.inquiry.form.label.maxMoney" path="maxMoney"/>
	<acme:form-textbox code="authenticated.inquiry.form.label.email" path="email"/>

	<acme:form-return code="authenticated.inquiry.form.button.return"/>
</acme:form>