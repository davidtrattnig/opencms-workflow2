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

package com.bearingpoint.opencms.workflow2.engine;

/**
 * Transition
 * 
 * Types of transitions the workflow process definition can contain.
 * 
 * @author David.Trattnig
 */
public enum Transition {
	
	/**
	 * Transition to publish a resource or set of resources
	 * to another project (next project of the projects the 
	 * current user belongs to)
	 */
	APPROVE,
	
	/**
	 * Transition to reject a resource to one of the previous
	 * projects (all previous projects of the project the resource
	 * belongs to).
	 */
	REJECT,
		
	/**
	 * Publishes a resource to the online project. Just available
	 * for workflow super-users or approvers (depending on the configuration). 
	 */
	PUBLISH,
	
	/**
	 * Moves a resource within the current project. 
	 */
	MOVE
}
