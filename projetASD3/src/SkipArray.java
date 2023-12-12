public class SkipArray {
    // an array used to store the positions at which you want to store a new element in a skip list (keeps the good order aswell)
    SkipNodeList first, last;

    /** simple constructor taking no argument
     *
     */
    public SkipArray(){
        this.first = null;
        this.last = null;
    }

    /** Adds a new node to the list at the first place
     *
     * @param sk a skipNode (skip-list node) to add to this array
     */
    public void addIn(SkipNode sk){
        if (this.last ==null && this.first == null){
            this.first = new SkipNodeList(sk);
            this.last = this.first;
        }else {
            SkipNodeList temp  = new SkipNodeList(sk);
            temp.next = this.first;
            this.first = temp;
        }
    }
}
