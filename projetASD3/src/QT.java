public class QT {
    private int currLum; // if equals to -1 means you need to seek for the 4 children
    private QT V1, V2, V3, V4;
    private int selfHeight; // where it is in the depth of the tree

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
        return "0";
    }

    public void abandonChildren(int lum){
        //TODO: drop the child
    }

    public void tree_simplification() {

        if (this.getCurrLum()==-1){
            this.getV1().tree_simplification();
            this.getV1().tree_simplification();
            this.getV1().tree_simplification();
            this.getV1().tree_simplification();
            int l1 = this.getV1().getCurrLum();
            int l2 = this.getV2().getCurrLum();
            int l3 = this.getV3().getCurrLum();
            int l4 = this.getV4().getCurrLum();
            if (l1 != -1 && l1 == l2 && l2 == l3 && l3 == l4) {
                        this.setCurrLum(l1);

                    }
        }
    }



    public void tree_compression_lambda(){
        if (this.getCurrLum()==-1){
            int l1 = this.getV1().getCurrLum();
            int l2 = this.getV2().getCurrLum();
            int l3 = this.getV3().getCurrLum();
            int l4 = this.getV4().getCurrLum();
            if (l1!=-1 && l2!=-1 && l3!=-1 && l4!=-1){
                int lambda = (int) Math.round(Math.exp(0.25*((Math.log(0.1+l1))+(Math.log(0.1+l2))+(Math.log(0.1+l3))+(Math.log(0.1+l4)))));
                //TODO: Il faut faire de l'abandon d'enfant ici
            }



        }
        //TODO: Gérer le remplacement du père par la valeur calculé et abandoner les enfants + enlever les if si néccesaire
    }

    public void tree_compression_rho() {
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
                float rho1 = Math.max(L1, L2);
                float rho2 = Math.max(L1, L2);
                int rho = Math.round(Math.max(rho1, rho2));
                //TODO: Il faut faire de l'abandon d'enfant ici
            }
        }
        //TODO: Gérer le remplacement du père par la valeur calculé et abandoner les enfants + enlever les if si néccesaire
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
