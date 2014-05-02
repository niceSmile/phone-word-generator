package com.mac;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.mac.data.Dictionary;
import com.mac.util.FileReader;
import com.mac.util.WordGenerator;


/**
 * 
 * Implementing class that calls all services ({@link FileReader}, {@link WordGenerator}, {@link Dictionary}) 
 * to get all possible word replacements from a dictionary.  
 * 
 * @author Froilan
 *
 */
public class PhoneWordConverter {
	
	/**
	 * Default phone number file/location
	 */
	public static final String NUMBER_FILE="numbers.txt";
	
	
	/**
	 * Default dictionary file/location
	 */
	public static final String WORD_FILE="dictionary.txt";
	
	/**
	 * Get telephone numbers in a filename
	 * 
	 * @param aFileName the telephone number file and location
	 * @return <code>List<String></code> of telephone numbers
	 * @throws IOException if file is not found
	 */
	public List<String> getNumbers(String aFileName) throws IOException{
		FileReader f=new FileReader(aFileName);
		return f.getData();
	}
	
	/**
	 * Generate words from a single telephone number
	 * 
	 * @param telNo a single telephone number
	 * @return <code>List<String></code> of all words combination
	 */
	public List<String> convertToWords(String telNo){
		return WordGenerator.generateWords(telNo);
	}
	
	
	/**
	 * Generate words from a <code>List<String></code> telephone number
	 * 
	 * @param telNos <code>List<String></code> of telephone numbers
	 * @return <code>Map<String, List<String>></code> key=telephone number , value=all words combination
	 */
	public Map<String, List<String>> convertToWords(List<String> telNos){
		Map<String, List<String>> m=new HashMap<>();
		for(String n : telNos){
			m.put(n, convertToWords(n));
		}
		return m;
	}
	
	
	/**
	 * Displays all words combination in the console
	 * 
	 * @param set <code>Set<String></code> of distinct words combination
	 */
	public static void displayPhrase(Set<String> set){
		for(String p : set){
			display("   "+p);
		}
		display("         ");
	}
	
	
	/**
	 * Displays string in console
	 * 
	 * @param s the <code>String</code> 
	 */
	public static void display(String s){
		System.out.println(s);
	}
	
	
	/**
	 * Null value checker
	 * 
	 * @param obj the <code>Object</code> to be checked
	 * @return <code>Object</code> is non null value or an empty <code>String</code>
	 */
	public static String checkForNull(Object obj) {
		String st=null;
		try{
			if(obj==null)
				 return "";
			else
				 st=(String) obj;
			st=st.trim();
		} catch(NullPointerException e) {
			throw e;
		} catch(Throwable t) {
			throw t;
		}
		return st;
	} 
	
	
	/**
	 * Main method to load files and trigger word conversion logic
	 * 
	 * @param telNo the telephone number
	 * @param telFile the telephone number file/location
	 * @param wordFile the dictionary number file/location
	 * @throws IOException if file is not found
	 */
	public static void beginConversion(String telNo, String telFile, String wordFile) throws IOException{
		telFile=telFile.length()>0?telFile:NUMBER_FILE;
		wordFile=wordFile.length()>0?wordFile:WORD_FILE;
		
		try {
			PhoneWordConverter con=new PhoneWordConverter();
			Dictionary dictionary=new Dictionary(wordFile);
			
			List<String> telNos=new ArrayList<>();
			if(telNo.length()>0) telNos.add(telNo.replaceAll("\\W", ""));
			else telNos.addAll(con.getNumbers(telFile));
			
			/* convert numbers to words */
			Map<String, List<String>> m=con.convertToWords(telNos);
			
			Set<String> finalSet = new HashSet<String>();;
			
			/* get word match */
			for(String tel : m.keySet()){
				display("Telephone Number : "+tel);
				List<String> ls=m.get(tel);
				for(String word : ls){
					List<String> converted=dictionary.findWordMatch(tel, word);
					
					if(converted!=null&&converted.size()>0){
						for(String phrase : converted){
							finalSet.add(phrase);
						}
					}
				}
				
				if(finalSet.size()==0) display("   No word match");
				
				displayPhrase(finalSet);
				finalSet=new HashSet<String>(); /* reset final list */
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String telFile=checkForNull(System.getProperty("numbers"));
		String wordFile=checkForNull(System.getProperty("dictionary"));
		Scanner stdin=null;
		
		try{
			
			if(telFile.length()==0){
				display("Please enter a phone number: ");
				stdin = new Scanner(new BufferedInputStream(System.in));
				
		        while (stdin.hasNextLine()) {
		        	String in=stdin.next();
		        	
		            if(in.equalsIgnoreCase("EXIT")) System.exit(0);

					beginConversion(in,telFile,wordFile);
		        }
			} else {
				beginConversion("",telFile,wordFile);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			display("ERROR: Unable to load file.");
		} finally{
			stdin.close();
		}

	}

}
