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























"object network obj-10.236.122.68",
"no host 10.236.122.68",
"access-list PUB-VLAN-10.236.122.64 extended permit tcp object obj-10.236.122.68 object-group PUBLICIES_EXTERNAL eq 22999 log",
"nat (PUB-VLAN-10.236.122.64/27,outside) source dynamic obj-10.236.122.68 interface destination static PUBLICIES_EXTERNAL PUBLICIES_EXTERNAL"





    if(script.size() != 0){
			        // def displayHost = deviceContextMap.containsKey(host) ? (host + " (" + deviceContextMap[host] + ")") : host;
			        if(asaList.contains(host)) { displayHost = "ASA:" + displayHost }	
			        
			        // =========================================================DEVELOPMENT GONG ON=======================================
			        // def objectGroups = [:]
			        // def parentObjects = [:]
			        // def acls = []
			        // def nats = []
			
			        // def current = []
			        // def currentName = null
			        // def isParent = false
			        
			        detail += "\n===================RAW BACKOUT======================\n"
			        script.each{i->
			        	detail += i + "\n"
			        }
			        detail += "\n===================RAW BACKOUT======================\n"
			
			        // --- STEP 1: Categorize blocks ---
					def objectGroups = [:]     // name -> lines (normal object groups)
					def parentObjects = [:]    // name -> lines (object-groups with group-objects)
					def acls = []              // list of ACL lines
					def nats = []              // list of NAT lines
					
					def current = []
					def currentName = null
					def isParent = false
					
					script.each { line ->
					    line = line.trim()
					
					    if (line.startsWith("object-group")) {
					        // Save previous block if exists
					        if (currentName) {
					            if (isParent) parentObjects[currentName] = current
					            else objectGroups[currentName] = current
					        }
					        // Start new block
					        current = [line]
					        currentName = line.split("\\s+")[2]
					        isParent = false
					
					    } else if (line.startsWith("group-object")) {
					        current << line
					        isParent = true
					
					    } else if (line.startsWith("network-object")) {
					        current << line
					
					    } else {
					        // End of object-group block
					        if (currentName) {
					            if (isParent) parentObjects[currentName] = current
					            else objectGroups[currentName] = current
					            current = []
					            currentName = null
					            isParent = false
					        }
					
					        if (line.startsWith("access-list")) acls << line
					        else if (line.startsWith("nat")) nats << line
					    }
					}
					// Handle last open block
					if (currentName) {
					    if (isParent) parentObjects[currentName] = current
					    else objectGroups[currentName] = current
					}
					
					// --- STEP 2: Detect relationships ---
					def parentRefMap = [:]          // child -> parent
					def aclMap = [:].withDefault { [] } 
					def natMap = [:].withDefault { [] } 
					
					parentObjects.each { pname, plines ->
					    plines.each { l ->
					        if (l.startsWith("group-object")) {
					            def child = l.split("\\s+")[1]
					            parentRefMap[child] = pname
					        }
					    }
					}
					
					acls.each { l ->
					    def matches = (l =~ /object-group\s+(\S+)/)
					    matches.each { m -> aclMap[m[1]] << l }
					}
					
					nats.each { l ->
					    def matches = (l =~ /static\s+(\S+)/)
					    matches.each { m -> natMap[m[1]] << l }
					}
					
					// --- STEP 3: Identify fully deleted object groups ---
					def deletedGroups = [] as Set
					objectGroups.keySet().each { name ->
					    if (parentRefMap[name] || aclMap[name] || natMap[name]) {
					        deletedGroups << name
					    }
					}
					
					// --- STEP 4: Build final ordered script ---
					def output = []
					
					// Step 1: Add all non-deleted object-groups first
					objectGroups.each { name, block ->
					    if (!deletedGroups.contains(name)) {
					        output += block
					    }
					}
					
					// Step 2: For deleted object-groups, maintain dependency sequence
					deletedGroups.each { name ->
					    if (objectGroups[name]) output += objectGroups[name]
					    if (parentRefMap[name] && parentObjects[parentRefMap[name]])
					        output += parentObjects[parentRefMap[name]]
					    if (aclMap[name]) output += aclMap[name]
					    if (natMap[name]) output += natMap[name]
					}
					
					// --- STEP 5: Print final script ---
					detail += "\n======== FINAL BACKOUT ========\n"
					detail += output.join("\n")
					detail += "\n======== FINAL BACKOUT ========\n"
					
					def preBackoutScript = output.join("\n")
			        // =========================================================DEVELOPMENT GONG ON=======================================
			
			
			        scriptStr += "##"+ displayHost + "##\n"
			        scriptStr += "\n##BackoutPlanStart##\n"
			        preBackoutScript.eachLine{it->
			            scriptStr += it + "\n"
			            scriptCheck += it + "\n"
			        }
			        scriptStr += "##BackoutPlanEnd##\n\n"
			    }



























