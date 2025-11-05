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

















no object-group network PHL-ORACLE-BR-SERVERS
no access-list Global_ACL extended permit object-group ORACLE-BR-PORTS object-group PHL-ORACLE-BR-SERVERS object-group PHL-CRL-SUBNETS log 
no access-list Global_ACL extended permit object-group ORACLE-BR-PORTS object-group PHL-CRL-SUBNETS object-group PHL-ORACLE-BR-SERVERS log 
object-group network ESXi_DESTINATION_PHL_CRL
no network-object host 10.91.242.3
no object-group network NETBACKUP-SRC
no access-list Backup-VLAN-1055-10.91.242.0/23-IN extended permit icmp object-group NETBACKUP-SRC object 168.162.206.89-32 log 
object-group network ESXi_DESTINATION_PHL_CRL
no network-object host 10.91.242.4
object-group network ESXi_DESTINATION_PHL_CRL
no network-object host 10.91.242.5


no access-list Global_ACL extended permit object-group ORACLE-BR-PORTS object-group PHL-ORACLE-BR-SERVERS object-group PHL-CRL-SUBNETS log 
no access-list Global_ACL extended permit object-group ORACLE-BR-PORTS object-group PHL-CRL-SUBNETS object-group PHL-ORACLE-BR-SERVERS log 
no object-group network PHL-ORACLE-BR-SERVERS
object-group network ESXi_DESTINATION_PHL_CRL
 no network-object host 10.91.242.3
no access-list Backup-VLAN-1055-10.91.242.0/23-IN extended permit icmp object-group NETBACKUP-SRC object 168.162.206.89-32 log 
no object-group network NETBACKUP-SRC
object-group network ESXi_DESTINATION_PHL_CRL
 no network-object host 10.91.242.4
object-group network ESXi_DESTINATION_PHL_CRL
 no network-object host 10.91.242.5



def reorderFirewallScript(List<String> scriptLines) {
    // Buckets in correct install plan order
    def aclLines = []
    def natLines = []
    def parentObjectGroupLines = []
    def objectGroupLines = []
    def parentObjectLines = []
    def objectLines = []

    def currentObjectGroupBlock = []

    scriptLines.each { rawLine ->
        def line = rawLine?.trim()
        if (!line) return // skip blanks or nulls

        switch (true) {
            case line.startsWith("no access-list"):
                aclLines << line
                break

            case line ==~ /.*\bnat\b.*/:
                natLines << line
                break

            case line.startsWith("no object-group network"):
                // Commit any open object-group block first
                if (currentObjectGroupBlock) {
                    objectGroupLines.addAll(currentObjectGroupBlock)
                    currentObjectGroupBlock.clear()
                }
                parentObjectGroupLines << line
                break

            case line.startsWith("object-group network"):
                // Commit previous object-group block before starting a new one
                if (currentObjectGroupBlock) {
                    objectGroupLines.addAll(currentObjectGroupBlock)
                    currentObjectGroupBlock.clear()
                }
                currentObjectGroupBlock << line
                break

            case line.startsWith("no object network"):
                // Commit any open object-group block before moving on
                if (currentObjectGroupBlock) {
                    objectGroupLines.addAll(currentObjectGroupBlock)
                    currentObjectGroupBlock.clear()
                }
                parentObjectLines << line
                break

            case line.startsWith("object network"):
                if (currentObjectGroupBlock) {
                    objectGroupLines.addAll(currentObjectGroupBlock)
                    currentObjectGroupBlock.clear()
                }
                objectLines << line
                break

            case line.startsWith("no network-object") || line.startsWith("network-object"):
                // part of an object-group block
                currentObjectGroupBlock << "  " + line
                break

            default:
                // catch any stragglers or indented continuation
                if (currentObjectGroupBlock) {
                    currentObjectGroupBlock << line
                }
                break
        }
    }

    // Commit any remaining open object-group block
    if (currentObjectGroupBlock) {
        objectGroupLines.addAll(currentObjectGroupBlock)
    }

    // Build final install plan in required order
    def finalScript = []
    finalScript.addAll(aclLines)
    finalScript.addAll(natLines)
    finalScript.addAll(parentObjectGroupLines)
    finalScript.addAll(objectGroupLines)
    finalScript.addAll(parentObjectLines)
    finalScript.addAll(objectLines)

    // Return as list
    return finalScript.findAll { it?.trim() }
}










