######################################################
## opencmsmodule-com.bearingpoint.opencms.workflow2 ##
## BearingPoint Infonova, 2007                      ##
######################################################

BearingPoint OpenCms Workflow2 Module
----------------------------------------------------------------
This module extends OpenCms by adding basic workflow related
features as:

     - Definition of worklow states (defined as common 
         OpenCms projects)     
     - Assignment of VFS resources to specific workflow states
     - Integrated user/role management    
     - Additional Interfaces for attaching a workflow engine
 
The Workflow2 module works by overlapping several OpenCms projects.
Each of these projects represents one workflow state:

       project "Offline", assigned to root folder  
       project "REVIEW", assigned to root folder
       project "APPROVAL", assigned to root folder

As you see the Workflow2 module you can simply create n-eye workflow 
mechanisms.

When creating a new resource in e.g. project one it's also visible 
from project two. Indeed OpenCms is showing an icon that the resource
belongs to project1, but the main functionalities (context menu) are
also available from the other projects. 

This packages is solving that issue by adding certain behaviours and
rules. The transformation to other workflow states is done by moving 
resources between the projects. 
    
When attaching a workflow engine like JBPM following additional 
features are available:

     - Assignment of Tasks to VFS resources
     - Dedicated task view

For details see chapter "Workflow Engine Interface".


System Requirements
----------------------------------------------------------------

	- OpenCms 7.0.2 or later
    - bearingpoint commons modules:
        opencmsmodule-com.bearingpoint.opencms.commons 
        opencmsmodule-com.bearingpoint.opencms.commons.springmanager
    	

Quick & Default Installation
----------------------------------------------------------------

This type of installation is intended for OpenCms systems which 
are fresh and clean or have no need for special configuration.
As precondition you need the Workflow2 all in one package.

1. Import the commons, springmanager and workflow2 module via 
   OpenCms module import mechanism.
2. Copy opencms-workplace.xml to %OPENCMS_DIR%\WEB-INF\config\ 
   (replace current configuration)
3. Restart your server. Voilá!

By default the Workflow2 module uses your OpenCms database connection
properties. If you don't want your Workflow2 tables stored there have
a look at the database configuration chapter of the "Advanced Installation"
section.


Advanced Installation
----------------------------------------------------------------

1. Build the workflow2 module
2. Deploy to OpenCms via module import mechanism
3. Create a database where to store the workflow states.
4. Declare your new database inside the workflow2 spring configuration:
       a) Inside the OpenCms workbench switch to the root ("/") view
       b) Edit /system/modules/com.bearingpoint.opencms.workflow2/config/spring/DataSource.xml
       c) By Default the current OpenCms database connection properties are used.
          If you have the need for a different database location or settings
          have a look at the workflow.datasource bean. Here you can easily overwrite
          certain parameters:
          
              <bean id="workflow.dataSource" class="com.bearingpoint.opencms.workflow2.WorkflowDataSource">
				<!-- 
					INSERT ADDITIONAL INITIALIZATIONS HERE 
					BY DEFAULT THE PARAMETERS ARE READ FROM opencms.properties
				
					EXAMPLE PROPERTIES ARE:
					<property name="driverClassName"><value>com.mysql.jdbc.Driver</value></property>
	                <property name="url"><value>jdbc:mysql://localhost:3306/workflow2?useServerPrepStmts=false&amp;jdbcCompliantTruncation=false</value></property>
	                <property name="username"><value>root</value></property>
	                <property name="password"><value>root</value></property>
                -->
		      </bean>          
       
       d) In some cases you also have to change the Hibernate settings within
          this file (Especially when you are using an database other than MySQL)
          
          By default this configuration does not set a Hibernate Dialect. If you
          are using special database environments or if you are facing problems 
          regarding Hibernate and database I recommend to set at least a Hibernate
          dialect within the Hibernate section, which could look like this:
          
          	<prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop> 
          
       e) Save and quit the editor. Publish the VFS resource.
       
5. Configure the opencms-workplace.xml for new context menu entries to approve/reject resources
   respectively to create tasks if an workflow engine is attached. Inside the folder <patches>
   you can find an example opencms-workplace.xml. There is also a patch to modify your OpenCms
   sources within Eclipse. If you have a zipped OpenCms distrubution and/or a customized 
   workplace configuration it is recommended to do it manually:
   
       a) To approve/reject resources/task this packages offers a new context menu entry.
          To add the context menu action you have to add following definiton to your
          workplace-config.xml:
          
            <!-- WORKFLOW2 ITEM -->
            <entry key="GUI_EXPLORER_CONTEXT_COPYTOPROJECT_0" uri="commons/movetoproject.jsp" rule="movetoproject"/>
          
          Note: Unfortunately workflow for folder doesn't work at the moment. For that 
          reason bypass the entry at the folder-section.
          
   	   b) The workflow2 packages introduces a new menu item rule (MIR) which is called
   	   
   	   		"com.bearingpoint.opencms.workflow2.cms.CmsMirPrBelongsToOtherInvisible"    
   	   		
   	   	  This rule deactivates the regarding context menu items if the refering resource 
   	   	  doesn't belong to the current project.  
   	   	  
   	   	  For example this type of rule would be 
   	   	  applied to the "Publish" functionality if the current user is just an author
   	   	  and no publisher.   	   	     	   	  
   	   	   
   	   	  Insert the new rule at the menu rules section:
   	   	  
   	   	    <menurule name="movetoproject">
    		  <menuitemrule class="com.bearingpoint.opencms.workflow2.cms.CmsMirPrBelongsToOtherInvisible" />
    	    </menurule>
   	   	   
   	   	c) Furthermore all other context menu actions should not be available if the
   	   	   resource belongs to another project. For that reason we have to insert the
   	   	   "CmsMirPrBelongsToOtherInvisible" rule to all menu rule definitions 
   	   	   (note: it has to be at the first position if there are other MIRs).
   	   	   
