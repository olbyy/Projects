/*Dillon Olbrich
* Programming Assignment 1
* 2/10/2023
* COSC 3302
*/
import java.util.*;

public class NFAtoDFA {
    public static void main(String[] args) { 
    	
    	Scanner in = new Scanner(System.in);   
    	String acceptingStates;
    	//Let
    	//q0 - A
    	//q1 - {A,B,C,D}
    	//q2 - {B,D}
    	//q3 - {C,D}
    	//q4 - {D}
    	//q5 - dead state
    	
    	System.out.println("Enter the accepting states (q0 - q4)");
    	acceptingStates = in.nextLine();
    	

    	System.out.println("Enter the input string :");
    	String inputString = in.next();
    	//initial state
    	String currentState = "q0";
    	
    	//loop through given input and loop through states
    	for(int i = 0; i < inputString.length(); i++) {
    	  	if(inputString.charAt(i) == '0' && currentState =="q0") {
    	  		currentState = "q1";
        	}
    	  	else if(inputString.charAt(i) == '1' && currentState =="q0") {
    	  		currentState = "q5";
        	}
    	  	else if(inputString.charAt(i) == '0' && currentState =="q1") {
    	  		currentState = "q1";
        	}
    	  	else if(inputString.charAt(i) == '1' && currentState =="q1") {
    	  		currentState = "q2";
        	}
    	  	else if(inputString.charAt(i) == '0' && currentState =="q2") {
    	  		currentState = "q3";
        	}
    	  	else if(inputString.charAt(i) == '1' && currentState =="q2") {
    	  		currentState = "q5";
        	}
    	  	else if(inputString.charAt(i) == '0' && currentState =="q3") {
    	  		currentState = "q4";
        	}
    	  	else if(inputString.charAt(i) == '1' && currentState =="q3") {
    	  		currentState = "q2";
        	}
    	  	else if(inputString.charAt(i) == '0' && currentState =="q4") {
    	  		currentState = "q4";
        	}
    	  	else if(inputString.charAt(i) == '1' && currentState =="q4") {
    	  		currentState = "q5";
        	}
    		else if(inputString.charAt(i) == '0' && currentState =="q5") {
    	  		currentState = "q5";
        	}
    	  	else if(inputString.charAt(i) == '1' && currentState =="q5") {
    	  		currentState = "q5";
        	}
    	}
    	if(acceptingStates.contains(currentState)) {
    		System.out.println("String is accepted by DFA");
    		System.out.println("Final State is : " + currentState);
    		System.out.println("Accepting States are : " + acceptingStates);
    	}
    	else {
    		System.out.println("String is not accepted by DFA");
    		System.out.println("Final State is : " + currentState);
    		System.out.println("Accepting States are : " + acceptingStates);
    	}
   
 
    	} 
}