object-group network grp_cortex_test_2
no network-object host 10.166.86.153
object-group network RC-Project-Group
no network-object host 10.166.86.153
object-group network DEV-LPAR-DPP
no network-object host 10.166.86.153
object-group network LPAR-DPP-DEVELOPMENT
no network-object host 10.166.86.153
object-group network CORTEX_SERVERS
no network-object host 10.166.86.153
no object network p750-Curtiz
no object network ukbitestmvrs11
object-group network group-p750-servers
no network-object object p750-Curtiz
object-group network grp_unix_servers_2
no network-object object p750-Curtiz
object-group network UK-DEV-SERVERS
no network-object object p750-Curtiz
no access-list global_access extended permit object-group DM_INLINE_SERVICE_9 object p750-Curtiz object H_10.135.128.47
no access-list global_access extended permit ip object-group group_local_vmhost object p750-Curtiz log
no access-list global_access extended permit ip object p750-Curtiz object-group group_local_vmhost log
no access-list global_access extended permit tcp object-group CTX_Complaince object p750-Curtiz object-group port-ctx-comp
no access-list global_access extended permit tcp object p750-Curtiz object emeasvn.fnfis.com eq https
no access-list global_access extended permit ip object-group DM_INLINE_NETWORK_80 object p750-Curtiz
no access-list global_access extended permit tcp object p750-Curtiz object copara
no access-list global_access extended permit tcp object copara object p750-Curtiz
no access-list global_access extended permit tcp 10.58.250.0 255.255.255.0 object p750-Curtiz eq ssh
no access-list global_access extended permit tcp object FIS_VDI_Subnet object p750-Curtiz eq 33501
no access-list global_access extended permit tcp object FIS_VDI_Subnet object p750-Curtiz eq 11799
object-group network group_new1102
no network-object object ukbitestmvrs11
no access-list global_access extended permit tcp host 10.74.145.70 host 10.166.86.153 eq 10596
no access-list global_access extended permit tcp object-group GROUP-PRODUCT-TESTING-UAT-2 host 10.166.86.153 object-group PORT-PRODUCT-TESTING
no access-list global_access extended permit tcp host 192.168.2.81 host 10.166.86.153 object-group PORT-PRODUCT-TESTING
no access-list global_access extended permit tcp object-group GROUP-PRODUCT-TESTING-UAT host 10.166.86.153 object-group PORT-PRODUCT-TESTING
no access-list global_access extended permit tcp 10.58.250.0 255.255.255.0 host 10.166.86.153 eq 33598
no access-list global_access extended permit tcp 10.58.250.0 255.255.255.0 host 10.166.86.153 object-group port_v32

















access-list global_access extended permit tcp object FIS_VDI_Subnet object p750-Curtiz eq 11799
access-list global_access extended permit tcp object FIS_VDI_Subnet object p750-Curtiz eq 33501
access-list global_access extended permit tcp 10.58.250.0 255.255.255.0 object p750-Curtiz eq ssh
access-list global_access extended permit tcp object copara object p750-Curtiz
access-list global_access extended permit tcp object p750-Curtiz object copara
access-list global_access extended permit ip object-group DM_INLINE_NETWORK_80 object p750-Curtiz
access-list global_access extended permit tcp object p750-Curtiz object emeasvn.fnfis.com eq https
access-list global_access extended permit tcp object-group CTX_Complaince object p750-Curtiz object-group port-ctx-comp
access-list global_access extended permit ip object p750-Curtiz object-group group_local_vmhost log
access-list global_access extended permit ip object-group group_local_vmhost object p750-Curtiz log
access-list global_access extended permit object-group DM_INLINE_SERVICE_9 object p750-Curtiz object H_10.135.128.47
access-list global_access extended permit tcp host 10.74.145.70 host 10.166.86.153 eq 10596
access-list global_access extended permit tcp object-group GROUP-PRODUCT-TESTING-UAT-2 host 10.166.86.153 object-group PORT-PRODUCT-TESTING
access-list global_access extended permit tcp host 192.168.2.81 host 10.166.86.153 object-group PORT-PRODUCT-TESTING
access-list global_access extended permit tcp object-group GROUP-PRODUCT-TESTING-UAT host 10.166.86.153 object-group PORT-PRODUCT-TESTING
access-list global_access extended permit tcp 10.58.250.0 255.255.255.0 host 10.166.86.153 eq 33598
access-list global_access extended permit tcp 10.58.250.0 255.255.255.0 host 10.166.86.153 object-group port_v32

