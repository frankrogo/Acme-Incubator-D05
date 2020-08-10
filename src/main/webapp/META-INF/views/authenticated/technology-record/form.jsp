<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.technology-record.form.label.stars" path="stars"/>
	<acme:form-textbox code="authenticated.technology-record.form.label.title" path="title"/>
	<acme:form-textbox code="authenticated.technology-record.form.label.sector" path="sector"/>
	<acme:form-textbox code="authenticated.technology-record.form.label.inventorName" path="inventorName"/>
	<acme:form-textarea code="authenticated.technology-record.form.label.description" path="description"/>
	<acme:form-url code="authenticated.technology-record.form.label.website" path="website"/>
	<acme:form-textbox code="authenticated.technology-record.form.label.email" path="email"/>
	<acme:form-textbox code="authenticated.technology-record.form.label.source" path="source"/>

  	<acme:form-return code="authenticated.technology-record.form.button.return"/>
</acme:form>