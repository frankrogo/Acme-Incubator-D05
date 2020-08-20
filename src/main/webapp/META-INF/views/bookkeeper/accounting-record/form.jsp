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

<acme:form >
	<h4><acme:message code="bookkeeper.accounting-record.form.label.investmentRoundTicker"/> <acme:print value="${investmentRoundTicker}"/></h4><br>
	<acme:form-textbox code="bookkeeper.accounting-record.form.label.title" path="title"/>
		<jstl:if test="${command != 'create'}">
	<acme:form-moment  code="bookkeeper.accounting-record.form.label.creationMoment" path="creationMoment"/>
	 </jstl:if>
	<acme:form-textbox code="bookkeeper.accounting-record.form.label.body" path="body"/>
	<acme:form-hidden path="investmentRoundId"/> 
	<acme:form-checkbox code="bookkeeper.accounting-record.form.label.status" path="status"/>
	<acme:form-submit test="${command == 'create'}" code="bookkeeper.accounting-record.form.button.create"
		action="/bookkeeper/accounting-record/create"/>	
	<acme:form-submit test="${command != 'create' && status==false}" code="bookkeeper.accounting-record.form.button.update" action="/bookkeeper/accounting-record/update"/>
		
	<acme:form-return code="bookkeeper.accounting-record.form.button.return"/>
</acme:form>