package Capstone.PeerGradedAssignment;


/**
 * Write a description of class MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
public class MovieRunnerSimilarRatings {


    public void printSimilarRatings(){
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        
        FourthRatings fRO = new FourthRatings();
        
        String id = "71";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        ArrayList <Rating> avgRatings = fRO.getSimilarRatings(id, numSimilarRaters, minimalRaters);
        Collections.sort( avgRatings, Collections.reverseOrder() );

        System.out.println( "The number of raters is:" );
        System.out.println( RaterDatabase.size() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+avgRatings.size()+" movies with >= "+minimalRaters+" ratings");
        System.out.println("Printing ratings in descending order:");
        for ( Rating rDat : avgRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }

    }

    public void printSimilarRatingsByGenre(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        
        FourthRatings fRO = new FourthRatings();
        
        String id = "964";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        String genre = "Mystery";
        
        ArrayList <Rating> avgRatings = fRO.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new GenreFilter(genre));
        Collections.sort( avgRatings, Collections.reverseOrder() );

        System.out.println( "The number of raters is:" );
        System.out.println( RaterDatabase.size() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+avgRatings.size()+" movies with >= "+minimalRaters+" ratings");
        System.out.println("Printing ratings in descending order:");
        for ( Rating rDat : avgRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }

    }
    
    public void printSimilarRatingsByDirector(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        
        FourthRatings fRO = new FourthRatings();
        
        String id = "120";
        int numSimilarRaters = 10;
        int minimalRaters = 2;
        String director = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        
        ArrayList <Rating> avgRatings = fRO.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new DirectorsFilter(director));
        Collections.sort( avgRatings, Collections.reverseOrder() );

        System.out.println( "The number of raters is:" );
        System.out.println( RaterDatabase.size() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+avgRatings.size()+" movies with >= "+minimalRaters+" ratings");
        System.out.println("Printing ratings in descending order:");
        for ( Rating rDat : avgRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }

    }
    
    public void printSimilarRatingsByGenreAndMinutes(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        
        FourthRatings fRO = new FourthRatings();
        
        String id = "168";
        int numSimilarRaters = 10;
        int minimalRaters = 3;
        String Genre = "Drama";
        int minMin =80;
        int minMax =160;
        
        AllFilters allFilt = new AllFilters();
        allFilt.addFilter( new GenreFilter(Genre) );
        allFilt.addFilter( new MinutesFilter(minMin,minMax) );
        
        ArrayList <Rating> avgRatings = fRO.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, allFilt );
        Collections.sort( avgRatings, Collections.reverseOrder() );

        System.out.println( "The number of raters is:" );
        System.out.println( RaterDatabase.size() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+avgRatings.size()+" movies with >= "+minimalRaters+" ratings");
        System.out.println("Printing ratings in descending order:");
        for ( Rating rDat : avgRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }
    
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        
        FourthRatings fRO = new FourthRatings();
        
        String id = "314";
        int numSimilarRaters = 10;
        int minimalRaters = 5;
        int year = 1975;
        int minMin =70;
        int minMax =200;
        
        AllFilters allFilt = new AllFilters();
        allFilt.addFilter( new YearAfterFilter(year) );
        allFilt.addFilter( new MinutesFilter(minMin,minMax) );
        
        ArrayList <Rating> avgRatings = fRO.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, allFilt );
        Collections.sort( avgRatings, Collections.reverseOrder() );

        System.out.println( "The number of raters is:" );
        System.out.println( RaterDatabase.size() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+avgRatings.size()+" movies with >= "+minimalRaters+" ratings");
        System.out.println("Printing ratings in descending order:");
        for ( Rating rDat : avgRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }
    
    }
}
