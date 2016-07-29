/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the LmsPrefs service. Represents a row in the &quot;Lms_LmsPrefs&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.lms.model.impl.LmsPrefsModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.lms.model.impl.LmsPrefsImpl}.
 * </p>
 *
 * @author TLS
 * @see LmsPrefs
 * @see com.liferay.lms.model.impl.LmsPrefsImpl
 * @see com.liferay.lms.model.impl.LmsPrefsModelImpl
 * @generated
 */
public interface LmsPrefsModel extends BaseModel<LmsPrefs> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a lms prefs model instance should use the {@link LmsPrefs} interface instead.
	 */

	/**
	 * Returns the primary key of this lms prefs.
	 *
	 * @return the primary key of this lms prefs
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this lms prefs.
	 *
	 * @param primaryKey the primary key of this lms prefs
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the company ID of this lms prefs.
	 *
	 * @return the company ID of this lms prefs
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this lms prefs.
	 *
	 * @param companyId the company ID of this lms prefs
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the teacher role of this lms prefs.
	 *
	 * @return the teacher role of this lms prefs
	 */
	public long getTeacherRole();

	/**
	 * Sets the teacher role of this lms prefs.
	 *
	 * @param teacherRole the teacher role of this lms prefs
	 */
	public void setTeacherRole(long teacherRole);

	/**
	 * Returns the editor role of this lms prefs.
	 *
	 * @return the editor role of this lms prefs
	 */
	public long getEditorRole();

	/**
	 * Sets the editor role of this lms prefs.
	 *
	 * @param editorRole the editor role of this lms prefs
	 */
	public void setEditorRole(long editorRole);

	/**
	 * Returns the lms templates of this lms prefs.
	 *
	 * @return the lms templates of this lms prefs
	 */
	@AutoEscape
	public String getLmsTemplates();

	/**
	 * Sets the lms templates of this lms prefs.
	 *
	 * @param lmsTemplates the lms templates of this lms prefs
	 */
	public void setLmsTemplates(String lmsTemplates);

	/**
	 * Returns the activities of this lms prefs.
	 *
	 * @return the activities of this lms prefs
	 */
	@AutoEscape
	public String getActivities();

	/**
	 * Sets the activities of this lms prefs.
	 *
	 * @param activities the activities of this lms prefs
	 */
	public void setActivities(String activities);

	/**
	 * Returns the courseevals of this lms prefs.
	 *
	 * @return the courseevals of this lms prefs
	 */
	@AutoEscape
	public String getCourseevals();

	/**
	 * Sets the courseevals of this lms prefs.
	 *
	 * @param courseevals the courseevals of this lms prefs
	 */
	public void setCourseevals(String courseevals);

	/**
	 * Returns the scoretranslators of this lms prefs.
	 *
	 * @return the scoretranslators of this lms prefs
	 */
	@AutoEscape
	public String getScoretranslators();

	/**
	 * Sets the scoretranslators of this lms prefs.
	 *
	 * @param scoretranslators the scoretranslators of this lms prefs
	 */
	public void setScoretranslators(String scoretranslators);

	/**
	 * Returns the users results of this lms prefs.
	 *
	 * @return the users results of this lms prefs
	 */
	public long getUsersResults();

	/**
	 * Sets the users results of this lms prefs.
	 *
	 * @param usersResults the users results of this lms prefs
	 */
	public void setUsersResults(long usersResults);

	/**
	 * Returns the debug scorm of this lms prefs.
	 *
	 * @return the debug scorm of this lms prefs
	 */
	public boolean getDebugScorm();

	/**
	 * Returns <code>true</code> if this lms prefs is debug scorm.
	 *
	 * @return <code>true</code> if this lms prefs is debug scorm; <code>false</code> otherwise
	 */
	public boolean isDebugScorm();

	/**
	 * Sets whether this lms prefs is debug scorm.
	 *
	 * @param debugScorm the debug scorm of this lms prefs
	 */
	public void setDebugScorm(boolean debugScorm);

	/**
	 * Returns the has a p i licence of this lms prefs.
	 *
	 * @return the has a p i licence of this lms prefs
	 */
	public boolean getHasAPILicence();

	/**
	 * Returns <code>true</code> if this lms prefs is has a p i licence.
	 *
	 * @return <code>true</code> if this lms prefs is has a p i licence; <code>false</code> otherwise
	 */
	public boolean isHasAPILicence();

	/**
	 * Sets whether this lms prefs is has a p i licence.
	 *
	 * @param hasAPILicence the has a p i licence of this lms prefs
	 */
	public void setHasAPILicence(boolean hasAPILicence);

	/**
	 * Returns the show hide activity of this lms prefs.
	 *
	 * @return the show hide activity of this lms prefs
	 */
	public boolean getShowHideActivity();

	/**
	 * Returns <code>true</code> if this lms prefs is show hide activity.
	 *
	 * @return <code>true</code> if this lms prefs is show hide activity; <code>false</code> otherwise
	 */
	public boolean isShowHideActivity();

	/**
	 * Sets whether this lms prefs is show hide activity.
	 *
	 * @param showHideActivity the show hide activity of this lms prefs
	 */
	public void setShowHideActivity(boolean showHideActivity);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(LmsPrefs lmsPrefs);

	public int hashCode();

	public CacheModel<LmsPrefs> toCacheModel();

	public LmsPrefs toEscapedModel();

	public String toString();

	public String toXmlString();
}