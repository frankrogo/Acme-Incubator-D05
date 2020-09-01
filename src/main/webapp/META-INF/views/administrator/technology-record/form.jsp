<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<acme:form>
	<acme:form-textbox code="administrator.technology-record.form.label.title" path="title"/>
	
	<h5><acme:message code="administrator.technology-record.form.label.sector"  /></h5>
       <select name="sector">
          <c:forEach var="item" items="${params}">
            <option value="${item}"${item == sector ? 'selected':''}>${item}</option>
          </c:forEach>
        </select>
        <br>
	
	<acme:form-textbox code="administrator.technology-record.form.label.inventorName" path="inventorName"/>
	<acme:form-textarea code="administrator.technology-record.form.label.description" path="description"/>
	<acme:form-url code="administrator.technology-record.form.label.website" path="website"/>
	<acme:form-textbox code="administrator.technology-record.form.label.email" path="email"/>
	<acme:form-textbox code="administrator.technology-record.form.label.stars" path="stars"/>
	
	<acme:form-select code="administrator.technology-record.form.label.isOpenSource" path="isOpenSource">
			<acme:form-option code="administrator.technology-record.form.label.isOpenSource.true" value="true" />
			<acme:form-option code="administrator.technology-record.form.label.isOpenSource.false" value="false" />
	</acme:form-select>
	
	<jstl:if test="${command == 'update'}">
		<acme:form-textbox readonly="true" code="administrator.technology-record.form.label.source" path="source"/>
	</jstl:if>
	
	<jstl:if test="${command == 'show'}">
		<acme:form-textbox readonly="true" code="administrator.technology-record.form.label.source" path="source"/>
	</jstl:if>
	
	<jstl:if test="${command == 'delete'}">
		<acme:form-textbox code="administrator.technology-record.form.label.source" path="source"/>
	</jstl:if>
	
	<acme:form-submit test="${command == 'create'}" code="administrator.technology-record.form.button.create"
		action="/administrator/technology-record/create"/>
	<acme:form-submit test="${command == 'update'}" code="administrator.technology-record.form.button.update"
		action="/administrator/technology-record/update"/>
	<acme:form-submit test="${command == 'delete'}" code="administrator.technology-record.form.button.delete"
		action="/administrator/technology-record/delete"/>
		
	<acme:form-submit test="${command == 'show'}" code="administrator.technology-record.form.button.update"
		action="/administrator/technology-record/update"/>
	<acme:form-submit test="${command == 'show'}" code="administrator.technology-record.form.button.delete"
		action="/administrator/technology-record/delete"/>
		
	<acme:form-return code="administrator.technology-record.form.button.return"/>
</acme:form>