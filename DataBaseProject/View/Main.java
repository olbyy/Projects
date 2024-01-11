package View;
import Controller.*;
import Model.*;

import java.util.Scanner;

//Test class
public class Main {
	
	//create login variables
	public static String user = "username";
	public static String userPassword = "password";
	public static boolean adminStatus = false;
	public static boolean loginSucessful = false;
	
		
		//main method
		public static void main(String[] args) throws Exception {
			
			
		//load database
		Database.loadDatabase();
		
		
		//create scanner input
		Scanner input = new Scanner(System.in);
		
		//set system is running
		boolean running = true;
		
		//display program and login info
		System.out.println("Welcome to Schedule Program!");
		System.out.println("Enter login type. (Admin or User), or enter 'Quit' to quit.\n");				
				
		
		
		//while system is running
		while(running)
		{
			String userInput = input.nextLine();	// gets user input for login type
		//if user inputs quit exit software
		if(userInput.equalsIgnoreCase("Quit")) {
			System.out.println("Software Exiting");
			break;
		}
		//else if user in
		else if(userInput.equalsIgnoreCase("User")) {
			
			//while system is running
			while(running) {
				
			//create controllers
			RoomController roomController = new RoomController();
			CourseController courseController = new CourseController();
			
			//display options
			System.out.println("Please choose an option\n");
			System.out.println("1: Display Room Information");
			System.out.println("2: Display Number of Students in Course");
			System.out.println("3: Generate report");
			System.out.println("4: Quit");
			
			//get user input
			userInput = input.next();
			
			//switch case based on user input
			switch(userInput) {
			
				//case 1
				case "1":
					System.out.println(roomController.getAll());
					
					break; //break
				
				//case 2
				case "2":
					System.out.println("Please enter the Course Type\n");
					String courseType = input.next();
					System.out.println("Please enter the Course Level\n");
					String courseLevel = input.next();
					
					System.out.println("Number of students in "+ courseType + " "+ courseLevel + " = " +courseController.getStudents(courseType, courseLevel) + "\n");
					
					break; //break
					
				//case 3
				case "3":
					
					//generate report
					System.out.println(roomController.getAll());
					System.out.println(courseController.getAll());
					
					break; //break
					
				//case 4
				case "4":
					
					//display software exiting
					System.out.println("Software Exiting");
					
					//set running to false
					running = false;
					
					break; //break
					
				//default case
				default:
					//display 
					System.out.println("Incorrect choice. Please enter a correct choice");
					break; //break
					
			}
		}
		}
		
		//else if user input is admin
		else if(userInput.equalsIgnoreCase("Admin")) {
			
			//display please enter name
			System.out.println("Please Enter User Name\n");
			
			//get user input
			userInput = input.next();
		//else
		}else {
			
		System.out.println("Invalid input: Please enter valid input");
		
		
		}
		//if user input is user
		if(userInput.equals(user)) {
					
					//display enter password
					System.out.println("Please Enter Password\n");
					
					//get user input
					userInput = input.next();
					
		}
		
		// user input is user password
		if(userInput.equals(userPassword)) {
			
				//while system running do
				while(running) {
					
							//create controllers
							RoomController roomController = new RoomController();
							CourseController courseController = new CourseController();
							
							//display options
							System.out.println("Please choose an option\n");
							System.out.println("1: Display Room Information");
							System.out.println("2: Display Number of Students in Course");
							System.out.println("3: Generate report");
							System.out.println("4: Add students to course");
							System.out.println("5: Add a room");
							System.out.println("6: Add a course");
							System.out.println("7: Assign a Course to a Room");
							System.out.println("8: Quit");
							
							//grab userinput
							userInput = input.next();
							
							//switch case based on user input
							switch(userInput) {
							
								//case 1 Display room info
								case "1":
									
									//display room info
									System.out.println(roomController.getAll());	
									
									break; //break
									
								//case 2 Display Number of Students in Course
								case "2":
									
									//display please enter the course type
									System.out.println("Please enter the Course Type\n");
									
									//get user input
									String courseType = input.next();
									
									//display pleae enter the course level
									System.out.println("Please enter the Course Level\n");
									
									//get user input
									String courseLevel = input.next();
									
									//display results
									System.out.println("Number of students in "+ courseType + " "+ courseLevel + " = " + courseController.getStudents(courseType, courseLevel) + "\n");
									
									
									break; //break
								
								//case 3 Generate report
								case "3":
									
									//generate report
									System.out.println(roomController.getAll());
									System.out.println(courseController.getAll());
									
									break; //break
									
								//case 4 Add students to course
								case "4":
									
									int numberOfStudents =0;
									
									System.out.println("Please enter the Course Type\n");
									courseType = input.next();
									
									System.out.println("Please enter the Course Level\n");
									courseLevel = input.next();
									
									System.out.println("Please enter the Number of Student to Add\n");
									numberOfStudents = input.nextInt();
									
									courseController.registerStudents(courseType, courseLevel, numberOfStudents);
									
									break; //break
									
								//case 5
								case "5":
									
									//instantiate room object
									Room r;
									
									//display please enter the room type
									System.out.println("Please enter the Room Type\n");
									
									
									//get room 
									String roomType = input.nextLine();
									
									//while room type is not lab and room type is not room
									while(!roomType.equalsIgnoreCase("Lab") || !roomType.equalsIgnoreCase("Room")) {
										
										
										roomType = input.next();
								
										
										if(roomType.equalsIgnoreCase("Lab") || roomType.equalsIgnoreCase("Room")) {
											
											break; //break
											
										}
										System.out.println("Please insert a valid type.");
									}
									
									//display please enter room number
									System.out.println("Please enter the Room Number\n");
									
									//get user input
									String roomNumber = input.next();
									
									//display please enter room size
									System.out.println("Please enter the Room Size\n");
									
									//get user input
									int roomSize = input.nextInt();
									
									//create room object with parameters
									r = new Room(roomType, roomNumber, roomSize, "none");
									
									//room controller calls insert method passing in r
									roomController.insert(r);
									
									break; //break
									
								//case 6
								case "6":
									
									//instantiate course object
									Course c;
									
									
									System.out.println("Please enter the Course Type\n");
									
									//get user input
									courseType = input.next();
									
									System.out.println("Please enter the Course Level\n");
									
									//get user input
									courseLevel = input.next();
									
									System.out.println("Please enter the Course Size\n");
									
									//get user input
									int studentNumber = input.nextInt();
									
									System.out.println("Is this class a Lab?");
									
									//get user input
									String isLab = input.next();
									
									//create course object with parameters
									c = new Course(courseType, courseLevel, studentNumber, isLab, "none");
									
									//course controller calls insert method passing in c
									courseController.insert(c);
									
									break; //break
									
								//case 7	
								case "7":
									
									System.out.println("Please enter the Course Type\n");
									
									//get user input
									courseType = input.next();
									
									//display please enter course level
									System.out.println("Please enter the Course Level\n");
									
									//get user input
									courseLevel = input.next();
									
									
									//display please enter room type
									System.out.println("Please enter the Room Type(Lab/Room)\n");
									
									//get user input
									roomType = input.next();
									
									
									//display please enter room number
									System.out.println("Please enter the Room Number\n");
									
									//get user input
									roomNumber = input.next();
									
									//course controller calls assignRoom method passing in parameters
									courseController.assignRoom(courseType, courseLevel, roomType, roomNumber);
									
									break; //break
									
									
								//case 8
								case "8":
									
									//Display exit software and set running to false
									System.out.println("Software Exiting");
									
									//set running to false
									running = false;
									
									break; //break
								
								//default case
								default:
									
									//Display to enter correct choice
									System.out.println("Please enter a correct choice");
									
									break; //break
									
							}
					}
				}
			}
		}		
}
		
