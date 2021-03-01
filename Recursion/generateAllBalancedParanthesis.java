import javax.lang.model.util.ElementScanner14;

public class generateAllBalancedParanthesis {
    static int count = 0;
    public static void find(int Lip, int Rip, String op){
        if(Lip==0 && Rip==0){
            System.out.print(op + "    ");
            count++;
            return;
        }
        
        if(Lip!=0){
            String op1 = op;
            op1 += "(";
            find(Lip-1, Rip, op1);
        }
        if(Lip<Rip){
            String op2 = op;
            op2 += ")";
            find(Lip, Rip-1, op2);
        }
        return;
        
    }
    public static void main(String[] args) {
        int num = 5;
        int Lip = num;
        int Rip = num;
        String op = "";
        find(Lip, Rip, op);
        System.out.println(count);
    }
}
