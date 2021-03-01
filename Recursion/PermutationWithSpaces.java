

public class PermutationWithSpaces {
    public static void permute(String ip, String op, int n){
        if(ip.length()==0){
            System.out.println(op);
            return;
        }
        String op1 = op;
        String op2 = op;
        if(ip.length()<n &&ip.length()>1)
            op2 += new StringBuilder(ip).insert(0, " ").toString();
        else{
            op2 += ip.charAt(0);
        }
        ip = ip.substring(1);
        permute(ip, op1, n);
        permute(ip, op2, n);
        return;

    }
    public static void main(String[] args) {
        String num = "ABC";
        String output = "";
        permute(num, output, num.length());
    }
}
