import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner reader = new Scanner(System.in);
        if (args == null){
            // menu part
            boolean wantsToGoOn = true;
            String currentFile = "";
            String lastQtLocation = "";
            QuadTree loadedQT;
            int startAmountKnot = 0;
            while(wantsToGoOn){
                System.out.println("Currently loaded pgm : " + currentFile);
                System.out.println("What do you wanna do (press the number before the action to choose)?" +
                        "\n 1- Choose a pgm to load amongst a list (you'll have to provide the absolute path to the directory containing those pgms)." +
                        "\n 2- Apply a lambda compression on the currently loaded pgm and create its .qt file (you'll have to provide its name)." +
                        "\n 3- Apply a rho compression on the currently loaded pgm and create its.qt file (you'll have to provide its name and the parameter p)." +
                        "\n 4- Generate the pgm from the previously created .qt file (can also create a pgm from a provided and well-built .qt file)." +
                        "\n 5- Show the compression statistics ( if you haven't reloaded a pgm in between multiple compressions the stats will take it into account)." +
                        "\n Any other input will result in the termination of this application.");
                int answer = Integer.parseInt(reader.nextLine());
                switch (answer){
                    case 1:
                        System.out.println("Please provide your pgm directory's absolute path : ");
                        File pgsDir = new File(reader.nextLine());
                        if (pgsDir.exists()){

                            String[] pgmsfiles = pgsDir.list();

                            System.out.println("Press the number appearing before the wanted file");
                            int countPgs = 0;
                            for(int i = 0; i< Objects.requireNonNull(pgmsfiles).length; i++){
                                if (pgmsfiles[i].endsWith(".pgm")){
                                    countPgs++;
                                    System.out.println(i+" : "+ pgmsfiles[i]);
                                }
                            }
                            if (countPgs >0){
                                String selectedPgm = "";
                                while (selectedPgm.isEmpty()){
                                    int tempChoice = reader.nextInt();
                                    if (pgmsfiles[tempChoice].endsWith(".pgm")){
                                        selectedPgm = pgmsfiles[tempChoice];
                                    }else {
                                        System.out.println("This wasn't a pgm file, please choose again.");
                                    }
                                }
                                // load it now
                                currentFile = selectedPgm;
                                try{
                                    loadedQT = new QuadTree(currentFile);
                                    startAmountKnot = loadedQT.getKnot();
                                    System.out.println("PGM file successfully loaded");
                                }
                                catch (FileNotFoundException e){
                                    System.out.println(e);
                                    currentFile = "";
                                }

                            }else {
                                System.out.println("There are no .pgm file in the provided directory");
                            }
                        }else {
                            System.out.println("The provided path doesn't seem to exist");
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        break;
                }
            }

        }else {
            // part where the soft works by itself || the first param is the filename, the second is p
            // DONE: check if params are good; load pgm; do lambda;
            //TODO:   create .qt; create .pgm; read out loud stats;
            //TODO: reload pgm; do rho with p (read out loud progress); create .qt; create. pgm; read out loud stats;
            int p = Integer.parseInt(args[1]);
            if(p<1 || p>100){
                System.out.println("The value of p isn't between 1 and 100; p = "+ p);
            }else {
                QuadTree temp;
                try {
                    temp = new QuadTree(args[0]);
                    int nbKnotBefore = temp.getKnot();
                    // lambda compression
                    temp.lambdaCompr();
                    int nbKnotAfterLamb = temp.getKnot();
                    new File(System.getProperty("user.dir") + "\\QT").mkdirs(); //(would be /QT on linux)
                    if (temp.saveCurrentQT(System.getProperty("user.dir") + "\\QT\\lambda.qt")) {
                        new File(System.getProperty("user.dir") + "\\PGM").mkdirs(); //(would be /PGM on linux)
                        QuadTree.qtFileToPgm(System.getProperty("user.dir") + "\\QT\\lambda.qt", System.getProperty("user.dir") + "\\PGM\\lambda.pgm");
                        // rho compression
                        temp = new QuadTree(args[0]);
                        temp.rhoCompr(p);
                        int nbKnotAfterRho = temp.getKnot();
                        if (temp.saveCurrentQT(System.getProperty("user.dir") + "\\QT\\rho.qt")) {
                            QuadTree.qtFileToPgm(System.getProperty("user.dir") + "\\QT\\rho.qt", System.getProperty("user.dir") + "\\PGM\\rho.pgm");
                            System.out.println("Compression stats : \n Lambda : \n - original number of knots : " + nbKnotBefore + " \n - current number of knots : " + nbKnotAfterLamb
                                    + "\n - Compression : " + ((float) nbKnotAfterLamb / (float) nbKnotBefore) * 100 + "% \n Rho : \n - original number of knots : "
                                    + nbKnotBefore + "\n - current number of knots : " + nbKnotAfterRho + "\n - compression : " + ((float) nbKnotAfterRho / (float) nbKnotBefore) * 100
                                    + "%");
                        } else {
                            System.out.println("Couldn't save rho's .qt, closing application...");
                        }
                    } else {
                        System.out.println("Couldn't save lambda's .qt, closing application...");
                    }

                } catch (FileNotFoundException e) {
                    System.out.println(e);
                    System.out.println("Closing application ...");
                }
            }
        }



        /** way to update progress on rho
         *              System.out.print("test");
         *             Thread.sleep(1000);
         *             System.out.print("\b\b\b\b");
         *             System.out.print("HEYOOO");
         */
        //System.out.println(System.getProperty("user.dir")); current dir
        //System.out.println(args[0]+args[1]); arguments
        //QuadTree temp = new QuadTree("/comptes/E218341C/Documents/l3/s1/ASD3/projet/imgCompress/projetASD3/src/boat.pgm"); LINUX version

        //QuadTree temp = new QuadTree("C:\\Users\\lucas\\Documents\\CMI_etudes\\L3\\ASD3\\project\\imgCompress\\projetASD3\\src\\boat.pgm");
        //System.out.println(temp.toString());
        //System.out.println("KNOT before" +temp.getKnot());
        //temp.rhoCompr(80);
        //System.out.println("KNOT after"+temp.getKnot());



        //System.out.println(temp.toString());
        //if (temp.saveCurrentQT("C:\\Users\\lucas\\Documents\\CMI_etudes\\L3\\ASD3\\project\\imgCompress\\projetASD3\\src\\QTtest1.qt")){
        //    QuadTree.qtFileToPgm("C:\\Users\\lucas\\Documents\\CMI_etudes\\L3\\ASD3\\project\\imgCompress\\projetASD3\\src\\QTtest1.qt",
        //            "C:\\Users\\lucas\\Documents\\CMI_etudes\\L3\\ASD3\\project\\imgCompress\\projetASD3\\src\\producedPGMtest1.pgm");
        //}
          /*
           for (int i =0; i< temp.getSize(); i++){
            for (int j=0; j<temp.getSize(); j++){
                System.out.print(temp.getTab()[i][j]);
                System.out.print("  ");
            }
            System.out.println(" ");
        }

        int q1=3;
        int q2=2;
        int q3=16;
        int q4=30;
        float lambda = (float) Math.exp(0.25*((Math.log(0.1+q1))+(Math.log(0.1+q2))+(Math.log(0.1+q3))+(Math.log(0.1+q4))));
        System.out.println(Math.abs(lambda-q1));
        System.out.println(Math.abs(lambda-q2));
        System.out.println(Math.abs(lambda-q3));
        System.out.println(Math.abs(lambda-q4));
        int lamb = (int) Math.round(Math.exp(0.25*((Math.log(0.1+q1))+(Math.log(0.1+q2))+(Math.log(0.1+q3))+(Math.log(0.1+q4)))));
        System.out.println(lamb);
        */

    }
}
