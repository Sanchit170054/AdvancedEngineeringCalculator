package calculator;

import java.awt.event.HierarchyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.crypto.SealedObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lexer.Lexer;
import lexer.Token;
import lexer.Token.Kind;
/**
 * <p> Title: ProgramUserInterface Class. </p>
 * 
 * <p> Description: The Java/FX-based user interface for the Program window. The class works with String
 * objects and passes work to other classes to deal with all other aspects of the computation.</p>
 * 
 * <p> Copyright: Sanchit</p>
 * 
 * @author Sanchit
 * 
 * @version 2.00 2019-03-31 This is the class which supports the user to interact with UI and this class is enhacned to evaluate the 
 * 							Expression based on user's input
 * @version 2.01 2019-05-05 This class is enhanced version of pervious one along with the logics related to the statement-based
 * 							program
 */


public class programUserInterface {
	
	
	/**********************************************************************************************

	Attributes
s
	 **********************************************************************************************/

	/* Constants used to parameterize the graphical user interface.  We do not use a layout manager for
	   this application. Rather we manually control the location of each graphical element for exact
	   control of the look and feel. */
	
	
	public static TextArea writeProgram  = new TextArea();
	public static TextArea outputProgram = new TextArea();
	public static TextArea solutionProgram = new TextArea();
	
	
	 public static Button runProgram = new Button("Run");
	 public static Button debugProgram = new Button("Debug");
	 public static Button saveProgram = new Button("Save");
	 public static Button delteProgram = new Button("Delete");
	 public static Button availableProgram = new Button("Available Programs");
	 public Button saveFileName = new Button("Save File as");
	 public static TextField saveFileNametxt = new TextField();
	// public static TextField loadFileNametxt = new TextField();
	 public static Button createProgram = new Button("Create new program");
	 
	 public static String theExpression = "";
	 public static String storedValue = "";
	 public static RadioButton rb = new RadioButton("For Program");
	 
	 public static RadioButton RunStatements = new RadioButton("Run Statements");
	 
	 public static Label solution = new Label("Solution Flow: ");
	 public static Label result = new Label("Actual Result: ");
	 
	 static ObservableList<String> list = FXCollections.observableArrayList();
	 public static ComboBox<String> cbox = new ComboBox<>(list);
	 	 
	 static File thedirectory = new File("Programs");
	 static FileChooser fileChooser = new FileChooser();
	 public static ComboBox<String> ComboboxOperand3 = new ComboBox<String>();
	 public static TextField pathField = new TextField();
	
	 // The following are the attributes that support the scanning and lexing of the input
	 public static Scanner theReader;
	 private static Lexer lexer;
	 private static Token current;
	 private static Token next;
	 public static Stack<ExprNode> exprStack = new Stack<>();
	 public static Stack<Token> opStack = new Stack<>();
	 static String data = "";
	 static String[] VName = new String[100];
	 static double[] value = new double[100];
	 static int count=0;
	 static String line = "";
	 
	// static Dictionary dict = new Dictionary();
		public static ProgramInterface<String> dict = new ProgramInterface<>();

		
		/**********
		 * This method initializes all of the elements of the graphical user interface. These assignments
		 * determine the location, size, font, color, and change and event handlers for each GUI object.
		 * 
		 */

