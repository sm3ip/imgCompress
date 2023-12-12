public class SkipNodeList {
    SkipNode self;
    SkipNodeList next;

    public SkipNodeList(SkipNode skip){
        this.self = skip;
        this.next = null;
    }
}
