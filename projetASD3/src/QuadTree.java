public class QuadTree extends QT {
    private int maxLum;
    private int size;
    private int[][] tab;

    private String fileRoute; // the pgm file's location

    public QuadTree(String file){
        super();
        setFileRoute(file);
        //TODO: implement the bullshit to retrieve the other data from the file ( might need to call the function pgmToQT)
    }

    // getters

    public int getMaxLum() {
        return maxLum;
    }

    public int getSize() {
        return size;
    }

    public int[][] getTab() {
        return tab;
    }

    public String getFileRoute() {
        return fileRoute;
    }

    // setters

    private void setMaxLum(int maxLum) {
        this.maxLum = maxLum;
    }

    private void setSize(int size) {
        this.size = size;
    }

    private void setTab(int[][] tab) {
        this.tab = tab;
    }

    private void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }

    // to_string

    public String toString(){
        // TODO: implement it as an override of QT's to string do super() and shit
        return "0";
    }

    private int pgmToQT(){
        //TODO: read file and store to temporary 2d array, read the array to build a QT
        return 0;
    }

    //TODO: check if really a QT (simplify if needed)

    //TODO: compress methods

    private int qtToPgm(){
        //TODO: go through the QT returns array of tuple[coords pt, lum, height],
        //TODO: read this array and write it into the 2d array
        //TODO: w/ the 2d array and previous data write the PGM file
        return 0;
    }

}
