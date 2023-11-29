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

    public void tree_simplification(){
        //TODO: parcours de l'arbre et si tous ses fils ont la même luminosité alors remplacer le père par la luminosité
    }

    public void tree_compression_lambda(){
        //TODO: parcours arbre et utilise la méthode de compression lambda sur les dernières branches
    }

    public void tree_compression_rho(){
        //TODO: parcours arbre et utilise la méthode de compression rho sur les dernières branches
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
