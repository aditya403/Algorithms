=========VIP==============
10.236.135.150 82
=========DEVICES==========
cdl126-cha01ma-azus
cdl127-cha01ma-azus
cdl126-lrd03ma-arus
cdl127-lrd03ma-arus
=========VIP==============
156.55.138.96 80
156.55.138.96 443
=========DEVICES==========
cdl124-cha01ma-azus
cdl125-cha01ma-azus
cdl124-lrd03ma-arus
cdl125-lrd03ma-arus



RITM25007516580 CH25001981435 Phase1 Decom

Detailed ask of the decommission; anything associated with these VIPs and FQDN must decommissioned ASAP; please reach out to Angelo Tomaras or me or POWEB DL for any clarification.
AMEX profile 7 client application "webclient/storefront" is decommissioned and below is the cleanup needed.
1.	 External VIP 156.55.138.96 and any ports associated. Ex(80, 443) in LRK
2.	FQDN and VIP mapping or reference on FIS side - personalsavings.com.    A       156.55.138.96
3.	LRK RP – Rewrite rules and anything associated with personalsavings.com.    A       156.55.138.96
a.	Ex:-below.
b.	RewriteRule   "^/personalsavings/onlinebanking(.+)"  "https://personalsavings.americanexpress.com/onlinebanking$1"  [R,L]
4.	Airgap configs – for Akamai and External VIP and ports redirects to internal VIP port 10001 and 1003 
5. Internal Airgap VIP - 10.236.135.150 (Port 80: Storefront, Port 81: WebClient, Port 82:eDAOClient)

AMEX profile 7 client application "webclient/storefront" is decommissioned and below is the cleanup needed.
1.	 External VIP 156.55.139.228 and any ports associated. Ex(80, 443) in PDC
2.	FQDN and VIP mapping or reference on FIS side - personalsavings.com.    A       156.55.139.228
3.	PDC RP – Rewrite rules and anything associated with personalsavings.com.    A       156.55.139.228
a.	Ex:-below.
b.	RewriteRule   "^/personalsavings/onlinebanking(.+)"  "https://personalsavings.americanexpress.com/onlinebanking$1"  [R,L]
4.	Airgap configs – for Akamai and External VIP and ports redirects to internal VIP 10001 and 1003 
5. Internal Airgap VIP - 10.236.135.150 (Port 80: Storefront, Port 81: WebClient, Port 82:eDAOClient)
====================
Change Script
====================
cdl126-cha01ma-azus

disable lb vserver AMX-eDAOClient-AG-10.236.135.150-82
save ns config

#####################
cdl126-lrd03ma-arus

disable lb vserver AMX_eDAOClient_AG-10.236.135.150-82
disable lb vserver sorry_AMX_Storefront-AG-10.236.135.150-80 
disable lb vserver sorry_AMX_WebClient-AG-10.236.135.150-81 
disable lb vserver sorry_AMX_eDAOClient_AG-10.236.135.150-82 

save ns config

####################
cdl125-cha01ma-azus

disable lb vserver PSG-Amex-RP-156.55.138.96-443
disable lb vserver PSG-Amex-RP-156.55.138.96-80 
save ns config

####################
cdl124-lrd03ma-arus

disable lb vserver PSG-Amex-RP-156.55.139.228-443 
disable lb vserver PSG-Amex-RP-156.55.139.228-80  
save ns config

====================
Post Validation
====================
cdl126-cha01ma-azus

sho lb vserver AMX-eDAOClient-AG-10.236.135.150-82

#####################
cdl126-lrd03ma-arus

sho lb vserver AMX_eDAOClient_AG-10.236.135.150-82
sho lb vserver sorry_AMX_Storefront-AG-10.236.135.150-80 
sho lb vserver sorry_AMX_WebClient-AG-10.236.135.150-81 
sho lb vserver sorry_AMX_eDAOClient_AG-10.236.135.150-82 

####################
cdl125-cha01ma-azus

sho lb vserver PSG-Amex-RP-156.55.138.96-443
sho lb vserver PSG-Amex-RP-156.55.138.96-80 

####################
cdl124-lrd03ma-arus

