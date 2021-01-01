
/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;

public class BabyBirths {
    public void printNames(){
        FileResource fr = new FileResource();
        for(CSVRecord rec: fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            if(numBorn <= 100){
                
                System.out.println("Name " + rec.get(0) +
                                " Gender " + rec.get(1) +
                                " Num Born " + rec.get(2));
            }      
        }
    }
    
    public void totalBirths(FileResource fr){
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for(CSVRecord rec: fr.getCSVParser(false)){
            int newBorn = Integer.parseInt(rec.get(2));
            totalBirths += newBorn;
            if(rec.get(1).equals("M")){
                totalBoys += newBorn;
            }
            else{
                totalGirls += newBorn;
            }
        }
        
        System.out.println("total births = " + totalBirths);
        System.out.println("total boys = " + totalBoys);
        System.out.println("total girls = " + totalGirls);
    }
    
    public void testTotalBirths(){
        FileResource fr = new FileResource("us_babynames/us_babynames_test/example-small.csv");
        totalBirths(fr);
    }
}
