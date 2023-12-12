public class SkipNode {

    //bornes
    final static int infPos = 300;
    final static int infNeg = -1;

    QT x ; // the value

    //other nodes :
    SkipNode previous, next, top, bottom;

    public SkipNode(QT val){
        this.x = val;
        this.previous=null;
        this.next=null;
        this.bottom=null;
        this.top=null;
    }

    public QT deleteSelf(){
        if (this.x.getSelfEpsi()==infNeg|| this.x.getSelfEpsi() == infPos){
            // cant delete borders this way
            return null;
        }else {
            this.previous.next=this.next;
            this.next.previous=this.previous;
            if (this.top!=null){
                this.top.bottom=null;
                return this.top.deleteSelf();
            }else {
                return this.x;
            }
        }

    }

    //getters

    public SkipNode getBottom() {
        return bottom;
    }

    public SkipNode getNext() {
        return next;
    }

    public SkipNode getPrevious() {
        return previous;
    }

    public SkipNode getTop() {
        return top;
    }

    public int getInfNeg() {
        return infNeg;
    }

    public int getInfPos() {
        return infPos;
    }

    public QT getX() {
        return x;
    }

    //setters


    public void setBottom(SkipNode bottom) {
        this.bottom = bottom;
    }

    public void setNext(SkipNode next) {
        this.next = next;
    }

    public void setPrevious(SkipNode previous) {
        this.previous = previous;
    }

    public void setTop(SkipNode top) {
        this.top = top;
    }

    public void setX(QT x) {
        this.x = x;
    }
}