sho lb vserver PSG-Amex-RP-156.55.139.228-443 
sho lb vserver PSG-Amex-RP-156.55.139.228-80  

====================
Backout
====================
cdl126-cha01ma-azus

enable lb vserver AMX-eDAOClient-AG-10.236.135.150-82
save ns config

#####################
cdl126-lrd03ma-arus

enable lb vserver AMX_eDAOClient_AG-10.236.135.150-82
enable lb vserver sorry_AMX_Storefront-AG-10.236.135.150-80 
enable lb vserver sorry_AMX_WebClient-AG-10.236.135.150-81 
enable lb vserver sorry_AMX_eDAOClient_AG-10.236.135.150-82 

save ns config

####################
cdl124-lrd03ma-arus

enable lb vserver PSG-Amex-RP-156.55.138.96-443
enable lb vserver PSG-Amex-RP-156.55.138.96-80 
save ns config

####################
cdl124-lrd03ma-arus

enable lb vserver PSG-Amex-RP-156.55.139.228-443 
enable lb vserver PSG-Amex-RP-156.55.139.228-80  
save ns config


====================
Pre Validation
====================
Primary|nsroot@cdl126-cha01ma-azus-2025/10/15-07:25> sho save | grep -i 10.236.135.150
add ns ip 10.236.135.150 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver AMX-eDAOClient-AG-10.236.135.150-82 HTTP 10.236.135.150 82 -persistenceType COOKIEINSERT -timeout 20 -cltTimeout 180 -comment C180025028 -RHIstate ACTIVE -devno 222822400
bind lb vserver AMX-eDAOClient-AG-10.236.135.150-82 10.237.86.103-443
bind lb vserver AMX-eDAOClient-AG-10.236.135.150-82 10.237.86.104-443

Primary|nsroot@cdl126-cha01ma-azus-2025/10/15-07:26> sho save | grep -i 10.237.86.103-443
add service 10.237.86.103-443 10.237.86.103 SSL 443 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP NO -comment C180025028 -devno 125173760
bind lb vserver AMX-eDAOClient-AG-10.236.135.150-82 10.237.86.103-443
bind service 10.237.86.103-443 -monitorName https-IP-AMX-60000-APP -devno 525271040
set ssl service 10.237.86.103-443 -ssl3 DISABLED -dtls1 DISABLED
bind ssl service 10.237.86.103-443 -eccCurveName P_256
bind ssl service 10.237.86.103-443 -eccCurveName P_384
bind ssl service 10.237.86.103-443 -eccCurveName P_224
bind ssl service 10.237.86.103-443 -eccCurveName P_521

Primary|nsroot@cdl126-cha01ma-azus-2025/10/15-07:26> sho lb vserver AMX-eDAOClient-AG-10.236.135.150-82
        AMX-eDAOClient-AG-10.236.135.150-82 (10.236.135.150:82) - HTTP  Type: ADDRESS 
        State: DOWN
        Last state change was at Fri Aug 29 09:01:41 2025
        Time since last state change: 47 days, 10:26:22.230
        Effective State: DOWN
		
cdl126-cha01ma-azus#sho run | grep  10.236.135.150
ip prefix-list AMX-EID28920 seq 10 permit 10.236.135.150/32

Secondary|nsroot@cdl127-cha01ma-azus-19:28> vtysh
cdl127-cha01ma-azus#sho run | grep  10.236.135.150
ip prefix-list AMX-EID28920 seq 10 permit 10.236.135.150/32

