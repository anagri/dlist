package com.dlistproject.app.data;

/**
 * User: kaushik Date: 14/11/13 Time: 11:14 PM
 */
public class TodoTask
{

  private String title;
  private String status;
  private String assignedTo;
  private String priority;
  private String remindAt;
  private String geoLoc;
  private String createdBy;
  private String createdAt;

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getTitle()
  {
    return title;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public String getStatus()
  {
    return status;
  }

  public void setAssignedTo(String assignedTo)
  {
    this.assignedTo = assignedTo;
  }

  public String getAssignedTo()
  {
    return assignedTo;
  }

  public void setPriority(String priority)
  {
    this.priority = priority;
  }

  public String getPriority()
  {
    return priority;
  }

  public void setRemindAt(String remindAt)
  {
    this.remindAt = remindAt;
  }

  public String getRemindAt()
  {
    return remindAt;
  }

  public void setGeoLoc(String geoLoc)
  {
    this.geoLoc = geoLoc;
  }

  public String getGeoLoc()
  {
    return geoLoc;
  }

  public void setCreatedBy(String createdBy)
  {
    this.createdBy = createdBy;
  }

  public String getCreatedBy()
  {
    return createdBy;
  }

  public void setCreatedAt(String createdAt)
  {
    this.createdAt = createdAt;
  }

  public String getCreatedAt()
  {
    return createdAt;
  }

  public String[] toStringArray()
  {
    return new String[]{title,status,assignedTo,priority,remindAt,geoLoc,createdBy,createdAt};
  }
}
