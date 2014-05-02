package com.mac.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Retrieves all possible string combination from a phone number
 * 
 * @author Froilan
 *
 */
public class WordGenerator {
	
	/**
	 * Number coding
	 */
	private static Map<Character, char[]> numLetterMap;
	
	static {
		numLetterMap=new HashMap<Character, char[]>();
		numLetterMap.put(Character.valueOf('0'), new char[] { ' ' });
		numLetterMap.put(Character.valueOf('1'), new char[] { ' ' });
		numLetterMap.put(Character.valueOf('2'), new char[] { 'A', 'B', 'C' });
		numLetterMap.put(Character.valueOf('3'), new char[] { 'D', 'E', 'F' });
		numLetterMap.put(Character.valueOf('4'), new char[] { 'G', 'H', 'I' });
        numLetterMap.put(Character.valueOf('5'), new char[] { 'J', 'K', 'L' });
        numLetterMap.put(Character.valueOf('6'), new char[] { 'M', 'N', 'O' });
        numLetterMap.put(Character.valueOf('7'), new char[] { 'P', 'Q', 'R', 'S' });
        numLetterMap.put(Character.valueOf('8'), new char[] { 'T', 'U', 'V' });
        numLetterMap.put(Character.valueOf('9'), new char[] { 'W', 'X', 'Y', 'Z' });
	}
	
	
	/**
	 * Recursively getting all possible character combinations 
	 * 
	 * @param input the telephone number
	 * @param resultSoFar a word generated
	 * @param allResults <code>List<String></code> of all character combinations
	 */
	public static void convert(String input, String resultSoFar, List<String> allResults) {
        if (input.length() == 0) {
            allResults.add(resultSoFar);
        } else {
            /* Strip the next character off the front of the phone number */
            Character nextDigit = Character.valueOf(input.charAt(0));

            /* Look up the list of mappings from that digit to all letters */
            char[] mappingArray = numLetterMap.get(nextDigit);

            if (mappingArray != null) {

                /* Processed the first digit in the rest of the number, recurse with the rest of the number */
                String inputTail = input.substring(1);

                for (char nextLetter : mappingArray) {
                    convert(inputTail, resultSoFar + nextLetter, allResults);
                }
            }
        }

    }
	
	/**
	 * Generate words
	 * 
	 * @param telno the telephone number
	 * @return <code>List<String></code> of all character combinations
	 */
	public static List<String> generateWords(String telno){
		List<String> results = new ArrayList<String>();
        convert(telno, "", results);
		return results;
	}

}
