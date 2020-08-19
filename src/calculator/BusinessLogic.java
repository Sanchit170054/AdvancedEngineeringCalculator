
package calculator;

/**
 * <p> Title: BusinessLogic Class. </p>
 *
 * <p> Description: The code responsible for performing the calculator business logic functions.
 * This method deals with CalculatorValues and performs actions on them. The class expects data
 * from the User Interface to arrive as Strings and returns Strings to it. This class calls the
 * CalculatorValue class to do computations and this class knows nothing about the actual
 * representation of CalculatorValues, that is the responsibility of the CalculatorValue class and
 * the classes it calls.</p>
 *
 * <p> Copyright: Lynn Robert Carter © 2017 </p>
 *
 * @author Lynn Robert Carer
 * Updated Author Sanchit
 *
 * @version 4.5	2018-02-27 The JavaFX-based GUI implementation of a Double calculator
 * @version 4.6 2018-10-06 The JavaFX which perform the business logic function by unsing the UNumber Library
 * @version 4.7 2018-11-24 This class has been enhanced by the some of the logics that has been used for the computation 
 * 							of all the operators
 */

public class BusinessLogic {

	/**********************************************************************************************

	Attributes

	**********************************************************************************************/

	// These are the major calculator values
	private CalculatorValue operand1 = new CalculatorValue(0);
	private CalculatorValue operand2 = new CalculatorValue(0);
	private CalculatorValue operand3 = new CalculatorValue(0);
	private CalculatorValue operand4 = new CalculatorValue(0);
	private CalculatorValue result = new CalculatorValue(0);
	private CalculatorValue resulterr = new CalculatorValue(0);
	private String operand1ErrorMessage = "";
	private boolean operand1Defined = false;
	private String operand2ErrorMessage = "";
	private boolean operand2Defined = false;
	private String ErrorTerm1ErrorMessage = "";
	private boolean ErrorTerm1Defined = false;
	private String ErrorTerm2ErrorMessage = "";
	private boolean ErrorTerm2Defined = false;
	private String resultErrorMessage = "";
	private String resulterrErrorMessage = "";


	/**********************************************************************************************

	Constructors

	**********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the business logic aspect of the calculator.
	 * There is no special computational initialization required, so the initialization of the
	 * attributes above are all that are needed.
	 */
	public BusinessLogic() {
	}