	 public static void openPopup()
		{
			Stage stage = new Stage(); // setting up the stage
			 Pane pane = new Pane(); // defining the object for the pane
			 stage.setTitle("Program");
			 
			 //This is the attribute for the text area which is used to 
			 //set the layout of the texarea.
			 writeProgram.setLayoutX(20);
			 writeProgram.setLayoutY(10);
			writeProgram.setMaxSize(500, 450);
			 
			//This is the attribute for the text area which is used to 
			 //set the layout of the texarea.			 
			 outputProgram.setLayoutX(20);
			 outputProgram.setLayoutY(280);
			 outputProgram.setMaxSize(250, 20);
			 		
			 // This is text field used to show the result of the computations 
			 // These are the properties of this text field
			 solutionProgram.setLayoutX(650);
			 solutionProgram.setLayoutY(10);
			 solutionProgram.setMaxSize(500, 450);
			
			 
			 //// ----User guidlines which show at the result field to help the new user------////////////
			 solutionProgram.setPromptText("--> Use small leters word for the computation "+
					 "\n for example, sum, div,sub,mpy" + "\n" +"                                                 "
					 		+ "                                               "
					 		+ "--> Select radio button to write the statements based prorgams." + "\n" +"                                                 "
					 		+ "                                               "
					 		+ "--> Use sign ':' while writing the statement based program." + "\n" +"                                                 "
					 		+ "                                               "
					 		+ "   for instance for print, print: Enter the value && for input, input A" + "\n"  
					 		+ "        --> At the time of computation, use the '='sign" + "\n" +"                                                 "
					 		+ "                                               "
					 		+ "          --> For proper guidence, watch the related screen cast for it.");
						 
			 // These are the attributes of the run button
			 runProgram.setLayoutX(535);
			 runProgram.setLayoutY(25);
			 runProgram.setMinWidth(100);
			 final double MAX_FONT_SIZE2 = 20; 
			 runProgram.setFont(new Font(MAX_FONT_SIZE2));
			 runProgram.setTextFill(Color.web("Black"));
			 
			//Event handling of the run button which when pressed check the condition			 
			 runProgram.setOnAction((event) -> {
				
				 if(!rb.isSelected()) // check if the radio button is clicked or not,
				 {
					 	
					String exp =""; // declaring a string
					solutionProgram.clear();  // clearing the text field
					String s = writeProgram.getText(); // fetch the data from the user
							
					Scanner sc = new Scanner(s); // scanner class used for reading the data
				
				while (sc.hasNext()) { // if there are more lines
					exp=sc.next(); // store it in expression string
					runExpressions(exp); // call the method and pass the expression
			 }
				sc.close();
				 }
				 else { // if yes, then
						String s = writeProgram.getText(); // fetch the data from the user
						Scanner sc = new Scanner(s); // scanner class used for reading the data
							
					runProgram(sc); // call the method and pass the expression
				 }
					});
			 
			 
			 // These are the attributes of the debug button
			 debugProgram.setLayoutX(535);
			 debugProgram.setLayoutY(75);
			 debugProgram.setMinWidth(100);
			 final double MAX_FONT_SIZE3 = 20; 
			 debugProgram.setFont(new Font(MAX_FONT_SIZE3));
			 debugProgram.setTextFill(Color.web("Black"));
			 
			 //event handling for the debug button
			 debugProgram.setOnAction((event) ->
			 {
				 
				 String exp =""; // declaring a string
				solutionProgram.clear();  // clearing the text field
				String s = writeProgram.getText(); // fetch the data from the user
				
				if(!Pattern.matches("[a-zA-Z]+", s)) // check if the string contains any alphabet
				{
					data= s.replaceFirst("[a-zA-Z]+", ""); //if yes, replace the alphabet wiht empty space
				
					Scanner sc = new Scanner(data); // scanner class used for reading the data
				
				while (sc.hasNext()) { // if there are more lines
					exp=sc.next(); // store it in expression string
					debugProgram(exp); // call the method and pass the expression
					 }
				sc.close();
				}});
			 
			 
			 // These are the attributes of the save button
			 saveProgram.setLayoutX(535);
			 saveProgram.setLayoutY(125);
			 saveProgram.setMinWidth(100);
			 final double MAX_FONT_SIZE4 = 20; 
			 saveProgram.setFont(new Font(MAX_FONT_SIZE4));
			 saveProgram.setTextFill(Color.web("Black"));
			 
			 //event handling of the button to perform some function
			 saveProgram.setOnAction((event) -> {
				 solutionProgram.setText(""); // clear the text
				 outputProgram.setText(""); // clear the text
				 dynamicName(); // call the method
			 });
			 
			 // These are the attributes of the run button
			 delteProgram.setLayoutX(535);
			 delteProgram.setLayoutY(175);
			 delteProgram.setMinWidth(100);
			 final double MAX_FONT_SIZE31 = 20; 
			 delteProgram.setFont(new Font(MAX_FONT_SIZE31));
			 delteProgram.setTextFill(Color.web("Black"));
			 
			 //event handling of the button to perform some functions 
			 delteProgram.setOnAction((event) -> 
			 {
				 FileChooser fileChooser = new FileChooser(); // definaing an object for Filechooser
				 fileChooser.setInitialDirectory(thedirectory);  // set the initial directory
				    fileChooser.setTitle("Delete File...!"); // set the title of the window
				    List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);  // create a list

				    if (selectedFiles != null) { // check if tthe file is selected
				        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // if yes, show the message bbox
				        alert.setTitle("Confirmation Dialog"); // set the title
				        alert.setHeaderText("Warning !"); // set the header text
				        alert.setContentText("Are you sure you want to delete this file ?"); // message to display on window

				        Optional<ButtonType> result = alert.showAndWait(); //decalig a result button
				        if (result.get() == ButtonType.OK) { // check if clcik is on OK
				            for (File selectedFile1 : selectedFiles) { // if yes, 
				                selectedFile1.delete(); // delete the file
				            }
				        }
				        
				    } else {
				        solutionProgram.setText("Error Selection");
				    }
			 });
			 
