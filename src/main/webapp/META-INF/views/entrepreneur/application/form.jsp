<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not request any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<h4><acme:message code="entrepreneur.application.form.label.investmentRoundTicker"/> <acme:print value="${investmentRoundTicker}"/></h4><br>
	<acme:form-textbox code="entrepreneur.application.form.label.ticker" path="ticker"/>
	<acme:form-moment code="entrepreneur.application.form.label.creationMoment" path="creationMoment"/>
	<acme:form-textarea code="entrepreneur.application.form.label.statement" path="statement"/>
	<acme:form-money code="entrepreneur.application.form.label.moneyOffer" path="moneyOffer"/>
	<acme:form-textbox code="entrepreneur.application.form.label.status" path="status"/>
	
	<acme:form-return code="entrepreneur.application.form.button.return"/>
</acme:form>
