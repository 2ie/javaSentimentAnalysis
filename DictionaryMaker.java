import java.io.FileNotFoundException;
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

/**
 * DictionaryMaker class allows us to create HashSet dictionaries of a given file.
 * These will come in handy later on in the program when we compare the contents
 * inside of the positive and negative HashSet dictionaries.
 * @author Mateo Escamilla
 */
public class DictionaryMaker {
    
    /** 
     * Returns a HashSet<String> that contains all of the dictionary entries in a given file.
     * The param, pathToWordDir, holds the address to the .txt file containing the words.
     * @param pathToWordDir
     * @return HashSet<String>
     * @throws FileNotFoundException
     */
    public HashSet<String> dictStore(File pathToWordDir) throws FileNotFoundException {
        HashSet<String> wordSet = new HashSet<String>();
        try {
            Scanner scnr = new Scanner(pathToWordDir);

            while(scnr.hasNextLine()) {
                String currLineString = scnr.nextLine();
                if(currLineString.contains(";") || (currLineString.length() == 0)) {
                    continue;
                }
                else {
                    wordSet.add(currLineString);
                }
            }
            scnr.close();
        } catch(FileNotFoundException e) {
            System.out.printf("No file found at %s...\n", pathToWordDir.getPath());
            e.printStackTrace();
        }
        return wordSet;
    }
}
