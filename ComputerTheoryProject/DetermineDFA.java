/*Dillon Olbrich
* Programming Assignment 1
* 2/28/2023
* COSC 3302
*/
import java.util.*;
import java.io.*;

public class DetermineDFA {
	  public static void main(String[] args) { 
	    	
	    	Scanner in = new Scanner(System.in);
	    	String acceptingStates;
	    	String states;
	    	String[] acceptingStatesArray;
	    	String[] statesArray;
	    	String transition;
	    	
	    	
	    	
	    	String initialState;
	    	
	    	System.out.println("Enter the initial state");
	    	initialState = in.nextLine();
	    	
	    	System.out.println("Enter the states");
	    	states = in.nextLine();
	    	statesArray = states.split(" ");
	    	
	    	String[][] transitionArray = new String[statesArray.length][2];
	    	
	    	System.out.println(statesArray.length);
	    	
	    	System.out.println("Enter the accepting states");
	    	acceptingStates = in.nextLine();
	    	acceptingStatesArray = acceptingStates.split(" ");
	    	
	    	

	    	
	    	for(int i=0; i<statesArray.length; i++) {
	    		
	    	
	    			
	    			System.out.println("Enter the transition when State " + statesArray[i] + " recieves input 0");
		    		transition = in.nextLine();
		    		transitionArray[i][0] = transition;
		    		
		    		System.out.println("Enter the transition when State " + statesArray[i] + " recieves input 1");
		    		transition = in.nextLine();
		    		transitionArray[i][1] = transition;
		    		
		    		
	    	}
	    	
	    	
	    	
	    	
	    	System.out.println("Enter the input string :");
	    	String inputString = in.next();
	    	
	    	//initial state
	    	String currentState = initialState;
	    	
	    
	    	
	    	//loop through given input and loop through states
	    	for(int i = 0; i < statesArray.length; i++) {
	    		
	    		for(int j = 0; j < inputString.length(); j++) {
		    	  	if(inputString.charAt(j) == '1' && currentState == statesArray[i]) {
		    	  	
		    	  		currentState = transitionArray[j][1];
		        	}
		    	  	else if(inputString.charAt(j) == '0' && currentState == statesArray[i]) {
		    	  		currentState = transitionArray[i][0];
		        	}
		 
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
