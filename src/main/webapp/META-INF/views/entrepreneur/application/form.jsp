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

<acme:form>
	<h4><acme:message code="entrepreneur.application.form.label.investmentRoundTicker"/> <acme:print value="${investmentRoundTicker}"/></h4><br>
	<h4><acme:message code="entrepreneur.application.form.label.investmentRoundTitle"/> <acme:print value="${investmentRoundTitle}"/></h4><br>
	<acme:form-textbox readonly="true" code="entrepreneur.application.form.label.ticker" path="ticker"/>
	<acme:form-moment readonly="true" code="entrepreneur.application.form.label.creationMoment" path="creationMoment"/>
	<acme:form-textarea readonly="true" code="entrepreneur.application.form.label.statement" path="statement"/>
	<acme:form-money readonly="true"code="entrepreneur.application.form.label.moneyOffer" path="moneyOffer"/>
	
	
	<jstl:if test="${status == 'pending'}">
	<acme:form-select code="entrepreneur.application.form.label.status" path="status">
			<acme:form-option code="entrepreneur.application.form.label.statusPending" value="pending" selected="true"/>
			<acme:form-option code="entrepreneur.application.form.label.statusAccepted" value="accepted"/>
			<acme:form-option code="entrepreneur.application.form.label.statusRejected" value="rejected"/>
	</acme:form-select>
	</jstl:if>
	
	<jstl:if test="${status == 'accepted' || status == 'rejected'}">
		<acme:form-textbox readonly="true" code="entrepreneur.application.form.label.status" path="status"/>
	</jstl:if>
	
	<jstl:if test="${status == 'pending' || status == 'rejected'}">
		<acme:form-textbox readonly="false" code="entrepreneur.application.form.label.reasonRejected" path="reasonRejected"/>
	</jstl:if>
	
	<jstl:if test="${status == 'pending' || command == 'update'}">
		<acme:form-submit test="${command == 'show' || command == 'update'}" code="entrepreneur.application.form.button.update" action="/entrepreneur/application/update"/>
	</jstl:if>

	<acme:form-return code="entrepreneur.application.form.button.return"/>
</acme:form>
