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
























======== PRE FINAL BACKOUT ========
[MCAFEE-AV-INTERNAL-SERVERS:[object-group network MCAFEE-AV-INTERNAL-SERVERS], FIS-FIREEYE-ENCASE-BIT9-MCAFEE:[object-group network FIS-FIREEYE-ENCASE-BIT9-MCAFEE], null:[access-list Outside-IN extended permit object-group ENCASE-MCAFEE-PORTS object-group MCAFEE-AV-INTERNAL-SERVERS object-group AvantGard_Servers_PHL_Inside log, access-list Inside-273_10.88.89.0/24-IN extended permit object HTTPS object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log, access-list Inside-273_10.88.89.0/24-IN extended permit object TCP-8080 object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log, access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object HTTPS object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log, access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object TCP-8080 object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log], FIREEYE-INTERNAL-SERVERS:[object-group network FIREEYE-INTERNAL-SERVERS]]
======== PRE FINAL BACKOUT ========

======== FINAL BACKOUT ========

======== FINAL BACKOUT ========
