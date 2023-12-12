import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        // let's test skip list
        SkipList temp = new SkipList();
        temp.addElem(new QT(200));
        System.out.println("_______________________");
        System.out.println(temp);
        temp.addElem(new QT(175));
        System.out.println("_______________________");
        System.out.println(temp);
        temp.addElem(new QT(50));
        System.out.println("_______________________");
        System.out.println(temp);
        temp.addElem(new QT(55));
        System.out.println("_______________________");
        System.out.println(temp);
        temp.addElem(new QT(60));
        System.out.println("_______________________");
        System.out.println(temp);
        temp.addElem(new QT(110));
        System.out.println("_______________________");
        System.out.println(temp);
        temp.deleteMin();
        System.out.println("_______________________");
        System.out.println(temp);
        temp.deleteMin();
        System.out.println("_______________________");
        System.out.println(temp);
        temp.deleteMin();
        System.out.println("_______________________");
        System.out.println(temp);
        temp.deleteMin();
        System.out.println("_______________________");
        System.out.println(temp);
        temp.deleteMin();
        System.out.println("_______________________");
        System.out.println(temp);
        temp.deleteMin();
        System.out.println("_______________________");
        System.out.println(temp);
        temp.deleteMin();
        System.out.println("_______________________");
        System.out.println(temp);
        temp.addElem(new QT(200));
        System.out.println("_______________________");
        System.out.println(temp);
        temp.addElem(new QT(200));
        System.out.println("_______________________");
        System.out.println(temp);





        /*
        Scanner reader = new Scanner(System.in);
        if (args.length == 0){
            // menu part
            String menuMessage = """
                    What do you wanna do (press the number before the action to choose)?
                     1- Choose a pgm to load amongst a list (you'll have to provide the absolute path to the directory containing those pgms).
                     2- Apply a lambda compression on the currently loaded pgm and create its .qt file (you'll have to provide its name).
                     3- Apply a rho compression on the currently loaded pgm and create its.qt file (you'll have to provide its name and the parameter p).
                     4- Generate the pgm from the previously created .qt file (can also create a pgm from a provided and well-built .qt file).
                     5- Show the compression statistics ( if you haven't reloaded a pgm in between multiple compressions the stats will take it into account).
                     Any other input will result in the termination of this application.""";
            boolean wantsToGoOn = true; // continues the loop
            String currentFile = ""; // the pgm being taken care of
            String lastQtLocation = ""; // the last saved .qt
            String comprDone =""; // all the compressions done
            Quadtree loadedQT = null; // the software's stored qt
            int startAmountNode = 0;
            while(wantsToGoOn){
                // show the menu
                System.out.println("Currently loaded pgm : " + currentFile + "\n" + menuMessage);
                // retrieve the answer
                int answer = Integer.parseInt(reader.nextLine());
                switch (answer){ // cases depending on the answer
                    case 1: // load a pgm
                        System.out.println("Please provide your pgm directory's absolute path : ");
                        File pgsDir = new File(reader.nextLine());
                        if (pgsDir.exists()){
                            String[] pgmsfiles = pgsDir.list();
                            System.out.println("Press the number appearing before the wanted file");
                            int countPgs = 0;
                            for(int i = 0; i< Objects.requireNonNull(pgmsfiles).length; i++){
                                if (pgmsfiles[i].endsWith(".pgm")){ // show all pgm files in the directory
                                    countPgs++;
                                    System.out.println(i+" : "+ pgmsfiles[i]);
                                }
                            }
                            if (countPgs >0){ // makes sure that the user chooses the correct option
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
                                currentFile = pgsDir.getAbsolutePath()+ System.getProperty("file.separator") +selectedPgm;
                                try{
                                    loadedQT = new Quadtree(currentFile);
                                    startAmountNode = loadedQT.getNodes();
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
                    case 2: // if asked to do a lambda compression
                        if (!currentFile.isEmpty()) {
                            // lambda compression
                            loadedQT.compressLambda();
                            comprDone +="\n lambda compression";
                            System.out.println("Lambda compression done on "+ currentFile + "\n Compressions done : " +
                                    "\n"+ comprDone);
                            lastQtLocation = saveQtWithFilename(reader, lastQtLocation, loadedQT);
                        }else {
                            System.out.println("No pgm image where loaded, please do so before proceeding with a compression");
                        }
                        break;
                    case 3: // if asked to do a rho compression
                        if (!currentFile.isEmpty()){
                            int p=-1;
                            while (p<1 || p>100){
                                System.out.println("Please provide p : ");
                                p = Integer.parseInt(reader.nextLine());
                            }
                            // Rho compression
                            loadedQT.compressRho(p);
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
                                if (Quadtree.qtFileToPgm(qtLoc, System.getProperty("user.dir") + System.getProperty("file.separator") + filename + ".pgm")) {
                                    System.out.println("Your pgm has successfully been generated at : " + System.getProperty("user.dir") + System.getProperty("file.separator") + filename + ".pgm");
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
                                +currentFile + "\n Compressions done : \n" + comprDone + "- amount of nodes at first : " + startAmountNode +
                                "\n - amount of nodes after compressions : " + loadedQT.getNodes()
                                + "\n - Compression : "+ ((float)loadedQT.getNodes()/(float)startAmountNode)*100 +"%");
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
            int p = Integer.parseInt(args[1]);
            if(p<1 || p>100){
                System.out.println("The value of p isn't between 1 and 100; p = "+ p);
            }else {
                Quadtree temp;
                try {
                    temp = new Quadtree(args[0]);
                    int nbNodeBefore = temp.getNodes();
                    // lambda compression
                    temp.compressLambda();
                    int nbNodeAfterLamb = temp.getNodes();
                    new File(System.getProperty("user.dir") +System.getProperty("file.separator") +"QT").mkdirs(); //(would be /QT on linux)
                    if (temp.saveCurrentQT(System.getProperty("user.dir")+ System.getProperty("file.separator") + "QT"+System.getProperty("file.separator")+"lambda.qt")) {
                        new File(System.getProperty("user.dir") +System.getProperty("file.separator")+ "PGM").mkdirs(); //(would be /PGM on linux)
                        Quadtree.qtFileToPgm(System.getProperty("user.dir") + System.getProperty("file.separator")+ "QT"+System.getProperty("file.separator")+"lambda.qt", System.getProperty("user.dir") +System.getProperty("file.separator")+ "PGM"+System.getProperty("file.separator")+"lambda.pgm");
                        // rho compression
                        temp = new Quadtree(args[0]);
                        temp.compressRho(p);
                        int nbNodeAfterRho = temp.getNodes();
                        if (temp.saveCurrentQT(System.getProperty("user.dir") + System.getProperty("file.separator")+"QT"+System.getProperty("file.separator")+"rho.qt")) {
                            Quadtree.qtFileToPgm(System.getProperty("user.dir") + System.getProperty("file.separator")+"QT"+System.getProperty("file.separator")+"rho.qt", System.getProperty("user.dir") +System.getProperty("file.separator")+ "PGM"+System.getProperty("file.separator")+"rho.pgm");
                            System.out.println("Compression stats : \n Lambda : \n - original number of nodes : " + nbNodeBefore + " \n - current number of nodes : " + nbNodeAfterLamb
                                    + "\n - Compression : " + ((float) nbNodeAfterLamb / (float) nbNodeBefore) * 100 + "% \n Rho : \n - original number of nodes : "
                                    + nbNodeBefore + "\n - current number of nodes : " + nbNodeAfterRho + "\n - compression : " + ((float) nbNodeAfterRho / (float) nbNodeBefore) * 100
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
    }

    private static String saveQtWithFilename(Scanner reader, String lastQtLocation, Quadtree loadedQT) {
        String filename = "";
        while (filename.isEmpty()){
            System.out.println("Please provide the name with which you wanna save the quadtree file :");
            filename = reader.nextLine();
            if (loadedQT.saveCurrentQT(System.getProperty("user.dir")+System.getProperty("file.separator")+filename+".qt")){
                System.out.println("Your quadtree file has successfully been saved at : " + System.getProperty("user.dir")+System.getProperty("file.separator")+filename+".qt");
                lastQtLocation = System.getProperty("user.dir")+System.getProperty("file.separator")+filename+".qt";
            } else {
                filename = "";
                System.out.println("We couldn't save with this filename, please try again.");
            }
        }
        return lastQtLocation;

         */
    }


}
