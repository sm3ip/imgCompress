public class StrFloatTuple {
    private String pathway;
    private float epsilonVal;
    public StrFloatTuple(String _pathway, float _epsilonValue){
        this.pathway=_pathway;
        this.epsilonVal=_epsilonValue;
    }
    // getters

    public float getEpsilonVal() {
        return epsilonVal;
    }

    public String getPathway() {
        return pathway;
    }

    public static StrFloatTuple sFChooseSmallest(StrFloatTuple v1Epsis, StrFloatTuple v2Epsis) {
        // might have to put it in its class
        if (v1Epsis==null && v2Epsis==null){
            return null;
        } else if (v1Epsis==null) {
            return v2Epsis;
        } else if (v2Epsis==null) {
            return v1Epsis;
        }else {
            if (v1Epsis.getEpsilonVal()< v2Epsis.getEpsilonVal()){
                return v1Epsis;
            }else {
                return v2Epsis;
            }
        }
    }
}
