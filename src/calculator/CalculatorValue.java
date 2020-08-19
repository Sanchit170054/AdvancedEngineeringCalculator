package calculator;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import uNumberLibrary.UNumber;
import uNumberLibrary.UNumberWithGetters;
import calculator.UserInterface;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
/**
 * <p> Title: CalculatorValue Class. </p>
 *
 * <p> Description: A component of a JavaFX demonstration application that performs computations wiht the help of UNUmber library </p>
 *
 * <p> Copyright: Lynn Robert Carter © 2017 </p>
 *
 * @author Lynn Robert Carter
 * Updated Author Sanchit
 * 
 * @version 4.4	2018-02-27 Double implementation of the CalculatorValue class
 * @version 4.5 2018-03-06 Implementation of ErrorTerm
 * @version 4.6 2018-10-06 Computation of all the operators by using the UNumber library and performa its error term also
 * @version 4.7 2018-11-24 This class has been enhaced by some of the essnatial method that has been used in the UserInterface
 */

public class CalculatorValue {
	
	/*********************************************************************************************
	Attributes
	**********************************************************************************************/

	// These are the major values that define a calculator value
	
	String errorMessage = "";
	String newMessage ="Invalid Input";                					// this defines the error message when the divisor is zero
	public static String measuredValueErrorMessage = "";				// The alternate error message text
	public static String measuredValueInput = "";						// The input being processed
	public static int measuredValueIndexofError = -1;					// The index where the error was located
	public static int stateMeasuredValue = 0;							// The current state value
	public static int nextStateMeasuredValue = 0;						// The next state value
	public static boolean finalStateMeasuredValue = false;				// Is this state a final state
	private static String inputLineMeasuredValue = "";					// The input line
	private static char currentCharMeasuredValue;						// The current character in the line
	private static int currentCharNdxMeasuredValue;						// The index of the current character
	private static boolean runningMeasuredValue;						// The flag that specifies if it is running
	public static String errorTermErrorMessage = "";					// The alternate error message text
	public static String errorTermInput = "";							// The input being processed
	public static int errorTermIndexofError = -1;						// The index where the error was located
	private static int stateErrorTerm = 0;								// The current state value
	private static int nextStateErrorTerm = 0;							// The next state value
	private static boolean finalStateErrorTerm = false;					// Is this state a final state
	private static String inputLineErrorTerm = "";						// The input line
	private static char currentCharErrorTerm;							// The current character in the line
	private static int currentCharNdxErrorTerm;							// The index of the current character
	private static boolean runningErrorTerm;							// The flag that specifies if it is running

	double measuredValue = 0;
	UNumber StringMeasuredValue;
	double MV = 0.00;
	UNumber StringErrorTermValue;
	public static String errorTerm1 = "0.0";
	public static String errorTerm2 = "0.0";
	public static double errorTerm = 0.00;
	
	public static String errorTermInputLine = "";
	public static boolean errorTermRunning;
	public static boolean errorTermFinalState = false;
	
	double operand1LowerBound = 0.00;
	double operand2LowerBound = 0.00;
	double operand1UpperBound = 0.00;
	double operand2UpperBound = 0.00;
	double resultLowerBound = 0.00;
	double resultUpperBoundB = 0.00;
	
	String zeroMessage ="Invalid Input";                // this defines the error message when the divisor is zero
	public static int state = 0;						// The current state value
	public static int nextState = 0;					// The next state value
	public static boolean finalState = false;			// Is this state a final state
	
	
	
	
	private  boolean CalculationIsPossible = true;  // check whethe the calculation possible or not
	
	// Here is the lookup table whihc is used in mapping of units and its conversions value

	public String[][] UnitsWithValue ={
			{" "            , "m"      , "km"     , "miles"      , "days"         , "seconds", "km/seconds", "miles/seconds"},
			{"m"            , "1"      , "0.001"  , "0.000621371", "-"            , "-"      , "-"         , "-"            },
			{"km"           , "1000"   , "1"      , "0.621371"   , "-"            , "-"      , "-"         , "-"            },
			{"miles"        , "1609.34", "1.60934", "1"          , "-"            , "-"      , "-"         , "-"            }, 
			{"days"         , "-"      , "-"      , "-"          , "1"            , "86400"  , "-"         , "-"            }, 
			{"seconds"      , "-"      , "-"      , "-"          , "0.00001157407", "1"      , "-"         , "-"            },
			{"km/seconds"   , "-"      , "-"      , "-"          , "-"            , "-"      , "1"         , "0.621371"     },
			{"miles/seconds", "-"      , "-"      , "-"          , "-"            , "-"      , "1.60934"   , "1"            },};

	// This will check whether the conversion is possible or not, if come true then conversion is possible else not
	
	public boolean ConversionChecker[][] = {
			{false ,false ,false ,false ,false ,false ,false ,false }, 
			{false ,true  ,true  ,true  ,false ,false ,false ,false }, 
			{false ,true  ,true  ,true  ,false ,false ,false ,false }, 
			{false ,true  ,true  ,true  ,false ,false ,false ,false }, 
			{false ,false ,false ,false ,true  ,true  ,false ,false }, 
			{false ,false ,false ,false ,true  ,true  ,false ,false }, 
			{false ,false ,false ,false ,false ,false ,true  ,true  }, 
			{false ,false ,false ,false ,false ,false ,true  ,true  },};

	
	/**********************************************************************************************

	Constructors

	**********************************************************************************************/

	/*****
	 * This is the default constructor
	 */
	public CalculatorValue() {
	}

	/*****
	 * This constructor creates a calculator value based on a double. For future calculators, it
	 * is best to avoid using this constructor.
	 * @param v = create the calculator value based on a double
	 */
	public CalculatorValue(double v) {
		measuredValue = v;
	}

	/*****
	 * This copy constructor creates a duplicate of an already existing calculator value
	 * @param v = calculate the error term
	 */
	public CalculatorValue(CalculatorValue v) {
		measuredValue = v.measuredValue;
		errorMessage = v.errorMessage;
	}

	/*****
	 * This constructor creates a calculator value from a string... Due to the nature
	 * of the input, there is a high probability that the input has errors, so the
	 * routine returns the value with the error message value set to empty or the string
	 * of an error message.
	 * 
	 * @param s = create calculator value from string
	 */
	public CalculatorValue(String s) {
		measuredValue = 0;
		if (s.length() == 0) {							     				// If there is nothing there,
			errorMessage = "***Error*** Input is empty";					// signal an error
			return;
		}
		// If the first character is a plus sign, ignore it.
		int start = 0;														// Start at character position zero
		boolean negative = false;											// Assume the value is not negative

		switch(s.charAt(start)) {
		case 1: start++; break;                             				//Switch case is used to check sign only once as break will stops
		case 2: start++; negative =true; break;             				// after one check and two consecutive signs will be considered as
		                                                    				//an invalid input
				}

		// See if the user-entered string can be converted into an double value
		Scanner tempScanner = new Scanner(s.substring(start));				// Create scanner for the digits
		if (!tempScanner.hasNextDouble()) {									// See if the next token is a valid
			errorMessage = "***Error*** Invalid value";
			tempScanner.close();
			return;
		}

		// Convert the user-entered string to a integer value and see if something else is following it
		measuredValue = tempScanner.nextDouble();							// Convert the value and check to see
		if (tempScanner.hasNext()) {										// that there is nothing else is
			errorMessage = "***Error*** Excess data"; 						// following the value.  If so, it
			tempScanner.close();											// is an error.  Therefore we must
			measuredValue = 0;												// return a zero value.
			return;
		}
		tempScanner.close();
		errorMessage = "";
		if (negative)														// Return the proper value based
			measuredValue = -measuredValue;									// on the state of the flag that
	}

