public class PrintNbitBinary {
    public static boolean cheak(String op){
        int[] n = new int[op.length()];
        int countOne = 0;
        int countZero = 0;
        for(int i =0; i<op.length(); i++){
            if(Character.getNumericValue(op.charAt(i))==0){
                countZero++;
            }
            else{
                countOne++;
            }
        }
        if(countOne==countZero)
            return true;
        else    
            return false;
    }
    public static void find(int ip, String op){
        if(ip==0){
            System.out.print(op + "   ");
            return;
        }
        if(cheak(op)|| op.isEmpty()){
            op += "1";
            ip--;
            find(ip, op);
        }
        else{
            String op1 = op;
            String op2 = op;
            op1 += "0";
            op2 += "1";
            ip--;
            find(ip, op1);
            find(ip, op2);
        }
        return;
    }
    public static void main(String[] args) {
        int ip = 18;
        String op = "";
        find(ip, op);
    }
}
