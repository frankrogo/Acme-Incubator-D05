<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.challenge.form.label.title" path="title" />
	<acme:form-moment code="authenticated.challenge.form.label.deadline" path="deadline" />
	<acme:form-textarea code="authenticated.challenge.form.label.description" path="description" />
	<acme:form-textarea code="authenticated.challenge.form.label.rookieGoal" path="rookieGoal" />
	<acme:form-money code="authenticated.challenge.form.label.rookieReward" path="rookieReward" />
	<acme:form-textarea code="authenticated.challenge.form.label.averageGoal" path="averageGoal" />
	<acme:form-money code="authenticated.challenge.form.label.averageReward" path="averageReward" />
	<acme:form-textarea code="authenticated.challenge.form.label.expertGoal" path="expertGoal" />
	<acme:form-money code="authenticated.challenge.form.label.expertReward" path="expertReward" />

	<acme:form-return code="authenticated.challenge.form.button.return" />
</acme:form>