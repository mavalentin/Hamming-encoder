/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hamming.encoder;

import java.io.File;
import java.io.FileNotFoundException;
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

        while (true) {

            System.out.print("Write \"decode\" or \"encode\": ");
            String action = scan.nextLine();
            if (action.equals("exit")) {
                System.exit(0);
            }

            if (action.equals("decode")) {
                System.out.print("Please write the filename where the encoded message is located: ");
                filename = scan.nextLine();
                decode(filename);
            } else if (action.equals("encode")) {
                System.out.print("Please write the message to be encoded: ");
                message = scan.nextLine();
                System.out.print("Please write the filename where to save the encoded message: ");
                filename = scan.nextLine();

                encode(message, filename);
                System.out.println("Your message has been encoded in file \"" + filename + "\"\n");
            }

        }

    }

    public static void encode(String message, String filename) throws IOException {
        String encoded = "";
        String[][] array = new String[message.length()][2];

        for (int i = 0; i < message.length(); i++) {
            String currentChar = Integer.toBinaryString(message.charAt(i));

            if (currentChar.length() < 8) {
                String leadingZeroes = "";

                for (int l = 0; l < 8 - currentChar.length(); l++) {
                    leadingZeroes = "0" + leadingZeroes;
                }

                currentChar = leadingZeroes + currentChar;
            }

            String partOne = currentChar.substring(0, 4);
            String partTwo = currentChar.substring(4);

            array[i][0] = partOne + parityBits(partOne);
            array[i][1] = partTwo + parityBits(partTwo);
        }

        for (int i = 0; i < array.length; i++) {
            encoded = encoded + array[i][0] + "\n" + array[i][1] + "\n\n";
        }

        FileWriter writer = new FileWriter(filename, false);
        writer.write(encoded);
        writer.close();

    }

    public static String parityBits(String binary) {
        String parityBits = "";
        int sum = 0;

        for (int i = 0; i < 3; i++) {
            for (int c = i; c < i + 3; c++) {
                char tempchar = binary.charAt(c % 4);
                sum = sum + (Character.getNumericValue(tempchar));
            }
            if ((sum % 2) == 0) {
                parityBits = parityBits + "0";
            } else {
                parityBits = parityBits + "1";
            }
            sum = 0;
        }

        return parityBits;
    }

    public static String decode(String filename) throws FileNotFoundException {
        String decoded = "";
        String binaryRead = "";
        boolean errors = false;
        Scanner fileReader = null;

        try {
            fileReader = new Scanner(new File(filename));
        } catch (FileNotFoundException exception) {
            System.out.println("The file does not exist.\n");
            return null;
        }

        while (fileReader.hasNext()) {
            for (int a = 0; a < 2; a++) {

                String scanned = fileReader.nextLine();
                if (scanned.equals("")) {
                    scanned = fileReader.nextLine();
                };

                String parityBits = parityBits(scanned.substring(0, 4));

                if (!scanned.substring(4).equals(parityBits)) {
                    errors = true;
                    binaryRead = binaryRead + correct(scanned, parityBits);
                } else {
                    binaryRead = binaryRead + scanned.substring(0, 4);
                }

            }

            decoded = decoded + binaryToASCII(binaryRead);
            binaryRead = "";
        }

        if (errors == true) {
            System.out.println("Some errors in the file have been corrected");
        }

        System.out.println("The message is: " + decoded + "\n");

        return decoded;
    }

    public static String correct(String sequence, String parityBits) {
        String corrected = "";
        String myCharacter = sequence.substring(0, 4);
        boolean p1, p2, p3;

        p1 = (sequence.charAt(4) == parityBits.charAt(0));
        p2 = (sequence.charAt(5) == parityBits.charAt(1));
        p3 = (sequence.charAt(6) == parityBits.charAt(2));

        if (!p1 && p2 && p3) {
            corrected = myCharacter;
        }

        if (!p1 && !p2 && p3) {
            corrected = invertValue(myCharacter, 1);
        } else if (!p1 && !p2 && !p3) {
            corrected = invertValue(myCharacter, 2);
        } else if (p1 && !p2 && p3) {
            corrected = myCharacter;
        } else if (p1 && !p2 && !p3) {
            corrected = invertValue(myCharacter, 3);
        } else if (p1 && p2 && !p3) {
            corrected = myCharacter;
        } else if (!p1 && p2 && !p3) {
            corrected = invertValue(myCharacter, 0);
        }

        return corrected;
    }

    public static String invertValue(String sequence, int toInvert) {
        String updated = "";
        if (sequence.charAt(toInvert) == '1') {
            updated = sequence.substring(0, toInvert) + "0" + sequence.substring(toInvert + 1);
        } else if (sequence.charAt(toInvert) == '0') {
            updated = sequence.substring(0, toInvert) + "1" + sequence.substring(toInvert + 1);
        }

        return updated;
    }

    public static String binaryToASCII(String sequence) {
        int value = Integer.parseInt(sequence, 2);
        char[] character = Character.toChars(value);
        return String.valueOf(character);
    }

}
