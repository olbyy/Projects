
/*Dillon Olbrich and Nathan Ha
* Poker Project
* 11/21/2021
* COSC 1174
* This is a program that refers to our Poker Assignments and is for the group project for COSC 1174
* This program uses javaFX and deals 5 card images. New card images come from the top and cards not held go out bottom using animations. 
* The cards can be clicked to hold them and once the player chooses to draw it will determine what the players hand is.
* Uses boolean methods to determine if the play has once.
* Player will place a bet and choose the cards they want. the program determines if the player has won if he has the bet amount is doubled and placed into their pocket.
* The win condition is determined by boolean methods that if determines if a player has a natural royal flush, 5oak, 4oak, royal flush with jokers, straight flush, full house, straight and 3oak
*/
//import util and javafx 
import java.util.ArrayList;
import java.util.Collections;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

//Declare class Poker_3 that extends Application class
public class Poker_Project extends Application {
	
	//create image array list deck to place card images in
	public ArrayList<Image> deck = new ArrayList<>();
	
	//created array list of integers for card value
	public ArrayList<Integer> cardVal = new ArrayList<>();
	
	//create array list of integers for hand
	public ArrayList<Integer> hand = new ArrayList<>();
    
    //create image array list back to place back of card images in
    public ArrayList<Image> back = new ArrayList<>();
    
    
    //data field variables
    
    //Image for back of card
    public Image backImg;
   
    
    //count set to 0
    public int count = 0;
    
    //booleans for holding button
    public boolean isHeld0 = false;
    public boolean isHeld1 = false;
    public boolean isHeld2 = false;
    public boolean isHeld3 = false;
    public boolean isHeld4 = false;
    
    //boolean for drawAble set to false
    public boolean drawAble = false;
    //boolean for dealAble set to false;
    public boolean dealAble = true;
    
    public boolean oneBet = true;
    
    //boolean for hasWon
    public boolean hasWon= false;
    
    //boolean for radioButtonPushed
    public boolean radioButtonPushed =false;
    
    //starting value of player's pocket
    public final int MONEY = 200;
    
    //player's pocket value that is changeable
	public int pocket = MONEY;
	
	//value of 1 bet
	public final int BET_1 = 1;
	
	//value of 10 bet
	public final int BET_10 = 10;
	
	// value of 100 bet
	public final int BET_100 = 100;
	
	//Label for win condition
	public static Label winCon = new Label();
	
	//label for betPrompt
	public Label betPrompt = new Label();
    
    //Image views for card images and for the payTable
    public ImageView view0;
    public ImageView view1;
    public ImageView view2;
    public ImageView view3;
    public ImageView view4;
    public ImageView payView;
    public Image payTable = new Image("WinTable.jpg");
  
