/**
 * A class for the representation of positive integer numbers with 64 digits. 
 */
package de.inpiraten.jdemocrator.Numbers;

//This whole class could probably 
//be way more efficient, but I could 
//not figure out how to work this out 
//with standard Base64 methods

/**
 * @author andi
 *
 */
public class Base64Number {
	
	/**
	 * Transforms a positive long into a Base64 number string
	 * @param n The positive long
	 * @return the Base64 string representation of the number
	 * @throws NoBase64NumberException if n is negative
	 */
	public static String toString(long n) throws NoBase64NumberException{
		if (n < 0){ 
			throw new NoBase64NumberException("Class Base64Number only handles positive numbers");
		}
			
		if (n == 0) return "A";
		
		String output = "";
		
		byte exp = 1;
		
		while (n > 0){
			int divisor = new Double(Math.pow(64, exp)).intValue();
			byte digit = (byte) ((n % divisor)/Math.pow(64, exp-1));
			output = toDigit(digit)+output;
			n -= digit*Math.pow(64, exp-1);
			exp++;
		}
		
		return output;
	}
	
	/**
	 * transform a string of a base64 number to an integer
	 * @param s
	 * @return
	 */
	public static long toLong(String s) throws NoBase64NumberException{
		//break the String into digits
		char[] digits = s.toCharArray();
		
		long output = 0;
		
		//sum up the digits
		for(int i = 0; i < digits.length; i++){
			//get digit value
			output += digitValue(digits[i])*Math.pow(64, digits.length-i-1);
		}
		
		return output;
	}
	
	/**
	 * 
	 * @param c the char that represents the Base64 digit
	 * @return the integer value of the digit
	 * @throws NoBase64NumberException if the char is not a Base64 digit
	 */
	public static byte digitValue(char c) throws NoBase64NumberException{
		
		byte output = -1;
		
		switch (c) {
	        case 'A':  output = 0;
	                  	break;
	        case 'B':  output = 1;
	         			break;
	        case 'C':  output = 2;
						break;		
	        case 'D':  output = 3;
						break;
	        case 'E':  output = 4;
						break;
	        case 'F':  output = 5;
						break;
	        case 'G':  output = 6;
						break;
	        case 'H':  output = 7;
						break;
	        case 'I':  output = 8;
				       	break;
			case 'J':  output = 9;
						break;
			case 'K':  output = 10;
						break;		
			case 'L':  output = 11;
						break;
			case 'M':  output = 12;
						break;
			case 'N':  output = 13;
						break;
			case 'O':  output = 14;
						break;
			case 'P':  output = 15;
						break;	
	        case 'Q':  output = 16;
          				break;
			case 'R':  output = 17;
			 			break;
			case 'S':  output = 18;
						break;		
			case 'T':  output = 19;
						break;
			case 'U':  output = 20;
						break;
			case 'V':  output = 21;
						break;
			case 'W':  output = 22;
						break;
			case 'X':  output = 23;
						break;
			case 'Y':  output = 24;
				       	break;
			case 'Z':  output = 25;
						break;
			case 'a':  output = 26;
						break;		
			case 'b':  output = 27;
						break;
			case 'c':  output = 28;
						break;
			case 'd':  output = 29;
						break;
			case 'e':  output = 30;
						break;
			case 'f':  output = 31;
						break;
	        case 'g':  output = 32;
			          	break;
			case 'h':  output = 33;
			 			break;
			case 'i':  output = 34;
						break;		
			case 'j':  output = 35;
						break;
			case 'k':  output = 36;
						break;
			case 'l':  output = 37;
						break;
			case 'm':  output = 38;
						break;
			case 'n':  output = 39;
						break;
			case 'o':  output = 40;
				       	break;
			case 'p':  output = 41;
						break;
			case 'q':  output = 42;
						break;		
			case 'r':  output = 43;
						break;
			case 's':  output = 44;
						break;
			case 't':  output = 45;
						break;
			case 'u':  output = 46;
						break;
			case 'v':  output = 47;
						break;	
			case 'w':  output = 48;
						break;
			case 'x':  output = 49;
			 			break;
			case 'y':  output = 50;
						break;		
			case 'z':  output = 51;
						break;
			case '0':  output = 52;
						break;
			case '1':  output = 53;
						break;
			case '2':  output = 54;
						break;
			case '3':  output = 55;
						break;
			case '4':  output = 56;
				       	break;
			case '5':  output = 57;
						break;
			case '6':  output = 58;
						break;		
			case '7':  output = 59;
						break;
			case '8':  output = 60;
						break;
			case '9':  output = 61;
						break;
			case '+':  output = 62;
						break;
			case '/':  output = 63;
						break;					
			default: throw new NoBase64NumberException("The character "+c+" is no valid Base64 number digit");
		}
		
		return output;
	}

