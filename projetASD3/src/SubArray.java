import java.util.Arrays;
public class SubArray {
    /** Copies a subrange of a 2d array
    *
     * @param origin the original 2d array from which we want to copy
     * @param orX the value in the x-axis of the point determining the zone to copy
     * @param orY the value in the y-axis of the point determining the zone to copy
     * @param size the size of the zone to copy
     * @return a 2d array of size : sizexsize
    * */
    public static int[][] copySubArray(int [][] origin, int orX, int orY, int size ){
        // first we take care of peculiar cases where our approach doesn't necessarily work
        if (origin == null){
            return null;
        } else if (size ==0) {
            return new int[0][0];
        } else if (size<0 ) {
            throw new IllegalArgumentException("Size gotta be positive");
        } else if (orY+size > origin.length || orX+size > origin.length) {
            throw new IllegalArgumentException("subrange too wide or high, max height/width :" + origin.length + " current height :" + (orY+size) + " current width :" +(orX+size) );
        }
        // If we fall in the cases where we can go through the array to copy it,
        // we first create an empty array of the corresponding size and then copy row by row (while adjusting the row's size)
        int[][] subCopy = new int[size][size];
        for (int i = 0; i< size; i++){
            int[] oriRow = origin[orX+i];
            System.arraycopy(oriRow,orY,subCopy[i],0,size);
        }
        return subCopy;
    }
}
