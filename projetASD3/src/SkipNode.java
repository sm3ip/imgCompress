public class SkipNode {

    // represents a single node of a skip-list

    //boundaries, we know that epsilon can't get past those values
    final static int infPos = 300;
    final static int infNeg = -1;

    QT x ; // the QT objects we are storing in this particular node

    //other nodes :
    SkipNode previous, next, top, bottom; // all its neighbours

    /** simple constructor taking a Qt as a parameter
     *
     * @param val the QT that will be stored in this node
     */
    public SkipNode(QT val){
        this.x = val;
        this.previous=null;
        this.next=null;
        this.bottom=null;
        this.top=null;
    }

    /** Methods used to delete a node and all its occurrences while relinking the skipList correctly
     *
     * @return the value that was stored in the Node
     */
    public QT deleteSelf(){
        if (this.x.getSelfEpsi()==infNeg|| this.x.getSelfEpsi() == infPos){
            // cant delete borders this way
            return null;
        }else {
            // links its next and previous neighbours
            this.previous.next=this.next;
            this.next.previous=this.previous;
            // check if there is an occurrence of itself on top
            if (this.top!=null){
                //unlinks top and do a recursive call on the higher level
                this.top.bottom=null;
                return this.top.deleteSelf();
            }else {
                // if we reached the end returns the value
                return this.x;
            }
        }

    }

    //getters

    /** retrieves the bottom node
     *
     * @return bottom
     */
    public SkipNode getBottom() {
        return bottom;
    }

    /** retrieves the next node
     *
     * @return the node to the right
     */
    public SkipNode getNext() {
        return next;
    }

    /** retrieves the previous node
     *
     * @return the node to the left
     */
    public SkipNode getPrevious() {
        return previous;
    }

    /** retrieves the higher level node
     *
     * @return the node on top
     */
    public SkipNode getTop() {
        return top;
    }

    /** gets the -infinity value
     *
     * @return an int defining the lower border
     */
    public int getInfNeg() {
        return infNeg;
    }

    /** gets the +infinity value
     *
     * @return an int defining the higher border
     */
    public int getInfPos() {
        return infPos;
    }

    /** retrieves the stored value
     *
     * @return the stored QT object
     */
    public QT getX() {
        return x;
    }

    //setters


    /** sets the bottom Node
     *
     * @param bottom a skipNode
     */
    public void setBottom(SkipNode bottom) {
        this.bottom = bottom;
    }

    /** sets the next node
     *
     * @param next a skipNode
     */
    public void setNext(SkipNode next) {
        this.next = next;
    }

    /** sets the previous node
     *
     * @param previous a skipNode
     */
    public void setPrevious(SkipNode previous) {
        this.previous = previous;
    }

    /** sets the top node
     *
     * @param top a skipNode
     */
    public void setTop(SkipNode top) {
        this.top = top;
    }

    /** sets the value stored in that particular node
     *
     * @param x a QT object
     */
    public void setX(QT x) {
        this.x = x;
    }
}
