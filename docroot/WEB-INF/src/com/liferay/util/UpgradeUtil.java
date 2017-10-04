package com.liferay.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

public class UpgradeUtil {

	private static Log log = LogFactoryUtil.getLog(UpgradeUtil.class);
	public static void upgrade() throws SystemException{
		 Role siteMember;
	    
		log.warn("--- UPGRADING LMS TO 3.2 ");

		 
		 String createScormTables = "CREATE TABLE sco_scormcontent (  uuid_ VARCHAR(75) NULL DEFAULT NULL, "+
				 					"scormId BIGINT(20) NOT NULL,  companyId BIGINT(20) NULL DEFAULT NULL, "+
				 					"groupId BIGINT(20) NULL DEFAULT NULL,  userId BIGINT(20) NULL DEFAULT NULL, "+
				 					"status INT(11) NULL DEFAULT NULL,  statusByUserId BIGINT(20) NULL DEFAULT NULL, "+
				 					"statusByUserName VARCHAR(75) NULL DEFAULT NULL,  statusDate DATETIME NULL DEFAULT NULL, "+
				 					"title VARCHAR(75) NULL DEFAULT NULL,  description LONGTEXT NULL DEFAULT NULL, "+
				 					"index_ VARCHAR(75) NULL DEFAULT NULL,  ciphered TINYINT(4) NULL DEFAULT NULL, "+
				 					"PRIMARY KEY (scormId), UNIQUE INDEX IX_5DEB5C3B (uuid_,  groupId), "+
				 					"INDEX IX_78C3B643 (companyId), INDEX IX_153AF185 (groupId), "+
				 					"INDEX IX_884753FF (userId), INDEX IX_9EF32F8B (userId, groupId), "+
				 					"INDEX IX_E6F9214F (uuid_) ) COLLATE='utf8_general_ci' ENGINE=InnoDB ;";
		 String insertScormContent = "insert into sco_scormcontent select * from lms_scormcontent;";
		 
		 
		 String alterLearningActivity = "ALTER TABLE `lms_learningactivity` " +
				 	"ADD COLUMN `linkedActivityId` BIGINT(20) NULL DEFAULT NULL AFTER `commentsActivated`;";

		 String alterCourseLinked = "ALTER TABLE `lms_course` "+
		 			"ADD COLUMN `isLinked` TINYINT(4) NULL DEFAULT NULL AFTER `goodbyeSubject`;";

		 String alterCourseStartDate = "ALTER TABLE `lms_course` "+
				 	"ADD COLUMN `executionStartDate` DATETIME NULL DEFAULT NULL AFTER `isLinked`;";

		 String alterCourseEndDate = "ALTER TABLE `lms_course` "+
				 	"ADD COLUMN `executionEndDate` DATETIME NULL DEFAULT NULL AFTER `executionStartDate`;";
		 
		 

		 //Execute SQL Queries
		 //Create Scorm Tables
		 log.warn("Creating Scorm Tables ");
		 DB db = DBFactoryUtil.getDB();
		 try {
			db.runSQL(createScormTables);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//Insert Scorm
		 log.warn("Insert Scorm content "); 
		try {
			db.runSQL(insertScormContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 log.warn("Alter table activity linked ");
		//Activity linked
		try {
			db.runSQL(alterLearningActivity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Course Linked
		 log.warn("Alter table course linked ");
		 try {
			db.runSQL(alterCourseLinked);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Course Start Date
		log.warn("Alter table course start execution date ");
		try {
			db.runSQL(alterCourseStartDate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Course End Date
		log.warn("Alter table course end execution date ");
		try {
			db.runSQL(alterCourseEndDate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//Establecer las fechas de incio y fin
		List<Course> allCourses = CourseLocalServiceUtil.getCourses(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		boolean changesInCourse;
		for(Course course : allCourses){
			changesInCourse = false;
			if(course.getExecutionStartDate()==null){
				course.setExecutionStartDate(CourseLocalServiceUtil.getFirstModuleDateInCourse(course.getCourseId()));
				changesInCourse=true;
			}
			if(course.getExecutionEndDate()==null){
				course.setExecutionEndDate(CourseLocalServiceUtil.getLastModuleDateInCourse(course.getCourseId()));
				changesInCourse=true;
			}
			if(changesInCourse){
				CourseLocalServiceUtil.updateCourse(course);
			}	
		}
		
		log.warn("--- CREATING ACCESS PERMISSION TO MODULE ");
		
		List<String> actionIds = new ArrayList<String>();
		actionIds.add("ACCESS");
		ResourceActionLocalServiceUtil.checkResourceActions(
				Module.class.getName(), actionIds);
		

		
		log.warn("--- SETTING ACCESS PERMISSION TO COURSE ADMINISTRATION");
	    List<Company> companys = CompanyLocalServiceUtil.getCompanies();
	    List<Course> listCourses = null;
	    String title = null;
	    Map<Locale, String> titleMap = null;
	    Iterator<Entry<Locale,String>> iterator = null;
	    boolean finded = false;
	    Entry<Locale,String> entry = null;
	    AssetEntry assetEntry = null;
	    for(Company company : companys){
	    	try{
	    		 Role courseAdministrator = RoleLocalServiceUtil.getRole(company.getCompanyId(), "Administrador de cursos");
		    	 if(courseAdministrator!=null){
		    		  ResourcePermissionLocalServiceUtil.setResourcePermissions(company.getCompanyId(), 
				     			Module.class.getName(),ResourceConstants.SCOPE_COMPANY, String.valueOf(company.getCompanyId()),  courseAdministrator.getRoleId(),  new String[]{"ACCESS","VIEW","DELETE", "ADD_LACT","UPDATE","PERMISSIONS","SOFT_PERMISSIONS"});
		    	  }
	    	 }catch(Exception e){
	    		 log.warn("No hay Administrador de cursos");
	    	}
	    	//Ponemos el idioma por defecto a los cursos que no lo tienen
	    	listCourses = CourseLocalServiceUtil.getCourses(company.getCompanyId());
	    	if(listCourses != null && listCourses.size() > 0){
	    		for(Course course: listCourses){
	    			try {
	    				assetEntry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId());
	    				titleMap = course.getTitleMap();
						log.debug("courseId: " + course.getCourseId());
						//Si no tienen el idioma por defecto relleno, se lo cargamos
						log.debug("idioma por defect contenido: " + titleMap.containsKey(company.getLocale()));
						if(!titleMap.containsKey(company.getLocale()) || Validator.isNull(titleMap.get(company.getLocale()))){
							//Buscamos idioma que no esté vacio
							iterator = titleMap.entrySet().iterator();
							finded = false;
							while(!finded && iterator.hasNext()){
								entry = iterator.next();
								if(Validator.isNotNull(entry.getValue())){
									title = entry.getValue();
									finded = true;
								}
							}
							
							log.debug("Cargamos el titulo: " + title);
							titleMap.put(company.getLocale(), title);
						}
						course.setTitleMap(titleMap, company.getLocale());
						assetEntry.setTitleMap(titleMap, company.getLocale());
						CourseLocalServiceUtil.updateCourse(course);
					} catch (PortalException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e){
						e.printStackTrace();
					}
	    			
	    		}
	    	}	  
	    }
	    
	    
	    log.warn("--- SETTING ACCESS PERMISSION TO MODULES ");
	    List<Module> modules = ModuleLocalServiceUtil.getModules(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		for(Module module : modules){
			try{
				log.warn("--- MODULE  "+ module.getModuleId());
			    siteMember = RoleLocalServiceUtil.fetchRole(module.getCompanyId(), RoleConstants.SITE_MEMBER);
				if(siteMember!=null){
					ResourcePermissionLocalServiceUtil.setResourcePermissions(module.getCompanyId(), 
			     			Module.class.getName(),ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(module.getModuleId()),  siteMember.getRoleId(),  new String[]{"ACCESS","VIEW"});
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		 log.warn("--- END SETTING ACCESS PERMISSION TO MODULES ");
			
			
	}
	
	
}