<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="anonymous.rodriguez-bulletin.form.label.author" path="author"/>
	<acme:form-textbox code="anonymous.rodriguez-bulletin.form.label.description" path="description"/>
	
	<acme:form-submit code="anonymous.rodriguez-bulletin.form.button.create" action="/anonymous/rodriguez-bulletin/create"/>
  	<acme:form-return code="anonymous.rodriguez-bulletin.form.button.return"/>
</acme:form>