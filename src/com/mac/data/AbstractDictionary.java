package com.mac.data;

import java.io.IOException;
import java.util.List;

import com.mac.util.FileReader;

/**
 * AbstractDictionary is the abstract base class for all Dictionary objects.
 * Dictionary is loaded from a text file of words. The dictionary word is expected to have one word per line.
 * 
 * @author Froilan
 *
 */
public abstract class AbstractDictionary {
	
	/**
	 * Loads dictionary word bank from a specified file
	 * 
	 * @param filename the file that contains words
	 * @return <code>List<String></code> if dictionary is successfully loaded
	 * @throws IOException if file is not available
	 */
	protected List<String> loadDictionary(String filename) throws IOException {
		FileReader fileR = null;
		try {
			fileR = new FileReader(filename);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return fileR==null?null:fileR.getData();
	}
	
	
	/**
	 * Finds all possible words from an input string.
	 * 
	 * @param telNo the telephone number
	 * @param str one of the possible string generated from the number encoding
	 * @return <code>List<String></code> list of all possible possible word matches
	 */
	public abstract List<String> findWordMatch(String telNo, String str);
}
