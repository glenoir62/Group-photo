<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.util.PortletKeys"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileShortcut"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@include file="/jsp/init.jsp" %>

<liferay-ui:search-container id="users" searchContainer="${searchUserContainer}" var="searchUserContainer">
	<liferay-ui:search-container-results
		results="${searchUserContainer.results}"
		total="${searchUserContainer.total}" 
	/>
	<c:if test="${searchUserContainer.results.size()>0 && searchUserContainer.delta >= 20}">
		<liferay-ui:search-paginator searchContainer="${searchUserContainer}" />
	</c:if>
	<aui:row cssClass="span10 offset1">
		<liferay-ui:search-container-row 
			className="com.liferay.portal.model.User"
			keyProperty="userId"
			modelVar="user2"
		>	
			<liferay-ui:app-view-entry
				displayStyle="icon"
				locked="false"
				cssClass="user-photo-group"
				showCheckbox='true'
				rowCheckerId="${user2.userId}"
				rowCheckerName="<%= DLFileShortcut.class.getSimpleName() %>"
				status="0"
				thumbnailDivStyle="<%= DLUtil.getThumbnailStyle(false, 4) %>"
				thumbnailSrc="${user2.getPortraitURL(themeDisplay)}"
				thumbnailStyle="<%= DLUtil.getThumbnailStyle() %>"
				title="${user2.getFullName()}"
				url="#"
			/>
		
		</liferay-ui:search-container-row>
	</aui:row>
	<div style="clear:both;">
	<liferay-ui:search-paginator searchContainer="${searchUserContainer}" />
</liferay-ui:search-container>

<aui:script use="aui-base">

A.all('#<portlet:namespace/>users .user-photo-group').on('click', function(event){
	event.preventDefault();
	var currentNode = event.currentTarget.one('input[type=checkbox]');
	var userId = currentNode.get('value');
	
	if (userId) {
		var portletURL = '${portletDirectoryURL}';
		var namespace = Liferay.Util.getPortletNamespace('<%= PortletKeys.DIRECTORY %>');
		portletURL = portletURL + '&' + namespace + 'p_u_i_d' + '=' + userId;
		
		Liferay.Util.openWindow (
	        {
     			dialog: {
					destroyOnHide: true,
					width: 650,
					height: 450
				},
				title: Liferay.Language.get("User"),
	            id: '<portlet:namespace/>window-directory-user',
	            uri: portletURL
	        }
	    );
	}
});

</aui:script>

