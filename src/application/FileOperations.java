package application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FileOperations {
	
	
	// USED FOR READING DATA FROM THE FILE     // size is the character size which should be read
	public static String readFixedLengthString(int size,DataInput in) throws IOException {
														//
		char [] chars  = new char [size]; // we create an array to read characters as we want
		
		for (int i =0;i<size;i++){
			chars[i] = in.readChar(); // it reads byte by byte, turns it into character and adds it into array "chars"
		}
		
		return new String(chars);  // it returns chars array
			
	}
	
	// USED FOR WRITING DATA INTO THE FILE     // "s" is the string which will be added into the file
	public static void writeFixedLengthString(String s,int size,DataOutput out) throws IOException{
													// "size" is the size of the "s"
		
		// We create an array to write the string into the file
		char [] chars = new char[size];
		
		
		s.getChars(0, s.length(),chars,0);  // It's used to copy string "s" into chars array
		
		
		// Fill in the rest of the array with blank characters
		for(int i = Math.min(s.length(),size); i < chars.length; i++) {
			chars[i] = ' ';
		}
		
		out.writeChars(new String(chars)); // it writes char into the file
		
	}
}
