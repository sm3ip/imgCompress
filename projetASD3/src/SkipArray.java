public class SkipArray {
    SkipNodeList first, last;

    public SkipArray(){
        this.first = null;
        this.last = null;
    }

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