##
Primary|nsroot@cdl126-lrd03ma-arus-2025/10/15-07:30> sho save | grep -i 10.236.135.150
add ns ip 10.236.135.150 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver sorry_AMX_Storefront-AG-10.236.135.150-80 HTTP 0.0.0.0 0 -persistenceType NONE -cltTimeout 180 -comment 1802606725 -RHIstate ACTIVE -devno 183697408
add lb vserver sorry_AMX_WebClient-AG-10.236.135.150-81 HTTP 0.0.0.0 0 -persistenceType NONE -cltTimeout 180 -comment 1802606725 -RHIstate ACTIVE -devno 183762944
add lb vserver sorry_AMX_eDAOClient_AG-10.236.135.150-82 HTTP 0.0.0.0 0 -persistenceType NONE -cltTimeout 180 -comment 1802606725 -RHIstate ACTIVE -devno 183861248
add lb vserver AMX_eDAOClient_AG-10.236.135.150-82 HTTP 10.236.135.150 82 -persistenceType COOKIEINSERT -timeout 20 -cltTimeout 180 -comment 1802606725 -RHIstate ACTIVE -devno 183894016
bind lb vserver sorry_AMX_Storefront-AG-10.236.135.150-80 10.237.86.47-50000
bind lb vserver sorry_AMX_WebClient-AG-10.236.135.150-81 10.237.86.47-50001
bind lb vserver sorry_AMX_eDAOClient_AG-10.236.135.150-82 10.237.86.47-50002
bind lb vserver AMX_eDAOClient_AG-10.236.135.150-82 10.237.84.131-443
bind lb vserver AMX_eDAOClient_AG-10.236.135.150-82 10.237.84.132-443

Primary|nsroot@cdl126-lrd03ma-arus-2025/10/15-07:30> sho save | grep -i 10.237.86.47-50000
add service 10.237.86.47-50000 10.237.86.47 SSL 50000 -gslb NONE -maxClient 0 -maxReq 0 -cip ENABLED X-Forwarded-For -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP YES -comment 1802606725 -devno 124813312
bind lb vserver sorry_AMX_Storefront-AG-10.236.135.150-80 10.237.86.47-50000
set ssl service 10.237.86.47-50000 -ssl3 DISABLED -dtls1 DISABLED
bind ssl service 10.237.86.47-50000 -eccCurveName P_256
bind ssl service 10.237.86.47-50000 -eccCurveName P_384
bind ssl service 10.237.86.47-50000 -eccCurveName P_224
bind ssl service 10.237.86.47-50000 -eccCurveName P_521

Primary|nsroot@cdl126-lrd03ma-arus-2025/10/15-07:31> sho save | grep -i 10.237.86.47-50001
add service 10.237.86.47-50001 10.237.86.47 SSL 50001 -gslb NONE -maxClient 0 -maxReq 0 -cip ENABLED X-Forwarded-For -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP YES -comment 1802606725 -devno 124911616
bind lb vserver sorry_AMX_WebClient-AG-10.236.135.150-81 10.237.86.47-50001
set ssl service 10.237.86.47-50001 -ssl3 DISABLED -dtls1 DISABLED
bind ssl service 10.237.86.47-50001 -eccCurveName P_256
bind ssl service 10.237.86.47-50001 -eccCurveName P_384
bind ssl service 10.237.86.47-50001 -eccCurveName P_224
bind ssl service 10.237.86.47-50001 -eccCurveName P_521

Primary|nsroot@cdl126-lrd03ma-arus-2025/10/15-07:31> sho save | grep -i 10.237.86.47-50002
add service 10.237.86.47-50002 10.237.86.47 SSL 50002 -gslb NONE -maxClient 0 -maxReq 0 -cip ENABLED X-Forwarded-For -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP YES -comment 1802606725 -devno 125009920
bind lb vserver sorry_AMX_eDAOClient_AG-10.236.135.150-82 10.237.86.47-50002
set ssl service 10.237.86.47-50002 -ssl3 DISABLED -dtls1 DISABLED
bind ssl service 10.237.86.47-50002 -eccCurveName P_256
bind ssl service 10.237.86.47-50002 -eccCurveName P_384
bind ssl service 10.237.86.47-50002 -eccCurveName P_224
bind ssl service 10.237.86.47-50002 -eccCurveName P_521

Primary|nsroot@cdl126-lrd03ma-arus-2025/10/15-07:31> sho save | grep -i 10.237.84.131-443
add service 10.237.84.131-443 10.237.84.131 SSL 443 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP NO -comment 1802606725 -devno 124944384
bind lb vserver AMX_eDAOClient_AG-10.236.135.150-82 10.237.84.131-443
bind service 10.237.84.131-443 -monitorName AMX_eDAOClient_AG -devno 345899008
set ssl service 10.237.84.131-443 -ssl3 DISABLED -dtls1 DISABLED
bind ssl service 10.237.84.131-443 -eccCurveName P_256
bind ssl service 10.237.84.131-443 -eccCurveName P_384
bind ssl service 10.237.84.131-443 -eccCurveName P_224
bind ssl service 10.237.84.131-443 -eccCurveName P_521

