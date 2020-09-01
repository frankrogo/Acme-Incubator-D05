<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not job any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="bookkeeper.investment-round.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="bookkeeper.investment-round.form.label.title" path="title"/>
	<acme:form-moment code="bookkeeper.investment-round.form.label.creationMoment" path="creationMoment"/>
	<acme:form-textbox code="bookkeeper.investment-round.form.label.round" path="round"/>
	<acme:form-textarea code="bookkeeper.investment-round.form.label.description" path="description"/>
	<acme:form-money code="bookkeeper.investment-round.form.label.moneyAmount" path="moneyAmount"/>
	<acme:form-url code="bookkeeper.investment-round.form.label.moreInfo" path="moreInfo"/>
	<acme:form-checkbox code="bookkeeper.investment-round.form.label.finalMode" path="finalMode"/>
	
	<jstl:if test="${command == 'show'}">
	<acme:form-submit code="bookkeeper.investment-round.form.button.accounting-records-create" action="/bookkeeper/accounting-record/create?investmentRoundId=${investmentRoundId}" method="get"/>
  	</jstl:if>
	<acme:form-submit code="bookkeeper.investment-round.form.button.activities" action="/bookkeeper/activity/list-by-ir?investmentRoundId=${investmentRoundId}" method="get"/>
	<acme:form-submit code="bookkeeper.investment-round.form.button.accounting-records" action="/authenticated/accounting-record/list-by-ir?investmentRoundId=${investmentRoundId}" method="get"/>
  	<acme:form-return code="bookkeeper.investment-round.form.button.return"/>
</acme:form>