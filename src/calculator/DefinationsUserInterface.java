package calculator;


import java.io.IOException;
import java.util.Comparator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;



public class DefinationsUserInterface {
	/**
	 * <p>
	 * Title: DefinationsUserinterface - A component of the Programmable Calculator Project
	 * </p>
	 *
	 * <p>
	 * Description: A controller object class that implements an editable TableView UI
	 * </p>
	 *
	 * <p>
	 * Copyright: Copyright © 2019
	 * </p>
	 *
	 * @author Lynn Robert Carter
	 * @version 1.00	Baseline version 2019-01-26
	 * 
	 * @author Sanchit
	 * @version 2.00    Improved data export and import in tableview
	 * @verson 	2.01	Enhanced version to support all the functionality to manage definations 
	 * 
	 */

	/**********************************************************************************************
	 * 
	 * The DefinitionDemo Class provides an experimental platform upon which the user interface
	 * for the a pop-up window implementation of the Variable / Constant Definition Table can be
	 * developed and experiments can be run without dealing with the rest of the Calculator.
	 * 
	 * The following are the primary followed by the secondary attributes for the DefinitionDemo
	 * Class
	 */

	// A Label used to guide the user
	private static Label lbl_EditingGuidance = new Label("Editing a Table Cell!  When finished, press <enter> or <return> to commit the change."); 

	 // A flag to signal when to ignore case
	private static boolean whenSorting = false;			       
	
	// The list of values being defined
	public static ObservableList<Quantity> tableData =	FXCollections.observableArrayList();                                                       

	//Defining the attributes for there further use
	TextField VNameTF = new TextField();
	TextField Vtypetf = new TextField();
	TextField Unitstf = new TextField();
	TextField Valuestf = new TextField();
	TextField ErrorTermtf = new TextField();
	 public static TextField TextFile = new TextField();
	 
	// public static Dictionary<String> d = new Dictionary<String>(); //declaring the object of Dictionary class
	 public static ManageDefinations perform; //declaring the object for manage definations class
	
	 /**********
		 * This inner class is used to define the various fields required by the variable/constant
		 * definitions.
		 * 
		 * @author lrcarter
		 *
		 */
	 
	public static class Quantity {	
		StringProperty nameValue = new SimpleStringProperty();     																			// The name of the value
		StringProperty isConstantValue = new SimpleStringProperty(); 																		// Specifies if this is a constant
		StringProperty measureValue = new SimpleStringProperty(); 																			// The measured value
		StringProperty errorTermValue = new SimpleStringProperty();																			// Error term, if there is one
		StringProperty unitsValue = new SimpleStringProperty();																				// Units, if there is one

		public Quantity() {
			this.nameValue = new SimpleStringProperty();
			this.isConstantValue = new SimpleStringProperty();
			this.measureValue = new SimpleStringProperty();
			this.errorTermValue = new SimpleStringProperty();
			this.unitsValue = new SimpleStringProperty();
		}


		/*****
		 * This fully-specified constructor establishes all of the fields of a Quantity object
		 * 
		 * @param n - A String that specifies the name of the constant or variable
		 * @param c - A String that serves as a T or F flag as to where or not this is a constant
		 * @param m - A String that specifies the measured value / value, if there is one
		 * @param e - A String that specifies the error term, if there is one
		 * @param u - A String that specifies the units definition, if there is one
		 */

		public Quantity(String n, String c, String m, String e, String u) {
			this.nameValue = new SimpleStringProperty(n);
			this.isConstantValue = new SimpleStringProperty(c);
			this.measureValue = new SimpleStringProperty(m);
			this.errorTermValue = new SimpleStringProperty(e);
			this.unitsValue = new SimpleStringProperty(u);
		}

		public final StringProperty nameProperty() {
			return this.nameValue;
		}

		public final StringProperty isconstantProperty() {
			return this.isConstantValue;
		}

		public final StringProperty measureValueProperty() {
			return this.measureValue;
		}

		public final StringProperty errorTermValueProperty() {
			return this.errorTermValue;
		}

		public final StringProperty unitsTableProperty() {
			return this.unitsValue;
		}

		/*****
		 * This getter gets the value of the variable / constant name field - If the whenSorting
		 * flag is true, this method return the String converted to lower case - otherwise, it 
		 * return the String as is
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 * 
		 * @return	String - of the name of the variable / constant
		 */
		public String getNameValue() {
			if (whenSorting)
				return nameValue.get().toLowerCase();
			else
				return nameValue.get();
		}

