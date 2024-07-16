package com.ljmu.comp7501.eodoh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/*
 * -------------------------------------[PHRASE BUILDER CLASS]------------------------------
 * The PhaseBuilder class contains methods that executes a simple random sentence generation program
 * The sentences are genrated in subject-verb-object pattern (SVO)
 * This program can generate a random sentence from a selection of preloaded words and phrases in the csv resources 
 * file: Random Advice 
 * The phrasebuilder class contains methods to read from and write to file, print from file, return random
 * words from an array list and print out concatented phrase to console as feedback to user prompt
 * 
 */
public class PhraseBuilder {

    /**
     * ----------------------------------[MAIN METHOD]------------------------------------
     * This is the main method of the program. This method controls the flow of the program to generate, print
     * or save random sentences creates from preloaded words in a csv file. 
     * @param args
     */
    public static void main(String[] args) {

        //Initialize string objects with relative file paths to required documents in resources folder
        String readWordsFilePath = "Random  Advice.csv";
        String saveAdviceFilePath = "Saved Advice.txt"; 
        String printAdviceFilePath = "Saved Advice.txt"; 

        //Create new arraylist to hold processes file lines 
        List<String> subjects = new ArrayList<>();
        List<String> verbs = new ArrayList<>();
        List<String> objects = new ArrayList<>();

        //Call the read and split method to populate arrays from file
        readAndSplitFile(readWordsFilePath, subjects, verbs, objects);

        //Try with resource to manage system in resources
        try (Scanner scanner = new Scanner(System.in)) { 
    
            //Print welcome messgae to console 
            System.out.println(" \nWelcome to your New Year advisor! \nWe will give random advices to guide your new year resolutions! \nIf you like an adivce, just save it your list! \n \nLet's Go!... \n ");
    
            //Use a while loop to keep program on a run loop until user exit by break statement or system.exit in if control flow statement 
            while (true) {

                //Generate and print random sentence by calling the generate random advice method, passing in arrays from file
                String newAdvice = generateRandomAdvice(subjects, objects, verbs);
                System.out.println(newAdvice);

                //print out user menu
                System.out.println(" \nNAVIGATION: N - Next Advice; S - Save Advice; P - Print Saved Advice; E - Exit.");
                
                //control user input with specifies regex
                while (!scanner.hasNext("[nspeNSPE]")) {
                    System.out.println("Invlaid Input. Please enter an alphabet from the options");
                    scanner.next();
                } 
    
                //Assign user input to variable
                String userOption = scanner.next().toUpperCase().trim();
                scanner.nextLine();//clears out scanner buffer
                
                //Use an if else block to control processing of user choice
                if (userOption.equals("N")) {
                    //do nothing. This let's the algorithm flow to the restart while loop to generate an new advice
                    System.out.println();//prints space to console
        
                } else if (userOption.equals("S")) {
                    //writes the current string value of newAdvice to Saved Advice.txt file in resources by calling method
                    savedAdviceToFile(saveAdviceFilePath, newAdvice);
                    System.out.println();//prints blank space to console 
                    
                } else if (userOption.equals("P")) {
                    //Prints out all the contents of the saved advice file and terminates while loop at break statement
                    System.out.println(" \nSAVED ADVICE:");//Prints header for advice list
                    printAdviceFromFile(printAdviceFilePath);
                    System.out.println();//prints a blank line to console 
                    break;

                } else if (userOption.equals("E")) {
                    //notify user and exit program with system.exit
                    System.out.println("\n \nExiting Application... \n");
                    System.exit(0);

                } else {
                    System.out.println("Invlaid Input. Please enter an alphabet from the options");
                    break;
                }
            }
        } 

    }
    
    
    
    
    
    /**
     * -------------------------------------[READ AND SPLIT FILE METHOD]------------------------------------
     * This method reads lines from a passed in file path string object and splits lines at specified regex into
     * passed in arraylist
     * This method divides each of the file lines into three in keeping with the SVO sentence format required
     * and assigns each part into a array specifies for that purpose
     * The requires regex is a comma as source file is a csv with a comma separated data structure
     * @param filePath string obj with a file path value
     * @param array1 array list to hold subjects of the sentences
     * @param array2 array list to hold verbs of the sentences 
     * @param array3 array list to hold objects of the sentences
     */
    private static void readAndSplitFile(String filePath, List<String> array1, List<String> array2, List<String> array3) {
        //Use a try-with-resource to manage file reader resource
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line based on comma (",") and add parts to respective arrays
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    array1.add(parts[0].trim());
                    array2.add(parts[1].trim());
                    array3.add(parts[2].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Oops! Something went wrong with the Random Advice file...");
            e.printStackTrace();
        }
    }


    
    
    /**
     * --------------------------------[GENERATE RANDOM ADVICE METHOD]-------------------------------------
     * This method uses the java.util.random to generate a random interger that is then substituted as a arraylist index
     * to return a random subject, object, and verb. The method that returns a string concatenation of the random
     * arraylist components 
     * @param subjects arraylist of subjects passed in
     * @param objects arraylist of objects passed in 
     * @param verbs arraylist of verbs passed in 
     * @return String object of concatenated ArrayList strings from passedin arraylist 
     */
    private static String generateRandomAdvice(List<String> subjects, List<String> objects, List<String> verbs) {
        Random random = new Random();
        String subject = subjects.get(random.nextInt(subjects.size()));
        String object = objects.get(random.nextInt(objects.size()));
        String verb = verbs.get(random.nextInt(verbs.size()));

        // Combine the words to form a sentence
        return subject + " " + verb + " " + object + "..";
    }





    /**
     * -----------------------------------[SAVED ADVICE TO FILE METHOD]-----------------------------------------
     * This method writes one or more Strings to the end of a file when called.
     * @param filePath filepath of destination file to write to 
     * @param lines String objects to write to file 
     */
    private static void savedAdviceToFile(String filePath, String... lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine(); // Add a newline after each line
            }
        } catch (IOException e) {
            System.out.println("Oops! Something went wrong while writing to the Saved Advice file ");
            e.printStackTrace();
        }
    }




    /**
     * -------------------------------------[PRINT ADVICE FROM FILE]-------------------------------
     * This method prints lines from a specified file passed in during method invocation 
     * @param filePath String obj with file path value of required file to be printed 
     */
    private static void printAdviceFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("**" + line);
            }
        } catch (IOException e) {
            System.out.println("Oops! Something went wrong while reading from the Saved Advice file");
            e.printStackTrace();
        }
    }

}
