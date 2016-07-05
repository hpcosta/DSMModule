/**
 * @author Hernani Costa
 * iCorpora
 * EXPERT (EXPloiting Empirical appRoaches to Translation)
 * ESR3 - Collection & preparation of multilingual data for multiple corpus-based approaches to translation
 * Department of Translation and Interpreting 
 * Faculty of Philosophy and Humanities 
 *
 * Copyright (c) 2013-2016 University of Malaga, Spain.
 * All rights reserved.
 */
package data.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * This class is responsible for writing and reading for and from CSV files.
 */
/**
 * see: http://opencsv.sourceforge.net/#how-to-read
 */
/**
 * @author Hernani Costa
 *
 * @version 0.1/2015
 */
public class ReadWriteCSV
{

	/**
	 * Writes to a CSV file
	 * 
	 * @param fileName
	 *           - file name
	 * @param data
	 *           - list with all the data
	 * @param append
	 *           - true or false
	 * @param colSeparator
	 *           - columns separator
	 */
	public void write(String fileName, List<String[]> data, boolean append, char colSeparator)
	{
		CSVWriter writer = null;
		try
		{
			writer = new CSVWriter(new FileWriter(fileName, append), colSeparator);
			for (String[] line : data)
				writer.writeNext(line);
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Writes one line at a time to a CSV file
	 * 
	 * @param pathToFile
	 *           - path to file plus the file name
	 * @param line
	 *           - list with all the data for one single line
	 * @param append
	 *           - true or false
	 * @param columnSeparator
	 *           - columns separator
	 */
	public void write(String pathToFile, String[] line, boolean append, char columnSeparator)
	{
		CSVWriter writer = null;
		try
		{
			writer = new CSVWriter(new FileWriter(pathToFile, append), columnSeparator);
			writer.writeNext(line);
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Writes the first line "SEP=;" in order to be readable by the Excel
	 * 
	 * @param pathToFile
	 *           - path to file plus the file name
	 * @param line
	 *           - "SEP=;"
	 */
	public void write(String pathToFile, String line)
	{
		String[] firstLine = new String[1];
		firstLine[0] = line;
		CSVWriter writer = null;
		try
		{
			writer = new CSVWriter(new FileWriter(pathToFile, true));
			writer.writeNext(firstLine);
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Reads from a CSV file
	 * 
	 * @param filePath
	 *           - file path name
	 * @param columnSeparator
	 *           - columns separator
	 * @param nColumns
	 *           - total number of columns to be read
	 * @param startingLine
	 *           - starting line
	 * @param startingColumn
	 *           - starting column
	 * @return
	 */
	public List<String[]> read(String filePath, char columnSeparator, int nColumns, int startingLine, int startingColumn)
	{
		CSVReader reader = null;
		String[] line = null;
		List<String[]> data = new ArrayList<String[]>();
		try
		{
			// see http://opencsv.sourceforge.net/#how-to-read for more information about the parameters
			reader = new CSVReader(new FileReader(filePath), columnSeparator, '\'', startingLine);
			String[] nextLine;
			try
			{
				while ((nextLine = reader.readNext()) != null)
				{
					line = new String[nColumns];
					for (int i = startingColumn; i < nColumns; i++)
					{
						line[i] = nextLine[i];
						// System.out.print(line[i]);
					}
					data.add(line);
					// System.out.println();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public static void main(String args[])
	{
		List<String[]> data = new ArrayList<String[]>();
		// String[] line = { "a", "b", "c" };
		// String[] line2 = { "1", "2", "3" };
		// data.add(line);
		// data.add(line2);
		// ReadWriteCSV writeCSV = new ReadWriteCSV();
		// writeCSV.write("cenas.csv", data, false, ';');
		// writeCSV.read("cenas.csv", ';', 3, 0, 0);
		ReadWriteCSV readCSV = new ReadWriteCSV();
		data = readCSV.read("cenas.csv", ',', 21, 1, 0);
		for (String[] line : data)
		{
			System.out.println(Arrays.toString(line));
		}
	}
}
