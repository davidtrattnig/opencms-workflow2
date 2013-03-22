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

package com.bearingpoint.opencms.workflow2.relation;

import java.util.Comparator;

import org.opencms.file.CmsResource;

import com.bearingpoint.opencms.workflow2.WorkflowException;

/**
 * Simple class to identifie CmsResources by path
 * and the unique UUID
 * 
 * @author David.Trattnig
 *
 */
public class ResourceIdentifier {

	private String _resourcePath;
	private String _resourceUUID;
	
	/**
	 * Constructor / needs a CmsResource object
	 * 
	 * @param resource
	 * @throws WorkflowException
	 */
	public ResourceIdentifier(CmsResource resource) throws WorkflowException {
		
		if (resource == null) {
			throw new WorkflowException("CmsResource is null");
		}
		else if (resource.getRootPath() == null || resource.getRootPath().length() == 0) {
			throw new WorkflowException("CmsResource Path is null or invalid");
		}
		else if ((resource.getFlags() & CmsResource.FLAG_TEMPFILE) == CmsResource.FLAG_TEMPFILE){
			throw new WorkflowException("The given resource is a temporary file.");            
        }
		else if (resource.getResourceId() == null) {
			throw new WorkflowException("CmsResource UUID is null");
		}
		
		_resourcePath = resource.getRootPath();
		_resourceUUID = resource.getResourceId().toString();
	}
	
	/**
	 * private default constructor.
	 * avoid instantiation without CmsResource
	 */
	private ResourceIdentifier() {}
	
	/**
	 * @return resource Path
	 */
	public String getResourcePath() {
		return _resourcePath;
	}
	
	/**
	 * @return resource UUID
	 */
	public String getResourceUUID() {
		return _resourceUUID;
	}
	
	/**
	 * @param resource Path
	 */
	private void setResourcePath(String path) {
		_resourcePath = path;
	}
	
	/**
	 * @param resource UUID
	 */
	private void setResourceUUID(String uuid) {
		_resourceUUID = uuid;
	}
	
	/**
	 * Checks if the resource UUID is existing/valid
	 * 
	 * @return true if valid
	 */
	public boolean hasValidResouceUUID() {
		
		return (_resourceUUID!=null && _resourceUUID.length()>0);
	}
		
//	/* (non-Javadoc)
//	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
//	 */
//	public int compare(Object o1, Object o2) {
//		
//		//expects a ResourceIdentifier object, otherwise a ClassCastException 
//		//will be thrown:
//		ResourceIdentifier ri1 = (ResourceIdentifier) o1;
//		ResourceIdentifier ri2 = (ResourceIdentifier) o2;
//		
//		if (ri1 == null || !ri1.hasValidResouceUUID()) {
//			return -1;
//		}
//		
//		if (ri2 == null || !ri2.hasValidResouceUUID()) {
//			return 1;
//		}
//		
//		
//		
//		if (ri2.getResourceUUID().equals(ri1.getResourceUUID())) {
//			return 0;
//		}			
//		
//		if (ri2.getResourcePath().equals(ri1.getResourcePath())) {
//			return 0;
//		}
//		
//		return 1;
//	}
//	
//	/* (non-Javadoc)
//	 * @see java.lang.Comparable#compareTo(java.lang.Object)
//	 */	
//	public int compareTo(Object resourceIdentifier) throws ClassCastException {
//		
//		return compare(this, resourceIdentifier);
//	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		
		ResourceIdentifier ri = null;
		
		try {
			ri = (ResourceIdentifier) o;
		}
		catch (ClassCastException e) {
			return false;
		}
		
		if (this == o) {
			return true;
		}
		
		if (ri == null || !ri.hasValidResouceUUID()) {
			return false;
		}
		
		if (ri.getResourceUUID().equals(this.getResourceUUID()) ||
				ri.getResourcePath().equals(this.getResourcePath())) {
			return true;
		}		
				
		
		return false;
	}
	
	public static ResourceIdentifier createRIWithResourcePath(String path) {
		
		ResourceIdentifier ri = new ResourceIdentifier();
		ri.setResourcePath(path);
		ri.setResourceUUID(null);
		return ri;
	}
	
	public static ResourceIdentifier createRIWithResourceUUID(String uuid) {
		
		ResourceIdentifier ri = new ResourceIdentifier();
		ri.setResourcePath(null);
		ri.setResourceUUID(uuid);
		return ri;
	}	
	
	public static void updateResourcePath(ResourceIdentifier ri, String path) {
		
		ri.setResourcePath(path);
	}
	
	public static void updateResourceUUID(ResourceIdentifier ri, String uuid) {
		
		ri.setResourceUUID(uuid);
	}
}
