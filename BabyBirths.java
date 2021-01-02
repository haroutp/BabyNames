
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
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        
        int totalRankedHigher = 0;
        int rank = getRank(year, name,gender);
        if(rank == -1){
            return totalRankedHigher;
        }
        for(CSVRecord rec: fr.getCSVParser(false)){
           
            
            if(rec.get(1).equals(gender)){
                if(rec.get(0).equals(name)){
                    break;
                }
                totalRankedHigher += Integer.parseInt(rec.get(2));
            }
            
        }
        return totalRankedHigher;
    }  
    
    public void testGetTotalBirthsRankedHigher(){
        System.out.println(getTotalBirthsRankedHigher(1990, "Emily", "F"));
        System.out.println(getTotalBirthsRankedHigher(1990, "Drew", "M"));
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
        System.out.println(getAverageRank("Susan", "F"));
        System.out.println(getAverageRank("Robert", "M"));
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
        System.out.println(yearOfHighestRank("Mich", "M"));
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
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year +".csv");
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
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year +".csv");
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
        int uniqueGirls = 0;
        int uniqueBoys = 0;
        int uniqueTotal = 0;
        for(CSVRecord rec: fr.getCSVParser(false)){
            int newBorn = Integer.parseInt(rec.get(2));
            totalBirths += newBorn;
            uniqueTotal += 1;
            if(rec.get(1).equals("M")){
                totalBoys += newBorn;
                uniqueBoys += 1;
            }
            else{
                totalGirls += newBorn;
                uniqueGirls += 1;
            }
        }
        
        System.out.println("total births = " + totalBirths);
        System.out.println("total unique names = " + uniqueTotal);
        System.out.println("total boys = " + totalBoys);
        System.out.println("total unique boy names = " + uniqueBoys);
        System.out.println("total girls = " + totalGirls);
        System.out.println("total unique girl names = " + uniqueGirls);
        
    }
    
    public void testTotalBirths(){
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    
    public void testGetRank(){
        String name = "Frank";
        String gender = "M";
        int year = 1971;
        System.out.println(name + " is ranked at: " + getRank(year, name, gender));
    }
    
    public void testGetName(){
        String gender = "M";
        int year = 1982;
        int rank = 450;
        System.out.println("The name of the person at rank " + rank + " is " + getName(year, rank, gender));
    }
    
    public void testWhatIsNameInYear(){
        String name = "Susan";
        int year = 1972;
        int newYear = 2014;
        String gender = "F";
        whatIsNameInYear(name, year, newYear, gender);
        
        name = "Jack";
        year = 1995;
        newYear = 1991;
        gender = "M";
        whatIsNameInYear(name, year, newYear, gender);
        
    }
    
}
