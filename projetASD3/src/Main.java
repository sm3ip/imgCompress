public class Main {
    public static void main(String[] args) {
        //QuadTree temp = new QuadTree("/comptes/E218341C/Documents/l3/s1/ASD3/projet/imgCompress/projetASD3/src/boat.pgm"); LINUX version
        QuadTree temp = new QuadTree("C:\\Users\\lucas\\Documents\\CMI_etudes\\L3\\ASD3\\project\\imgCompress\\projetASD3\\src\\test.pgm"); // windows version
        System.out.println(temp.getMaxLum());
        /*
        for (int i =0; i< temp.getSize(); i++){
            for (int j=0; j<temp.getSize(); j++){
                System.out.print(temp.getTab()[i][j]);
                System.out.print("  ");
            }
            System.out.println(" ");
        }
        */
         System.out.println(temp.toString());
    }
}