	/**********************************************************************************************

	Getters and Setters

	**********************************************************************************************/

	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand1, any associated error message
	 * into operand1ErrorMessage accordingly.
	 *
	 * @param value input which is eter by the user
	 * @return	True if the set did not generate an error; False if there was invalid input
	 * @param value it is a measured value
	 * @param errorTerm it is a error term for the measured value
	 */
	public boolean setOperand1(String value, String errorTerm) {
		operand1Defined = false;							// Assume the operand will not be defined
		ErrorTerm1Defined = false;
		if (value.length() <= 0 && errorTerm.length()<=0) {						// See if the input is empty. If so no error
			operand1ErrorMessage = "";					// message, but the operand is not defined.
			ErrorTerm1ErrorMessage = "";
			return true;									// Return saying there was no error.
		}
		operand1 = new CalculatorValue(value);			// If there was input text, try to convert it
		operand3 = new CalculatorValue(errorTerm);			// If there was input text, try to convert it
		operand1ErrorMessage = operand1.getErrorMessage();	// into a CalculatorValue and see if it
		ErrorTerm1ErrorMessage = operand3.getErrorMessage();	// into a CalculatorValue and see if it
		if (operand1ErrorMessage.length() > 0 && ErrorTerm1ErrorMessage.length() > 0) 			// worked. If there is a non-empty error
			return false;								// message, signal there was a problem.
		operand1Defined = true;							// Otherwise, set the defined flag and
		ErrorTerm1Defined = true;
		return true;										// signal that the set worked
	}


	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand2, any associated error message
	 * into operand1ErrorMessage accordingly.
	 *
	 * The logic of this method is the same as that for operand1 above.
	 * @param value it is a measured value
	 * @param errorTerm it is a error term for the measured value
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setOperand2(String value, String errorTerm) {
		operand2Defined = false;							// Assume the operand will not be defined
		ErrorTerm2Defined = false;
		if (value.length() <= 0 && errorTerm.length()<=0) {						// See if the input is empty. If so no error
			operand2ErrorMessage = "";					// message, but the operand is not defined.
			ErrorTerm2ErrorMessage = "";
			return true;									// Return saying there was no error.
		}
		operand2 = new CalculatorValue(value);			// If there was input text, try to convert it
		operand4 = new CalculatorValue(errorTerm);			// If there was input text, try to convert it
		operand2ErrorMessage = operand2.getErrorMessage();	// into a CalculatorValue and see if it
		ErrorTerm2ErrorMessage = operand4.getErrorMessage();	// into a CalculatorValue and see if it
		if (operand2ErrorMessage.length() > 0 && ErrorTerm2ErrorMessage.length() > 0) 			// worked. If there is a non-empty error
			return false;								// message, signal there was a problem.
		operand2Defined = true;							// Otherwise, set the defined flag and
		ErrorTerm2Defined = true;
		return true;										// signal that the set worked
	}


	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand2, any associated error message
	 * into operand1ErrorMessage accordingly.
	 *
	 * The logic of this method is the same as that for operand1 above.
	 *
	 * @param value input for the error term
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setOperand3(String value) {			// The logic of this method is exactly the
		ErrorTerm1Defined = false;							// same as that for operand1, above.
		if (value.length() <= 0) {
			ErrorTerm1ErrorMessage = "";
			return true;
		}
		operand3 = new CalculatorValue(value);
		ErrorTerm1ErrorMessage = operand3.getErrorMessage();
		if (ErrorTerm1ErrorMessage.length() > 0)
			return false;
		ErrorTerm1Defined = true;
		return true;
	}

	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand4, any associated error message
	 * into operand1ErrorMessage accordingly.
	 *
	 * The logic of this method is the same as that for operand1 above.
	 *
	 * @param value input for the error term 2
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setOperand4(String value) {			// The logic of this method is exactly the
		ErrorTerm2Defined = false;							// same as that for operand1, above.
		if (value.length() <= 0) {
			ErrorTerm2ErrorMessage = "";
			return true;
		}
		operand4 = new CalculatorValue(value);
		ErrorTerm2ErrorMessage = operand4.getErrorMessage();
		if (ErrorTerm2ErrorMessage.length() > 0)
			return false;
		ErrorTerm2Defined = true;
		return true;
	}

	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into result, any associated error message
	 * into resuyltErrorMessage, and sets flags accordingly.
	 *
	 * The logic of this method is similar to that for operand1 above. (There is no defined flag.)
	 * 
	 * @param value the value that comes after computation
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */

	public boolean setResult(String value) {				// The logic of this method is similar to
		if (value.length() <= 0) {						// that for operand1, above.
			operand2ErrorMessage = "";
			return true;
		}
		result = new CalculatorValue(value);
		resultErrorMessage = operand2.getErrorMessage();
		if (operand2ErrorMessage.length() > 0)
			return false;
		return true;
	}


	public boolean setResulterr(String value) {				// The logic of this method is similar to
		if (value.length() <= 0) {						// that for operand1, above.
			ErrorTerm2ErrorMessage = "";
			return true;
		}
		resulterr = new CalculatorValue(value);
		resulterrErrorMessage = operand4.getErrorMessage();
		if (ErrorTerm2ErrorMessage.length() > 0)
			return false;
		return true;
	}


	/**********
	 * This public setter sets the String explaining the current error in operand1.
	 * @param m sets the string explaining the current error
	 */
	public void setOperand1ErrorMessage(String m) {
		operand1ErrorMessage = m;
		return;
	}

	/**********
	 * This public getter fetches the String explaining the current error in operand1, it there is one,
	 * otherwise, the method returns an empty String.
	 *
	 * @return and error message or an empty String
	 */
	public String getOperand1ErrorMessage() {
		return operand1ErrorMessage;
	}

	/**********
	 * This public setter sets the String explaining the current error into operand2.
	 *
	 *@param m set the string for error in operand 2
	 */
	public void setOperand2ErrorMessage(String m) {
		operand2ErrorMessage = m;
		return;
	}

