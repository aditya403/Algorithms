public class lexicographicallySorted {
    public static void print(String ip, String op){
        if(ip.isEmpty()){
            System.out.println(op);
            return;
        }
        String op1 = op;
        String op2 = op;
        op2 += ip.charAt(0);
        ip = ip.substring(1);
        print(ip, op1);
        print(ip, op2);
        return;
    }
    public static void main(String[] args) {
        String input = "122";
        String output = "";
        print(input, output);
    
    }
}

