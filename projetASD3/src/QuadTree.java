import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class QuadTree extends QT {
    private int maxLum;
    private int size;
    private int[][] tab;

    private String fileRoute; // the pgm file's location

    public QuadTree(String file){
        super();
        setFileRoute(file);
        pgmToArr();
        arrToQT(this.tab,0);
        this.trueQT();
    }

    // getters

    public int getMaxLum() {
        return maxLum;
    }

    public int getSize() {
        return size;
    }

    public int[][] getTab() {
        return tab;
    }

    public String getFileRoute() {
        return fileRoute;
    }

    // setters

    private void setMaxLum(int maxLum) {
        this.maxLum = maxLum;
    }

    private void setSize(int size) {
        this.size = size;
    }

    private void setTab(int[][] tab) {
        this.tab = tab;
    }

    private void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }


    private void pgmToArr(){
        //TODO: read file and store to temporary 2d array, read the array to build a QT
        try{
            int count =0;
            int i =0;
            int j =0;
            File thePgm = new File(this.getFileRoute());
            Scanner theReader = new Scanner(thePgm);
            while (theReader.hasNextLine()){
                String currLine = theReader.nextLine();
                if (count<2){
                    //System.out.println(currLine);
                    count+=1;
                } else {
                    String[] tokens = currLine.split(" +");
                    switch (count) {
                        case 2 -> {
                            //System.out.println(tokens[0]);
                            this.setSize(Integer.parseInt(tokens[0]));
                            this.setTab(new int[this.getSize()][this.getSize()]);
                            count+=1;
                        }

                        //TODO: create 2D array
                        case 3 -> {
                            this.setMaxLum(Integer.parseInt(tokens[0]));
                            count+=1;
                        }
                        default -> {
                            for (String token: tokens) {
                                    int temp = Integer.parseInt(token);
                                    this.tab[i][j] = temp;
                                    i++;
                                    if (i>=this.getSize()){
                                        i=0;
                                        j++;
                                    }
                            }
                        }
                    }
                }
            }
            theReader.close();
        } catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    @Override
    public String toString(){
        return this.size+":"+this.maxLum+":"+ super.toString();
    }

    public boolean saveCurrentQT(String location){
        try{
            File daFile = new File(location);
            if (daFile.createNewFile()){
                System.out.println("File created");
            }else {
                System.out.println("A file with the same name already exists");
                return false;
            }
        } catch (IOException e){
            return false;
        }
        try{
            FileWriter writy = new FileWriter(location);
            writy.write(this.toString());
            writy.close();
            System.out.println("File successfully written");
        } catch (IOException e){
            System.out.println("Couldn't write in the file");
            return false;
        }
        return true;
    }

    public static boolean qtFileToPgm(String location,String producedLocation){
        String temp = "";
        try{
            File qtFile = new File(location);
            Scanner theReader = new Scanner(qtFile);
            while (theReader.hasNextLine()){
                temp = theReader.nextLine();
            }
            theReader.close();
        } catch (FileNotFoundException e){
            System.out.println(e);
            return false;
        }
        //TODO: process the data
        //format is size:max Lum:QT
        String[] data = temp.split(":");
        int size = Integer.parseInt(data[0]);
        int maxLum = Integer.parseInt(data[1]);
        int[][] tempTab = new int[size][size];
        String[] tree = data[2].split(";");
        String whereWeAt= "1"; // keeps track to where we are in the tree

        for (int i = 0; i < tree.length; i++) {
            String buffer = "";
            int rightPCount = 0;
            for (int j = 0; j < tree[i].length(); j++) {
                if (tree[i].charAt(j)=='('){
                    whereWeAt = whereWeAt + "1";
                }else if (tree[i].charAt(j)==')'){
                    //do nothing maybe count them
                    rightPCount++;
                    //whereWeAt = whereWeAt.substring(0,whereWeAt.length()-1);
                }else {
                    buffer = buffer + tree[i].charAt(j);
                }
            }
            //System.out.println("buffer is "+ buffer); BUFFER HERE HAS GOOD VALUE
            // do shit with that one number
            int workingSize = size; // the size in which we can fill the 2d array
            int orX = 0; // the x value of the area origin point
            int orY = 0; // the y value of the area origin
            // updates the area that's gonna be filled
            for (int j = 1; j < whereWeAt.length(); j++) {
                switch (whereWeAt.charAt(j)){
                    case '1':
                        workingSize = workingSize/2;
                        break;
                    case '2':
                        orY+= workingSize/2;
                        workingSize = workingSize/2;
                        break;
                    case '3':
                        orY+= workingSize/2;
                        orX+= workingSize/2;
                        workingSize = workingSize/2;
                        break;
                    case '4':
                        orX+= workingSize/2;
                        workingSize = workingSize/2;
                        break;
                }
            }
            //System.out.println("size is "+workingSize+" x is " + orX + " y is " + orY + "buffer is " + buffer + " where value is " + whereWeAt);

            for (int j = orX; j < orX+workingSize ; j++) {
                for (int k = orY; k <orY+workingSize ; k++) {
                    tempTab[j][k] = Integer.parseInt(buffer);
                }
            }
            // take out if its a 4
            for (int j = 0; j < rightPCount; j++) {
                whereWeAt = whereWeAt.substring(0,whereWeAt.length()-1);
            }

            //updates last char when we get to the next token
            if (whereWeAt !=null && whereWeAt.length()>0){
                char lastChar =whereWeAt.charAt(whereWeAt.length()-1);//= (char) (Character.getNumericValue(whereWeAt.charAt(whereWeAt.length()-1))+1);
                whereWeAt = whereWeAt.substring(0,whereWeAt.length()-1) + (Character.getNumericValue(lastChar)+1);
            }

        }
        // the data struct is saved in this function, now is the time to write it into a file
        try{ // creates the file
            File newPgmFile = new File(producedLocation);
            if (newPgmFile.createNewFile()){
                System.out.println("Pgm File has been created");
            }else {
                System.out.println("couldnt create the file cuz another one already exists with the same name");
                return false;
            }
        } catch (IOException e){
            System.out.println("Couldnt create the file for unknown reasons");
            return false;
        }

        // writes in the file
        try{
            FileWriter writy = new FileWriter(producedLocation);
            // writing header's data
            writy.write("P2" + System.getProperty("line.separator"));
            writy.write("# This generated pgm has been brought to you through Emile Bonmarriage and Lucas Pereira's hardwork" + System.getProperty("line.separator"));
            writy.write(size +" "+ size + System.getProperty("line.separator"));
            writy.write(maxLum + System.getProperty("line.separator"));
            // writing the picture's main data
            for (int i = 0; i < tempTab.length; i++) {
                String currLine = "";
                for (int j = 0; j < tempTab.length; j++) {
                    currLine += tempTab[i][j] + " ";
                }
                writy.write(currLine + System.getProperty("line.separator"));
            }
            writy.close();
            System.out.println("File successfully written");

        } catch (IOException e){
            System.out.println(" Couldnt write in the file for some reasons");
            return false;
        }

        return true;
    }


    public void lambdaCompr(){
        this.tree_compression_lambda(); // do the compression
        this.trueQT(); // gets it back as an actual quadTree
    }

    // Rho compression methods
    //TODO: evaluate epsilon gotta add the var in QT and the method in there aswell
    //TODO: find the smallest espilon

    public void rhoCompr(int p){
        int startAmountKnots = this.getKnot();
        int currAmountKnots = startAmountKnots;
        System.out.println("p is" + p + " currKnot is" + currAmountKnots + " startKnot is " + startAmountKnots + " val to comp is "+ (currAmountKnots/startAmountKnots)*100);
        while (p<((float)currAmountKnots/(float)startAmountKnots)*100){

            StrFloatTuple[] smallyEpsi = this.smallestEpsi("");
            float smollerEpsi = smallyEpsi[0].getEpsilonVal();
            String smollerPath = smallyEpsi[0].getPathway();
            // find the smallest epsilon
            for (int i = 1; i < smallyEpsi.length ; i++) {
                if (smallyEpsi[i].getEpsilonVal()<smollerEpsi){
                    smollerEpsi = smallyEpsi[i].getEpsilonVal();
                    smollerPath = smallyEpsi[i].getPathway();
                }
            }
            // get to that peculiar knot
            // and do the abandonchildren with the epsi val
            QT tempCute = this;
            while (smollerPath.length()>0){
                switch (smollerPath.charAt(0)){
                    case '1':
                        tempCute = tempCute.getV1();
                        break;
                    case '2':
                        tempCute = tempCute.getV2();
                        break;
                    case '3':
                        tempCute = tempCute.getV3();
                        break;
                    case '4':
                        tempCute = tempCute.getV4();
                        break;
                }
                smollerPath = smollerPath.substring(1);
            }
            tempCute.tree_compression_lambda();
            this.trueQT();
            currAmountKnots = this.getKnot();
            System.out.println("p is" + p + " currKnot is" + currAmountKnots + " startKnot is " + startAmountKnots + " val to comp is "+ ((float)currAmountKnots/(float)startAmountKnots)*100);
        }
    }

}
