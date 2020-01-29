package Capstone.WeightedAverages;


/**
 * Write a description of class MinutesFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinutesFilter implements Filter{
    private int myMinMin;
    private int myMinMax;
    
    public MinutesFilter(int min, int max) {
        myMinMin = min;
        myMinMax = max;
    }
    
    @Override
    public boolean satisfies(String id) {
        return ( MovieDatabase.getMinutes(id) >= myMinMin ) && ( MovieDatabase.getMinutes(id) <= myMinMax );
    }
}