	/**********************************************************************************************

	Getters and Setters

	**********************************************************************************************/

	/*****
	 * This is the start of the getters and setters
	 *
	 * Get the error message
	 * @return error messages
	 * @return new message
	 */
	public String getErrorMessage(){
		return errorMessage;
	}
	public String getNewMessage () {
		return newMessage;                            						// getter when the value of divisor is 0

	}

	/*****
	 * Set the current value of a calculator value to a specific long integer
	 * @param v store the double value into m
	 */
	public void setValue(double v){
		measuredValue = v;
	}

	/*****
	 * Set the current value of a calculator error message to a specific string
	 * @param m set the current value of a calculator
	 */
	public void setErrorMessage(String m){
		errorMessage = m;
	}
	public void setNewMessage (String n){
		newMessage = n;                                  					// setter when the value of divisor is 0
	}

	/*****
	 * Set the current value of a calculator value to the value of another (copy)
	 * 
	 * @param v  set current value of a character
	 */
	public void setValue(CalculatorValue v){
		measuredValue = v.measuredValue;
		errorMessage = v.errorMessage;
	}

	/**********************************************************************************************

	The toString() Method

	**********************************************************************************************/

	/*****
	 * This is the default toString method
	 *
	 * When more complex calculator values are creating this routine will need to be updated
	 */
	public String toString() {
		return StringMeasuredValue.toDecimalString();
	}
	public String toStringE() {
		return StringErrorTermValue.toDecimalString();
	}

	/*****
	 * This is the debug toString method
	 *
	 * When more complex calculator values are creating this routine will need to be updated
	 * @return error message
	 */
	public String debugToString() {
		return "measuredValue = " + measuredValue + "\nerrorMessage = " + errorMessage + "\n";
	}


	/**********************************************************************************************

	The computation methods

	**********************************************************************************************/

	/***********************************************************
	 * Computation of Addition of Operands Using UNumber Library
	 ***********************************************************/
	/*******************************************************************************************************
	 * The following methods implement computation of addition on the calculator values.  These routines assume that the
	 * caller has verified that things are okay for the operation to take place. This method perform the task by using the 
	 * UNumber Library and can compute the values up to 25 significant digits.
	 * Since this is addition and we do not yet support units, we don't recognize any errors.
	 * 
	 * @param v pass the parameters for the computations
	 */
	
	public void add(CalculatorValue v) {
		
		double s = Double.parseDouble(errorTerm1);
	    double a = Double.parseDouble(errorTerm2);
	    
		UNumber MeasuredvalueOP1 = new UNumber(measuredValue);  //create the variable to store the first operand Value
		MeasuredvalueOP1 = new UNumber(MeasuredvalueOP1, 25);
		UNumber MeasuredvalueOP2 = new UNumber(v.measuredValue); //create the variable to store the Second Operand Value
		MeasuredvalueOP2 = new UNumber(MeasuredvalueOP2, 25);
		UNumber errorTerm1 = new UNumber(s);  //create the variable to store the error TErm1
		errorTerm1 = new UNumber(errorTerm1, 25); 
		
		UNumber errorTerm2 = new UNumber(a);  //create the variable to store the error Term2
		errorTerm1 = new UNumber(errorTerm1, 25);
		
		UNumber two = new UNumber(2.0);
		two = new UNumber(two, 25);
		
		UNumber copyOP1 = new UNumber(MeasuredvalueOP1);  //save the first Operand Value
		copyOP1 = new UNumber(copyOP1, 25);
		UNumber copyOP2 = new UNumber(MeasuredvalueOP2);// save the second Operand value
		copyOP2 = new UNumber(copyOP2, 25);
	    copyOP1.sub(errorTerm1); // compute  the Lower bound of First operand
		errorTerm1.add(MeasuredvalueOP1);  // Compute the upper bound of the first Operand
		
		copyOP2.sub(errorTerm2); // compute the lower bound of the second operand
		errorTerm2.add(MeasuredvalueOP2); // Compete the upper bound of the second operand
		
		copyOP1.add(copyOP2);  // compute the result lower bound
		errorTerm1.add(errorTerm2);  // compute the result upper bound
		  
		copyOP1.add(errorTerm1);copyOP1.div(two);  // compute the result value
		
		StringMeasuredValue = new UNumber(copyOP1, 25);  // Store the value to display it on UI
		errorMessage = "";

	}
	/***********************************************************
	 * Computation of Subtraction of Operands Using UNumber Library
	 ***********************************************************/

	/*******************************************************************************************************
	 * The following methods implement computation of subtraction on the calculator values.  These routines assume that the
	 * caller has verified that things are okay for the operation to take place. This method perform the task by using the 
	 * UNumber Library and can compute the values up to 25 significant digits.
	 *
	 * @param v error term
	 */
	public void sub(CalculatorValue v) {
//		
		double s = Double.parseDouble(errorTerm1); 
	    double a = Double.parseDouble(errorTerm2);
//		
		UNumber MeasuredvalueOP1 = new UNumber(measuredValue); //create the variable to store the first operand Value
		MeasuredvalueOP1 = new UNumber(MeasuredvalueOP1, 25); 
		UNumber MeasuredvalueOP2 = new UNumber(v.measuredValue);  //Create the variable to store the second OPeradns value
		MeasuredvalueOP2 = new UNumber(MeasuredvalueOP2, 25);
		UNumber errorTerm1 = new UNumber(s);  //Create the variable to hold the error Term 1
		errorTerm1 = new UNumber(errorTerm1, 25);
		UNumber errorTerm2 = new UNumber(a); //Create the variable to hold the error term 2
		errorTerm2 = new UNumber(errorTerm2, 25);
		UNumber two = new UNumber(2.0); // Variable to store the value 2.0
		two = new UNumber(two, 25);
		
		UNumber copyOP1 = new UNumber(MeasuredvalueOP1);   // save the copy for the first Operands
		copyOP1 = new UNumber(copyOP1, 25);  
		UNumber copyOP2 = new UNumber(MeasuredvalueOP2); // save the copy for the second operand
		copyOP2 = new UNumber(copyOP2, 25);
		
	    copyOP1.sub(errorTerm1);  // compute the Lower bound for the Operand 1
		errorTerm1.add(MeasuredvalueOP1);  // compute the Upper bound for the Operand 1
		
		copyOP2.sub(errorTerm2);  //compute the Lower bound for the Operand 2
		errorTerm2.add(MeasuredvalueOP2);  // compute the Upper bound for the operand 2

		copyOP1.sub(copyOP2);  //compute the lower bound
		errorTerm1.sub(errorTerm2); // compute the Upper bound
		
		copyOP1.add(errorTerm1);copyOP1.div(two); //compute the result
		
		StringMeasuredValue = new UNumber(copyOP1,25); // Store the resultant value into UNumebr object
		errorMessage = "";
		
	}
	
	/***********************************************************
	 * Computation of Multiplication of Operands Using UNumber Library
	 ***********************************************************/
	
	/*******************************************************************************************************
	 * The following methods implement computation of multiplication on the calculator values.  These routines assume that the
	 * caller has verified that things are okay for the operation to take place. This method perform the task by using the 
	 * UNumber Library and can compute the values up to 25 significant digits.
	 *
	 * @param v  pass the parameters for the computation
	 */

