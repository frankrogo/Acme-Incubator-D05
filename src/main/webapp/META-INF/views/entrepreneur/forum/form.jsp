
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-hidden path="investmentRoundId"/>
	
	<h4><acme:message code="entrepreneur.forum.form.label.investmentRoundTicker"/> <acme:print value="${investmentRoundTicker}"/></h4><br>
	<acme:form-textbox code="entrepreneur.forum.form.label.title" path="title"/>
	
	<acme:form-submit method="get" code="entrepreneur.forum.form.button.messages" action="/entrepreneur/message/list-by-forum?forumId=${forumId}"/>
	<acme:form-submit method="get" code="entrepreneur.forum.form.button.messengers" action="/authenticated/messenger/list-by-forum?forumId=${forumId}"/>
	<acme:form-submit method="get" code= "entrepreneur.forum.form.button.createMessage" action= "/authenticated/message/create?forumId=${forumId}"/>

	<acme:form-return code="entrepreneur.forum.form.button.return"/>
</acme:form>