	/**
	 * 
	 * @param n the number to be turned into a Base64 digit
	 * @return a character digit that represents the number
	 * @throws NoBase64NumberException if the value of the integer is not between 0 and 63
	 */
	public static char toDigit(byte n) throws NoBase64NumberException{
		char output = '.';
		
		switch (n) {
        case 0:  output = 'A';
                  	break;
        case 1:  output = 'B';
         			break;
        case 2:  output = 'C';
					break;		
        case 3:  output = 'D';
					break;
        case 4:  output = 'E';
					break;
        case 5:  output = 'F';
					break;
        case 6:  output = 'G';
					break;
        case 7:  output = 'H';
					break;
        case 8:  output = 'I';
			       	break;
		case 9:  output = 'J';
					break;
		case 10:  output = 'K';
					break;		
		case 11:  output = 'L';
					break;
		case 12:  output = 'M';
					break;
		case 13:  output = 'N';
					break;
		case 14:  output = 'O';
					break;
		case 15:  output = 'P';
					break;	
        case 16:  output = 'Q';
      				break;
		case 17:  output = 'R';
		 			break;
		case 18:  output = 'S';
					break;		
		case 19:  output = 'T';
					break;
		case 20:  output = 'U';
					break;
		case 21:  output = 'V';
					break;
		case 22:  output = 'W';
					break;
		case 23:  output = 'X';
					break;
		case 24:  output = 'Y';
			       	break;
		case 25:  output = 'Z';
					break;
		case 26:  output = 'a';
					break;		
		case 27:  output = 'b';
					break;
		case 28:  output = 'c';
					break;
		case 29:  output = 'd';
					break;
		case 30:  output = 'e';
					break;
		case 31:  output = 'f';
					break;
        case 32:  output = 'g';
		          	break;
		case 33:  output = 'h';
		 			break;
		case 34:  output = 'i';
					break;		
		case 35:  output = 'j';
					break;
		case 36:  output = 'k';
					break;
		case 37:  output = 'l';
					break;
		case 38:  output = 'm';
					break;
		case 39:  output = 'n';
					break;
		case 40:  output = 'o';
			       	break;
		case 41:  output = 'p';
					break;
		case 42:  output = 'q';
					break;		
		case 43:  output = 'r';
					break;
		case 44:  output = 's';
					break;
		case 45:  output = 't';
					break;
		case 46:  output = 'u';
					break;
		case 47:  output = 'v';
					break;	
		case 48:  output = 'w';
					break;
		case 49:  output = 'x';
		 			break;
		case 50:  output = 'y';
					break;		
		case 51:  output = 'z';
					break;
		case 52:  output = '0';
					break;
		case 53:  output = '1';
					break;
		case 54:  output = '2';
					break;
		case 55:  output = '3';
					break;
		case 56:  output = '4';
			       	break;
		case 57:  output = '5';
					break;
		case 58:  output = '6';
					break;		
		case 59:  output = '7';
					break;
		case 60:  output = '8';
					break;
		case 61:  output = '9';
					break;
		case 62:  output = '+';
					break;
		case 63:  output = '/';
					break;					
		default: throw new NoBase64NumberException("The value "+n+" is no valid Base64 number digit");
	}
		
		return output;
	}

	/**
	 * Converts a Base64 into a binary string
	 * @param base64String
	 * @return
	 * @throws NoBase64NumberException 
	 */
	public static String toBinaryString(String base64String) throws NoBase64NumberException{
		String output = "";
		char[] digits = base64String.toCharArray();
		for (int i = 0; i < digits.length; i++){
			String partial = Integer.toBinaryString(digitValue(digits[i]));
			while (partial.length() < 6){
				partial = "0"+partial;
			}
			output = output + partial;
		}
		return output;
	}
	

}
