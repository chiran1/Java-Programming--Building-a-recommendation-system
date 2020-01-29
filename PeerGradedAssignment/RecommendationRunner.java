package Capstone.PeerGradedAssignment;


/**
 * Write a description of class RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class RecommendationRunner implements Recommender{
    
    ArrayList <String> movieIDs= new ArrayList <String>();
    
    public ArrayList<String> getItemsToRate (){
        TrueFilter TF = new TrueFilter();
        ArrayList <String> MDB = MovieDatabase.filterBy( TF );
        
        Random rand = new Random();
        if(MDB.size() >= 20){
            while(movieIDs.size() < 20){
                String randomMovieIDFromList = MDB.get(rand.nextInt(MDB.size()));
                if(!movieIDs.contains(randomMovieIDFromList)){
                    movieIDs.add(randomMovieIDFromList);
                }
            }
        }
        return movieIDs;
    }

    public void testGetItemsToRate(){
        ArrayList<String> movieList = getItemsToRate();
        for(int i = 0; i <movieList.size(); i++){
            System.out.println(MovieDatabase.getTitle(movieList.get(i)));
        }
    }

    public void printRecommendationsFor(String webRaterID){
        //testGetItemsToRate();
        ArrayList<String> movieTitles = new ArrayList<String>();
        for (int k = 0;k<movieIDs.size();k++){
            movieTitles.add( MovieDatabase.getTitle(movieIDs.get(k)).toLowerCase() );
        }
        
        FourthRatings FR = new FourthRatings();
        TrueFilter TF = new TrueFilter();
        int numOfSimilarRaters = 5;
        int minimalRaters = 1;
        ArrayList<Rating> list = FR.getSimilarRatingsByFilter(webRaterID, numOfSimilarRaters, minimalRaters, TF);
        
        ArrayList<String> list2 = new ArrayList<String>();
        for ( int k=0;k < list.size();k++ ){
            if ( ! movieTitles.contains( MovieDatabase.getTitle( list.get(k).getItem().toLowerCase() ) ) ){
                list2.add(list.get(k).getItem());
            }
        }
        
        ArrayList<String> RecommendedMovies = new ArrayList<String>();
        if(list2.size() >= 10){
            for(int i = 0; i < 10; i++){
                RecommendedMovies.add(list2.get(i));
                //System.out.println( MovieDatabase.getTitle(list2.get(i)) );
            }
        }
        
        if(RecommendedMovies.isEmpty()){
            System.out.println("Not enough movies to recommend for this user!");
        }
        else{
            System.out.println("<h2>You may also like (click movie for details)</h2>");
            System.out.println("<table style=width:80%>");
            //System.out.println("<tr>");
            //System.out.println("<th> </th><th>Title</th><th>Year</th>");
            //System.out.println("</tr>");
            System.out.println("<tr>");
            int count2 = 0;
            for(int count = 0; count < RecommendedMovies.size(); count++){
                count2 = count2+1;
                String moviePoster = MovieDatabase.getPoster(RecommendedMovies.get(count));
                String movieTitle = MovieDatabase.getTitle(RecommendedMovies.get(count));
                String movieLink = "https://www.imdb.com/title/tt"+ MovieDatabase.getMovie(RecommendedMovies.get(count)).getID() +"/";
                
                if ( moviePoster.indexOf(".jpg")!=-1 ){
                    System.out.println(
                    "<td>" + "<a href=\""+movieLink+"\"> "+"<img src=\"" + moviePoster + "\" alt=\""+ movieTitle +"\" width=\"200\" height=\"275\">" +"</td>"                
                    );           
                    
                    
                } else{
                    System.out.println(
                    "<td>" + movieTitle +"</td>"                
                    );
                }
                
                //"<td>"+ "<img src=" + Poster + " width=75 height=150>" +"</td>"+
                //"<td>"+MovieDatabase.getTitle(RecommendedMovies.get(count).getItem()) +"</td>"+
                //"<td>"+ MovieDatabase.getYear(RecommendedMovies.get(count).getItem())+"</td>");
                //System.out.println("</tr>");
                
                if (count2 == 5){
                    System.out.println("</tr>");
                    System.out.println("<tr>");
                }
            }
            System.out.println("</tr>");
            System.out.println("</table>");
        }
    }
    
    public void testPrintRecommendationsFor(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        printRecommendationsFor("71");
        /*Random rand = new Random();
        printRecommendationsFor(Integer.toString(rand.nextInt(RaterDatabase.size())));*/
    }
    
}

