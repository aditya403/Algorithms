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





gridStringJSON = '[{"SYSID":"1","VIP":"","PRIMARY_DEVICENAME":"","SECONDARY_DEVICENAME":"","BACKEND_SERVER":"","ROUTE_PREFIX_IP":"","STATUS":""}]';


=========VIP==========
156.55.203.135	443										
156.55.203.243	443										
156.55.203.244	443										
156.55.203.245	443										
156.55.154.19	443										
156.55.154.20	443										
=========VIP==========
=========DEVICES==========
=========DEVICES==========
=========BACKEND SERVER==========
=========BACKEND SERVER==========
=========ROUTE-PREFIX==========
=========ROUTE-PREFIX==========



RITM24006356481 CH25001925004 Phase1 Decom

Remove VIP and all associated Ports and backend servers

156.55.203.135	443										
156.55.203.243	443										
156.55.203.244	443										
156.55.203.245	443										
156.55.154.19	443										
156.55.154.20	443										
156.55.154.21	443										
156.55.154.22	443										
156.55.154.24	443										
156.55.154.23	443										
156.55.154.25	443										
156.55.203.241	443										
156.55.154.30	443										
156.55.154.28	443										
156.55.154.26	443										
156.55.154.27	443										
156.55.154.29	443	

lease add a validation task for "D1Flex ApplicationDelivery" and also send a meeting invite to "D1.Flex.ServiceDelivery@fisglobal.com" so that we can validate at the time of implementation.
====================
Change Script
====================
cdl120-lrd03ma-arus -> Primary
cdl121-lrd03ma-arus

disable lb vserver OLBImps1.fisglobal.com
disable lb vserver olbimps6.fisglobal.com-443
disable lb vserver olbimps7.fisglobal.com-443
disable lb vserver olbimps8.fisglobal.com-443
disable lb vserver VIP-156.55.154.19-443
disable lb vserver VIP-156.55.154.20-443
disable lb vserver VIP-156.55.154.21-443
disable lb vserver VIP-156.55.154.22-443
disable lb vserver VIP-156.55.154.24-443
disable lb vserver VIP-156.55.154.23-443
disable lb vserver VIP-156.55.154.25-443
disable lb vserver olbimps4.fisglobal.com
disable lb vserver VIP-156.55.154.30-443
disable lb vserver VIP-156.55.154.28-443
disable lb vserver VIP-156.55.154.26-443
disable lb vserver VIP-156.55.154.27-443
disable lb vserver VIP-156.55.154.29-443


save ns config


====================
Post Validation
====================
stat lb vserver | grep 156.55.203.135
stat lb vserver | grep 156.55.203.243
stat lb vserver | grep 156.55.203.244
stat lb vserver | grep 156.55.203.245
stat lb vserver | grep "156.55.154.19	"										
stat lb vserver | grep "156.55.154.20	"										
stat lb vserver | grep "156.55.154.21	"										
stat lb vserver | grep "156.55.154.22	"										
stat lb vserver | grep "156.55.154.24	"										
stat lb vserver | grep "156.55.154.23	"										
stat lb vserver | grep "156.55.154.25	"										
stat lb vserver | grep 156.55.203.241											
stat lb vserver | grep "156.55.154.30	"										
stat lb vserver | grep "156.55.154.28	"										
stat lb vserver | grep "156.55.154.26	"										
stat lb vserver | grep "156.55.154.27	"										
stat lb vserver | grep "156.55.154.29	"	

====================
Backout
====================
enable lb vserver OLBImps1.fisglobal.com
enable lb vserver olbimps6.fisglobal.com-443
enable lb vserver olbimps7.fisglobal.com-443
enable lb vserver olbimps8.fisglobal.com-443
enable lb vserver VIP-156.55.154.19-443
enable lb vserver VIP-156.55.154.20-443
enable lb vserver VIP-156.55.154.21-443
enable lb vserver VIP-156.55.154.22-443
enable lb vserver VIP-156.55.154.24-443
enable lb vserver VIP-156.55.154.23-443
enable lb vserver VIP-156.55.154.25-443
enable lb vserver olbimps4.fisglobal.com
enable lb vserver VIP-156.55.154.30-443
enable lb vserver VIP-156.55.154.28-443
enable lb vserver VIP-156.55.154.26-443
enable lb vserver VIP-156.55.154.27-443
enable lb vserver VIP-156.55.154.29-443


