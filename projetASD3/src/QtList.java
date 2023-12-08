/**
 * Represents a List of QT
 * */

//TODO: modify it as a tree avl
public class QtList {
    private QT qtObj; // the current object
    private QtList right; // the next object in the list
    private QtList left;
    private int balance;

    /** Creates a list
     * @param obj the qt to set as the value of this part of the list
     */
    public QtList(QT obj,int height){
        this.qtObj =obj;
        balance = 0;
        right = null;
        left = null;
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

    public int getHeight() {
        return height;
    }

    /** Sets the current value of the list
     *
     * @param qtObj a QT object
     */
    private void setQtObj(QT qtObj) {
        this.qtObj = qtObj;
    }

    public boolean simpleAdd(QT qt){
        if (qt.getSelfEpsi()<this.qtObj.getSelfEpsi()){
            if (this.left!=null){
                this.left.simpleAdd(qt);
            }else {
                this.left = new QtList(qt,this.getHeight()+1);
            }
        }else {
            if (this.right!=null){
                this.right.simpleAdd(qt);
            }else {
                this.right = new QtList(qt, this.getHeight()+1);
            }
        }
    }

    /** Creates a sorted list from two sorted lists
     *
     * @param tree1 the first list
     * @param tree2 the second list
     * @return a sorted list containing all elements from both lists
     */
    public static QtList sFAdd(QtList tree1, QtList tree2) {
        // taking care of edge cases
        if (tree1==null && tree2==null){
            return null;
        } else if (tree1==null) {
            return tree2;
        } else if (tree2==null) {
            return tree1;
        }else { // if not an edge case merges the two trees
            //TODO: merge the 2 trees

            return beg;
        }
    }

    public static QtList rOTG(QtList A){
        QtList B = new QtList()
    }



    /** Pops the first element of a given list
     *
     * @param me the list from which we wanna delete the first element
     * @return the first element of that list
     */
    public static QtList pop(QtList me){
        QtList temp = new QtList(me.getQtObj(),0);
        me.qtObj =me.right.getQtObj();
        me.right =me.right.right;
        return temp;
    }
    // avl rotations
    public QtList ROTG(QtList A){
        QtList tempB;
        int a,b;
        tempB = A.getRight();
        a=A.getBalance();
        b= tempB.getBalance();
        A.setRight(tempB.getLeft());
        tempB.setLeft(A);
        A.setBalance(a-Math.max(b,0)-1);
        tempB.setBalance(Math.min(a-2,Math.min(a+b-2,b-1)));
        return tempB;
    }

    public QtList ROTD(QtList A){
        QtList tempB;
        int a,b;
        tempB = A.getLeft();
        a=A.getBalance();
        b= tempB.getBalance();
        A.setLeft(tempB.getRight());
        tempB.setRight(A);
        A.setBalance(a+Math.max(b,0)+1);
        tempB.setBalance(Math.min(a+2,Math.min(a+b+2,b+1)));
        return tempB;
    }

    public QtList stabilise(QtList A){
        switch (A.getBalance()){
            case 2:
                if (A.getRight().getBalance() < 0) {
                    A.setRight(ROTD(A.getRight()));
                }
                return ROTG(A);
            case -2:
                if (A.getLeft().getBalance() > 0) {
                    A.setLeft(ROTG(A.getLeft()));
                }
                return ROTD(A);
            default:
                return A;
        }
    }

    public QtList minTree(){
        if (this.getLeft()!=null){
            return this.getLeft();
        }else {
            return this;
        }
    }

    public QtList maxTree(){
        if (this.getRight()!=null){
            return this.getRight();
        }else {
            return this;
        }
    }

    public int addSmth(int value){
        //TODO: modify it to fit
        if (this.isEmpty()){
            this.setIsEmpty(false);
            this.setValue(value);
            this.balance =0;
            this.left = new treeAVL();
            this.right = new treeAVL();
            return 1;
        }else {
            if (this.getValue()<value){
                this.setBalance(this.getBalance()+Math.abs(this.getRight().addSmth(value)));
                // check balance
            } else if (this.getValue()>value) {
                this.setBalance(this.getBalance()-Math.abs(this.getLeft().addSmth(value)));
            }
            if (this.getBalance()==2 || this.getBalance()==-2){
                this.stabilise(this);
                return 0;
            }else {
                return Math.abs(this.getBalance());
            }
        }

    }

    private QtList getRight() {
        return this.right;
    }

    public int getBalance(){
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setRight(QtList right) {
        this.right = right;
    }

    public void setLeft(QtList left) {
        this.left = left;
    }

    public QtList getLeft(){
        return this.left;
    }
}
