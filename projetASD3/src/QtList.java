/**
 * Represents a List of QT
 * */
public class QtList {
    private QT qtObj; // the current object
    private QtList next; // the next object in the list

    /** Creates a list
     * @param obj the qt to set as the value of this part of the list
     */
    public QtList(QT obj){
        this.qtObj =obj;
        next = null;
    }

    // getters

    /** Gets the value of epsilon
     *
     * @return the value of epsilon
     */
    public float getEpsilonVal() {
        return qtObj.getSelfEpsi();
    }

    /** Gets the list's value
     *
     * @return the qt object
     */
    public QT getQtObj() {
        return qtObj;
    }

    /** Sets the current value of the list
     *
     * @param qtObj a QT object
     */
    private void setQtObj(QT qtObj) {
        this.qtObj = qtObj;
    }

    /** Creates a sorted list from two sorted lists
     *
     * @param list1 the first list
     * @param list2 the second list
     * @return a sorted list containing all elements from both lists
     */
    public static QtList sFAdd(QtList list1, QtList list2) {
        // taking care of edge cases
        if (list1==null && list2==null){
            return null;
        } else if (list1==null) {
            return list2;
        } else if (list2==null) {
            return list1;
        }else { // if not an edge case merges the two lists
            // we're working with 4 pointers :
            // beg points on the smallest first element and will be our anchor for the return
            // and then with the three others we get iterator to point on the smallest element of the first list as well
            // and other to point to the element that's being compared on the other list
            // while the next value of iterator is smaller than the value of other we increment iterator
            // when its no more the case we set the next value of iterator as the value that other was pointing to
            // and set other to the previous value of iterator.next that was stored in a temporary pointer
            // We do all that until other is set to null
            QtList beg = (list1.getEpsilonVal()< list2.getEpsilonVal())?(list1):(list2);
            QtList other = (list1.getEpsilonVal()>= list2.getEpsilonVal())?(list1):(list2);
            QtList iterator = beg;
            QtList tmp = null;
            while (other !=null){
                while (iterator.next !=null && iterator.next.getEpsilonVal()< other.getEpsilonVal()){
                    iterator = iterator.next;
                }
                tmp = iterator.next;
                iterator.next = other;
                other = tmp;
                iterator = iterator.next;
            }
            return beg;
        }
    }

    /** Pops the first element of a given list
     *
     * @param me the list from which we wanna delete the first element
     * @return the first element of that list
     */
    public static QtList pop(QtList me){
        QtList temp = new QtList(me.getQtObj());
        me.qtObj =me.next.getQtObj();
        me.next=me.next.next;
        return temp;
    }
}
