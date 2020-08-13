
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<jstl:if test="${(command != create)}">
		<acme:form-textbox readonly= "true" code="authenticated.forum.form.label.title" path="title"/>
	</jstl:if>
	
	<jstl:if test="${(command == create)}">
		<acme:form-textbox code="authenticated.forum.form.label.title" path="title"/>
	</jstl:if>
	
	<acme:form-submit test="${command != 'create'}" method="get" code="authenticated.forum.form.button.messages" action="/authenticated/message/list-by-forum?forumId=${forumId}"/>
	<acme:form-submit test="${command != 'create'}" method="get" code="authenticated.forum.form.button.messengers" action="/authenticated/messenger/list-by-forum?forumId=${forumId}"/>
	
	<jstl:if test="${(command != 'create' && ownerForum == true)}">
		<acme:form-submit method="get" code= "authenticated.forum.form.button.createMessenger" action= "/authenticated/messenger/create?forumId=${forumId}"/>
	</jstl:if>
	
	<acme:form-submit test="${command != 'create'}" method="get" code= "authenticated.forum.form.button.createMessage" action= "/authenticated/message/create?forumId=${forumId}"/>
	
	<acme:form-submit test="${command == 'create'}" code= "authenticated.forum.form.button.create" action= "/authenticated/forum/create"/>
	
	<acme:form-return code="authenticated.forum.form.button.return"/>
</acme:form>