public enum Exp {
    LOW (1),
    MEDIUM (2),
    HIGH(3);
    private int val;
    Exp(int val){
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    static public int maxExp(){
        int max = Integer.MIN_VALUE;
        for(Exp exp: Exp.values()){
            if(exp.getVal() > max){
                max = exp.getVal();
            }
        }
        return max;
    }
}