    //Override start method to create Stage
    @Override
    public void start(Stage primaryStage) {
    	
    
        //set title to Poker_3
        primaryStage.setTitle("Poker_3");
        
        //create pane objects
        BorderPane bPane1 = new BorderPane();
        FlowPane pane1 = new FlowPane();
    	HBox pane2 = new HBox(10);
    	HBox pane3 = new HBox(10);
    	VBox pane4 = new VBox(10);
    	HBox pane5 = new HBox(10);
 		
        //Instantiate image views for each card on start
         view0 = new ImageView();
         view1 = new ImageView();
         view2 = new ImageView();
         view3 = new ImageView();
         view4 = new ImageView();
         payView = new ImageView(payTable);
         
         
        //Instantiate Buttons and label them
        Text pocketBal = new Text("Pocket : $"+pocket);
        
        //set the color of pocketBal
        pocketBal.setFill(Color.ANTIQUEWHITE);
        
        
        // Label betAmt to display Betting amounts
        Label betAmt = new Label("Betting Amounts:");
        
        //set betAmt color
        betAmt.setTextFill(Color.ANTIQUEWHITE);
        
        //create radio buttons and set label them
        RadioButton radio1 = new RadioButton("Bet: $1     ");
        RadioButton radio2 = new RadioButton("Bet: $10   ");
        RadioButton radio3 = new RadioButton("Bet: $100 ");
        
        //set the text color of each radio button
        radio1.setTextFill(Color.ANTIQUEWHITE);
        radio2.setTextFill(Color.ANTIQUEWHITE);
        radio3.setTextFill(Color.ANTIQUEWHITE);
        
        //create buttons for newGame, deal, draw, and all hold
        Button newGameBtn= new Button("Start New Game");
        Button dealBtn= new Button("Deal");
        Button drawBtn = new Button("Draw");
        
        //setAlignments of panes
        pane1.setAlignment(Pos.CENTER);
        pane2.setAlignment(Pos.CENTER);
        pane3.setAlignment(Pos.CENTER);
        pane4.setAlignment(Pos.CENTER);
        pane5.setAlignment(Pos.CENTER);
        
        //set the height gap and max width of pane1 with the imageviews
        pane1.setHgap(9);
        pane1.setMaxWidth(440);
        
        //add all imageviews to pane1
        pane1.getChildren().addAll(view0, view1, view2, view3,view4,betPrompt);
        
        //add hold buttons to pane 2
      	pane2.getChildren().addAll(payView);
      	
        //add deal, draw and newGame buttons to pane 3
        pane3.getChildren().addAll(dealBtn,drawBtn,newGameBtn);
        
        //add radio buttons and pockBal and betAmt labels to pane 4
        pane4.getChildren().addAll(pocketBal,betAmt, radio1, radio2, radio3);
        
        //add winCon label to pane 5
        pane5.getChildren().addAll(winCon);
        
        //set the scale of winCon to make it bigger
        winCon.setScaleX(1.70);
        winCon.setScaleY(1.70);
        
        //set border pane color
        bPane1.setStyle("-fx-background-color: GREEN");
  
        //set center pane to center that holds imgviews
        bPane1.setCenter(pane1);
        bPane1.setBottom(pane2);
        bPane1.setLeft(pane3);
        bPane1.setRight(pane4);
        bPane1.setTop(pane5);
        
        //create scene and set parameters and panel1
        Scene scene1 = new Scene(bPane1, 800, 800);
        
        //set primary stage scene to scene1
        primaryStage.setScene(scene1);
        
        //show primary stage
        primaryStage.show();
        
        //when mouse is clicked remove card and put a placeholder as well as add card to hand
        view0.setOnMouseClicked((event)  ->{
        	if(!isHeld0) {
        	//set held
            isHeld0 = true;
            hand.add(cardVal.get(0));
            //remove the card
            cardVal.remove(0);  
            //add place holder
            cardVal.add(0,0);
       
        	}
          
        	
        });
        
        //set view0 opacity when mouse enters
        view0.setOnMouseEntered((event)  ->{
        	
        //if card is drawable set the opacity when the mouse enters	
        if(drawAble) {	
        	//set opacity
        view0.setOpacity(0.5);
        }
        	
        });
        
        //set view0 opacity when mouse exits
        view0.setOnMouseExited((event)  ->{
        	
        	//if card is not held reset the opacity
        	if(!isHeld0) {
        		
        	//set opacity back	
            view0.setOpacity(1);
            	
        	}	
            });
        //when mouse is clicked remove card and put a placeholder as well as add card to hand
        view1.setOnMouseClicked((event)  ->{
        	
        	//if card is not held allow to add card to hand and clicked once
        	if(!isHeld1) {
        		
        	//set held
            isHeld1 = true;
            
            //add card to hand
            hand.add(cardVal.get(1));
            
            //remove the card
            cardVal.remove(1);
            
            //add place holder
            cardVal.add(1,0);
       
        	}
        	
        });
        
        //set view1 opacity when mouse enters
        view1.setOnMouseEntered((event)  ->{
        	
        //if card is drawable set the opacity when the mouse enters	
        if(drawAble) {	
        	view1.setOpacity(0.5);
        }
        	
        });
        
        //set view1 opacity when mouse exits
        view1.setOnMouseExited((event)  ->{
        	
        	//if card is not held reset the opacity
        	if(!isHeld1) {
        		
            view1.setOpacity(1);
            	
        	}	
         });
        
        //when mouse is clicked remove card and put a placeholder as well as add card to hand
        view2.setOnMouseClicked((event)  ->{
        	//if card is not held allow to add card to hand
        	if(!isHeld2) {
        		
        	//set held
            isHeld2 = true;
            
            //add card to hand
            hand.add(cardVal.get(2));
            
            //remove the card
            cardVal.remove(2);
            
            //add place holder
            cardVal.add(2,0);
         
        	}
        	
        });
        
        //set view2 opacity when mouse enters
        view2.setOnMouseEntered((event)  ->{
        	
	        //if card is drawable set the opacity when the mouse enters	
	        if(drawAble) {	
	        //set opacity	
	        view2.setOpacity(0.5);
	        }
	        	
        });
        //set view2 opacity when mouse exits
        view2.setOnMouseExited((event)  ->{
        	
        	//if card is not held reset the opacity
        	if(!isHeld2) {
        		
            view2.setOpacity(1);
            	
        	}	
        });
        
        //when mouse is clicked remove card and put a placeholder as well as add card to hand
        view3.setOnMouseClicked((event)  ->{
        	if(!isHeld3) {        	//set held
	            isHeld3 = true;
	            hand.add(cardVal.get(3));
	            //remove the card
	            cardVal.remove(3);
	            
	            //add place holder
	            cardVal.add(3,0); 
        	}
        });
        
        //set view3 opacity when mouse enters
        view3.setOnMouseEntered((event)  ->{
        	
        //if card is drawable set the opacity when the mouse enters	
        if(drawAble) {	
        	//set opacity
        	view3.setOpacity(0.5);
        }
        	
        });
        
        //set view3 opacity when mouse exits
        view3.setOnMouseExited((event)  ->{
        	
        	//if card is not held reset the opacity
        	if(!isHeld3) {
        		
        	//set opacity	
            view3.setOpacity(1);
            	
        	}	
            });
        //when mouse is clicked remove card and put a placeholder as well as add card to hand
        view4.setOnMouseClicked((event)  ->{
        	//
        	if(!isHeld4) {    
        		
            isHeld4 = true;	//set held
            
            hand.add(cardVal.get(4));
            
            //remove the card
            cardVal.remove(4);
         
            //add place holder
            cardVal.add(4,0);
        	}
        	
        });
        
        //set view4 opacity when mouse enters
        view4.setOnMouseEntered((event)  ->{
        	
        //if card is drawable set the opacity when the mouse enters	
        if(drawAble) {	
        	
        	//set opacity
        	view4.setOpacity(0.5);
        }
        	
        });
        //set view4 opacity when mouse exits
        view4.setOnMouseExited((event)  ->{
        	
        	//if card is not held reset the opacity
        	if(!isHeld4) {
        		
        	//set opacity	
            view4.setOpacity(1);
            	
        	}	
            });
        
        //set on action when button is pressed to draw
        drawBtn.setOnAction((event) -> {
       
        	//if drawAble. is set to true from deal button
        	if(drawAble) {
        		
        	 //shuffle cardVal and deck
            shuffle(cardVal, deck);
            	
        	//increment count
        	count++;
        	
        	//reset winCon text
        	winCon.setText(null);
        	
        	//if count is less than 3
        	if(count<3) {		
	        		//if hand is less than 5
	        	if(hand.size()<5) {	
	        		
	        		 //if card 1 not held initialize animation and add cardVal index 0 to hand array
	              	if(!isHeld0) {
	            		
	                    initialize(view0 , 0);
	                    hand.add(cardVal.get(0));
	              
	                    
	              	}
	              	 //if card 2 not held initialize animation and add cardVal index 1 to hand array
			        if(!isHeld1) {
			                	   
			             initialize(view1, 1);
			             hand.add(cardVal.get(1));
			                	  
			           }
			        //if card 3 not held initialize animation and add cardVal index 2 to hand array
			        if(!isHeld2) {
			                	  
			             initialize(view2, 2);
			             hand.add(cardVal.get(2));
			           }
			        //if card 4 not held initialize animation and add cardVal index 3 to hand array        
			        if(!isHeld3) {
			         	  
			         	 initialize(view3 , 3);
			         	 hand.add(cardVal.get(3));
			         	  
			           }
			        //if card 5 not held initialize animation and add cardVal index 4 to hand array
			        if(!isHeld4) {
			         	  
			         	  initialize(view4, 4);
			         	  hand.add(cardVal.get(4));
			         	  
			           }
		
	        	}
        	}
        			//if count 2 
		        	if(count==2) {
		        		
		        	//reset booleans to false and count
		        	isHeld0 = false;
				    isHeld1 = false;
				    isHeld2 = false;
				    isHeld3 = false;
				    isHeld4 = false;
				    drawAble=false;
		        	count=0;
		      
		        	
		        	//if player hand is a RoyalFlush and if they put a bet pays 250x
		 			if(hasRoyalFlush(hand)) {
		 				
			 				//invoke animation for big win
	 						bigWin();
 						
		 				
		 					//set winCon text	
		 					winCon.setText("You have a Royal Flush.");
		 					hasWon=true;
		 					
							if(radio1.isSelected()) {
								
								//doubles bet amount and updates pocket
								pocket+=BET_1*250;
								pocketBal.setText("Pocket : $"+pocket);
								
							}
							if(radio2.isSelected()) {
								
								//doubles bet amount and updates pocket
								pocket+=BET_10*250;
								pocketBal.setText("Pocket : $"+pocket);
									
							}
							//adds money to pocket if Radio3 was selected and updates pocketBal text
							if(radio3.isSelected()) {
								
								//doubles bet amount and updates pocket
								pocket+=BET_100*250;
								pocketBal.setText("Pocket : $"+pocket);
								
						
							}
		 					
			 			}
		 				else if(hasFiveofKind(hand)) {//checks if player hand is a Pair and if they put a bet pays 100x
		 					
								//invoke animation for big win
		 						bigWin();
		 						
								//set winCon text	
								winCon.setText("You win! You have a Five of a Kind.");
								
								//set hadWon to true
								hasWon=true;
								
								if(radio1.isSelected()) {
									
									//doubles bet amount and updates pocket
									pocket+=BET_1*100;
									pocketBal.setText("Pocket : $"+pocket);
									
								}
								if(radio2.isSelected()) {
									
									//doubles bet amount and updates pocket
									pocket+=BET_10*100;
									pocketBal.setText("Pocket : $"+pocket);
										
								}
								//adds money to pocket if Radio3 was selected and updates pocketBal text
								if(radio3.isSelected()) {
									
									//doubles bet amount and updates pocket
									pocket+=BET_100*100;
									pocketBal.setText("Pocket : $"+pocket);
									
							
								}
								
								}	//if player hand is a RoyalFlush with jokers and if they put a bet pays 50x
		 						else if(hasRoyalFlushJoker(hand)) {
		 							
				 					//set winCon text	
				 					winCon.setText("You have a Royal Flush.");
				 					hasWon=true;
				 					
									if(radio1.isSelected()) {
										
										//doubles bet amount and updates pocket
										pocket+=BET_1*50;
										pocketBal.setText("Pocket : $"+pocket);
										
									}
									if(radio2.isSelected()) {
										
										//doubles bet amount and updates pocket
										pocket+=BET_10*50;
										pocketBal.setText("Pocket : $"+pocket);
											
									}
									//adds money to pocket if Radio3 was selected and updates pocketBal text
									if(radio3.isSelected()) {
										
										//doubles bet amount and updates pocket
										pocket+=BET_100*50;
										pocketBal.setText("Pocket : $"+pocket);
										
						
							}
					 					
						 			}else if(hasStraight(hand) && hasFlush(hand)) {//checks if player hand for straight and flush pays 50x
											
									//set winCon text	
									winCon.setText("You win! You have Straight Flush.");
									
									//set hadWon to true
									hasWon=true;
									if(radio1.isSelected()) {
										
										//doubles bet amount and updates pocket
										pocket+=BET_1*50;
										pocketBal.setText("Pocket : $"+pocket);
										
									}
									if(radio2.isSelected()) {
										
										//doubles bet amount and updates pocket
										pocket+=BET_10*50;
										pocketBal.setText("Pocket : $"+pocket);
											
									}
									//adds money to pocket if Radio3 was selected and updates pocketBal text
									if(radio3.isSelected()) {
										
										//doubles bet amount and updates pocket
										pocket+=BET_100*50;
										pocketBal.setText("Pocket : $"+pocket);
										
								
									}
								
									}else if(hasFourofKind(hand)) {//checks if player hand is a Pair and if they put a bet
									
									//set winCon text	
									winCon.setText("You win! You have a Four of a Kind.");
									
									//set hadWon to true
									hasWon=true;
									
									if(radio1.isSelected()) {
										
										//doubles bet amount and updates pocket
										pocket+=BET_1*20;
										pocketBal.setText("Pocket : $"+pocket);
										
									}
									if(radio2.isSelected()) {
										
										//doubles bet amount and updates pocket
										pocket+=BET_10*20;
										pocketBal.setText("Pocket : $"+pocket);
											
									}
									//adds money to pocket if Radio3 was selected and updates pocketBal text
									if(radio3.isSelected()) {
										
										//doubles bet amount and updates pocket
										pocket+=BET_100*20;
										pocketBal.setText("Pocket : $"+pocket);
										
								
									}
									
										}else if(hasFullHouse(hand)) {//checks if player hand is a Pair and if they put a bet
											
											//set winCon text	
											winCon.setText("You win! You have a a Full House.");
											
											//set hadWon to true
											hasWon=true;
											
											if(radio1.isSelected()) {
												
												//doubles bet amount and updates pocket
												pocket+=BET_1*8;
												pocketBal.setText("Pocket : $"+pocket);
												
											}
											if(radio2.isSelected()) {
												
												//doubles bet amount and updates pocket
												pocket+=BET_10*8;
												pocketBal.setText("Pocket : $"+pocket);
													
											}
											//adds money to pocket if Radio3 was selected and updates pocketBal text
											if(radio3.isSelected()) {
												
												//doubles bet amount and updates pocket
												pocket+=BET_100*8;
												pocketBal.setText("Pocket : $"+pocket);
												
										
											}
											
												}else if(hasFlush(hand)) {//checks if player hand is 3 of a Kind
													
													//set winCon text	
													winCon.setText("You win! You have a Flush");
													
													//set hadWon to true
													hasWon=true;
													if(radio1.isSelected()) {
														
														//doubles bet amount and updates pocket
														pocket+=BET_1*7;
														pocketBal.setText("Pocket : $"+pocket);
														
													}
													if(radio2.isSelected()) {
														
														//doubles bet amount and updates pocket
														pocket+=BET_10*7;
														pocketBal.setText("Pocket : $"+pocket);
															
													}
													//adds money to pocket if Radio3 was selected and updates pocketBal text
													if(radio3.isSelected()) {
														
														//doubles bet amount and updates pocket
														pocket+=BET_100*7;
														pocketBal.setText("Pocket : $"+pocket);
														
												
													}
													
														}else if(hasStraight(hand)) {
											 					
											 						//set winCon text	
											 						winCon.setText("You have a Straight.");
											 						
											 						//set hadWon to true
																	hasWon=true;
																	if(radio1.isSelected()) {
																		
																		//doubles bet amount and updates pocket
																		pocket+=BET_1*5;
																		pocketBal.setText("Pocket : $"+pocket);
																		
																	}
																	if(radio2.isSelected()) {
																		
																		//doubles bet amount and updates pocket
																		pocket+=BET_10*5;
																		pocketBal.setText("Pocket : $"+pocket);
																			
																	}
																	//adds money to pocket if Radio3 was selected and updates pocketBal text
																	if(radio3.isSelected()) {
																		
																		//doubles bet amount and updates pocket
																		pocket+=BET_100*5;
																		pocketBal.setText("Pocket : $"+pocket);
																		
																
																	}
																	
																	
														}else if(has3ofKind(hand)) {//checks if player hand is 3 of a Kind
																
																//set winCon text	
																winCon.setText("You win! You have 3 of a Kind.");
																
																//set hadWon to true
																hasWon=true;
																if(radio1.isSelected()) {
																	
																	//doubles bet amount and updates pocket
																	pocket+=BET_1*2;
																	pocketBal.setText("Pocket : $"+pocket);
																	
																}
																if(radio2.isSelected()) {
																	
																	//doubles bet amount and updates pocket
																	pocket+=BET_10*2;
																	pocketBal.setText("Pocket : $"+pocket);
																		
																}
																//adds money to pocket if Radio3 was selected and updates pocketBal text
																if(radio3.isSelected()) {
																	
																	//doubles bet amount and updates pocket
																	pocket+=BET_100*2;
																	pocketBal.setText("Pocket : $"+pocket);
																	
															
																}
																
													
													}
							//else not a win
							else {
									//set winCon text		
									winCon.setText("Not a winning hand");
									
									//set hadWon to false
									hasWon = false;
											
										}
		 		
								if(!hasWon&&(pocket<=0)) {
									pocketBal.setText("Pocket : $"+pocket);
									winCon.setText("You are out of money please start a new game");	
									dealAble=false;
									}
							
		        	}
        	}

});//end of draw set on action

        
       
        //Set on action event first for button to hold card setting isHeld0 to true
        dealBtn.setOnAction((event) -> {
   
        	//set winCon text to null to clear
        	winCon.setText(null);
        	
        		//if 1 radio button is selected set one bet to true
        		if((radio3.isSelected() && !radio2.isSelected() && !radio1.isSelected())
        				|| (radio2.isSelected() && !radio3.isSelected() && !radio1.isSelected())
        				||	(radio1.isSelected() && !radio2.isSelected() && !radio3.isSelected())) {
        			oneBet=true;
        		}
        		//if no radio buttons are pressed prompt to place a bet
        		if(!radio3.isSelected() && !radio2.isSelected() && !radio1.isSelected()) {
        			oneBet=false;
        			dealAble=false;
        			betPrompt.setTextFill(Color.RED);
					betPrompt.setText("Please place a bet");
					betPrompt.setScaleX(1.70);
					betPrompt.setScaleY(1.70);
        		}
        	
        	
	    		//if statement that removes the money from the pocket of place bet and updates according to the radio3 button
				if(			(radio3.isSelected() && radio2.isSelected() && radio1.isSelected())	
						||	(radio2.isSelected() && radio1.isSelected()) 
						||	(radio1.isSelected() && radio3.isSelected()) 
						||  (radio3.isSelected() && radio2.isSelected())) {
					
					oneBet = false;
		        	dealAble=false;
		        	betPrompt.setTextFill(Color.RED);
					betPrompt.setText("Max bet is only one option");
					betPrompt.setScaleX(1.70);
					betPrompt.setScaleY(1.70);
					
				}
			
        	
				//if statement that will not allow the user to continue to bet unless they choose a lower bet
				if(radio3.isSelected() && pocket<=99) {
					
					betPrompt.setTextFill(Color.BLACK);
					betPrompt.setText("Please choose a lower bet");
					dealAble=false;
					betPrompt.setScaleX(1.70);
					betPrompt.setScaleY(1.70);
					
				}
				//if statement that will not allow the user to continue to bet unless they choose a lower bet
				if(radio2.isSelected() && pocket<=9) {
					
					betPrompt.setTextFill(Color.BLACK);
					betPrompt.setText("Please choose a lower bet");
					dealAble=false;
					betPrompt.setScaleX(1.70);
					betPrompt.setScaleY(1.70);
					
				}
				//if statement that will not allow the user to play without money
				if(radio1.isSelected() && pocket==0) {
					
					betPrompt.setTextFill(Color.BLACK);
					betPrompt.setText("Please start a new game! You are out of money");
					dealAble=false;
					betPrompt.setScaleX(1.70);
					betPrompt.setScaleY(1.70);
					
				}
        		
	        	//if statement that removes the money from the pocket of place bet and updates according to the radio1 button
	        	if(radio1.isSelected() && pocket>=1 && oneBet) {
	        		
	            	
	        		dealAble=true;
	        		radioButtonPushed=true;
	        		betPrompt.setText(null);
	        		pocket-= BET_1;
	        		pocketBal.setText("Pocket : $"+ pocket);
	        		
				}
	        	//if statement that removes the money from the pocket of place bet and updates according to the radio2 button
				if(radio2.isSelected()&& pocket>=10 && oneBet) {
					
			 
					dealAble=true;
					radioButtonPushed=true;
					betPrompt.setText(null);
					pocket-= BET_10;
					pocketBal.setText("Pocket : $"+ pocket);
					
				}
				//if statement that removes the money from the pocket of place bet and updates according to the radio3 button
				if(radio3.isSelected() && pocket>=100 && oneBet) {
					
					
					dealAble=true;
					radioButtonPushed=true;
					betPrompt.setText(null);
					pocket-=BET_100;
					pocketBal.setText("Pocket : $"+ pocket);
					
				}
		


       
		    //if count equals 0 and radio button is pushed initialize Start animation    	
        	if(count == 0 && radioButtonPushed && dealAble) {
        		
        	//invoke refresh
        	refresh();
            
        	//invoke method to set view to element
    	    initializeStart(view0,0);
            initializeStart(view1,1);
            initializeStart(view2,2);
            initializeStart(view3,3);
            initializeStart(view4,4);
            
            //increment count
            count++;
            
            //set drawable to true
            drawAble=true;
    		}

        });
        
        //Set on action event first for button to hold card setting isHeld0 to true
        newGameBtn.setOnAction((event) -> {
        	
        		//invoke refresh method
        		refresh();
        		
        		//set radio button pushed to false
    			radioButtonPushed=false;
    			
    			//clear radio buttons
    			radio1.setSelected(false);
    			radio2.setSelected(false);
    			radio3.setSelected(false);
    			
    			//clear winCon text
    			winCon.setText(null);
    			
    			//reset money back to original
    			pocket = MONEY;
    			pocketBal.setText("Pocket : $"+pocket);
    			
    			//load cards
        		load();
            

        });
        
        //end of start
    }
    //Method to load images to array then shuffle deck
    public void load() {
    	
    	//clear all when loading
    	deck.clear();
    	cardVal.clear();
    	hand.clear();
    	
    	//count set back to 0
    	count = 0;
    	
    	//Instantiate backImg to back of card image
    	backImg = new Image("card/b1fv.png");
    	
    	//for loop to add images i to deck array list and add int i to cardVal list
        for (int i =1; i < 55; i++) {
      
          	cardVal.add(i);
        	deck.add(new Image("card/"+ i +".png"));
        	
        	
        }
        
        //shuffle cardVal and deck
        shuffle(cardVal, deck);
    	}
    