save ns config

====================
Pre Validation
====================
OLBI...l.com  156.55.203.135   443          SSL         DOWN      0/s        0
olbi...m-443  156.55.203.243   443          SSL         DOWN      0/s        0
olbi...m-443  156.55.203.244   443          SSL         DOWN      0/s        0
olbi...m-443  156.55.203.245   443          SSL         DOWN      0/s        0
VIP-...9-443   156.55.154.19   443          SSL OUT ...RVICE      0/s        0
VIP-...0-443   156.55.154.20   443          SSL         DOWN      0/s        0
VIP-...1-443   156.55.154.21   443          SSL OUT ...RVICE      0/s        0
VIP-...2-443   156.55.154.22   443          SSL OUT ...RVICE      0/s        0
VIP-...4-443   156.55.154.24   443          SSL         DOWN      0/s        0
VIP-...3-443   156.55.154.23   443          SSL         DOWN      0/s        0
VIP-...5-443   156.55.154.25   443          SSL         DOWN      0/s        0
olbi...l.com  156.55.203.241   443          SSL         DOWN      0/s        0
VIP-...0-443   156.55.154.30   443          SSL         DOWN      0/s        0
VIP-...8-443   156.55.154.28   443          SSL         DOWN      0/s        0
VIP-...6-443   156.55.154.26   443          SSL         DOWN      0/s        0
VIP-...7-443   156.55.154.27   443          SSL         DOWN      0/s        0
VIP-...9-443   156.55.154.29   443          SSL         DOWN      0/s        0


