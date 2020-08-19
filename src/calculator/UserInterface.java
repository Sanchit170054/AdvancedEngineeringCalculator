package calculator;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import calculator.CalculatorValue;
import calculator.DefinationsUserInterface.Quantity;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import calculator.BusinessLogic;

/**
 * <p> Title: UserInterface Class. </p>
 * 
 * <p> Description: The Java/FX-based user interface for the calculator. The class works with String
 * objects and passes work to other classes to deal with all other aspects of the computation.</p>
 * 
 * <p> Copyright: Lynn Robert Carter Â© 2017 </p>
 * 
 * @author Lynn Robert Carter
 * updated Author Sanchit, Shivam Singhal (Helper in LookUp table)
 * 
 * @version 4.4	2018-02-27 Simple Userinterface for the all Opertors
 * @version 4.5 2018-03-06 Implementation of ErrorTerm
 * @version 4.6 2018-11-24 This class is enhanced by the SI Units which are used for the computation using all the Operators
 * @version 4.7 2019-02-24 This is the class which supports the user to interact with UI and this class is enhacned to perform computations
 * 						   over variables or constants
 * 
 */
public class UserInterface {

	/**********************************************************************************************

	Attributes
s
	 **********************************************************************************************/

	/* Constants used to parameterize the graphical user interface.  We do not use a layout manager for
	   this application. Rather we manually control the location of each graphical element for exact
	   control of the look and feel. */
	
	private final double BUTTON_WIDTH = 60;
	private final double BUTTON_OFFSET = BUTTON_WIDTH / 2;
	private double buttonSpace;
	public static BusinessLogic perform = new BusinessLogic();
	public CalculatorValue cv = new CalculatorValue();
	public DefinationsUserInterface definitionUI;
	public programUserInterface ProgramUI;
	
	// These are the application values required by the user interface
	
	// various required labels and textfield used for UI of calculator
	private Label label_Calculator = new Label("Science and Engineering Calculators with Units");
	private Label label_Operand1 = new Label(" First operand ");
	public static TextField text_Operand1 = new TextField();
	private Label label_Operand2 = new Label(" Second operand");
	private static TextField text_Operand2 = new TextField();
	private Label label_Result = new Label("Result ");
	public static TextField text_Result = new TextField();
	public static TextField TextErrorTerm1 = new TextField();
	private static TextField TextErrorTerm2 = new TextField();
	public static TextField TextResultErrorTerm = new TextField();
	
	public Label loadfile = new Label("Enter file name to read data: ");
	public static TextField LoadFiletxt = new TextField();
	 public static TextField textUser = new TextField();	
	
	// Some of the essntial application values required for the display of the error
	// messages
	private Label label_Operand2Errro2 = new Label("");	            
	private Label label_ResultError = new Label("");	
	private Label label_Operand1Error = new Label("");               
	private Label label_Operand2Error = new Label("");              
	private Text operand1ErrPart2 = new Text();
	private Text operand2ErrPart1 = new Text();
	private Label label_Operand1Errro2 = new Label("");              
	private Label label_errOperand1ETerm = new Label("");                 
	private Label label_errOperand2ETerm = new Label(""); 
	private Text operand1ErrPart1 = new Text();                     
	private Text operand2ErrPart2 = new Text();
	private Text Operand2ErrorTermErrPart1 = new Text();
	private Text Operand2ErrorTermErrPart2 = new Text();
	private Text Operand1ErrorTermErrPart1 = new Text();
	private Text Operand1ErrorTermErrPart2= new Text();
	private TextFlow Operand1ErrorFlow;
	private TextFlow Operand2ErrorFlow;
	private TextFlow Operand1ErrorTermErrorFlow;
	private TextFlow Operand2ErrorTermErrorFlow;

	// buttons for the different computations
	public static Button button_Addition = new Button("+");
	private Button button_Subtraction = new Button("-");
	private Button button_Multiplication = new Button("*");	
	private Button button_Division = new Button("/");	
	private Button button_Squareroot = new Button("\u221A");  // Uni Code for the square root button
	private Button definationDemo = new Button("Add Definations");
	
	public Button loadButton = new Button("Load file's data");
	public Button saveComputation = new Button("Save Computed Result");
	
	// label for the plus/minus sign 
	private static Label PlusMinus1 = new Label ("\u00B1");  // Unit code for the +/- label
	private Label PlusMinus2 = new Label ("\u00B1");
	private Label PlusMinus3 = new Label ("\u00B1");

	// Various combobox defined for the each operands
	public static ComboBox<String> ComboboxOperand1 = new ComboBox<String>();
	public static ComboBox<String> ComboboxOperand2 = new ComboBox<String>();
	public static ComboBox<String> ComboboxOperand3 = new ComboBox<String>();
	
	// These are some boolean values whihc are used for the compuatations in terms of Units

//	private  boolean CalculationIsPossible = true;  // check whethe the calculation possible or not
	private boolean addIsPossible = false;  // used in computation for addition methods
	private boolean subIsPossible = false; // used in computation for subtraction methods
	private boolean mulitplyIsPossible = false; // used in computation for multiplication methods
	private boolean divisionIsPossible = false; // used in computation for division methods
	private boolean squarerootIsPossible = false; // used in computation for squareroot methods
	
	// Various lists are used to differntaite the units in terms lenght, velocity and time
	public static List<String> VelocityUnits = new Vector<String>();
	public static List<String> LengthUnits = new Vector<String>();
	public static List<String> TimeUnits = new Vector<String>();
	
	public static String ResultantUnit= null;  // before computation, resultant combobx will be empty
	
	public Stage theStage = new Stage();
	public Button btnLogin = new Button();
		
	// This attribute holds the text that will be displayed 
	public TextField blk_Text = new TextField();
	public TextField blk_Text1 = new TextField();
	public Label lbl_blk = new Label("For Operand 1");
	public Label lbl_blk1 = new Label("For Operand 2");
	
	// Some of the necessary attributes that we will use for further process
	 private Label FileFoud = new Label(""); 
	 private Label message = new Label("");
	 private Label FIleNotFound = new Label("");
	 private Label message_ErrorDetails = new Label("");
	 	 
