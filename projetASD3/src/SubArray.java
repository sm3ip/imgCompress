import java.util.Arrays;
public class SubArray {
    public static int[][] copySubArray(int [][] origin, int orX, int orY, int size ){
        if (origin == null){
            return null;
        } else if (size ==0) {
            return new int[0][0];
        } else if (size<0 ) {
            throw new IllegalArgumentException("Size gotta be positive");
        } else if (orY+size > origin.length || orX+size > origin.length) {
            throw new IllegalArgumentException("subrange too wide or high, max height/width :" + origin.length + " current height :" + (orY+size) + " current width :" +(orX+size) );
        }
        int[][] subCopy = new int[size][size];
        for (int i = 0; i< size; i++){
            int[] oriRow = origin[orY+i];
            System.arraycopy(oriRow,orX,subCopy[i],0,size);
        }
        return subCopy;
    }
}