	/**********
	 * This public getter fetches the String explaining the current error in operand2, it there is one,
	 * otherwise, the method returns an empty String.
	 *
	 * @return and error message or an empty String
	 */
	public String getOperand2ErrorMessage() {
		return operand2ErrorMessage;
	}

	/**********
	 * This public setter sets the String explaining the current error in operand3.
	 * 
	 * @param m set the string for the error in error term 1
	 */
	public void setErrorTerm1ErrorMessage(String m) {
		ErrorTerm1ErrorMessage = m;
		return;
	}

	/**********
	 * This public getter fetches the String explaining the current error in operand3, it there is one,
	 * otherwise, the method returns an empty String.
	 *
	 * @return and error message or an empty String
	 */
	public String getErrorTerm1ErrorMessage() {
		return ErrorTerm1ErrorMessage;
	}


	/**********
	 * This public setter sets the String explaining the current error in operand4.
	 *
	 * @param m sets the string explaingin error in error term 2
	 */
	public void setErrorTerm2ErrorMessage(String m) {
		ErrorTerm2ErrorMessage = m;
		return;
	}

	/**********
	 * This public getter fetches the String explaining the current error in operand4, it there is one,
	 * otherwise, the method returns an empty String.
	 *
	 * @return and error message or an empty String
	 */
	public String getErrorTerm2ErrorMessage() {
		return ErrorTerm2ErrorMessage;
	}


	/**********
	 * This public setter sets the String explaining the current error in the result.
	 *
	 * @param m set the string for the result
	 */


	public void setResultErrorMessage(String m) {
		resultErrorMessage = m;
		return;
	}

	/**********
	 * This public getter fetches the String explaining the current error in the result, it there is one,
	 * otherwise, the method returns an empty String.
	 *
	 * @return and error message or an empty String
	 */
	public String getResultErrorMessage() {
		return resultErrorMessage;
	}

	public void setErrorResultErrorMessage(String m) {
		resulterrErrorMessage = m;
		return;
	}

	/**********
	 * This public getter fetches the String explaining the current error in the result, it there is one,
	 * otherwise, the method returns an empty String.
	 *
	 * @return and error message or an empty String
	 */
	public String getErrorResultErrorMessage() {
		return resulterrErrorMessage;
	}

	/**********
	 * This public getter fetches the defined attribute for operand1. You can't use the lack of an error
	 * message to know that the operand is ready to be used. An empty operand has no error associated with
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 *
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand1Defined() {
		return operand1Defined;
	}

	/**********
	 * This public getter fetches the defined attribute for operand2. You can't use the lack of an error
	 * message to know that the operand is ready to be used. An empty operand has no error associated with
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 *
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand2Defined() {
		return operand2Defined;
	}

	/**********
	 * This public getter fetches the defined attribute for operand3. You can't use the lack of an error
	 * message to know that the operand is ready to be used. An empty operand has no error associated with
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 *
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getErrorTerm1Defined() {
		return ErrorTerm1Defined;
	}

	/**********
	 * This public getter fetches the defined attribute for operand4. You can't use the lack of an error
	 * message to know that the operand is ready to be used. An empty operand has no error associated with
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 *
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getErrorTerm2Defined() {

		return ErrorTerm2Defined;
	}

	/**********************************************************************************************

	The toString() Method

	**********************************************************************************************/

	/**********
	 * This toString method invokes the toString method of the result type (CalculatorValue is this
	 * case) to convert the value from its hidden internal representation into a String, which can be
	 * manipulated directly by the BusinessLogic and the UserInterface classes.
	 */



	public String toString() {
		return result.toString();
	}

