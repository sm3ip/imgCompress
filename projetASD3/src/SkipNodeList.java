public class SkipNodeList {
    // A node of the skip array class, it has its own skipnode value and a pointer towards the next element of the array
    SkipNode self;
    SkipNodeList next;

    /** Constructor taking as a parameter the value that you want to store
     *
     * @param skip a Skipnode
     */
    public SkipNodeList(SkipNode skip){
        this.self = skip;
        this.next = null;
    }
}
