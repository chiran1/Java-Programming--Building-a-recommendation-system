package Capstone.EfficientRecommender;


/**
 * Write a description of class MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
public class MovieRunnerWithFilters {

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
        String ratingsName = "ratings.csv";
        ThirdRatings tRO = new ThirdRatings(ratingsName);

        MovieDatabase.initialize("ratedmoviesfull.csv");

        int threshold = 35;
        ArrayList <Rating> avgRatings = tRO.getAverageRatings(threshold);
        ArrayList <Rating> sortedRatings = sortAvgRatings( avgRatings );

        System.out.println( "The number of raters is:" );
        System.out.println( tRO.getRaterSize() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+sortedRatings.size()+" movies with >= "+threshold+" ratings");
        System.out.println("Printing ratings in ascending order:");
        for ( Rating rDat : sortedRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }

    }

    public void printAverageRatingsByYear(){
        String ratingsName = "ratings.csv";
        ThirdRatings tRO = new ThirdRatings(ratingsName);

        MovieDatabase.initialize("ratedmoviesfull.csv");

        int yA = 2000;
        int threshold = 20;
        ArrayList <Rating> avgRatings = tRO.getAverageRatingsByFilter( threshold, new YearAfterFilter(yA) );
        ArrayList <Rating> sortedRatings = sortAvgRatings( avgRatings );

        System.out.println( "The number of raters is:" );
        System.out.println( tRO.getRaterSize() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+sortedRatings.size()+" movies with >= "+threshold+" ratings");
        System.out.println("Printing ratings in ascending order:");
        for ( Rating rDat : sortedRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }
    }

    public void printAverageRatingsByGenre(){
        String ratingsName = "ratings.csv";
        ThirdRatings tRO = new ThirdRatings(ratingsName);

        MovieDatabase.initialize("ratedmoviesfull.csv");

        String genre = "Comedy";
        int threshold = 20;
        ArrayList <Rating> avgRatings = tRO.getAverageRatingsByFilter( threshold, new GenreFilter(genre) );
        ArrayList <Rating> sortedRatings = sortAvgRatings( avgRatings );

        System.out.println( "The number of raters is:" );
        System.out.println( tRO.getRaterSize() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+sortedRatings.size()+" movies with >= "+threshold+" ratings");
        System.out.println("Printing ratings in ascending order:");
        for ( Rating rDat : sortedRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Movie genre :" + MovieDatabase.getGenres( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }
    }

    public void printAverageRatingsByMinutes(){
        String ratingsName = "ratings.csv";
        ThirdRatings tRO = new ThirdRatings(ratingsName);

        MovieDatabase.initialize("ratedmoviesfull.csv");

        int minMin = 105;
        int minMax = 135;
        int threshold = 5;
        ArrayList <Rating> avgRatings = tRO.getAverageRatingsByFilter( threshold, new MinutesFilter(minMin,minMax) );
        ArrayList <Rating> sortedRatings = sortAvgRatings( avgRatings );

        System.out.println( "The number of raters is:" );
        System.out.println( tRO.getRaterSize() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+sortedRatings.size()+" movies with >= "+threshold+" ratings");
        System.out.println("Printing ratings in ascending order:");
        for ( Rating rDat : sortedRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Movie genre :" + MovieDatabase.getGenres( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }

    }

    public void printAverageRatingsByDirectors(){
        String ratingsName = "ratings.csv";
        //String ratingsName = "ratings_short.csv";
        ThirdRatings tRO = new ThirdRatings(ratingsName);

        MovieDatabase.initialize("ratedmoviesfull.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");

        String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        //String directors = "Charles Chaplin,Michael Mann,Spike Jonze";
        
        System.out.println( "The number of raters is:" );
        System.out.println( tRO.getRaterSize() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        int threshold = 4;

        ArrayList <Rating> avgRatings = tRO.getAverageRatingsByFilter( threshold, new DirectorsFilter(directors) );
        ArrayList <Rating> sortedRatings = sortAvgRatings( avgRatings );

        System.out.println("There are in total "+sortedRatings.size()+" movies with >= "+threshold+" ratings");
        System.out.println("Printing ratings in ascending order:");

        for ( Rating rDat : sortedRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Movie Director :" + MovieDatabase.getDirector( rDat.getItem() ) );
            System.out.println( "Movie genre :" + MovieDatabase.getGenres( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }

    }

    public void printAverageRatingsByYearAfterAndGenre(){
        String ratingsName = "ratings.csv";
        ThirdRatings tRO = new ThirdRatings(ratingsName);

        MovieDatabase.initialize("ratedmoviesfull.csv");

        AllFilters allFilt = new AllFilters();
        allFilt.addFilter( new YearAfterFilter(1990) );
        allFilt.addFilter( new GenreFilter("Drama") );

        int threshold = 8;
        ArrayList <Rating> avgRatings = tRO.getAverageRatingsByFilter( threshold, allFilt );
        ArrayList <Rating> sortedRatings = sortAvgRatings( avgRatings );

        System.out.println( "The number of raters is:" );
        System.out.println( tRO.getRaterSize() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+sortedRatings.size()+" movies with >= "+threshold+" ratings");
        System.out.println("Printing ratings in ascending order:");
        for ( Rating rDat : sortedRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Movie genre :" + MovieDatabase.getGenres( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }

    }

    public void printAverageRatingsByDirectorsAndMinutes(){
        int threshold = 3;
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";

        int time1 =90;
        int time2 = 180;

        String ratingsName = "ratings.csv";
        ThirdRatings tRO = new ThirdRatings(ratingsName);

        MovieDatabase.initialize("ratedmoviesfull.csv");

        AllFilters allFilt = new AllFilters();
        allFilt.addFilter( new MinutesFilter(time1,time2) );
        allFilt.addFilter( new DirectorsFilter( directors ) );

        ArrayList <Rating> avgRatings = tRO.getAverageRatingsByFilter( threshold, allFilt );
        ArrayList <Rating> sortedRatings = sortAvgRatings( avgRatings );

        System.out.println( "The number of raters is:" );
        System.out.println( tRO.getRaterSize() );

        System.out.println( "The number of mobies in the database is:" );
        System.out.println( MovieDatabase.size() );

        System.out.println("There are in total "+sortedRatings.size()+" movies with >= "+threshold+" ratings");
        System.out.println("Printing ratings in ascending order:");
        for ( Rating rDat : sortedRatings ){
            System.out.println( "Movie ID :" + MovieDatabase.getTitle( rDat.getItem() ) );
            System.out.println( "Movie genre :" + MovieDatabase.getGenres( rDat.getItem() ) );
            System.out.println( "Director :" + MovieDatabase.getDirector( rDat.getItem() ) );
            System.out.println( "Time :" + MovieDatabase.getMinutes( rDat.getItem() ) );
            System.out.println( "Average rating :" +rDat.getValue() );
            break;
        }
    }
    
    public void testGenreFilter(){
        String ratingsName = "ratings.csv";
        ThirdRatings tRO = new ThirdRatings(ratingsName);
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        int threshold = 1;
        ArrayList <Rating> avgRatings = tRO.getAverageRatingsByFilter( threshold, new DirectorsFilter(directors) );
    }

}