			 // These are the attributes of the combobx whihc displays the list of available program
			 cbox.setLayoutX(950); cbox.setLayoutY(280);
			 cbox.setValue("Available Programs");
			 thedirectory.listFiles(); // show all the files

				// adding files to the list
				File[] files = new File("Programs/").listFiles();
				// If this pathname does not denote a directory, then listFiles() returns null.
				for (File file : files) {
					if (file.isFile()) { // check if the file exist
						cbox.getItems().add(file.getName()); // if exist, then add all files into the combobx
					}
				}

				//event handling of the cbox
				cbox.setOnAction(e->{
					try {
						
						loadFileData(); //calling the mehtod
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});

			// These are the attributes of the run button
			 createProgram.setLayoutX(490);
			 createProgram.setLayoutY(280);
			 createProgram.setMinWidth(100);
			 final double MAX_FONT_SIZE7 = 20; 
			 createProgram.setFont(new Font(MAX_FONT_SIZE7));
			 createProgram.setTextFill(Color.web("Black"));
			 
			 //Event handling of the create program
			 createProgram.setOnAction((event) -> {
				 
				 // setting the fields empty
				 writeProgram.setText("");
				outputProgram.setText("");
				solutionProgram.setText("");
			 });
			 
			 // THis is the attributes for its label
			 pathField.setLayoutX(200);
			 pathField.setLayoutY(400);
			 pathField.setMinWidth(760);
			 pathField.setMinHeight(50);
			 
			 rb.setLayoutX(150);
			 rb.setLayoutY(300);
			 	 
			 //This is the combobox attributes
			 ComboboxOperand3.setLayoutX(1500);
			 ComboboxOperand3.setLayoutY(350);
			 
			 RunStatements.setLayoutX(100);
			 RunStatements.setLayoutY(300);
			
			   // add all the necessary elements in the pane 
			     pane.getChildren().addAll(writeProgram, runProgram, saveProgram, debugProgram,
			    		 				solutionProgram,rb,pathField,ComboboxOperand3,createProgram,cbox, delteProgram);
			 
			     // Setting up the scene and stages
			 		 Scene scene = new Scene(pane, 1200, 500); // defing the height and width of the scene
			         stage.setScene(scene); 
			         stage.show();
		}

	 	/**********
		 * This method is called when we click on the combobx and then select the desired file to
		 * load. This mehtod just fetch the content of the file from the particular loadtion and load the 
		 * data into specifed field
		 *  
		 * @throws IOException -  exception to handle IO errors
		 */
	 
	 public static void loadFileData() throws IOException {
			
		 	writeProgram.clear();  // clear the text area
			String gg = cbox.getSelectionModel().getSelectedItem(); // fetch the selected file.
			File file1 = new File("Programs/"+cbox.getSelectionModel().getSelectedItem()); // declaring an object for file and provides its path
			String dd = file1.getAbsolutePath(); // ftech the absolute path of the file
			pathField.setText(dd); // set the path into the specifed text field
			
			// set the specified path into inpuotfile and pass it to scanner class
			File inputfile = new File(pathField.getText());
		
			Scanner scan_input = new Scanner(inputfile);
			dict.defineDictionary(scan_input); // calling the API method from dicitionary
		
			String theListOfPrograms = dict.getListOfPrograms(); // fetch the list of the files
			Scanner scan_programs = new Scanner(theListOfPrograms); // scan all the availabel files
			
			ComboboxOperand3.getItems().clear(); //clear the combobx 
			//ComboBox2.getItems().clear();
			while (scan_programs.hasNext())  // use the while loop, 
				ComboboxOperand3.getItems().add(scan_programs.next()); // add all items into the combobx
			//ComboBox2.getItems().add(scan_programs1.next());
			ComboboxOperand3.getSelectionModel().selectFirst(); // fetch the selected items 
			//ComboBox2.getSelectionModel().selectFirst();
			dict.setSearchString(ComboboxOperand3.getSelectionModel().getSelectedItem()); // call the method from the dictionary API
			
			writeProgram.appendText(dict.findNextEntry().getDefinition().toString().trim()); // append all the text
	}
	 
	 
	 	/**********
		 * This method is called when user click on the save button,
		 * This method initiates the window having a single text field as well as
		 * label and buttons.
		 * 
		 */
	 
