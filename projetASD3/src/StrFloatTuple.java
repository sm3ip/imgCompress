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
}
