/** Represent a data structure holding a String and a float
 * */
public class StrFloatList {
    private QT qtObj;

    private StrFloatList next;

    /** Creates a tuple
     * @param _pathway the path to get to the corresponding knot in the QT
     */
    public StrFloatList(QT _pathway){
        this.qtObj =_pathway;
        //this.epsilonVal=_epsilonValue;
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

    /** Gets the pathway
     *
     * @return a string containing a path leading to the knot
     */
    public QT getQtObj() {
        return qtObj;
    }

    public void setQtObj(QT qtObj) {
        this.qtObj = qtObj;
    }

    /** Chooses the smallest epsilon between two tuples
     *
     * @param v1Epsis one of the two value to get compared
     * @param v2Epsis the second value to get compared
     * @return the tuple with the smallest epsilon between the two parameters
     */
    public static StrFloatList sFAdd(StrFloatList v1Epsis, StrFloatList v2Epsis) {
        // taking care of edge cases
        if (v1Epsis==null && v2Epsis==null){
            return null;
        } else if (v1Epsis==null) {
            return v2Epsis;
        } else if (v2Epsis==null) {
            return v1Epsis;
        }else { // if not an edge case returns the smallest
            StrFloatList beg = (v1Epsis.getEpsilonVal()< v2Epsis.getEpsilonVal())?(v1Epsis):(v2Epsis);
            StrFloatList other = (v1Epsis.getEpsilonVal()>= v2Epsis.getEpsilonVal())?(v1Epsis):(v2Epsis);
            StrFloatList iterator = beg;
            StrFloatList tmp = null;
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

    public static StrFloatList pop(StrFloatList me){
        StrFloatList temp = new StrFloatList(me.getQtObj());
        me.qtObj =me.next.getQtObj();
        me.next=me.next.next;
        return temp;
    }
}
