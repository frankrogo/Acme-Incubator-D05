<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<acme:form>
	<acme:form-hidden path="forumId"/>
	
	<jstl:if test="${command != 'create' }">
		<h4><acme:message code="authenticated.messenger.form.label.forumName"/> <acme:print value="${forumName}"/></h4><br>
		<acme:form-textbox readonly = "true" code="authenticated.messenger.form.label.ownsTheForum" path="ownsTheForum"/>
	</jstl:if>
	
	<jstl:if test="${(command == 'show' && ownerForum == true) || command == 'delete'}">
		<c:if test="${investmentRoundTicker == null}">
			<acme:form-submit code= "authenticated.messenger.form.button.delete" action= "/authenticated/messenger/delete"/>
		</c:if>	
	</jstl:if>
	
	<jstl:if test="${command == 'create' }">
		<acme:form-textbox code="authenticated.messenger.form.label.userName" path="userName"/>
		<acme:form-submit code= "authenticated.messenger.form.button.create" action= "/authenticated/messenger/create"/>
	</jstl:if>
	
	<acme:form-return code="authenticated.messenger.form.button.return"/>
</acme:form>