	public void mpy(CalculatorValue v) {

		double s = Double.parseDouble(errorTerm1);
	    double a = Double.parseDouble(errorTerm2);	
	    
		UNumber MeasuredvalueOP1 = new UNumber(measuredValue);  //create the variable to store the first operand Value
		MeasuredvalueOP1 = new UNumber(MeasuredvalueOP1, 25);

		UNumber MeasuredvalueOP2 = new UNumber(v.measuredValue);  //Create the variable to store the second OPeradns value
		MeasuredvalueOP2 = new UNumber(MeasuredvalueOP2, 25);
		
		UNumber errorTerm1 = new UNumber(s);  //Create the variable to hold the error Term 1
		errorTerm1 = new UNumber(errorTerm1, 25); 
		
		UNumber errorTerm2 = new UNumber(a);  //Create the variable to hold the error term 2
		errorTerm2 = new UNumber(errorTerm2, 25);
		
		UNumber two = new UNumber(2.0); // Variable to store the value 2.0
		two = new UNumber(two, 25);
	
		UNumber copyOP1 = new UNumber(MeasuredvalueOP1);  // save the copy for the first Operands
		copyOP1 = new UNumber(copyOP1, 25);
		
		UNumber copyOP2 = new UNumber(MeasuredvalueOP2);  // save the copy for the second operand
		copyOP2 = new UNumber(copyOP2, 25);
		
	    copyOP1.sub(errorTerm1);  // compute the Lower bound for the Operand 1
		errorTerm1.add(MeasuredvalueOP1); // compute the Upper bound for the Operand 2
		
		copyOP2.sub(errorTerm2);   //compute the Lower bound for the Operand 2
		errorTerm2.add(MeasuredvalueOP2);  //compute the Upper bound for the Operand 2
		
		copyOP1.mpy(copyOP2);  //compute the lower bound
		errorTerm1.mpy(errorTerm2);  // compute the Upper bound
		
		copyOP1.add(errorTerm1);copyOP1.div(two);  //compute the result 
		
		StringMeasuredValue = new UNumber(copyOP1,25); //Store the value into variable to display the result
		errorMessage = "";
		
	}
		
	/***********************************************************************
	*Computation of the Error Terms for multiplication using UNumber Library
	************************************************************************/
	
	/*******************************************************************************************************
	 * The following methods implement computation of subtraction for the error terms.  These routines assume that the
	 * caller has verified that things are okay for the operation to take place. This method perform the task by using the 
	 * UNumber Library and can compute the values up to 25 significant digits.
	 * 
	 * @param v = error term1
	 * @param m = first operand
	 * @param e = error term 2
	 * @param s = second operand
	 */

	public void mpyE(CalculatorValue v, CalculatorValue m, CalculatorValue e, CalculatorValue s) {
		
		UNumber VMeasuredValue = new UNumber(v.measuredValue);  // create the variable for the error term 1
		VMeasuredValue = new UNumber(VMeasuredValue, 25);
		
		UNumber MMeasuredValue = new UNumber(m.measuredValue); // create the value for the FIrst Operand
		MMeasuredValue = new UNumber(MMeasuredValue, 25);
		
		UNumber EMeasuredVaue = new UNumber(e.measuredValue);  // create the variable to hold the error term 2
		EMeasuredVaue = new UNumber(EMeasuredVaue, 25);
	
		UNumber SMeausuredValue = new UNumber(s.measuredValue);  // create the variable to second Operand
        SMeausuredValue = new UNumber(SMeausuredValue, 25);
        
        UNumber CM = new UNumber(m.measuredValue); // save the copy of FIrst Operand
		CM = new UNumber(CM, 25);
		
		UNumber CS = new UNumber(s.measuredValue);  // save the copy of the Second Operand
		CS = new UNumber(CS, 25);
		
		VMeasuredValue.div(MMeasuredValue);  // compute the fraction 1
		EMeasuredVaue.div(SMeausuredValue);   // compute the fraction 2
		
		VMeasuredValue.add(EMeasuredVaue);  // now add the fraction 1 and fraction 2
		CM.mpy(CS); // multipley the operand 1 and operand 2 values
		
		VMeasuredValue.mpy(CM);  //compute the resultant error term
		StringErrorTermValue = new UNumber(VMeasuredValue, 25); // store the result value in the UNumber object

		errorMessage = "";
			}

	/********************************************************
	*Computation of division of Operands using UNumber Library
	**********************************************************/
	
	/*******************************************************************************************************
	 * The following methods implement computation of division on the calculator values.  These routines assume that the
	 * caller has verified that things are okay for the operation to take place. This method perform the task by using the 
	 * UNumber Library and can compute the values up to 25 significant digits.
	 *
	 * @param v pass the parameters to calculate the result
	 */

	public void div(CalculatorValue v) {

		double s = Double.parseDouble(errorTerm1);
	    double a = Double.parseDouble(errorTerm2);
	
	    UNumber MeasuredvalueOP1 = new UNumber(measuredValue);  //create the variable to store the first operand Value
		MeasuredvalueOP1 = new UNumber(MeasuredvalueOP1, 25);
	
		UNumber MeasuredvalueOP2 = new UNumber(v.measuredValue); //Create the variable to store the second OPeradns value
		MeasuredvalueOP2 = new UNumber(MeasuredvalueOP2, 25);
		
		UNumber errorTerm1 = new UNumber(s); //Create the variable to hold the error Term 1
		errorTerm1 = new UNumber(errorTerm1, 25);
		
		UNumber errorTerm2 = new UNumber(a);  //Create the variable to hold the error term 2
		errorTerm2 = new UNumber(errorTerm2, 25);
		
		UNumber two = new UNumber(2.0);  // Variable to store the value 2.0
		two = new UNumber(two, 25);
		
		UNumber copyOP1 = new UNumber(measuredValue);  // save the copy for the first Operands
		copyOP1 = new UNumber(copyOP1, 25);
	
		UNumber copyOP2 = new UNumber(v.measuredValue);  // save the copy for the second operand
		copyOP2 = new UNumber(copyOP2, 25);
		
	    copyOP1.sub(errorTerm1);   // compute the Lower bound for the Operand 1
		errorTerm1.add(MeasuredvalueOP1); // compute the Upper bound for the Operand 1
		
		copyOP2.sub(errorTerm2);  // compute the Lower bound for the Operand 2
		errorTerm2.add(MeasuredvalueOP2);  // compute the upper bound for the Operand 2
		
		copyOP1.div(copyOP2); //compute the lower bound
		errorTerm1.div(errorTerm2); //compute the Upper bound
		
		copyOP1.add(errorTerm1);copyOP1.div(two); // compute the result value
		
		StringMeasuredValue = new UNumber(copyOP1,25); // store the resultant value to print the result in UI
	    errorMessage = "";
		
		}
	
	/******************************************************************
	 * Computation of Error Terms for the division using UNumber Library
	 * ****************************************************************/
	
	/*******************************************************************************************************
	 * The following methods implement computation of division for the error terms. These routines assume that the
	 * caller has verified that things are okay for the operation to take place. This method perform the task by using the 
	 * UNumber Library and can compute the values up to 25 significant digits.
	 * 
	 * @param v = error term1
	 * @param m = first operand
	 * @param e = error term 2
	 * @param s = second operand
	 */

