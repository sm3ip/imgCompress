public class AVLTree{
    public int bal;
    public float value;
    public QtList list;
    public AVLTree left,right;

    public AVLTree(QtList val){
        this.value = val.getQtObj().getSelfEpsi();
        this.left = null;
        this.right = null;
        this.bal = 0;
        this.list = val;
    }

    public AVLTree(QtList val, AVLTree left,AVLTree right){
        this.value = val.getQtObj().getSelfEpsi();
        this.list = val;
        this.left = left;
        this.right = right;
    }


    public static AVLTree rotLeft(AVLTree A){
        AVLTree B = A.right;
        int a = A.bal;
        int b = B.bal;
        A.right = B.left;
        B.left = A;
        A.bal = a-Math.max(b,0)-1;
        B.bal = b-1+Math.min(A.bal,0);
        System.out.println("HERE" + B.toString());
        return B;
    }

    public static AVLTree dRotLeft(AVLTree A){
        A.right = AVLTree.rotRight(A.right);
        return AVLTree.rotLeft(A);
    }

    public static AVLTree dRotRight(AVLTree A){
        A.left = AVLTree.rotLeft(A.left);
        return AVLTree.rotRight(A);
    }

    public static AVLTree balanceOut(AVLTree A){
        if (A.bal==2){
            if (A.right.bal>= 0){
                return rotLeft(A);
            }else {
                return dRotLeft(A);
            }
        } else if (A.bal ==-2) {
            if (A.left.bal <=0){
                return rotRight(A);
            }else {
                return dRotRight(A);
            }
        }else {
            return A;
        }
    }


    public static AVLTree rotRight(AVLTree A){
        AVLTree B = A.left;
        int a = A.bal;
        int b = B.bal;
        A.left = B.right;
        B.right = A;
        A.bal = a- Math.min(a,0)+1;
        B.bal = b+1+Math.max(A.bal,0);
        return B;
    }

    public AVLTree getLeft(){
        return this.left;
    }

    public AVLTree getRight(){
        return this.right;
    }

    public float getValue(){
        return this.value;
    }

    public boolean isEmpty(){
        return this.value == -1;
    }

    public static boolean exists(float x, AVLTree A){
        if (A == null || A.isEmpty()){
            return false;
        }else if (x == A.getValue()){
            return true;
        }else if (x < A.getValue()){
            return AVLTree.exists(x,A.getLeft());
        }else {
            return AVLTree.exists(x,A.getRight());
        }
    }

    public static AVLTree search(float x, AVLTree A){
        if (A == null || A.isEmpty()){
            return null;
        }else if (x == A.getValue()){
            return A;
        }else if (x < A.getValue()){
            return AVLTree.search(x,A.getLeft());
        }else {
            return AVLTree.search(x,A.getRight());
        }
    }

    public static AvlInt add(QT x, AVLTree A){
        int h;
        AvlInt temp;
        if (A==null||A.isEmpty()){
            AVLTree B = new AVLTree( new QtList(x),null,null);
            B.bal =0;
            return new AvlInt(B,1);
        } else if (x.getSelfEpsi()<A.getValue()) {
            temp =AVLTree.add(x,A.left);
            h = -temp.i;
            A.left = temp.tree;
        } else if(x.getSelfEpsi()>A.getValue()) {
            temp =AVLTree.add(x,A.right);
            h = temp.i;
            A.right = temp.tree;
        }else {
            A.list = QtList.simpleAdd(A.list,new QtList(x));
            return new AvlInt(A,0);
        }
        if (h==0){
            return new AvlInt(A,0);
        }else {
            A.bal = A.bal + h;
            A = AVLTree.balanceOut(A);
            return A.bal==0 ? new AvlInt(A,0):new AvlInt(A,1);
        }
    }

    public static AvlInt addList(QtList x, AVLTree A){
        int h;
        AvlInt temp;
        if (A==null||A.isEmpty()){
            AVLTree B = new AVLTree( x,null,null);
            B.bal =0;
            return new AvlInt(B,1);
        } else if (x.getQtObj().getSelfEpsi()<A.getValue()) {
            temp =AVLTree.addList(x,A.left);
            h = -temp.i;
            A.left = temp.tree;
        } else if(x.getQtObj().getSelfEpsi()>A.getValue()) {
            temp =AVLTree.addList(x,A.right);
            h = temp.i;
            A.right = temp.tree;
        }else {
            A.list = QtList.simpleAdd(A.list,x);
            h=0;
            return new AvlInt(A,0);
        }
        if (h==0){
            return new AvlInt(A,0);
        }else {
            A.bal = A.bal + h;
            A = AVLTree.balanceOut(A);
            return A.bal==0 ? new AvlInt(A,0):new AvlInt(A,1);
        }
    }

    public static AVLTree max(AVLTree A){
        if (A==null|| A.isEmpty()){
            return null;
        } else if (A.right!=null) {
            return max(A.right);
        }else {
            return A;
        }
    }

    public static AVLTree supprMax(AVLTree A){
        AVLTree temp = A;
        if (A.right == null){
            return A.left;
        }
        while (temp.right.right!=null){
            temp = temp.right;
        }
        temp.right=temp.right.left;
        return A;
    }

    public static AvlInt supprMin(AVLTree A){
        if (A.left == null){
            return new AvlInt(A.right,-1);
        }else {
            AvlInt temp = supprMin(A.left);
            int h = -temp.i;
            if (h==0){
                return new AvlInt(A,0);
            }else {
                A.bal = A.bal +h;
                A = AVLTree.balanceOut(A);
                return  (A.bal ==0)? new AvlInt(A,-1):new AvlInt(A,0);
            }
        }
    }

    public static AVLTree min(AVLTree A){
        if (A==null|| A.isEmpty()){
            return null;
        } else if (A.left!=null) {
            return min(A.left);
        }else {
            return A;
        }
    }

    public static AvlInt delete(float x,AVLTree A){
        AvlInt temp = null;
        if (A== null||A.isEmpty() ){
            return new AvlInt(A,0);
        } else if (x<A.getValue()) {
            temp = delete(x,A.left);
            int h = -temp.i;
            A.left = temp.tree;
        } else if (x>A.getValue()) {
            temp = delete(x,A.right);
            int h = temp.i;
            A.right = temp.tree;
        }else { // in the case of an equality
            // delete the qt
            QtList.pop(A.list);
            //then check if list empty
            if (A.list==null) { // suppress here
                if (A.left == null || A.left.isEmpty()) {
                    return new AvlInt(A.right, -1);
                } else if (A.right == null || A.right.isEmpty()) {
                    return new AvlInt(A.left, -1);
                } else {
                    A.value = AVLTree.min(A.right).value;
                    temp = AVLTree.supprMin(A.right);
                    A.right = temp.tree;
                }
            }else {
                temp = new AvlInt(null,0);
                //temp.i = 0;
            }
        }
        if (temp.i == 0){
            return new AvlInt(A,0);
        }else {
            A.bal = A.bal+ temp.i;
            A = balanceOut(A);
            return A.bal==0 ? new AvlInt(A,-1):new AvlInt(A,0);
        }
    }

    public static AVLTree fuseTrees(AVLTree t1, AVLTree t2){
        if (t1==null && t2 ==null){
            return null;
        } else if (t1==null) {
            return t2;
        }else if (t2==null){
            return t1;
        }else {
            t1 = AVLTree.addList(t2.list, t1).tree;

            t1 = fuseTrees(t1,t2.left);
            t1 = fuseTrees(t1,t2.right);
            return t1;
        }
    }

    @Override
    public String toString(){

        String right = this.right!=null? this.right.toString(): "__";
        String left = this.left != null? this.left.toString(): "__";
        return "("+left+";"+this.value+"|"+this.bal+":"+right+")";
    }

}
