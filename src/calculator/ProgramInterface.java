 package calculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * Title: Dictionary Class - A component of the Dictionary system
 * </p>
 *
 * <p>
 * Description: A controller object class that implements a dictionary
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2018-05-05
 * </p>
 *
 * @author Lynn Robert Carter
 * @version 2.00 - Baseline for transition from Swing to JavaFX 2018-05-07
 */

public class ProgramInterface<T> {

	/**********************************************************************************************

	Class Attributes

	 **********************************************************************************************/
	
	private int numEntries = 0;							// The number of entries in the dictionary
	private Definations[] entry;							// The actual dictionary as an array
	private String searchString = "";					// The string to use for searching
	private int currentIndex = -2;						// The index into the dictionary; this is
														// use when two separate commands need
														// to access the same entry. -2 means
														// that no search string has been set
														// or no more items can be found

	private final int maxNumberOfEntries = 100000;		// The maximum number of dictionary entries

	private String word = null;								// Establish the word to be defined
	private String definition = "";							// Establish the definition of the word
	
	private String largestWord = "";					// Keep track of the largest word read in
	
	private int numberOfSearchItemsFound = 0;			// How many search items found?


	/**********************************************************************************************

	Constructor

	 **********************************************************************************************/

	/**********
	 * This is the default constructor.
	 */
	public ProgramInterface(){
		numEntries = 0;									// Start at the beginning of the array
		entry = new Definations[maxNumberOfEntries];		// Allocate the array
		word = null;
	}


	/**********************************************************************************************

	Methods to implement the dictionary

	 **********************************************************************************************/

	/**********
	 * This method creates the dictionary based on a Scanner object passed in as a parameter
	 * 
	 * @param dictReader - is the Scanner obejct is used to read in the data, one line at a time
	 */
	public void defineDictionary(Scanner dictReader){
		if(dictReader == null)return;					// If there is no Scanner, just return
		
		// Skip the first line as it just has the word "Demonstration" in it;
		dictReader.nextLine();

		// Establish the first word to be defined
		if (dictReader.hasNextLine()){					// See if there is a first line in the file
			word = dictReader.nextLine().trim();		// Establish the word to be defined

			// Skip blank lines
			while (dictReader.hasNextLine() && word.length() == 0){
				word = dictReader.nextLine().trim();
			}
		}
		else
			return;

		// Use the word plus a definition to define a dictionary entry. The loop assumes that a
		// word to be defined has been found and loops adding a definition to it followed by an
		// attempt to find the next word to be defined.
		while (dictReader.hasNextLine()){

			// Establish the definition of the word
			String theNextLine = dictReader.nextLine() + "\n";

			// Keep adding in more lines as long as the line starts with white space
			while (theNextLine.substring(0,1).trim().length()==0) {
				definition += theNextLine;
				if (dictReader.hasNextLine())
					theNextLine = dictReader.nextLine() + "\n";
				else
					break;
			}

			// Check to see if this word, if added to the dictionary, would cause an overflow
			// If so, just return and display an error message
			if (entry.length <= numEntries + 1) {
				System.out.println("*** Too many words in the dictionary. The excess are discarded!");
				return;
			}

			// A word and a definition have been established... create a new dictionary entry
			this.addEntry(word, definition);

			// Set up for the next entry 
			definition = "";				// by resetting the definition
			word = theNextLine.trim();		// and look for the next word to be defined

			// Skip blank lines while looking for the word to be defined or the end of the file
			while (dictReader.hasNextLine() && word.length() == 0){
				word = dictReader.nextLine().trim();
			}
		}
	}

	/**********************************************************************************************

	Standard support methods

	 **********************************************************************************************/

	/**********
	 * This method adds a word with its definition to this dictionary
	 * 
	 * @param w		The word to be defined
	 * @param d		The definition of the word
	 *
	 */
	public void addEntry(String w, String d){	
		
		// See if there is enough room for another entry.
		if (entry.length > numEntries + 1) {

			// See if it is larger than the largest word in the dictionary (lexicographically)
			if (w.compareTo(largestWord) > 0) {
				// The new word being defined is larger than the largest so it can be appended
				largestWord = w;									// Keep track of the new
				entry[numEntries++] = new Definations(w, d);			// largest word
			} else {
				// The new words is not larger than the largest so it must be inserted into the
				// array somewhere before the end.  Loop through the dictionary entries, moving
				// each entry to the right one position until you find an entry smaller than 
				// the new one or you reach the start of the array.  The insert the new entry.
				int ndx = numEntries++;
				while (ndx >= 1 && entry[ndx-1].getWord().compareTo(w) >= 0) {
					entry[ndx] = entry[ndx-1];
					ndx--;
				}
				entry[ndx] = new Definations(w, d);
			}
		}
		else
			// Display an error message if there isn't enough room
			System.out.println("*** Too many words in the dictionary. This word: " + w + " has not been added!");
		}

	/**********
	 * This method gives the read the number of available programs
	 * 
	 * @return - an index
	 */
	
	public String getListOfPrograms() {
		String programList_scan = ""; // declare and empty string
		for (int i = 0; i < entry.length; i++) { // use for loop
			if (entry[i] != null) // if program is not null,
				if (entry[i].getWord() != null) // and words are there
					programList_scan = entry[i].getWord() + "\n"; //then enter the progrsm into string
		}
		return programList_scan;
	}
	
	/**********
	 * this mehtod create and update the dictionary as pe the user's input
	 * 
	 * list - shows the number of available programs
	 * fileName - path of the file
	 */
	
