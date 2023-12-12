/** Represents the basic structure of a quadtree
 */
public class QT {
    private int currLum, selfLamb;
    private QT V1, V2, V3, V4, parent;
    private float selfEpsi;
    private int selfHeight; // might be useless

    //constructor

    /** Creates a basic QuadTree
     * only selfLamb and selfEpsi are initialized because the other values will be set through other processes
     */
    public QT(){
        this.selfLamb =-1;
        this.selfEpsi=-1;
    }

    /** Basic QT constructor taking an int as a parameter
     *
     * @param x the epsilon that this QT has as a value
     */
    public QT(int x){
        this.selfEpsi = x;
    }

    // getters

    /** Gets this node's luminosity
     *
     * @return the luminosity
     */
    public int getCurrLum() { return currLum; }

    /** Gets this node's lambda value
     *
     * @return lambda
     */
    public int getSelfLamb() {
        return selfLamb;
    }

    /** Gets this node's epsilon value
     *
     * @return epsilon
     */
    public float getSelfEpsi() {
        return selfEpsi;
    }

    /** Gets the top left-hand child
     *
     * @return child 1
     */
    public QT getV1() { return V1; }

    /** Gets the top right-hand child
     *
     * @return child 2
     */
    public QT getV2() { return V2; }

    /** Gets the bottom right-hand child
     *
     * @return child 3
     */
    public QT getV3() { return V3; }

    /** Gets the bottom left-hand child
     *
     * @return child 4
     */
    public QT getV4() { return V4; }

    /** retrieves this node's parent
     *
     * @return the parent
     */
    public QT getParent() {
        return parent;
    }

    /** Get this node's height
     *
     * @return the depth at wich this node is
     */
    public int getSelfHeight() { return selfHeight; }

    // setters

    /** sets the value of the parent node
     *
     * @param parent a quadtree
     */
    public void setParent(QT parent) {
        this.parent = parent;
    }

    /** Sets this node's luminosity value
     *
     * @param currLum an int representing the luminosity
     */
    public void setCurrLum(int currLum) { this.currLum = currLum; }

    /** Sets this node's top-left child
     *
     * @param v1 a quadtree
     */
    public void setV1(QT v1) {
        V1 = v1;
        if (this.V1!=null) {
            this.V1.setParent(this);
        }
    }

    /** Sets this node's top-right child
     *
     * @param v2 a quadtree
     */
    public void setV2(QT v2) {
        V2 = v2;
        if (this.getV2()!=null) {
            this.V2.setParent(this);
        }
    }

    /** Sets this node's bottom-right child
     *
     * @param v3 a quadtree
     */
    public void setV3(QT v3) {
        V3 = v3;
        if (this.getV3()!=null) {
            this.V3.setParent(this);
        }
    }

    /** Sets this node's bottom-left child
     *
     * @param v4 a quadtree
     */
    public void setV4(QT v4) {
        V4 = v4;
        if (this.V4!=null) {
            this.V4.setParent(this);
        }
    }

    /** Sets this node's height
     *
     * @param selfHeight an int representing a height
     */
    public void setSelfHeight(int selfHeight) { this.selfHeight = selfHeight; }

    /** returns a String representing the quadTree under a format with which it is possible to rebuild a quadtree
     * (V1;V2;V3;V4)
     *
     * @return a string representing this quadtree
     */
    @Override
    public String toString() {
        return (this.getCurrLum()==-1) ? ("("+this.getV1().toString()+";"+this.getV2().toString()+";"+this.getV3().toString()+";"+this.getV4().toString()+")"):(Integer.toString(this.getCurrLum()));
    }

    /** Determines (for the whole tree) the node's epsilon and lambda values
     *
     * @return this node's lambda value because the parent node needs it for its lambda and epsilon computations
     */
    public int determineEpsiLamb(){
        if (this.currLum==-1){ // if this node has children
            // goes to the deepest parts so the children get their own lambda
            // And retrieves the children's lambda
            int l1 = this.getV1().determineEpsiLamb();
            int l2 = this.getV2().determineEpsiLamb();
            int l3 = this.getV3().determineEpsiLamb();
            int l4 = this.getV4().determineEpsiLamb();
            // compute this node's lambda and epsilon
            //lambda computation
            double lambdaComputation = Math.exp(0.25 * ((Math.log(0.1 + l1)) + (Math.log(0.1 + l2)) + (Math.log(0.1 + l3)) + (Math.log(0.1 + l4))));
            this.selfLamb = (int) Math.round(lambdaComputation);
            //epsilon computation
            float lambda = (float) lambdaComputation;
            float mLamb12 = Math.max(Math.abs(lambda-l1),Math.abs(lambda-l2));
            float mLamb34 = Math.max(Math.abs(lambda-l3), Math.abs(lambda-l4));
            this.selfEpsi = Math.max(mLamb12,mLamb34);
        }else { // if the node doesn't have children we set its lambda value to its own luminosity
            this.selfLamb = this.currLum;
        }
        return this.selfLamb;
    }

