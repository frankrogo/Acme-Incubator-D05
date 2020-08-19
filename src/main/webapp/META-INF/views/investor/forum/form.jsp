
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-hidden path="investmentRoundId"/>
	<h4><acme:message code="investor.forum.form.label.investmentRoundTicker"/> <acme:print value="${investmentRoundTicker}"/></h4><br>
	<acme:form-textbox code="investor.forum.form.label.title" path="title"/>
	
	<acme:form-submit code="investor.forum.form.button.messages" action="/investor/message/list-mine?forumId=${forumId}"  method="get"/>
	<acme:form-submit code="investor.forum.form.button.forumMessages" action="/investor/message/list-all?forumId=${forumId}"  method="get"/>
	<acme:form-submit test="${command == 'show'}" method="get" code= "investor.forum.form.button.createMessage" action= "/investor/message/create?forumId=${forumId}"/>
	<acme:form-submit test="${command == 'show'}" method="get" code="investor.forum.form.button.messengers" action="/investor/messenger/list-by-forumId?forumId=${forumId}"/>
	<acme:form-return code="investor.forum.form.button.return"/>
</acme:form>