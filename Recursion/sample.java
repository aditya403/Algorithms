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



    1) First command to check on firewall, sh run | i ipaddr
 
sh run | i 10.144.138.228
 
//output:
 
object network obj-10.144.138.228
host 10.144.138.228
 
network-object host 10.144.138.228
network-object host 10.144.138.228
network-object host 10.144.138.228
network-object host 10.144.138.228
network-object host 10.144.138.228
access-list CMA-VLAN127-10.144.160.208/28-IN extended permit tcp 10.144.160.208 255.255.255.240 object obj-10.144.138.228 eq smtp log
NAT config
//
 
1.a) In case if object name doesn't have IP and has some words, then you need to use same command "sh run | i <object-name>" to figure out the ACL and NAT related.
 
 
2)  We already got related config above for object.. since IP is part of five object group's as above (line 13 to 17). Use below command to find out the object-grp names
 
sho run object-group network | i object-group| ipAddr   
 
(you will five group in same output)
 
 
3) extract obj-grp to find out whether single input IP or mutiple.

