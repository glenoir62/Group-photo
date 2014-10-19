package com.glenoir.plugins.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author guillaumelenoir
 *
 */
public class UserSearch extends SearchContainer<User> {
	
	/**
	 * Default empty result message
	 */
	public static final String EMPTY_RESULTS_MESSAGE = "no-users-were-found";
	
	/**
	 * Default cur param
	 */
	public static final String DEFAULT_CUR_PARAM = "curUsers";

	/**
	 * UserSearch
	 * 
	 * @param portletRequest
	 * @param iteratorURL
	 */
	public UserSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		this(portletRequest, DEFAULT_CUR_PARAM, iteratorURL);
	}

	/**
	 * UserSearch
	 * 
	 * @param portletRequest
	 * @param curParam
	 * @param iteratorURL
	 */
	public UserSearch (
		PortletRequest portletRequest, String curParam,
		PortletURL iteratorURL) {

		super(
			portletRequest, new UserDisplayTerms(portletRequest),
			new UserSearchTerms(portletRequest), curParam,
			ParamUtil.getInteger(portletRequest, DEFAULT_CUR_PARAM),
			ParamUtil.getInteger(portletRequest, DEFAULT_DELTA_PARAM, DEFAULT_DELTA),
			iteratorURL, null, EMPTY_RESULTS_MESSAGE);

		UserDisplayTerms displayTerms = (UserDisplayTerms)getDisplayTerms();
		UserSearchTerms searchTerms = (UserSearchTerms)getSearchTerms();

		displayTerms.setStatus(WorkflowConstants.STATUS_APPROVED);
		searchTerms.setStatus(WorkflowConstants.STATUS_APPROVED);
		
		iteratorURL.setParameter(
			UserDisplayTerms.STATUS, String.valueOf(displayTerms.getStatus()));

		iteratorURL.setParameter(
			UserDisplayTerms.EMAIL_ADDRESS, displayTerms.getEmailAddress());
		iteratorURL.setParameter(
			UserDisplayTerms.FIRST_NAME, displayTerms.getFirstName());
		iteratorURL.setParameter(
			UserDisplayTerms.LAST_NAME, displayTerms.getLastName());
		iteratorURL.setParameter(
			UserDisplayTerms.MIDDLE_NAME, displayTerms.getMiddleName());
		iteratorURL.setParameter(
			UserDisplayTerms.ORGANIZATION_ID,
			String.valueOf(displayTerms.getOrganizationId()));
		iteratorURL.setParameter(
			UserDisplayTerms.SCREEN_NAME, displayTerms.getScreenName());

	}
}