    //method to shuffle intList and imageList passed in.
	public void shuffle(ArrayList<Integer> intList, ArrayList<Image> imageList) { //shuffles the Integer objects
		
		// int temp to swap index
		int temp; 
		//for loop to shuffle the intList and imageList
		for(int i = 0; i<intList.size(); i++) {  
			
			//temp set to random times intlist size
			temp = (int)(Math.random()*intList.size());
			
			//assign int a to int list i
			Integer a = intList.get(i);
			
			//assign image b to image list i
			Image b = imageList.get(i);
			
			//set intlist i to element temp
			intList.set(i, intList.get(temp));
			
			//set image list i to image list temp
			imageList.set(i, imageList.get(temp));
			
			
			//set int list temp to a
			intList.set(temp, a);
			
			//set image list temp to b
			imageList.set(temp, b);
			
		}
	}
    //method to initialize image view and the index
  private void initialize(ImageView i , int x) {
	  
	  	//reset the opacity of all cards images
	  	view0.setOpacity(1);
		view1.setOpacity(1);
		view2.setOpacity(1);
		view3.setOpacity(1);
		view4.setOpacity(1);
	  
	  	//Instantiate translate
    	TranslateTransition translate = new TranslateTransition();
    	
    	//set node to image view i
    	translate.setNode(i);
    	
    	//set image to back of card
    	i.setImage(backImg);
    	
    	//set duration for animation
    	translate.setDuration(Duration.millis(1000));
    	
    	//set the Y to move card off screen
    	translate.setByY(1000);
    	
    	//play animation
    	translate.play();
    	
    	//when animation is finished the event sets the index of the card
    	translate.setOnFinished(event -> {
    		
    		
    		
    		//set image of image view to image at deck index
    		i.setImage(deck.get(x));
    		
    	
    		//play animation
        	translate.play();
        	
        	
        	//when the animation to set image is finished move card in from top
        	translate.setOnFinished(e -> {
        		
        		//set image view to Y to come from top 
        		translate.setFromY(-1000);
        		
        		//set duration of animation 
            	translate.setDuration(Duration.millis(500));
            	
            	//set to stop at Y
            	translate.setToY(0);         	
            		
            	//play animation
            	translate.play();
            	
            	
            	//shuffle cardVal and deck
            	shuffle(cardVal,deck);
            	
            	//set animation to null to stop repeating
            	translate.setOnFinished(null);
            	
            
         	});
        	
        
     	});
    	
    	
    }
  
