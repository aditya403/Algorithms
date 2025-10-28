Route
command:
> sh save | grep 10.239.3.23
add ns ip 10.239.3.23 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED
result:
route configured 
prefix-list:
command:
> vtysh
cdl155-bda01ma-wius#sh run | in 10.239.3.23
ip prefix-list PRIMARY seq 5 permit 10.239.3.23/32
result:
prefix-list configured 
Note: please check pre check config we already find under IP 10.239.3.23 , 2 VIP are configured which are requested to decom in the RITM
Also run the command on the Secondary LB to validate the sequence number, it may not match the Primary LB.





/**
 * ActionTask Name: PreCheckBackendServer
 * Purpose: Validate whether the given backend server is active or not,
 *          and show which VIP(s) it is bound to along with its state.
 *
 * Inputs:
 *   BackendServer (String) - IP:PORT of the backend (e.g. "10.237.250.35:443")
 *
 * Outputs:
 *   preCheckResult (String) - A summary of backend bindings and state
 */

import com.resolve.sysapi.*
import com.resolve.sysapi.workflow.*

def logger = execution.getLogger()

// ✅ 1. Read Input
def backendIpPort = input.get("BackendServer")
if (!backendIpPort) {
    output.set("preCheckResult", "ERROR: BackendServer input not provided.")
    return
}

logger.info("Starting PreCheck for backend server: ${backendIpPort}")

// ✅ 2. Run Command
def command = "sh save | grep ${backendIpPort}"
logger.info("Executing command: ${command}")

def cmdResult
try {
    cmdResult = commandHelper.executeCommand(command)
} catch (Exception e) {
    logger.error("Command execution failed: ${e.message}")
    output.set("preCheckResult", "Command execution failed: ${e.message}")
    return
}

if (!cmdResult) {
    logger.warn("No output received from command for ${backendIpPort}")
    output.set("preCheckResult", "No command output received for ${backendIpPort}")
    return
}

// ✅ 3. Parse Command Result
def cmdResultList = new StringReader(cmdResult).readLines()
def state = "UNKNOWN"
def vipList = []

cmdResultList.each { line ->
    line = line.trim()
    
    // Extract service state
    if (line.startsWith("add service") && line.contains(backendIpPort)) {
        def matcher = line =~ /-state\s+(\S+)/
        if (matcher.find()) {
            state = matcher.group(1)
            logger.info("Found backend state: ${state}")
        }
    }
    
    // Extract VIP bindings
    else if (line.startsWith("bind lb vserver") && line.contains(backendIpPort)) {
        def matcher = line =~ /bind lb vserver\s+(\S+)/
        if (matcher.find()) {
            def vipName = matcher.group(1)
            vipList << vipName
            logger.info("Found VIP binding: ${vipName}")
        }
    }
}

// ✅ 4. Compose Result Summary
def resultMessage

if (vipList.size() > 1) {
    resultMessage = "server ${backendIpPort} is bound to multiple VIPs: ${vipList.join(', ')}\nstate - ${state.toLowerCase()}"
} else if (vipList.size() == 1) {
    resultMessage = "server ${backendIpPort} is bind only VIP ${vipList[0]}\nstate - ${state.toLowerCase()}"
} else {
    // (Per your rule #2, this should never happen)
    resultMessage = "server ${backendIpPort} not bound to any VIP\nstate - ${state.toLowerCase()}"
}

logger.info("PreCheck Result:\n${resultMessage}")

// ✅ 5. Set Output
output.set("preCheckResult", resultMessage)

