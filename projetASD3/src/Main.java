public class Main {
    public static void main(String[] args) {
        //QuadTree temp = new QuadTree("/comptes/E218341C/Documents/l3/s1/ASD3/projet/imgCompress/projetASD3/src/boat.pgm"); LINUX version
        QuadTree temp = new QuadTree("C:\\Users\\lucas\\Documents\\CMI_etudes\\L3\\ASD3\\project\\imgCompress\\projetASD3\\src\\boat.pgm");
        //System.out.println(temp.toString());
        System.out.println("KNOT before" +temp.getKnot());
        temp.rhoCompr(80);
        System.out.println("KNOT after"+temp.getKnot());
        /*
        for (int i =0; i< temp.getSize(); i++){
            for (int j=0; j<temp.getSize(); j++){
                System.out.print(temp.getTab()[i][j]);
                System.out.print("  ");
            }
            System.out.println(" ");
        }
        */
        //System.out.println(temp.toString());
        if (temp.saveCurrentQT("C:\\Users\\lucas\\Documents\\CMI_etudes\\L3\\ASD3\\project\\imgCompress\\projetASD3\\src\\QTtest1.qt")){
            QuadTree.qtFileToPgm("C:\\Users\\lucas\\Documents\\CMI_etudes\\L3\\ASD3\\project\\imgCompress\\projetASD3\\src\\QTtest1.qt",
                    "C:\\Users\\lucas\\Documents\\CMI_etudes\\L3\\ASD3\\project\\imgCompress\\projetASD3\\src\\producedPGMtest1.pgm");
        }

    }
}
