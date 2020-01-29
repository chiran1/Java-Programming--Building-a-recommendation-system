package Capstone.EfficientRecommender;


/**
 * Write a description of class GenreFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class GenreFilter implements Filter {
    private String myGenre;
    public GenreFilter(String genre) {
        myGenre = genre;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getGenres(id).indexOf( myGenre )!= -1;
    }
}
