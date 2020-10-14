package Section_1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Stack;

import calculator.BusinessLogic;
import calculator.DefinationsUserInterface;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import calculator.UserInterface;



/**
 * <p> Title: Q1toQ4 Class. </p>
 * 
 * <p> Description: This class contains the logic based on the new reuquirements and implements data structures for its use .</p>
 * 
 * @author Sanchit 
 * 
 * @version 4.1 support the functionaity to manipulate the computed resulsts as per the user.
 */

public class Q1toQ4 {
	
	 public static BusinessLogic perform = new BusinessLogic();
	 public static LinkedList<String> list = new LinkedList<>();
	 public static Stack<String> st = new Stack<>();
	 private static Label message1 = new Label("");
	 public static Label saveResult = new Label("Save Results");

	 public static Button AddComputedResult = new Button("\u24B6");
	 public static Button RemoveComputedResult = new Button("\u24B9");
	 public static Button ViewComputedResult = new Button("\u24CB");
		
		
		
		
	 
	 
	 
	 
	 /************
		 * This method willexecute when it will be called by the Add button for saving the computed result.
		 * This mehtod basically use the stack and will save the computed result into it and then write it
		 * int user defiend file.
		 * Moreover, if the limit is exceed by 10, then this mehtod automatically show the error messge to the UI
		 * for correction.
		 */
	 

	public static void SaveCurrentResult() 
	{
			
		if(st.isEmpty() || st.size() <=10)
		{
			
		try {
			FileWriter fstream = new FileWriter("Computations\\"+DefinationsUserInterface.TextFile.getText()+".txt");

		  BufferedWriter out = new BufferedWriter(fstream);
		  
		  st.push("\n"+"Addition: -\n"+"MeasuredValue: " +perform.addition() +" ErrorTerm: "+ perform.addition1()
			+ " Units: " + UserInterface.ComboboxOperand3.getValue()+"\n\n"
			+"Subtractions: -\n"+"MeasuredValue: " +perform.subtraction() +" ErrorTerm: "+ perform.subtractionE()
 			+ " Units: " + UserInterface.ComboboxOperand3.getValue()+"\n\n"
 			+ "Multiplication: -\n"+"MeasuredValue: " +perform.multiplication() +" ErrorTerm: "+ perform.multiplicationE()
  			+ " Units: " + UserInterface.ComboboxOperand3.getValue()+"\n\n"
 			+ "Division: -\n"+"MeasuredValue: " +perform.division() +" ErrorTerm: "+ perform.divisionE()
 			+ " Units: " + UserInterface.ComboboxOperand3.getValue()+"\n\n"
 			+ "Squareroot: -\n"+"MeasuredValue: " +perform.squareroot() +" ErrorTerm: "+ perform.squarerootE()
 	 			+ " Units: " + "sqrt"+ UserInterface.ComboboxOperand3.getValue());


		 out.write(st.pop());
		
		  out.close(); // close the writing
		  }catch (Exception e){
		  System.err.println("Error: " + e.getMessage());
		 
	}
		}
		else {
			message1.setLayoutX(100);
			message1.setLayoutY(820);
			message1.setText("Error" +"\n" +"Limit Exceed....!");
			final double MAX_FONT_SIZE = 24; 
			message1.setFont(new Font(MAX_FONT_SIZE));
			message1.setTextFill(Color.web("Green"));
			
		}
	
	
	}
	///////////////////Answer for Question 2/////////////////////////////////////////////////////////////////////////
	/**
	 * the Data structure I used in this question is Stack because of its tendencey LIFO order. As the main requiredment
	 * of this question is that we have to show the recent added entry ar the top. Therfore, in the stack
	 * we can easily get the show the element at top using pop and push mehods. 
	 * Morever, stack is baed on the concept of  LIFO (last In First Out). Hence, it is suitable for this
	 * quesion according to me.
	 * 
	 * Some Methods for Stack
	 * 1. push(): - to add the element into the stack
	 * 2. pop(): - to remove the element into the
	 * 3. size(): - to get he size of the stack 
	 */
	/////////////////////////////////////////////////////////////////////////////////
	
	
	
