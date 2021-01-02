
/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;

public class BabyBirths {    
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        FileResource fr = new FileResource("us_babynames/us_babynames_test/yob" + year + "short.csv");
        
        int totalRankedHigher = 0;
        for(CSVRecord rec: fr.getCSVParser(false)){
            int rank = getRank(year, name,gender);
            if(rank == -1){
                return totalRankedHigher;
            }
            if(rec.get(0).equals(name)){
                break;
            }
            
            if(rec.get(1).equals(gender) && !rec.get(0).equals(name)){
                totalRankedHigher += Integer.parseInt(rec.get(2));
            }
            
        }
        return totalRankedHigher;
    }  
    
    public void testGetTotalBirthsRankedHigher(){
        System.out.println(getTotalBirthsRankedHigher(2012, "Ethan", "M"));
        System.out.println(getTotalBirthsRankedHigher(2012, "Sophia", "F"));
        System.out.println(getTotalBirthsRankedHigher(2012, "Ethan", "F"));
        System.out.println(getTotalBirthsRankedHigher(2013, "Noah", "M"));
        System.out.println(getTotalBirthsRankedHigher(2012, "Ava", "M"));
        System.out.println(getTotalBirthsRankedHigher(2014, "Liam", "M"));
    }
    
    public double getAverageRank(String name, String gender){
        double ranks = 0.0;
        int countFiles = 0;
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            String fileName = f.getName();
            int year = Integer.parseInt(fileName.substring(3, 7));
            int rank = getRank(year, name, gender);
            if(rank == -1) continue;
            ranks += rank;
            countFiles += 1;
            
        }
        if (ranks == 0) return -1.0;
        
        return ranks / countFiles;
    }
    
    public void testGetAverageRank(){
        System.out.println(getAverageRank("Isabella", "F"));
        System.out.println(getAverageRank("Mason", "M"));
        System.out.println(getAverageRank("Jacob", "M"));
        System.out.println(getAverageRank("Mason", "F"));
    }
    
    public int yearOfHighestRank(String name, String gender){
        Integer highestRank = null;
        int yearOfHighestRank = 0;
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            String fileName = f.getName();
            int year = Integer.parseInt(fileName.substring(3, 7));
            int rank = getRank(year, name, gender);
            if(rank == -1) continue;
            if(highestRank == null){
                highestRank = rank;
                yearOfHighestRank = year;
            }
            
            if(rank > 0 && rank < highestRank){
                highestRank = rank;
                yearOfHighestRank = year;
            }
        }
        return yearOfHighestRank;
    }
    
    public void testYearOfHighestRank(){
        System.out.println(yearOfHighestRank("Isabella", "F"));
        System.out.println(yearOfHighestRank("Mason", "M"));
        System.out.println(yearOfHighestRank("Isabella", "M"));
        System.out.println(yearOfHighestRank("Mason", "F"));
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        if(gender.equals("F")){
            System.out.println(name + " born in " + year + " would be " + newName + " if she was born in " + newYear);
            
        }else{
            System.out.println(name + " born in " + year + " would be " + newName + " if he was born in " + newYear);
        }
    }
    
    public String getName(int year, int rank, String gender){
        FileResource fr = new FileResource("us_babynames/us_babynames_test/yob" + year +"short.csv");
        int count = 0;
        for(CSVRecord rec: fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
                count += 1;
                if(count == rank){
                    return rec.get(0);
                }
            }
        }
        return "NO NAME";
    }
    
    public int getRank(int year, String name, String gender){
        int rank = 0;
        FileResource fr = new FileResource("us_babynames/us_babynames_test/yob" + year +"short.csv");
        for(CSVRecord rec: fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
                if(rec.get(0).equals(name)){
                    rank += 1;
                    return rank;
                }
                else {
                    rank += 1;
                }
            }
        }
        return -1;
    }
    
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
    
    public void testGetRank(){
        String name = "Olivia";
        String gender = "M";
        int year = 2013;
        System.out.println(name + " is ranked at: " + getRank(year, name, gender));
    }
    
    public void testGetName(){
        String gender = "M";
        int year = 2013;
        int rank = 4;
        System.out.println("The name of the person at rank " + rank + " is " + getName(year, rank, gender));
    }
    
    public void testWhatIsNameInYear(){
        String name = "Isabella";
        int year = 2012;
        int newYear = 2013;
        String gender = "F";
        whatIsNameInYear(name, year, newYear, gender);
        
        name = "harout";
        year = 2013;
        newYear = 2014;
        gender = "M";
        whatIsNameInYear(name, year, newYear, gender);
        
    }
    
}
