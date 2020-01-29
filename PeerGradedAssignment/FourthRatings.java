package Capstone.PeerGradedAssignment;


/**
 * Write a description of class FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class FourthRatings {

    ArrayList<Rater> myRaters = RaterDatabase.getRaters();

    public FourthRatings() {
        // default constructor
    }

    private double dotProduct(Rater me, Rater r){
        ArrayList<String> listM = me.getItemsRated();
        ArrayList<String> listR = r.getItemsRated();

        ArrayList <String> comList = new ArrayList <String>();         
        for (int k = 0;k<listM.size();k++){
            for (int l = 0;l < listR.size();l++){
                if ( listM.get(k).equals(listR.get(l)) ){
                    if ( ! comList.contains(listM.get(k)) ){
                        if ( ( r.getRating(listR.get(l)) > 0.0 ) &&  (me.getRating(listM.get(k)) >0.0 ) ){
                            comList.add(listM.get(k));
                        }
                    }  
                }
            }
        }

        double [] ratingsM = new double [comList.size()];
        double [] ratingsR = new double [comList.size()];
        for (int k=0;k<comList.size();k++){
            String movID = comList.get(k); 
            ratingsM[k] = me.getRating(movID)-5.0;
            ratingsR[k] = r.getRating(movID)-5.0;
        }

        double dP = 0.0;
        for (int k=0;k<comList.size();k++){
            dP = dP + (ratingsM[k]*ratingsR[k]);
        }
        return dP;
    }

    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> simRatings = new ArrayList <Rating>();

        Rater me = RaterDatabase.getRater(id);
        for ( Rater r : RaterDatabase.getRaters() ){
            if ( ! r.getID().equals(id) ){
                if ( dotProduct(me,r) >= 0.0 ){
                    simRatings.add( new Rating( r.getID(), dotProduct(me,r) ) );
                }
            }
        }
        Collections.sort( simRatings, Collections.reverseOrder() );
        return simRatings;
    }

    public ArrayList <Rating> getSimilarRatings(String id, int numSimilarRaters,int minimalRaters){
        ArrayList <Rating> wAvg = new ArrayList <Rating>();
        ArrayList<Rating> simRatings = getSimilarities(id);

        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());

        for (String movID : movies){
            
            int rCnt = 0;
            for (int k=0;k<numSimilarRaters;k++){
                String id2 = simRatings.get(k).getItem();
                Rater r = RaterDatabase.getRater(id2);
                if (  r.getRating(movID) > 0.0 ){
                    rCnt = rCnt+1;
                }   
            }
                  
            if ( rCnt >= minimalRaters ) {
                double movSum = 0.0;
                double n = 0;
                for (int k=0;k<numSimilarRaters;k++){
                    double sim = simRatings.get(k).getValue();

                    String id2 = simRatings.get(k).getItem();
                    Rater r = RaterDatabase.getRater(id2);

                    double rat = r.getRating(movID);
                    if (rat > 0.0){
                        movSum = movSum + (sim*rat);
                        n = n+1.0;
                    }
                }                
                double wA = movSum/n;
                wAvg.add( new Rating(movID,wA) );
            } 
        }
        Collections.sort( wAvg, Collections.reverseOrder() );
        return wAvg;
    }    
   
    public ArrayList <Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters,int minimalRaters, Filter filterCriteria){
        ArrayList <Rating> wAvg = new ArrayList <Rating>();
        ArrayList<Rating> simRatings = getSimilarities(id);

        AllFilters allFilt = new AllFilters();
        allFilt.addFilter( new TrueFilter() );
        allFilt.addFilter( filterCriteria );

        ArrayList<String> movies = MovieDatabase.filterBy( allFilt );

        for (String movID : movies){
            
            int rCnt = 0;
            for (int k=0;k<numSimilarRaters;k++){
                String id2 = simRatings.get(k).getItem();
                Rater r = RaterDatabase.getRater(id2);
                if (  r.getRating(movID) > 0.0 ){
                    rCnt = rCnt+1;
                }   
            }
                  
            if ( rCnt >= minimalRaters ) {
                double movSum = 0.0;
                double n = 0;
                for (int k=0;k<numSimilarRaters;k++){
                    double sim = simRatings.get(k).getValue();

                    String id2 = simRatings.get(k).getItem();
                    Rater r = RaterDatabase.getRater(id2);

                    double rat = r.getRating(movID);
                    if (rat > 0.0){
                        movSum = movSum + (sim*rat);
                        n = n+1.0;
                    }
                }                
                double wA = movSum/n;
                wAvg.add( new Rating(movID,wA) );
            } 
        }
        Collections.sort( wAvg, Collections.reverseOrder() );
        return wAvg;
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
        System.out.println(movieID.size());
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
