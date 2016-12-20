/*
 By Ehsan khaligh
 ehosseinzadeh@csus.edu
 If Resulution is "Duplicate", reqires user to enter linked issed key and Linked issue field must be "is duplicated by".
*/

import com.atlassian.jira.component.ComponentAccessor;
import webwork.action.ActionContext;
import com.opensymphony.workflow.InvalidInputException;

def issueLinkManager = ComponentAccessor.getIssueLinkManager();
def request = ActionContext.getRequest(); //Rest API
def firstComparisonResult = issue.resolutionObject.name != "Duplicate" || issueLinkManager.getOutwardLinks(issue.id)*.issueLinkType?.name?.contains('Duplicate'); //Checks if the resolution is duplicate
def linkedIssueType = request.getParameter("issuelinks-linktype"); //checks what is the value of the drop down linked issue value (Rest API)
def linkedIssueKeys = request.getParameterValues("issuelinks-issues");  //Checks what the issue key through Rest API

if(!(firstComparisonResult || (linkedIssueType == 'is duplicated by' && linkedIssueKeys)) ){


    throw new InvalidInputException("issuelinks", "Duplicate issues must have linked issue field  \"is duplicated by\" with duplicate issue key.");


} else    {  

    return true;
}

