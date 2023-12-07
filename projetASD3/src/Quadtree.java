import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

/** Represents the root of a guadtree it extends qt
 *
 * @see QT
 */
public class Quadtree extends QT {
    private int maxLum;
    private int size;
    private int[][] tab;

    private String fileRoute; // the pgm file's location

    /** Creates the QuadTree structure
     *
     * @param file the pgm file location
     */
    public Quadtree(String file) throws FileNotFoundException {
        super();
        setFileRoute(file);
        try{
            pgmToArr();
            arrToQT(this.tab,0,null);
            this.trueQT();
            this.determineEpsiLamb();
        } catch (FileNotFoundException e){
            throw e;
        }

    }

    // getters

    /** Gets the maximum luminosity of the picture
     *
     * @return the max luminosity
     */
    public int getMaxLum() {
        return maxLum;
    }

    /** gets the pgm's size
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /** Gets the 2D-array containing the pgm data
     *
     * @return the pgm's 2D-array
     */
    public int[][] getTab() {
        return tab;
    }

    /** Gets the pgm file location
     *
     * @return the string wich contains the pgm location on the disk
     */
    public String getFileRoute() {
        return fileRoute;
    }

    // setters

    /** Sets the maximum luminosity
     *
     * @param maxLum an int representing a luminosity
     */
    private void setMaxLum(int maxLum) {
        this.maxLum = maxLum;
    }

    /** Sets the pgm's size
     *
     * @param size a positive int
     */
    private void setSize(int size) {
        this.size = size;
    }

    /** Sets the 2D-array representing the pgm's data
     *
     * @param tab a 2D-array composed of int
     */
    private void setTab(int[][] tab) {
        this.tab = tab;
    }

