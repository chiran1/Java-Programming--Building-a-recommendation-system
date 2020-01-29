package Capstone.PeerGradedAssignment;


/**
 * Write a description of class ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class ThirdRatings {
    private ArrayList<Rater> myRaters;

    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings( String ratingsfile ){
        FirstRatings fRO = new FirstRatings();
        myRaters = fRO.loadRaters(ratingsfile);
    }

    public int getRaterSize(){
        return myRaters.size();
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());

        for ( String id : movies ){// compute the average rating for each movie
            double avgR = getAverageByID(id, minimalRaters );
            Rating avgRating = new Rating (id,avgR);
            avgRatingDat.add(avgRating);
        }

        return avgRatingDat;
    }

    public ArrayList <Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList <Rating> avgRatingDat = new ArrayList<Rating> ();
        ArrayList <String> movieID = MovieDatabase.filterBy( filterCriteria );

        int [] rCnt = new int [movieID.size()];
        for ( int k=0;k<movieID.size();k++ ){
            for (Rater ratDat : myRaters){
                if ( ratDat.getRating( movieID.get(k) ) > 0.0 ){
                    rCnt[k] = rCnt[k]+1;
                }
            }
        }

        for ( int k=0;k<movieID.size();k++ ){
            if (rCnt[k] >= minimalRaters){
                double avgR = getAverageByID(movieID.get(k), minimalRaters );
                Rating avgRating = new Rating (movieID.get(k),avgR);
                avgRatingDat.add(avgRating);
            }
        }
        return avgRatingDat;
    }

}
