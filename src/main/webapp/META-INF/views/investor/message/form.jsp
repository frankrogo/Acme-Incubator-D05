<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not thread-message any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-hidden path="forumId"/>
	
	<jstl:if test="${command != 'create'}">
		<h4><acme:message code="investor.message.form.userName"/> <acme:print value="${userName}"/></h4>
		<acme:form-moment code="investor.message.form.label.creationMoment" path="creationMoment"/>
	</jstl:if>
	
	<acme:form-textbox code="investor.message.form.label.title" path="title"/>
	<acme:form-textarea code="investor.message.form.label.tags" path="tags"/>
	<acme:form-textarea code="investor.message.form.label.body" path="body"/>
	
	<jstl:if test="${command == 'create'}">
		<acme:form-checkbox code="investor.message.form.label.checkbox" path="checkbox"/>
	<acme:form-submit code= "investor.message.form.button.create" action= "/investor/message/create?forumId=${forumId}"/>
	</jstl:if>
		
  	<acme:form-return code="investor.message.form.button.return"/>
</acme:form>