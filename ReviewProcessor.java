import java.io.FileNotFoundException;
import java.lang.NullPointerException;
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

/**
 * ReviewProcessor class allows us to fully process our reviews
 * as long as we know the path to the reviews, and have a dictionary
 * HashSet stored somewhere in our program.
 * @author Mateo Escamilla
 */
public class ReviewProcessor {
    protected int corrReviewCount;
    protected int totalReviewCount;

    protected String realClass;
    protected String predClass;

    protected File pathToReviewsFolder;

    /**
     * This ReviewProcessor constructor takes in a path to the
     * reviewDirectory as a parameter, initializes the path to
     * the directory as pathToReviewsFolder, and sets all the
     * other default values to 0.
     * @param reviewDirectory
     */
    public ReviewProcessor(File reviewDirectory) {
        this.corrReviewCount = 0;
        this.totalReviewCount = 0;
        this.pathToReviewsFolder = reviewDirectory;
        setRealClass(reviewDirectory);
    }

    
    /** 
     * This method sets the real class of the object invoking
     * it to either Negative or Positive depending on the path. 
     * This helps us when printing the results in the future.
     * @param reviewDirectory
     */
    protected void setRealClass(File reviewDirectory) {
        String realClassIs;
        if (reviewDirectory.getName().contains("neg")) {
            realClassIs = "Negative";
        }
        else {
            realClassIs = "Positive";
        }
        this.realClass = realClassIs;
    }

    
    /** 
     * Returns a String value representing real class.
     * Can be "Positive" or "Negative"
     * @return String
     */
    public String getRealClass() {
        return realClass;
    }

    
    /** 
     * Sets the predicted class value to a String.
     * Helpful when classifying the file's contents.
     * @param aPredClass
     */
    protected void setPredClass(String aPredClass) {
        this.predClass = aPredClass;
    }

    
    /** 
     * Simple getter method that returns the predicted class.
     * @return String
     */
    public String getPredClass() {
        return predClass;
    }

    
    /** 
     * Simple getter method that returns the correct review count.
     * @return int
     */
    public int getCorrReviewCount() {
        return corrReviewCount;
    }

    
    /** 
     * Simple getter method that returns total review count.
     * @return int
     */
    public int getTotalReviewCount() {
        return totalReviewCount;
    }

    
    /** 
     * This method is the one that classifies if a particular file
     * in the directory is either a positive, or negative file. For
     * each file in the directory, we go through the file, split up
     * the String tokens, and then for each String token, compare it
     * to our positive and negative dictionaries. At the very end, it
     * classifies the current review as being positive or negative,
     * and outputs the results to console.
     * @param pos_dict
     * @param neg_dict
     * @throws FileNotFoundException
     * @throws NullPointerException
     */
    public void reviewAnalyze(HashSet<String> pos_dict, HashSet<String> neg_dict) throws FileNotFoundException, NullPointerException {
        File[] reviewDirectory = pathToReviewsFolder.listFiles();
        try {
            this.totalReviewCount = reviewDirectory.length;
            for(File f : reviewDirectory) {
                int posWordCount = 0;
                int negWordCount = 0;
                try {
                    Scanner scnr = new Scanner(f);
    
                    while(scnr.hasNextLine()) {
                        String currentLine = scnr.nextLine();
                        String[] currLineArr = currentLine.toLowerCase().replaceAll("\\p{Punct}", "").split("\\s+");
        
                        for(String s : currLineArr) {
                            if(pos_dict.contains(s)) {
                                posWordCount++;
                            } else if(neg_dict.contains(s)) {
                                negWordCount++;
                            } else {
                                continue;
                            }
                        }
                    }
                    if(negWordCount >= posWordCount) {
                        if(realClass.contains("Negative")) {
                            corrReviewCount++;
                        }
                        setPredClass("Negative");
                    } else if(posWordCount > negWordCount) {
                        if(realClass.contains("Positive")) {
                            corrReviewCount++;
                        }
                        setPredClass("Positive");
                    }
                    scnr.close();
                    reviewPrinter(f);
                } catch(FileNotFoundException e) {
                    System.out.printf("No file found at %s...\n", f.getPath());
                    e.printStackTrace();
                }
            }
        } catch(NullPointerException e) {
            System.out.printf("Directory \"%s\" does not contain any files...\n", pathToReviewsFolder.getName());
            e.printStackTrace();
        }     
    }

    
    /** 
     * This method reviewPrinter is called while the reviews are
     * being analyzed, and it outputs the current filename, the 
     * real class, and the predicted class.
     * @param file
     */
    public void reviewPrinter(File file) {
        System.out.printf("File: %-10s  Real Class: %s  Predicted Class: %s\n", file.getName(), realClass, predClass);
    }

    /**
     * An overloaded reviewPrinter method that allows us to print
     * the total results of either positive or negative.
     */
    public void reviewPrinter() {
        System.out.printf("%d %s reviews were analyzed, the system correctly identified %d.\n", totalReviewCount, getRealClass().toLowerCase(), corrReviewCount);
        System.out.printf("%s review accuracy is %.2f%%.\n", getRealClass(), ((float)corrReviewCount/totalReviewCount) * 100);
    }

}
