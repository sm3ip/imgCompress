/** Represents the basic structure of a quadtree
 */
public class QT {
    private int currLum, selfLamb;
    private QT V1, V2, V3, V4;
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

    // getters

    /** Gets this knot's luminosity
     *
     * @return the luminosity
     */
    public int getCurrLum() { return currLum; }

    /** Gets this knot's lambda value
     *
     * @return lambda
     */
    public int getSelfLamb() {
        return selfLamb;
    }

    /** Gets this knot's epsilon value
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

    /** Get this knot's height
     *
     * @return the depth at wich this knot is
     */
    public int getSelfHeight() { return selfHeight; }

    // setters

    /** Sets this knot's luminosity value
     *
     * @param currLum an int representing the luminosity
     */
    public void setCurrLum(int currLum) { this.currLum = currLum; }

    /** Sets this knot's top-left child
     *
     * @param v1 a quadtree
     */
    public void setV1(QT v1) { V1 = v1; }

    /** Sets this knot's top-right child
     *
     * @param v2 a quadtree
     */
    public void setV2(QT v2) { V2 = v2; }

    /** Sets this knot's bottom-right child
     *
     * @param v3 a quadtree
     */
    public void setV3(QT v3) { V3 = v3; }

    /** Sets this knot's bottom-left child
     *
     * @param v4 a quadtree
     */
    public void setV4(QT v4) { V4 = v4; }

    /** Sets this knot's height
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

    /** Determines (for the whole tree) the Knot's epsilon and lambda values
     *
     * @return this knot's lambda value because the parent knot needs it for it's lambda and epsilon computations
     */
    public int determineEpsiLamb(){
        if (this.currLum==-1){ // if this knot has children
            // goes to the deepest parts so the children get their own lambda
            // And retrieves the children's lambda
            int l1 = this.getV1().determineEpsiLamb();
            int l2 = this.getV2().determineEpsiLamb();
            int l3 = this.getV3().determineEpsiLamb();
            int l4 = this.getV4().determineEpsiLamb();
            // compute this knot's lambda and epsilon
            //lambda computation
            double lambdaComputation = Math.exp(0.25 * ((Math.log(0.1 + l1)) + (Math.log(0.1 + l2)) + (Math.log(0.1 + l3)) + (Math.log(0.1 + l4))));
            this.selfLamb = (int) Math.round(lambdaComputation);
            //epsilon computation
            float lambda = (float) lambdaComputation;
            float mLamb12 = Math.max(Math.abs(lambda-l1),Math.abs(lambda-l2));
            float mLamb34 = Math.max(Math.abs(lambda-l3), Math.abs(lambda-l4));
            this.selfEpsi = Math.max(mLamb12,mLamb34);
        }else { // if the Knot doesn't have children we set its lambda value to its own luminosity
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
     * @param height the knot's height
     */
    public void arrToQT(int[][] tab, int height){
        this.selfHeight = height;
        // checks if all values in the array are equal
        boolean isEqual = isTabAllEqual(tab);
        if (isEqual){ // this Knot won't have children
            this.abandonChildren(tab[0][0]);
        }else {
            // if values aint equal we call the function recursively on the children
            this.currLum = -1;
            height++;
            this.V1 = new QT();
            this.V1.arrToQT(SubArray.copySubArray(tab,0,0,tab.length/2), height);
            this.V2 = new QT();
            this.V2.arrToQT(SubArray.copySubArray(tab,tab.length/2,0,tab.length/2), height);
            this.V3 = new QT();
            this.V3.arrToQT(SubArray.copySubArray(tab,tab.length/2,tab.length/2,tab.length/2), height);
            this.V4 = new QT();
            this.V4.arrToQT(SubArray.copySubArray(tab,0,tab.length/2,tab.length/2), height);
        }
    }

    /** Sets this knot's luminosity value and sets childs as null
     *
     * @param lum the luminosity
     */
    public void abandonChildren(int lum){
        setCurrLum(lum);
        setV1(null);
        setV2(null);
        setV3(null);
        setV4(null);
    }

    /** Checks if this quadtree as the correct format to be considered as a quadtree
     *
     * @return this knot's luminosity (so the previous call can retrieve this data)
     */
    public int trueQT(){
        if (this.getCurrLum()==-1){
            // if this knot has children we call the function upon them and retrieve their luminosity
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

    /** Retrieves the amount of knot this quadtree has
     *
     * @return 1 if this knot doesn't have children, 1 plus this function's value applied to each children if otherwise
     */
    public int getKnot(){
        return (this.getCurrLum()==-1) ? (1+ this.getV1().getKnot() + this.getV2().getKnot() + this.getV3().getKnot() + this.getV4().getKnot()):(1);
    }

    /** Does the lambda compression on this quadtree's final children
     *
     */
    public void tree_compression_lambda(){
        if (this.getCurrLum()==-1){
            int l1 = this.getV1().getCurrLum();
            int l2 = this.getV2().getCurrLum();
            int l3 = this.getV3().getCurrLum();
            int l4 = this.getV4().getCurrLum();
            if (l1!=-1 && l2!=-1 && l3!=-1 && l4!=-1){
                // if this knot has children and none of them have children we do the lambda compression
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

    /** Retrieves the knot containing the smallest epsilon
     *
     * @param path the path used to get to the current knot
     * @return a tuple composed of the path to the quadtree and its epsilon
     */
    public StrFloatTuple smallestEpsi(String path) {
        if (this.getCurrLum() == -1) {
            int l1 = this.getV1().getCurrLum();
            int l2 = this.getV2().getCurrLum();
            int l3 = this.getV3().getCurrLum();
            int l4 = this.getV4().getCurrLum();
            if (l1 != -1 && l2 != -1 && l3 != -1 && l4 != -1) {
                // in the case where this knot has children which don't have children we return this knot's epsilon
                return new StrFloatTuple(path,this.getSelfEpsi());
            }else {
                // if they have children we call the function recursively upon them while also updating the path
                StrFloatTuple v1Epsis = this.getV1().smallestEpsi(path+"1");
                StrFloatTuple v2Epsis = this.getV2().smallestEpsi(path+"2");
                StrFloatTuple v3Epsis = this.getV3().smallestEpsi(path+"3");
                StrFloatTuple v4Epsis = this.getV4().smallestEpsi(path+"4");
                // now gotta find the smallest epsilon amongst them
                //comparing 1 and 2
                StrFloatTuple v12Epsis = StrFloatTuple.sFChooseSmallest(v1Epsis, v2Epsis);
                //comparing 3 and 4
                StrFloatTuple v34Epsis = StrFloatTuple.sFChooseSmallest(v3Epsis, v4Epsis);
                //comparing 12 and 34
                return StrFloatTuple.sFChooseSmallest(v12Epsis, v34Epsis);
            }
        }
        return null;
    }
}
