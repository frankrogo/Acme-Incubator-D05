<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-hidden path="forumId"/>
	
	<jstl:if test="${command != 'create' }">
		<h4><acme:message code="authenticated.messenger.form.forumTitle"/> <acme:print value="${forumTitle}"/></h4><br>
		<acme:form-textbox code="authenticated.messenger.form.label.ownsTheForum" path="ownsTheForum"/>
	</jstl:if>
	
	<jstl:if test="${(command != 'create' && ownsTheForum == true)}">
		<acme:form-submit code= "authenticated.messenger.form.button.delete" action= "/authenticated/messenger/delete"/>
	</jstl:if>
	
	<jstl:if test="${command == 'create' }">
		<acme:form-textbox code="authenticated.messenger.form.label.userName" path="userName"/>
		<acme:form-submit code= "authenticated.messenger.form.button.create" action= "/authenticated/messenger/create"/>
	</jstl:if>
	
	<acme:form-return code="authenticated.messenger.form.button.return"/>
</acme:form>