		public static void dynamicName()
		{
			Stage stage = new Stage(); // declaring the stage
			 Pane pane = new Pane(); // declaring the pane
			 
			 // defining the label and setting up its attributes for further use
			 Label label = new Label("Enter file name: ");
			 label.setLayoutX(80);label.setLayoutY(100);
			 
			 //setting up the properties of textfield present in the popup window
			 saveFileNametxt.setLayoutX(80);
			 saveFileNametxt.setLayoutY(125);
			 saveFileNametxt.setMinWidth(250);
			 
			  // create a buuton and define its properties
			         Button btnLogin = new Button();    
			         btnLogin.setLayoutX(350);btnLogin.setLayoutY(125);
			         btnLogin.setText("Save File as");
			         
			         //event handling of the button for its specific functioning
			          btnLogin.setOnAction((event) ->{ 	try {
			        	  cbox.getItems().add(saveFileNametxt.getText());
			        	
						createProgramFile();
			          } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			         
			          stage.hide(); 	});
			         
			         // adding the relevant el	ements to the pane
			         pane.getChildren().addAll(label,btnLogin,saveFileNametxt);
			      
			 		  Scene scene = new Scene(pane, 500, 300); // setting up the attribute of the scene
			          stage.setScene(scene);
			          stage.show();
			 
		}
		
		/**********
		 * This method is called when user click on the save button. THis method stores the file into the 
		 * data structures called list and then fetch the file by using combobx.
		 * 
		 * @throws IOException -  exception to handle IO errors
		 */
		
		public static void createProgramFile() throws IOException
		{
				List<List<String>> list = new ArrayList<>(); // declaring a list of types string
				
				 File file = new File("Programs\\"+programUserInterface.saveFileNametxt.getText()); // providing the path of the files
	            
				String fileName = file.getAbsolutePath(); // fetch the specified path of file and store it into string

				// use for loop, and add the file into list
				for (int i = 0; i < 1; i++) {
					list.add(new ArrayList<>()); // add file into list
					list.get(0).add("Dictionary" + "\n" + "Demo"+ "\n"
							+"\t"+ writeProgram.getText());
					}
	
				dict.saveDictionary(list, fileName); // calling the method from the dictionary API
			
		}
		
		/**********
		 * The addSub Expression method parses a sequence of expression elements that are added
		 * together, subtracted from one another, or a blend of them.
		 * 
		 * @return	The method returns a boolean value indicating if the parse was successful
		 */
		
		public static boolean addSubExpr() {

			// The method assumes the input is a sequence of additive/subtractive elements separated
			// by addition and/or subtraction operators
			if (mpyDivExpr()) {
				
				// Once an additive/subtractive element has been found, it can be followed by a
				// sequence of addition or subtraction operators followed by another 
				// additive/subtractive element.  Therefore we start by looking for a "+" or a "-"
				while ((current.getTokenKind() == Kind.SYMBOL) && 
						((current.getTokenCode() == 6) ||		// The "+" operator
						 (current.getTokenCode() == 7))) {		// The "-" operator
					
					// When you find a "+" or a "-", push it onto the operator stack
					opStack.push(current);
					
					// Advance to the next input token
					current = next;
					
					next = lexer.accept();
					
					// Look for the next additive/subtractive element
					if (mpyDivExpr()) {
						
						// If one is found, pop the two operands and the operator
						ExprNode expr2 = exprStack.pop();
						ExprNode expr1 = exprStack.pop();
						Token oper = opStack.pop();
						
						// Create an Expression Tree node from those three items and push it onto
						// the expression stack
						exprStack.push(new ExprNode(oper, true, expr1, expr2));
					}
					else {
						
						// If we get here, we saw a "+" or a "-", but it was not followed by a valid
						// additive/subtractive element
						solutionProgram.setText("Parse error: A required additive/subtractive element was not found"+"/n");
						
						return false;
					}
				}
				
				// Reaching this point indicates that we have processed the sequence of 
				// additive/subtractive elements
				return true;
			}
			else
				
				// This indicates that the first thing found was not an additive/subtractive element
				return false;
		}
		