6. Restart your OpenCms Server


Configuration
----------------------------------------------------------------

As an example workflow we create three workflow stages:
	* Authoring (Default offline project)
	* Review
	* Approval

Each workflow stage needs a related OpenCms project. 
This means you have to move to the OpenCms Administration View
and create those projects. Afterwards the Workflow has to be 
defined:

1. Inside the CMS administration select workflow2 --> Show Project Flow
2. Here you click "New Workflow Stage"
3. Select Project "Authoring" and stage 0. Confirm by clicking Ok.
4. Go on the same way with the other projects. The stage number represents
   the position of the project within flow.
5. When you are finished you should have following configuration:

Offline  ---->  Review   ---->  Approval
Stage 0           Stage 1         Stage 2

6. Now you can start pushing resources between the projects.
   Just right-click on a resource within the explorer view and
   select "Approve/Reject".
   

Workflow Engine Interface
----------------------------------------------------------------

Task functionalities are just available if an 3rd party 
workflow engine or an self implemented engine is attached.

To attach a third party workflow engine (e.g. JBoss JBPM) 
the workflow2 module provides an interface to be implemented:

    com.bearingpoint.opencms.workflow2.engine.I_WorkflowEngine.java

The implementation of that interface should contain simple delegation
methods to the engine.

2. Add the Workflow Engine Beans declaration to your spring-config.xml
   a) Inside the OpenCms workbench switch to the root ("/") view
   b) Edit /system/modules/com.bearingpoint.opencms.workflow2/config/spring/spring-config.xml
   c) Add following line to the other Spring Config Locations:
   
      <SpringConfigFileLocation>opencms:WorkflowEngineBeans.xml</SpringConfigFileLocation>

   d) Save & Exit editor + publish VFS resource.
3. Set the correct Workflow Engine Connector Class:
   a) Inside the OpenCms workbench switch to the root ("/") view
   b) Edit /system/modules/com.bearingpoint.opencms.workflow2/config/spring/WorkflowEngineBeans.xml.xml
   c) Update the class attribute with your desired class file:
   
        <bean id="workflow.workflowEngine"
           class="com.bearingpoint.opencms.workflow2.engine.jbpm.JBPMEngineFacade">
        </bean>
   d) Save & Exit editor + publish VFS resource.
4. Restart OpenCms Server

Event Handling
----------------------------------------------------------------

(A) Integrated Events

	EVENT_WORKFLOW_STARTED
	EVENT_WORKFLOW_TRANSITION_APPROVED
	EVENT_WORKFLOW_TRANSITION_REJECTED
	EVENT_WORKFLOW_FINISHED
	EVENT_WORKFLOW_TASK_CREATED
	EVENT_WORKFLOW_TASK_ASSIGNED
	EVENT_WORKFLOW_TASK_POOLED
	EVENT_WORKFLOW_TASK_FINISHED
	EVENT_WORKFLOW_TASK_COMMENTED
	EVENT_WORKFLOW_RESOURCE_MOVED


(B) Releasing Events

This workflow module contains a class which offers a simple approach to fire
workflow related events. Due to consistency this module designed that workflow events
are fired within your workflow engine implementation. To fire an event just use
the static class

	com.bearingpoint.opencms.workflow2.events.WorkflowEventGun
	
For example if you want your workflow engine implementation to fire an event
notifying the listeners that a workflow (JBPM term: process instance) has been
created just call:

	WorkflowEventGun.fireEventWorkflowStarted(Long workflowID); 


(C) Catching/Listening to Events

OpenCms Workflow2 provides an interface for listening
to both OpenCms Events and Workflow2 Events:

	I_CmsWorkflowEventListener extends I_CmsEventListener

To get notified when an event happens you have to register your 
implementation of the event listener:

	OpenCms.addCmsEventListener(yourListenerInstance);


FAQ
----------------------------------------------------------------
Q: When importing any of these OpenCms Modules I receive an error
   message like "Caused by: com.mysql.jdbc.PacketTooBigException: 
   Packet for query is too large (1980691 > 1048576)."

A: The packet size of your database is to small! =) For example in 
   case of an MySQL database you have to change this value inside
   the my.ini file. E.g. insert "max_allowed_packet = 16M" at the end
   of your MySQL configuration.
--

Q: When switching to the Workflow2 Administration Panel I get an 
   error message saying: "java.lang.NullPointerException
	at com.bearingpoint.opencms.commons.springmanager.SpringManager
		.getBean(SpringManager.java:160)" 
		or "ERROR initializing SpringManager: Error creating bean.."
		
A: Possibly the configuration for your Workflow Engine connector is 
   not valid respectively the configured class file is not available.
   If you don't have any engine implementation you have to avoid the 
   loading of your engine bean definition (!) inside the spring-config.xml
   For details see chapter "Workflow Engine Interface".

  
Legal / License
----------------------------------------------------------------
This package is distributed under the LGPL and without any 
warranty. For details see license.txt

Contact
----------------------------------------------------------------
If you have any concerns, questions or contributions contact
me at "david.trattnig (at) gmail (dot) com"