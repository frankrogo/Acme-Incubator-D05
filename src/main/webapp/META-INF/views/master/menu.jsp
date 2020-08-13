<%--
- menu.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.entities.roles.Provider,acme.entities.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.frankrogo" action="https://es.wikipedia.org/wiki/Wikipedia:Portada"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.serperrui1" action="https://www.realbetisbalompie.es//"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.perea-bulletin.list" action="/anonymous/perea-bulletin/list"/>
	 		<acme:menu-suboption code="master.menu.anonymous.perea-bulletin.create" action="/anonymous/perea-bulletin/create"/>
	 		<acme:menu-separator/>
	 		<acme:menu-suboption code="master.menu.anonymous.rodriguez-bulletin.list" action="/anonymous/rodriguez-bulletin/list"/>
	 		<acme:menu-suboption code="master.menu.anonymous.rodriguez-bulletin.create" action="/anonymous/rodriguez-bulletin/create"/>
	 		<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.list-notice" action="/anonymous/notice/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.list-technology-record" action="/anonymous/technology-record/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.list-tool-record" action="/anonymous/tool-record/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shutdown" action="/master/shutdown"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-notice" action="/administrator/notice/list" />
			<acme:menu-suboption code="master.menu.administrator.create-notice" action="/administrator/notice/create" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-technology-record" action="/administrator/technology-record/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-technology-record" action="/administrator/technology-record/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.list-tool-record" action="/administrator/tool-record/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-tool-record" action="/administrator/tool-record/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.list-inquiry" action="/administrator/inquiry/list" />
			<acme:menu-suboption code="master.menu.administrator.create-inquiry" action="/administrator/inquiry/create" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-overture" action="/administrator/overture/list" />
			<acme:menu-suboption code="master.menu.administrator.create-overture" action="/administrator/overture/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.list-challenge" action="/administrator/challenge/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-challenge" action="/administrator/challenge/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.configuration" action="/administrator/configuration/show" />	
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.list-notice" action="/authenticated/notice/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.list-technology-record" action="/authenticated/technology-record/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.list-tool-record" action="/authenticated/tool-record/list"/>
			<acme:menu-separator/>
		    <acme:menu-suboption code="master.menu.authenticated.list-inquiry" action="/authenticated/inquiry/list" />
			<acme:menu-separator/>	
		    <acme:menu-suboption code="master.menu.authenticated.list-overture" action="/authenticated/overture/list" />
			<acme:menu-separator/> 
	      	<acme:menu-suboption code="master.menu.authenticated.list-challenge" action="/authenticated/challenge/list"/>
	      	<acme:menu-separator/> 
	      	<acme:menu-suboption code="master.menu.authenticated.list-investment-round" action="/authenticated/investment-round/list" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.entrepreneur" access="hasRole('Entrepreneur')">
			<acme:menu-suboption code="master.menu.entrepreneur.investment-round.list-mine" action="/entrepreneur/investment-round/list-mine" />
			<acme:menu-suboption code="master.menu.entrepreneur.investment-round.create" action="/entrepreneur/investment-round/create" />
			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.entrepreneur.application.list-mine" action="/entrepreneur/application/list-mine" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.entrepreneur.forum.list-mine" action="/entrepreneur/forum/list-mine" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.investor" access="hasRole('Investor')">
			<acme:menu-suboption code="master.menu.investor.application.list-mine" action="/investor/application/list-mine" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.investor.forum.list-mine" action="/investor/forum/list-mine" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.bookkeeper" access="hasRole('Bookkeeper')">
			<acme:menu-suboption code="master.menu.bookkeeper.accounting-record.list-mine" action="/bookkeeper/accounting-record/list-mine" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.bookkeeper.investment-round.list-mine" action="/bookkeeper/investment-round/list-mine" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.bookkeeper.investment-round.list-not-mine" action="/bookkeeper/investment-round/list-not-mine" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-entrepreneur" action="/authenticated/entrepreneur/create" access="!hasRole('Entrepreneur')"/>
			<acme:menu-suboption code="master.menu.user-account.entrepreneur" action="/authenticated/entrepreneur/update" access="hasRole('Entrepreneur')"/>
			<acme:menu-suboption code="master.menu.user-account.become-investor" action="/authenticated/investor/create" access="!hasRole('Investor')"/>
			<acme:menu-suboption code="master.menu.user-account.investor" action="/authenticated/investor/update" access="hasRole('Investor')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

