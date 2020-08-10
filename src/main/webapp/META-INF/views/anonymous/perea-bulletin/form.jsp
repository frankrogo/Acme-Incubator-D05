<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="anonymous.perea-bulletin.form.label.title" path="title"/>
	<acme:form-textbox code="anonymous.perea-bulletin.form.label.body" path="body"/>
	
	<acme:form-submit code="anonymous.perea-bulletin.form.button.create" action="/anonymous/perea-bulletin/create"/>
  	<acme:form-return code="anonymous.perea-bulletin.form.button.return"/>
</acme:form>