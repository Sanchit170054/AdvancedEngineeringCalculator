package uNumberLibrary;

import java.util.Scanner;

/**
* <p> Title: UNumberWithSqrt. </p>
* 
* <p> Description: Mainline to support the functionality or the logic of coding to compute the square root using the Newton-Raphson square root
*                  method extends with UNumber</p>
* 
* <p> Copyright: Lynn Robert Carter © 2018-08-11 </p>
* 
* @author Lyn Robert Carter
* Updated author: Sanchit
* 
* @version 2.00	2018-08-11 Formula used for the computation and other attributes which provides the accurate output
* @version 2.10	2018-09-08 Enhance version of the previous one which shows the convergence speed of roughly doubling
*                          the number of significant digits with each iteration and different estimates for computations
* @version 2.20 2018-10-04 Modifed class whihc work according to the calling method present in the calculator value
*/
public class UNumberWithSqrt extends UNumber {

	private static int howManyDigitsMatch2(UNumberWithGetters newGuess, UNumberWithGetters oldGuess, int maxMatchingDigits) {
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
	
/** This methods is used to compute the sqaure root of the given input and also display the number
 * of iteration wiht the speed of convergence using the Newton Raphson Method 	
 * 
 * @param input  take the inout from the user
 * @param keyboard  set the keyboard as the scanner object
 */
public static void sqrt(String input, Scanner keyboard){
	while (input.length() > 0) {
		
		// Produce a scanner from the input so we can fetch a double value from it
		Scanner value = new Scanner(input);
		
		// As long as there is another double value, compute the square root of it
		double inputValue = value.nextDouble();
	
		// check if the input value consist of value greater than 0
		if (inputValue >0) {
			
	   		System.out.print("The value to be used: ");
			System.out.println(inputValue); // display the input value
			System.out.println("\n     *****************************************************");
			int numSigDigits = 25;									// This is for number of significant digits for the conputation			
			System.out.println("\n     Compute the Square Root using UNumber values to " + numSigDigits + " significant digits\n");

			// Set up the values for the loop to compute the answer
			UNumberWithGetters theValue2 = new UNumberWithGetters(inputValue);	// We will compute the square root of this value
			theValue2 = new UNumberWithGetters(theValue2, numSigDigits+1);
			
			UNumberWithGetters two2 = new UNumberWithGetters(2.0);				// create the varible 'two2' and store the value 2.0 in it
			two2 = new UNumberWithGetters(two2, numSigDigits);
			
			UNumberWithGetters newGuess2 = new UNumberWithGetters(theValue2);	//wiht the help of the newGuess2, calculate the first esstimate
			newGuess2 = new UNumberWithGetters(newGuess2, numSigDigits+1);
			newGuess2.div(two2);							                    // divide the newGuess2 wiht 2.0
			
			System.out.print("\n     The first estimate to be used: ");			// Display the first estimate
			System.out.println(newGuess2.toDecimalString() + "\n");				
			
			UNumberWithGetters oldGuess2;										// Temporary value for determining when to terminate the loop
		
			int iteration = 0;												     // This is used to count the number of iterations
			int digitsMatch = 0;												// This used to hold the number of matching significant digits
			
			System.out.println("     0 estimate " + newGuess2.toDecimalString());	// Display the first estimate
			
			//initate the loop that provides the iteration of the input
			
			do {
				long start = System.nanoTime();									// Capture the start time for this iteration
				iteration++;													// Next iteration, so count it
		     	oldGuess2 = newGuess2;                                           //save the First estimate into oldGuess 2
				newGuess2 = new UNumberWithGetters(theValue2);                  //store the value in the new object which is newGu ess2
				newGuess2.div(oldGuess2);newGuess2.add(oldGuess2);newGuess2.div(two2); //compute the other estimates using the using the newton Raphson formula
				long stop = System.nanoTime();									// Capture the stop time for this iteration
				
				digitsMatch = howManyDigitsMatch2(newGuess2, oldGuess2, numSigDigits);	// Determine how many significatn digits match wiht the iterations
				
				// Display the result of the number of iterations wiht its convergence speed 
				System.out.println("     " + iteration + " estimate " + newGuess2.toDecimalString() + " with " + digitsMatch + " digits matching taking " + (stop-start)/1000000000.0 + " seconds" );		
									
			} while (digitsMatch < numSigDigits);				// check whether the old guess and the new guess are nearly close
			System.out.println("\n     The square root");
			System.out.println("     " + newGuess2.toDecimalString());			// Display the final result to the console
			UNumberWithGetters resultSquared2 = new UNumberWithGetters(newGuess2);
			resultSquared2.mpy(newGuess2);                                      // Verify the computed output to check whether the computation is right or not
			System.out.println("*****************************************************");
			System.out.println("\n     The square of the computed square root: "); // display the verified result of the output
			System.out.println("     " + resultSquared2.toDecimalString());		// Display the result squared
		}
		System.out.println("*****************************************************");
		// Ask for more input
		System.out.print("\nEnter a double value or just press return (enter) to stop the loop: ");
		input = keyboard.nextLine().trim(); // Fetch the input from the user, removing leading and trailing white space characters
		value.close(); // close the incoming input
	}
	// An empty input line has been entered, so the tell the user we are stopping
	System.out.print("Empty line detected... the program stops"); // display the message
	keyboard.close(); // stops the program
}
}