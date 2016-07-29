package com.liferay.lms;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class LmsConfig
 */
public class LmsConfig extends MVCPortlet {
	
	private final long DEFAULT_USERS_RESULTS = 1000;
	
	public void changeSettings(ActionRequest request , ActionResponse response) throws Exception
	{
		
		String redirect = ParamUtil.get(request, "redirect", "");
		
		String sitetemplates=StringUtil.merge(request.getParameterMap().get( "lmsTemplatesCheckbox"));
		String activitytypes=StringUtil.merge(request.getParameterMap().get( "activitiesCheckbox"));
		String calificationTypes=StringUtil.merge(request.getParameterMap().get( "calificationTypesCheckbox"));
		String courseEvalsTypes=StringUtil.merge(request.getParameterMap().get( "courseEvalsCheckbox"));
		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		Long usersResults = (ParamUtil.getLong(request, "usersResults", DEFAULT_USERS_RESULTS));
		
		boolean isDebugScorm = ParamUtil.getBoolean(request, "debugScorm");
		
		boolean hasAPILicence = ParamUtil.getBoolean(request, "hasAPILicence");
	
		boolean showHideActivity = ParamUtil.getBoolean(request, "showHideActivity", false);
		
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
		prefs.setLmsTemplates(sitetemplates);
		prefs.setDebugScorm(isDebugScorm);
		prefs.setActivities(activitytypes);
		prefs.setCourseevals(courseEvalsTypes);
		prefs.setScoretranslators(calificationTypes);
		prefs.setUsersResults(usersResults > 0 ? usersResults : DEFAULT_USERS_RESULTS);
		prefs.setHasAPILicence(hasAPILicence);
		prefs.setShowHideActivity(showHideActivity);
		LmsPrefsLocalServiceUtil.updateLmsPrefs(prefs);
		
		if (Validator.isNotNull(redirect)) {
			response.sendRedirect(redirect);
		}

	}
}
