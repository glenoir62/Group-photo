package com.glenoir.plugins.web;

import com.glenoir.plugins.search.UserDisplayTerms;
import com.glenoir.plugins.search.UserSearch;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;


/**
 * @author guillaumelenoir
 * GroupPhoto controller
 */
public class GroupPhotoController extends MVCPortlet {


	/**
	 * GroupPhotoController Logger.
	 */
	protected static Log LOGGER = LogFactoryUtil.getLog(GroupPhotoController.class);
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletURL iteratorURL = renderResponse.createRenderURL();
		
		// create search container
		SearchContainer<User> searchContainer = new UserSearch(renderRequest, iteratorURL);
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		List<User> users = new ArrayList<User>();
		int total = 0;
		
		int start = searchContainer.getStart();
		int end = searchContainer.getEnd();
		PortletURL portletDirectoryURL = null;
		try {
			// create portletUrl
			portletDirectoryURL = PortletURLFactoryUtil.create(
				renderRequest, PortletKeys.DIRECTORY, PortalUtil.getControlPanelPlid(renderRequest),
				PortletRequest.RENDER_PHASE);
			
			portletDirectoryURL.setWindowState(LiferayWindowState.POP_UP);
			portletDirectoryURL.setPortletMode(PortletMode.VIEW);
			portletDirectoryURL.setParameter("struts_action", "/directory/view_user");
			portletDirectoryURL.setParameter("tabs1Names", "Info");
			Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

			// create search context
			SearchContext searchContext = SearchContextFactory.getInstance(PortalUtil.getHttpServletRequest(renderRequest));
			UserDisplayTerms displayTerms = (UserDisplayTerms) searchContainer.getDisplayTerms();
			searchContext.setStart(start);
			searchContext.setEnd(end);

			if (displayTerms.isAdvancedSearch()) {
				searchContext.setAndSearch(displayTerms.isAndOperator());

			}
			else {
				searchContext.setKeywords(displayTerms.getKeywords());
			}
			searchContext.setSorts(new Sort(UserDisplayTerms.LAST_NAME, false));
			
			QueryConfig queryConfig = new QueryConfig();

			queryConfig.setHighlightEnabled(true);

			searchContext.setQueryConfig(queryConfig);

			Hits hits = indexer.search(searchContext);

			PortletURL hitURL = renderResponse.createRenderURL();

			List<SearchResult> searchResultsList = SearchResultUtil.getSearchResults(hits, themeDisplay.getLocale(), hitURL);
			total = hits.getLength();
			if (total > 0) {
				for (SearchResult searchResult : searchResultsList) {
					User user = UserLocalServiceUtil.getUser(searchResult.getClassPK());
					users.add(user);
				}
			}
		}
		catch (SearchException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e);
			}
			LOGGER.error("SearchException : impossible to searh searchUserContainer");
		}
		catch (PortalException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e);
			}
			LOGGER.error("PortalException : impossible to search users");
		}
		catch (SystemException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e);
			}
			LOGGER.error("PortalException : impossible to search users");
		}

		searchContainer.setTotal(total);
		searchContainer.setResults(users);
		renderRequest.setAttribute("searchUserContainer", searchContainer);
		renderRequest.setAttribute("portletDirectoryURL", portletDirectoryURL);
		super.doView(renderRequest, renderResponse);
	}

}
