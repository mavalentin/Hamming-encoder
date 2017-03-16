/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hamming.encoder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Manuel
 */
public class HammingCode {
    public static Scanner scan = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here   
        String message;
        String filename;
        
        System.out.println("Write \"decode\" or \"encode\":");
        String action = scan.nextLine();
        
        if (action.equals("decode")) {
            System.out.println("Please write the filename where the encoded message is located:");
            filename = scan.nextLine();
            String decoded = decode(filename);
            System.out.println("The message is:\n"+decoded);
        }
        
        else if (action.equals("encode")) {
            System.out.println("Please write the message to be encoded:");
            message = scan.nextLine();
            System.out.println("Please write the filename where to save the encoded message:");
            filename = scan.nextLine();
            
            encode(message, filename);
            System.out.println("Your message has been encoded in file "+filename);
        }
        
        
        
        
       
    }
    
    public static void encode(String message, String filename) throws IOException {
        String encoded = "";
        String[][] array = new String[message.length()][2];
        
        for(int i=0; i<message.length(); i++) {
            String currentChar = Integer.toBinaryString(message.charAt(i));
            char test = message.charAt(i);
            if (currentChar.length()<8) {
                String leadingZeroes = "";
                
                for (int l=0; l<8-currentChar.length(); l++) {
                    leadingZeroes = "0"+leadingZeroes;
                }
                
                currentChar = leadingZeroes+currentChar;
            }
            
            array[i][0]= currentChar.substring(0, 4);
            array[i][1]= currentChar.substring(4);  
        }
        
        for (int i=0; i<array.length; i++) {
            encoded = encoded+array[i][0]+"\n"+array[i][1]+"\n\n";
        }
        
        FileWriter writer = new FileWriter(filename, false);
        writer.write(encoded);
        writer.close();
        
    }
    
    public static String decode(String filename) {
        String decoded;
        decoded = "not yet implemented";
        
        return decoded;
    }
    
}
