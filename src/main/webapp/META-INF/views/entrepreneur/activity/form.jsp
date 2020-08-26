<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not duty any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form >

	<acme:form-textbox code="entrepreneur.activity.form.label.title" path="title"/>
	<jstl:if test="${command != 'create'}">
		<acme:form-moment code="entrepreneur.activity.form.label.creationMoment" path="creationMoment"/>
	</jstl:if>
	<acme:form-hidden path="investmentRoundId"/>
	<acme:form-moment code="entrepreneur.activity.form.label.deadline" path="deadline"/>
	<acme:form-money code="entrepreneur.activity.form.label.budget" path="budget"/>
	<acme:form-submit test="${command == 'create' }" code="entrepreneur.activity.form.button.create" action="/entrepreneur/activity/create"/>
  	<acme:form-return code="entrepreneur.activity.form.button.return"/>
</acme:form>