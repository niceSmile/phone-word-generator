package com.mac.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class to reads files.
 * 
 * @author Froilan
 *
 */
public class FileReader {

	private final Path fFilePath;
	
	/**
	 * List of words
	 * 
	 */
	private List<String> DATA;


	/**
	 * Constructor for file processing
	 * 
	 * @param aFileName the file to read
	 */
	public FileReader(String aFileName) throws IOException{
		fFilePath = Paths.get(aFileName);
		processFile();
	}


	/**
	 * Reading file line per line
	 * 
	 * @throws IOException if file is not found
	 */
	private final void processFile() throws IOException {
		try (Scanner scanner =  new Scanner(fFilePath)){
			while (scanner.hasNextLine()){
				processLine(scanner.nextLine());
			}
		}
	}


	/**
	 * Remove special characters on each line and load to dictionary
	 * 
	 * @param aLine a line from a file
	 */
	private void processLine(String aLine){
		String noSpecialChar=aLine.replaceAll("\\W", "");
		if(DATA==null) DATA=new ArrayList<>();
		DATA.add(noSpecialChar);
	}
	

	/**
	 * Get the list of data
	 * 
	 * @return data loaded to <code>List<String></code>
	 */
	public List<String> getData(){
		return DATA;
	}
	
}
