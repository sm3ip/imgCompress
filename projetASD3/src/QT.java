import java.util.Arrays;

public class QT {
    private int currLum; // if equals to -1 means you need to seek for the 4 children
    private QT V1, V2, V3, V4;
    private int selfHeight; // where it is in the depth of the tree

    //constructor
    public QT(){
        // constructor, basically nothing happens here
    }

    public void arrToQT(int[][] tab, int height){
        //System.out.println(height);
        // gonna build the qt later on
        this.selfHeight = height;
        int tempVal = tab[0][0];
        boolean isEqual = true;
        for (int[] ints : tab) {
            for (int j = 0; j < tab.length; j++) {
                isEqual = isEqual && tempVal == ints[j];
            }
        }
        if (isEqual){
            this.currLum = tempVal;
            this.V1 = null;
            this.V2 = null;
            this.V3 = null;
            this.V4 = null;
        }else {
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

    // getters

    public int getCurrLum() { return currLum; }

    public QT getV1() { return V1; }

    public QT getV2() { return V2; }

    public QT getV3() { return V3; }

    public QT getV4() { return V4; }

    public int getSelfHeight() { return selfHeight; }

    // setters

    public void setCurrLum(int currLum) { this.currLum = currLum; }

    public void setV1(QT v1) { V1 = v1; }

    public void setV2(QT v2) { V2 = v2; }

    public void setV3(QT v3) { V3 = v3; }

    public void setV4(QT v4) { V4 = v4; }

    public void setSelfHeight(int selfHeight) { this.selfHeight = selfHeight; }

    @Override
    public String toString() {
        //TODO: some bullshit with tostring from QuadTree;
        return (this.getCurrLum()==-1) ? ("("+this.getV1().toString()+";"+this.getV2().toString()+";"+this.getV3().toString()+";"+this.getV4().toString()+")"):(Integer.toString(this.getCurrLum()));
    }

    public void abandonChildren(int lum){
        setCurrLum(lum);
        setV1(null);
        setV2(null);
        setV3(null);
        setV4(null);
    }

    public int trueQT(){
        if (this.getCurrLum()==-1){
            int l1 = this.getV1().trueQT();
            int l2 = this.getV2().trueQT();
            int l3 = this.getV3().trueQT();
            int l4 = this.getV4().trueQT();
            if(l1!=-1 && l1==l2 && l2==l3 && l3==l4){
                this.abandonChildren(l1);
            }
        }
        return this.getCurrLum();
        // DONE: if currLum ==-1 goes down a level (on all 4 children) recursively
        // DONE: if none = -1 and all equal simplify then returns own value
        // DONE: else returns -1
    }

    public int getKnot(){
        // might have to be solely implemented in QT
        //TODO: if currLum = -1 returns 1 + getKnot of each child else returns 1
        return (this.getCurrLum()==-1) ? (1+ this.getV1().getKnot() + this.getV2().getKnot() + this.getV3().getKnot() + this.getV4().getKnot()):(1);
    }


    public void tree_compression_lambda(){
        if (this.getCurrLum()==-1){
            int l1 = this.getV1().getCurrLum();
            int l2 = this.getV2().getCurrLum();
            int l3 = this.getV3().getCurrLum();
            int l4 = this.getV4().getCurrLum();
            if (l1!=-1 && l2!=-1 && l3!=-1 && l4!=-1){
                int lambda = (int) Math.round(Math.exp(0.25*((Math.log(0.1+l1))+(Math.log(0.1+l2))+(Math.log(0.1+l3))+(Math.log(0.1+l4)))));
                this.abandonChildren(lambda);
            }else {
                this.getV1().tree_compression_lambda();
                this.getV2().tree_compression_lambda();
                this.getV3().tree_compression_lambda();
                this.getV4().tree_compression_lambda();
            }
        }
        //Do nothing
    }

    public StrFloatTuple[] smallestEpsi(String path) {
        if (this.getCurrLum() == -1) {
            int l1 = this.getV1().getCurrLum();
            int l2 = this.getV2().getCurrLum();
            int l3 = this.getV3().getCurrLum();
            int l4 = this.getV4().getCurrLum();
            if (l1 != -1 && l2 != -1 && l3 != -1 && l4 != -1) {
                float lambda = (float) Math.exp(0.25 * ((Math.log(0.1 + l1)) + (Math.log(0.1 + l2)) + (Math.log(0.1 + l3)) + (Math.log(0.1 + l4))));
                float L1 = Math.abs(l1 - lambda);
                float L2 = Math.abs(l2 - lambda);
                float L3 = Math.abs(l3 - lambda);
                float L4 = Math.abs(l4 - lambda);
                float epsi1 = Math.max(L1, L2);
                float epsi2 = Math.max(L3, L4);
                float epsi = Math.round(Math.max(epsi1, epsi2));
                return new StrFloatTuple[]{new StrFloatTuple(path,epsi)};
                //TODO: Il faut faire de l'abandon d'enfant ici
            }else {
                StrFloatTuple[] v1Epsis = this.getV1().smallestEpsi(path+"1");
                StrFloatTuple[] v2Epsis = this.getV2().smallestEpsi(path+"2");
                StrFloatTuple[] v3Epsis = this.getV3().smallestEpsi(path+"3");
                StrFloatTuple[] v4Epsis = this.getV4().smallestEpsi(path+"4");
                // now gotta return the concatenation of those 4 arrays
                //merging 1 and 2
                // gotta check null val before
                StrFloatTuple[] v12Epsis = sFMerge(v1Epsis, v2Epsis);
                //merging 3 and 4
                StrFloatTuple[] v34Epsis = sFMerge(v3Epsis, v4Epsis);
                //merging 12 and 34
                return sFMerge(v12Epsis, v34Epsis);
            }
        }
        return null;
        //TODO: Gérer le remplacement du père par la valeur calculé et abandoner les enfants + enlever les if si néccesaire
    }

    private StrFloatTuple[] sFMerge(StrFloatTuple[] v1Epsis, StrFloatTuple[] v2Epsis) {
        // might have to put it in its class
        int v1Len = (v1Epsis == null)?(0):(v1Epsis.length);
        int v2Len = (v2Epsis == null)?(0):(v2Epsis.length);
        StrFloatTuple[] v12Epsis = null;
        if (v1Len+v2Len==0){
            //StrFloatTuple[]v12Epsis = null;
        } else if (v1Len==0) {
            v12Epsis = v2Epsis;
        } else if (v2Len==0) {
            v12Epsis = v1Epsis;
        }else {
            v12Epsis = Arrays.copyOf(v1Epsis,v1Epsis.length+v2Epsis.length);
            System.arraycopy(v2Epsis,0,v12Epsis,v1Epsis.length,v2Epsis.length);
        }
        return v12Epsis;
    }


    public int tab_data(){
        //TODO: retourne une table qui donne les origines qui permettent de remplir le tableau final
        // renvoi (lum, heigth, origin)
        return 0;
    }

    public static int dimension(int height){
        //TODO: en fonction de la hauteur du quadtree, renvoie les dimmensions du quadtree
        return 0;
    }

    //TODO: faire une autre classe qui stocke le tableau (lum, height, origin)
}
