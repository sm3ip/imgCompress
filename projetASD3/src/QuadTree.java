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

    public int trueQT(){
        //TODO: switch it to QT
        if (this.getCurrLum()==-1){
            int l1 = this.getV1().trueQT();
            int l2 = this.getV2().trueQT();
            int l3 = this.getV3().trueQT();
            int l4 = this.getV4().trueQT();
            if(l1!=-1 && l1==l2 && l2==l3 && l3==l4){
                // it'll be a method deleting every child and setting curLum as the int given in param
                this.abandonChildren(l1); // TODO: implement it in QT
            }
        }
        return this.getCurrLum();
        // DONE: if currLum ==-1 goes down a level (on all 4 children) recursively
        // DONE: if none = -1 and all equal simplify then returns own value
        // DONE: else returns -1
        return 0;
    }

    public void lambdaCompr(){
        //TODO: super the one from QT (its implementation here is maybe useless)
        //TODO: See method from page 4 of the project specifications
        //TODO: check if all 4 children don't have children
        //TODO: if verified do the compression
        //TODO: else recursive through the children with child(ren)
    }

    // Rho compression methods
    //TODO: evaluate epsilon gotta add the var in QT and the method in there aswell
    //TODO: find the smallest espilon
    public int getKnot(){
        // might have to be solely implemented in QT
        //TODO: if currLum = -1 returns 1 + getKnot of each child else returns 1
        return (this.getCurrLum()==-1) ? (1+ this.getV1().getKnot() + this.getV2().getKnot() + this.getV3().getKnot() + this.getV4().getKnot()):(1);
    }
    public void rhoCompr(int p){
        //TODO: get amount of knot
        //TODO: while p> currAmountKnot/prevAmountKnot*100
        //TODO: find the smallest epsilon and do the basic compression implemented through QT
        //TODO: update all epsilon or only the parent's one
        //TODO: update currAmountKnot
    }


    private int qtToPgm(){
        //TODO: go through the QT returns array of tuple[coords pt, lum, height],
        //TODO: read this array and write it into the 2d array
        //TODO: w/ the 2d array and previous data write the PGM file
        return 0;
    }

}
