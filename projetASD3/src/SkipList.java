import java.lang.management.ThreadInfo;
import java.util.Random;

public class SkipList {

    SkipNode minBot, maxBot, minTop, maxTop;
    int height;

    public SkipList(){
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

    public QT deleteMin(){
        if (this.minBot.next.x.getSelfEpsi()!=SkipNode.infPos){
            // get value by suppressing it
            QT ans = this.minBot.next.deleteSelf();
            //TODO: check if there isnt too much layers
            if (this.minBot!=this.minTop){
                this.trueSkList();
            }
            return ans;
        }else {
            //si empty
            return null;
        }
    }



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
            // if not linking to null, links bottom list to top
            if (this.minTop.bottom!=null && this.maxTop.bottom!=null){
                this.minTop.bottom.top = this.minTop;
                this.maxTop.bottom.top = this.maxTop;
            }

        }
    }

    public void addElem(QT elem){
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
        //SkipNode[] tab = new SkipNode[];
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

    public SkipNode getMaxBot() {
        return maxBot;
    }

    public SkipNode getMaxTop() {
        return maxTop;
    }

    public SkipNode getMinBot() {
        return minBot;
    }

    public SkipNode getMinTop() {
        return minTop;
    }

    //setters

    public void setMaxBot(SkipNode maxBot) {
        this.maxBot = maxBot;
    }

    public void setMaxTop(SkipNode maxTop) {
        this.maxTop = maxTop;
    }

    public void setMinBot(SkipNode minBot) {
        this.minBot = minBot;
    }

    public void setMinTop(SkipNode minTop) {
        this.minTop = minTop;
    }
}
