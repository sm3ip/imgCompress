import java.lang.management.ThreadInfo;
import java.util.Random;

public class SkipList {
// a skip list implementation
    SkipNode minBot, maxBot, minTop, maxTop; // the borders of the skiplist
    int height; // the height of the skiplist

    /** a simple constructor taking no argument
     *
     */
    public SkipList(){
        // creates the 4 SkipNode objects
        this.minBot = new SkipNode(new QT(SkipNode.infNeg));
        this.maxBot = new SkipNode(new QT(SkipNode.infPos));
        this.minTop = new SkipNode(new QT(SkipNode.infNeg));
        this.maxTop = new SkipNode(new QT(SkipNode.infPos));
        // link elements of bottom list
        this.minBot.next = this.maxBot;
        this.maxBot.previous = this.minBot;
        //links elements of top list
        this.minTop.next = this.maxTop;
        this.maxTop.previous= this.minTop;
        //links min elements
        this.minBot.top = this.minTop;
        this.minTop.bottom = this.minBot;
        // links max elements
        this.maxBot.top = this.maxTop;
        this.maxTop.bottom = this.maxBot;
        this.height = 1;
    }

    /** deletes the minimal element from the skip list
     *
     * @return the value that was stored in it
     */
    public QT deleteMin(){
        // checks the conditions to being able to delete an element
        if (this.height>0 && this.minBot.next.x.getSelfEpsi()!=SkipNode.infPos){
            // get value by suppressing it
            QT ans = this.minBot.next.deleteSelf();
            //checks if there isnt too much layers
            if (this.minBot!=this.minTop){
                this.trueSkList();
            }
            return ans;
        }else {
            //if empty
            return null;
        }
    }


    /** Makes sure that the skip list doesn't have too many layers
     *
     */
    public void trueSkList(){
        // go on top, check if bottom one is empty if true delete it
        while (this.maxTop.bottom!=null && this.minTop.bottom!=null &&this.minTop.bottom.next.x.getSelfEpsi()==SkipNode.infPos){
            //update height
            this.height--;
            // delete link in the empty list
            this.minTop.bottom.next = null;
            this.maxTop.bottom.previous = null;
            // link with list under it
            this.minTop.bottom = this.minTop.bottom.bottom;
            this.maxTop.bottom = this.maxTop.bottom.bottom;
            //unlink bottom one
            // if not linking to null, links bottom list to top
            if (this.minTop.bottom!=null && this.maxTop.bottom!=null){
                // delete every pointers from the deleted nodes
                this.minTop.bottom.top.top=null;
                this.maxTop.bottom.top.top=null;
                this.minTop.bottom.top.bottom=null;
                this.maxTop.bottom.top.bottom=null;
                // relinks bottom list with top list
                this.minTop.bottom.top = this.minTop;
                this.maxTop.bottom.top = this.maxTop;
            }
        }
        if (this.height == 0){
            // if height to zero, resets the skipList to a new one
            this.minTop = new SkipNode(new QT(SkipNode.infNeg));
            this.maxTop = new SkipNode(new QT(SkipNode.infPos));
            this.minTop.next = this.maxTop;
            this.maxTop.previous = this.minTop;
            this.minBot = this.maxBot;
            this.maxBot = this.maxTop;
        }
    }

    /** adds an element to the skipList
     *
     * @param elem the QT you wanna add
     */
    public void addElem(QT elem){
        // gets the height at which it should be inserted using the random method
        int i = -1;
        Random rand = new Random();
        int randNb = 1;
        while (randNb!=0){
            i++;
            randNb = rand.nextInt(2);
        }
        if (i>=this.height){
            //adds the empty lists update height and top nodes i+1 -h times
            int temp = i+1 - this.height;
            for (int j = 0; j< temp;j++){
                SkipNode tempMin = new SkipNode(new QT(SkipNode.infNeg));
                SkipNode tempMax = new SkipNode(new QT(SkipNode.infPos));
                // links both of them
                tempMin.next = tempMax;
                tempMax.previous = tempMin;
                // links them with top
                tempMin.bottom=this.minTop;
                this.minTop.top = tempMin;
                tempMax.bottom=this.maxTop;
                this.maxTop.top = tempMax;
                // switch top to the upper level
                this.minTop = tempMin;
                this.maxTop = tempMax;
                this.height++;
            }
        }
        //find the smallest element smaller than elem from top to bottom line
        SkipArray tab = new SkipArray();
        SkipNode seekSmall = this.minTop;
        while (seekSmall.bottom!=null){
            seekSmall= seekSmall.bottom;
            while (seekSmall.next.x.getSelfEpsi()<elem.getSelfEpsi()){
                seekSmall=seekSmall.next;
            }
            tab.addIn(seekSmall);
        }
        // when found add the elem from layer 0 to layer i included
        SkipNode bot = null;
        SkipNodeList parkour = tab.first;
        for (int j = 0; j<i+1;j++) { // adds in the good order
            SkipNode tempNext = parkour.self.next;
            parkour.self.next = new SkipNode(elem);
            parkour.self.next.previous = parkour.self;
            parkour.self.next.bottom = bot;
            if (bot != null) {
                bot.top = parkour.self.next;
            }
            parkour.self.next.next = tempNext;
            tempNext.previous = parkour.self.next;
            bot = parkour.self.next;
            parkour = parkour.next;
        }
    }


    /** simple override of the toString method to show the structure of the skip-list
     *
     * @return a string representing the structure of the skip-list
     */
    @Override
    public String toString(){
        String ans = "";
        SkipNode heightParkour = this.minTop;
        while (heightParkour!=null){
            SkipNode widthParkour = heightParkour;
            while (widthParkour!=null){
                ans += " [ " + widthParkour.x.getSelfEpsi()+" ] ";
                widthParkour = widthParkour.next;
            }
            ans += "\n";
            heightParkour=heightParkour.bottom;
        }
        return ans;
    }
    //getters

    /** retrieves the node +infinity on the bottommost line
     *
     * @return maxBot
     */
    public SkipNode getMaxBot() {
        return maxBot;
    }

    /** retrieves the node +infinity on the topmost line
     *
     * @return maxTop
     */
    public SkipNode getMaxTop() {
        return maxTop;
    }

    /** retrieves the node -infinity on the bottommost line
     *
     * @return min Bot
     */
    public SkipNode getMinBot() {
        return minBot;
    }

    /** retrieves the node -infinity on the topmost line
     *
     * @return minTop
     */
    public SkipNode getMinTop() {
        return minTop;
    }

    //setters

    /** sets the node +infinity on the bottommost line
     *
     * @param maxBot a SkipNode
     */
    public void setMaxBot(SkipNode maxBot) {
        this.maxBot = maxBot;
    }

    /** sets the node +infinity on the topmost line
     *
     * @param maxTop a skipNode
     */
    public void setMaxTop(SkipNode maxTop) {
        this.maxTop = maxTop;
    }

    /** sets the node -infinity on the bottommost line
     *
     * @param minBot a skipNode
     */
    public void setMinBot(SkipNode minBot) {
        this.minBot = minBot;
    }

    /** sets the node -infinity on the topmost line
     *
     * @param minTop a skipNode
     */
    public void setMinTop(SkipNode minTop) {
        this.minTop = minTop;
    }
}