Primary@cdl120-lrd03ma-arus-2025/07/31-06:23> sho save | grep -i OLBImps1.fisglobal.com
add lb vserver OLBImps1.fisglobal.com SSL 156.55.203.135 443 -persistenceType COOKIEINSERT -timeout 0 -cltTimeout 180 -devno 128221184
bind lb vserver OLBImps1.fisglobal.com 10.237.66.47:1158
bind lb vserver OLBImps1.fisglobal.com 10.237.66.48:1158
bind lb vserver OLBImps1.fisglobal.com 10.237.66.49:1158
set ssl vserver OLBImps1.fisglobal.com -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED
bind ssl vserver OLBImps1.fisglobal.com -cipherName FIS-2017
bind ssl vserver OLBImps1.fisglobal.com -certkeyName wildcard.fisglobal
bind ssl vserver OLBImps1.fisglobal.com -eccCurveName P_256
bind ssl vserver OLBImps1.fisglobal.com -eccCurveName P_384
bind ssl vserver OLBImps1.fisglobal.com -eccCurveName P_224
bind ssl vserver OLBImps1.fisglobal.com -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:24> sho save | grep -i olbimps6.fisglobal.com-443
add lb vserver olbimps6.fisglobal.com-443 SSL 156.55.203.243 443 -persistenceType COOKIEINSERT -timeout 0 -cltTimeout 180 -comment 1902056926 -redirectFromPort 80 -httpsRedirectUrl "https://olbimps6.fisglobal.com" -devno 129105920
bind lb vserver olbimps6.fisglobal.com-443 10.237.66.50-1155
bind lb vserver olbimps6.fisglobal.com-443 10.237.66.51-1155
bind lb vserver olbimps6.fisglobal.com-443 10.237.66.52-1155
bind lb vserver olbimps6.fisglobal.com-443 -policyName check_delete_xff -priority 20 -gotoPriorityExpression NEXT -type REQUEST
bind lb vserver olbimps6.fisglobal.com-443 -policyName client_to_xff -priority 30 -gotoPriorityExpression END -type REQUEST
bind lb vserver olbimps6.fisglobal.com-443 -policyName add_xff -priority 40 -gotoPriorityExpression END -type REQUEST
bind lb vserver olbimps6.fisglobal.com-443 -policyName rw_pol_force_secure_cookie -priority 100 -gotoPriorityExpression NEXT -type RESPONSE
set ssl vserver olbimps6.fisglobal.com-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED
bind ssl vserver olbimps6.fisglobal.com-443 -cipherName FIS-2020
bind ssl vserver olbimps6.fisglobal.com-443 -certkeyName wildcard.fisglobal
bind ssl vserver olbimps6.fisglobal.com-443 -eccCurveName P_256
bind ssl vserver olbimps6.fisglobal.com-443 -eccCurveName P_384
bind ssl vserver olbimps6.fisglobal.com-443 -eccCurveName P_224
bind ssl vserver olbimps6.fisglobal.com-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:25> sho save | grep -i olbimps7.fisglobal.com-443
add lb vserver olbimps7.fisglobal.com-443 SSL 156.55.203.244 443 -persistenceType COOKIEINSERT -timeout 0 -cltTimeout 180 -comment 1902247545 -redirectFromPort 80 -httpsRedirectUrl "https://olbimps7.fisglobal.com" -devno 129138688
bind lb vserver olbimps7.fisglobal.com-443 10.237.66.53:1151
bind lb vserver olbimps7.fisglobal.com-443 10.237.66.54:1151
bind lb vserver olbimps7.fisglobal.com-443 10.237.66.55:1151
set ssl vserver olbimps7.fisglobal.com-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED
bind ssl vserver olbimps7.fisglobal.com-443 -cipherName FIS-2020
bind ssl vserver olbimps7.fisglobal.com-443 -cipherName DEFAULT
bind ssl vserver olbimps7.fisglobal.com-443 -certkeyName wildcard.fisglobal
bind ssl vserver olbimps7.fisglobal.com-443 -eccCurveName P_256
bind ssl vserver olbimps7.fisglobal.com-443 -eccCurveName P_384
bind ssl vserver olbimps7.fisglobal.com-443 -eccCurveName P_224
bind ssl vserver olbimps7.fisglobal.com-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:25> sho save | grep -i olbimps8.fisglobal.com-443 
add lb vserver olbimps8.fisglobal.com-443 SSL 156.55.203.245 443 -persistenceType COOKIEINSERT -timeout 0 -cltTimeout 180 -comment 1902247545 -redirectFromPort 80 -httpsRedirectUrl "https://olbimps8.fisglobal.com" -devno 129171456
bind lb vserver olbimps8.fisglobal.com-443 10.237.66.53:1150
bind lb vserver olbimps8.fisglobal.com-443 10.237.66.54:1150
bind lb vserver olbimps8.fisglobal.com-443 10.237.66.55:1150
set ssl vserver olbimps8.fisglobal.com-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED
bind ssl vserver olbimps8.fisglobal.com-443 -cipherName FIS-2020
bind ssl vserver olbimps8.fisglobal.com-443 -cipherName DEFAULT
bind ssl vserver olbimps8.fisglobal.com-443 -certkeyName wildcard.fisglobal
bind ssl vserver olbimps8.fisglobal.com-443 -eccCurveName P_256
bind ssl vserver olbimps8.fisglobal.com-443 -eccCurveName P_384
bind ssl vserver olbimps8.fisglobal.com-443 -eccCurveName P_224
bind ssl vserver olbimps8.fisglobal.com-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:26> sho save | grep "156.55.154.19"
add ns ip 156.55.154.19 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.19-443 SSL 156.55.154.19 443 -persistenceType COOKIEINSERT -timeout 20 -cookieName 8BT7945fkc9 -state DISABLED -cltTimeout 180 -comment CH20000277421 -RHIstate ACTIVE -devno 129499136
bind lb vserver VIP-156.55.154.19-443 10.237.66.47:1158
bind lb vserver VIP-156.55.154.19-443 10.237.66.49:1158
bind lb vserver VIP-156.55.154.19-443 10.237.66.48:1158
set ssl vserver VIP-156.55.154.19-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.19-443 -cipherName FIS-2020
bind ssl vserver VIP-156.55.154.19-443 -certkeyName wildcard.fisglobal
bind ssl vserver VIP-156.55.154.19-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.19-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.19-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.19-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.20"
add ns ip 156.55.154.20 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.20-443 SSL 156.55.154.20 443 -persistenceType COOKIEINSERT -timeout 20 -cookieName 8BT7945fkc9 -cltTimeout 180 -comment CH20000277421 -RHIstate ACTIVE -devno 129531904
bind lb vserver VIP-156.55.154.20-443 10.236.119.22:80
set ssl vserver VIP-156.55.154.20-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.20-443 -cipherName FIS-2020
bind ssl vserver VIP-156.55.154.20-443 -certkeyName wildcard.fisglobal
bind ssl vserver VIP-156.55.154.20-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.20-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.20-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.20-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.21"
add ns ip 156.55.154.21 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.21-443 SSL 156.55.154.21 443 -persistenceType COOKIEINSERT -timeout 20 -cookieName 8BT7945fkc9 -state DISABLED -cltTimeout 180 -comment CH20000277421 -RHIstate ACTIVE -devno 129564672
bind lb vserver VIP-156.55.154.21-443 10.237.66.47-1160
bind lb vserver VIP-156.55.154.21-443 10.237.66.48-1160
bind lb vserver VIP-156.55.154.21-443 10.237.66.49-1160
set ssl vserver VIP-156.55.154.21-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.21-443 -cipherName FIS-2020
bind ssl vserver VIP-156.55.154.21-443 -certkeyName wildcard.fisglobal
bind ssl vserver VIP-156.55.154.21-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.21-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.21-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.21-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.22"
add ns ip 156.55.154.22 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.22-443 SSL 156.55.154.22 443 -persistenceType COOKIEINSERT -timeout 20 -cookieName 8BT7945fkc9 -state DISABLED -cltTimeout 180 -comment CH20000277421 -RHIstate ACTIVE -devno 129597440
bind lb vserver VIP-156.55.154.22-443 10.237.66.47-1161
bind lb vserver VIP-156.55.154.22-443 10.237.66.48-1161
bind lb vserver VIP-156.55.154.22-443 10.237.66.49-1161
set ssl vserver VIP-156.55.154.22-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.22-443 -cipherName FIS-2020
bind ssl vserver VIP-156.55.154.22-443 -certkeyName wildcard.fisglobal
bind ssl vserver VIP-156.55.154.22-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.22-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.22-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.22-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.24"
add ns ip 156.55.154.24 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.24-443 SSL 156.55.154.24 443 -persistenceType COOKIEINSERT -timeout 0 -cookieName 8BT7945fkc9 -cltTimeout 180 -comment CH20000284522 -RHIstate ACTIVE -devno 129630208
add lb vserver VIP-156.55.154.24-777 HTTP 156.55.154.24 777 -persistenceType NONE -cltTimeout 180 -comment CH21000383484 -devno 130154496
bind lb vserver VIP-156.55.154.24-443 10.237.66.47-1163
bind lb vserver VIP-156.55.154.24-443 10.237.66.48-1163
bind lb vserver VIP-156.55.154.24-443 10.237.66.49-1163
bind lb vserver VIP-156.55.154.24-777 always-up-service
set ssl vserver VIP-156.55.154.24-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.24-443 -cipherName FIS-2020
bind ssl vserver VIP-156.55.154.24-443 -certkeyName wildcard.fisglobal
bind ssl vserver VIP-156.55.154.24-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.24-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.24-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.24-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.23"
add ns ip 156.55.154.23 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.23-443 SSL 156.55.154.23 443 -persistenceType COOKIEINSERT -timeout 0 -cookieName 8BT7945fkc9 -cltTimeout 180 -comment CH20000284522 -RHIstate ACTIVE -devno 129662976
add lb vserver VIP-156.55.154.23-777 HTTP 156.55.154.23 777 -persistenceType NONE -cltTimeout 180 -comment CH21000383484 -devno 130121728
bind lb vserver VIP-156.55.154.23-443 10.237.66.47-1162
bind lb vserver VIP-156.55.154.23-443 10.237.66.48-1162
bind lb vserver VIP-156.55.154.23-443 10.237.66.49-1162
bind lb vserver VIP-156.55.154.23-777 always-up-service
set ssl vserver VIP-156.55.154.23-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.23-443 -cipherName FIS-2020
bind ssl vserver VIP-156.55.154.23-443 -certkeyName wildcard.fisglobal
bind ssl vserver VIP-156.55.154.23-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.23-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.23-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.23-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.25"
add ns ip 156.55.154.25 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.25-443 SSL 156.55.154.25 443 -persistenceType COOKIEINSERT -timeout 0 -cookieName 8BT7945fkc9 -cltTimeout 180 -comment CH20000284522 -RHIstate ACTIVE -devno 129695744
add lb vserver VIP-156.55.154.25-777 HTTP 156.55.154.25 777 -persistenceType NONE -cltTimeout 180 -comment CH21000383484 -devno 130187264
bind lb vserver VIP-156.55.154.25-443 10.237.66.47-1164
bind lb vserver VIP-156.55.154.25-443 10.237.66.48-1164
bind lb vserver VIP-156.55.154.25-443 10.237.66.49-1164
bind lb vserver VIP-156.55.154.25-777 always-up-service
set ssl vserver VIP-156.55.154.25-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.25-443 -cipherName FIS-2020
bind ssl vserver VIP-156.55.154.25-443 -certkeyName wildcard.fisglobal
bind ssl vserver VIP-156.55.154.25-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.25-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.25-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.25-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:30> sho save | grep -i olbimps4.fisglobal.com
add lb vserver olbimps4.fisglobal.com SSL 156.55.203.241 443 -persistenceType COOKIEINSERT -timeout 0 -cltTimeout 180 -devno 129826816
bind lb vserver olbimps4.fisglobal.com 10.237.66.47-1164
bind lb vserver olbimps4.fisglobal.com 10.237.66.48-1164
bind lb vserver olbimps4.fisglobal.com 10.237.66.49-1164
set ssl vserver olbimps4.fisglobal.com -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED
bind ssl vserver olbimps4.fisglobal.com -cipherName FIS-2017
bind ssl vserver olbimps4.fisglobal.com -cipherName DEFAULT
bind ssl vserver olbimps4.fisglobal.com -certkeyName wildcard.fisglobal
bind ssl vserver olbimps4.fisglobal.com -eccCurveName P_256
bind ssl vserver olbimps4.fisglobal.com -eccCurveName P_384
bind ssl vserver olbimps4.fisglobal.com -eccCurveName P_224
bind ssl vserver olbimps4.fisglobal.com -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.30"
add ns ip 156.55.154.30 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.30-443 SSL 156.55.154.30 443 -persistenceType COOKIEINSERT -timeout 20 -cltTimeout 180 -comment CH21000369514 -RHIstate ACTIVE -devno 129859584
bind lb vserver VIP-156.55.154.30-443 10.237.66.103-1153
bind lb vserver VIP-156.55.154.30-443 10.237.66.104-1153
bind lb vserver VIP-156.55.154.30-443 10.237.66.105-1153
set ssl vserver VIP-156.55.154.30-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.30-443 -cipherName FIS-2021
bind ssl vserver VIP-156.55.154.30-443 -certkeyName fisglobal.com-wild
bind ssl vserver VIP-156.55.154.30-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.30-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.30-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.30-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.28"
add ns ip 156.55.154.28 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.28-443 SSL 156.55.154.28 443 -persistenceType COOKIEINSERT -timeout 20 -cltTimeout 180 -comment CH21000369514 -RHIstate ACTIVE -devno 129892352
bind lb vserver VIP-156.55.154.28-443 10.237.66.103-1151
bind lb vserver VIP-156.55.154.28-443 10.237.66.104-1151
bind lb vserver VIP-156.55.154.28-443 10.237.66.105-1151
set ssl vserver VIP-156.55.154.28-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.28-443 -cipherName FIS-2021
bind ssl vserver VIP-156.55.154.28-443 -certkeyName fisglobal.com-wild
bind ssl vserver VIP-156.55.154.28-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.28-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.28-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.28-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.26"
add ns ip 156.55.154.26 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.26-443 SSL 156.55.154.26 443 -persistenceType COOKIEINSERT -timeout 20 -cltTimeout 180 -comment CH21000369514 -RHIstate ACTIVE -devno 129925120
bind lb vserver VIP-156.55.154.26-443 10.237.66.103-1110
bind lb vserver VIP-156.55.154.26-443 10.237.66.104-1110
bind lb vserver VIP-156.55.154.26-443 10.237.66.105-1110
set ssl vserver VIP-156.55.154.26-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.26-443 -cipherName FIS-2021
bind ssl vserver VIP-156.55.154.26-443 -certkeyName fisglobal.com-wild
bind ssl vserver VIP-156.55.154.26-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.26-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.26-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.26-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.27"
add ns ip 156.55.154.27 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.27-443 SSL 156.55.154.27 443 -persistenceType COOKIEINSERT -timeout 20 -cltTimeout 180 -comment CH21000369514 -RHIstate ACTIVE -devno 129957888
bind lb vserver VIP-156.55.154.27-443 10.237.66.103-1150
bind lb vserver VIP-156.55.154.27-443 10.237.66.104-1150
bind lb vserver VIP-156.55.154.27-443 10.237.66.105-1150
set ssl vserver VIP-156.55.154.27-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.27-443 -cipherName FIS-2021
bind ssl vserver VIP-156.55.154.27-443 -certkeyName fisglobal.com-wild
bind ssl vserver VIP-156.55.154.27-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.27-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.27-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.27-443 -eccCurveName P_521

