package com.dlistproject.app.csv;

import au.com.bytecode.opencsv.CSVWriter;
import com.dropbox.sync.android.DbxFile;
import com.dlistproject.app.data.TodoTasksArray;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * User: kaushik Date: 14/11/13 Time: 10:33 PM
 */

public class CsvHelper
{
  public TodoTasksArray getTodoList(DbxFile dbxFile) throws IOException
  {
    return new TodoTasksArray(dbxFile.getReadStream());
  }

  public void writeCsv(DbxFile dbxFile, TodoTasksArray tasksArray) throws IOException
  {
    CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(dbxFile.getWriteStream()));
    writeTaskToFile(tasksArray.getHeaders().toArray(new String[]{}),csvWriter);

    for (int i = 0; i < tasksArray.getCount(); i++)
    {
      writeTaskToFile(tasksArray.getPosition(i).toStringArray(), csvWriter);
    }
  }

  private void writeTaskToFile(String[] taskArray, CSVWriter csvWriter)
  {
    csvWriter.writeNext(taskArray);
  }
}