	 private String str_FileName;   // The string that the user enters for the file name
	 private Scanner scanner_Input = null;  // The Scanners used to evaluate whether or not the
	 private String errorMessage_FileContents = ""; // These attributes are used to tell the user, in detail, about errors in the input data
	 
	 
	 
	 
	 public Button program = new Button("Program");
	 
	 
	/**********************************************************************************************
	Constructors
    **********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the graphical user interface. These assignments
	 * determine the location, size, font, color, and change and event handlers for each GUI object.
	 * 
	 * @param theRoot which carry all the elements used in UI
	 */
	public UserInterface(Pane theRoot) {

		// Managing the space between the buttions
		buttonSpace = Calculator.WINDOW_WIDTH / 6;

		// Label theScene with the name of the calculator, centered at the top of the pane
		setupLabelUI(label_Calculator, "Arial", 24, Calculator.WINDOW_WIDTH, Pos.CENTER, 0, 10);

		// Label the first operand just above it, left aligned
		setupLabelUI(label_Operand1, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 10, 70);

		// Establish the first text input operand field and when anything changes in operand 1,
		// process both fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Operand1, "Arial", 18, Calculator.WINDOW_WIDTH-700, Pos.BASELINE_LEFT, 150, 70, true);
		text_Operand1.setMinWidth(400);
		text_Operand1.textProperty().addListener((observable, oldValue, newValue) -> {setOperand1(); });
		
		// Label the operands to accept the varibles for first operand
		setupLabelUI(lbl_blk, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 120, 420 );
		lbl_blk.setDisable(true);
		blk_Text1.setLayoutX(500);blk_Text1.setLayoutY(450);
		blk_Text1.setDisable(true);
		blk_Text1.setPromptText("Enter your varible here");
		
		// Label the operands to accept the varibles for second operand
		setupLabelUI(lbl_blk1, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 500, 420 );
		lbl_blk1.setDisable(true);
		blk_Text.setLayoutX(120);blk_Text.setLayoutY(450);
		blk_Text.setDisable(true);
		blk_Text.setPromptText("Enter your varible here");
		
		//Event handling of the text field for varibales accpeted operands 
		blk_Text.textProperty().addListener((observable, oldValue, newValue) -> {try {
			loadfile.setDisable(false);loadButton.setDisable(false);
			LoadFiletxt.setDisable(false);
			SetData();} catch (IOException e) {e.printStackTrace();} });
			
		//Event handling of the text field for varibales accpeted operands 
		blk_Text1.textProperty().addListener((observable, oldValue, newValue) -> {try {
			SetData1();	} catch (IOException e) {e.printStackTrace();} });
		
		
		// Move focus to the error term when the user presses the enter (return) key
		text_Operand1.setOnAction((event) -> { TextErrorTerm1.requestFocus();});

		//Plus Minus Label for Operand 1
		setupLabelUI(PlusMinus1, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 570, 70 );

		//Error Term Text Field for Operand 1
		setupTextUI(TextErrorTerm1, "Arial", 18, Calculator.WINDOW_WIDTH-800, Pos.BASELINE_LEFT, 600, 70, true);
		TextErrorTerm1.setMinWidth(400);
		TextErrorTerm1.textProperty().addListener((observable, oldValue, newValue) -> {setOperand1();  });

		// Move focus to the operand 2  when the user presses the enter (return) key
		TextErrorTerm1.setOnAction((event) -> { text_Operand2.requestFocus();});

		// Establish an error message for the first operand just above it with, left aligned
		// Label the Second operand just above it, left aligned
		setupLabelUI(label_Operand2, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 10, 160);

		// Establish the second text input operand field and when anything changes in operand 2,
		// process both fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Operand2, "Arial", 18, Calculator.WINDOW_WIDTH-700, Pos.BASELINE_LEFT, 150, 160, true);
		text_Operand2.setMinWidth(400);
		text_Operand2.textProperty().addListener((observable, oldValue, newValue) -> {setOperand2(); });

		// Move the focus to the error term 2 when the user presses the enter (return) key
		text_Operand2.setOnAction((event) -> { TextErrorTerm2.requestFocus(); });

		//Error Term Text Field for Operand 2
		setupTextUI(TextErrorTerm2, "Arial", 18, Calculator.WINDOW_WIDTH-800, Pos.BASELINE_LEFT, 600, 160, true);
		TextErrorTerm2.setMinWidth(400);
		TextErrorTerm2.textProperty().addListener((observable, oldValue, newValue) -> {setOperand2();});

		// Move focus to the result when the user presses the enter (return) key
		TextErrorTerm2.setOnAction((event) -> { text_Result.requestFocus(); });

		//Plus Minus Label for Operand 2
		setupLabelUI(PlusMinus2, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 570, 160);

		// Label the result just above the result output field, left aligned
		setupLabelUI(label_Result, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 10, 330);

		// Establish the result output field.  It is not editable, so the text can be selected and copied,
		// but it cannot be altered by the user.  The text is left aligned.
		setupTextUI(text_Result, "Arial", 18, Calculator.WINDOW_WIDTH-700, Pos.BASELINE_LEFT, 150, 330, false);
		text_Result.setMinWidth(400);

		//Error Term Result
		setupTextUI(TextResultErrorTerm, "Arial", 18, Calculator.WINDOW_WIDTH-800, Pos.BASELINE_LEFT, 600, 330, false);
		TextResultErrorTerm.setMinWidth(400);

		//Plus Minus Label for Result
		setupLabelUI(PlusMinus3, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 570, 330);

		// Establish the ADD "+" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Addition, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 1 * buttonSpace-BUTTON_OFFSET, 230);
		button_Addition.setOnAction((event) -> { addOperands();});

		// Establish the SUB "-" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Subtraction, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 2 * buttonSpace-BUTTON_OFFSET, 230);
		button_Subtraction.setOnAction((event) -> { subOperands();});

		// Establish the MPY "x" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Multiplication, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 3 * buttonSpace-BUTTON_OFFSET, 230);
		button_Multiplication.setOnAction((event) -> { mpyOperands();});

		// Establish the DIV "/" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Division, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 4 * buttonSpace-BUTTON_OFFSET, 230); 	
		button_Division.setOnAction((event) -> { divOperands();});

		// Establish the SQRT button, and link it to methods to accomplish its work
		setupButtonUI(button_Squareroot, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 5 * buttonSpace-BUTTON_OFFSET, 230 );
		button_Squareroot.setOnAction((event) -> {sqrtOperand();});

		// Establish the SaveComputation button, and link it to methods to accomplish its work
		setupButtonUI(saveComputation, "Times", 20, BUTTON_WIDTH, Pos.BASELINE_LEFT, 5 * buttonSpace-BUTTON_OFFSET, 470 );
		saveComputation.setOnAction((event) -> {saveComputations();});
		saveComputation.setDisable(true);
		
		
		// Establish the SQRT button, and link it to methods to accomplish its work
		setupButtonUI(definationDemo, "Times", 20, BUTTON_WIDTH, Pos.BASELINE_LEFT, 5 * buttonSpace-BUTTON_OFFSET, 400 );
		definationDemo.setOnAction((event) -> {	windowPupup(); saveComputation.setDisable(false);LoadFiletxt.setText("");
		message.setText(""); blk_Text.setDisable(true);blk_Text1.setDisable(true);lbl_blk.setDisable(true);lbl_blk1.setDisable(true);
		loadButton.setDisable(true);
		});
		
		
		
		setupButtonUI(program, "Times", 20, BUTTON_WIDTH, Pos.BASELINE_LEFT, 5 * buttonSpace-BUTTON_OFFSET, 550 );
		program.setOnAction((event) -> {ProgramUI.openPopup();});
		
		
		
		
		// Label the loadFile to specify the calculator that from which file, scan the value
		setupLabelUI(loadfile, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 100, 520 );

		// Establish the loadtext file field for user to enter the name of file from where we read the value
		setupTextUI(LoadFiletxt, "Arial", 18, Calculator.WINDOW_WIDTH-700, Pos.BASELINE_LEFT, 100, 550, true);
		LoadFiletxt.setMinWidth(400);
		
		//Event handling of the textfield to specify messgaes
		LoadFiletxt.textProperty().addListener((observable, oldValue, newValue) -> {
					
					File f = new File("JRE System Libraries\\"+LoadFiletxt.getText());
					if(f.exists())
						
					{
						message.setLayoutX(100);
						message.setLayoutY(600);
						message.setDisable(false);
						final double MAX_FONT_SIZE = 24; 
						message.setFont(new Font(MAX_FONT_SIZE));
						message.setTextFill(Color.web("Green"));
						message.setText("File is Found.......!");
						loadButton.setDisable(false);
					}
					
					else {
						message.setLayoutX(100);
						message.setLayoutY(600);
						message.setDisable(false);
						final double MAX_FONT_SIZE = 24; 
						message.setFont(new Font(MAX_FONT_SIZE));
						message.setTextFill(Color.web("Red"));
						message.setText("File is Not Found....!");
						loadButton.setDisable(true);
					}
				});
				
				// Establish the load button, and link it to methods to accomplish its work
				setupButtonUI(loadButton, "Times", 20, BUTTON_WIDTH, Pos.BASELINE_LEFT, 2.5 * buttonSpace-BUTTON_OFFSET, 540 );
				loadButton.setDisable(true);
				loadButton.setOnAction((event) -> 
				{
							saveComputation.setDisable(false);
							blk_Text.setDisable(false);blk_Text1.setDisable(false);
							lbl_blk.setDisable(false);lbl_blk1.setDisable(false);
							message.setText("Your file has been Loaded....!");
							blk_Text.requestFocus(); 
							
							// Some of the properties for the file found message
							message.setLayoutX(200);
							message.setLayoutY(600);
							final double MAX_FONT_SIZE = 28; 
							message.setFont(new Font(MAX_FONT_SIZE));
							message.setTextFill(Color.web("Green"));
							
							LoadFiletxt.setDisable(false);
							loadButton.setDisable(true);
							loadfile.setDisable(true);
					
				});
					
				
		// Establish an error message for the first operand just above it , left aligned
		setupLabelUI(label_Operand1Errro2, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 180, 45);
		label_Operand1Errro2.setTextFill(Color.RED);

		//FSM Specific Error Message for Operand 1
		label_Operand1Error.setTextFill(Color.RED);
		label_Operand1Error.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_Operand1Error, "Arial", 14,  Calculator.WINDOW_WIDTH-150-10, Pos.BASELINE_LEFT, 150, 120);

