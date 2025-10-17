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




    def installPlan = []
def objectPattern = ~/^object\s+network\s+([\w\-.]+)/
def ipPattern = /\b\d{1,3}(\.\d{1,3}){3}\b/

// Step 1: Identify object-network based entries
def hasObjectNetwork = str.any { it.trim() ==~ /^object\s+network\s+.*/ }

if (hasObjectNetwork) {
    for (int i = 0; i < str.size(); i++) {
        def line = str[i].trim()
        def matcher = (line =~ objectPattern)

        if (matcher.matches()) {
            def objectName = matcher[0][1]
            if (objectName == "") continue

            // Case 1: Object name contains IP
            if (objectName ==~ ipPattern) {
                installPlan << line
                if (i + 1 < str.size() && str[i + 1].trim().startsWith("host")) {
                    installPlan << str[i + 1].trim()
                }
            } 
            // Case 2: Word-based object name
            else {
                def cmdObjectName = "sh run | i ${objectName}"
                println "\nExecuting command: ${cmdObjectName}"

                def cmdObjectNameResult = execute.sendAndExpect(cmdObjectName, prompt, timeout, true)

                // Now process the result like a config dump
                cmdObjectNameResult.each { l ->
                    if (l.contains("access-list") || l.contains("nat")) {
                        installPlan << l.trim()
                    }
                }
            }
        }
    }
} else {
    // Step 2: No object network found → detect IPs from ACL/NAT lines

    str.each { line ->
        if (line.contains("access-list") || line.contains("nat")) {
            installPlan << line.trim()
        }
    }


    // def ips = [] as Set
    // str.each { line ->
    //     def matcher = (line =~ ipRegex)
    //     if (matcher.find()) {
    //         ips << matcher.group()
    //     }
    // }

    // ips.each { ip ->
    //     str.each { l ->
    //         if ((l.contains("access-list") || l.contains("nat")) && l.contains(ip)) {
    //             installPlan << l.trim()
    //         }
    //     }
    // }
}

// Step 3: Print Install Plan
println "==== INSTALL PLAN ===="
installPlan.each { println it }




