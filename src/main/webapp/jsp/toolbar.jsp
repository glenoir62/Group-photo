<%@page import="com.glenoir.plugins.search.UserDisplayTerms"%>
<%@page import="javax.portlet.WindowState"%>
<%@include file="/jsp/init.jsp"%>

<c:set value="${searchUserContainer.displayTerms}" var="displayTerms" />

<aui:nav-bar>
	<aui:nav-bar-search cssClass="pull-right">
		<div class="form-search">
			<liferay-ui:search-toggle
				autoFocus="<%= liferayPortletRequest.getWindowState().equals(WindowState.MAXIMIZED) %>"
				buttonLabel="search" displayTerms="${displayTerms}"
				id="<%= renderResponse.getNamespace() %>">
				<br>
				<br>
				<aui:fieldset>

					<aui:input name="<%= UserDisplayTerms.SCREEN_NAME %>" size="20" type="text" value="${displayTerms.getScreenName()}" />
					
					<aui:input name="<%= UserDisplayTerms.FIRST_NAME %>" size="20" type="text" value="${displayTerms.getFirstName()}" />

					<aui:input name="<%= UserDisplayTerms.LAST_NAME %>" size="20" type="text" value="${displayTerms.getLastName()}" />
					
					<aui:input name="<%= UserDisplayTerms.EMAIL_ADDRESS %>" size="20" type="text" value="${displayTerms.getEmailAddress()}" />
					
				</aui:fieldset>
			</liferay-ui:search-toggle>
		</div>
	</aui:nav-bar-search>
</aui:nav-bar>

