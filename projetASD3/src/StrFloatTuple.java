import java.util.Arrays;

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

    public static StrFloatTuple[] sFMerge(StrFloatTuple[] v1Epsis, StrFloatTuple[] v2Epsis) {
        // might have to put it in its class
        int v1Len = (v1Epsis == null)?(0):(v1Epsis.length);
        int v2Len = (v2Epsis == null)?(0):(v2Epsis.length);
        StrFloatTuple[] v12Epsis = null;
        if (v1Len+v2Len==0){
            //StrFloatTuple[]v12Epsis = null;
        } else if (v1Len==0) {
            v12Epsis = v2Epsis;
        } else if (v2Len==0) {
            v12Epsis = v1Epsis;
        }else {
            v12Epsis = Arrays.copyOf(v1Epsis,v1Epsis.length+v2Epsis.length);
            System.arraycopy(v2Epsis,0,v12Epsis,v1Epsis.length,v2Epsis.length);
        }
        return v12Epsis;
    }
}