	public void saveDictionary(List<List<String>> list, String fileName) {
//		

		File theDefinitionsFile = new File(fileName); // provided the name of file
		try (FileWriter writer = new FileWriter(theDefinitionsFile)) { // use filewriter to write the updated content in the file
			for (int i = 0; i < list.size(); i++) { // use for loop to iterate all the contents
				for (int j = 0; j < list.get(i).size(); j++) { 
					writer.write(list.get(i).get(j) + "\n"); // write it into list
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**********
	 * Fetch the number of words in the dictionary
	 * 
	 * @return the number of words defined in the dictionary
	 */
	public int getNumEntries(){return numEntries;}

	/***********
	 * Set the string that will be used in searching for words in the dictionary
	 * 
	 * @param s - This is the string to be used by the search
	 */
	public void setSearchString(String s){
		searchString = s.toUpperCase();			// Use upper case for searching
		currentIndex = -1;						// -1 means the search string has been defined, but
												// no values have been found
	}

	/**********
	 * Find the next entry that contains the search string. If it finds a match, it returns a
	 * dictionary entry that matches. If there is no match or there is no search string, the
	 * method returns a null
	 * 
	 * @return a matching dictionary entry or return a null
	 */
	public Definations findNextEntry(){
		if (currentIndex < -1) return null;		// Return null if no search string set
		while (currentIndex < numEntries-1) {	// While there are entries left to search
			Definations de = entry[++currentIndex];	// Fetch the entry, convert it to upper case
			if (de.getWord().toUpperCase().indexOf(searchString)>=0) // If there is a match
				return de;						// return the match to the caller and do not
		}										// finish the loop
		currentIndex = -2;			// If the loop finishes, signal that by resetting the index
		return null;				// so there is no search string and return a null
	}

	/**********
	 * Find all of the entries that have a word that contains the search string specified by the
	 * method parameter
	 * 
	 * @param target - is the String that should be used to drive the search
	 * @return - an empty String or a String of the sequence of entries that match the search String
	 */
	public String findAll(String target){
		String result = "";						// Set the default condition for the result string
		setSearchString(target);				// Set the search target string
		numberOfSearchItemsFound = 0;			// Reset the number of search items found
		Definations aMatch = findNextEntry();		// Try to fetch the next entry
		if (aMatch == null) return "";			// If there isn't one, return an empty string

		// Since there is one, there may be many, so we will loop looking to add them together
		while (aMatch != null) {				// See of there is one to add to the string
			numberOfSearchItemsFound++;			// Increment the number of search items found
			result += "\n" + aMatch.getWord() + ": " + "\n" + aMatch.getDefinition() + "----------\n";
			aMatch = findNextEntry();			// If so, add it and then search for the next
		}										// The loop ends when there are no more matches
		return result;		// Return the String of matches to the caller
	}

	/**********
	 * Get a specified dictionary entry
	 * 
	 * @param ndx - The index of the entry to be fetched
	 * 
	 * @return the DictrEntry corresponding to the specified index or return a null
	 */
	public Definations getDictEntry(int ndx){		// Check the index to see if it is in range
		if (ndx < 0 || ndx >= numEntries) return null;	// If not, return a null
		return entry[ndx];						// If it is, return the specified entry
	}

	/**********
	 * Remove a specified dictionary entry
	 * 
	 * @param ndx - The index of the entry to be removed
	 * 
	 */
	public void remove(int ndx){		// Check the index to see if it is in range
		if (ndx < 0 || ndx >= numEntries) return;	// If not, just return doing nothing
		for (int i=ndx; i < numEntries-1; i++)		// Otherwise, shift the following entries to
			entry[i] = entry[i+1];					// the left by one
		entry[--numEntries] = null;		// Update the number of entries and clear the removed entry
		return;							// If it is, return the specified entry
	}

	/**********
	 * Remove the current dictionary entry
	 *
	 */
	public void remove(){				// Check the index to see if it is in range
		if (currentIndex < 0 || currentIndex >= numEntries) return;	// If not, just return
		for (int i=currentIndex; i < numEntries-1; i++)	// Otherwise, shift the following entries
			entry[i] = entry[i+1];						// to the left by one
		entry[--numEntries] = null;		// Update the number of entries and clear the removed entry
		currentIndex = -1;				// Reset the current index so it does not point to anything
		return;							// If it is, return the specified entry
	}

	/**********
	 * List all of the entries in the dictionary
	 * 
	 * @return - an empty String or a String of the sequence of entries that match the search String
	 */
	public String listAll(){
		if (numEntries <=0) return "The dictionary is empty.\n----------\n";
		String result = "";	// Set the result empty and then add all of the entries to it
		for (int ndx = 0; ndx < numEntries; ndx++)
			result += "\n" + (ndx+1) + ". " + entry[ndx].getWord() + ":\n" + entry[ndx].getDefinition() + "\n----------\n";
		return result;		// Return the String  to the caller
	}

	/**********
	 * Get the index of the current entry
	 * 
	 * @return - an index
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**********
	 * Set the index of the current entry
	 * 
	 * @param ndx = values at current index
	 *
	 */
	public void setCurrentIndex(int ndx) {
		currentIndex = ndx;
	}
	
	/**********
	 * Get the index of the current entry
	 * 
	 * @return - an index
	 */
	public int getNumberSearchItemsFound() {
		return numberOfSearchItemsFound;
	}
	
	
}