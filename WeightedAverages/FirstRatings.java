package Capstone.WeightedAverages;


/**
 * Write a description of class FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {

    public ArrayList <Movie> loadMovies(String filename){
        ArrayList <Movie> movieList = new ArrayList <Movie>();

        FileResource fr = new FileResource("data/"+filename);// FileResource object
        CSVParser parser = fr.getCSVParser();// new Class from Apache library
        for (CSVRecord record : parser){ // CSVRecord class

            String ID = record.get("id");
            String Title = record.get("title");
            String Year = record.get("year");
            String Genres = record.get("genre");
            String Director = record.get("director");
            String Country = record.get("country");
            String Poster = record.get("poster");
            int Minutes = Integer.parseInt( record.get("minutes") );
            
            Movie movieDat = new Movie(ID,Title,Year,Genres,Director,
                                                         Country,Poster,Minutes);
            movieList.add(movieDat);
        }
        
        return movieList;
    }
    
    public ArrayList <Rater> loadRaters( String filename ){
        ArrayList <Rater> raterList = new ArrayList <Rater>();
        ArrayList <String> raterID = new ArrayList <String>();
        FileResource fr = new FileResource("data/"+filename);// FileResource object
        CSVParser parser = fr.getCSVParser();// new Class from Apache library
        
        for (CSVRecord record : parser){ // CSVRecord class
            if ( raterID.indexOf(record.get("rater_id")) ==-1 ){
                raterID.add( record.get("rater_id") );
            }
        }
        
        for (String id : raterID){
            Rater raterDat = new EfficientRater(id);
            CSVParser parser2 = fr.getCSVParser();// new Class from Apache library
            for (CSVRecord record : parser2){
                if ( record.get("rater_id").equals(id) ){
                    raterDat.addRating( record.get("movie_id"),Double.parseDouble( record.get("rating")) );
                }
            }
            raterList.add(raterDat);
        }
        
        return raterList;
    }
    
    public void testLoadMovies(){
        //ArrayList <Movie> movieDat = loadMovies("ratedmovies_short.csv");
        ArrayList <Movie> movieDat = loadMovies("ratedmoviesfull.csv");
        int comedyCnt =0;
        int minutesCnt =0;
        HashMap <String, ArrayList <String> > movDir = new HashMap <String, ArrayList <String> >();
        for (Movie mov : movieDat){
            if ( mov.getGenres().indexOf("Comedy")!=-1 ){
                comedyCnt =comedyCnt+1;
            }
            if ( mov.getMinutes()>150 ){
                minutesCnt =minutesCnt+1;
            }
            if ( ! movDir.containsKey( mov.getDirector() )){
                ArrayList <String> dirList = new ArrayList <String>();
                dirList.add(mov.getTitle());
                movDir.put(mov.getDirector(),dirList);
            } else {
                ArrayList <String> dirList = movDir.get(mov.getDirector());
                dirList.add(mov.getTitle());
                movDir.put(mov.getDirector(),dirList);
            }
        }
        
        int maxMovN = 0;
        for ( String key : movDir.keySet() ){
            ArrayList <String> list = movDir.get(key);
            if ( list.size() > maxMovN ) {
                maxMovN = list.size();
            }
        }
        
        int dirMax = 0;
        ArrayList <String> maxDirList = new ArrayList <String> ();
        for ( String key : movDir.keySet() ){
            ArrayList <String> list = movDir.get(key);
            if ( list.size() == maxMovN ) {
                dirMax = dirMax + 1;
                maxDirList.add(key);
            }
        }
        
        System.out.println("The total number of movies is :" + movieDat.size());
        System.out.println("The number of Comedy movies is :" + comedyCnt);
        System.out.println("The number of movies > than 150 min :" + minutesCnt);
        System.out.println("The maximum number of movies by any director :" + maxMovN);
        System.out.println("There are "+dirMax+" directors that directed one such movie ");
        for (String dir : maxDirList){
            System.out.println(dir);
        }
    }
    
    public void testLoadRaters(){
        //ArrayList <Rater> raterDat = loadRaters( "ratings_short.csv" );
        ArrayList <Rater> raterDat = loadRaters( "ratings.csv" );
        
        ArrayList <String> ratedMovs = new ArrayList <String>();
        String chosenRater = "193";
        String chosenMov = "1798709";
        int movRatingCnt = 0;
        double chosenRaterCnt = 0;
        double maxRatings = 0.0;
        for (Rater dat : raterDat){
            
            ArrayList <String> movList = dat.getItemsRated();
            for (String s : movList){
                if ( ! ratedMovs.contains(s) ){
                    ratedMovs.add(s);
                }
            }
            
            if ( movList.indexOf(chosenMov) !=-1){
                movRatingCnt = movRatingCnt+1;
            }
            
            if (dat.numRatings()>maxRatings) {
                maxRatings = dat.numRatings();
            }
            
            if ( dat.getID().equals(chosenRater) ){
                chosenRaterCnt = dat.numRatings();
            }
        }
        
        int maxRaterCnt = 0;
        ArrayList <String> maxRaterID = new ArrayList <String>();
        for ( Rater dat : raterDat ){
        
            if ( dat.numRatings() == maxRatings ){
                maxRaterCnt = maxRaterCnt+1;
                maxRaterID.add( dat.getID() );
            }
        }
        
        System.out.println( "Total raters : "+raterDat.size() );
        System.out.println("Total movies rated :"+ratedMovs.size());
        System.out.println("The movie "+chosenMov+" was rated by "+movRatingCnt+" raters");
        System.out.println("Rater " + chosenRater+" has "+chosenRaterCnt +" ratings");
        System.out.println("Maximum number of ratings of all raters :" + maxRatings);
        System.out.println("There is only " + maxRaterCnt +" rater with "+maxRatings+" ratings.");
        for (String s : maxRaterID){
            System.out.println( s );
        }
    }
}