    /** Checks if all values in a 2D array are equal
     *
     * @param tab a 2D-array composed of int
     * @return true if all values are equal, false if otherwise
     */
    public boolean isTabAllEqual(int[][] tab){
        int tempVal = tab[0][0];
        for (int[] ints : tab) {
            for (int j = 0; j < tab.length; j++) {
                if (tempVal != ints[j]){
                    return false;
                }
            }
        }
        return true;
    }

    /** Reads a 2D array and stores it in the QuadTree
     *
     * @param tab the 2D-array containing the picture's data
     * @param height the node's height
     */
    public void arrToQT(int[][] tab, int height,QT parent){
        this.selfHeight = height;
        this.parent = parent;
        // checks if all values in the array are equal
        boolean isEqual = isTabAllEqual(tab);
        if (isEqual){ // this node won't have children
            this.abandonChildren(tab[0][0]);
        }else {
            // if values aint equal we call the function recursively on the children
            this.currLum = -1;
            height++;
            this.V1 = new QT();
            this.V1.arrToQT(SubArray.copySubArray(tab,0,0,tab.length/2), height,this);
            this.V2 = new QT();
            this.V2.arrToQT(SubArray.copySubArray(tab,tab.length/2,0,tab.length/2), height,this);
            this.V3 = new QT();
            this.V3.arrToQT(SubArray.copySubArray(tab,tab.length/2,tab.length/2,tab.length/2), height,this);
            this.V4 = new QT();
            this.V4.arrToQT(SubArray.copySubArray(tab,0,tab.length/2,tab.length/2), height,this);
        }
    }

    /** Sets this node's luminosity value and sets childs as null
     *
     * @param lum the luminosity
     */
    public void abandonChildren(int lum){
        setCurrLum(lum);
        if (this.getV1()!=null) {
            this.getV1().setParent(null);
            setV1(null);
        }
        if (this.getV2()!=null) {
            this.getV2().setParent(null);
            setV2(null);
        }
        if (this.getV3()!=null) {
            this.getV3().setParent(null);
            setV3(null);
        }
        if (this.getV4()!=null) {
            this.getV4().setParent(null);
            setV4(null);
        }
    }

    /** Checks if this quadtree has the correct format to be considered as a quadtree
     *
     * @return this node's luminosity (so the previous call can retrieve this data)
     */
    public int trueQT(){
        if (this.getCurrLum()==-1){
            // if this node has children we call the function upon them and retrieve their luminosity
            int l1 = this.getV1().trueQT();
            int l2 = this.getV2().trueQT();
            int l3 = this.getV3().trueQT();
            int l4 = this.getV4().trueQT();
            if(l1!=-1 && l1==l2 && l2==l3 && l3==l4){
                // if all children have the same luminosity (not being -1) we merge them
                this.abandonChildren(l1);
            }
        }
        return this.getCurrLum();
    }

    /** Retrieves the amount of nodes this quadtree has
     *
     * @return 1 if this node doesn't have children, 1 plus this function's value applied to each children if otherwise
     */
    public int getNodes(){
        return (this.getCurrLum()==-1) ? (1+ this.getV1().getNodes() + this.getV2().getNodes() + this.getV3().getNodes() + this.getV4().getNodes()):(1);
    }

    /** Does the lambda compression on this quadtree's final children
     *
     */
    public void tree_compression_lambda(){
        if (this.getCurrLum()==-1){
            if (this.isATwig()){
                // if this node has children and none of them have children we do the lambda compression
                this.abandonChildren(this.getSelfLamb());
            }else {
                // else we do the compression on the children
                this.getV1().tree_compression_lambda();
                this.getV2().tree_compression_lambda();
                this.getV3().tree_compression_lambda();
                this.getV4().tree_compression_lambda();
            }
        }
    }

    /**
     * checks if its children have children themselves
     * @return true if none of its children have children
     */
    public boolean isATwig(){
        return (this.getV1().getCurrLum()!=-1 &&this.getV2().getCurrLum()!=-1&& this.getV3().getCurrLum()!=-1&& this.getV4().getCurrLum()!=-1);
    }

    /** Retrieves the skip list of nodes sorted by the smallest epsilon
     *
     */
    public void smallestEpsi(SkipList skL) {
        if (this.getCurrLum() == -1) {
            if (this.isATwig()){
                skL.addElem(this);
            }else {
                // if they have children we call the function recursively upon them
                this.getV1().smallestEpsi(skL);
                this.getV2().smallestEpsi(skL);
                this.getV3().smallestEpsi(skL);
                this.getV4().smallestEpsi(skL);
            }
        }
    }
}