no access-list global_access extended permit object-group DM_INLINE_SERVICE_9 object p750-Curtiz object H_10.135.128.47
no access-list global_access extended permit ip object-group group_local_vmhost object p750-Curtiz log
no access-list global_access extended permit ip object p750-Curtiz object-group group_local_vmhost log
no access-list global_access extended permit tcp object-group CTX_Complaince object p750-Curtiz object-group port-ctx-comp
no access-list global_access extended permit tcp object p750-Curtiz object emeasvn.fnfis.com eq https
no access-list global_access extended permit ip object-group DM_INLINE_NETWORK_80 object p750-Curtiz
no access-list global_access extended permit tcp object p750-Curtiz object copara
no access-list global_access extended permit tcp object copara object p750-Curtiz
no access-list global_access extended permit tcp 10.58.250.0 255.255.255.0 object p750-Curtiz eq ssh
no access-list global_access extended permit tcp object FIS_VDI_Subnet object p750-Curtiz eq 33501
no access-list global_access extended permit tcp object FIS_VDI_Subnet object p750-Curtiz eq 11799
 
no access-list global_access extended permit tcp host 10.74.145.70 host 10.166.86.153 eq 10596
no access-list global_access extended permit tcp object-group GROUP-PRODUCT-TESTING-UAT-2 host 10.166.86.153 object-group PORT-PRODUCT-TESTING
no access-list global_access extended permit tcp host 192.168.2.81 host 10.166.86.153 object-group PORT-PRODUCT-TESTING
no access-list global_access extended permit tcp object-group GROUP-PRODUCT-TESTING-UAT host 10.166.86.153 object-group PORT-PRODUCT-TESTING
no access-list global_access extended permit tcp 10.58.250.0 255.255.255.0 host 10.166.86.153 eq 33598
no access-list global_access extended permit tcp 10.58.250.0 255.255.255.0 host 10.166.86.153 object-group port_v32
 
object-group network grp_cortex_test_2
no network-object host 10.166.86.153
object-group network RC-Project-Group
no network-object host 10.166.86.153
object-group network DEV-LPAR-DPP
no network-object host 10.166.86.153
object-group network LPAR-DPP-DEVELOPMENT
no network-object host 10.166.86.153
object-group network CORTEX_SERVERS
no network-object host 10.166.86.153
 
object-group network group-p750-servers
no network-object object p750-Curtiz
object-group network grp_unix_servers_2
no network-object object p750-Curtiz
object-group network UK-DEV-SERVERS
no network-object object p750-Curtiz
object-group network group_new1102
no network-object object ukbitestmvrs11
 
no object network p750-Curtiz
no object network ukbitestmvrs11




def shouldProcessLine(line, ip) {
    // Find all "host <something>" patterns in the line
    def matcher = (line =~ /host\s+(\S+)/)
    
    // If no "host" is present, return true (means IP not mentioned at all)
    if (!matcher) {
        return true
    }

    // Extract all host IPs from the line
    def hostIPs = matcher.collect { it[1] }

    // If the given IP is present among the host IPs, return true
    if (hostIPs.contains(ip)) {
        return true
    }

    // If there are host IPs but not matching the requested IP, return false
    return false
}