  //Method when app starts to send cards in from the top
  private void initializeStart(ImageView i, int x) {
	  
	view0.setOpacity(1);
	view1.setOpacity(1);
	view2.setOpacity(1);
	view3.setOpacity(1);
	view4.setOpacity(1);
	  
	//Instantiate translate
  	TranslateTransition translate = new TranslateTransition();
  	
  	//set node to image view i
  	translate.setNode(i);
  	
  	//set image of image view to image at deck index
  	i.setImage(deck.get(x));
  	
  	//set image view to Y to come from top 
	translate.setFromY(-1000);
	
	//set duration of animation
	translate.setDuration(Duration.millis(500));
	
	//set to stop at Y
	translate.setToY(0);
	
  	//play animation
  	translate.play();

  }
  public void refresh() {
	  	//clear deck hand and cardVal arrays
	  	deck.clear();
	  	hand.clear();
	  	cardVal.clear();
	  	
	  	//set count to 0
		count=0;
		
		//empty image views
		view0.setImage(null);
		view1.setImage(null);
		view2.setImage(null);
		view3.setImage(null);
		view4.setImage(null);
		
		betPrompt.setText(null);
		
		//set booleans back to false
		isHeld0 = false;
		isHeld1 = false;
		isHeld2 = false;
		isHeld3 = false;
		isHeld4 = false;
		drawAble=false;
		dealAble=true;
		hasWon = false;
		
		//load cards
		load();
	  
  }
  	//method to return true if has flush or return false if not
  	public static boolean hasFlush(ArrayList<Integer> hand) {
	  
	  //array that holds the suit values from the player's hand
	  ArrayList<Integer> suit = new ArrayList<Integer>();
	  ArrayList<Integer> rank = new ArrayList<Integer>();
	  
	  	//adds suit(Hearts,Clubs,Spades,Diamonds) of card to Arraylist by getting the hand divide by 13 if card is king set suit
		for(int i = 0; i<5; i++) {
			
			//add suit to list i divided by 13
			suit.add((hand.get(i))/13);
			rank.add((hand.get(i)));
			
			if((hand.get(i))==13) {
				
				suit.set(i,0);
			}
			if((hand.get(i))==26) {
				
				suit.set(i,1);
			}
			if((hand.get(i))==39) {
			
			suit.set(i,2);
			}
			if((hand.get(i))==52) {
				
				suit.set(i,3);
				}
			
		}
		Collections.sort(rank);
		
		//if card suits are equal indicate a flush
		if(		  (suit.get(0)==suit.get(1))
				&&(suit.get(1)==suit.get(2))
				&&(suit.get(2)==suit.get(3) || rank.get(3)==53)
				&&(suit.get(3)==suit.get(4) || rank.get(4)==53 || rank.get(4)==54))  {
			
			//return true has flush
			return true;
			
		}
		//else
		else {
			
			//return false doesnt have flush
			return false;
		}
	}
  	//method to return true if has straight or return false if not
	public static boolean hasStraight(ArrayList<Integer> hand) {
		
		
		ArrayList<Integer> rank = new ArrayList<Integer>();//copy of player's hand
		ArrayList<Integer> rank2 = new ArrayList<Integer>();//copy of player's hand
		
		//adds rank(King,Queen,Jack,etc) of card to Arraylist by getting the hand %13
		for(int i = 0; i<5; i++) {
			if(hand.get(i)<=52) {
				
			//add rank to list the remainder of i%13 and add hand to rank2 array for jokers
			rank.add(hand.get(i)%13);
			rank2.add(hand.get(i));
			
			//if remainder is zero assign it to set i to element 13
			if((hand.get(i)%13)==0) {
				rank.set(i,13);
			}
			}
			//else add the jokers 53 and 54
			else {
				rank.add(i);
				rank2.add(i);
			}
			
		}
		//sorts the rank Arraylist
		Collections.sort(rank);
		
		//subracts card rank value from another card rank value if equals 1 indicates a straight
		if(		  ((rank.get(1)-rank.get(0))==1)
				&&((rank.get(2)-rank.get(1))==1)
				&&((rank.get(3)-rank.get(2))==1 || rank2.get(3)==53)
				&&((rank.get(4)-rank.get(3))==1 || rank2.get(4)==53 || rank2.get(4)==54))
		{
			
			//return true if all equal 1 or element 3 and 4 are jokers
			return true;
		}
		//else
		else {
			
			//return false
			return false;
		}
	}
	//method to return true if has four of a kind
	public static boolean hasFourofKind(ArrayList<Integer> hand) {
		
	//copy hand to rank array list
	ArrayList<Integer> rank = new ArrayList<Integer>();
	ArrayList<Integer> rank2 = new ArrayList<Integer>();
		
		//adds rank(King,Queen,Jack,etc) of card to Arraylist by getting the hand %13
		for(int i = 0; i<5; i++) {
			if(hand.get(i)<=52) {
				
			//add rank to list the remainder of i%13 and add hand to rank2 array for jokers
			rank.add(hand.get(i)%13);
			rank2.add(hand.get(i));
			
			//if remainder is zero assign it to set i to element 13
			if((hand.get(i)%13)==0) {
				rank.set(i,13);
			}
			}
			//else add the jokers 53 and 54
			else {
				rank.add(i);
				rank2.add(i);
			}
			
		}
		
		//sorts the Arraylist
		Collections.sort(rank);
		Collections.sort(rank2);
		
		//compare element for four of a kind
		if (
				
			 (rank.get(0)== rank.get(1) && rank.get(1)==rank.get(2) && rank.get(2)==rank.get(3)) 
			 ||  (rank.get(1)==rank.get(2) && rank.get(2)==rank.get(3) && rank.get(3)==rank.get(4))
			 ||	 ((rank.get(1)==rank.get(2) && rank.get(2)==rank.get(3)) && (rank.get(4)==53||rank.get(4)==54))
			 ||	 ((rank.get(0)==rank.get(1) && rank.get(1)==rank.get(2)) && (rank.get(4)==53||rank.get(4)==54))
			 ||  (rank.get(1)==rank.get(2) && (rank2.get(3)==53 && rank2.get(4)==54))
			 ||  (rank.get(0)==rank.get(1) && (rank2.get(3)==53 && rank2.get(4)==54))
			
			) {
			//return true
			return true;
		}
		//else
		else {
			//return false
			return false;
		}
		
	}
	//method to return true if has three of a kind
	public static boolean has3ofKind(ArrayList<Integer> hand) {
		
	//copy hand to rank array list
	ArrayList<Integer> rank = new ArrayList<Integer>();
	ArrayList<Integer> rank2 = new ArrayList<Integer>();
		
		//adds rank(King,Queen,Jack,etc) of card to Arraylist by getting the hand %13
		for(int i = 0; i<5; i++) {
			if(hand.get(i)<=52) {
				
			//add rank to list the remainder of i%13 and add hand to rank2 array for jokers
			rank.add(hand.get(i)%13);
			rank2.add(hand.get(i));
			
			//if remainder is zero assign it to set i to element 13
			if((hand.get(i)%13)==0) {
				rank.set(i,13);
			}
			}
			//else jokers add 53 and 54 to both arrays
			else {
				rank.add(hand.get(i));
				rank2.add(hand.get(i));
			}
			
		}
		
		//sorts the Arraylist
		Collections.sort(rank);
		Collections.sort(rank2);
		
		//compare for 3 of a kind
		if(
				(rank.get(0)== rank.get(1) && rank.get(1)==rank.get(2) && rank.get(3)!=rank.get(0) && rank.get(4)!=rank.get(0) && rank.get(3)!=rank.get(4))
			 || (rank.get(1)==rank.get(2) && rank.get(2)==rank.get(3) && rank.get(0)!= rank.get(1) && rank.get(4)!= rank.get(1) && rank.get(0) != rank.get(4)) 
			 ||	(rank.get(2)==rank.get(3) && rank.get(3)==rank.get(4) && rank.get(0)!= rank.get(2) && rank.get(1)!= rank.get(2) && rank.get(0) != rank.get(1))
			 ||	(rank.get(0)==rank.get(1) && rank2.get(4)==53 || rank2.get(4)==54)
			 ||	(rank.get(1)==rank.get(2) && rank2.get(4)==53 || rank2.get(4)==54)
			 ||	(rank.get(2)==rank.get(3) && rank2.get(4)==53 || rank2.get(4)==54) 
			 ||	(rank2.get(3)==53 && rank2.get(4)==54)
			
			) {
			
			//return true
			return true;
		}
		//else
		else {
			//return false
			return false;
		}
		
	}
	
