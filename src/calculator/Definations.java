package calculator;

import calculator.CalculatorValue;

/**
 * <p>
 * Title: DictEntry Class - A class component of the Dictionary system
 * </p>
 *
 * <p>
 * Description: An entity object class that implements a dictionary entry
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2018-05-05
 * </p>
 *
 * @author Lynn Robert Carter
 * updated author Sanchit
 * 
 * @version 2.00 - Baseline for transition from Swing to JavaFX 2018-05-05
 * @version 2.01 - Installed in PKMT Tool
 * @version 2.02 - Updated and enhaced class for PKMT withe genric concept 2019-02-04
 * @version 2.03 - Updated and enhaced class for the support of calculator concept withe genric concept 2019-02-24
  */

public class Definations<T> {
	
	
	/**********************************************************************************************

	Class Attributes
	
	**********************************************************************************************/
	private String word;			// A dictionary entry's word
	public CalculatorValue cv;
	private boolean type;			// A dictionary type to determine the constant or variable
	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/
	
	/**********
	 * This is the default constructor.  We do not expect it to be used.
	 */
	private T CV;			// A dictionary entry's definition with genric data type
	public Definations() {
		word = "";
		CV = (T)"";
	}
	
	/**********
	 * This is defining constructor.  This is the one we expect people to use.
	 * @param w = word
	 * @param d = definaton
	 */
	public Definations(String w, T d) {
		word = w;
		CV = d;
	}
	
	
	/**********
	 * This is defining constructor.  This is the one we expect people to use.
	 * @param w = word
	 * @param d = definaton
	 * @param t = type
	 */
	public Definations(String w, T d, boolean t) {
		word = w;
		CV = d;
		type = t;
	}
	
	
	/**********************************************************************************************

	Standard support methods
	
	**********************************************************************************************/
	
	/**********
	 * This is the debugging toString method.
	 */
	public String toString(){
		return word + "\n" + CV;
	}
	
	/**********
	 * This is the formatted toString method.
	 * @return word + "\n" get the word from the user input
	 */
	public String formattedToString(){
		return word + "\n" + CV + "\n--------------------\n";
	}
	
	public void setWord(String w) {
		word =w;
	}
	public void setDefn(T d) {
		CV=d;
	}
	public void setType(boolean t) {
		type = t;
	}
	
	
	
	/**********
	 * These are the getters and setters for the class
	 * @return word = word
	 * @return defination = defination
	 * @return type = type
	 */
	public String wordToString(){return word + "\n";}
	public String getWord(){return word;}
	public T getDefinition(){return CV;}
	public boolean getType(){return type;}

}