"object-group network MCAFEE-AV-INTERNAL-SERVERS",
" network-object host 10.238.67.18",
" network-object host 168.162.99.130",
" network-object host 10.238.67.19",
" network-object host 168.162.99.132",
"object-group network FIS-FIREEYE-ENCASE-BIT9-MCAFEE",
" group-object MCAFEE-AV-INTERNAL-SERVERS",
"access-list Outside-IN extended permit object-group ENCASE-MCAFEE-PORTS object-group MCAFEE-AV-INTERNAL-SERVERS object-group AvantGard_Servers_PHL_Inside log ",
"access-list Inside-273_10.88.89.0/24-IN extended permit object HTTPS object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log ",
"access-list Inside-273_10.88.89.0/24-IN extended permit object TCP-8080 object VLAN273-10.88.89.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log ",
"access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object HTTPS object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log ",
"access-list AVGD-LABS-VLAN530-10.110.4.0/24-IN extended permit object TCP-8080 object AVGD-LABS-VLAN530-10.110.4.0-24 object-group MCAFEE-AV-INTERNAL-SERVERS log ",
"nat (Outside,any) source static MCAFEE-AV-INTERNAL-SERVERS MCAFEE-AV-INTERNAL-SERVERS destination static AvantGard_Servers_PHL_Inside AvantGard_Servers_PHL_Inside",
"object-group network FIREEYE-INTERNAL-SERVERS",
" network-object host 168.162.99.132"
						









// --- STEP 1: Categorize blocks ---
        def objectGroups = [:]      // name -> lines (normal object-groups)
        def parentObjects = [:]     // name -> lines (object-groups with group-objects)
        def networkObjects = [:]    // name -> lines (object network ...)
        def acls = []               // list of ACL lines
        def nats = []               // list of NAT lines

        def current = []
        def currentName = null
        def isParent = false
        def isNetworkObject = false

        script.each { line ->
            line = line.trim()

            if (line.startsWith("object network")) {
                // Save previous block if exists
                if (currentName) {
                    if (isParent) parentObjects[currentName] = current
                    else if (isNetworkObject) networkObjects[currentName] = current
                    else objectGroups[currentName] = current
                }
                // Start new network object
                current = [line]
                currentName = line.split("\\s+")[2]
                isNetworkObject = true
                isParent = false

            } else if (line.startsWith("object-group")) {
                // Save previous block if exists
                if (currentName) {
                    if (isParent) parentObjects[currentName] = current
                    else if (isNetworkObject) networkObjects[currentName] = current
                    else objectGroups[currentName] = current
                }
                // Start new object-group
                current = [line]
                currentName = line.split("\\s+")[2]
                isParent = false
                isNetworkObject = false

            } else if (line.startsWith("group-object")) {
                current << line
                isParent = true

            } else if (line.startsWith("network-object") || line.startsWith("host")) {
                current << line

            } else {
                // End of block (any type)
                if (currentName) {
                    if (isParent) parentObjects[currentName] = current
                    else if (isNetworkObject) networkObjects[currentName] = current
                    else objectGroups[currentName] = current
                    current = []
                    currentName = null
                    isParent = false
                    isNetworkObject = false
                }

                if (line.startsWith("access-list")) acls << line
                else if (line.startsWith("nat")) nats << line
            }
        }

        // Handle last open block
        if (currentName) {
            if (isParent) parentObjects[currentName] = current
            else if (isNetworkObject) networkObjects[currentName] = current
            else objectGroups[currentName] = current
        }

        // --- STEP 2: Detect relationships ---
        def parentRefMap = [:]          // child -> parent
        def aclMap = [:].withDefault { [] }
        def natMap = [:].withDefault { [] }

        parentObjects.each { pname, plines ->
            plines.each { l ->
                if (l.trim().startsWith("group-object")) {
                    def child = l.split("\\s+")[1]
                    parentRefMap[child] = pname
                }
            }
        }

        acls.each { l ->
            def matches = (l =~ /object-group\s+(\S+)/)
            matches.each { m -> aclMap[m[1]] << l }
        }

        nats.each { l ->
            def matches = (l =~ /static\s+(\S+)/)
            matches.each { m -> natMap[m[1]] << l }
        }

        // --- STEP 3: Identify fully deleted object groups ---
        def deletedGroups = [] as Set
        objectGroups.keySet().each { name ->
            if (parentRefMap[name] || aclMap[name] || natMap[name]) {
                deletedGroups << name
            }
        }

        // --- STEP 4: Build final ordered script ---
        def output = []

        // Step 1: Add all network objects first
        networkObjects.each { name, block ->
            output += block
        }

        // Step 2: Add all non-deleted object-groups
        objectGroups.each { name, block ->
            if (!deletedGroups.contains(name)) {
                output += block
            }
        }

        // Step 3: Add deleted object-groups + parents + ACL/NAT dependencies
        deletedGroups.each { name ->
            if (objectGroups[name]) output += objectGroups[name]
            if (parentRefMap[name] && parentObjects[parentRefMap[name]])
                output += parentObjects[parentRefMap[name]]
            if (aclMap[name]) output += aclMap[name]
            if (natMap[name]) output += natMap[name]
        }

        // Step 4: Add remaining ACLs not tied to deleted groups
        def usedAcls = output.findAll { it.startsWith("access-list") } as Set
        acls.findAll { !usedAcls.contains(it) }.each { output << it }

        // Step 5: Add NATs last (not already included)
        def usedNats = output.findAll { it.startsWith("nat") } as Set
        nats.findAll { !usedNats.contains(it) }.each { output << it }

        // --- STEP 5: Print final script ---
        def detail = ""
        detail += "\n======== FINAL BACKOUT ========\n"
        detail += output.join("\n")
        detail += "\n======== FINAL BACKOUT ========\n"

        def preBackoutScript = output.join("\n")
