/*
 * Dillon Olbrich L20243449
 * COSC 4310 Cache Project
 * Due Date: November 13, 2023
 * This program implements a simple cache with the options of direct mapping, set associative (2-way/4-way), and fully associative mapping.
 * The user will select the options and give inputs on the number of blocks which determines the block addresses or sets.
 * The computer will fill the cache based on the users input and keep track of the miss rate
 * The results of the cache are presented to the user after computations as well as the miss rate
 * 
 * 
 */

import java.util.*;

public class Cache {
	
	/*Global variables*/
	public static double missRate = 0;
	public static double missCount = 0;
	public static int numOfSets = 0;
	public static int setNum = 0;
	public static int lru = Integer.MAX_VALUE;

	/* main */
	public static void main(String[] args) {
		
	startProgram();
			
	}/* end of main method */
	//method for direct mapping cache
	public static void directMap(LinkedList<Integer> addr, int numOfBlocks) {
		
		
		double addrSize = addr.size();

		
		LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();
		
		for(int i = 0; i < addr.size(); i++) {
			
			int temp = addr.get(i);	//get address of entry
			int blockAddr = temp % numOfBlocks; //calculate block address based on entry value mod number of blocks

			if(cache.get(blockAddr) == null || !cache.containsValue(temp)) { //if cache is empty or does not contain or entry otherwise its a hit.
				missCount++;	
				cache.put(blockAddr, temp);
			}
		}
		
		missRate = Double.parseDouble(String.format("%.2f", (double) (missCount / addrSize * 100))); //format miss rate and calculate missrate = number of misses / total entries.
		
		System.out.println("\n*****Results after Entries*****\nIndex\t\tContent");
		
		for(int i = 0; i < addr.size(); i++) {
			if(cache.get(i) == null) {
				System.out.println(i + "\t\t" + "------");
			}
			else {
				System.out.println(i + "\t\t" + "Mem["+ cache.get(i)+"]");
			}
		}
		
		System.out.println("\nMiss rate = " + missRate + " % "); //print miss rate
		
	}
	//method for set assoc type cache. implements two and four way set associative
	public static void setAssoc(LinkedList<Integer> addr, int numOfBlocks, int setAssocOpt, int replacementPolicy) {
		
		
		double addrSize = addr.size();
		
		LinkedHashMap<Integer, int[]> cache = new LinkedHashMap<>(); //cache holds the set number (integer) and the ways of that set (int[number of ways])
		LinkedList<Integer> lruList = new LinkedList<>(); //lruList to keep track of indexes used LRU = head of list
		
		if(replacementPolicy == 1) { //LRU replacement policy chosen
		
		if(setAssocOpt == 1){ //two way
			
			numOfSets = (int) Math.ceil((double) numOfBlocks / 2); //get number of sets if number of blocks is odd round up.
			
			//fill sets with empty array of -1(empty) for two ways
			for(int i = 0; i < numOfSets; i++) {
				cache.put(i, new int[] {-1, -1});
			}
			
			for(int i = 0; i < addr.size(); i++) {
				
				int temp = addr.get(i);	//get address of entry
				setNum = addr.get(i) % numOfSets; //(Block number) modulo (Number of sets in the cache)
				
				
				int[] temparr = cache.get(setNum); //get set from cache to check and update
				if(temparr[0] == temp || temparr[1] == temp) { //if set contains the data
					lru = updateLRU(temp, lruList);
					continue; //do nothing Hit! skip to next iteration
				}
				else if(temparr[0] == -1 && temparr[1] == -1) { //if both are empty place in first way
					temparr[0] = temp;
					lru = updateLRU(temp, lruList);
					missCount++;
				}
				else if(temparr[0] > -1 && temparr[1] == -1) { //if way 0 is full place in way 1 if empty
					temparr[1] = temp;
					lru = updateLRU(temp, lruList);
					missCount++;
				}
				else if(temparr[1] > -1 && temparr[0] == -1) { //if way 1 is full place in way 0 if empty
					temparr[0] = temp;
					lru = updateLRU(temp, lruList);
					missCount++;
				}
				else { //else both are full, find and replace LRU
					//search for LRU index
					int swapIndex = 0;
					for(int j = 0; j < 2; j++) {
						if(lru == temparr[j]) {
							swapIndex = j; //set swapIndex to found LRU 
						}
					}
					temparr[swapIndex] = temp; //LRU found, replace
					lru = updateLRU(temp, lruList); //update LRU
					missCount++;
				}
				cache.put(setNum, temparr); //update set/ways in cache
			}
			missRate = Double.parseDouble(String.format("%.2f", (double) ((missCount / addrSize) * 100))); //format miss rate and calculate missrate = number of misses / total entries.
			
			System.out.println("\n*****Results after Entries******\nSet\t\tWay 0\t\tWay 1");
			
			//print cache results
			
					for(int i = 0; i < numOfSets; i++) {
						int[] tempVals = cache.get(i);
						System.out.print(i + "\t\t");
						if(tempVals[0] < 0) {
							System.out.print("------" + "\t\t");
						}
						else {
							System.out.print("Mem["+ tempVals[0] +"]" + "\t\t");
						}
						if(tempVals[1] < 0) {
							System.out.print("------\n");
						}
						else {
							System.out.print("Mem["+ tempVals[1] +"]" + "\n");
						}
				}
			
		}//end of two way
		
		if(setAssocOpt == 2){//four way
			
			numOfSets = (int) Math.ceil((double) numOfBlocks / 4);	//get number of sets if number of blocks is odd round up.

			//fill sets with empty array of -1(empty) for four ways
			for(int i = 0; i < numOfSets; i++) {
				cache.put(i, new int[] {-1, -1, -1, -1});
			}
			
			for(int i = 0; i < addr.size(); i++) {
				
				int temp = addr.get(i);	//get address of entry
				
				setNum = addr.get(i) % numOfSets; //(Block number) modulo (Number of sets in the cache)
				
				int[] temparr = cache.get(setNum); //get set from cache to check and update
			
				if(temparr[0] == temp || temparr[1] == temp || temparr[2] == temp || temparr[3] == temp) { //if set contains the data from address
					lru = updateLRU(temp, lruList);
					continue; //do nothing Hit! skip to next iteration
				}
				if(temparr[0] == -1 && temparr[1] == -1 && temparr[2] == -1 && temparr[3] == -1) {
					temparr[0] = temp;
					lru = updateLRU(temp, lruList);
					missCount++;
				}
				else if(temparr[0] > -1 && (temparr[1] == -1 || temparr[2] == -1 || temparr[3] == -1)) { //if all are empty place in way 0
					if(temparr[1] == -1) {
						temparr[1] = temp;
					}
					else if(temparr[2] == -1) {
						temparr[2] = temp;
					}
					else {
						temparr[3] = temp;
					}
					lru = updateLRU(temp, lruList);
					missCount++;
				}
				else if(temparr[1] > -1 && (temparr[0] == -1 || temparr[2] == -1 || temparr[3] == -1)) { //if way 1 is full place in empty way
					if(temparr[0] == -1) {
						temparr[0] = temp;
					}
					else if(temparr[2] == -1) {
						temparr[2] = temp;
					}
					else {
						temparr[3] = temp;
					}
					lru = updateLRU(temp, lruList);
					missCount++;
				}
				else if(temparr[2] > -1 && (temparr[0] == -1 || temparr[1] == -1 || temparr[3] == -1)) { //if way 2 is full place in empty way
					if(temparr[0] == -1) {
						temparr[0] = temp;
					}
					else if(temparr[1] == -1) {
						temparr[1] = temp;
					}
					else {
						temparr[3] = temp;
					}
					lru = updateLRU(temp, lruList);
					missCount++;
				}
				else if(temparr[3] > -1 && (temparr[0] == -1 || temparr[1] == -1 || temparr[2] == -1)) { //if way 3 is full place in empty way
					if(temparr[0] == -1) {
						temparr[0] = temp;
					}
					else if(temparr[1] == -1) {
						temparr[1] = temp;
					}
					else {
						temparr[2] = temp;
					}
					lru = updateLRU(temp, lruList);
					missCount++;
				}
				else { //else all are full, find and replace LRU
					//search for LRU index
					int swapIndex = 0;
					for(int j = 0; j < 4; j++) {
						if(lru == temparr[j]) {
							swapIndex = j;//set swapIndex to found LRU 
						}
					}
					temparr[swapIndex] = temp;	//LRU found, replace
					lru = updateLRU(temp, lruList);	//update LRU
					missCount++;
				}
				
				cache.replace(setNum, temparr); //update set/ways in cache
			
			}
			missRate = Double.parseDouble(String.format("%.2f", (double) ((missCount / addrSize) * 100))); //format miss rate and calculate missrate = number of misses / total entries.
			
			System.out.println("\n*****Results after Entries*****\nSet\t\tWay 0\t\tWay 1\t\tWay 2\t\tWay 3");
			
			//print cache results
			
			for(int i = 0; i < numOfSets; i++) {
				int[] tempVals = cache.get(i);
			
						System.out.print(i + "\t\t");
						if(tempVals[0] < 0) {
							System.out.print("------" + "\t\t");
						}
						else {
							System.out.print("Mem["+ tempVals[0] +"]" + "\t\t");
						}
						if(tempVals[1] < 0) {
							System.out.print("------" + "\t\t");
						}
						else {
							System.out.print("Mem["+ tempVals[1] +"]" + "\t\t");
						}
						if(tempVals[2] < 0) {
							System.out.print("------" + "\t\t");
						}
						else {
							System.out.print("Mem["+ tempVals[2] +"]" + "\t\t");
						}
						if(tempVals[3] < 0) {
							System.out.print("------\n");
						}
						else {
							System.out.print("Mem["+ tempVals[3] +"]" + "\n");
						}
				}
			
		}//end of four way
		

		System.out.println("\nMiss rate = " + missRate + " % "); //print miss rate
		
	}

	}
	//method for implements fully associative cache mapping
	public static void fullyAssoc(LinkedList<Integer> addr, int numOfBlocks, int setAssocOpt, int replacementPolicy) {
		
		double addrSize = addr.size();
		
		LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>(); //original cache
		LinkedHashMap<Integer, Integer> swapped = new LinkedHashMap<>(); //map to hold swapped values when cache spot is full
		
		
		if(replacementPolicy == 1) { //LRU replacement policy chosen
			
		int lru = Integer.MAX_VALUE;
		LinkedList<Integer> lruList = new LinkedList<>(); //lruList to keep track of indexes used LRU = head of list
		
		//loop for all address entries
		for(int i = 0; i < addr.size(); i++) {
			
		int temp = addr.get(i);	//get address entry
		
		int blockAddr = temp % numOfBlocks; //set block address based on given address in decimal
		
		

		if(cache.get(blockAddr) == null) { //cache block is empty so we place data at address
			
			missCount++; //Miss, increment miss count
	
			cache.put(blockAddr, temp);
			
			//update LRU
			lru = updateLRU(blockAddr,lruList);
			
		}
		else if(cache.get(blockAddr) != null && !cache.containsValue(temp)) { //if block contains data find a spot
			
			missCount++; //Miss, increment miss count
			
			int count = 1; //count is 1 because first address is full
			
			int holdBlock = blockAddr; //hold current block address to keep track of
		
			while(cache.get(blockAddr) != null && count < numOfBlocks) { //while cache entry isnt empty increment count until find a spot for all cache blocks
				
				blockAddr = (temp + count) % numOfBlocks; //set block address to temp + count, count increments until a spot is found in cache
				count++;
				
			}
			if(cache.get(blockAddr) == null) { //spot found in cache
				
				cache.put(blockAddr, temp); //put the data in cache at new empty address
				swapped.put(holdBlock, blockAddr); //hold the address that is swapped and set a value for it.
				
				//update LRU
				lru = updateLRU(blockAddr, lruList);
			}
			else {
				
				blockAddr = lru; //cache is full, set new address to LRU
				cache.put(blockAddr, temp); // put data in new cache address
				
				//update LRU
				lru = updateLRU(blockAddr, lruList);
				
			}
			
		}
		else { //else HIT, update LRU
			
			//update LRU
			lru = updateLRUswapped(blockAddr, lruList, swapped);
			
		}
			
		}
		
		missRate = Double.parseDouble(String.format("%.2f", (double) ((missCount / addrSize) * 100))); //format miss rate and calculate missrate = number of misses / total entries.
		
		System.out.println("\n*****Results after Entries*****\nIndex\t\tWay\t\tContent");
		//print cache results
		for(int i = 0; i < addr.size(); i++) {
			if(cache.get(i) == null) {
				System.out.println(i + "\t\t" + i + "\t\t" + "-----------");
			}
			else {
				System.out.println(i + "\t\t" + i + "\t\t" + "Mem["+ cache.get(i)+"]"); 
			}
		}
		System.out.println("\nMiss rate = " + missRate + " % "); //print miss rate
	}
	}
	
