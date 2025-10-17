import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
public class sample {
    public static Stack<Integer> add(Stack<Integer> s, int m){
        if(s.peek()>=m){
            s.push(m);
            return s;
        }
        int n = s.pop();
        add(s, m);
        s.push(n);
        return s;
    }
    public static Stack<Integer> fun(Stack<Integer> s){
        if(s.size()==1){
            return s;
        }
        int m = s.pop();
        fun(s);
        
        return(add(s, m));
    }
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        s.add(5);
        s.add(6);
        s.add(0);
        s.add(1);
        s.add(2);
        s = fun(s);
        System.out.print(s);
    }
}



















sh run | i 10.91.242.5
 network-object host 10.91.242.5
 network-object host 10.91.242.5
 network-object host 10.91.242.5
access-list Global_ACL extended permit tcp host 10.91.242.5 10.91.240.0 255.255.248.0 eq 1556 
access-list Global_ACL extended permit tcp host 10.91.242.5 10.91.240.0 255.255.248.0 eq 13724 
access-list Global_ACL extended permit tcp host 10.91.242.5 10.91.240.0 255.255.248.0 eq 10102 
access-list Global_ACL extended permit tcp host 10.91.242.5 10.91.240.0 255.255.248.0 eq 10082 
access-list Global_ACL extended permit tcp 10.91.240.0 255.255.248.0 host 10.91.242.5 eq 1556 
access-list Global_ACL extended permit tcp 10.91.240.0 255.255.248.0 host 10.91.242.5 eq 13724 
access-list Global_ACL extended permit tcp 10.91.240.0 255.255.248.0 host 10.91.242.5 eq 10102 
access-list Global_ACL extended permit tcp 10.91.240.0 255.255.248.0 host 10.91.242.5 eq 10082 