	public String toString1() {
		return resulterr.toString();
	}
	/**********
	 * This public toString method is used to display all the values of the BusinessLogic class in a
	 * textual representation for debugging purposes.
	 *
	 * @return a String representation of the class
	 */
	public String debugToString() {
		String r = "\n******************\n*\n* Business Logic\n*\n******************\n";
		r += "operand1 = " + operand1.toString() + "\n";
		r += "     operand1ErrorMessage = " + operand1ErrorMessage+ "\n";
		r += "     operand1Defined = " + operand1Defined+ "\n";
		r += "operand2 = " + operand2.toString() + "\n";
		r += "     operand2ErrorMessage = " + operand2ErrorMessage+ "\n";
		r += "     operand2Defined = " + operand2Defined+ "\n";
		r += "operand3 = " + operand3.toString() + "\n";
		r += "     operand3ErrorMessage = " + ErrorTerm1ErrorMessage+ "\n";
		r += "     operand3Defined = " + ErrorTerm1Defined+ "\n";
		r += "operand4 = " + operand4.toString() + "\n";
		r += "     operand4ErrorMessage = " + ErrorTerm2ErrorMessage+ "\n";
		r += "     operand4Defined = " + ErrorTerm2Defined+ "\n";
		r += "result = " + result.toString() + "\n";
		r += "     resultErrorMessage = " + resultErrorMessage+ "\n";
		r += "resulterr = " + resulterr.toString() + "\n";
		r += "     resulterrErrorMessage = " + resulterrErrorMessage+ "\n";
		r += "*******************\n\n";
		return r;
	}

	/**********************************************************************************************

	Business Logic Operations

	**********************************************************************************************/

	/**********
	 * This public method computes the sum of the two operands using the CalculatorValue class method
	 * for addition. The goal of this class is to support a wide array of different data representations
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 *
	 * This method assumes the operands are defined and valid. It replaces the left operand with the
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 *
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 *
	 * @return a String representation of the result
	 */
	public String addition() {
		result = new CalculatorValue(operand1);
		result.add(operand2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}

	public String addition1() {
		resulterr = new CalculatorValue(operand3);
		resulterr.add(operand4);
		resulterrErrorMessage = resulterr.getErrorMessage();
		return resulterr.toString();
	}

	/**********
	 * This public method computes the subtraction of the two operands using the CalculatorValue class method
	 * for addition. The goal of this class is to support a wide array of different data representations
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 *
	 * This method assumes the operands are defined and valid. It replaces the left operand with the
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 *
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 *
	 * @return a String representation of the result
	 */
	
	public String subtraction() {
		result = new CalculatorValue(operand1);
		result.sub(operand2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();

	}

	public String subtractionE() {
		resulterr = new CalculatorValue(operand3);
		resulterr.add(operand4);
		resulterrErrorMessage = resulterr.getErrorMessage();
		return resulterr.toString();

	}
	
	/**********
	 * This public method computes the multiplication of the two operands using the CalculatorValue class method
	 * for addition. The goal of this class is to support a wide array of different data representations
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 *
	 * This method assumes the operands are defined and valid. It replaces the left operand with the
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 *
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 *
	 * @return a String representation of the result/
	 */

	public String multiplication() {
		result = new CalculatorValue(operand1);
		result.mpy(operand2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}

	public String multiplicationE() {
		resulterr = new CalculatorValue(result);
		resulterr.mpyE(operand3,operand1,operand4,operand2);
		resulterrErrorMessage = resulterr.getErrorMessage();
		return resulterr.toStringE();
	}

	/**********
	 * This public method computes the division of the two operands using the CalculatorValue class method
	 * for addition. The goal of this class is to support a wide array of different data representations
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 *
	 * This method assumes the operands are defined and valid. It replaces the left operand with the
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 *
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 *
	 * @return a String representation of the result
	 */


	public String division() {
		result = new CalculatorValue(operand1);
		result.div(operand2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}

	public String divisionE() {
		resulterr = new CalculatorValue(result);
		resulterr.divE(operand3,operand1,operand4,operand2);
		resulterrErrorMessage = resulterr.getErrorMessage();
		return resulterr.toStringE();
	}


	/**********
	 * This public method computes the square root of first operadn using the CalculatorValue class method
	 * for addition. The goal of this class is to support a wide array of different data representations
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 *
	 * This method assumes the operands are defined and valid. It replaces the left operand with the
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 *
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 *
	 * @return a String representation of the result
	 */
	public String squareroot() {
		result = new CalculatorValue(operand1);
		result.sqrt(operand1);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}


    public String squarerootE() {
	    resulterr = new CalculatorValue(result);
	    resulterr.sqrtE(operand3,operand1);
	    resulterrErrorMessage = resulterr.getErrorMessage();
	   return resulterr.toStringE();
    }


   }