		/**********
		 * The mpyDiv Expression method parses a sequence of expression elements that are multiplied
		 * together, divided from one another, or a blend of them.
		 * 
		 * @return	The method returns a boolean value indicating if the parse was successful
		 */
		public static boolean mpyDivExpr() {

			// The method assumes the input is a sequence of terms separated by multiplication and/or 
			// division operators
			if (term()) {
				
				// Once an multiplication/division element has been found, it can be followed by a
				// sequence of multiplication or division operators followed by another 
				// multiplication/division element.  Therefore we start by looking for a "*" or a "/"
				while ((current.getTokenKind() == Kind.SYMBOL) && 
						((current.getTokenCode() == 8) ||		// The "*" operator	
						 (current.getTokenCode() == 9))) {		// The "/" operator
					
					// When you find a "*" or a "/", push it onto the operator stack
					opStack.push(current);
					
					// Advance to the next input token
					current = next;
					next = lexer.accept();
					
					// Look for the next multiplication/division element
					if (term()) {
						
						// If one is found, pop the two operands and the operator
						ExprNode expr2 = exprStack.pop();
						ExprNode expr1 = exprStack.pop();
						Token oper = opStack.pop();
						
						// Create an Expression Tree node from those three items and push it onto
						// the expression stack
						exprStack.push(new ExprNode(oper, true, expr1, expr2));
					}
					else {
						
						// If we get here, we saw a "*" or a "/", but it was not followed by a valid
						// multiplication/division element
						solutionProgram.setText("Parse error: A required multiplcative/division element was not found"+"\n");
						return false;
					}
				}
				
				// Reaching this point indicates that we have processed the sequence of 
				// additive/subtractive elements
				return true;
			}
			else
				
				// This indicates that the first thing found was not a multiplication/division element
				return false;
		}
		
		/**********
		 * The term Expression method parses constants.
		 * 
		 * @return	The method returns a boolean value indicating if the parse was successful
		 */
		private static boolean term() throws EmptyStackException{
			
			if (current.getTokenKind() == Kind.IDENTIFIER) {
				// When you find one, push a corresponding expression tree node onto the stack
				exprStack.push(new ExprNode(current, false, null, null));
			
				current = next;
				
				next = lexer.accept();
				
				return true;
			}
//			
			else if (current.getTokenKind() == Kind.FLOAT || current.getTokenKind() == Kind.INTEGER) {
				// When you find one, push a corresponding expression tree node onto the stack
				exprStack.push(new ExprNode(current, false, null, null));
			
				current = next;
				
				next = lexer.accept();
				
				return true;
			}
//			
			// check that if there is a symbol having a token code 4
			else if(current.getTokenKind() == Kind.SYMBOL && current.getTokenCode() == 4)
			{
				
					current = next;  // if yes then skip it
					
					next = lexer.accept();  // and acccept the next token
					
				addSubExpr(); // call the method for the further computations 
					
				
				//check if there is symbol of token code 5
				if(current.getTokenKind() == Kind.SYMBOL && current.getTokenCode() == 5)
				{
					
						current = next; // if yes then skip it 
						next = lexer.accept(); // and accpet the next token
					}
//				
//			
				return true;  // retun the true value
				
			}		

			//check if there is symbol of token code 7
				else if (current.getTokenKind() == Kind.SYMBOL && current.getTokenCode() == 7) {
							
										
					// fetch the kind of the next token, fetcht the next token code and fetch the current text and next token text
								next = new Token(next.getTokenKind(), (int)next.getTokenCode(), current.getTokenText() + next.getTokenText());
								
								exprStack.push(new ExprNode(next, false, null, null)); // push the next value into the stack
			
								current = lexer.accept(); // accpet the current values
								next = lexer.accept(); // accpet the next values
								
							return true;  // return the true
			
						}
			

				//check if there is symbol of token code 3
				else if(current.getTokenKind() == Kind.SYMBOL && current.getTokenCode() == 3)
				{
					current = next; // skip the current value
					next = lexer.accept(); // accept the next value
					
					addSubExpr(); // call the method for the further computations
					
					
					return true;
				}
			
						else {
							// This indicates that the first thing found was not an additive/subtractive element
							solutionProgram.setText("Error...!" +"\n");
							solutionProgram.appendText("Please enter at least two values");
							
							return false;
						}


		}
		
