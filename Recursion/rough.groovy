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























command:
> sh save | grep <Backend server Name>
> sh save | grep 10.237.250.35:443
add service 10.237.250.35:443 10.237.250.35 SSL_BRIDGE 443 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp ON -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP NO -state DISABLED -comment BDC8RFISOSIAP01 -devno 44892160 ## exclude “-devno 44892160” as backout plan
bind lb vserver bdoc-ose-wildcard-443-sslbridge-staging 10.237.250.35:443
## Comments : if multipe lines like this, don’t put in the install plan
bind service 10.237.250.35:443 -monitorName ocp-router -devno 52527104
> 
result:
server 10.237.250.35:443 is bind only VIP bdoc-ose-wildcard-443-sslbridge-staging
state - disabled 

command:
> sh save | grep 10.237.250.35:80
add service 10.237.250.35:80 10.237.250.35 HTTP 80 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp ON -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP YES -state DISABLED -comment BDC8RFISOSIAP01 -devno 44990464
bind lb vserver bdoc-ose-wildcard-80-staging 10.237.250.35:80
bind service 10.237.250.35:80 -monitorName ocp-router -devno 52428800
>
result:
server 10.237.250.35:80 is bind only VIP bdoc-ose-wildcard-80-staging
state - disabled 
