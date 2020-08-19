package calculator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Dictionary;
import java.util.stream.Collectors;

import calculator.DefinationsUserInterface.Quantity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ManageDefinations{
	
	/**
	 * <p>
	 * Title: ManageDefinations - A component of the DefinationsUserInterface
	 * </p>
	 *
	 * <p>
	 * Description: A controller object class whihc perform the CRUD operations
	 * </p>
	 *
	 * <p>
	 * Copyright: Copyright © 2019
	 * </p>
	 *
	 * @author Sanchit
	 * 
	 * @verson 	2.00	An entity class which supports all the operations based on CRUD methodology
	 * 
	 */

	/**********************************************************************************************
	 * 
	 * The ManageDefinations Class provides an platform upon which the DefinationsUserInterface
	 * for the a pop-up window implementation of the Variable / Constant Definition Table can be
	 * developed and experiments can be run without dealing with the rest of the Calculator.
	 * 
	 */
		/**********************************************************************************************
	 * 
	 * This method read the definations present in the saved or created file.
	 * The purpose of this method is to open an table by reading all the content present in
	 * file
	 * 
	 * @throws IOException -  exception to handle IO errors
	 *  
	 */
	public static void readDefiantons() throws IOException {
		
		
		String filePath = "empty.txt"; // providing the path of empty file
		File file = new File(filePath);
		@SuppressWarnings({ "resource", "unused" })
		BufferedReader br = new BufferedReader(new FileReader(file)); // reading the file using buffered reader
		
		//further process to read the data into small tokens and then store it into
		// an array of string type. After reading the data, it wil display the data into table
		Collection<Quantity> list1 = Files.readAllLines(new File("empty.txt").toPath())
				.stream()
				.map(line -> {
					Quantity cd = new Quantity();
					String[] details = line.split("-"); // declaring the array of string 
					
					//divide the data into small token
					cd.setNameValue(details[0]);
					cd.setIsConstantValue(details[1]);
					cd.setMeasureValue(details[2]);
					cd.setErrorTermValue(details[3]);
					cd.setUnitsValue(details[4]);
					return cd;
					
				})
				.collect(Collectors.toList());
		

		DefinationsUserInterface.tableData =FXCollections.observableArrayList(list1); // The list of values being defined

		
	}
	
	/***
	 * This method will call when user wants to perfomr CUD operation as this
	 * method will open the popup  where differnet buttons will be there for several
	 * operations.
	 * THis method will create a new file in which user can enter the data by using buttons
	 */

	public static void addDefinations()
	{
		 Stage stage = new Stage(); // declaring the stage
		 Pane pane = new Pane(); // declaring the pane
		 
		 // defining the label and setting up its attributes for further use
		 Label label = new Label("Enter file name: ");
		 label.setLayoutX(80);label.setLayoutY(100);
		 
		 //setting up the properties of textfield present in the popup window
		 DefinationsUserInterface.TextFile.setLayoutX(80);
		 DefinationsUserInterface.TextFile.setLayoutY(125);
		  DefinationsUserInterface.TextFile.setMinWidth(250);
		 
		  // create a buuton and define its properties
		         Button btnLogin = new Button();    
		         btnLogin.setLayoutX(350);btnLogin.setLayoutY(125);
		         btnLogin.setText("Save File as");
		         
		         //event handling of the button for its specific functioning
		          btnLogin.setOnAction((event) ->{ 	createRepository();
		          			stage.hide(); 	});
		         
		         // adding the relevant elements to the pane
		         pane.getChildren().addAll(label,btnLogin, DefinationsUserInterface.TextFile);
		      
		 		  Scene scene = new Scene(pane, 500, 300); // setting up the attribute of the scene
		          stage.setScene(scene);
		          stage.show();
		 
	     }

	/***
	 * The purpose of this method to takes the input file from the user and load
	 * it into the table by clciking on the button.this method is call when user click on the
	 * load file's data button
	 * 
	 * @throws IOException -  exception to handle IO errors
	 */
	
	
	public static void loadDictionary() throws IOException {
		
		String file1 = "RepositoryFolder\\"; // setting the path of the file
		String filePath = "RepositoryFolder\\"+UserInterface.textUser.getText();
		File file = new File(filePath);
		@SuppressWarnings({ "resource", "unused" })
		BufferedReader br = new BufferedReader(new FileReader(file)); // start reading the file

		//add the content of the file by using collections and then divide the onctent into tokkens
		//then set into to the table
		Collection<Quantity> list1 = Files.readAllLines(new File(file1+UserInterface.textUser.getText()).toPath())
				.stream()
				.map(line -> {
					Quantity cd = new Quantity();
					String[] details = line.split("-"); //dealcring an array of type string
					cd.setNameValue(details[0]);
					cd.setIsConstantValue(details[1]);
					cd.setMeasureValue(details[2]);
					cd.setErrorTermValue(details[3]);
					cd.setUnitsValue(details[4]);
					return cd;
				})
				.collect(Collectors.toList());

		DefinationsUserInterface.tableData =	FXCollections.observableArrayList(list1); // The list of values being define		
	}
	
	
	/***
	 * This method is call when user wants to save the file wiht user specific name
	 * Basically this method save the data of the table into the user defiend file.
	 */
	
	public static void createRepository() {
		
		// setting up the path as a repository folder
		File file2 = new File("RepositoryFolder\\"+DefinationsUserInterface.TextFile.getText()+".txt");
		
		//declaring the object of the list for the further use
		ObservableList<Quantity> observablelistsss =DefinationsUserInterface.tableData;

		try {
			BufferedWriter outWriter = new BufferedWriter(new FileWriter(file2)); // write the content of the file using buffer writer
			
			//using for each loop and iterate all the content into the file
			for(Quantity person: observablelistsss) {
				
				outWriter.write(person.toString());
				outWriter.newLine();
			}
			outWriter.close();
		}
		catch(Exception e) { String w = "";	String d = "";	boolean t = true;
							//Dictionary.addEntry(w, d, t);
		}
		// setting up the path as a repository folder
		File file3 = new File("JRE System Libraries\\"+DefinationsUserInterface.TextFile.getText()+".txt");
		try {
			// write the content of the file using buffer writer
			BufferedWriter outWriter = new BufferedWriter(new FileWriter(file3));
			
			//using for each loop and iterate all the content into the file
			for(Quantity person: observablelistsss) {
				
				outWriter.write(person.toString1());
				outWriter.newLine();
			}
			outWriter.close();
		}
		catch(Exception e) {
		}
	}
	
	

}