    /** Sets the pgm file's location
     *
     * @param fileRoute string containing a pgm file's location
     */
    private void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }

    /** Reads a pgm file and stores it as a int 2D-array
     *
     */
    private void pgmToArr() throws FileNotFoundException {
        //reading the file
        try{
            int count =0;
            int i =0;
            int j =0;
            File thePgm = new File(this.getFileRoute());
            Scanner theReader = new Scanner(thePgm);
            while (theReader.hasNextLine()){
                String currLine = theReader.nextLine();
                if (count<2){
                    //Nothing matters to us before line 2
                    count+=1;
                } else {
                    // we split the line at each space to get nicely formatted data, we use " +" because there can be any amount
                    // of space in between the numbers (and at least 1)
                    String[] tokens = currLine.split(" +");
                    switch (count) {
                        case 2 -> { // in the case where the retrieved data is the picture's size
                            this.setSize(Integer.parseInt(tokens[0]));
                            this.setTab(new int[this.getSize()][this.getSize()]);
                            count+=1;
                        }
                        case 3 -> { // in the case where the retrieved data is the picture's max luminosity
                            this.setMaxLum(Integer.parseInt(tokens[0]));
                            count+=1;
                        }
                        default -> { // in all the other cases the line contains the pgm's pixels
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
            // if the file doesn't exist
            throw e;

        }
    }

    /** overriding QT's to string function to add the picture's size and max luminosity
     *
     * @return a readable String containing all information needed to rebuild the quadTree
     */
    @Override
    public String toString(){
        return this.size+":"+this.maxLum+":"+ super.toString();
    }

    /** Saves the current QuadTree structure as a readable and correctly formatted file
     *
     * @param location the location in which the file will be saved
     * @return true if it happened with no issue, false otherwise
     */
    public boolean saveCurrentQT(String location){
        // File creation
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
        // writing the data into the file
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

    /** Saves the current stored quadtree as a pgm file at a given location
     *
     * @param location the path to where the file is gonna be generated
     * @return true if works, false if otherwise
     */
    public boolean toPGM(String location){
        return strToPgm(this.toString(),location);
    }

    /** Saves a quadtree in the format of a String to a given location
     *
     * @param file quadtree as a tostring
     * @param newLocation the path to where the file is gonna be generated
     * @return true if works, false if otherwise
     */
    public static boolean strToPgm(String file,String newLocation){
        //processes the data
        // format is size:maxlum:(V1;V2;V3;V4)
        String[] data = file.split(":");
        int size = Integer.parseInt(data[0]);
        int maxLum = Integer.parseInt(data[1]);
        int[][] tempTab = new int[size][size];
        String[] tree = data[2].split(";");
        String whereWeAt= "1"; // keeps track to where we are in the tree
        // goes through each token
        for (int i = 0; i < tree.length; i++) {
            String buffer = ""; // this token's luminosity
            int rightPCount = 0; // amount of closing parentheses
            // goes through each char of the token
            for (int j = 0; j < tree[i].length(); j++) {
                if (tree[i].charAt(j)=='('){
                    // opening parentheses means that we are going down one level
                    whereWeAt = whereWeAt + "1";
                }else if (tree[i].charAt(j)==')'){
                    // gotta count them because each one of them mean that we'll have to go up a level after processing
                    // that token
                    rightPCount++;
                }else {
                    // storing the luminosity value decimal by decimal
                    buffer = buffer + tree[i].charAt(j);
                }
            }
            // we fill the corresponding sub array with this token's luminosity
            int workingSize = size; // the size in which we can fill the 2d array
            int orX = 0; // the x value of the area origin point
            int orY = 0; // the y value of the area origin
            // updates the area that's gonna be filled
            for (int j = 1; j < whereWeAt.length(); j++) {
                switch (whereWeAt.charAt(j)){
                    case '1':
                        // in the case of a top-left child
                        workingSize = workingSize/2;
                        break;
                    case '2':
                        // in the case of a top-right child
                        orY+= workingSize/2;
                        workingSize = workingSize/2;
                        break;
                    case '3':
                        // in the case of a bottom-right child
                        orY+= workingSize/2;
                        orX+= workingSize/2;
                        workingSize = workingSize/2;
                        break;
                    case '4':
                        // in the case of a bottom-left child
                        orX+= workingSize/2;
                        workingSize = workingSize/2;
                        break;
                }
            }

            //fills the previously computed area
            for (int j = orX; j < orX+workingSize ; j++) {
                for (int k = orY; k <orY+workingSize ; k++) {
                    tempTab[j][k] = Integer.parseInt(buffer);
                }
            }
            // go up one level for each closing parentheses recorded
            for (int j = 0; j < rightPCount; j++) {
                whereWeAt = whereWeAt.substring(0,whereWeAt.length()-1);
            }

            //updates last char when we get to the next token
            if (whereWeAt !=null && !whereWeAt.isEmpty()){
                char lastChar =whereWeAt.charAt(whereWeAt.length()-1);
                whereWeAt = whereWeAt.substring(0,whereWeAt.length()-1) + (Character.getNumericValue(lastChar)+1);
            }

        }
        // the data struct is saved in this function, now is the time to write it into a file
        try{ // creates the file
            File newPgmFile = new File(newLocation);
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
            FileWriter writy = new FileWriter(newLocation);
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

    /** Reads a quadtree file and creates the corresponding pgm file
     *
     * @param location String containing the quadTree File's location
     * @param producedLocation String containing the location inwhich the pgm will be stored
     * @return true if achieved, false otherwise
     */
    public static boolean qtFileToPgm(String location,String producedLocation){
        // retrieves the quadTree data as a String
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
        return strToPgm(temp,producedLocation);
    }

    /** Does the lambda compression and makes sure that the new quadtree is still a true quadtree
     *
     */
    public void compressLambda(){
        this.tree_compression_lambda(); // do the compression
        this.trueQT(); // gets it back as an actual quadTree
    }

    /** Does the Rho compression from the root
     *
     * @param p the int parameter deciding of the compression's strength
     */
    public boolean compressRho(int p){
        if (p<0||p>100){return false;}
        // finds the epsilons
        QtList smallEpsi = this.smallestEpsi();
        // saves the amount of nodes at the start
        int startAmountNodes = this.getNodes();
        int currAmountNodes = startAmountNodes;
        // while the compression is still not enough do it on the smallest epsilon
        while (p<((float)currAmountNodes/(float)startAmountNodes)*100 && currAmountNodes>1){
            // we wanna show progress at each occurence
            float prog = 100 -((((float)currAmountNodes/(float)startAmountNodes)*100-p)/((float)100-p))*100;
            String message = "Progress at "+ prog+"%";
            System.out.println(message);
            // find the tree's smallest epsilon
            QtList smallyEpsi = QtList.pop(smallEpsi);
            // finds the corresponding node
            QT tempCute = smallyEpsi.getQtObj();
            //tempCute.tree_compression_lambda();
            tempCute.abandonChildren(tempCute.getSelfLamb());
            // now check if tempCute's parent would be a "brindille"
            if (tempCute.getParent().isATwig()){
                smallEpsi = QtList.sFAdd(smallEpsi, new QtList(tempCute.getParent()));
            }
            currAmountNodes -= 4;
        }
        this.trueQT(); // is it overkill ?
        return true;
    }
}
