package com.dlistproject.app.data;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: kaushik Date: 15/11/13 Time: 2:13 AM
 */

public class TodoTasksArray
{
  private List<String> headers;
  private List<TodoTask> todoTasks;

  public TodoTasksArray(FileInputStream inputStream) throws IOException
  {
    todoTasks = new ArrayList<TodoTask>();
    headers = new ArrayList<String>();
    setTodoList(inputStream, todoTasks, headers);
  }

  public List<String> getHeaders()
  {
    return headers;
  }

  public TodoTask getPosition(int i)
  {
    return todoTasks.get(i);
  }

  public int getCount()
  {
    return todoTasks.size();
  }

  public void setTodoList(FileInputStream inputStream, List<TodoTask> todoTasks,
                          List<String> headers) throws IOException
  {
    CSVReader r = new CSVReader(new InputStreamReader(inputStream));

    String[] row = r.readNext();
    for (String h : row)
      headers.add(h);

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
      todoTasks.add(t);
    }
  }

  public void insert(TodoTask todoTask)
  {
    todoTasks.add(todoTask);
  }
}
