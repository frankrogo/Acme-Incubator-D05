
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<acme:form>
	<jstl:if test="${(command != 'create')}">
		<c:if test="${investmentRoundTicker != null}">
			<h4><acme:message code="entrepreneur.forum.form.label.investmentRoundTicker"/> <acme:print value="${investmentRoundTicker}"/></h4><br>
		</c:if>	
		<acme:form-textbox readonly= "true" code="authenticated.forum.form.label.title" path="title"/>
	</jstl:if>
	
	<jstl:if test="${(command == 'create')}">
		<acme:form-textbox readonly= "false" code="authenticated.forum.form.label.title" path="title"/>
	</jstl:if>
	
	<acme:form-submit test="${command != 'create'}" method="get" code="authenticated.forum.form.button.messages" action="/authenticated/message/list-by-forum?forumId=${forumId}"/>
	<acme:form-submit test="${command != 'create'}" method="get" code="authenticated.forum.form.button.messengers" action="/authenticated/messenger/list-by-forum?forumId=${forumId}"/>
	
	<jstl:if test="${(command != 'create' && ownerForum == true)}">
		<c:if test="${investmentRoundTicker == null}">
			<acme:form-submit method="get" code= "authenticated.forum.form.button.createMessenger" action= "/authenticated/messenger/create?forumId=${forumId}"/>
		</c:if>	
	</jstl:if>
	
	<acme:form-submit test="${command != 'create'}" method="get" code= "authenticated.forum.form.button.createMessage" action= "/authenticated/message/create?forumId=${forumId}"/>
	
	
	<jstl:if test="${(command == 'show' && ownerForum == true) || command == 'delete'}">
		<c:if test="${investmentRoundTicker == null}">
			<acme:form-submit code= "authenticated.forum.form.button.delete" action= "/authenticated/forum/delete"/>
		</c:if>	
	</jstl:if>
	
	<acme:form-submit test="${command == 'create'}" method="post" code= "authenticated.forum.form.button.create" action= "/authenticated/forum/create"/>
	
	<acme:form-return code="authenticated.forum.form.button.return"/>
</acme:form>