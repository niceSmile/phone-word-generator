package com.mac.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of the abstract class <code>Dictionary</code>
 * 
 * @author Froilan
 *
 */
public class Dictionary extends AbstractDictionary {

	private List<String> data;

	/**
	 * Constructor to load dictionary from the specified filename.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public Dictionary(String fileName) throws IOException {
		super();
		data=loadDictionary(fileName);
	}
	
	
	/* (non-Javadoc)
	 * @see com.mac.data.AbstractDictionary#findWordMatch(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> findWordMatch(String telNo, String word) {
		List<String> resultLst = new ArrayList<String>();
		recursiveWord(word, "", resultLst, data, telNo, 1);
		return resultLst;
	}
	
	
	/**
	 * @param input
	 * @param result
	 * @param resultLst
	 * @param data
	 * @deprecated Use {@link #recursiveWord(String, String, List, List, String, Integer))}
	 */
	private static void recursiveWord(String input, String result, List<String> resultLst, List<String> data){
		if(input.length()==0){
			resultLst.add(result);
			return;
		} else {
			/* get pattern */
			List<String> possibleWords=getPattern(input, data, 0);
			
			for(String tmp : possibleWords){
				String inputNext=input.substring(tmp.length());
				recursiveWord(inputNext,result.length()==0?tmp:result+"-"+tmp,resultLst,data);
			}
		}
	}
	
	
	
	/**
	 * Retrieves all word combinations recursively.
	 * 
	 * @param input the character combination generated from phone number encoding. 
	 * @param result the matched words separated by a single dash "-"
	 * @param resultLst the <code>List<String></code> of all word combinations
	 * @param data the dictionary
	 * @param telNo the numeric telephone number
	 * @param nc the allowed number of non-matching characters between words
	 */
	private static void recursiveWord(String input, String result, List<String> resultLst, List<String> data, String telNo, int nc){
		if(input.length()==0){
			resultLst.add(result);
			return;
		} else {
			List<String> possibleWords=getPattern(input, data, 0);
			
			boolean charInBetween=false;
			String subChar="";
			if(possibleWords==null||possibleWords.size()==0){
				for(; nc>0; nc--){ /* allowed number of chars between words, recursive down to 1 */
					possibleWords=getPattern(input, data, nc);
					charInBetween=true; /* indicator that chars between words exists */
					subChar=telNo.substring(0,nc); /* set of chars between words - append to result */
					
					for(String tmp : possibleWords){
						String inputNext=input.substring(nc+tmp.length());
						String telNoNext=telNo.substring(nc+tmp.length());
						String resStr=result.length()==0?(charInBetween?subChar+"-"+tmp:tmp):(charInBetween?result+"-"+subChar+"-"+tmp:result+"-"+tmp);
						recursiveWord(inputNext,resStr,resultLst,data,telNoNext,nc);
					}
				}
			} else {
				for(String tmp : possibleWords){
					String inputNext=input.substring(tmp.length());
					String telNoNext=telNo.substring(tmp.length());
					String resStr=result.length()==0?(charInBetween?subChar+"-"+tmp:tmp):(charInBetween?result+"-"+subChar+"-"+tmp:result+"-"+tmp);
					recursiveWord(inputNext,resStr,resultLst,data,telNoNext,nc);
				}
			}
		}
	}
	
	
	/**
	 * Get a list of possible words in a substring.
	 * 
	 * @param str the string to be searched with words
	 * @param data the dictionary
	 * @param index the number of characters from the left that will not be included on the search 
	 * @return <code>List<String></code> of words found in the string
	 */
	public static List<String> getPattern(String str, List<String> data, int index){
		List<String> lst=new ArrayList<>();
		
		if(data!=null){
			for(String tmp : data){
				Pattern p = Pattern.compile(tmp, Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(str);
				if (m.find()) {
					int start=m.start();
					if(start==index){
						lst.add(m.group());
					}
				}
			}
		}

		return lst;
	}
	
}
