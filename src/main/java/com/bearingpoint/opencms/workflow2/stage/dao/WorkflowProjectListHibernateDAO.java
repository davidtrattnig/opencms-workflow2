/**
 * 
 */
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