		//FSM Specific Error Message for Operand 1 Error Term
		label_errOperand1ETerm.setTextFill(Color.RED);
		label_errOperand1ETerm.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand1ETerm, "Arial", 14,Calculator.WINDOW_WIDTH-150-10, Pos.BASELINE_LEFT, 540, 118);

		//FSM Specific Error Message for Operand 1
		label_Operand2Error.setTextFill(Color.RED);
		label_Operand2Error.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_Operand2Error, "Arial", 14, Calculator.WINDOW_WIDTH-150-10, Pos.BASELINE_LEFT, 150, 210);
		label_Operand2Error.setTextFill(Color.RED);

		//FSM Specific Error Message for Operand 2 Error Term
		label_errOperand2ETerm.setTextFill(Color.RED);
		label_errOperand2ETerm.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand2ETerm, "Arial", 14, Calculator.WINDOW_WIDTH-160, Pos.BASELINE_LEFT, 540,208);

		//Establish an error messges for the operand 1
		operand1ErrPart1.setFill(Color.RED);
		operand1ErrPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		operand1ErrPart2.setFill(Color.RED);
		operand1ErrPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		Operand1ErrorFlow = new TextFlow(operand1ErrPart1, operand1ErrPart2);
		Operand1ErrorFlow.setMinWidth(Calculator.WINDOW_WIDTH-60);
		Operand1ErrorFlow.setLayoutX(160);
		Operand1ErrorFlow.setLayoutY(100);

		//Establish an error message for the operand error term 1
		Operand1ErrorTermErrPart1.setFill(Color.RED);
		Operand1ErrorTermErrPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		Operand1ErrorTermErrPart2.setFill(Color.RED);
		Operand1ErrorTermErrPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		Operand1ErrorTermErrorFlow =  new TextFlow(Operand1ErrorTermErrPart1, Operand1ErrorTermErrPart2);

		//Establish an error messages for the operand 2
		operand2ErrPart1.setFill(Color.RED);
		operand2ErrPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		operand2ErrPart2.setFill(Color.RED);
		operand2ErrPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		Operand2ErrorFlow = new TextFlow(operand2ErrPart1, operand2ErrPart2);
		Operand2ErrorFlow.setMinWidth(Calculator.WINDOW_WIDTH-10);
		Operand2ErrorFlow.setLayoutX(160);
		Operand2ErrorFlow.setLayoutY(190);

		//Establish an error message for the operand 2 error term
		Operand2ErrorTermErrPart1.setFill(Color.RED);
		Operand2ErrorTermErrPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		Operand2ErrorTermErrPart2.setFill(Color.RED);
		Operand2ErrorTermErrPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		Operand2ErrorTermErrorFlow =  new TextFlow(Operand2ErrorTermErrPart1, Operand2ErrorTermErrPart2);

		//Aligning the Text Flow Elements
		Operand1ErrorTermErrorFlow.setMinWidth(Calculator.WINDOW_WIDTH-10);
		Operand1ErrorTermErrorFlow.setLayoutX(600);
		Operand1ErrorTermErrorFlow.setLayoutY(100);
		Operand2ErrorTermErrorFlow.setMinWidth(Calculator.WINDOW_WIDTH-10);
		Operand2ErrorTermErrorFlow.setLayoutX(600);
		Operand2ErrorTermErrorFlow.setLayoutY(190);
		

		// Some of the properties for the file found message
					FileFoud.setLayoutX(100);
					FileFoud.setLayoutY(180);
					final double MAX_FONT_SIZE = 18; 
					FileFoud.setFont(new Font(MAX_FONT_SIZE));
					FileFoud.setTextFill(Color.web("Green"));
					 
				// Some of the properties of the error message for file not found
					FIleNotFound.setLayoutX(100);
					FIleNotFound.setLayoutY(180);
					final double MAX_FONT_SIZE1 = 18; 
					FIleNotFound.setFont(new Font(MAX_FONT_SIZE1));
					FIleNotFound.setTextFill(Color.web("Red"));
			 		
		
		// Setting the position of the operands combo box
		setupComboBoxUI(ComboboxOperand1, "Arial", 18, Calculator.WINDOW_WIDTH-1350, Pos.BASELINE_LEFT,1050, 70, true);
		setupComboBoxUI(ComboboxOperand2, "Arial", 18, Calculator.WINDOW_WIDTH-1350, Pos.BASELINE_LEFT, 1050, 160, true);
		setupComboBoxUI(ComboboxOperand3, "Arial", 18, Calculator.WINDOW_WIDTH-1300, Pos.BASELINE_LEFT, 1050, 330, true);

		// perfroming the event handling of the combo box for the various and distinct actions
		ComboboxOperand1.setOnAction((event)->{cv.clearResultField(); setOperandsDueToChangedUnits();});
		ComboboxOperand2.setOnAction((event)->{cv.clearResultField(); setOperandsDueToChangedUnits();});
		ComboboxOperand3.setOnAction((event)->{cv.changeResultantUnit();});

		// add the combo box elements into the unit list for different dimensions
		cv.unitsList(ComboboxOperand1); cv.unitsList(ComboboxOperand2);
		ComboboxOperand1.getSelectionModel().select("NO_UNIT");
		ComboboxOperand2.getSelectionModel().select("NO_UNIT");

		// Place all of the just-initialized GUI elements into the pane
		theRoot.getChildren().addAll(label_Calculator, label_Operand1, text_Operand1, label_Operand1Error,label_Operand2, text_Operand2, label_Operand2Error, label_Result, text_Result, label_ResultError,
		button_Addition, button_Subtraction, button_Multiplication, button_Division, button_Squareroot, definationDemo,TextErrorTerm1, PlusMinus1, PlusMinus2
		,TextErrorTerm2, TextResultErrorTerm, PlusMinus3, label_Operand2Errro2, label_Operand1Errro2, label_errOperand1ETerm, label_errOperand2ETerm,Operand1ErrorFlow,Operand2ErrorFlow,
		Operand1ErrorTermErrorFlow,Operand2ErrorTermErrorFlow,ComboboxOperand1,ComboboxOperand2,ComboboxOperand3, blk_Text, loadfile, 
		LoadFiletxt, loadButton, blk_Text1, lbl_blk, lbl_blk1,FileFoud,message, saveComputation,program);}



	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
	l.setFont(Font.font(ff, f));
	l.setMinWidth(w);
	l.setAlignment(p);
	l.setLayoutX(x);
	l.setLayoutY(y);	
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
	t.setFont(Font.font(ff, f));
	t.setMinWidth(w);
	t.setMaxWidth(w);
	t.setAlignment(p);
	t.setLayoutX(x);
	t.setLayoutY(y);	
	t.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
	b.setFont(Font.font(ff, f));
	b.setMinWidth(w);
	b.setAlignment(p);
	b.setLayoutX(x);
	b.setLayoutY(y);	
	}

	private void setupComboBoxUI(ComboBox<String> t, String ff, double f, double w, Pos p, double x, double y, boolean e){
	t.setMinWidth(w);
	t.setMaxWidth(w);
	t.setLayoutX(x);
	t.setLayoutY(y);
	t.setEditable(e);
	}


	/**********************************************************************************************

	User Interface Actions

	 **********************************************************************************************/

	
	/************
	 * Set Operands set the value of operands as combination of their measured values and error terms
	 * as they could be used as attributes to a single object in CalculatorValue Constructor.
	 */
	

		/**********
	 * Setting the values for the operand 1 and its error term (error term 1)
	 */
	private void setOperand1()  {
		
		// removing the current error messages
		Operand1ErrorTermErrPart1.setText("");
		Operand1ErrorTermErrPart2.setText("");
			cv.clearResultField();

			label_Result.setText("Result");	
			label_ResultError.setText("");
			label_Operand1Error.setText("");
			label_Operand1Errro2.setText("");
			operand1ErrPart1.setText("");
			operand1ErrPart2.setText("");
			label_errOperand1ETerm.setText("");
			
			errorMessageOP1(); // calling the method to check the errors

			if (text_Operand1.getText().length()!=0 && TextErrorTerm1.getText().length()!=0)
			{ 
				if (perform.setOperand1(text_Operand1.getText(),TextErrorTerm1.getText())) { //Once both the attributes required for a CalculatorValue object are present
				
					//Create the CalculatorValue object and clear 
						label_Operand1Error.setText(""); //all the error messages
						label_Operand1Errro2.setText("");
						operand1ErrPart1.setText("");
						operand1ErrPart2.setText("");

							if (text_Operand2.getText().length() == 0)	
								label_Operand2Error.setText("");
							}
					}
			}

	/**********
	 * This method is call by the Variable operand 1 and is used to read the values from the user specify 
	 * file. This method automatically fill the values of computed operands based on the defined value 
	 * in the specifed file 
	 * 
	 * @throws IOException -  exception to handle IO errors
	 */
	
	public void SetData() throws IOException{
	
	        FileReader fr = new FileReader("JRE System Libraries\\"+LoadFiletxt.getText());  // using filereader to read the file and then
	        BufferedReader br = new BufferedReader(fr);  // buffer reader to append it
	        String s;
	        String keyword = blk_Text.getText();  // after that get the text from the textfield
	      
	        while ((s=br.readLine())!=null) {   // check if the word is exist
	            if(s.contains(keyword)) { // if yes, the read it
	           
	            	// then again read the other four lines from that particulr string
	                String nextLine = br.readLine();
	                String nextLine1 = br.readLine();
	                String nextLine2 = br.readLine(); 
	                String nextLine3 = br.readLine(); 
	           
	                // fill the value as per the defind value from the file
	                text_Operand1.setText(nextLine1.toString());
	                TextErrorTerm1.setText(nextLine2.toString());
	                ComboboxOperand1.getSelectionModel().select(nextLine3);
	              } 
	            
	            
	        }
		}
	
	/**********
	 * This method is call by the Variable operand 1 and is used to read the values from the user specify 
	 * file. This method automatically fill the values of computed operands based on the defined value 
	 * in the specifed file 
	 * 
	 * @throws IOException -  exception to handle IO errors
	 */
		
	public void SetData1() throws IOException{		
		
			FileReader fr = new FileReader("JRE System Libraries\\"+LoadFiletxt.getText() );  // using filereader to read the file and then
	        BufferedReader br = new BufferedReader(fr);  // buffer reader to append it
	        String s;
	        String keyword = blk_Text1.getText();  // after that get the text from the textfield
	      
	        while ((s=br.readLine())!=null) {   // check if the word is exist
	            if(s.contains(keyword)) { // if yes, the read it
	            
	            	// then again read the other four lines from that particulr string
	                String nextLine = br.readLine();
	                String nextLine1 = br.readLine();
	                String nextLine2 = br.readLine(); 
	                String nextLine3 = br.readLine(); 
	        
	                // fill the values as per the defned values from the specifed file.
	                text_Operand2.setText(nextLine1.toString());
	                TextErrorTerm2.setText(nextLine2.toString());
	                ComboboxOperand2.getSelectionModel().select(nextLine3);
	                
	               }
	            }
	        }

	/*** 
	 * Setting the values for the operand 2 and its error term (error term 2)
	 */

	private void setOperand2()  {
		// removing the current error messages
	Operand2ErrorTermErrPart1.setText("");
	Operand2ErrorTermErrPart2.setText("");
		cv.clearResultField();
						
		label_Result.setText("Result");	
		label_ResultError.setText("");
		label_Operand2Error.setText("");
		label_Operand2Errro2.setText("");
		operand2ErrPart1.setText("");
		operand2ErrPart2.setText("");
		label_errOperand2ETerm.setText("");
		
			errorMessageOP2(); // calling the method to check the errors
			
			// check to see if the operand 1 and operand 2 value is ok
			if (text_Operand2.getText().length()!=0 
					&&TextErrorTerm2.getText().length()!=0) 
				{
					if (perform.setOperand2(text_Operand2.getText(),TextErrorTerm2.getText())) {	//Once both the attributes required for a CalculatorValue object are present
						
						//clear all the error messages
						label_Operand2Error.setText(""); 
						label_Operand2Errro2.setText("");
						
						if (text_Operand1.getText().length() == 0)	  // if operand 1 equals to 0
								label_Operand1Error.setText("");   // then clear the error messages label
							}
					}
			}

	/**********
	 * This method is called when an binary operation button has been pressed. It assesses if there are issues 
	 * with either of the binary operands or they are not defined. If not return false (there are no issues)
	 * 
	 * @return	True if there are any issues that should keep the calculator from doing its work.
	 */
	private boolean binaryOperandIssues() {
	String errorMessage1 = perform.getOperand1ErrorMessage();	// Fetch the error messages, if there are any
	String errorMessage2 = perform.getOperand2ErrorMessage();
	if (errorMessage1.length() > 0) {	// Check the first.  If the string is not empty
	label_Operand1Errro2.setText(errorMessage1);	// there's an error message, so display it.
	if (errorMessage2.length() > 0) {	// Check the second and display it if there is
	label_Operand2Errro2.setText(errorMessage2);	// and error with the second as well.
	return true;	// Return true when both operands have errors
	}
	else {
	return true;	// Return true when only the first has an error
	}
	}
	else if (errorMessage2.length() > 0) {	// No error with the first, so check the second
	label_Operand2Errro2.setText(errorMessage2);	// operand. If non-empty string, display the error
	return true;	// message and return true... the second has an error
	}	// Signal there are issues

	// If the code reaches here, neither the first nor the second has an error condition. The following code
	// check to see if the operands are defined.
	if (!perform.getOperand1Defined()) {	// Check to see if the first operand is defined
	label_Operand1Errro2.setText("No value found");	// If not, this is an issue for a binary operator
	if (!perform.getOperand2Defined()) {	// Now check the second operand. It is is also
	label_Operand2Errro2.setText("No value found");	// not defined, then two messages should be displayed
	return true;	// Signal there are issues
	}
	return true;
	} else if (!perform.getOperand2Defined()) {	// If the first is defined, check the second. Both
	label_Operand2Errro2.setText("No value found");	// operands must be defined for a binary operator.
	return true;	// Signal there are issues
	}

	return false;	// Signal there are no issues with the operands
	}

	
	/****
	 * This method is call to save the computed result into the new repository.
	 * This mehtod is basically call by the button "save computed result" to store all the computed
	 * result into the respository folder
	 */

	public static void saveComputations() {
	
		 Stage stage = new Stage(); // declaring the stage 
		 Pane pane = new Pane(); // defining the pane
		 
		 // setting up the label for guidelines for user
		 // and defininf its properties
		 Label label = new Label("Enter file name: ");
		 label.setLayoutX(80); label.setLayoutY(100);
		 
		 // setting up the attributes for the textfield present in the popup
		 DefinationsUserInterface.TextFile.setLayoutX(80);
		 DefinationsUserInterface.TextFile.setLayoutY(125);
		  DefinationsUserInterface.TextFile.setMinWidth(250);
		 
		  // creating he button for sving the file
		         Button btnLogin = new Button();    
		         btnLogin.setLayoutX(350);
		         btnLogin.setLayoutY(125);
		         btnLogin.setText("Save File as");
		         
		       //event handling of the button for its specific functioning
		         btnLogin.setOnAction((event) ->{	 UserInterface.save();
		         				stage.hide();  	});
		         
		         // adding the relevant elements to the pane 
		         pane.getChildren().addAll(label,btnLogin, DefinationsUserInterface.TextFile);
		      
		          Scene scene = new Scene(pane, 500, 300); // setting up the attribute of the scene
		          stage.setScene(scene);
		          stage.show();

	}
	
	
	/********
	 * This method is call by the button "Add Definations" to open a pane in whichuser can decide whether
	 * he/she wants to createa file or just want to load the file
	 */
	
	public void windowPupup()
	{
		 Stage stage = new Stage(); // setting up the stage
		 Pane pane = new Pane(); // defining the object for the pane
		 stage.setTitle("Load File");
		 
		 // defining the attributes for the label for popup
		 Label label = new Label("Enter file name: ");
		 label.setLayoutX(80);label.setLayoutY(100);
		 
		 // defining the lebel for the textfield in popup
		 textUser.setLayoutX(80); textUser.setLayoutY(125);textUser.setMinWidth(250);
		   
		 // event handling for the textfield and link wiht method to accomplish the work
		 textUser.textProperty().addListener((observable, oldValue, newValue) -> { checkFileValidation(); });
		 
		 // Setting up some attributes for the load button
		         btnLogin.setLayoutX(350); btnLogin.setLayoutY(125);
		         btnLogin.setText("Load File's Data");
		         btnLogin.setDisable(true);
		 		
		         // label to dsiplay some specifed messgae
		         Label message = new Label("OR");
		         
		         // defining the attributes for the messsage lable
		         message.setLayoutX(200);  message.setLayoutY(200);
		         final double MAX_FONT_SIZE = 20; 
		         message.setFont(new Font(MAX_FONT_SIZE));
		         message.setTextFill(Color.web("Black"));
		         
		         //setting the attributes of butto for creating new file
		         Button create = new Button("Create New File");
		         create.setLayoutX(165);create.setLayoutY(250);
		         
		         //event handling for the create new file button 
		         create.setOnAction((event) ->{ 		
		        	 try {
		        		openSecondDefinitionWindow(stage);   //calling the method
						stage.hide();
					} catch (IOException e) {e.printStackTrace(); }});
		         
		         //event handling for the load button and link wiht mehtod to accomplish the work
		         btnLogin.setOnAction((event) ->{
		        	 try {
		        		openDefinitionWindow(stage); // calling the method
						stage.hide();
					} catch (IOException e) {e.printStackTrace();}}); 
		 
		         
		         // add all the necessary elements in the pane 
		         pane.getChildren().addAll(label, textUser,btnLogin, FileFoud, FIleNotFound, create, message);
		 
		 		 Scene scene = new Scene(pane, 500, 300); // defing the height and width of the scene
 		         stage.setScene(scene); 
		         stage.show();
		 
		     }
	
	
	/**********
	 * This routine checks, after each character is typed, to see if a file of that name is found, it checks to see if the contents conforms
	 * to the specification.
	 * 		If it does, the Load button is enabled and a green message is displayed
	 * 		If it does not, the Load button is disabled and a red error message is displayed
	 *      If a file is not found, a warning message is displayed and the button is disabled.
	 *      If the input is empty, all the related messages are removed and the Load button is disabled.
	 */

	public void checkFileValidation(){
	
		
		str_FileName = "RepositoryFolder\\"+textUser.getText().trim();			// Whenever the text area for the file name is changed
		if (str_FileName.length()<=0){					// this routine is called to see if it is a valid filename.
			//FileFoud.setDisable(false);
			FileFoud.setText("");				// Reset the messages 
			FIleNotFound.setText("");           // to empty
			scanner_Input = null;
		} else 											// If there is something in the file name text area
			try {										// this routine tries to open it and establish a scanner.
				scanner_Input = new Scanner(new File(str_FileName));

				// There is a readable file there... this code checks the data to see if it is valid 
				// for this application (Basic user input errors are GUI issues, not analysis issues.)
				if (showDictionary()) {
					
					FileFoud.setText("File found and the contents are valid!");
					message_ErrorDetails.setText("");
					FIleNotFound.setText("");
					
					btnLogin.setDisable(false);
				}
				// If the methods returns false, it means there is a problem with input file
				else {	// and the method has set up a String to explain what the issue is
					FileFoud.setText("");
					FIleNotFound.setText("File found, but the contents are not valid!");
					message_ErrorDetails.setText(errorMessage_FileContents);
					btnLogin.setDisable(true);
				}
			} catch (FileNotFoundException e) {			// If an exception is thrown, the file name
				FileFoud.setText("");			// that the button to run the analysis is
				FIleNotFound.setText("File not found!");	// not enabled.
				message_ErrorDetails.setText("");
				scanner_Input = null;	
				//theDictionary = null;
				btnLogin.setDisable(true);
			}
	}
	
	/**********
	 * This method reads in the contents of the data file and discards it as quickly as it reads it
	 * in order to verify that the data meets the input data specifications@sid and helps reduce the 
	 * change that invalid input data can lead to some kind of hacking.
	 * 
	 * @return	true - 	when the input file *is* valid
	 * when the input file data is *not* valid - The method also sets a string with
	 * details about what is wrong with the input data so the user can fix it
	 */
		
	public boolean showDictionary (){
		
		// Declare and initialize data variables used to control the method
		int numberOfLinesInTheInputFile = 0; // This attribute sets the number of lines set during the first read
		String firstLine = "";
		
		// Read in the first line and verify that it has the proper header
		if (scanner_Input.hasNextLine()) {
			
			firstLine = scanner_Input.nextLine().trim();		// Fetch the first line from the file
//			if (firstLine.equalsIgnoreCase(""))	// See if it is what is expected
//				numberOfLinesInTheInputFile = 1;				// If so, count it as one line
//			else {												// If not, issue an error message
//				return false;									// and return false
//			}
		} else {
		// If the execution comes here, there was no first line in the file
			return false;
		}
		
		// Process each and every subsequent line in the input to make sure that none are too long
		while (scanner_Input.hasNextLine()) {
			numberOfLinesInTheInputFile++;						// Count the number of input lines
			
			// Read in the line 
			String inputLine = scanner_Input.nextLine();
			
			// Verify that the input line is not larger than 250 characters...
			if (inputLine.length() > 250) {
				// If it is larger than 250 characters, display an error message on the console
				System.out.println("\n***Error*** Line " + numberOfLinesInTheInputFile + " contains " + 
						inputLine.length() + " characters, which is greater than the limit of 250.");
				
				// Stop reading the input and tell the user this data file has a problem
				return false;
			}
		}
			// Should the execution reach here, the input file appears to be valid
		errorMessage_FileContents = "";							// Clear any messages
		return true;											// End of file - data is valid
	}
	
	
	/*
	 * This mehtod is call when the user clcik on the load button to open the
	 * loaded content in the table
	 * 	
	 */
	public void openDefinitionWindow(Stage theStage) throws IOException {
		try {
			DefinationsUserInterface.setupwindow(theStage); //calling the method 
		} catch (NullPointerException e) 
		{e.printStackTrace();}}
	
	
	/*
	 * This mehtod is call when the user clcik on the crete new file button to open the
	 * blank table to enter new enteries 	
	 */
	
	public void openSecondDefinitionWindow(Stage theStage) throws IOException {
		try {
			DefinationsUserInterface.DefinationsUserInterface(theStage); // calling the  method
		} catch (NullPointerException e) 
		{ e.printStackTrace();}}
	
	/*******************************************************************************************************
	 * This portion of the class defines the actions that take place when the various calculator
	 * buttons (add, subtract, multiply, divide and square root) are pressed.
	 */

	/**********
	 * This is the add routine for both the operands and their error terms.
	 */

	private void addOperands(){
		
		setDefaultValues(); // setting the default values of all the texfileds
		
			addIsPossible = true; // indication for the addittion possibility
			
			// check if the normalization is required, if yes perfrom the conversion of the units
			if (cv.checkForNormalise(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem())) 
				{
				  if (!cv.conversionIsPossible(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem()))  {
					  	cv.warningMessage(1);  // dispaly the warning messgae for addition 
					  		return;
					  	}
				  			else {
				  						cv.performNormalization(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem());
				  					}
				  		}
					// if the value of the text operand 1 and text operand 2 is 0
						if (text_Operand1.getText().equals("0")
								 &&text_Operand2.getText().equals("0")) 
										{		
											text_Result.setText("0"); // setthe result txfiled value as 0
												addErrorTerms(); // call the method for the computation of the error term
										getTheResultantUnit(); // fecth the resultatn units
										return;
							}
				{
						// Check to see if both operands are defined and valid
						if (binaryOperandIssues()) 	
								return;	
							
						String theAnswer = perform.addition();	// perfrom the adition computation
							label_ResultError.setText("");	// updae the label error messgae for the result
							
							if (theAnswer.length() > 0) {	// Check the returned String to see if it is okay
									text_Result.setText(theAnswer);	// displaythe result into its field
								
									label_Result.setText("Sum");	// update the title of the label
									}
										else {	
												text_Result.setText("");	// make the result filed as empty
												label_Result.setText("Result");	// update the value of the reult labe
												label_ResultError.setText(perform.getResultErrorMessage());	// Display the error message.
												}
											addErrorTerms();  //call the method for the eror term computation
											getTheResultantUnit(); // fetch its resultant units
										}
						}
	
	/*
	 * This method is call to save the computed result into the new repository.
	 * This mehtod is basically call by the button "save computed result" to store all the computed
	 * result into the respository folder
	 */
	
	public static void save()
	{
		
		try{
			  // Create file 
			  FileWriter fstream = new FileWriter("Computations\\"+DefinationsUserInterface.TextFile.getText()+".txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  
			 // write the computed content in the file
			  out.write("Addition: -\n"+"MeasuredValue: " +perform.addition() +" ErrorTerm: "+ perform.addition1()
					  			+ " Units: " + ComboboxOperand3.getValue()+"\n\n");

			  out.write("Subtractions: -\n"+"MeasuredValue: " +perform.subtraction() +" ErrorTerm: "+ perform.subtractionE()
					  			+ " Units: " + ComboboxOperand3.getValue()+"\n\n");

			  out.write("Multiplication: -\n"+"MeasuredValue: " +perform.multiplication() +" ErrorTerm: "+ perform.multiplicationE()
					  			+ " Units: " + ComboboxOperand3.getValue()+"\n\n");
			  
			  out.write("Division: -\n"+"MeasuredValue: " +perform.division() +" ErrorTerm: "+ perform.divisionE()
	  			+ " Units: " + ComboboxOperand3.getValue()+"\n\n");
			  
			  out.write("Squareroot: -\n"+"MeasuredValue: " +perform.squareroot() +" ErrorTerm: "+ perform.squarerootE()
	  			+ " Units: " + "sqrt"+ ComboboxOperand3.getValue());
			  
			  out.close(); // close the writing
			  }catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
			  }
	}
	
	
	/**********
	 * THis method is used for the computation for error terms of the 
	 * respective operands and display the valid result into the resul error term field
	 * 
	 */
	private void addErrorTerms() {
			String theAnswer = perform.addition1(); // computes the additon for the error term too
			
				if (theAnswer.length()>0) { // check if the result error term field is ok
						TextResultErrorTerm.setText(theAnswer);   // display the result into the respective field
						}
							else {
									TextResultErrorTerm.setText(""); // display the eror term as empty
									}
				
				// if the value ofthe both the error terms is empty
						if(TextErrorTerm1.getText().equals("")
								 && TextErrorTerm2.getText().equals("")) 
								{ 
										TextResultErrorTerm.setText("0.5");   // result setsd to be empty then
									}
					}

	/**********
	 * This method is called when the subtaction button is pressed.THis mehtod computes on the
	 * both operadns as well as their error terms too for the precise result on the basis of the uNumber library
	 * 
	 */
	private void subOperands(){
		
		setDefaultValues(); // setting the default value into the respective textfileds
		
		// checking for the normalization, if it required then perfom the conversion else do the normalization
		if (cv.checkForNormalise(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem())) 
			{
					if (!cv.conversionIsPossible(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem()))  
					{
						cv.warningMessage(0);
							return; 
						}
							else {
								cv.performNormalization(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem());
							}
						} 
					subIsPossible=true; // indication for the possiblity for subtraction computation
						{
							// setting the default values as 0 in the respective field of the operands
							if (text_Operand1.getText().equals("0") && 
									text_Operand2.getText().equals("0")) 
							{	
								text_Result.setText("0");
								}
									else
										if (binaryOperandIssues()) // check to see operands are valid
												return;
							
							String perfromSubtraction = perform.subtraction(); // perform the computatipn for the operands only
							label_ResultError.setText(""); // empty the error message label
								
							if (perfromSubtraction.length()>0) { // checking the length of the resultant value
								
								label_Result.setText("Difference");	 // update the values of the label for the result
								text_Result.setText(perfromSubtraction);	// display the reuslt into the result textfield
								}
									else {
										text_Result.setText(""); // result sets to be empty
										label_Result.setText("Result"); // label sets to be updated
										label_ResultError.setText(perform.getResultErrorMessage());
									}
										subErrorTerms(); // call the method for the error terms computatipon
										getTheResultantUnit(); // fetch the result unit
								}
						}

	/**********
	 * THis method is used for the computation for error terms of the 
	 * respective operands and display the valid result into the resul error term field
	 * 
	 */
	private void subErrorTerms() {
		
		String theAnswer = perform.subtractionE(); // perfrom the computation for the error term
			
			if (theAnswer.length()>0)  // check if the result is greater than 0
				{
					TextResultErrorTerm.setText(theAnswer); //Display the result into the respective field
				}
					else {
							TextResultErrorTerm.setText(""); // make the result field empty
							}
				
				if(TextErrorTerm1.getText().equals("") &&
						TextErrorTerm2.getText().equals(""))
						{ 
								TextResultErrorTerm.setText("0.5");
							}
				}

	/**********
	 * This method is called when the multiplication button is pressed.THis mehtod computes on the
	 * both operadns as well as their error terms too for the precise result on the basis of the uNumber library
	 * 
	 */
	private void mpyOperands(){
		
			setDefaultValues(); //putting the default values to the reective fields
			
			mulitplyIsPossible=true; // indication that multiplication is possible
			
			// if matching dimensions are ok, the perform the normalization over the operands unit
			
			if(cv.MatchDimensions(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem())) 
				{
				cv.performNormalization(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem());
					} 
				{
					if (binaryOperandIssues())return;
					
					String performProduct = perform.multiplication(); // Perform the mpy computation
					
					label_ResultError.setText(""); // make error message for ressult as empty
					
					if (performProduct.length()>0) {
							label_Result.setText("Product"); //update the message for the result
							
							text_Result.setText(performProduct);  //Display the result into the result field
						}
							else {
									label_Result.setText("");	 // else label for the result is empty
									text_Result.setText("Result"); // text field for the result sets to be empty
									label_ResultError.setText(perform.getResultErrorMessage());
								}
									mpyErrorTerms(); // caling method for the error terms
									getTheResultantUnit();
									}
					}
			
	/**********
	 * This method is called for the multiplication operation on the error
	 * terms of both the operands 
	 */
	
	private void mpyErrorTerms() {
		
		String theAnswer = perform.multiplicationE(); //perfor the computaion on the error terms
		
				if (theAnswer.length()>0) { // if there is value in the result
						TextResultErrorTerm.setText(theAnswer); //Display reuslt in the result's error term textfield
					}
						else {
							TextResultErrorTerm.setText(""); // else display the result for error term as empty
						}
				}

	/**********
	 * This method is called when the division button is pressed.THis mehtod computes on the
	 * both operadns as well as their error terms too for the precise result on the basis of the uNumber library
	 * If the divisor is zero, the divisor is declared to be invalid.
	 * 
	 */

	private void divOperands(){
		
			//Check if both the operands are valid	
				setDefaultValues();// setting their default values
				
				divisionIsPossible=true; // indication for the possiblitiy of the divison
				
				// check if the value of the second operands and its error terms is equals to 0
				if (Double.parseDouble(text_Operand2.getText())==0 || 
						Double.parseDouble(TextErrorTerm2.getText())==0) 
					{
					// fill the tect field with the values
						text_Result.setText("Can' divide by 0"); 
						TextResultErrorTerm.setText("Can't divide by 0");
					
						return;
					}
				
				if(cv.MatchDimensions(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem())) 
						{
					
							cv.performNormalization(ComboboxOperand1.getSelectionModel().getSelectedItem(), ComboboxOperand2.getSelectionModel().getSelectedItem());
							} 
					{
							String perfromDivision = perform.division();   // caaling method for the computation fo division
							String theAnswer = perform.divisionE();  // perform the operation for thr error term too
							
							label_ResultError.setText(""); 
							
							if (perfromDivision.length()>0 && theAnswer.length()>0) 
								{
									label_Result.setText("Quotient");      // update the vaue of the label result
									text_Result.setText(perfromDivision);    // displayt the result on result textfield
									
									TextResultErrorTerm.setText(theAnswer);  // display the result in the error term textfield
								}
									else {
										label_Result.setText("Quotient");	// set the updated value to the label
										text_Result.setText("");	 // result sets t be empty
										TextResultErrorTerm.setText(""); // error term sets to be empty
										}
								getTheResultantUnit();
								}
					}

	/***
	 * This is the routine of Square Root feature for both the operand and the error terms.
	 * If the operand or error term is negative then it will be declared as invalid.
	 */

	private void sqrtOperand() {
		
		// putting the default values into the text field
			setDefaultValues();
			squarerootIsPossible=true; //boolean value which indicates the possiblitiy of the square root
			
			// clear the unit of the second operands for this computation
				ComboboxOperand2.getSelectionModel().clearSelection();
					{
						//clear the fields of the text operands
						text_Operand2.clear(); 
						TextErrorTerm2.clear();
					}
					
					// check if the value of the firt operands and its error terms is less than 0
					if (Double.parseDouble(text_Operand1.getText())<0 || 
							Double.parseDouble(TextErrorTerm1.getText())<0) 
					{
							// set the value of their text as "Invalid input"
							text_Result.setText("Invalid Input");
							TextResultErrorTerm.setText("Invalid Input");
					}
						else 
							{
								if (text_Operand1.getText().equals("0"))  // if first operand have value 0
								{
									text_Result.setText("0"); // results set to be 0
									TextResultErrorTerm.setText("0"); // its error term sets to be 0
								}
									else {
									String performSqrt = perform.squareroot(); //Perform the computation for the square rrot
									text_Result.setText(performSqrt);	//display the result in the result textfield
									label_Result.setText("Square Root"); // update the previous label name
									
									String theAnswer=perform.squarerootE();  //Perform the computation for the error term too
									TextResultErrorTerm.setText(theAnswer);//Display the result in the error term textfield
										}
								
								getTheResultantUnit();
							}
					}
	
	/********************
	 * 
	 * This method sets the default values for the operands 1 and ites error term textfield as well as
	 * operand 2 and its error term textfield along with their respected comboboxes
	 * 
	 ****/
	public static void setDefaultValues() {
		
		// setting the default values for the operand 1
			if (text_Operand1.getText().length()==0) 
				text_Operand1.setText("");

			// setting the default values for the operand 2
			if (text_Operand2.getText().length()==0) 
				text_Operand2.setText("");

			// setting the default values for the error term 1
			if (TextErrorTerm1.getText().length()==0)
				TextErrorTerm1.setText("0.5");

			// setting the default values for the error term 2
			if (TextErrorTerm2.getText().length()==0) 
				TextErrorTerm2.setText("0.5");

			// setting the default values for the combobxes
			if (ComboboxOperand2.getSelectionModel().isEmpty()) 
				ComboboxOperand2.getSelectionModel().select(ComboboxOperand1.getSelectionModel().getSelectedItem());
			}

	/*******
	 * This method provides the essenat error messages for the first operands and its error term
	 */

	private void errorMessageOP1() {
		
		// checking the error message for the operand 1 and if there is possiblity for error
		// then this helps in the show the error message for operand 1
			String errMessage = CalculatorValue.checkMeasureValue(text_Operand1.getText());
				if (errMessage != "") {
					label_Operand1Error.setText(CalculatorValue.measuredValueErrorMessage);
				if (CalculatorValue.measuredValueIndexofError<=-1) return;
					String input = CalculatorValue.measuredValueInput;
					
				operand1ErrPart1.setText(input.substring(0,CalculatorValue.measuredValueIndexofError));
				operand1ErrPart2.setText("\u21EB");
			}

		// checking the error message for the error term 1 and if there is possiblity for error
		// then this helps in the show the error message for error term 1
				String	eErrorMessage = CalculatorValue.checkErrorTerm(TextErrorTerm1.getText());
					if (eErrorMessage!="") {
						label_errOperand1ETerm.setText(CalculatorValue.errorTermErrorMessage);
					if (CalculatorValue.errorTermIndexofError<=-1) return;
						String input = CalculatorValue.errorTermInput;
						
					Operand1ErrorTermErrPart1.setText(input.substring(0,CalculatorValue.errorTermIndexofError));
					Operand1ErrorTermErrPart2.setText("\u21EB");
				}
	}
	/*******
	 * This method provides the essential FSM error message for the second operands and its error term.
	 * 
	 */
	private void errorMessageOP2() {
		
		// checking the error message for the operand 2 and if there is possiblity for error
		// then this helps in the show the error message for operand 2
			String errMessage = CalculatorValue.checkMeasureValue(text_Operand2.getText());
				if (errMessage != "") {
						label_Operand2Error.setText(CalculatorValue.measuredValueErrorMessage);
					if (CalculatorValue.measuredValueIndexofError<=-1) return;
				String input = CalculatorValue.measuredValueInput;
				
				operand2ErrPart1.setText(input.substring(0,CalculatorValue.measuredValueIndexofError));
				operand2ErrPart2.setText("\u21EB");
				}
		
		// checking the error message for the error term 2 and if there is possiblity for error
		// then this helps in the show the error message for error term 2	
			String eErrMessage = CalculatorValue.checkErrorTerm(TextErrorTerm2.getText());
				if (eErrMessage!="") {
					label_errOperand2ETerm.setText(CalculatorValue.errorTermErrorMessage);
						if (CalculatorValue.errorTermIndexofError<=-1) return;
							String input = CalculatorValue.errorTermInput;
					
				Operand2ErrorTermErrPart1.setText(input.substring(0,CalculatorValue.errorTermIndexofError));
				Operand2ErrorTermErrPart2.setText("\u21EB");
				}
	}
	
	/*********
	 * This method is used to get the resultant unit as accoridng to the user selection
	 * based on the butttons.
	 */
	public void getTheResultantUnit() {
		
			String Operand1Unit = ComboboxOperand1.getSelectionModel().getSelectedItem(); // fetch the first operands unit
			String Operand2Unit = ComboboxOperand2.getSelectionModel().getSelectedItem(); // fetch the second operands unit
			
			String resultantUnit =null; //empty the resultant unit equals null
			
			// In case of addition, the units must be same (after normalization), hence the resultant will have same units
	
			if (addIsPossible || subIsPossible)  // if addition and subtraction is possble
			{
				resultantUnit = Operand2Unit; // then resultant will be some
			}
			
			// If the operation selected is multiplication
			 else if (mulitplyIsPossible) {  // if mpy operator button is selected
				 if (Operand1Unit.equals(Operand2Unit) ) {  	//both selected units are same
					 // If both the operands have no units then the resultant will also not have any unit
					 
			if (Operand1Unit.equals("NO_UNIT")) resultantUnit = "NO_UNIT";  // if there is no unit in operands 1, the result will be "No Unit"
	
			//if both operand will have same unit then resultant will be multiplied
				else resultantUnit = Operand2Unit+"\u00B2"; // \u00B2 means superscripted 2
			}
			// Resultant unit will get squared
				 else if (cv.MatchDimensions(Operand1Unit,Operand2Unit)) resultantUnit = Operand2Unit+"\u00B2";
				 
				 // If only one operand has unit then resultant unit will be same as that unit 
				 else if (Operand1Unit.equals("NO_UNIT") || Operand2Unit.equals("NO_UNIT")) 
				 {
					 if (Operand1Unit.equals("NO_UNIT")) resultantUnit = Operand2Unit;
					 else resultantUnit = Operand1Unit;
				 }
				 
				 // In case both the units are different, place a 'x' symbol between both of them
				 	else resultantUnit = Operand1Unit+"\u00D7"+Operand2Unit;
			 }
				// If the operation selected is division
				else if (divisionIsPossible) {
						// If both the units are same, then the resultant will have no unit
						if (Operand1Unit.equals(Operand2Unit) ) {
								resultantUnit = "NO_UNIT";
								}
						
						// If only one operand has unit then the resultant will vary accordingly.
						else if (Operand1Unit.equals("NO_UNIT") || Operand2Unit.equals("NO_UNIT")) {
							
					// If numerator has no unit then the resultant will have unit equal to inverse of unit of second operand
						if (Operand1Unit.equals("NO_UNIT")) resultantUnit = Operand2Unit+"\u207B\u2081";  // \u207B\u2081 means superscripted -1 
				
						else resultantUnit = Operand1Unit; // else the resultant unit will be the same as unit of numerator
					}
				// If both the numerator and denominator have unique units, then place a '÷' symbol between them 
				else {
					resultantUnit = Operand1Unit+"\u00F7"+Operand2Unit;
					
					//resultantUnit = Operand1Unit+"/"+Operand2Unit; // Uncomment this line to place a '/' symbol instead of '÷' symbol.
					}
			}
			// If the selected operation is square-root
				else if (squarerootIsPossible) {
					// Place a '√' symbol before the unit of the operand
						resultantUnit = "\u221A"+Operand1Unit;
							}
					// Clear the combobox
				ComboboxOperand3.getItems().clear();
				
				// If the resultant unit belongs to length dimension, the it will be converted into all of its type and
				// add the length units into the list
					
				if (LengthUnits.indexOf(resultantUnit)!=-1) {
					ComboboxOperand3.getItems().addAll(LengthUnits);
					}
				
				// If the resultant unit belongs to time dimension, the it will be converted into all of its type and
				// add the time units into the list
				
				else  if (TimeUnits.indexOf(resultantUnit)!=-1) {
					ComboboxOperand3.getItems().addAll(TimeUnits);
					}
				
				// If the resultant unit belongs to length dimension, the it will be converted into all of its type and
				// add the length units into the list
				
				else  if (VelocityUnits.indexOf(resultantUnit)!=-1) {
					ComboboxOperand3.getItems().addAll(VelocityUnits);
					}
				
				// If the resultant unit is unique, then it can't be converted.
				else {
						ComboboxOperand3.getItems().add(resultantUnit);	// Hence add the unit to the combobox
						ComboboxOperand3.setOpacity(0.8); // Set opacity to 0.8 so that the unit will still be visible easily
					}
					// Select the resultant unit from the combobox
					ComboboxOperand3.getSelectionModel().select(resultantUnit);
						// Store this unit so that further conversion could be done
						ResultantUnit = resultantUnit;
				}
	
	/************
	 * This method will set the value of operands as combination of their measured values and error terms after 
	 * normalization so that they could be used as attributes to a single object in CalculatorValue Constructor for further calculations.
	 */
	
	private void setOperandsDueToChangedUnits() throws NullPointerException{
		//creating an array which calls method to perfrom the normalization
		double[] thePair = cv.performNormalization(ComboboxOperand1.getSelectionModel().getSelectedItem(),ComboboxOperand2.getSelectionModel().getSelectedItem());
		
		double newOperand = thePair[0];
		double newErrorTerm = thePair[1];
		
		if (text_Operand1.getText().length()!=0 &&  // check the value of the text Operand
			TextErrorTerm1.getText().length()!=0) // check the value of its error term
			{ 
				if (perform.setOperand1(String.valueOf(newOperand),String.valueOf(newErrorTerm))) {	//then call the setOperand 1 method from calculator value
			
			label_Operand1Error.setText(""); //all the error messages
			label_Operand1Errro2.setText("");
			operand1ErrPart1.setText("");
			operand1ErrPart2.setText("");
			
			if (text_Operand2.getText().length() == 0)	
					label_Operand2Error.setText("");}
			}
	}	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
}