
package calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*******
 * <p> Title: Calculator Class. </p>
 * 
 * <p> Description: A JavaFX file which launch the application for the interaction</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2016 </p>
 * 
 * @author Lyn Robert Carter
 * Updated Author Sanchit
 *
 * @version 4.4	2018-02-27 The mainline of a JavaFX-based GUI implementation of a Double calculator
 * @version 4.5 2018-03-05 The mainline of a JavaFX-based GUI implementation of a Double calculator along wiht the error terms
 * @version 4.6 2018-10-06 The mainline of a JavaFX-based GUI implementation of a UNumber Library
 * @version 4.7 2018-11-24 The mainline of a JavaFX-based GUI implementation of a UNumber Library extends with the Units for the 
 * 							operators computations
 */

public class Calculator extends Application {
	

	public final static double WINDOW_WIDTH = 1500;
	public final static double WINDOW_HEIGHT = 900;
	
	public UserInterface theGUI;
	
	/**********
	 * This is the start method that is called once the application has been loaded into memory and is 
	 * ready to get to work.
	 * 
	 
	 */
	@Override
	public void start(Stage theStage) throws Exception {
		
		theStage.setTitle("Sanchit's Calculator");				// Label the stage (a window)
		
		Pane theRoot = new Pane();							// Create a pane within the window
		
		theGUI = new UserInterface(theRoot);					// Create the Graphical User Interface
		
		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene
		
		theStage.setScene(theScene);							// Set the scene on the stage
		
		theStage.show();										// Show the stage to the user
		
		// When the stage is shown to the user, the pane within the window is visible.  This means that the
		// labels, fields, and buttons of the Graphical User Interface (GUI) are visible//
	}

	/*******************************************************************************************************
	 * This is the method that launches the JavaFX application
	 * @param args launch the arguments
	 * 
	 */
	public static void main(String[] args) {						// This method may not be required
		launch(args);											// for all JavaFX applications using
	}															// other IDEs.
}