	public void divE(CalculatorValue v, CalculatorValue m, CalculatorValue e, CalculatorValue s) {
		UNumber VMeasuredValue = new UNumber(v.measuredValue);  // create the variable for the error term 1
		VMeasuredValue = new UNumber(VMeasuredValue, 25);
		
		UNumber MMeasuredValue = new UNumber(m.measuredValue);  // create the variable for the first Operand
		MMeasuredValue = new UNumber(MMeasuredValue, 25);
		
		UNumber EMeasuredVaue = new UNumber(e.measuredValue);  // create the variable for the error term 2
		EMeasuredVaue = new UNumber(EMeasuredVaue, 25);

		UNumber SMeausuredValue = new UNumber(s.measuredValue); // create the variable for the second Operand
        SMeausuredValue = new UNumber(SMeausuredValue, 25);
        
        UNumber CM = new UNumber(m.measuredValue); // Save the first Operands
		CM = new UNumber(CM, 25);
		
		UNumber CS = new UNumber(s.measuredValue);  // save the second operand
		CS = new UNumber(CS, 25);
		
		VMeasuredValue.div(MMeasuredValue);  // COmpute the fraction 1
		EMeasuredVaue.div(SMeausuredValue);  // compute the fraction 2
		
		VMeasuredValue.add(EMeasuredVaue);  // add both the fractions
		CM.div(CS);  // multiply first and second operand
		
		VMeasuredValue.mpy(CM);  // compute the result error term
		StringErrorTermValue = new UNumber(VMeasuredValue, 25);  // store the error term to print it on UI
		
		errorMessage = "";
		
	}
	
	/******************************************************************
	 * Computation of Square root of First Operand using UNumber Library
	 * ****************************************************************/
	
	/*******************************************************************************************************
	 * The following methods implement computation of square root on the calculator values.  These routines assume that the
	 * caller has verified that things are okay for the operation to take place. This method perform the task by using the 
	 * UNumber Library and can compute the values up to 25 significant digits.
	 *
	 *
	 * @param v pass the pararmter to calculate the square root
	 */
	public void sqrt(CalculatorValue v){
	
	int numSigDigits = 22; // Specify the number of significant digits
	int digitsMatch = 0; // this is used to hold the number of matching significant digits
	
		UNumberWithGetters two = new UNumberWithGetters(2.0); // create the variable 'two2' and store the value 2.0 in it
		two = new UNumberWithGetters(two, numSigDigits);
		
		
		UNumberWithGetters MeasuredValue1 = new UNumberWithGetters(measuredValue); // create the variable first Operand value into it
		MeasuredValue1 = new UNumberWithGetters(MeasuredValue1, numSigDigits);
		
		UNumberWithGetters CopyOP1 = new UNumberWithGetters(MeasuredValue1);	// Save the copy of the First Operand
		CopyOP1 = new UNumberWithGetters(CopyOP1, numSigDigits);
	
	    CopyOP1.div(two);  //divide the first Operand by 2.0
	 
		UNumberWithGetters oldGuess2;   //declare the new variable to hold the value
		
	    digitsMatch = 0;  // Initialize the digital match from 0
	  
	do
	{
		int iteration = 0;  // Initialize the iteration from 0
		iteration++;
		oldGuess2 = CopyOP1;  // save the updated first operand value into new guess 2
		
		CopyOP1 = new UNumberWithGetters(MeasuredValue1);  // again update the value for the copy operand
		CopyOP1.div(oldGuess2);CopyOP1.add(oldGuess2);CopyOP1.div(two);  // apply the neaten raphson method for the computation and store the result into
		                                                                  // copyOP1
	    digitsMatch = howManyDigitsMatch2(CopyOP1, oldGuess2, numSigDigits);  // call the method
	}
	while (digitsMatch < numSigDigits);  // use condition to get the precise value up to 25 digits
	
	StringMeasuredValue = new UNumberWithGetters(CopyOP1,22);  // Store the resultant value to display it on UI
	errorMessage = "";
	
   }

      private int howManyDigitsMatch2(UNumberWithGetters newGuess, UNumberWithGetters oldGuess, int maxMatchingDigits) {
	// TODO Auto-generated method stub
	// If the characteristics are not the same, the digits in the mantissa do not matter
			if (newGuess.getCharacteristic() != oldGuess.getCharacteristic()) return 0;

			// The characteristic is the same, so fetch the mantissas so we can compare them
			String newGuessStr = newGuess.getMantissa();
			String oldGuessStr = oldGuess.getMantissa();
			// Set the upper limit;
			int maxIterations = maxMatchingDigits;

			// No need to do this because we are working with the mantissa, so there are not decimal points
			for (int ndx = 0; ndx<15; ndx++) {
				if (newGuessStr.charAt(ndx) == '.') {
					String start = newGuessStr.substring(0, ndx);
					String rest = newGuessStr.substring(ndx+1);
					newGuessStr = start + rest;
					break;
				}
			}

			for (int ndx = 0; ndx<maxMatchingDigits; ndx++) 
				if (oldGuessStr.charAt(ndx) == '.') {
					String start = oldGuessStr.substring(0, ndx);
					String rest = oldGuessStr.substring(ndx+1);
					oldGuessStr = start + rest;
					break;
				}
			

			// Loop through the digits as long as they match
			for (int ndx = 0; ndx < maxIterations; ndx++) {
				if (oldGuessStr.charAt(ndx) != newGuessStr.charAt(ndx)) return ndx;
			}

			// If the loop completes, we consider all 15 to match
			return maxMatchingDigits;
}

/***********************************************************************************
 * Computation of Square root for the first Operand Error terms using UNumber Library
 ***********************************************************************************/

/*******************************************************************************************************
 * The following methods implement computation of square root for the error terms. These routines assume that the
 * caller has verified that things are okay for the operation to take place. This method perform the task by using the 
 * UNumber Library and can compute the values up to 25 significant digits.
 * 
 *  @param v = error term1
 *  @param m = first operand
 */
	public void sqrtE(CalculatorValue v, CalculatorValue m) {
		
		UNumber VMeausredValue = new UNumber(v.measuredValue); // create the variable for the error term 1
		VMeausredValue = new UNumber(VMeausredValue,20);
		
		UNumber MMeasuredValue = new UNumber(m.measuredValue); // create the variable for the first Operand
		MMeasuredValue = new UNumber(MMeasuredValue, 20);
		
		UNumber one = new UNumber(0.5); // create the variable to store 0.5 value
		
		VMeausredValue.div(MMeasuredValue);  // create the variable for the error term 2
		VMeausredValue.mpy(one);
		
		UNumber MV1 = new UNumber(measuredValue); // create the variable for the second Operand
		MV1.mpy(VMeausredValue); // Compute resultant value
		
		StringErrorTermValue = new UNumber(VMeausredValue, 22);  //Store the resultant value to print it on UI
		errorMessage = "";
	
		
	}
	private static String displayInput(String input, int currentCharNdx) {
		// Display the entire input line
		String result = input + "\n";

		// Display a line with enough spaces so the up arrow point to the point of an error
		for (int ndx=0; ndx < currentCharNdx; ndx++) result += " ";

		// Add the up arrow to the end of the second line
		return result + "\u21EB";				// A Unicode up arrow with a base
	}


	
	private static void moverrorTermoNextCharacterMeasuredValue() {
		currentCharNdxMeasuredValue++;
		if (currentCharNdxMeasuredValue < inputLineMeasuredValue.length())
			currentCharMeasuredValue = inputLineMeasuredValue.charAt(currentCharNdxMeasuredValue);
		else {
			currentCharMeasuredValue = ' ';
			runningMeasuredValue = false;
		}
	}

	private static void moveToNextCharacterErrorTerm() {
		currentCharNdxErrorTerm++;
		if (currentCharNdxErrorTerm < inputLineErrorTerm.length())
			currentCharErrorTerm = inputLineErrorTerm.charAt(currentCharNdxErrorTerm);
		else {
			currentCharErrorTerm = ' ';
			runningErrorTerm = false;
		}
	}

