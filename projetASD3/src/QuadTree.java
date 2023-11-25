public class QuadTree extends QT {
    private int maxLum;
    private int size;
    private int[][] tab;

    private String fileRoute; // the pgm file's location

    // herits from QT as the og knot
    //TODO: constructor, getters, setters, toString,
    public QuadTree(String file){
        super();
        this.fileRoute = file;
        //TODO: do the bullshit to retrieve the other data from the file ( might need to call the function pgmToQT)
    }
    

    //TODO: PgmToQT(read file and store to 2d array, read the array to build a QT), QTToPgm(route returns tab of data,
    //TODO: read this array of tuples[coords pt, lum, height] and write it into the 2d array, w/ the 2d array and previous
    //TODO: data write the PGM file)
    //TODO: compress methods
    //TODO: check if really a QT (simplify if needed)
}