	/************
	 * This method will execute when it will be called by the remove button for rwemoving the oldest computed result.
	 * This mehtod basically use the linked list and will remove the oldest result as per the user's desired file
	 * Moreover, if the entered file is already empty, then this mehtod automatically show the error messge to the UI
	 * for correction.
	 */
 
	public static void RemoveOldestResult() throws IOException
	{
		
		 Scanner sc = new Scanner(new File("Computations\\"+DefinationsUserInterface.TextFile.getText()+".txt"));
		 
		 LinkedList<String> list = new LinkedList<>(st);
		 
		 if(sc.hasNextLine() )
		  {
			  for(int i = 0; i< 39; i++)
			  {
				  list.remove(sc.next());
			  }
			  message1.setLayoutX(700);
				message1.setLayoutY(820);
				message1.setText("Removed the Current Entry");
				final double MAX_FONT_SIZE = 24; 
				message1.setFont(new Font(MAX_FONT_SIZE));
				message1.setTextFill(Color.web("Green"));
		  }
		 else {
				message1.setLayoutX(700);
				message1.setLayoutY(820);
				message1.setText("Error" +"\n" +"File is Empty");
				final double MAX_FONT_SIZE = 24; 
				message1.setFont(new Font(MAX_FONT_SIZE));
				message1.setTextFill(Color.web("Green"));
				
		 }
		  
		  FileWriter fstream = new FileWriter("Computations\\"+DefinationsUserInterface.TextFile.getText()+".txt");

		  BufferedWriter out = new BufferedWriter(fstream);
		  out.write(list.toString());
		  out.close();
		  
	}
	
	
	/////////////////////Anwers for the Question 3///////////////////////
	/**
	 * For this question I used the concept of list becaiuse, we have a mehtod removeFirst() and remove() method which will help us 
	 * to fetch the oldest item. 
	 * Moreover,using linked list, i can esily fetch the first and top order values and wiht the the help of remove method, I
	 * can easily remove the elements from the Linked list and then I write the updated linked list into files as per the requirements
	 * 
	 * Methods of linked list
	 * 1. remove: - to remove the elements from the list
	 * 2. removeFirst- to remove the first order of elements from list
	 */
	////////////////////////////////////////////////////////////////////////////////////////
	
	/************
	 * This method willexecute when it will be called by the view button for viewwing the computed results.
	 * This mehtod basically use the linked list and will save the data from another list which further executes the command
	 * show all the result to the user
	 */
 

	public static void ViewtheComputedReuslts() throws FileNotFoundException
	{
		
		LinkedList<String> list1 = new LinkedList<>(list);
		
		 Stage stage = new Stage(); // declaring the stage 
		 Pane pane = new Pane(); // defining the pane
		 
		 // setting up the label for guidelines for user
		 // and defininf its properties
		 Label label = new Label("Your Computed Reuslt");
		 label.setLayoutX(300); label.setLayoutY(50);
		 
		 TextField result = new TextField();
		 result.setLayoutX(20); result.setLayoutY(100);
		 result.setMinWidth(700); result.setMinHeight(600);
		         // adding the relevant elements to the pane 
		 pane.getChildren().addAll(label,result);
		 
	       Scene scene = new Scene(pane, 750, 750); // setting up the attribute of the scene
		          stage.setScene(scene);
		          stage.show();
		
		          Scanner sc = new Scanner(new FileReader("Computations\\"+DefinationsUserInterface.TextFile.getText()+".txt"));
		          while(sc.hasNext())
		          {
		        	  list1.add(sc.nextLine());
		          }
		          
		ListIterator<String> itr = list1.listIterator();
		while(itr.hasNext())
		{
			System.out.println(itr.next());
			result.appendText(itr.next());
		}
			
	}
	
	
	///////////////////////////Answers for Question 4////////////////////////////////
	
	/**
	 * To view the result I used again the concept of the linked list. As there are saevral methods in the list, which helps me
	 * to fetcht he remaining elements.
	 * thereofre, I used the iterator to iterate allover the values and view the list into the values.
	 */

}