	//menu based program to select cache mapping options and input
	public static void startProgram() {
		
		/* data field */
		boolean directMap = false;
		boolean setAssoc = false;
		boolean fullAssoc = false;
		boolean selectingOption = true;
		boolean correctSetOpt = false;
		boolean correctRepPolicy = false;
		String input = "";
		int numberOfBlocks = 0;
		int setAssocOpt = 0;
		int replacementPolicy = 0;
		int blockAddRef = 0;

		LinkedList<String> binaryStrings = new LinkedList<String>(); //implement binary addressing
		LinkedList<Integer> decimalAddress = new LinkedList<Integer>(); //implement decimal addressing
		
		Scanner s = new Scanner(System.in); //create scanner
		
		/* Prompt user to select option */
		System.out.println("Please select an option:\n1 for direct mapping\n2 for set associative\n3 for fully associative");
		
		/* While user is selecting option*/
		while(selectingOption) {
		
		/* Scanner for user input option */
		
		input = s.nextLine();
		
			/* Set users option*/
			switch(input) {
			
			case "1": /* Set to Direct Mapping */
				directMap = true;
				selectingOption = false;
				System.out.println("****Changed to Direct Mapping****\n");
				break;
				
			case "2": /* Set to Set Associative */
				setAssoc = true;
				selectingOption = false;
				System.out.println("****Changed to Set Associative****\n");
				break;
				
			case "3": /* Set to Fully Associative */
				System.out.println("****Changed to Fully Associative****\n");
				selectingOption = false;
				fullAssoc = true;
				break;
				
			default: /* Loop and display incorrect choice */
				System.out.println("Incorrect choice");
				break;
			} /* end of switch */
		
		}/*end of while selecting option loop*/
		
		System.out.println("Enter the number of cache blocks");
		if(s.hasNextInt()) {
			numberOfBlocks = s.nextInt();
		}
		else {
			System.out.println("Error with input");
			System.exit(0);
		}
		
		
		while((fullAssoc || setAssoc) && !correctRepPolicy) {
			
			while(setAssoc && !correctSetOpt) {
				
				System.out.println("Enter the set associativity option (1 for 2-way, 2 for 4-way)");
				setAssocOpt = s.nextInt();
				if(setAssocOpt == 1) {
					
					correctSetOpt = true;
				}
				else if (setAssocOpt == 2) {
					
					correctSetOpt = true;
				}
				else {
					System.out.println("Incorrect set associativy option\n");
				}
			}

			System.out.println("Enter the replacement policy option (1 for LRU)");
			replacementPolicy = s.nextInt();
			
			if(replacementPolicy == 1) {
				correctRepPolicy = true;
			}
			else {
				System.out.println("Incorrect replacement policy option\n");
			}
		}
	
		System.out.println("Enter the block address reference for each set associativity (type exit when finished)");
		
		while(s.hasNextInt()) {
	
			 blockAddRef = s.nextInt();
		
			 binaryStrings.addLast(Integer.toBinaryString(blockAddRef)); //implement binary
			 decimalAddress.addLast(blockAddRef); //implement decimal
				
		}
		if(directMap == true) {//execute direct mapping
			directMap(decimalAddress, numberOfBlocks);
		}
		else if (setAssoc == true) {//execute set associative passing the type(2way/4way)
			setAssoc(decimalAddress, numberOfBlocks, setAssocOpt, replacementPolicy);
		}
		else if(fullAssoc == true) { //execute fully associative
			fullyAssoc(decimalAddress, numberOfBlocks, setAssocOpt, replacementPolicy);
		}
		else {
			System.out.println("Error, no option selected.");
		}
		
	}
	//method to update and track LRU
	public static int updateLRU(int addr, LinkedList<Integer> lruList) {
		
			int lru = Integer.MAX_VALUE;
				//update LRU
			if(!lruList.contains(addr)) {
				lruList.add(addr);
				lru = lruList.getFirst(); 
			}			
			if(addr == lruList.getFirst() && lruList.size() > 0) {
				lruList.removeFirst();
				lruList.add(addr);
				lru = lruList.getFirst();
			}
		System.out.println("Address "+ addr + " used. LRU: "+ lruList.getFirst());
		return lru;
	}
	//method to update LRU that has been swapped used for fully associative
	public static int updateLRUswapped(int addr, LinkedList<Integer> lruList, LinkedHashMap<Integer, Integer> swapped) {
		
		int lru = Integer.MAX_VALUE;
		//update LRU that has been swapped used for fully associative
		if(addr == lruList.getFirst() && lruList.size() > 0) {
			lruList.removeFirst();
			lruList.add(addr);
			lru = lruList.getFirst();
		}
		else { //else the address has been swapped and is not the original blockAddr % numOfBlocks. Find where it is swapped.
			lruList.removeFirst();
			lruList.add(swapped.get(addr));
			lru = lruList.getFirst();
		}
		return lru;
	}
	
}