		/*****
		 * This Setter sets the value of the variable / constant name field
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 * 
		 * @param n = object for Name
		 */
		public void setNameValue(String n) {
			nameValue.set(n);
		}

		/*****
		 * This getter gets the value of the isConstant flag field - If this field is true, the
		 * item being defined is a constant and the calculator will not be allowed to alter the
		 * value (but the calculator's user may editing the value of this item).
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 * 
		 * @return	String - Either a "T" or an "F" String
		 */
		public String getIsConstantValue() {
			return isConstantValue.get();
		}

		/*****
		 * This Setter sets the value of the isConstant flag field - If the parameter c starts
		 * with a "T" or a "t", the field is set to "T", else it is set to "F". 
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 * 
		 * @param c	String - The first letter is used to determine if this is a "T" or "F"
		 */
		public void setIsConstantValue(String c) {
			if (c.startsWith("T") || c.startsWith("t") ||c.startsWith("C") || c.startsWith("c"))
				isConstantValue.set("T");
			else
				isConstantValue.set("F");
		}

		/*****
		 * This getter gets the value of the measureValue field.
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 * 
		 * @return	String - A String of the measuredValue specification is returned
		 */
		public String getMeasureValue() {
			return measureValue.get();
		}
		

		/*****
		 * This Setter sets the value of the measuredValue field 
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 *
		 * 
		 * @param m String - object for measured value
		 */
		public void setMeasureValue(String m) {
			measureValue.set(m);
		}

		/*****
		 * This getter gets the value of the errorTerm field.
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 * 
		 * @return	String - A String of the errorTerm specification is returned
		 */
		public String getErrorTermValue() {
			return errorTermValue.get();
		}

		/*****
		 * This Setter sets the value of the errorTerm field 
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 * 
		 * @param e	String - The value is assumed to be a value numeric string. It must be
		 * checked before this routine is used.
		 */
		public void setErrorTermValue(String e) {
			errorTermValue.set(e);
		}

		/*****
		 * This getter gets the value of the units field.
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 * 
		 * @return	String - A String of the units specification is returned
		 */
		public String getUnitsValue() {
			return unitsValue.get();
		}

		/*****
		 * This Setter sets the value of the unitsValue field 
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this code 
		 * generates method calls to these routines given the name of the field, it follows
		 * this naming pattern.
		 * 
		 * @param u	String - The value is assumed to be a value units string. It must be
		 * checked before this routine is used.
		 */
		public void setUnitsValue(String u) {
			unitsValue.set(u);
		}
		
		/***
		 * this the toString method to foramt the output as per the table's data
		 */
		public String toString() {
			return  getNameValue()+ "-" + getIsConstantValue() + "-" + getMeasureValue() + "-" + getErrorTermValue() + "-"+ getUnitsValue();
		}
		
		/***
		 * this the toString method to foramt the output as per the table's data
		 * @return  the values from the of the loaded table
		 */
		
