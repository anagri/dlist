package com.dlistproject.app;

import au.com.bytecode.opencsv.CSVReader;
import com.dropbox.sync.android.DbxFile;
import com.dlistproject.app.TodoTask;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: kaushik Date: 14/11/13 Time: 10:33 PM
 */

public class CsvReadHelper
{
  public static class TodoTasksArray
  {

    private List<TodoTask> todoTasks;

    public TodoTasksArray(FileInputStream inputStream) throws IOException
    {
      todoTasks = getTodoList(inputStream);
    }
    
    public int getCount() {
		return todoTasks.size();
	}
    
    public TodoTask getPosition(int i)
    {
      return todoTasks.get(i);
    }

    public void insert(TodoTask t) {
		todoTasks.add(t);
	}
    public List<TodoTask> getTodoList(FileInputStream inputStream) throws IOException
    {
      List<TodoTask> todoTaskList = new ArrayList<TodoTask>();
      CSVReader r = new CSVReader(new InputStreamReader(inputStream));

      String[] row = r.readNext();
      while((row = r.readNext()) != null)
      {
        TodoTask t = new TodoTask();
        t.setTitle(row[0]);
        t.setStatus(row[1]);
        t.setAssignedTo(row[2]);
        t.setPriority(row[3]);
        t.setRemindAt(row[4]);
        t.setGeoLoc(row[5]);
        t.setCreatedBy(row[6]);
        t.setCreatedAt(row[7]);
        todoTaskList.add(t);
      }
      return todoTaskList;
    }

  }

  public TodoTasksArray getTodoList(DbxFile dbxFile) throws IOException
  {
    return new TodoTasksArray(dbxFile.getReadStream());
  }

}
