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
