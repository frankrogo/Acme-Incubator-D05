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

	<jstl:choose>
		<jstl:when test="${status == 'rejected' && reasonRejected != ''}">
			<jstl:set var="option" value="true" />
		</jstl:when>
		<jstl:otherwise>
			<jstl:set var="option" value="false" />
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:form-textbox readonly="true" code="entrepreneur.application.form.label.ticker" path="ticker"/>
	
	<jstl:choose>
		<jstl:when test="${status == 'pending' || (status == 'rejected' && reasonRejected == '') || (status == 'accepted' && reasonRejected != '')}">
			<acme:form-select code="entrepreneur.application.form.label.status" path="status">
				<acme:form-option code="entrepreneur.application.form.label.statusPending" value="pending" selected="true"/>
				<acme:form-option code="entrepreneur.application.form.label.statusAccepted" value="accepted"/>
				<acme:form-option code="entrepreneur.application.form.label.statusRejected" value="rejected"/>
			</acme:form-select>
			<acme:form-textarea code="entrepreneur.application.form.label.reasonRejected" path="reasonRejected"/>
		</jstl:when>
		<jstl:when test="${status == 'rejected' && reasonRejected != ''}">
			<acme:form-textbox code="entrepreneur.application.form.label.status" path="status" readonly="true"/>
			<acme:form-textarea code="entrepreneur.application.form.label.reasonRejected" path="reasonRejected" readonly="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:form-textbox code="entrepreneur.application.form.label.status" path="status" readonly="true"/>
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:form-moment readonly="true" code="entrepreneur.application.form.label.creationMoment" path="creationMoment"/>
	<acme:form-textarea readonly="true" code="entrepreneur.application.form.label.statement" path="statement"/>
	<acme:form-money readonly="true" code="entrepreneur.application.form.label.moneyOffer" path="moneyOffer"/>
	
	
	
	
	
	
	
	
	
	<%-- <jstl:if test="${status == 'pending'}">
	<acme:form-select code="entrepreneur.application.form.label.status" path="status">
			<acme:form-option code="entrepreneur.application.form.label.statusPending" value="pending" selected="true"/>
			<acme:form-option code="entrepreneur.application.form.label.statusAccepted" value="accepted"/>
			<acme:form-option code="entrepreneur.application.form.label.statusRejected" value="rejected"/>
	</acme:form-select>
	</jstl:if>
	
	<jstl:if test="${command == 'update' && (status == 'accepted' || status == 'rejected')}">
	<acme:form-select code="entrepreneur.application.form.label.status" path="status">
			<acme:form-option code="entrepreneur.application.form.label.statusPending" value="pending" selected="true"/>
			<acme:form-option code="entrepreneur.application.form.label.statusAccepted" value="accepted"/>
			<acme:form-option code="entrepreneur.application.form.label.statusRejected" value="rejected"/>
	</acme:form-select>
	</jstl:if>
	
	<jstl:if test="${command == 'show' && (status == 'accepted' || status == 'rejected')}">
		<acme:form-textbox readonly="true" code="entrepreneur.application.form.label.status" path="status"/>
	</jstl:if>
	
	<jstl:if test="${status == 'pending' || status == 'rejected'}">
		<acme:form-textbox readonly="false" code="entrepreneur.application.form.label.reasonRejected" path="reasonRejected"/>
	</jstl:if> --%>
	
	<%-- <jstl:if test="${status == 'pending' || command == 'update'}">
		<acme:form-submit test="${command == 'show' || command == 'update'}" code="entrepreneur.application.form.button.update" action="/entrepreneur/application/update"/>
	</jstl:if> --%>
	
	<acme:form-submit test="${status == 'pending' || (status == 'rejected' && reasonRejected == '') || (status == 'accepted' && reasonRejected != '')}" code="entrepreneur.application.form.button.update" action="/entrepreneur/application/update"/>
	

	<acme:form-return code="entrepreneur.application.form.button.return"/>
</acme:form>
