/*Dillon Olbrich
* Programming Assignment 1
* 2/10/2023
* COSC 3302
*/
import java.util.*;
public class DFA {      
	
    public static void main(String[] args) { 
    	
    	Scanner in = new Scanner(System.in); 
    	String states;
    	String acceptingStates;
    	String transitionA;
    	String transitionB;
    	
    	System.out.println("Enter all states");
    	states = in.nextLine();
    	char currentState;
    	char cStartState;
    	
    	char[][] statesArray = new char[100][100];
    	
    	char[][] transitionArray = new char[100][100];
    	
    	states = states.replaceAll(" ", "");
    	
    	for(int i = 0; i < states.length(); i++) {
    		
			statesArray[0][i] = states.charAt(i);
	
	}
    	
    	int numberOfStates = states.length();
    	for(int i = 0; i < numberOfStates; i++) {
    		
    		
    		for(int j = 0; j < 2; j++) {
    			if(j==0) {
    				System.out.println("Enter the transition for State " + statesArray[0][i] + " when it recieves an (a) or (0)");
            		transitionA = in.nextLine();
            		transitionArray[i][j] = transitionA.charAt(0);
    			}
    			else{
    				System.out.println("Enter the transition for State " + statesArray[0][i] + " when it recieves an (b) or (1)");
            		transitionB = in.nextLine();
            		transitionArray[i][j] = transitionB.charAt(0);
    			}
        		
    		}
    		
    	}
    	
    	
    	
    	System.out.println("Enter the accepting states");
    	acceptingStates = in.nextLine();
    	
    	acceptingStates = acceptingStates.replaceAll(" ", "");
    	
    	
    	for(int i = 0; i < acceptingStates.length(); i++) {
    		
			statesArray[1][i] = acceptingStates.charAt(i);
			
	
    	}
    	
    	System.out.println("Enter the start state :");
    	String startState = in.nextLine();
    	cStartState = startState.charAt(0);
    	currentState = cStartState;
    	
    	
    	System.out.println("Enter the input string :");
    	String inputString = in.nextLine();
    	char[] inputArray = new char[100];
    	
    	for(int i = 0; i < inputString.length(); i++) {
    		
    		inputArray[i] = inputString.toLowerCase().charAt(i);
    		
    		
    	}
    	for(int i = 0; i < inputString.length(); i++) {
    		
    		for(int j = 0; j < 2; j++) {
    			
    			if(inputArray[i] == 'a') {
        			
        		}
        		else {
        			
        		}
    		}
    	
    		
    		
    	}
    	
    	} 
  }
        
   
