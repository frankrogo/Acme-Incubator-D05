<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<jstl:if test="${command != 'create'}">
	<img src ="${header}" />
	</jstl:if>
	<jstl:if test="${command == 'create'}">
		<acme:form-textarea placeholder="http://www.example1.com , http://www.example2.com " code="administrator.notice.form.label.links" path="links"/>
	</jstl:if>
	<jstl:if test="${command == 'create'}">
	<acme:form-textbox code="administrator.notice.form.label.header" path="header"/>
	</jstl:if>
	
	<acme:form-textbox code="administrator.notice.form.label.title" path="title"/>
	
	<jstl:if test="${command != 'create'}">
		<acme:form-moment code="administrator.notice.form.label.creationMoment" path="creationMoment"/>
	</jstl:if>
	
	<acme:form-moment code="administrator.notice.form.label.deadline" path="deadline"/>
	<acme:form-textarea code="administrator.notice.form.label.body" path="body"/>
	
	<jstl:if test="${command == 'show'}">
	<h6><strong><spring:message code="administrator.notice.form.label.links"></spring:message></strong></h6>
	<ul>
		<jstl:forEach items="${links}" var="link">
			<li>
				<jstl:out value="${link}"></jstl:out>
			</li>	
		</jstl:forEach>
	</ul>
	</jstl:if>
	
	<jstl:if test="${command == 'create'}">
		<acme:form-checkbox code="administrator.notice.form.label.checked" path="checked"/>
	</jstl:if>
	
	<acme:form-submit test="${command == 'create'}" code="administrator.notice.form.button.create"
		action="/administrator/notice/create"/>
		
  	<acme:form-return code="administrator.notice.form.button.return"/>
</acme:form>