import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner reader = new Scanner(System.in);
        if (args.length == 0){
            // menu part
            boolean wantsToGoOn = true; // continues the loop
            String currentFile = ""; // the pgm being taken care of
            String lastQtLocation = ""; // the last saved .qt
            String comprDone =""; // all the compr done
            QuadTree loadedQT = null; // the soft's qt
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
                                    int tempChoice = Integer.parseInt(reader.nextLine());
                                    if (tempChoice< pgmsfiles.length && tempChoice>=0 &&pgmsfiles[tempChoice].endsWith(".pgm")){
                                        selectedPgm = pgmsfiles[tempChoice];
                                    }else {
                                        System.out.println("This wasn't a pgm file, please choose again.");
                                    }
                                }
                                // load it now
                                currentFile = pgsDir.getAbsolutePath()+"\\"+selectedPgm;
                                try{
                                    loadedQT = new QuadTree(currentFile);
                                    startAmountKnot = loadedQT.getKnot();
                                    comprDone = "";
                                    System.out.println("PGM file successfully loaded");
                                }
                                catch (FileNotFoundException e){
                                    System.out.println(e + "  " + currentFile);
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
                        if (!currentFile.isEmpty()) {
                            // lambda compression
                            loadedQT.lambdaCompr();
                            comprDone +="\n lambda compression";
                            System.out.println("Lambda compression done on "+ currentFile + "\n Compressions done : " +
                                    "\n"+ comprDone);
                            lastQtLocation = saveQtWithFilename(reader, lastQtLocation, loadedQT);
                        }else {
                            System.out.println("No pgm image where loaded, please do so before proceeding with a compression");
                        }
                        break;
                    case 3:
                        if (!currentFile.isEmpty()){
                            int p=-1;
                            while (p<1 || p>100){
                                System.out.println("Please provide p : ");
                                p = Integer.parseInt(reader.nextLine());
                            }
                            // Rho compression
                            loadedQT.rhoCompr(p);
                            comprDone += "\n rho compression with p = " + p;
                            System.out.println("Rho compression done on "+ currentFile+"\n Compressions done : \n" + comprDone);
                            lastQtLocation = saveQtWithFilename(reader, lastQtLocation, loadedQT);
                        }else {
                            System.out.println("No pgm image where loaded, please do so before proceeding with a compression");
                        }
                        break;
                    case 4:
                        System.out.println("Type 1 if you wanna provide your own .qt file (otherwise we'll generate the pgm from the previously built .qt file).");
                        String qtLoc = "";
                        if (Integer.parseInt(reader.nextLine())!=1){
                            qtLoc = lastQtLocation;
                        }else {
                            while (qtLoc.isEmpty()) {
                                System.out.println("Please provide the absolute path of your .qt file ");
                                qtLoc = reader.nextLine();
                                File temp = new File(qtLoc);
                                if (!temp.exists()){
                                    qtLoc = "";
                                }
                            }
                        }
                        if (!qtLoc.isEmpty()) {
                            String filename = "";
                            while (filename.isEmpty()) {
                                System.out.println("Please provide the name with which you wanna save the pgm file :");
                                filename = reader.nextLine();
                                if (QuadTree.qtFileToPgm(qtLoc, System.getProperty("user.dir") + "\\" + filename + ".pgm")) {
                                    System.out.println("Your pgm has successfully been generated at : " + System.getProperty("user.dir") + "\\" + filename + ".pgm");
                                } else {
                                    filename = "";
                                    System.out.println("We couldn't save with this filename, please try again.");
                                }
                            }
                        }else {
                            System.out.println("Please load a pgm file beforehand");
                        }
                        break;
                    case 5:
                        String temp = (currentFile.isEmpty())?("There's no pgm file loaded,therefore we can't provide statistics"):("Compression statistics on : \n"
                                +currentFile + "\n Compressions done : \n" + comprDone + "- amount of knots at first : " + startAmountKnot +
                                "\n - amount of knot after compressions : " + loadedQT.getKnot()
                                + "\n - Compression : "+ ((float)loadedQT.getKnot()/(float)startAmountKnot)*100 +"%");
                        System.out.println(temp);
                        break;
                    default:
                        wantsToGoOn = false;
                        break;
                }
            }

        }else {
            //timing
            long startTime = System.currentTimeMillis();

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
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken : "+ (endTime-startTime)+"ms");
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

    private static String saveQtWithFilename(Scanner reader, String lastQtLocation, QuadTree loadedQT) {
        String filename = "";
        while (filename.isEmpty()){
            System.out.println("Please provide the name with which you wanna save the quadtree file :");
            filename = reader.nextLine();
            if (loadedQT.saveCurrentQT(System.getProperty("user.dir")+"\\"+filename+".qt")){
                System.out.println("Your quadtree file has successfully been saved at : " + System.getProperty("user.dir")+"\\"+filename+".qt");
                lastQtLocation = System.getProperty("user.dir")+"\\"+filename+".qt";
            } else {
                filename = "";
                System.out.println("We couldn't save with this filename, please try again.");
            }
        }
        return lastQtLocation;
    }
}
