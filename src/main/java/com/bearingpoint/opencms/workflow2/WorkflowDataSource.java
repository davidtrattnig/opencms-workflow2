/**
 * 
 */
package com.bearingpoint.opencms.workflow2;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.opencms.configuration.CmsConfigurationManager;
import org.opencms.main.CmsLog;

/**
 * WorkflowDataSource
 * <p>
 * This class holds the database configuration for the workflow tables.
 * By default these tables are stored within the current OpenCms database.
 * The properties are read from the opencms.properties file.
 * <p>
 * @author David.trattnig
 *
 */
public class WorkflowDataSource extends org.springframework.jdbc.datasource.DriverManagerDataSource {

	private static Map data;
	private static final Log LOG = CmsLog.getLog(WorkflowDataSource.class);
	
	public WorkflowDataSource() {

		if (data != null) {
			//TODO check if URL parameters is the same as connection properties??
			setUrl((String) data.get("db.pool.default.jdbcUrl")+(String) data.get("db.pool.default.jdbcUrl.params"));
			setUsername((String) data.get("db.pool.default.user"));
			setPassword((String) data.get("db.pool.default.password"));
			setDriverClassName((String) data.get("db.pool.default.jdbcDriver"));
			LOG.info("WORKFLOW2 DATABASE: properties assigned to hibernate!");
		}
		else {
			/* the workflow module hasn't been initialized yet, but now worries, the springmanager will retry. */
			LOG.info("WORKFLOW2 DATABASE: hibernate init hasn't been successfull in the first round. hope there is another one...");
		}
	}
	
	public static void initDatasourceData(CmsConfigurationManager config) {
		
		data = config.getConfiguration();
		LOG.info("WORKFLOW2 DATABASE: configuration initialized!");
	}
}
