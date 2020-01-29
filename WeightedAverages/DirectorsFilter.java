package Capstone.WeightedAverages;


/**
 * Write a description of class DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DirectorsFilter implements Filter{
    private String [] myDir;

    public DirectorsFilter(String director) {
        myDir= director.split(",");
    }

    @Override
    public boolean satisfies(String id) {
        String tmp =  MovieDatabase.getDirector(id);
        String [] dirs = tmp.split(",");
        for (int idx = 0;idx < myDir.length;idx ++){
            for (int k =0;k<dirs.length;k++){
                if ( myDir[idx].toLowerCase().equals(  dirs[k].toLowerCase().trim() ) ){
                    return  true;
                }
            }
        }
        return false;
    }   
}
