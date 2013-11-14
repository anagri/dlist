package com.dlistproject.app.csv;

import au.com.bytecode.opencsv.CSVWriter;
import com.dlistproject.app.data.TodoTasksArray;
import com.dropbox.sync.android.DbxFile;

import java.io.FileOutputStream;
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
    FileOutputStream writeStream = dbxFile.getWriteStream();
	CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(writeStream));
    String[] taskArray = tasksArray.getHeaders().toArray(new String[]{});
    writeTaskToFile(taskArray,csvWriter);

    for (int i = 0; i < tasksArray.getCount(); i++)
    {
      writeTaskToFile(tasksArray.getPosition(i).toStringArray(), csvWriter);
    }
    writeStream.flush();
    writeStream.close();
  }

  private void writeTaskToFile(String[] taskArray, CSVWriter csvWriter)
  {
    csvWriter.writeNext(taskArray);
  }
}
