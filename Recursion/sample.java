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



















def script = 
[
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

]
def objectGroups = [:]
def parentObjects = [:]
def acls = []
def nats = []

def current = []
def currentName = null
def isParent = false
script.each{line->
	line = line.trim()

	if(line.startsWith("object-group")){
		if(currentName){
			if(isParent)
				parentObjects[currentName] = current
			else
				objectGroups[currentName] = current
		}
		current = [line]
		currentName = line.split("\\s+")[2] // Get the group name
		isParent = false
	}else if(line.startsWith("group-object")){
		current << line
		isParent = true
	}else if(line.startsWith("access-list")){
		current << line
	}else{
		if(current){
			if(isParent)
				parentObjects[currentName] = current
			else
				objectGroups[currentName] = current
			current = []
			currentName = null
		}
		if(line.startsWith("access-list")) acls << line
		else if(line.startsWith("nat")) nats << line
	}
}
// handle last block
if(currentName){
if(isParent)
	parentObjects[currentName] = current
else
	objectGroups[currentName] = current
}

// --- Now detect dependencies ---
def deletedGroups = [] as Set
def parentRefMap = [:] //child -> Parent
def aclMap = [:].withDefault{[]}
def natMap = [:].withDefault{[]}

parentObjects.each{pname, plines ->
plines.each{l->
	if(l.startsWith("group-object")){
		def child = l.split("\\s+")[1]
		parentRefMap[child] = pname
	}
}
}
acls.each{l->
def matches = (l =~ /object-group\s+(\S+)/)
matches.each{m->
	aclMap[m[1]] << l
}
}

nats.each{l->
def matches = (l =~ /static\s+(\S+)/)
matches.each{m->
	natMap[m[1]] << l
}
}

// Find fully deleted groups
objectGroups.keySet().each{name->
if(parentRefMap[name] || aclMap[name] || natMap[name]){
	deletedGroups << name
}
}

print("\n======== PRE FINAL BACKOUT ========\n")
print(objectGroups)
print("\n======== PRE FINAL BACKOUT ========\n")

// --- Build Output ---
def output = []

objectGroups.each{name, block ->
if(!deletedGroups.contains(name)){
	output += block
}
}


objectGroups.each{name->
if(objectGroups[name]) output += objectGroups[name]
if(parentRefMap[name] && parentObjects[parentRefMap[name]])
	output += parentObjects[parentRefMap[name]]
if(aclMap[name]) output += aclMap[name]
if(natMap[name]) output += natMap[name]
}

def preBackoutScript = output.join("\n")

print("\n======== FINAL BACKOUT ========\n")
print(preBackoutScript)
print("\n======== FINAL BACKOUT ========\n")