		/**********
		 * The compute method is passed a tree as an input parameter and computes the value of the
		 * tree based on the operator nodes and the value node in the tree.  Precedence is encoded
		 * into the tree structure, so there is no need to deal with it during the evaluation.
		 * 
		 * @param r - The input parameter of the expression tree
		 * 
		 * @return  - A double value of the result of evaluating the expression tree
		 */
		public static double compute(ExprNode r) {

			// Check to see if this expression tree node is an operator.
			if ((r.getOp().getTokenKind() == Kind.SYMBOL) && ((r.getOp().getTokenCode() == 6) ||
					(r.getOp().getTokenCode() == 7) || (r.getOp().getTokenCode() == 8) ||
					(r.getOp().getTokenCode() == 9))) {

				
				// if so, fetch the left and right sub-tree references and evaluate them
				double leftValue = compute(r.getLeft());
				double rightValue = compute(r.getRight());
				
				// Give the value for the left and the right sub-trees, use the operator code
				// to select the correct operation
				double result = 0;
				switch ((int)r.getOp().getTokenCode()) {
				case 6: result = leftValue + rightValue; break;
				case 7: result = leftValue - rightValue; break;
				case 8: result = leftValue * rightValue; break;
				case 9: result = leftValue / rightValue; break;
				}
				
				// Display the actual computation working from the leaves up to the root
				
				solutionProgram.appendText("  "+"\n" + result + " = " + leftValue + r.getOp().getTokenText() + rightValue );
				
				// Return the result to the caller
				
				return result;
				
			}
			
			// If the node is not an operator, determine what it is and fetch the value 
			else if (r.getOp().getTokenKind() == Kind.INTEGER) {
				Scanner convertInteger = new Scanner(r.getOp().getTokenText());
				Double result = convertInteger.nextDouble();
				convertInteger.close();
				return result;
			}
			else if (r.getOp().getTokenKind() == Kind.FLOAT) {
				Scanner convertFloat = new Scanner(r.getOp().getTokenText());
				Double result = convertFloat.nextDouble();
				convertFloat.close();
				return result;
			}
			
			
			
			// check if there is a kind of indentifier 
			else if (r.getOp().getTokenKind() == Kind.IDENTIFIER) {
				
				String TtknTxt = r.getOp().getTokenText(); // if yes, then store the value into string
				
				double fetchingValues = 0; // initiate the double value
			//	ManageDefinations a = new ManageDefinations(); // creating an object of ManageDefinations.java
				
				if(Arrays.asList(VName).contains(TtknTxt)) {
					int indexFetcher = Arrays.asList(VName).indexOf(TtknTxt);
					
					return value[indexFetcher];
				}
				else {
				File file = new File("RepositoryFolder\\"+UserInterface.LoadFiletxt.getText());  // fetch the path of the 

				Scanner scanner; // creating an scanner class
				try {
					scanner = new Scanner(file); // pass the filepath into the scanner class
					while (scanner.nextLine() != null) { // if nextline is not null
						while (scanner.hasNextLine()) { // if there is nextline
							try {
								String[] tokens = scanner.nextLine().split("-"); // then split the tokens by using"-"
						
								if (TtknTxt.matches(tokens[0])) { // if token at index 0 mathces wiht the input
									fetchingValues = Double.parseDouble(tokens[2]); // then get the values at 2 index
								}
								
							} catch (ArrayIndexOutOfBoundsException e) {
								e.printStackTrace();
							}
						}
						break;
					}
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				return fetchingValues;
				}
			}

			// If it is not a recognized element, treat it as a value of zero
			else return 0.0;
		}
		
		/**********
		 * The method is called when the user clcik on the Run button. This method baseically, perform all
		 * the computations and evaluate the expression as per the precedence rule.
		 * 
		 * @param exp - input fetch from the text area of the program
		 * 
		 */
		
		
		public static void runExpressions(String exp)
		{
			outputProgram.clear(); // clear the program filed
			theReader = new Scanner(exp); //read the expression using the scanner class
			
			// Set up the Scanner and the Lexer
			lexer = new Lexer(theReader); 
			
			boolean isValid = true; // Inititate the isValid true;
			
			
			current = lexer.accept(); // accept the current 
			next = lexer.accept();	// and the next value throught the lexerical concept
			
			
			//cehck if the expression contain any identifier,
			if(current.getTokenKind() == Kind.IDENTIFIER) // if yes, 
			{
				current = next; // then skip the identifier
				next = lexer.accept(); //accept the next value
				
			}
//			
			// Invoke the parser and the tree builder
			 isValid = addSubExpr();
		
			 //ifcondition is valid, then
			 if (isValid) {
					
					// Display the expression tree
					ExprNode theTree = exprStack.pop();
						
					solutionProgram.appendText( "\n\nThe Computation Process: \n"); // display the messgae
					outputProgram.setText("The resulting value is: " + compute(theTree)); // set the text for the computations
							
					}
			 
				}
	
		/**********
		 * The method is at the time of computation when the user click on the toogle button for the prorgam
		 * This method basically compute the values in terms of sub, div, mpy and sum
		 * 
		 * @param exp - input fetch from the text area of the program
		 * 
		 */
				
		public static void getComputationResult(String exp)
		{
			Scanner scanner = new Scanner(exp); // inititae the scanner class
			
			lexer = new Lexer(scanner); // pass the scanner into lexer class
		
			current = lexer.accept(); // accpet the current
			next = lexer.accept(); // and the next value

			// Invoke the parser and the tree builder
			boolean isValid1 = addSubExpr(); 
			
			// if it is true,
			if (isValid1) {

				// Display the expression tree
				ExprNode theTree = exprStack.pop();
			
				// Evaluate the expression tree
				compute(theTree);
//				System.out.println("\nThe resulting value is: " + compute(theTree));
				
			}
			else {
				solutionProgram.clear(); // else, makes the text area, clear
			}
		}
		
		/**********
		 * The method is called when the user clcik on the toggle button basicalyy used to run the statement based prorga,
		 * This method baseically, perform all the computations and evaluate the statements as per user's order.
		 * 
		 * @param theReader - scanner class to read the input
		 * 
		 */
	
		public static void runProgram(Scanner theReader)
		{
//			
				line = theReader.nextLine(); //scann the whole input from the tex area
				Scanner nxtLines = new Scanner (line); // scan the number of lines
				
				lexer = new Lexer(nxtLines); // pass the scanner class into lexer
			
				// accept the current and next values both
				current = lexer.accept();
				next = lexer.accept();


					// Invoke the parser and the tree builder
					boolean isValid = addSubExpr();
					
					// if the expreesion is true					
					if (isValid) { //then
						
							solutionProgram.setEditable(true); // make it editable to write the values

								if (line.contains("print:")) { // if the input lines contain the print value
									
									// start the scnner class and pass the contain line
									Scanner printScanner = new Scanner(line);
									
									// skip the print line
									printScanner.skip("print: "); 
									//read the lines which contain the print word
									String readPrintLines = printScanner.nextLine();
									
									solutionProgram.appendText("\n"+readPrintLines); // set the text into the result area
									
									// if the inout contain more print keywords, then recursively call the method
									if (theReader.hasNextLine()) { runProgram(theReader);}
									
								}
									
								// same for the input, first check the key word
								else if (line.contains("input")) {
									// if find, then append the text to the result
									solutionProgram.appendText("\n");
									
									solutionProgram.setOnKeyPressed((event) -> // if the solution program has been presend by a key
									{
										if(event.getCode() == KeyCode.ENTER) //if the button is pressed is enter
										{
											// start the scnner class from the result area,
											Scanner sc = new Scanner(solutionProgram.getText());
																						
											String ab1 = line.substring(6); // read the line from the substring 
										
											VName[count] = ab1; // add the value from substring into the array
											
											// again use the scanner class, from the reslt field
											Scanner theReaderw2 = new Scanner (solutionProgram.getText());
											 
											   String scan = null;
										        while(theReaderw2.hasNextLine()) { //while there is next lines
										            scan = theReaderw2.nextLine(); // read the line by lines..
										            
										            //treatment
									        }
										      // convert the value into double 
										        double valuef = Double.parseDouble(scan);
										   
										        value[count] = valuef; // add it into array 
										        
										        count++;  // increase the count value
										        
										        // agin check if there is inout line or not
											if (theReader.hasNextLine()) {
												runProgram(theReader); // recursivly call the method
											}
										}
									});
								}
								
								// same for the SUM keyword, if find 
								else if(line.contains("sum") ){ 
									
									// use the scanner class
									Scanner sc = new Scanner(line);  
									sc.skip("sum");  //skip the first keyword
																	
									String j1 = sc.nextLine(); // read the line
									
									getComputationResult(j1); // recusivley call the method
//									
									// if there is value,
									 if(theReader.hasNextLine()) {	
										 // agin call the method
										 runProgram(theReader);
									 }
									 
									
								}
								// same for the SUM keyword, if find 
								else if(line.contains("mpy") ){ 
									
									// use the scanner class
									Scanner sc = new Scanner(line);  
									sc.skip("mpy");  //skip the first keyword
																	
									String j1 = sc.nextLine(); // read the line
									
									getComputationResult(j1); // recusivley call the method
//									
									// if there is value,
									 if(theReader.hasNextLine()) {	
										 // agin call the method
										 runProgram(theReader);
									 }
									 
									
								}
								// same for the SUM keyword, if find 
								else if(line.contains("div") ){ 
									
									// use the scanner class
									Scanner sc = new Scanner(line);  
									sc.skip("div");  //skip the first keyword
																	
									String j1 = sc.nextLine(); // read the line
									
									getComputationResult(j1); // recusivley call the method
//									
									// if there is value,
									 if(theReader.hasNextLine()) {	
										 // agin call the method
										 runProgram(theReader);
									 }
									 
									
								}
								// same for the SUM keyword, if find 
								else if(line.contains("sub") ){ 
									
									// use the scanner class
									Scanner sc = new Scanner(line);  
									sc.skip("sub");  //skip the first keyword
																	
									String j1 = sc.nextLine(); // read the line
									
									getComputationResult(j1); // recusivley call the method
//									
									// if there is value,
									 if(theReader.hasNextLine()) {	
										 // agin call the method
										 runProgram(theReader);
									 }
								}											
								
								else {	// Display the expression tree
									ExprNode theTree = exprStack.pop();
									
									System.out.println("\nThe resulting value is: " + compute(theTree));
									solutionProgram.appendText( "The Computation Process: \n"); // display the messgae
										
									// if there is simple expreresions
									if(theReader.hasNextLine()) {	//then,
										// agina call the method 
										runProgram(theReader);
										  
									 }
								}
								
					}
					else { // else,
						solutionProgram.clear(); // clear the result field
					}
			}
		
		/**********
		 * This method is called at the time of debug, This method basically perform the operations whihc 
		 * shows the error flow during the evaluations of the program.
		 *  
		 * @param exp - value from the program field
		 * 
		 */
				
		public static void debugProgram(String exp)
		{
			
			theReader = new Scanner(exp); // read the text from the program field
						
			// Set up the Scanner and the Lexer
			lexer = new Lexer(theReader);
			current = lexer.accept(); // accept the current 
			next = lexer.accept();// and next values

			// Invoke the parser and the tree builder
			boolean isValid = addSubExpr(); // for addSub mehotd
			boolean isValid2 = mpyDivExpr(); // for mpyDiv method
			boolean isValid3 = term(); // for term method
			
			// if the addSub is invalid
			if (!isValid) {
				// print this error line
				solutionProgram.appendText("\n" + "please check you expression"+"\n");
			}
			// if term is in valid
			else if(!isValid3)
			{ // print this line
				solutionProgram.appendText("\n"+"Error..!" +"\n"+"Expression Invalid..!");
				}
				else if(!isValid || !isValid2) // if both are invalid
					{
					// print this lines for error
						solutionProgram.setText("A required term was not found");
					}
			}
		}