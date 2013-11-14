package com.dlistproject.app.csv;

import com.dlistproject.app.data.TodoTasksArray;
import com.dropbox.sync.android.DbxFile;

import java.io.IOException;

/**
 * User: kaushik Date: 14/11/13 Time: 10:33 PM
 */

public class CsvHelper
{
  public static final int INITIAL_STRING_SIZE = 128;

  /** The character used for escaping quotes. */
  public static final char DEFAULT_ESCAPE_CHARACTER = '"';

  /** The default separator to use if none is supplied to the constructor. */
  public static final char DEFAULT_SEPARATOR = ',';

  /**
   * The default quote character to use if none is supplied to the
   * constructor.
   */
  public static final char DEFAULT_QUOTE_CHARACTER = '"';

  /** The quote constant to use when you wish to suppress all quoting. */
  public static final char NO_QUOTE_CHARACTER = '\u0000';

  /** The escape constant to use when you wish to suppress all escaping. */
  public static final char NO_ESCAPE_CHARACTER = '\u0000';

  /** Default line terminator uses platform encoding. */
  public static final String DEFAULT_LINE_END = "\n";

  private char separator = DEFAULT_SEPARATOR;
  private char quotechar = DEFAULT_QUOTE_CHARACTER;
  private int escapechar = DEFAULT_ESCAPE_CHARACTER;

  public TodoTasksArray getTodoList(DbxFile dbxFile) throws IOException
  {
    return new TodoTasksArray(dbxFile.getReadStream());
  }

  public void writeCsv(DbxFile dbxFile, TodoTasksArray tasksArray) throws IOException
  {
    String[] taskArray = tasksArray.getHeaders().toArray(new String[]{});
    writeTaskToFile(taskArray,dbxFile);

    for (int i = 0; i < tasksArray.getCount(); i++)
    {
      writeTaskToFile(tasksArray.getPosition(i).toStringArray(), dbxFile);
    }
  }

  private void writeTaskToFile(String[] taskArray, DbxFile dbxFile) throws IOException
  {
    dbxFile.writeString(getCsvLine(taskArray));
  }


  public String getCsvLine(String[] nextLine)
  {
    if (nextLine == null)
      return null;

    StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
    for (int i = 0; i < nextLine.length; i++) {

      if (i != 0) {

        sb.append(separator);
      }

      String nextElement = nextLine[i];
      if (nextElement == null)
        continue;
      if (quotechar !=  NO_QUOTE_CHARACTER)
        sb.append(quotechar);

      sb.append(stringContainsSpecialCharacters(nextElement) ? processLine(nextElement) : nextElement);

      if (quotechar != NO_QUOTE_CHARACTER)
        sb.append(quotechar);
    }

    sb.append(DEFAULT_LINE_END);
    return sb.toString();
  }

  private boolean stringContainsSpecialCharacters(String line) {
    return line.indexOf(quotechar) != -1 || line.indexOf(escapechar) != -1;
  }

  private StringBuilder processLine(String nextElement)
  {
    StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
    for (int j = 0; j < nextElement.length(); j++) {
      char nextChar = nextElement.charAt(j);
      if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
        sb.append(escapechar).append(nextChar);
      } else if (escapechar != NO_ESCAPE_CHARACTER && nextChar == escapechar) {
        sb.append(escapechar).append(nextChar);
      } else {
        sb.append(nextChar);
      }
    }

    return sb;
  }
}
