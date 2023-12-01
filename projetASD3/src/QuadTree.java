import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuadTree extends QT {
    private int maxLum;
    private int size;
    private int[][] tab;

    private String fileRoute; // the pgm file's location

    public QuadTree(String file){
        super();
        setFileRoute(file);
        pgmToQT();
        //TODO: implement the bullshit to retrieve the other data from the file ( might need to call the function pgmToQT)
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


    private int pgmToQT(){
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
                    System.out.println(currLine);
                    count+=1;
                } else {
                    String[] tokens = currLine.split(" +");
                    switch (count) {
                        case 2 -> {
                            System.out.println(tokens[0]);
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

            System.out.println(e.toString());
        }
        return 0;
    }



    public void lambdaCompr(){
        //TODO: super the one from QT (its implementation here is maybe useless)
        //TODO: See method from page 4 of the project specifications
        //TODO: check if all 4 children don't have children
        //TODO: if verified do the compression
        //TODO: else recursive through the children with child(ren)
    }

    // Rho compression methods
    //TODO: evaluate epsilon gotta add the var in QT and the method in there aswell
    //TODO: find the smallest espilon

    public void rhoCompr(int p){
        //TODO: get amount of knot
        //TODO: while p> currAmountKnot/prevAmountKnot*100
        //TODO: find the smallest epsilon and do the basic compression implemented through QT
        //TODO: update all epsilon or only the parent's one
        //TODO: update currAmountKnot
    }

    private int qtToPgm(){
        //TODO: go through the QT returns array of tuple[coords pt, lum, height],
        //TODO: read this array and write it into the 2d array
        //TODO: w/ the 2d array and previous data write the PGM file
        return 0;
    }

}