Primary|nsroot@cdl126-lrd03ma-arus-2025/10/15-07:31> sho save | grep -i 10.237.84.132-443
add service 10.237.84.132-443 10.237.84.132 SSL 443 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp OFF -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP NO -comment 1802606725 -devno 124977152
bind lb vserver AMX_eDAOClient_AG-10.236.135.150-82 10.237.84.132-443
bind service 10.237.84.132-443 -monitorName AMX_eDAOClient_AG -devno 345866240
set ssl service 10.237.84.132-443 -ssl3 DISABLED -dtls1 DISABLED
bind ssl service 10.237.84.132-443 -eccCurveName P_256
bind ssl service 10.237.84.132-443 -eccCurveName P_384
bind ssl service 10.237.84.132-443 -eccCurveName P_224
bind ssl service 10.237.84.132-443 -eccCurveName P_521

Primary|nsroot@cdl126-lrd03ma-arus-2025/10/15-07:31> sho lb vserver AMX_eDAOClient_AG-10.236.135.150-82
        AMX_eDAOClient_AG-10.236.135.150-82 (10.236.135.150:82) - HTTP  Type: ADDRESS 
        State: DOWN
        Last state change was at Thu Aug 28 10:18:06 2025
        Time since last state change: 48 days, 09:14:31.880
        Effective State: DOWN
		
Primary|nsroot@cdl126-lrd03ma-arus-2025/10/15-07:33> vtysh
cdl126-lrd03ma-arus#sho run | grep  10.236.135.150
ip prefix-list AMX-EID28920 seq 10 permit 10.236.135.150/32

Secondary|nsroot@cdl127-lrd03ma-arus-19:35> vtysh
cdl127-lrd03ma-arus#sho run | grep  10.236.135.150
ip prefix-list AMX-EID28920 seq 10 permit 10.236.135.150/32		

####
Secondary@cdl124-lrd03ma-arus-2025/10/15-07:37> sho save | grep -i 156.55.138.96
add ns ip 156.55.138.96 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver PSG-Amex-RP-156.55.138.96-443 SSL 156.55.138.96 443 -persistenceType SOURCEIP -timeout 30 -cltTimeout 1800 -comment 1802606725 -devno 58458112
add lb vserver PSG-Amex-RP-156.55.138.96-80 HTTP 156.55.138.96 80 -persistenceType SOURCEIP -timeout 30 -cltTimeout 1800 -comment 1802606725 -devno 58490880
bind lb vserver PSG-Amex-RP-156.55.138.96-443 10.236.132.27-10001
bind lb vserver PSG-Amex-RP-156.55.138.96-443 10.236.132.28-10001
bind lb vserver PSG-Amex-RP-156.55.138.96-80 10.236.132.27-80
bind lb vserver PSG-Amex-RP-156.55.138.96-80 10.236.132.28-80
set ssl vserver PSG-Amex-RP-156.55.138.96-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED
bind ssl vserver PSG-Amex-RP-156.55.138.96-443 -cipherName FIS-2017
bind ssl vserver PSG-Amex-RP-156.55.138.96-443 -cipherName DEFAULT
bind ssl vserver PSG-Amex-RP-156.55.138.96-443 -certkeyName personalsavings.americanexpress
bind ssl vserver PSG-Amex-RP-156.55.138.96-443 -eccCurveName P_256
bind ssl vserver PSG-Amex-RP-156.55.138.96-443 -eccCurveName P_384
bind ssl vserver PSG-Amex-RP-156.55.138.96-443 -eccCurveName P_224
bind ssl vserver PSG-Amex-RP-156.55.138.96-443 -eccCurveName P_521

