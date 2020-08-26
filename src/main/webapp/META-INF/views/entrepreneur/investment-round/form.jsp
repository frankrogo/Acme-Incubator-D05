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

<acme:form>

	<jstl:choose>
		<jstl:when test="${finalMode == 'true'&& command == 'show' && errors==null}">
			<jstl:set var="option" value="true" />
		</jstl:when>
		<jstl:otherwise>
			<jstl:set var="option" value="false" />
		</jstl:otherwise>
	</jstl:choose>

	
	<jstl:if test="${command != 'create'}">
		<acme:form-textbox code="entrepreneur.investment-round.form.label.ticker" placeholder="SSS-YY-NNNNNN" path="ticker" readonly="true"/>
	</jstl:if>
	<acme:form-textbox code="entrepreneur.investment-round.form.label.title" path="title" readonly="${option}"/>
	<jstl:if test="${command != 'create'}">
		<acme:form-moment code="entrepreneur.investment-round.form.label.creationMoment" path="creationMoment"  readonly="true"/>
	</jstl:if>
	<acme:form-textarea code="entrepreneur.investment-round.form.label.round" path="round" placeholder="SEED|ANGEL|SERIES-A|SERIES-B|SERIES-C|BRIDGE" readonly="${option}"/>
	<acme:form-textarea code="entrepreneur.investment-round.form.label.description" path="description"  readonly="${option}"/>
	<acme:form-money code="entrepreneur.investment-round.form.label.moneyAmount" path="moneyAmount" readonly="${option}"/>
	<acme:form-url code="entrepreneur.investment-round.form.label.moreInfo" path="moreInfo" readonly="${option}" />
	<acme:form-checkbox code="entrepreneur.investment-round.form.label.finalMode" path="finalMode" readonly="${option}"/>
	
	
	<jstl:if test="${command == 'create'}">
	<acme:form-textarea code="entrepreneur.investment-round.form.label.titleActivity" path="titleActivity"/>
	<jstl:if test="${!es}">
		<acme:form-textbox code="entrepreneur.investment-round.form.label.deadLineActivity" path="deadLineActivity" placeholder="yyyy/mm/dd hh:mm"/>
	</jstl:if>
	<jstl:if test="${es}">
		<acme:form-textbox code="entrepreneur.investment-round.form.label.deadLineActivity" path="deadLineActivity" placeholder="dd/mm/yyyy hh:mm"/>
	</jstl:if>
	<acme:form-fecha code="entrepreneur.investment-round.form.label.deadLineActivity" path="fecha" />
	<acme:form-textarea code="entrepreneur.investment-round.form.label.budgetActivity" placeholder="123.456,78 EUR" path="budgetActivity"/>
	<br>
	<acme:form-textarea code="entrepreneur.investment-round.form.label.titleForum" path="titleForum"/>
	
	</jstl:if>
	<acme:form-submit test="${command == 'create' }" code="entrepreneur.investment-round.form.button.create" action="/entrepreneur/investment-round/create"/>
	<acme:form-submit test="${command == 'show' && finalMode == 'false'}" code="entrepreneur.investment-round.form.button.update" action="/entrepreneur/investment-round/update"/>
	<acme:form-submit test="${command == 'update' }" code="entrepreneur.investment-round.form.button.update" action="/entrepreneur/investment-round/update"/>
	<jstl:if test="${command == 'show'}">
	<jstl:if test="${!finalmode}">
	<acme:form-submit code="entrepreneur.investment-round.form.button.activities-create" action="/entrepreneur/activity/create?investmentRoundId=${investmentRoundId}" method="get"/>
	</jstl:if>
	<acme:form-submit code="entrepreneur.investment-round.form.button.activities" action="/entrepreneur/activity/list-by-ir?investmentRoundId=${investmentRoundId}" method="get"/>
  	</jstl:if>
  		<jstl:if test="${command == 'show' && haveApplications==true}">
	<acme:form-submit code="entrepreneur.investment-round.form.button.delete" action="/entrepreneur/investment-round/delete"/>
  	</jstl:if>
  	<acme:form-return code="entrepreneur.investment-round.form.button.return"/>
</acme:form>