Primary@cdl120-lrd03ma-arus-2025/07/31-06:28> sho save | grep "156.55.154.29"
add ns ip 156.55.154.29 255.255.255.255 -type VIP -snmp DISABLED -hostRoute ENABLED -hostRtGw 0.0.0.0
add lb vserver VIP-156.55.154.29-443 SSL 156.55.154.29 443 -persistenceType COOKIEINSERT -timeout 20 -cltTimeout 180 -comment CH21000369514 -RHIstate ACTIVE -devno 129990656
bind lb vserver VIP-156.55.154.29-443 10.237.66.103-1152
bind lb vserver VIP-156.55.154.29-443 10.237.66.104-1152
bind lb vserver VIP-156.55.154.29-443 10.237.66.105-1152
set ssl vserver VIP-156.55.154.29-443 -ssl3 DISABLED -tls1 DISABLED -tls11 DISABLED -dtls1 DISABLED -HSTS ENABLED -maxage 31536000
bind ssl vserver VIP-156.55.154.29-443 -cipherName FIS-2021
bind ssl vserver VIP-156.55.154.29-443 -certkeyName fisglobal.com-wild
bind ssl vserver VIP-156.55.154.29-443 -eccCurveName P_256
bind ssl vserver VIP-156.55.154.29-443 -eccCurveName P_384
bind ssl vserver VIP-156.55.154.29-443 -eccCurveName P_224
bind ssl vserver VIP-156.55.154.29-443 -eccCurveName P_521





















def fetchNetworkObjects(scriptText) {
    def networkObjects = [] as Set  // Use Set to auto-remove duplicates
    def currentObject = null

    scriptText.eachLine { line ->
        line = line.replaceAll(/^-->\s*/, '').trim()  // Remove '-->' and extra spaces

        if (line.startsWith('object network')) {
            // Capture the main object name
            currentObject = line.split(/\s+/)[2]
        } else if (currentObject && line.startsWith('network-object object')) {
            // Extract object name from each network-object line
            def objName = line.split(/\s+/)[2]
            networkObjects << objName
        }
    }

    return networkObjects.toList()
}