		public String toString1() {
			return  getNameValue()+ "\n" + getIsConstantValue() + "\n" + getMeasureValue() + "\n" + getErrorTermValue() + "\n"+ getUnitsValue();
		}
		
		
	}
	/**********************************************************************************************

	Constructors

	 **********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the graphical user interface. These assignments
	 * determine the location, size, font, color, and change and event handlers for each GUI object.
	 * @throws IOException -  exception to handle IO errors
	 * @param primaryStage - used to show the table for definations
	 */
	public static void DefinationsUserInterface(Stage primaryStage) throws IOException {
		// Import and load data from txt file to table view
		
			ManageDefinations.readDefiantons();
				
				Stage dialog = new Stage(); //setting up the stage
				Pane thePane = new Pane(); // setting up the pane
				TableView<Quantity> table = new TableView<>();
				
			
				Scene dialogScene = new Scene(thePane, 790, 700); //setting the attributes of the scene
				dialog.setTitle("Definations");
				
				//setting the label for variable's Name
				// and define its attributes
				Label VNamelb = new Label("Variable Name");
				VNamelb.setLayoutX(20);
				VNamelb.setLayoutY(500);
				VNamelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for variable's Name
				// and define its attributes
				TextField VNameTF = new TextField();
				VNameTF.setPromptText("Varible Name");
				VNameTF.setMaxWidth(110);
				VNameTF.setLayoutX(20);
				VNameTF.setLayoutY(530);
				
				//setting the label for variable's type
				// and define its attributes
				Label ValueNamelb = new Label("Variable Type");
				ValueNamelb.setLayoutX(170);
				ValueNamelb.setLayoutY(500);
				ValueNamelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for variable's type
				// and define its attributes
				TextField Valuestf = new TextField();
				Valuestf.setPromptText("Variable Type");
				Valuestf.setMaxWidth(150);
				Valuestf.setLayoutX(150);
				Valuestf.setLayoutY(530);
				
				//setting the label for measured Value
				// and define its attributes
				Label ENamelb = new Label("Measured Value");
				ENamelb.setLayoutX(335);
				ENamelb.setLayoutY(500);
				ENamelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for measured's value
				// and define its attributes
				TextField ErrorTermtf = new TextField();
				ErrorTermtf.setPromptText("Measured Value");
				ErrorTermtf.setMaxWidth(150);
				ErrorTermtf.setLayoutX(330);
				ErrorTermtf.setLayoutY(530);
				
				//setting the label for Error term
				// and define its attributes
				Label UNamelb = new Label("Error Term");
				UNamelb.setLayoutX(510);
				UNamelb.setLayoutY(500);
				UNamelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for error term
				// and define its attributes
				TextField Unitstf = new TextField();
				Unitstf.setPromptText("Error Term");
				Unitstf.setMaxWidth(100);
				Unitstf.setLayoutX(510);
				Unitstf.setLayoutY(530);
				
				//setting the label for Units
				// and define its attributes
				Label Vtypelb = new Label("Units");
				Vtypelb.setLayoutX(650);
				Vtypelb.setLayoutY(500);
				Vtypelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for units
				// and define its attributes
				TextField Vtypetf = new TextField();
				Vtypetf.setPromptText("Units");
				Vtypetf.setMaxWidth(100);
				Vtypetf.setLayoutX(640);
				Vtypetf.setLayoutY(530);
				
//				//setting the label for variable's Name
//				// and define its attributes
//				TextField filetxt = new TextField();
//				filetxt.setPromptText("Units");
//				filetxt.setMaxWidth(100);
//				filetxt.setLayoutX(100);
//				filetxt.setLayoutY(150);
//				filetxt.setVisible(false);
//				
//				
				
				
				//creating the button for close the dialog box
				// and define its attributes
				Button btn_Close = new Button("Close");
				setupButton(btn_Close, 100, 200, 600); // and defining its attributes
				
			
				//event handling of the button for its working
				btn_Close.setOnAction((event) -> { dialog.hide(); });

				// Establish a button to add a new row into the TableView into the set of definitions
				Button btn_Add = new Button("Add new Defination");
				setupButton(btn_Add, 150, 350, 600);		
				Button btn_Delete = new Button("Delete Defination");

				//creating the button for creating new file
				// and define its attributes
				Button btn_addfile = new Button("Create File");
				setupButton(btn_addfile, 100, 500, 650);
				
				//event handling of the button to secify its working
				btn_addfile.setOnAction((event) -> {perform.addDefinations();
										primaryStage.hide();});

				//event handling of the button tospecify its working
				btn_Add.setOnAction((event) -> { 					
					tableData.add(new Quantity(VNameTF.getText(),Valuestf.getText(),ErrorTermtf.getText(),Unitstf.getText(), Vtypetf.getText()));
					VNameTF.setText(""); Valuestf.setText(""); ErrorTermtf.setText(""); Unitstf.setText(""); Vtypetf.setText("");
					

					
					// Create a new row after last row in the table
//					Quantity q = new Quantity("?","?","?","?", "?");
//					tableData.add(q);
//					btn_Delete.setDisable(false);
//					int row = tableData.size() - 1;
////
////					// Select the row that was just created
//					table.requestFocus();
//					table.getSelectionModel().select(row);
//					table.getFocusModel().focus(row);
				});

				// Establish a button to delete a row in the TableView into the set of definitions
				setupButton(btn_Delete, 150, 550, 600);		

				// If there is no data in the table, then disable the Delete Button else enable it
				if (tableData.size()<=0)
					btn_Delete.setDisable(true);
				else
					btn_Delete.setDisable(false);

				// This button handler deals with the various cases that arise when deleting a table row
				btn_Delete.setOnAction((event) -> {
					// Get selected row and delete
					int ix = table.getSelectionModel().getSelectedIndex();
					if (ix <= -1) return;
					tableData.remove(ix);
					if (table.getItems().size() == 0) {
						btn_Delete.setDisable(true);
						return;
					}
					if (ix != 0) {ix = ix -1;}
					table.requestFocus();
					table.getSelectionModel().select(ix);
					table.getFocusModel().focus(ix);
				});

				// Make the table editable and position it in the pop-up window
				table.setEditable(true);
				table.setLayoutX(20);
				table.setLayoutY(10);

				//**********//
				// Define each of the columns in the table view and set up the handlers to support editing

				// This is the column that support the Name column. When the name of a definition is changed
				// this code will cause the table of data to be re-sorted and rearranged so the rows will 
				// shown in the table as sorted.
				TableColumn<Quantity, String> col_NameValue = new TableColumn<Quantity, String>("Variable/Constant\nName");
				col_NameValue.setMinWidth(150);
				col_NameValue.setCellValueFactory(new PropertyValueFactory<>("nameValue"));
				col_NameValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing a Name column, a message is displayed giving guidance on how to
				// commit the change when done.
				col_NameValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_NameValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setNameValue(t.getNewValue());
							whenSorting = true;
							tableData.sort(Comparator.comparing(Quantity::getNameValue));
							whenSorting = false;
							lbl_EditingGuidance.setVisible(false);
							
						});

				//**********//
				// This is the column that supports the IsConstantValue field.  
				TableColumn<Quantity, String> col_IsConstantValue = new TableColumn<Quantity, String>("Is a\nConstant");
				col_IsConstantValue.setMinWidth(150);
				col_IsConstantValue.setCellValueFactory(new PropertyValueFactory<>("isConstantValue"));
				col_IsConstantValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing the IsConstantValue column, a message is displayed giving 
				// guidance on how to commit the change when done.
				col_IsConstantValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});	

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_IsConstantValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setIsConstantValue(t.getNewValue());
							lbl_EditingGuidance.setVisible(false);
						});

				//**********//
				// This is the column that supports the MeasureValue field.  
				TableColumn<Quantity, String> col_MeasureValue = new TableColumn<Quantity, String>("Measure or Value");
				col_MeasureValue.setMinWidth(150);
				col_MeasureValue.setCellValueFactory(new PropertyValueFactory<>("measureValue"));
				col_MeasureValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing the MeasureValue column, a message is displayed giving 
				// guidance on how to commit the change when done.
				col_MeasureValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});	

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_MeasureValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setMeasureValue(t.getNewValue());
							lbl_EditingGuidance.setVisible(false);
						});

				//**********//
				// This is the column that supports the ErrorTermValue field.  
				TableColumn<Quantity, String> col_ErrorValue = new TableColumn<Quantity, String>("Error Term");
				col_ErrorValue.setMinWidth(150);
				col_ErrorValue.setCellValueFactory(new PropertyValueFactory<>("errorTermValue"));
				col_ErrorValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing the ErrorTermValue column, a message is displayed giving 
				// guidance on how to commit the change when done.
				col_ErrorValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});			

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_ErrorValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setErrorTermValue(t.getNewValue());
							lbl_EditingGuidance.setVisible(false);
						});

				//**********//
				// This is the column that supports the UnitsValue field.  
				TableColumn<Quantity, String> col_UnitsValue = new TableColumn<Quantity, String>("Units");
				col_UnitsValue.setMinWidth(150);
				col_UnitsValue.setCellValueFactory(new PropertyValueFactory<>("unitsValue"));
				col_UnitsValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing the UnitsValue column, a message is displayed giving 
				// guidance on how to commit the change when done.
				col_UnitsValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});			

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_UnitsValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setUnitsValue(t.getNewValue());
							lbl_EditingGuidance.setVisible(false);
						});

				//**********//
				// The follow sets up the editing guidance text,. positions it below the table, sets the
				// text red, and hides the text so it is only shown during the edit process.
				lbl_EditingGuidance.setMinWidth(600);
				lbl_EditingGuidance.setLayoutX(20);
				lbl_EditingGuidance.setLayoutY(470);
				lbl_EditingGuidance.setTextFill(Color.RED);
				lbl_EditingGuidance.setVisible(false);

				// The right-most three columns are grouped into a single column as they define the value
				// elements of the definition.
				TableColumn<Quantity, String> col_ValueGroup = new TableColumn<Quantity, String>("Value");
				col_ValueGroup.getColumns().add(col_MeasureValue);
				col_ValueGroup.getColumns().add(col_ErrorValue);
				col_ValueGroup.getColumns().add(col_UnitsValue);

				// As we are setting up the GUI, we begin by sorting the data with which we start
				whenSorting = true;
				tableData.sort(Comparator.comparing(Quantity::getNameValue));
				whenSorting = false;

				// This loads the data from the ObservableList into the table, so the TableView code can
				// display it and provide all of the functions that it provides
				table.setItems(tableData);

				// This calls add the three major column titles into the table.  Notice that the right most
				// column is a composite of the three value fields (measure, error term, and units)
				table.getColumns().add(col_NameValue);
				table.getColumns().add(col_IsConstantValue);
				table.getColumns().add(col_ValueGroup);

				// With all of the GUI elements defined and initialized, we add them to the window pane
				thePane.getChildren().addAll(btn_Close, btn_Add, btn_Delete, table, lbl_EditingGuidance,btn_addfile,
						VNamelb,VNameTF,ValueNamelb, Valuestf, ENamelb, ErrorTermtf, UNamelb, Unitstf, Vtypelb,Vtypetf);
				// We set the scene into dialog for this window
				dialog.setScene(dialogScene);
				
				// We show the completed window to the user, making it possible for the user to start
				// clicking on the various GUI widgets in order to make things happen.
				dialog.show();
	}

	

	public static void setupwindow(Stage primaryStage) throws IOException {
		// Import and load data from txt file to table view
		
		ManageDefinations.loadDictionary();
		
				Stage dialog = new Stage();  //declaing the object for the stage
				Pane thePane = new Pane(); //declaring the object for the pane
				TableView<Quantity> table = new TableView<>();
				
				// defining up the attributes for scene
				Scene dialogScene = new Scene(thePane, 790, 700);
				dialog.setTitle("Definations");
			
				//setting the label for variable name
				// and define its attributes
				Label VNamelb = new Label("Variable Name");
				VNamelb.setLayoutX(20);
				VNamelb.setLayoutY(500);
				VNamelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for variable's name
				// and define its attributes
				TextField VNameTF = new TextField();
				VNameTF.setPromptText("Varible Name");
				VNameTF.setMaxWidth(110);
				VNameTF.setLayoutX(20);
				VNameTF.setLayoutY(530);
				
				//setting the label for constant
				// and define its attributes
				Label ValueNamelb = new Label("Is a Constant");
				ValueNamelb.setLayoutX(170);
				ValueNamelb.setLayoutY(500);
				ValueNamelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for variable type
				// and define its attributes
				TextField Valuestf = new TextField();
				Valuestf.setPromptText("Variable Type");
				Valuestf.setMaxWidth(150);
				Valuestf.setLayoutX(150);
				Valuestf.setLayoutY(530);

				//setting the label for measured value
				// and define its attributes
				Label ENamelb = new Label("Measured Value");
				ENamelb.setLayoutX(335);
				ENamelb.setLayoutY(500);
				ENamelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for measured value
				// and define its attributes
				TextField ErrorTermtf = new TextField();
				ErrorTermtf.setPromptText("Measured Value");
				ErrorTermtf.setMaxWidth(150);
				ErrorTermtf.setLayoutX(330);
				ErrorTermtf.setLayoutY(530);
				
				//setting the label for error terms
				// and define its attributes
				Label UNamelb = new Label("Error Term");
				UNamelb.setLayoutX(510);
				UNamelb.setLayoutY(500);
				UNamelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for error terms
				// and define its attributes
				TextField Unitstf = new TextField();
				Unitstf.setPromptText("Error Term");
				Unitstf.setMaxWidth(100);
				Unitstf.setLayoutX(510);
				Unitstf.setLayoutY(530);
				
				//setting the label for units
				// and define its attributes
				Label Vtypelb = new Label("Units");
				Vtypelb.setLayoutX(650);
				Vtypelb.setLayoutY(500);
				Vtypelb.setFont(Font.font("Arial", FontWeight.BOLD,15));
				
				//setting the textfield for units
				// and define its attributes
				TextField Vtypetf = new TextField();
				Vtypetf.setPromptText("Units");
				Vtypetf.setMaxWidth(100);
				Vtypetf.setLayoutX(640);
				Vtypetf.setLayoutY(530);
				
				
				Button btn_Close = new Button("Close"); //defining the button for its further use
				setupButton(btn_Close, 100, 200, 600); // and defining its attributes
				
				//event handling of the button for its working
				btn_Close.setOnAction((event) -> { dialog.hide(); });

				// Establish a button to add a new row into the TableView into the set of definitions
				Button btn_Add = new Button("Add new Defination");
				setupButton(btn_Add, 150, 350, 600);		
				Button btn_Delete = new Button("Delete Defination");

				Button btn_addfile = new Button("Save Data...");//defining the button for its further use
				setupButton(btn_addfile, 100, 500, 650); //and defining its attributes
				
				//event handling of the button for its working
				btn_addfile.setOnAction((event) -> { saveLoadedFile();	});

				//event handling of the button for its working
				btn_Add.setOnAction((event) -> { 
					
					tableData.add(new Quantity(VNameTF.getText(),Valuestf.getText(),ErrorTermtf.getText(),Unitstf.getText(), Vtypetf.getText()));
					VNameTF.setText(""); Valuestf.setText(""); ErrorTermtf.setText(""); Unitstf.setText(""); Vtypetf.setText("");
					
					btn_Delete.setDisable(false);
					int row = tableData.size() - 1;

					// Select the row that was just created
					table.requestFocus();
					table.getSelectionModel().select(row);
					table.getFocusModel().focus(row);
				});

				// Establish a button to delete a row in the TableView into the set of definitions
				setupButton(btn_Delete, 150, 550, 600);		

				// If there is no data in the table, then disable the Delete Button else enable it
				if (tableData.size()<=0)
					btn_Delete.setDisable(true);
				else
					btn_Delete.setDisable(false);

				// This button handler deals with the various cases that arise when deleting a table row
				btn_Delete.setOnAction((event) -> {
					// Get selected row and delete
					int ix = table.getSelectionModel().getSelectedIndex();
					if (ix <= -1) return;
					tableData.remove(ix);
					if (table.getItems().size() == 0) {
						btn_Delete.setDisable(true);
						return;
					}
					if (ix != 0) {ix = ix -1;}
					table.requestFocus();
					table.getSelectionModel().select(ix);
					table.getFocusModel().focus(ix);
				});

				// Make the table editable and position it in the pop-up window
				table.setEditable(true);
				table.setLayoutX(20);
				table.setLayoutY(10);

				//**********//
				// Define each of the columns in the table view and set up the handlers to support editing

				// This is the column that support the Name column. When the name of a definition is changed
				// this code will cause the table of data to be re-sorted and rearranged so the rows will 
				// shown in the table as sorted.
				TableColumn<Quantity, String> col_NameValue = new TableColumn<Quantity, String>("Variable/Constant\nName");
				col_NameValue.setMinWidth(150);
				col_NameValue.setCellValueFactory(new PropertyValueFactory<>("nameValue"));
				col_NameValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing a Name column, a message is displayed giving guidance on how to
				// commit the change when done.
				col_NameValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_NameValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setNameValue(t.getNewValue());
							whenSorting = true;
							tableData.sort(Comparator.comparing(Quantity::getNameValue));
							whenSorting = false;
							lbl_EditingGuidance.setVisible(false);
						});

				//**********//
				// This is the column that supports the IsConstantValue field.  
				TableColumn<Quantity, String> col_IsConstantValue = new TableColumn<Quantity, String>("Is a\nConstant");
				col_IsConstantValue.setMinWidth(150);
				col_IsConstantValue.setCellValueFactory(new PropertyValueFactory<>("isConstantValue"));
				col_IsConstantValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing the IsConstantValue column, a message is displayed giving 
				// guidance on how to commit the change when done.
				col_IsConstantValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});	

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_IsConstantValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setIsConstantValue(t.getNewValue());
							lbl_EditingGuidance.setVisible(false);
						});

				//**********//
				// This is the column that supports the MeasureValue field.  
				TableColumn<Quantity, String> col_MeasureValue = new TableColumn<Quantity, String>("Measure or Value");
				col_MeasureValue.setMinWidth(150);
				col_MeasureValue.setCellValueFactory(new PropertyValueFactory<>("measureValue"));
				col_MeasureValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing the MeasureValue column, a message is displayed giving 
				// guidance on how to commit the change when done.
				col_MeasureValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});	

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_MeasureValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setMeasureValue(t.getNewValue());
							lbl_EditingGuidance.setVisible(false);
						});

				//**********//
				// This is the column that supports the ErrorTermValue field.  
				TableColumn<Quantity, String> col_ErrorValue = new TableColumn<Quantity, String>("Error Term");
				col_ErrorValue.setMinWidth(150);
				col_ErrorValue.setCellValueFactory(new PropertyValueFactory<>("errorTermValue"));
				col_ErrorValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing the ErrorTermValue column, a message is displayed giving 
				// guidance on how to commit the change when done.
				col_ErrorValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});			

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_ErrorValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setErrorTermValue(t.getNewValue());
							lbl_EditingGuidance.setVisible(false);
						});

				//**********//
				// This is the column that supports the UnitsValue field.  
				TableColumn<Quantity, String> col_UnitsValue = new TableColumn<Quantity, String>("Units");
				col_UnitsValue.setMinWidth(150);
				col_UnitsValue.setCellValueFactory(new PropertyValueFactory<>("unitsValue"));
				col_UnitsValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

				// When one starts editing the UnitsValue column, a message is displayed giving 
				// guidance on how to commit the change when done.
				col_UnitsValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
					lbl_EditingGuidance.setVisible(true);
				});			

				// When the user commits the change, the editing guidance message is once again hidden and
				// the system sorts the data in the table so the data will always appear sorted in the table
				col_UnitsValue.setOnEditCommit(
						(CellEditEvent<Quantity, String> t) -> {
							((Quantity) t.getTableView().getItems().get(
									t.getTablePosition().getRow())
									).setUnitsValue(t.getNewValue());
							lbl_EditingGuidance.setVisible(false);
						});

				//**********//
				// The follow sets up the editing guidance text,. positions it below the table, sets the
				// text red, and hides the text so it is only shown during the edit process.
				lbl_EditingGuidance.setMinWidth(600);
				lbl_EditingGuidance.setLayoutX(20);
				lbl_EditingGuidance.setLayoutY(470);
				lbl_EditingGuidance.setTextFill(Color.RED);
				lbl_EditingGuidance.setVisible(false);

				// The right-most three columns are grouped into a single column as they define the value
				// elements of the definition.
				TableColumn<Quantity, String> col_ValueGroup = new TableColumn<Quantity, String>("Value");
				col_ValueGroup.getColumns().add(col_MeasureValue);
				col_ValueGroup.getColumns().add(col_ErrorValue);
				col_ValueGroup.getColumns().add(col_UnitsValue);

				// As we are setting up the GUI, we begin by sorting the data with which we start
				whenSorting = true;
				tableData.sort(Comparator.comparing(Quantity::getNameValue));
				whenSorting = false;

				// This loads the data from the ObservableList into the table, so the TableView code can
				// display it and provide all of the functions that it provides
				table.setItems(tableData);

				// This calls add the three major column titles into the table.  Notice that the right most
				// column is a composite of the three value fields (measure, error term, and units)
				table.getColumns().add(col_NameValue);
				table.getColumns().add(col_IsConstantValue);
				table.getColumns().add(col_ValueGroup);

				// With all of the GUI elements defined and initialized, we add them to the window pane
				thePane.getChildren().addAll(btn_Close, btn_Add, btn_Delete, table, lbl_EditingGuidance,btn_addfile,
						VNamelb,VNameTF,ValueNamelb, Valuestf, ENamelb, ErrorTermtf, UNamelb, Unitstf, Vtypelb,Vtypetf);
				// We set the scene into dialog for this window
				dialog.setScene(dialogScene);				
				// We show the completed window to the user, making it possible for the user to start
				// clicking on the various GUI widgets in order to make things happen.
				dialog.show();
	}
	
	/***
	 * This method is call for when the user load its already created file and do changes
	 * then this method will save the that specified changed result
	 */
	public static void saveLoadedFile() {
	
	// this is calling method from the file of manage definations 
		//where we define the CRUD operations
		ManageDefinations.addDefinations(); // calling of operations
	
	}
		
	/*****
	 * The setupButton method is used to factor out recurring text in order to speed the coding and
	 * make it easier to read the code.
	 * 
	 * @param b	- Button that specifies which button is being set up
	 * @param w - int that specifies the minimum width of the button
	 * @param x - int that specifies the left edge of the button in the window
	 * @param y - int that specifies the upper edge of the button in the window
	 */
	private static void setupButton(Button b, int w, int x, int y) {
		b.setMinWidth(w);
		b.setAlignment(Pos.BASELINE_CENTER);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}
	
}