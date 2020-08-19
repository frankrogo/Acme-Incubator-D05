<%----%>
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.messenger.list.label.forumName" path="forumName" width="20%"/>
	<acme:list-column code="authenticated.messenger.list.label.authName" path="authName" width="30%"/>
	<acme:list-column code="authenticated.messenger.list.label.ownsTheForum" path="ownsTheForum" width="20%"/>
</acme:list>

<acme:form>
	<acme:form-return code="authenticated.messenger.list.button.return"/>
</acme:form>