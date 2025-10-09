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
























The situation here is. This is the backout script for Firewall decommissioning. There is one issue which is the sequence of the script. The sequence should be like this... Object Group Parent object ACL NAT How to identify these 1. Object Group: The line starts with "object-group". eg: "object-group network MCAFEE-AV-INTERNAL-SERVERS", "object-group network FIREEYE-INTERNAL-SERVERS", "object-group network ABC", etc. Followed by one or more "network-object". Examples: object-group network ABC network-object host 168.162.99.132 object-group network FIREEYE-INTERNAL-SERVERS network-object host 168.162.99.132 object-group network MCAFEE-AV-INTERNAL-SERVERS network-object host 10.238.67.18 network-object host 168.162.99.130 network-object host 10.238.67.19 network-object host 168.162.99.132 object-group network XYZ network-object host 168.162.99.132 2. Parent Object: The line starts with "object-group". eg: "object-group network FIS-FIREEYE-ENCASE-BIT9-MCAFEE". Followed by "group-objects" Example: object-group network FIS-FIREEYE-ENCASE-BIT9-MCAFEE group-object MCAFEE-AV-INTERNAL-SERVERS 3. ACL: Lines starts with "access-list". The line contains "object-group" which we identify while making sequence Example: access-list Outside-IN extended permit object-group ENCASE-MCAFEE-PORTS object-group MCAFEE-AV-INTERNAL-SERVERS object-group AvantGard_Servers_PHL_Inside log access-list Inside-273_10.88.89.0/24-IN extended permit object HTTPS object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log access-list Inside-273_10.88.89.0/24-IN extended permit object TCP-8080 object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object HTTPS object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object TCP-8080 object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log 4. NAT: Lines starts with "nat". The line contains "object-group" which we identify while making sequence Example: nat (Outside,any) source static MCAFEE-AV-INTERNAL-SERVERS MCAFEE-AV-INTERNAL-SERVERS destination static AvantGard_Servers_PHL_Inside AvantGard_Servers_PHL_Inside Example ======== INPUT sequence: --------------- object-group network FIREEYE-INTERNAL-SERVERS network-object host 168.162.99.132 object-group network MCAFEE-AV-INTERNAL-SERVERS network-object host 10.238.67.18 network-object host 168.162.99.130 network-object host 10.238.67.19 network-object host 168.162.99.132 object-group network FIS-FIREEYE-ENCASE-BIT9-MCAFEE group-object MCAFEE-AV-INTERNAL-SERVERS access-list Outside-IN extended permit object-group ENCASE-MCAFEE-PORTS object-group MCAFEE-AV-INTERNAL-SERVERS object-group AvantGard_Servers_PHL_Inside log access-list Inside-273_10.88.89.0/24-IN extended permit object HTTPS object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log access-list Inside-273_10.88.89.0/24-IN extended permit object TCP-8080 object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object HTTPS object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object TCP-8080 object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log nat (Outside,any) source static MCAFEE-AV-INTERNAL-SERVERS MCAFEE-AV-INTERNAL-SERVERS destination static AvantGard_Servers_PHL_Inside AvantGard_Servers_PHL_Inside object-group network XYZ network-object host 168.162.99.132 OUTPUT Sequence: ---------------- object-group network FIREEYE-INTERNAL-SERVERS network-object host 168.162.99.132 object-group network MCAFEE-AV-INTERNAL-SERVERS network-object host 10.238.67.18 network-object host 168.162.99.130 network-object host 10.238.67.19 network-object host 168.162.99.132 object-group network XYZ network-object host 168.162.99.132 object-group network FIS-FIREEYE-ENCASE-BIT9-MCAFEE group-object MCAFEE-AV-INTERNAL-SERVERS access-list Outside-IN extended permit object-group ENCASE-MCAFEE-PORTS object-group MCAFEE-AV-INTERNAL-SERVERS object-group AvantGard_Servers_PHL_Inside log access-list Inside-273_10.88.89.0/24-IN extended permit object HTTPS object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log access-list Inside-273_10.88.89.0/24-IN extended permit object TCP-8080 object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object HTTPS object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object TCP-8080 object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log nat (Outside,any) source static MCAFEE-AV-INTERNAL-SERVERS MCAFEE-AV-INTERNAL-SERVERS destination static AvantGard_Servers_PHL_Inside AvantGard_Servers_PHL_Inside

