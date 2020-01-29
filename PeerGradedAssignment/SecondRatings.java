package Capstone.PeerGradedAssignment;


/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile , String ratingsfile){
        FirstRatings fRO = new FirstRatings();
        
        myMovies = fRO.loadMovies(moviefile);
        myRaters = fRO.loadRaters(ratingsfile);
    }
    
    public int getMovieSize(){
        return myMovies.size();
    }
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    public String getTitle(String id){
        for (Movie movDat : myMovies){
            if ( movDat.getID().equals(id) ){
                String title =movDat.getTitle();
                return title;
            }
        }
        
        return "ID does not exist";
    }
    
    public String getID(String title){
        for (Movie movDat : myMovies){
            if ( movDat.getTitle().equals(title) ){
                String ID =movDat.getID();
                return ID;
            }
        }
        return "NO SUCH TITLE.";
    }
    
    private double getAverageByID(String id, int minimalRaters){
        int rCnt = 0;
        double n = 0.0;
        double avgR = 0.0;
        
        for (Rater ratDat : myRaters){
            if ( ratDat.getRating(id) > 0.0 ){
                rCnt = rCnt+1;
            }
        }
        
        if (rCnt >= minimalRaters){
            for (Rater rDat : myRaters){
                if( rDat.hasRating(id) ){
                    avgR = avgR + rDat.getRating(id);
                    n = n+1.0;
                }
            }
            avgR = avgR/n;
            return avgR;
        }
        
        return avgR;
    }
    
    public ArrayList getAverageRatings(int minimalRaters){
        ArrayList <Rating> avgRatingDat = new ArrayList <Rating>();
        
        ArrayList <String> movID = new ArrayList <String>();
        for (Movie mov : myMovies){// returns the list of unique movie-IDs
            String id = mov.getID();
            if ( ! movID.contains(id) ){
                movID.add(id);
            }
        }
        
        for (String id : movID ){// compute the average rating for each movie
            double avgR = getAverageByID(id, minimalRaters );
            Rating avgRating = new Rating (id,avgR);
            avgRatingDat.add(avgRating);
        }
       
        return avgRatingDat;
        
    }
    
    
}
