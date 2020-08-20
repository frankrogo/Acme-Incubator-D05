
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-hidden path="investmentRoundId"/>
	<h4><acme:message code="investor.application.form.investmentRoundTicker"/> <acme:print value="${investmentRoundTicker}"/></h4><br>
	<acme:form-textbox readonly = "true" code="investor.application.form.label.ticker" path="ticker"/>
	
	<jstl:if test="${command != 'create' }">
		<acme:form-moment code="investor.application.form.label.creationMoment" path="creationMoment"/>
		<acme:form-textbox code="investor.application.form.label.status" path="status"/>
	</jstl:if>
	
	<acme:form-textarea code="investor.application.form.label.statement" path="statement"/>
	<acme:form-money code="investor.application.form.label.moneyOffer" path="moneyOffer"/>
	
	<jstl:if test="${status == 'rejected'}">
		<acme:form-textbox code="investor.application.form.label.reasonRejected" path="reasonRejected"/>
	</jstl:if>
	
	<acme:form-submit test="${command == 'create'}" code="investor.application.form.button.create" action= "/investor/application/create?investmentRoundId=${investmentRoundId}"/>
	<acme:form-return code="investor.application.form.button.return"/>
</acme:form>
