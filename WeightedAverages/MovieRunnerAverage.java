package Capstone.WeightedAverages;


/**
 * Write a description of class MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerAverage {

    public double findMaxRating( ArrayList <Rating> avgRatings ){
        double maxRating =0.0;
        for (Rating ratDat : avgRatings){
            if ( ratDat.getValue() > maxRating ){
                maxRating = ratDat.getValue();
            }
        }
        return maxRating;
    }

    public double findMinRating( ArrayList <Rating> avgRatings ){
        double maxRating = findMaxRating( avgRatings );
        double minRating =maxRating;
        for (Rating ratDat : avgRatings){
            if ( ( ratDat.getValue() !=0 ) &&  ( ratDat.getValue() < minRating ) ){
                minRating = ratDat.getValue();
            }
        }
        return minRating;
    }

    public ArrayList <Rating> sortAvgRatings( ArrayList <Rating> avgRatings ){

        ArrayList <Rating> sortedRatings = new ArrayList <Rating>();// init
        ArrayList <Rating> tmpRatings = avgRatings;// make tmp copy

        double minRating = findMinRating( avgRatings );// get minimum value

        while( true ){
            for (int idx = 0;idx<tmpRatings.size();idx++){
                Rating rDat = tmpRatings.get(idx);
                if (rDat.getValue() !=0){
                    if (rDat.getValue() == minRating){
                        sortedRatings.add( tmpRatings.get( idx ) );
                        tmpRatings.remove( idx );
                        minRating = findMinRating( tmpRatings );
                    }   
                } else {
                    tmpRatings.remove( idx );
                }
            }
            if ( tmpRatings.size() == 0){
                break;
            }
        }
        return sortedRatings;
    }

    public void getAverageRatingOneMovie(){
        //String movieName   = "ratedmovies_short.csv";
        //String ratingsName = "ratings_short.csv";
        String movieName   = "ratedmoviesfull.csv";
        String ratingsName = "ratings.csv";
        SecondRatings sRO = new SecondRatings(movieName,ratingsName);
        
        String desiredTitle = "Vacation";
        
        ArrayList <Rating> avgRatings = sRO.getAverageRatings( 3 );
        for ( Rating rDat : avgRatings ){
            String itemVal = rDat.getItem();
            String movTitle = sRO.getTitle(itemVal);
            
            if ( desiredTitle.equals( movTitle ) ){
                System.out.println("Average rating for "+movTitle+" :"+rDat.getValue() );
            }
        }
        
        
    }
    
    public void printAverageRatings(){

        String movieName   = "ratedmoviesfull.csv";
        String ratingsName = "ratings.csv";
        SecondRatings sRO = new SecondRatings(movieName,ratingsName);
        
        FirstRatings fRO = new FirstRatings();
        ArrayList <Movie> movDat = fRO.loadMovies(movieName);
        ArrayList <Rater> ratDat = fRO.loadRaters(ratingsName);
        
        // gets the IDs of all the movies in the database
        ArrayList <String> movID = new ArrayList <String>();
        for (Movie mov : movDat){
            if ( ! movID.contains( mov.getID() ) ){
                movID.add(mov.getID());
            }
        }
        
        // counts the number of ratings per movie ID
        int [] ratCnt = new int[movID.size()];
        for (Rater rat : ratDat){
            int cnt = 0;
            ArrayList <String> ratings = rat.getItemsRated();
            
            for (String s : ratings){
                if ( ( movID.contains( s ) ) && ( rat.hasRating( s ) ) && ( rat.getRating(s) !=0 ) ){
                    cnt = cnt+1;
                    ratCnt[ movID.indexOf(s) ] = cnt;
                }
            }
        }
        
        // counts the number of movies > 50 ratings
        int threshold = 50;
        int cntMvs = 0;
        for (int k=0;k<ratCnt.length;k++){
            
            if (ratCnt[k]>=threshold){
                cntMvs = cntMvs +1;
                
            }
            
        }
        
        ArrayList <Rating> avgRatings2 = sRO.getAverageRatings( threshold );
        ArrayList <Rating> avgRatings = sRO.getAverageRatings( 20 );
        ArrayList <Rating> sortedRatings = sortAvgRatings( avgRatings );
        ArrayList <Rating> sortedRatings2 = sortAvgRatings( avgRatings2 );
        
        System.out.println("There are in total "+sRO.getMovieSize()+" movies");
        System.out.println("There are in total "+sRO.getRaterSize()+" ratings");
        System.out.println("There are in total "+cntMvs+" with >= "+threshold+" ratings");
        System.out.println("There are in total "+sortedRatings2.size()+" with >= "+threshold+" ratings");
        System.out.println("Printing ratings in ascending order:");
        for ( Rating rDat : sortedRatings ){
            System.out.println( "Movie ID :" + sRO.getTitle( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }
    }

}