	/**********
	 * This method is a mechanical transformation of a Finite State Machine diagram into a Java
	 * method.
	 *
	 * @param input		The input string for the Finite State Machine
	 * @return			An output string that is empty if every things is okay or it will be
	 * 						a string with a help description of the error follow by two lines
	 * 						that shows the input line follow by a line with an up arrow at the
	 *						point where the error was found.
	 */
	public static String checkMeasureValue(String input) {
		if(input.length() <= 0) return "";
		// The following are the local variable used to perform the Finite State Machine simulation
		stateMeasuredValue = 0;							// This is the FSM state number
		inputLineMeasuredValue = input;					// Save the reference to the input line as a global
		currentCharNdxMeasuredValue = 0;					// The index of the current character
		currentCharMeasuredValue = input.charAt(0);		// The current character from the above indexed position

		// The Finite State Machines continues until the end of the input is reached or at some
		// state the current character does not match any valid transition to a next state

		measuredValueInput = input;			// Set up the alternate result copy of the input
		runningMeasuredValue = true;						// Start the loop


		// The Finite State Machines continues until the end of the input is reached or at some
		// state the current character does not match any valid transition to a next state
		while (runningMeasuredValue) {
			// The switch statement takes the execution to the code for the current state, where
			// that code sees whether or not the current character is valid to transition to a
			// next state
			switch (stateMeasuredValue) {
			case 0:
				// State 0 has three valid transitions.  Each is addressed by an if statement.

				// This is not a final state
				finalStateMeasuredValue = false;

				// If the current character is in the range from 1 to 9, it transitions to state 1
				if (currentCharMeasuredValue >= '0' && currentCharMeasuredValue <= '9') {
					nextStateMeasuredValue = 1;
					break;
				}
				// If the current character is a decimal point, it transitions to state 3
				else if (currentCharMeasuredValue == '.') {
					nextStateMeasuredValue = 3;
					break;
				}

				else if(currentCharMeasuredValue =='-') {
					nextStateMeasuredValue = 0;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningMeasuredValue = false;

				// The execution of this state is finished
				break;

			case 1:
				// State 1 has three valid transitions.  Each is addressed by an if statement.

				// This is a final state
				finalStateMeasuredValue = true;

				// In state 1, if the character is 0 through 9, it is accepted and we stay in this
				// state
				if (currentCharMeasuredValue >= '0' && currentCharMeasuredValue <= '9') {
					nextStateMeasuredValue = 1;
					break;
				}

				// If the current character is a decimal point, it transitions to state 2
				else if (currentCharMeasuredValue == '.') {
					nextStateMeasuredValue = 2;
					break;
				}
				// If the current character is an E or an e, it transitions to state 5
				else if (currentCharMeasuredValue == 'E' || currentCharMeasuredValue == 'e'
						) {
					nextStateMeasuredValue = 5;
					break;
				}
				// If it is none of those characters, the FSM halts
				else
					runningMeasuredValue = false;

				// The execution of this state is finished
				break;

			case 2:
				// State 2 has two valid transitions.  Each is addressed by an if statement.

				// This is a final state
				finalStateMeasuredValue = true;

				// If the current character is in the range from 1 to 9, it transitions to state 1
				if (currentCharMeasuredValue >= '0' && currentCharMeasuredValue <= '9') {
					nextStateMeasuredValue = 2;
					break;
				}
				// If the current character is an 'E' or 'e", it transitions to state 5
				else if (currentCharMeasuredValue == 'E' || currentCharMeasuredValue == 'e') {
					nextStateMeasuredValue = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningMeasuredValue = false;

				// The execution of this state is finished
				break;

			case 3:
				// State 3 has only one valid transition.  It is addressed by an if statement.

				// This is not a final state
				finalStateMeasuredValue = false;

				// If the current character is in the range from 1 to 9, it transitions to state 1
				if (currentCharMeasuredValue >= '0' && currentCharMeasuredValue <= '9') {
					nextStateMeasuredValue = 4;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningMeasuredValue = false;

				// The execution of this state is finished
				break;

			case 4:
				// State 4 has two valid transitions.  Each is addressed by an if statement.

				// This is a final state
				finalStateMeasuredValue = true;

				// If the current character is in the range from 1 to 9, it transitions to state 4
				if (currentCharMeasuredValue >= '0' && currentCharMeasuredValue <= '9') {
					nextStateMeasuredValue = 4;
					break;
				}
				// If the current character is an 'E' or 'e", it transitions to state 5
				else if (currentCharMeasuredValue == 'E' || currentCharMeasuredValue == 'e') {
					nextStateMeasuredValue = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningMeasuredValue = false;

				// The execution of this state is finished
				break;

			case 5:
                 //State 5 has two valid transitions.  Each is addressed by an if statement.


				finalStateMeasuredValue = false;

				// If the current character is in the range from 1 to 9, it transitions to state 4
				if (currentCharMeasuredValue >= '0' && currentCharMeasuredValue <= '9') {
					nextStateMeasuredValue = 7;
					break;
				}
				// If the current character is an 'E' or 'e", it transitions to state 5
				else if (currentCharMeasuredValue == '+' || currentCharMeasuredValue == '-') {
					nextStateMeasuredValue = 6;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningMeasuredValue = false;

				// The execution of this state is finished
				break;

			case 6:
                 //State 6 has one valid transitions.  It is addressed by an if statement.


				finalStateMeasuredValue = false;

				// If the current character is in the range from 1 to 9, it transitions to state 4
				if (currentCharMeasuredValue >= '0' && currentCharMeasuredValue <= '9') {
					nextStateMeasuredValue = 7;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningMeasuredValue = false;

				// The execution of this state is finished
				break;

			case 7:
                  //State 7 has one valid transitions.  It is addressed by an if statement.

				// This is a final state
				finalStateMeasuredValue = true;

				// If the current character is in the range from 1 to 9, it transitions to state 4
				if (currentCharMeasuredValue >= '0' && currentCharMeasuredValue <= '9') {
					nextStateMeasuredValue = 7;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningMeasuredValue = false;

				// The execution of this state is finished
				break;

			}

			if (runningMeasuredValue) {

				// When the processing of a state has finished, the FSM proceeds to the next character
				// in the input and if there is one, it fetches that character and updates the
				// currentChar.  If there is no next character the currentChar is set to a blank.
				moverrorTermoNextCharacterMeasuredValue();

				// Move to the next state
				stateMeasuredValue = nextStateMeasuredValue;

			}
			// Should the FSM get here, the loop starts again

		}

		measuredValueIndexofError = currentCharNdxMeasuredValue;		// Copy the index of the current character;

		// When the FSM halts, we must determine if the situation is an error or not.  That depends
		// of the current state of the FSM and whether or not the whole string has been consumed.
		// This switch directs the execution to separate code for each of the FSM states.
		switch (stateMeasuredValue) {
		case 0:
			// State 0 is not a final state, so we can return a very specific error message
			measuredValueIndexofError = currentCharNdxMeasuredValue;		// Copy the index of the current character;
			measuredValueErrorMessage = "The first character must be a digit or a decimal point.";
			return "The first character must be a \"+\" sign, digit a decimal point.";

		case 1:
			// State 1 is a final state, so we must see if the whole string has been consumed.
			if (currentCharNdxMeasuredValue<input.length()) {
				// If not all of the string has been consumed, we point to the current character
				// in the input line and specify what that character must be in order to move
				// forward.
				measuredValueErrorMessage = "This character may only be an \"E\", an \"e\", a digit, "
						+ "a \".\", or it must be the end of the input.\n";
				return measuredValueErrorMessage + displayInput(input, currentCharNdxMeasuredValue);
			}
			else {
				measuredValueIndexofError = -1;
				measuredValueErrorMessage = "";
				return measuredValueErrorMessage;
			}

		case 2:
		case 4:
			// States 2 and 4 are the same.  They are both final states with only one possible
			// transition forward, if the next character is an E or an e.
			if (currentCharNdxMeasuredValue<input.length()) {
				measuredValueErrorMessage = "This character may only be an \"E\", an \"e\", a digit or it must"
						+ " be the end of the input.\n";
				return measuredValueErrorMessage + displayInput(input, currentCharNdxMeasuredValue);
			}
			// If there is no more input, the input was recognized.
			else {
				measuredValueIndexofError = -1;
				measuredValueErrorMessage = "";
				return measuredValueErrorMessage;
			}
		case 3:
		case 6:
			// States 3, and 6 are the same. None of them are final states and in order to
			// move forward, the next character must be a digit.
			measuredValueErrorMessage = "This character may only be a digit.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdxMeasuredValue);

		case 7:
			// States 7 is similar to states 3 and 6, but it is a final state, so it must be
			// processed differently. If the next character is not a digit, the FSM stops with an
			// error.  We must see here if there are no more characters. If there are no more
			// characters, we accept the input, otherwise we return an error
			if (currentCharNdxMeasuredValue<input.length()) {
				measuredValueErrorMessage = "This character may only be a digit.\n";
				return measuredValueErrorMessage + displayInput(input, currentCharNdxMeasuredValue);
			}
			else {
				measuredValueIndexofError = -1;
				measuredValueErrorMessage = "";
				return measuredValueErrorMessage;
			}

		case 5:
			// State 5 is not a final state.  In order to move forward, the next character must be
			// a digit or a plus or a minus character.
			measuredValueErrorMessage = "This character may only be a digit, a plus, or minus "
					+ "character.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdxMeasuredValue);


		default:
			return "";
		}
	}

	public static String checkErrorTerm(String input) {
		if(input.length() <= 0) return "";
		// The following are the local variable used to perform the Finite State Machine simulation
		stateErrorTerm = 0;							// This is the FSM state number
		inputLineErrorTerm = input;					// Save the reference to the input line as a global
		currentCharNdxErrorTerm = 0;					// The index of the current character
		currentCharErrorTerm = input.charAt(0);		// The current character from the above indexed position

		// The Finite State Machines continues until the end of the input is reached or at some
		// state the current character does not match any valid transition to a next state

		errorTermInput = input;			// Set up the alternate result copy of the input
		runningErrorTerm = true;						// Start the loop

// The Finite State Machines continues until the end of the input is reached or at some
		// state the current character does not match any valid transition to a next state
		while (runningErrorTerm) {
			// The switch statement takes the execution to the code for the current state, where
			// that code sees whether or not the current character is valid to transition to a
			// next state
			switch (stateErrorTerm) {
			case 0:
				// State 0 has three valid transitions.  Each is addressed by an if statement.

				// This is not a final state
				finalStateErrorTerm = false;

				// If the current character is in the range from 1 to 9, it transitions to state 1
				if (currentCharErrorTerm >= '1' && currentCharErrorTerm <= '9') {
					nextStateErrorTerm = 1;
					break;
				}
				// If the current character is a decimal point, it transitions to state 3
				else if (currentCharErrorTerm == '.') {
					nextStateErrorTerm = 3;
					break;
				}
				// If the current character is 0, it transitions to state 8
				else if (currentCharErrorTerm == '0') {
					nextStateErrorTerm = 8;
					break;
				}
				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;

			case 1:
				// State 1 has three valid transitions.  Each is addressed by an if statement.

				// This is a final state
				finalStateErrorTerm = true;

				// If the character is 0, it is accepted and we stay in same state.
				if (currentCharErrorTerm == '0') {
					break;
				}

				// If the current character is a decimal point, it transitions to state 2
				else if (currentCharErrorTerm == '.') {
					nextStateErrorTerm = 2;
					break;
				}
				// If the current character is an E or an e, it transitions to state 5
				else if (currentCharErrorTerm == 'E' || currentCharErrorTerm == 'e') {
					nextStateErrorTerm = 5;
					break;
				}
				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;

			case 2:
				// State 2 has one valid transition.

				// This is a final state
				finalStateErrorTerm = true;

				// If the current character is in the range from 0 to 9, it transitions to state 5
				if (currentCharErrorTerm == 'E' || currentCharErrorTerm == 'e') {
					nextStateErrorTerm = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;

			case 3:
				// State 3 has two valid transitions.  Each of them is addressed by an if statement.

				// This is not a final state
				finalStateErrorTerm = false;

				// If the current character is in the range from 1 to 9, it transitions to state 4
				if (currentCharErrorTerm >= '1' && currentCharErrorTerm <= '9') {
					nextStateErrorTerm = 4;
					break;
				}
				// If the character is 0, it is accepted and we stay in same state.
				else if (currentCharErrorTerm == '0') {
					break;
				}
				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;

			case 4:
				// State 4 has one valid transition.

				// This is a final state
				finalStateErrorTerm = true;

				// If the current character is an 'E' or 'e", it transitions to state 5
				if (currentCharErrorTerm == 'E' || currentCharErrorTerm == 'e') {
					nextStateErrorTerm = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;

			case 5:
				// State 5 has two valid transitions.  Each is addressed by an if statement.

				// This is a final state
				finalStateErrorTerm = false;

				// If the current character is in the range from 0 to 9, it transitions to state 7
				if (currentCharErrorTerm >= '0' && currentCharErrorTerm <= '9') {
					nextStateErrorTerm = 7;
					break;
				}
				// If the current character is an '+' or '-", it transitions to state 6
				else if (currentCharErrorTerm == '+' || currentCharErrorTerm == '-') {
					nextStateErrorTerm = 6;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;

			case 6:
				// State 6 has one valid transition.

				// This is a final state
				finalStateErrorTerm = false;

				// If the current character is in the range from 0 to 9, it transitions to state 7
				if (currentCharErrorTerm >= '0' && currentCharErrorTerm <= '9') {
					nextStateErrorTerm = 7;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;

			case 7:
				// State 7 has one valid transition.

				// This is a final state
				finalStateErrorTerm = true;

				// If the current character is in the range from 0 to 9, it remains in the same state.
				if (currentCharErrorTerm >= '0' && currentCharErrorTerm <= '9') {
					break;
				}
				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;
			case 8:
				// State 8 has one valid transition.

				// This is not a final state
				finalStateErrorTerm = false;

				// If the current character is a decimal point, it transitions to state 9
				if (currentCharErrorTerm == '.') {
					nextStateErrorTerm = 9;
					break;
				}
				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;
			case 9:
				// State 9 has two valid transitions.  Each of them is addressed by an if statement.

				// This is not a final state
				finalStateErrorTerm = false;

				// If the current character is in the range from 1 to 9, it transitions to state 4
				if (currentCharErrorTerm >= '1' && currentCharErrorTerm <= '9') {
					nextStateErrorTerm = 4;
					break;
				}
				// If the character is 0, it is accepted and we stay in same state.
				else if (currentCharErrorTerm == '0') {
					break;
				}
				// If it is none of those characters, the FSM halts
				else
					runningErrorTerm = false;

				// The execution of this state is finished
				break;
			}

			if (runningErrorTerm) {
			//	displayDebuggingInfo();
				// When the processing of a state has finished, the FSM proceeds to the next character
				// in the input and if there is one, it fetches that character and updates the
				// currentChar.  If there is no next character the currentChar is set to a blank.
				moveToNextCharacterErrorTerm();

				// Move to the next state
				stateErrorTerm = nextStateErrorTerm;

			}
			// Should the FSM get here, the loop starts again

		}
		errorTermIndexofError = currentCharNdxErrorTerm;		// Copy the index of the current character;

		// When the FSM halts, we must determine if the situation is an error or not.  That depends
		// of the current state of the FSM and whether or not the whole string has been consumed.
		// This switch directs the execution to separate code for each of the FSM states.
		switch (stateErrorTerm) {
		case 0:
			// State 0 is not a final state, so we can return a very specific error message
			errorTermIndexofError = currentCharNdxErrorTerm;		// Copy the index of the current character;
			errorTermErrorMessage = "The first character must be a digit or a decimal point.";
			return "The first character must be a digit or a decimal point.";

		case 1:
			// State 1 is a final state, so we must see if the whole string has been consumed.
			if (currentCharNdxErrorTerm<input.length()) {
				// If not all of the string has been consumed, we point to the current character
				// in the input line and specify what that character must be in order to move
				// forward.
				errorTermErrorMessage = "This character may only be an \"E\", an \"e\", 0, "
						+ "a decimal, or it must be the end of the input.\n";
				return errorTermErrorMessage + displayInput(input, currentCharNdxErrorTerm);
			}
			else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}

		case 2:
			// States 2 and 4 are the same.  They are both final states with only one possible
			// transition forward, if the next character is an E or an e.
			if (currentCharNdxErrorTerm<input.length()) {
				errorTermErrorMessage = "This character may only be an \"E\", an \"e\", or it must"
						+ " be the end of the input.\n";
				return errorTermErrorMessage + displayInput(input, currentCharNdxErrorTerm);
			}
			// If there is no more input, the input was recognized.
			else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}
		case 4:
			// States 2 and 4 are the same.  They are both final states with only one possible
			// transition forward, if the next character is an E or an e.
			if (currentCharNdxErrorTerm<input.length()) {
				errorTermErrorMessage = "This character may only be an \"E\", an \"e\", or it must"
						+ " be the end of the input.\n";
				return errorTermErrorMessage + displayInput(input, currentCharNdxErrorTerm);
			}
			// If there is no more input, the input was recognized.
			else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}
		case 3:
			// States 3, and 6 are the same. None of them are final states and in order to
			// move forward, the next character must be a digit.
			errorTermErrorMessage = "This character may only be a digit.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdxErrorTerm);
		case 6:
			// States 3, and 6 are the same. None of them are final states and in order to
			// move forward, the next character must be a digit.
			errorTermErrorMessage = "This character may only be a digit.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdxErrorTerm);

		case 7:
			// States 7 is similar to states 3 and 6, but it is a final state, so it must be
			// processed differently. If the next character is not a digit, the FSM stops with an
			// error.  We must see here if there are no more characters. If there are no more
			// characters, we accept the input, otherwise we return an error
			if (currentCharNdxErrorTerm<input.length()) {
				errorTermErrorMessage = "This character may only be a digit.\n";
				return errorTermErrorMessage + displayInput(input, currentCharNdxErrorTerm);
			}
			else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}

		case 8:
			errorTermErrorMessage = "This character may only be a decimal.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdxErrorTerm);

		case 9:
			errorTermErrorMessage = "This character may only be a digit.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdxErrorTerm);

		case 5:
			// State 5 is not a final state.  In order to move forward, the next character must be
			// a digit or a plus or a minus character.
			errorTermErrorMessage = "This character may only be a digit, a plus, or minus "
					+ "character.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdxErrorTerm);
		default:
			return "";
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/****
	 * This method is called when the user wants to change the resultat unit according to the his/her wish.
	 * This will change the resultant value based on the selction of UNits
	 * 
	 * @param previouslyUsedUnit - units which are used before coversion 
	 * @param newUnit - units which are used after conversion
	 */
	
	public void convertResultantUnit(String previouslyUsedUnit, String newUnit) {
		
		uNumberLibrary.UNumber newConversionValue = new uNumberLibrary.UNumber(); // using library for the new conversion 
		
		// index of both the units
		int index_last = -2; int index_new = -2;
		
		for (int i = 0; i<UnitsWithValue.length;i++) {  	// Check if the previous unit belongs in lookup table or not
			if (UnitsWithValue[i][0].equals(previouslyUsedUnit)) index_last = i; // if belong, get its index
			}
		
		for (int j =0; j<UnitsWithValue.length;j++) { // Check if the new seleced unit belongs to lookup table or not
			if (UnitsWithValue[0][j].equals(newUnit)) index_new = j; // if belongs, get the index
			}
		
		// If both units are present there and if there conversion is possible,
			if (index_last>=0&&index_new>=0&&ConversionChecker[index_last][index_new]) {
					String resultantValue = null;  // this string will store the resultant value
					boolean negativeDetected = false;  // chek whether the value is negative or not
					
				if (UserInterface.text_Result.getText().charAt(0)=='-') { // If there is negative sign in the result
					negativeDetected = true; // return true
					resultantValue=UserInterface.text_Result.getText().substring(1); // fetch the result value after the - sign
				}
				else resultantValue=UserInterface.text_Result.getText(); // If it is positive, fetch the value from staring point

			// Calculating the characteristics by knowing the index of decimal point
		int characteristic = resultantValue.indexOf('.');  // fetch the index of decimal point
		int characteristicET = UserInterface.TextResultErrorTerm.getText().indexOf('.'); // fetch the index of decimal point in result error term 

		// Fetching the factor from the lookup table
		newConversionValue = new uNumberLibrary.UNumber(Double.parseDouble(UnitsWithValue[index_last][index_new]));

		// Defining UNumber values for both the result and resultant error term
		uNumberLibrary.UNumber  theResult = new uNumberLibrary.UNumber (resultantValue,characteristic,true);
		uNumberLibrary.UNumber  theResultET = new uNumberLibrary.UNumber (UserInterface.TextResultErrorTerm.getText(),characteristicET,true);

		// Multiplying both with the conversion factor to get the new resultant values
		theResult.mpy(newConversionValue); theResultET.mpy(newConversionValue); 
	
			if (negativeDetected) // if the value is staring from the - sign,
				UserInterface.text_Result.setText('-'+theResult.toDecimalString());  // put negative sign before showing the actual result
			else UserInterface.text_Result.setText(theResult.toDecimalString()); // if not
					UserInterface.TextResultErrorTerm.setText(theResultET.toDecimalString()); // display the actual result directly
				}
	}
	
	
	/************************
	 * This method is used to create a sepreate list for the different dimesion and add them to
	 * thericorresponding lists
	 *
	 * @param c String type Combobox
	 */		
	
	public void unitsList(ComboBox<String> c) {
	
	// create an array to strore the units of lenght
		String[] lengths = {"km","miles","m"};
		
		// create an array to strore the units of time
		String[] time = {"days","seconds"};
		
		// create an array to strore the units of velocity
		String[] velocity = {"km/seconds","m/seconds"};
		
		String gravityConstant = "km3/seconds2";  // importatn unit for critical space mission use
		
		String noUnit = "NO_UNIT";
		
		// Initializing the while loop counter
		
		int ndx=0; 
			while (ndx<lengths.length) {
				c.getItems().add(lengths[ndx++]);
				}
			
			// Reset the counter again
			ndx=0;
				while (ndx<time.length) {
						c.getItems().add(time[ndx++]);
					}
				
				// Reset the counter value again
				ndx=0;
					while (ndx<velocity.length) {
							c.getItems().add(velocity[ndx++]);
						}

					c.getItems().add(gravityConstant);
					c.getItems().add(noUnit);
					
						if (CalculationIsPossible) { //chech to see whther the calculation is possible or not
							
							ndx=0; // when the program launches
								while (ndx<lengths.length) { //Add units to their specified lists
									UserInterface.LengthUnits.add(lengths[ndx++]);
									}
								
								ndx=0; // when program launches
									while (ndx<time.length) { //Add all the units to specific lists
										UserInterface.TimeUnits.add(time[ndx++]);
									}
									
									ndx=0; // when the program launches
										while (ndx<velocity.length) {
												UserInterface.VelocityUnits.add(velocity[ndx++]);
										}
							}
					
						CalculationIsPossible=false; // CalculationIsPossible is set to zero so that the list does not gets updated again
					}

	
	
	
		/****** 
		 * This method will check whethe the normalization of Units is
		 * required during the computation of addition or subtraction, if true then normalization is required
		 * else false
		 * 
		 * @param unit1 first OPerands Units
		 * @param unit2 Second Operands Unit
		 * @return true if there is requirement of the normalization
		 * @return false if there is no need of normalization
		 */
		public boolean checkForNormalise(String unit1, String unit2) 
		{
			if (!unit1.equals(unit2)) 
				return true;
			
			return false;
		}
		
		/******
		 * This method clears the reslt field and its error terms whenver the user change the operands unit for
		 *  further calucluations 
		 *  
		 ***/
		public void clearResultField() throws NullPointerException{
				UserInterface.text_Result.clear(); 
				UserInterface.TextResultErrorTerm.clear(); 
				UserInterface.ComboboxOperand3.getSelectionModel().clearSelection();
		}
		
		
		
		/***
		 * This method is used to display the warning popup for the addidtion or subtracted operands
		 * when the units are of different dimesntions
		 * 
		 * @param i developer defined integer
		 */
	  public void warningMessage(int i) {
		
		Alert alert = new Alert(AlertType.WARNING); // Initialize a warning-type alert box
		
		// integer is equals to 1
		if (i==1)	
			// set the message label on the popup for addition
			alert.setContentText("Addition with "+UserInterface.ComboboxOperand1.getSelectionModel().getSelectedItem()+" and "
				+UserInterface.ComboboxOperand2.getSelectionModel().getSelectedItem()+" is not possible...!");
		
		else {
			// set the message label on the popup for the subtraction
			alert.setContentText("Subtraction with "+UserInterface.ComboboxOperand1.getSelectionModel().getSelectedItem()+" and "
					+UserInterface.ComboboxOperand2.getSelectionModel().getSelectedItem()+" is not possible...!");	
			}
		
		alert.setWidth(1000);  // setting the width of the box
		alert.show(); // show the message box
		
		}
			// These integer values will be used for conversion of units.
				int indexOfOldValue = -2; int indexOfNewValue = -2;
	
	
	/*******
	 * This following methods checks whther the units are convertible or not
	 * 
	 * @param unit1 first operand's unit
	 * @param unit2 second operand's unit
	 * @return true it means converion is possible
	 * 
	 */
	
	public boolean conversionIsPossible(String unit1, String unit2) {
		
		// Checking for the first unit in the lookup table
			for (int i = 0; i<UnitsWithValue.length;i++) {
				if (UnitsWithValue[i][0]==unit1) indexOfOldValue=i;	}
			
		// Checking for the second unit in the lookup table
			for (int i = 0; i<UnitsWithValue.length;i++) {
				if (UnitsWithValue[0][i]==unit2) indexOfNewValue=i;	}
			
		// If unit are not present, the conversion is not possible
			if (indexOfOldValue<0 || indexOfNewValue<0) 
				return false;
			
		// if the conversion is possible
			if (indexOfOldValue>=0&&indexOfNewValue>=0) {
				
				boolean isConversionDimensionallyAccurate = ConversionChecker[indexOfOldValue][indexOfNewValue]; // mapping the conversion factor for the conversion 
				return isConversionDimensionallyAccurate;}
			
			// If it gives false, then units are not convertible
				return false;
	}
	
	
	/*****
	 * This mehtod check that whether the selected unit is of same dimension or not.
	 * 
	 * @param unit1 First Operand selected Unit
	 * @param unit2 Second Operand selected Unit
	 * @return true if unit has same dimension
	 * @return false if unit has different dimensions
	 */
	public boolean MatchDimensions(String unit1,String unit2) {
		
	if (unit1.equals(unit2)) return true; // return true, if both belongs to same dimensions
		int ndx_row = -2; int ndx_col = -2;
			for (int i =0; i<UnitsWithValue.length;i++) {  // using loop to check the value of row from the array
				if (UnitsWithValue[i][0].equals(unit1)) ndx_row = i;
			}
			for (int i =0; i<UnitsWithValue.length;i++) {  // using loop to check the value of columns from the array
				if (UnitsWithValue[0][i].equals(unit2)) ndx_col = i;
				}
			if (ndx_row>=0&&ndx_col>=0&& ConversionChecker[ndx_row][ndx_col]) {
				return true;
				}
	return false;
	}
	
	/****
	 * This method is called when the user wants to change the resultat unit according to the his/her wish.
	 * This will change the resultant value based on the selction of UNits
	 */
	
	 public void changeResultantUnit() {
		
		String newUnit = UserInterface.ComboboxOperand3.getSelectionModel().getSelectedItem(); // fetch the index of the Unit selected by the User
			
		// make the resultant texfield empty for further conversion's result
			if (!(UserInterface.text_Result.getText().isEmpty())&&!(UserInterface.TextResultErrorTerm.getText().isEmpty()))
				convertResultantUnit(UserInterface.ResultantUnit,newUnit);
				UserInterface.ResultantUnit = newUnit; // save the result for further conversions.
		}
	 
	 /******
		 * This method will use the both operands unit and check for their convertability. If they are able to convertible, then
		 * the first unit will converted into second by multiplying the operand and its error term by the conversion value of the selected unit
		 * This method returns a two-elements containing 1-D array which stores the pair of the converted operand's
		 * measured value and error term. 
		 * 
		 * @param Operand1Unit unit which is going to be converted
		 * @param Operand2Unit unit in term of we are going to convert the first unit
		 * @return Array give the updated array wiht the new values
		 */
	 
	 public double[] performNormalization(String Operand1Unit, String Operand2Unit) throws NullPointerException{
			
			UserInterface.setDefaultValues(); // if the text field of both the operands are empty, fill it with default values.
			double newConversionValue = 0; // The conversion factor
			double firstOperand = Double.parseDouble(UserInterface.text_Operand1.getText()); // fetching the first mearsured value
			double firstET = Double.parseDouble(UserInterface.TextErrorTerm1.getText()); // fectching its error term value
			
			if (conversionIsPossible(Operand1Unit, Operand2Unit))
			{
				 // If conversion is possible, do the conversion according to the unit
				newConversionValue = Double.parseDouble(UnitsWithValue[indexOfOldValue][indexOfNewValue]); //see the conversion from thte lookup table
			}
			else newConversionValue=1; //else put the ner conversion fact as 1
			
			// multiplying the first operand value by the conversion factor of the Units
			double newOperand = firstOperand * newConversionValue;
			
			// multiply the error term value by the conversion factor of the unit
			double newErrorTerm = firstET * newConversionValue;
			
			// place both the nw values into the array as updated value
				double[] thePair = {newOperand,newErrorTerm};
				
				return thePair;
			}
	 }