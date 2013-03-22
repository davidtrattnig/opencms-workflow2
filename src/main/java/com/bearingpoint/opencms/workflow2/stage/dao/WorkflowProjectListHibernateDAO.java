/* ***************************************************************************
 * This library is part of INFONOVA OpenCms Modules.
 * Common Modules for the Open Source Content Management System: OpenCms.
 *
 * Copyright (c) 2007 INFONOVA GmbH, Austria.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about INFONOVA GmbH, Austria, please
 * see the company website: http://www.infonova.at/
 *
 * For further information about INFONOVA OpenCms Modules, please see the
 * project website: http://sourceforge.net/projects/bp-cms-commons/
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *****************************************************************************/

package com.bearingpoint.opencms.workflow2.stage.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bearingpoint.opencms.workflow2.stage.NoProjectsAvailableException;
import com.bearingpoint.opencms.workflow2.stage.domain.WorkflowProject;

/**
 * WorkflowProjectListHibernateDAO
 * <p>
 * @author David.Trattnig
 *
 */
public class WorkflowProjectListHibernateDAO extends HibernateDaoSupport implements I_WorkflowProjectListDAO {

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.dao.I_WorkflowProjectListDAO#retrieveAllWorkflowProjects()
	 */
	public List<WorkflowProject> retrieveAllWorkflowProjects() throws NoProjectsAvailableException {

        String query = "FROM WorkflowProject ORDER BY stage";        
        List<WorkflowProject> projectList = getHibernateTemplate().find(query);
        
        if (projectList.isEmpty()) {
        	throw new NoProjectsAvailableException("Could not find any project defined within the workflow");
        } 
        
        return projectList;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.dao.I_WorkflowProjectListDAO#updateProjectWorkflow(java.util.List)
	 */
	public void updateProjectWorkflow(List<WorkflowProject> projects) {

		try {
			removeAllWorkflowProjects();
		} catch (NoProjectsAvailableException e) {}
		
		cleanIDs(projects);
		for (WorkflowProject project : projects) {
			getHibernateTemplate().persist(project);
		}
	}

	
	/**
	 * Removes all projects of the workflow.
	 */
	private void removeAllWorkflowProjects() throws NoProjectsAvailableException {
		
		List<WorkflowProject> projectList = retrieveAllWorkflowProjects();
		
		if (projectList == null || projectList.size()<=0) {
			throw new NoProjectsAvailableException("there are no workflow projects defined!");
		}
		
		for (WorkflowProject wp : projectList) {
			getHibernateTemplate().delete(wp);
		}
	}
	
	/**
	 * Hack to avoid Hibernate exception -
	 * The IDs of the read items are still available when writing them.
	 * This may cause problems when hibernate tries to look up these 
	 * 'valid' objects.
	 * 
	 *  Exception thrown:
	 * org.hibernate.PersistentObjectException: detached entity passed to persist:
	 * 
	 * @param projects
	 * @return
	 */
	private List<WorkflowProject> cleanIDs(List<WorkflowProject> projects) {
		
		for (WorkflowProject p : projects) {
			p.setId(null);
		}		
		return projects;
	}
	
	
}