	//method to return true if has Royal flush with jokers
	public static boolean hasRoyalFlushJoker(ArrayList<Integer> hand) {
		
		//copy hand to rank array list
		ArrayList<Integer> rank = new ArrayList<Integer>();
			
			//adds rank from hand
			for(int i = 0; i<5; i++) {
				
				rank.add(hand.get(i));
				
				}
		
		//sorts the Arraylist
		Collections.sort(rank);
		
		//compares for Royal flush with jokers
		if (
				//one joker spades
				((rank.get(0)==1 && rank.get(1)==10 && rank.get(2)==11 && rank.get(3)==12) && (rank.get(4)==53 || rank.get(4)==54)) 
			 ||	((rank.get(0)==1 && rank.get(1)==10 && rank.get(2)==11 && rank.get(3)==13) && (rank.get(4)==53 || rank.get(4)==54)) 
			 ||	((rank.get(0)==1 && rank.get(1)==10 && rank.get(2)==12 && rank.get(3)==13) && (rank.get(4)==53 || rank.get(4)==54))
			 ||	((rank.get(0)==1 && rank.get(1)==11 && rank.get(2)==12 && rank.get(3)==13) && (rank.get(4)==53 || rank.get(4)==54))
			 ||	((rank.get(0)==10 && rank.get(1)==11 && rank.get(2)==12 && rank.get(3)==13) && (rank.get(4)==53 || rank.get(4)==54))
			 
			 //two jokers spades
			 ||	((rank.get(0)==1 && rank.get(1)==10 && rank.get(2)==11) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==1 && rank.get(1)==10 && rank.get(2)==12) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==1 && rank.get(1)==10 && rank.get(2)==13) && (rank.get(3)==53 && rank.get(4)==54)) 
			 ||	((rank.get(0)==1 && rank.get(1)==11 && rank.get(2)==12) && (rank.get(3)==53 && rank.get(4)==54)) 
			 ||	((rank.get(0)==1 && rank.get(1)==11 && rank.get(2)==13) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==1 && rank.get(1)==12 && rank.get(2)==13) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==10 && rank.get(1)==11 && rank.get(2)==12) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==10 && rank.get(1)==11 && rank.get(2)==13) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==10 && rank.get(1)==12 && rank.get(2)==13) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==11 && rank.get(1)==12 && rank.get(2)==13) && (rank.get(3)==53 && rank.get(4)==54))
			 
			 //one joker hearts
			 ||	((rank.get(0)==14 && rank.get(1)==23 && rank.get(2)==24 && rank.get(3)==25) && (rank.get(4)==53 || rank.get(4)==54)) 
			 ||	((rank.get(0)==14 && rank.get(1)==23 && rank.get(2)==24 && rank.get(3)==26) && (rank.get(4)==53 || rank.get(4)==54)) 
			 ||	((rank.get(0)==14 && rank.get(1)==23 && rank.get(2)==25 && rank.get(3)==26) && (rank.get(4)==53 || rank.get(4)==54))
			 ||	((rank.get(0)==14 && rank.get(1)==24 && rank.get(2)==25 && rank.get(3)==26) && (rank.get(4)==53 || rank.get(4)==54))
			 ||	((rank.get(0)==23 && rank.get(1)==24 && rank.get(2)==25 && rank.get(3)==26) && (rank.get(4)==53 || rank.get(4)==54))
			 
			 //two jokers hearts
			 ||	((rank.get(0)==14 && rank.get(1)==23 && rank.get(2)==24) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==14 && rank.get(1)==23 && rank.get(2)==25) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==14 && rank.get(1)==23 && rank.get(2)==26) && (rank.get(3)==53 && rank.get(4)==54)) 
			 ||	((rank.get(0)==14 && rank.get(1)==24 && rank.get(2)==25) && (rank.get(3)==53 && rank.get(4)==54)) 
			 ||	((rank.get(0)==14 && rank.get(1)==24 && rank.get(2)==26) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==14 && rank.get(1)==25 && rank.get(2)==26) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==23 && rank.get(1)==24 && rank.get(2)==25) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==23 && rank.get(1)==24 && rank.get(2)==26) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==23 && rank.get(1)==25 && rank.get(2)==26) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==24 && rank.get(1)==25 && rank.get(2)==26) && (rank.get(3)==53 && rank.get(4)==54))
			 
			 //one joker diamonds
			 ||	((rank.get(0)==27 && rank.get(1)==36 && rank.get(2)==37 && rank.get(3)==38) && (rank.get(4)==53 || rank.get(4)==54)) 
			 ||	((rank.get(0)==27 && rank.get(1)==36 && rank.get(2)==37 && rank.get(3)==39) && (rank.get(4)==53 || rank.get(4)==54)) 
			 ||	((rank.get(0)==27 && rank.get(1)==36 && rank.get(2)==38 && rank.get(3)==39) && (rank.get(4)==53 || rank.get(4)==54))
			 ||	((rank.get(0)==27 && rank.get(1)==37 && rank.get(2)==38 && rank.get(3)==39) && (rank.get(4)==53 || rank.get(4)==54))
			 ||	((rank.get(0)==36 && rank.get(1)==37 && rank.get(2)==38 && rank.get(3)==39) && (rank.get(4)==53 || rank.get(4)==54))
			 
			 //two jokers diamonds
			 ||	((rank.get(0)==27 && rank.get(1)==36 && rank.get(2)==37) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==27 && rank.get(1)==36 && rank.get(2)==38) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==27 && rank.get(1)==36 && rank.get(2)==39) && (rank.get(3)==53 && rank.get(4)==54)) 
			 ||	((rank.get(0)==27 && rank.get(1)==37 && rank.get(2)==38) && (rank.get(3)==53 && rank.get(4)==54)) 
			 ||	((rank.get(0)==27 && rank.get(1)==37 && rank.get(2)==39) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==27 && rank.get(1)==38 && rank.get(2)==39) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==36 && rank.get(1)==37 && rank.get(2)==38) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==36 && rank.get(1)==37 && rank.get(2)==39) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==36 && rank.get(1)==38 && rank.get(2)==39) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==37 && rank.get(1)==38 && rank.get(2)==39) && (rank.get(3)==53 && rank.get(4)==54))

			 //one joker clubs
			 ||	((rank.get(0)==40 && rank.get(1)==49 && rank.get(2)==50 && rank.get(3)==51) && (rank.get(4)==53 || rank.get(4)==54)) 
			 ||	((rank.get(0)==40 && rank.get(1)==49 && rank.get(2)==50 && rank.get(3)==52) && (rank.get(4)==53 || rank.get(4)==54)) 
			 ||	((rank.get(0)==40 && rank.get(1)==49 && rank.get(2)==51 && rank.get(3)==52) && (rank.get(4)==53 || rank.get(4)==54))
			 ||	((rank.get(0)==40 && rank.get(1)==50 && rank.get(2)==51 && rank.get(3)==52) && (rank.get(4)==53 || rank.get(4)==54))
			 ||	((rank.get(0)==49 && rank.get(1)==50 && rank.get(2)==51 && rank.get(3)==52) && (rank.get(4)==53 || rank.get(4)==54))
			 //two jokers clubs
			 ||	((rank.get(0)==40 && rank.get(1)==49 && rank.get(2)==50) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==40 && rank.get(1)==49 && rank.get(2)==51) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==40 && rank.get(1)==49 && rank.get(2)==52) && (rank.get(3)==53 && rank.get(4)==54)) 
			 ||	((rank.get(0)==40 && rank.get(1)==50 && rank.get(2)==51) && (rank.get(3)==53 && rank.get(4)==54)) 
			 ||	((rank.get(0)==40 && rank.get(1)==50 && rank.get(2)==52) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==40 && rank.get(1)==51 && rank.get(2)==52) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==49 && rank.get(1)==50 && rank.get(2)==51) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==49 && rank.get(1)==50 && rank.get(2)==52) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==49 && rank.get(1)==51 && rank.get(2)==52) && (rank.get(3)==53 && rank.get(4)==54))
			 ||	((rank.get(0)==50 && rank.get(1)==51 && rank.get(2)==52) && (rank.get(3)==53 && rank.get(4)==54))
			  
			 
			) {
			
			//return true
			return true;
		}
		//else
		else {
			//return false
			return false;
		}
		
	}
	
	//method to return true if has natural Royal flush
	public static boolean hasRoyalFlush(ArrayList<Integer> hand) {
		
		//copy hand to rank array list
		ArrayList<Integer> rank = new ArrayList<Integer>();
			
			//adds hand to rank array
			for(int i = 0; i<5; i++) {
				
				rank.add(hand.get(i));
				
				}
		
		//sorts the Arraylist
		Collections.sort(rank);
		
		//compare the elements and card values from hand
		if (
				(rank.get(0)==1 && rank.get(1)==10 && rank.get(2)==11 && rank.get(3)==12 && rank.get(4)==13)
			 || (rank.get(0)==14 && rank.get(1)==23 && rank.get(2)==24 && rank.get(3)==25 && rank.get(4)==26) 
			 ||	(rank.get(0)==27 && rank.get(1)==36 && rank.get(2)==37 && rank.get(3)==38 && rank.get(4)==39)
			 ||	(rank.get(0)==40 && rank.get(1)==49 && rank.get(2)==50 && rank.get(3)==51 && rank.get(4)==52)
			  
			 
			) {
			
			//return true
			return true;
		}
		//else
		else {
			//return false
			return false;
		}
		
	}
	
	

	//method to return true if has 5 of a kind compares array rank then compares the last 2 elements of rank2 if they have jokers
	public static boolean hasFiveofKind(ArrayList<Integer> hand) {
		
			//copy 2 hands to rank array list to implement jokers
			ArrayList<Integer> rank = new ArrayList<Integer>();
			ArrayList<Integer> rank2 = new ArrayList<Integer>();
		
				//adds rank(King,Queen,Jack,etc) of card to Arraylist by getting the hand %13 to 52 once we get to 52 add the jokers in
				for(int i = 0; i<5; i++) {
					if(hand.get(i)<=52) {
						
					//add rank to list the remainder of i%13 and add hand to rank2 array for jokers
					rank.add(hand.get(i)%13);
					rank2.add(hand.get(i));
					
					//if remainder is zero assign it to set i to element 13 for king
					if((hand.get(i)%13)==0) {
						rank.set(i,13);
					}
					}
					//if jokers add 53 and 54 to both arrays
					else {
						
						rank.add(hand.get(i));
						rank2.add(hand.get(i));
					}
					
				}
		
			//sorts the Arraylists
			Collections.sort(rank);
			Collections.sort(rank2);
		
		//compare for five of kind
		if (
				
				(rank.get(0)==rank.get(1) && rank.get(1)==rank.get(2) && rank.get(2)==rank.get(3) && ((rank.get(3)==rank.get(4)) || (rank2.get(4)==53 || rank2.get(4)==54)))
				||	(rank.get(0)==rank.get(1) && rank.get(1)==rank.get(2) && (rank2.get(3)==53 && rank2.get(4)==54))
			 
			
			) {
			
			//return true
			return true;
		}
		//else
		else {
			//return false
			return false;
		}
		
	}
	//method to return true if has Fullhouse
	public static boolean hasFullHouse(ArrayList<Integer> hand) {
				
			//copy hand to rank array list
			ArrayList<Integer> rank = new ArrayList<Integer>();
			ArrayList<Integer> rank2 = new ArrayList<Integer>();
		
			//adds rank(King,Queen,Jack,etc) of card to Arraylists by getting the hand %13 to 52 once we get to 52 add the jokers in
			for(int i = 0; i<5; i++) {
				if(hand.get(i)<=52) {
					
				//add rank to list the remainder of i%13 and add hand to rank2 array for jokers
				rank.add(hand.get(i)%13);
				rank2.add(hand.get(i));
				
				//if remainder is zero assign it to set i to element 13 for king
				if((hand.get(i)%13)==0) {
					rank.set(i,13);
				}
				}
				//else jokers add 53 and 54 to both arrays
				else {
					rank.add(hand.get(i));
					rank2.add(hand.get(i));
				}
		
	}
				
				//sorts the Arraylists
				Collections.sort(rank);
				Collections.sort(rank2);
				//compare elements for full house
				if (
				
					(rank.get(0)== rank.get(1) && rank.get(1)==rank.get(2) && (rank.get(3)==rank.get(4) || (rank2.get(4)==53) || (rank2.get(4)==54)))
				 || (rank.get(4)== rank.get(3) && rank.get(3)==rank.get(2) && rank.get(1)==rank.get(0)) 
				 || (rank.get(0)==rank.get(1) && rank.get(2)==rank.get(3) && (rank2.get(3)==53 && rank2.get(4)==54))
				 || (rank.get(1)==rank.get(2) && rank.get(2)==rank.get(3) && (rank2.get(4)==54))
				 ||	(rank.get(0)==rank.get(1) && rank.get(2)==rank.get(3) && (rank2.get(4)==54))
				 ||	(rank.get(0)==rank.get(1) && (rank2.get(3)==53 && rank2.get(4)==54))
				 ||	(rank.get(1)==rank.get(2) && (rank2.get(3)==53 && rank2.get(4)==54))
				 
				
			
			) {
			
			//return true
			return true;
		}
		//else
		else {
			//return false
			return false;
		}
		
	}
	  //Method for animation to display gif to move back and forth
	  private void bigWin() {
		  
		   	 //set image by using URL
	   	  	 Image exitImage = new Image("https://casinobilbaocity6d.com/img/bigwin2.gif");
	   	  	 
	   	  	 //Instantiate translate transition
	   	  	 TranslateTransition translate = new TranslateTransition();
	   	  	 
	   	  	 //setNode to payView
	   	  	 translate.setNode(payView);
	   	  	 
	   	  	 //set scale of exitView node
	   	  	 payView.setScaleX(1);
	   	  	 payView.setScaleY(1);
	   	  	 
	   	  	 //set Image of node
	   	  	 payView.setImage(exitImage);
	   	  	 
	   	  	 //set delay to show for 2 seconds
	   	  	 translate.setDelay(Duration.seconds(2));
	
	   	  	 //play animation
	   	  	 translate.play();
	   	  	 
	   	  	 //set payView to null after animation
	   	  	 translate.setOnFinished(e->{
	   	  		 
	   	  		 //set payView back to payTable image
	   	  		 payView.setImage(payTable);
	   	  		
	   	  		 
	   	  	 });
	   	  	 
	   	  	 
		  
	  }
	
	
    
  	//main method to start program
    public static void main(String[] args) {
  
    	//Invoke launch method
        launch(args);
        
    }
}
