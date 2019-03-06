/*
 
 * Turns number into binary by switching odd numbers into 1, even numbers into 0
 * This program simply asks for a integer number
 * That number will be converted into binary
 * And the binary will be encoded to Hamming code (without SECDED)
 * The second part will correct any Hamming code with 1 bit error and return the correct code 
 */
import java.util.*;
public class Driver {
	boolean exit;
	public int checkBitNumber (int numberlength) { // finds out the number of check bits needed
		int p = 0; 
		while (Math.pow(2, p) < p + numberlength + 1 ) {
			p++;
		}
		return p;
		
	}
	public void createCode() {
		int input;
		String result = "";
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a number to convert into binary and Hamming code: ");
		input = scanner.nextInt();
		
		 while(input > 0) //for some reasons if the condition is >= 0, the program would just stop
	     {
	         int a = input % 2;
	         result = a + result;
	         input = input / 2;
	     } // converts integer to binary
		
		 System.out.println("Binary: " + result);
		 
		 int encodedLength = result.length() + checkBitNumber(result.length());
		 int encodedNumber[] = new int[encodedLength + 1];
		 
		 int checkBitPosPower = 0; 
		 int dataBitPos = 0;
		 
		 for(int i=1; i <= encodedLength; i++) { // positions the check bits into the binary
			 if(i == (Math.pow(2, checkBitPosPower)) ) {
				 checkBitPosPower++;
				 
			 }
			 else {
				 encodedNumber[i] = Integer.parseInt(Character.toString(result.charAt(result.length() - dataBitPos - 1)));
				 dataBitPos++; 
			 }
		 }
		 
		 checkBitPosPower = 0;
		 
	 	 for(int i=1; i <= encodedLength; i++) { //change check bits from 0 to 1 where needed
			 if(i == (Math.pow(2, checkBitPosPower))) {
				 String check = "";
				 int temp = i;
				 
				 while(temp < encodedNumber.length) { //pattern for what bits the check bit should check
					 for (int k = temp; k <= temp + i - 1; k++) {
						 if(k < encodedNumber.length ) {
							 check += encodedNumber[k];
						 }
					 }	
				 temp = temp + 2*i;
				 
				 }	 
				 checkBitPosPower++;
				 
				 int counter = 0;
				 for( int j=0; j< check.length(); j++ ) {
				     if( check .charAt(j) == '1' ) {
				         counter++;
				     } 
				 } //counts the number of 1 for that parity bit
				 
				 if(counter % 2 !=0) { // if it's odd, then flip the parity bit to 1
					 encodedNumber[i] = 1;
				 }
			 }
			 
		 }
	 
	 	 	 System.out.print("The Hamming code is : ");
			 for(int m=encodedLength;m >= 1; m-- ) {
				 System.out.print(encodedNumber[m]);
			 }
			 System.out.println("");
		
			 promptEnterKey();
			 runMenu();
	}
		
	public void checkError() {
	 int input; 
	 Scanner scanner = new Scanner(System.in);
	 System.out.print("Please enter a Hamming code with one error bit: ");
	 String number = scanner.next();
	 
	 int[] encodedNumber = new int[number.length() +1];
	 int errorBit = 0;
	 
	 for (int i = 0; i < number.length(); i++) {
		 if(number.length() >= 3 && number.charAt(i) == '1' || number.charAt(i) == '0' ) {
			 encodedNumber[i+1] = Integer.parseInt(Character.toString(number.charAt(number.length() - i - 1)));
		 }
		 else {
			 System.out.println("Error. Please enter a correct code");
			 checkError();
		 }
	 }
	 
	 int checkBitPosPower = 0;
	 for (int i = 1; i < encodedNumber.length; i++) {
		 if(i == (Math.pow(2, checkBitPosPower))) {
			 String check = "";
			 int temp = i;
			 
			 while(temp < encodedNumber.length) { //pattern for what bits the check bit should check
				 for (int k = temp; k <= temp + i - 1; k++) {
					 if(k < encodedNumber.length ) {
						 check += encodedNumber[k];
					 }
				 }	
			 temp = temp + 2*i;
			 
			 }	 
			 checkBitPosPower++;
			 
			 int counter = 0;
			 for( int j=0; j< check.length(); j++ ) {
			     if( check .charAt(j) == '1' ) {
			         counter++;
			     } 
			 } //counts the number of 1 for that parity bit
			 
			 if(counter % 2 !=0) { 
				 errorBit += i;
			 }
		 }
	 }
	 
	 if(errorBit != 0) {
		 if(encodedNumber[errorBit] == 1) {
			 encodedNumber[errorBit] = 0;
		 }
		 else if(encodedNumber[errorBit] == 0) {
			 encodedNumber[errorBit] = 1;
		 }
	 
		 System.out.println("The bit in error is " + errorBit);
		 System.out.print("The correct code is : ");
		 for(int m= encodedNumber.length - 1; m >= 1; m-- ) {
			System.out.print(encodedNumber[m]);
		 }
	 }
	 else {
		 System.out.println("The code is either correct or has more than 1 error bits and cannot be detected.");
	 }
	 
	 System.out.println("");
	 promptEnterKey();
	 runMenu();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	Driver driver = new Driver();
	driver.runMenu();
	
	}
	
	public void runMenu() {
		
		while (!exit) {
			MenuSelection();
		}
	}
	
	public void MenuSelection() {
		System.out.println(String.format("%-50s %s\n", "Convert a natural number into Hamming code:", "A"));
		System.out.println(String.format("%-50s %s\n", "Correct Hamming code with 1 error bit:", "B"));
		System.out.print(String.format("%-50s %s", "Enter Selection", ""));
		Scanner scanner = new Scanner(System.in);
		String enter = scanner.nextLine();
		
			switch(enter) {
				case "A":
				case "a":
					createCode();
					break;
				case "B":
				case "b":
					checkError();
					break;
				default:
					System.out.println("Wrong selection, please try again");
			}
	}
	
	public void promptEnterKey(){
		// prompts user to press enter before continue
			   System.out.println("Press \"ENTER\" to go back to main menu...");
			   Scanner scanner = new Scanner(System.in);
			   scanner.nextLine();
		}
}
