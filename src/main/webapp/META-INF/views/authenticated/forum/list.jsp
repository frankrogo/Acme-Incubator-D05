
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<acme:list>
	<acme:list-column code="authenticated.forum.list.label.title" path="title" width="20%"/>
	<acme:list-column code="authenticated.forum.list.label.investmentRoundTicker" path="investmentRoundTicker" width="20%"/>
</acme:list>


