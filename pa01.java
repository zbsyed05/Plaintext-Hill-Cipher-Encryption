/*============================================================================
| 		Assignment: pa01 - Encrypting a plaintext file using the Hill cipher
|
| 			Author: Zainab Syed
| 		  Language: Java
| 		To Compile: javac pa01.java
| 		To Execute: java -> java pa01 kX.txt pX.txt
| 					where kX.txt is the keytext file
| 					and pX.txt is plaintext file
| 			  Note:
| 					All input files are simple 8 bit ASCII input
| 					All execute commands above have been tested on Eustis
| 		  Due Date: 2/23/2025
+===========================================================================*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class pa01 {
	
	public static void encrypt(int[][] key, int n, char[] plainText) {
		
		StringBuilder cipher = new StringBuilder();
		
		// number of characters in plaintext
		int length = 0;
		for(char ch : plainText) {
			if(ch == 0) break;
			length++;
		}
		// loops till end of plaintext is reached
		// increments by n in order to work on next block of characters
		for(int i = 0; i < length; i+= n) {			
			int[] block = new int[n];
			// ascii code for each letter
			for(int j = 0; j < n; j++) {
				block[j] = plainText[i+j] - 'a';
			}			
			int[] encryption = new int[n];
			
			// gets encryption array
			for(int j = 0; j < n; j++) {
				encryption[j] = 0;
				for (int k = 0; k < n; k++) {
					encryption[j] += key[j][k] * block[k];
				}
				encryption[j] %= 26;
			}
			// adds to end of string
			for(int j = 0; j < n; j++) {
				cipher.append((char)(encryption[j]+'a'));
			}
			
		}
		
		// prints Ciphertext result
		System.out.println("\nCiphertext:");
		
		// convert to String Builder to character array
		String result = cipher.toString();
		char[] ans = result.toCharArray();
		
		for(int j = 0; j < result.length(); j++) {
			System.out.print(ans[j]);			
			if((j+1) % 80 == 0) {// bounds reached
				System.out.println();
			}
		}
		System.out.println();
		
	}
	
	public static int[][] matrixKey(File keyFile) throws FileNotFoundException {
		
		System.out.println("\nKey matrix:");		
		Scanner sc = new Scanner(keyFile);
		
		int n = sc.nextInt(); // size of array		
		int [][] key = new int[n][n]; // initialize empty key
		
		// copies the information from the file into the key array
		for(int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				key[i][j] = sc.nextInt();
				System.out.printf("%4d",key[i][j]);
			}
			System.out.println();
		}		
		
		sc.close();
		return key;
	}
	
	public static char[] plainTextConversion(File plainTextFile, int n) {
		char[] result = new char[10000];
		int i = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(plainTextFile)) ;
			int ch;
			
			// loops till end of file is reached
			while((ch = reader.read()) != -1) {
				// retrieves character
				char letter = (char) ch;
				if(Character.isLetter(letter)) {
					char letter2 = Character.toLowerCase(letter);
					// makes sure letter is lower case and in English
					if(letter2 >= 'a' && letter2 <= 'z') {
						result[i++] = Character.toLowerCase(letter2);
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		// pads the ending with x
		while((i%n) != 0) {
			result[i++] = 'x';
		}
		
		// Prints the plaintext
		System.out.println("\nPlaintext:");
		for(int j = 0; j < i; j++) {
			System.out.print(result[j]);
				
			if((j+1) % 80 == 0) {
				System.out.println();
			}
		}
		System.out.println();
		
		return result;
	}
	
	public static void main(String[] args) {
		// gets access to these files
		File keyFile = new File(args[0]);
		File plainTextFile = new File(args[1]);
				
		try {
			// gets the matrix array
			int[][] keyMatrix = matrixKey(keyFile);
			
			// dimensions
			BufferedReader reader = new BufferedReader(new FileReader(keyFile));
			int n = Integer.parseInt(reader.readLine());
			
			char[] plainText = plainTextConversion(plainTextFile, n);
			
			encrypt(keyMatrix, n,plainText);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}