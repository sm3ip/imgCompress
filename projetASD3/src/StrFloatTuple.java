/** Represent a data structure holding a String and a float
 * */
public class StrFloatTuple {
    private String pathway;
    private float epsilonVal;

    /** Creates a tuple
     * @param _pathway the path to get to the corresponding knot in the QT
     * @param _epsilonValue the epsilon value of this specific knot
     */
    public StrFloatTuple(String _pathway, float _epsilonValue){
        this.pathway=_pathway;
        this.epsilonVal=_epsilonValue;
    }

    // getters

    /** Gets the value of epsilon
     *
     * @return the value of epsilon
     */
    public float getEpsilonVal() {
        return epsilonVal;
    }

    /** Gets the pathway
     *
     * @return a string containing a path leading to the knot
     */
    public String getPathway() {
        return pathway;
    }

    /** Chooses the smallest epsilon between two tuples
     *
     * @param v1Epsis one of the two value to get compared
     * @param v2Epsis the second value to get compared
     * @return the tuple with the smallest epsilon between the two parameters
     */
    public static StrFloatTuple sFChooseSmallest(StrFloatTuple v1Epsis, StrFloatTuple v2Epsis) {
        // taking care of edge cases
        if (v1Epsis==null && v2Epsis==null){
            return null;
        } else if (v1Epsis==null) {
            return v2Epsis;
        } else if (v2Epsis==null) {
            return v1Epsis;
        }else { // if not an edge case returns the smallest
            return (v1Epsis.getEpsilonVal()< v2Epsis.getEpsilonVal()) ?(v1Epsis):(v2Epsis);
        }
    }
}
