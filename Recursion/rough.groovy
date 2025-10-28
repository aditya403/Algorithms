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
 * ActionTask Name: PreCheckRouteAndPrefixList
 * Description:
 *   This task validates if the given VIP IP has:
 *     1. Proper Route configuration on the Load Balancer
 *     2. Proper Prefix-List configuration on both Primary and Secondary Load Balancers
 *
 * Input Parameters:
 *   - VIP_IP (e.g. 10.239.3.23)
 *   - PRIMARY_DEVICE (Hostname or IP of Primary LB)
 *   - SECONDARY_DEVICE (Hostname or IP of Secondary LB)
 *
 * Output:
 *   Returns status summary of Route and Prefix-List configurations
 */

import com.resolve.automation.utils.ConnectionUtils
import com.resolve.automation.logging.Logger

def logger = new Logger()
def resultMap = [
    routeConfigured: false,
    routeMisconfigured: false,
    prefixListConfigured: false,
    primarySeq: null,
    secondarySeq: null
]

// ======================== INPUT HANDLING ========================
def vipIp = inputs["VIP_IP"]
def primaryDevice = inputs["PRIMARY_DEVICE"]
def secondaryDevice = inputs["SECONDARY_DEVICE"]

if (!vipIp || !primaryDevice || !secondaryDevice) {
    throw new Exception("Missing required input(s): VIP_IP, PRIMARY_DEVICE, or SECONDARY_DEVICE")
}

logger.info("Starting PreCheck Route and Prefix validation for VIP: ${vipIp}")
logger.info("Primary Device: ${primaryDevice}")
logger.info("Secondary Device: ${secondaryDevice}")

// ======================== FUNCTION DEFINITIONS ========================

def runCommandOnDevice(device, command) {
    def session = ConnectionUtils.connect(device)
    def output = session.execute(command)
    session.disconnect()
    return output ?: ""
}

// ======================== ROUTE VALIDATION ========================

def routeCommand = "sh save | grep ${vipIp}"
def routeOutput = runCommandOnDevice(primaryDevice, routeCommand)
logger.info("Route check output for ${primaryDevice}:\n${routeOutput}")

if (routeOutput) {
    def lines = routeOutput.readLines()
    def validRoute = lines.find { line ->
        line.trim().startsWith("add") &&
        line.contains(vipIp) &&
        line.contains("-snmp DISABLED")
    }

    if (validRoute) {
        if (validRoute.contains("-hostRoute ENABLED")) {
            resultMap.routeConfigured = true
            logger.info("Route properly configured for ${vipIp} on ${primaryDevice}")
        } else {
            resultMap.routeMisconfigured = true
            logger.warn("Route misconfigured (missing -hostRoute ENABLED) on ${primaryDevice}")
        }
    } else {
        resultMap.routeMisconfigured = true
        logger.warn("Route misconfigured (missing -snmp DISABLED or doesn't start with add) on ${primaryDevice}")
    }
} else {
    logger.warn("No route found for ${vipIp} on ${primaryDevice}")
}

// ======================== PREFIX-LIST VALIDATION (PRIMARY) ========================

def prefixCmdPrimary = "vtysh -c 'sh run | in ${vipIp}'"
def prefixOutputPrimary = runCommandOnDevice(primaryDevice, prefixCmdPrimary)
logger.info("Prefix-list output (Primary):\n${prefixOutputPrimary}")

if (prefixOutputPrimary.contains("ip prefix-list")) {
    resultMap.prefixListConfigured = true
    def seqMatch = prefixOutputPrimary =~ /seq (\d+)/
    if (seqMatch.find()) resultMap.primarySeq = seqMatch[0][1]
} else {
    logger.warn("Prefix-list not configured for ${vipIp} on ${primaryDevice}")
}

// ======================== PREFIX-LIST VALIDATION (SECONDARY) ========================

def prefixCmdSecondary = "vtysh -c 'sh run | in ${vipIp}'"
def prefixOutputSecondary = runCommandOnDevice(secondaryDevice, prefixCmdSecondary)
logger.info("Prefix-list output (Secondary):\n${prefixOutputSecondary}")

def seqMatch2 = prefixOutputSecondary =~ /seq (\d+)/
if (seqMatch2.find()) resultMap.secondarySeq = seqMatch2[0][1]

// ======================== RESULT BUILDING ========================

def summary = []
if (resultMap.routeConfigured) {
    summary << "✅ Route configured correctly on ${primaryDevice}"
} else if (resultMap.routeMisconfigured) {
    summary << "⚠️ Route misconfigured on ${primaryDevice} (missing -snmp DISABLED or hostRoute ENABLED)"
} else {
    summary << "❌ Route not configured on ${primaryDevice}"
}

summary << (resultMap.prefixListConfigured ? "✅ Prefix-list configured on Primary" : "❌ Prefix-list not configured on Primary")

if (resultMap.primarySeq && resultMap.secondarySeq) {
    if (resultMap.primarySeq != resultMap.secondarySeq) {
        summary << "⚠️ Prefix-list sequence mismatch (Primary: ${resultMap.primarySeq}, Secondary: ${resultMap.secondarySeq})"
    } else {
        summary << "✅ Prefix-list sequence matched (${resultMap.primarySeq})"
    }
} else if (!resultMap.secondarySeq) {
    summary << "❌ Prefix-list not configured on Secondary"
}

// ======================== FINAL LOG & OUTPUT ========================

logger.info("PreCheck Summary:\n${summary.join('\n')}")
task.setOutput("PreCheck Summary", summary.join("\n"))

return summary.join("\n")
