import java.io.FileNotFoundException;
import java.io.File;
import java.util.HashSet;
import java.lang.IllegalArgumentException;

/**
 * This SentimentAnalysisApp houses the main function, 
 * and is where the (driver) program runs. 
 * @author Mateo Escamilla
 */
public class SentimentAnalysisApp 
{
    
    /** 
     * After reading in the commandline arguments, we initialize two objects,
     * DictionaryMaker's, and we read in the words from the .txt files. 
     * Then, we initialize two ReviewProcessor objects, one for the positive
     * directory, and for the negative directory. We then utilize ReviewProcessor
     * methods to analyze the .txt files to classify them as positive or negative.
     * The results are printed using a seperate ReviewProcessor method.
     * @param args
     * @throws FileNotFoundException
     * @throws IllegalArgumentException
     */
    public static void main (String[] args) throws FileNotFoundException, IllegalArgumentException
    {
        if(args.length != 4) {
            throw new IllegalArgumentException();
        }
        try {
            File pathToPosWords = new File(args[0]);
            File pathToNegWords = new File(args[1]);
            File pathToPosReviewsFolder = new File(args[2]);
            File pathToNegReviewsFolder = new File(args[3]);
    
            DictionaryMaker posWords = new DictionaryMaker();
            DictionaryMaker negWords = new DictionaryMaker();
    
            HashSet<String> posWordsDict = posWords.dictStore(pathToPosWords);
            HashSet<String> negWordsDict = negWords.dictStore(pathToNegWords);
    
            ReviewProcessor posReviewer = new ReviewProcessor(pathToPosReviewsFolder);
            ReviewProcessor negReviewer = new ReviewProcessor(pathToNegReviewsFolder);
    
            posReviewer.reviewAnalyze(posWordsDict, negWordsDict);
            System.out.println("");
            negReviewer.reviewAnalyze(posWordsDict, negWordsDict);
    
            System.out.println("");
            posReviewer.reviewPrinter();
            negReviewer.reviewPrinter();
            System.out.println("");

            int totalCorrReviews = (posReviewer.getCorrReviewCount() + negReviewer.getCorrReviewCount());
            int totalReviews = (posReviewer.getTotalReviewCount() + negReviewer.getTotalReviewCount());
            System.out.printf("%d reviews were analyzed, the system correctly identified %d.\n", totalReviews, totalCorrReviews);
            System.out.printf("Overall review accuracy is %.2f%%.\n\n", ((float)totalCorrReviews/totalReviews) * 100);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