##
Primary@cdl124-cha01ma-azus-2025/10/15-07:38> sho save | grep -i 156.55.139.228
add ns ip 156.55.139.228 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver PSG-Amex-RP-156.55.139.228-443 SSL 156.55.139.228 443 -persistenceType SOURCEIP -timeout 30 -cltTimeout 1800 -comment C170111999 -devno 53411840
add lb vserver PSG-Amex-RP-156.55.139.228-80 HTTP 156.55.139.228 80 -persistenceType SOURCEIP -timeout 30 -cltTimeout 1800 -comment C170111999 -devno 53444608
bind lb vserver PSG-Amex-RP-156.55.139.228-443 10.236.137.22-10001
bind lb vserver PSG-Amex-RP-156.55.139.228-443 10.236.137.23-10001
bind lb vserver PSG-Amex-RP-156.55.139.228-80 10.236.137.22-80
bind lb vserver PSG-Amex-RP-156.55.139.228-80 10.236.137.23-80
set ssl vserver PSG-Amex-RP-156.55.139.228-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED
bind ssl vserver PSG-Amex-RP-156.55.139.228-443 -cipherName FIS-2017
bind ssl vserver PSG-Amex-RP-156.55.139.228-443 -certkeyName personalsavings.amx
bind ssl vserver PSG-Amex-RP-156.55.139.228-443 -eccCurveName P_256
bind ssl vserver PSG-Amex-RP-156.55.139.228-443 -eccCurveName P_384
bind ssl vserver PSG-Amex-RP-156.55.139.228-443 -eccCurveName P_224
bind ssl vserver PSG-Amex-RP-156.55.139.228-443 -eccCurveName P_521

















// Regex matchers for flexible section headers
def vipSep = { s -> s ==~ /(?i)^[=\-\s]*VIP[=\-\s]*$/ }
def devSep = { s -> s ==~ /(?i)^[=\-\s]*DEVICES[=\-\s]*$/ }

def lines = input.readLines().collect { it.trim() }.findAll { it }
def result = []
def currentVips = []
def currentDevices = []
def mode = null

for (int i = 0; i < lines.size(); i++) {
    String line = lines[i]

    if (vipSep(line)) {
        if (currentVips && currentDevices) {
            currentVips.each { v -> 
                v.DEVICES = currentDevices.clone()
                result << v
            }
            currentVips = []
            currentDevices = []
        }
        mode = "VIP"
        continue
    }

    if (devSep(line)) {
        mode = "DEVICES"
        continue
    }

    if (mode == "VIP") {
        def parts = line.split(/\s+/)
        if (parts.size() >= 2) {
            currentVips << [VIP: parts[0], PORT: parts[1]]
        } else if (parts.size() == 1) {
            currentVips << [VIP: parts[0], PORT: null]
        }
        continue
    }

    if (mode == "DEVICES") {
        currentDevices << line
        boolean nextIsSep = (i + 1 < lines.size()) && (vipSep(lines[i + 1]) || devSep(lines[i + 1]))
        boolean atEnd = (i == lines.size() - 1)
        if (nextIsSep || atEnd) {
            currentVips.each { v -> 
                v.DEVICES = currentDevices.clone()
                result << v
            }
            currentVips = []
            currentDevices = []
            mode = null
        }
        continue
    }
}

// Final flush if needed
if (currentVips && currentDevices) {
    currentVips.each { v ->
        v.DEVICES = currentDevices.clone()
        result << v
    }
}

// Add SYSID and STATUS
result.eachWithIndex { map, idx ->
    map.SYSID = idx + 1
    map.STATUS = "UNPROCESSED"
}

prettyPrintList(result)



def prettyPrintList(listOfMaps) {
    println "["
    listOfMaps.eachWithIndex { item, listIndex ->
        println "    ["
        def entryList = item.entrySet() as List
        entryList.eachWithIndex { entry, entryIndex ->
            def (key, value) = [entry.key, entry.value]
            def isLastEntry = entryIndex == entryList.size() - 1

            if (value instanceof List) {
                println "        ${key}:["
                value.eachWithIndex { v, i ->
                    println "            ${v}${i < value.size() - 1 ? ',' : ''}"
                }
                println "        ]${!isLastEntry ? ',' : ''}"
            } else {
                print "        ${key}:${value}"
                println "${!isLastEntry ? ',' : ''}"
            }
        }
        println "    ]${listIndex < listOfMaps.size() - 1 ? ',' : ''}"
    }
    println "]"
}
