<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@include file="/jsp/init.jsp" %>

<portlet:renderURL var="searchURL">
	<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(renderRequest) %>" />
	<portlet:param name="searchView" value="true" />
</portlet:renderURL>

<aui:form action="${searchURL}" method="post" name="fm">
	<liferay-util:include page="/jsp/toolbar.jsp" servletContext="<%= application %>" />
</aui:form>

<div id="<portlet:namespace />users">
	<liferay-util:include page="/jsp/view_search.jsp" servletContext="<%= application %>" />
</div>