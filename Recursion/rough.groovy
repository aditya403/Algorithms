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










def fetchObjectGroupsWithObject(scriptText, targetObject) {
    def resultLines = []
    def currentGroup = null
    def includeCurrentGroup = false

    scriptText.eachLine { line ->
        line = line.replaceAll(/^-->\s*/, '').trim() // Remove '-->' and spaces

        if (line.startsWith('object-group network')) {
            currentGroup = line
            includeCurrentGroup = false
        } else if (line.startsWith('network-object object') && line.contains(targetObject)) {
            includeCurrentGroup = true
            // Add both lines only once per group
            if (!resultLines.contains(currentGroup)) resultLines << currentGroup
            resultLines << "  ${line}"
        }
    }

    // Filter out any accidental blanks (edge cases)
    return resultLines.findAll { it?.trim() }
}








--> show run object-group network | i object-group| p750-Curtiz
--> object-group network grp_FIS_Europe
--> object-group network grp_network_devices
--> object-group network grp_Network_Managment
--> object-group network grp_syslog_servers
--> object-group network grp_tacacs_servers
--> object-group network grp_FISDNS_servers
--> object-group network grp_MEVDNS_servers
--> object-group network grp_EFD3_access
--> object-group network EFD
--> object-group network PostOfficeTest_Webserver
--> object-group network PostOffice_Test_Servers
--> object-group network grp_Migration_Servers
--> object-group network grp_europe
--> object-group network grp_Windows_Servers
--> object-group network grp_vmware_servers
--> object-group network grp_server_oob_management
--> object-group network grp_Cortex_test_Servers
--> object-group network grp_mv_domain_controllers
--> object-group network grp_monitored_by_sitescope
--> object-group network grp_sitescope
--> object-group network grp_monitored_by_scom
--> object-group network grp_server_oob
--> object-group network grp_vsphere_servers
--> object-group network grp_unix_servers
--> object-group network grp_new_berlin
--> object-group network DM_INLINE_NETWORK_1
--> object-group network grp_windows_servers
--> object-group network grp_VPC_hosts
--> object-group network grp_web_front_end
--> object-group network grp_domain_controllers
--> object-group network grp_unsecure_unix_servers
--> object-group network grp_cortex_test
--> object-group network grp_indian_access
--> object-group network grp_indian_ps_general_access
--> object-group network grp_indian_ps_informix
--> object-group network grp_indian_ps_oracle
--> object-group network grp_indian_ps_perforce
--> object-group network grp_support_servers
--> object-group network grp_remote_domain_controllers
--> object-group network US_Cortex_Access_Servers
--> object-group network grp_monitored_by_tripwire
--> object-group network grp_tripwire_enterprise
--> object-group network grp_CorpRAS
--> object-group network US_Cortex_Access_Sources
--> object-group network grp_epo_servers
--> object-group network grp_remote_epo_servers
--> object-group network grp_perforce
--> object-group network grp_Indian_Dev_Servers
--> object-group network grp_australia_access
--> object-group network grp_australia_servers
--> object-group network grp_HP_Console_Servers
--> object-group network WUG-server
--> object-group network Grp_VM_Servers
--> object-group network grp_corpras_workstations
--> object-group network DM_INLINE_NETWORK_2
--> object-group network grp_FIS_DNS_Servers
--> object-group network grp_Indian_Testers
--> object-group network grp_Indian_Testers_servers
--> object-group network DM_INLINE_NETWORK_3
--> object-group network DM_INLINE_NETWORK_4
--> object-group network grp_test_hsm
--> object-group network grp_linux_workstations
--> object-group network DM_INLINE_NETWORK_5
--> object-group network DM_INLINE_NETWORK_6
--> object-group network DM_INLINE_NETWORK_11
--> object-group network DM_INLINE_NETWORK_8
--> object-group network DM_INLINE_NETWORK_12
--> object-group network grp_Indian_high_port_servers
--> object-group network Regent_Street_Access_Switches
--> object-group network 192_Subnet_blocks
--> object-group network grp_DNS
--> object-group network grp_mandiant
--> object-group network grp_vdi_mandiant
--> object-group network grp_vdi_mcafee
--> object-group network grp_vdi_wsus
--> object-group network grp_vdi_kms
--> object-group network grp_ntp
--> object-group network grp_vdi_ms_domain
--> object-group network grp_vdi_jms
--> object-group network grp_vedas_sources
--> object-group network grp_vedas_targets
--> object-group network grp_d8_sources
--> object-group network grp_rdf_sources
--> object-group network grp_windows_support
--> object-group network DM_INLINE_NETWORK_10
--> object-group network DM_INLINE_NETWORK_7
--> object-group network DM_INLINE_NETWORK_9
--> object-group network DM_INLINE_NETWORK_13
--> object-group network DM_INLINE_NETWORK_14
--> object-group network DM_INLINE_NETWORK_25
--> object-group network FIS_VDI_access
--> object-group network Indian_subnets
--> object-group network grp_locall_all
--> object-group network grp_connection_managers
--> object-group network grp_vCentre
--> object-group network DM_INLINE_NETWORK_15
--> object-group network DM_INLINE_NETWORK_16
--> object-group network grp_fileservers
--> object-group network grp-partner-sources
--> object-group network grp_smtp_sources
--> object-group network grp_smtp_servers
--> object-group network DM_INLINE_NETWORK_17
--> object-group network grp_website_mon_source
--> object-group network grp_website_monitor
--> object-group network grp_zenoss_monitors
--> object-group network DM_INLINE_NETWORK_18
--> object-group network grp_test_ldap
--> object-group network grp_test_web
--> object-group network DM_INLINE_NETWORK_19
--> object-group network DM_INLINE_NETWORK_20
--> object-group network grp_VDI_vupport_servers
--> object-group network Tripwire_servers
--> object-group network grp_india
--> object-group network DM_INLINE_NETWORK_21
--> object-group network DM_INLINE_NETWORK_22
--> object-group network grp_visa_test
--> object-group network DM_INLINE_NETWORK_23
--> object-group network Grp-India-VLAN53-targets
--> object-group network Grp-India-vlan53
--> object-group network DM_INLINE_NETWORK_24
--> object-group network DM_INLINE_NETWORK_26
--> object-group network DM_INLINE_NETWORK_27
--> object-group network DM_INLINE_NETWORK_28
--> object-group network DM_INLINE_NETWORK_29
--> object-group network DM_INLINE_NETWORK_30
--> object-group network Jboss-servers
--> object-group network grp_proxy_servers
--> object-group network DM_INLINE_NETWORK_31
--> object-group network DM_INLINE_NETWORK_32
--> object-group network group_Tripwire_Log_Center
--> object-group network group-p730-servers
--> object-group network group-p750-servers
-->  network-object object p750-Curtiz
--> object-group network group-FIS-Desktops
--> object-group network group-nim-servers
--> object-group network grp_Production_Nets
--> object-group network DM_INLINE_NETWORK_33
--> object-group network DM_INLINE_NETWORK_34
--> object-group network Beijing
--> object-group network grp_beijing_targets
--> object-group network grp_cert_server
--> object-group network DM_INLINE_NETWORK_35
--> object-group network DM_INLINE_NETWORK_36
--> object-group network DM_INLINE_NETWORK_37
--> object-group network group_India_Chennai
--> object-group network drp_vdi_centos_servers
--> object-group network DM_INLINE_NETWORK_38
--> object-group network DM_INLINE_NETWORK_40
--> object-group network DM_INLINE_NETWORK_39
--> object-group network DM_INLINE_NETWORK_41
--> object-group network DM_INLINE_NETWORK_42
--> object-group network monitor_foglight_server
--> object-group network DM_INLINE_NETWORK_43
--> object-group network DM_INLINE_NETWORK_44
--> object-group network DM_INLINE_NETWORK_45
--> object-group network DM_INLINE_NETWORK_46
--> object-group network DM_INLINE_NETWORK_47
--> object-group network DM_INLINE_NETWORK_48
--> object-group network DM_INLINE_NETWORK_49
--> object-group network DM_INLINE_NETWORK_50
--> object-group network DM_INLINE_NETWORK_51
--> object-group network grp_commvault
--> object-group network grp_emeasmarthost
--> object-group network grp_foglight
--> object-group network grp_india_chennai_lan
--> object-group network grp_india_chennai_lan_target
--> object-group network grp_monitor_nexpose
--> object-group network grp_monitoring
--> object-group network grp_ntp_servers
--> object-group network grp_bluecoats
--> object-group network grp_mailservers
--> object-group network group_local_vmhost
--> object-group network group_Bangalore_DRE_Source
--> object-group network group_Bangalore_DRE_targets
--> object-group network group_Chennai_Source
--> object-group network group_India_Testers_Source
--> object-group network group_India_FIS
--> object-group network group_Chennai_access
--> object-group network group_DPP_ATM_dev_test
--> object-group network group_DPP_test_servers
--> object-group network Grp-dev-servers
--> object-group network Grp-dev-test
--> object-group network Grp-dev-web-db
--> object-group network grp-desktop-fis
--> object-group network Grp_Chennai_source
--> object-group network MIP-OHx-Transit-Translated
--> object-group network group-access-by-toronto
--> object-group network grp_visa_test2
--> object-group network group_domain-controllers
--> object-group network DM_INLINE_NETWORK_52
--> object-group network DM_INLINE_NETWORK_53
--> object-group network DM_INLINE_NETWORK_54
--> object-group network DM_INLINE_NETWORK_55
--> object-group network group_McAfee-servers
--> object-group network group_Microsoft_licensing
--> object-group network group_Oracle_India_FIS
--> object-group network group_IST_suite
--> object-group network group_remote-offices
--> object-group network group_ukbidevictxlx01-02
--> object-group network DM_INLINE_NETWORK_56
--> object-group network DM_INLINE_NETWORK_57
--> object-group network DM_INLINE_NETWORK_58
--> object-group network DM_INLINE_NETWORK_59
--> object-group network DM_INLINE_NETWORK_60
--> object-group network group_dns_servers
--> object-group network group_Nexpose
--> object-group network grp-BOI-test-servers
--> object-group network grp-bhx-dev-servers
--> object-group network linuxredhat_boxes_newconnectivity
--> object-group network grp_AusFIS_access
--> object-group network Indian_subnet_chennai
--> object-group network UK_Network
--> object-group network India_chennai_maauxserver
--> object-group network Indian_chennai_maauxserver
--> object-group network USA_HMC_Network
--> object-group network DM_INLINE_NETWORK_61
--> object-group network maaux477-metavanteuk_ukbidevictxlx
--> object-group network DM_INLINE_NETWORK_62
--> object-group network DM_INLINE_NETWORK_63
--> object-group network DM_INLINE_NETWORK_64
--> object-group network DM_INLINE_NETWORK_65
--> object-group network Liferay_servers
--> object-group network ATOM_Xpress_Server
--> object-group network Atom-Profile-Servers
--> object-group network DM_INLINE_NETWORK_66
--> object-group network Atom_sftp
--> object-group network DM_INLINE_NETWORK_67
--> object-group network foglight_Object-Group
--> object-group network Chennai_Ambit_Unix
--> object-group network DM_INLINE_NETWORK_68
--> object-group network CIRBA-COLLECTORS
--> object-group network group-cyber-ark
--> object-group network DM_INLINE_NETWORK_69
--> object-group network group_India_FIS_2
--> object-group network P8_LPAR_1-10_22-25
--> object-group network P8_LPAR_11-16_group
--> object-group network DM_INLINE_NETWORK_70
--> object-group network DM_INLINE_NETWORK_71
--> object-group network DM_INLINE_NETWORK_72
--> object-group network DM_INLINE_NETWORK_73
--> object-group network DM_INLINE_NETWORK_74
--> object-group network ukbidpplx_vip
--> object-group network maaux46x_group
--> object-group network group_Chennai_Source_2
--> object-group network group_chennai_2_dpp_test
--> object-group network group_Chennai_Source_3
--> object-group network grp_cortex_test_2
--> object-group network SCAPM-SERVERS
--> object-group network BC_STT_VLANs
--> object-group network grp_unix_servers_2
-->  network-object object p750-Curtiz
--> object-group network Wintel-SCAPM
--> object-group network CTX_Complaince
--> object-group network DM_INLINE_NETWORK_75
--> object-group network DM_INLINE_NETWORK_76
--> object-group network RC-Project-Group
--> object-group network DM_INLINE_NETWORK_80
--> object-group network group_new1102
--> object-group network DM_INLINE_NETWORK_77
--> object-group network GROUP-FIS-OFFICES-POWER8
--> object-group network SC-SMARTCLOUD-AGENTS
--> object-group network P8_LPAR_1-10_17-25
--> object-group network SC-SMARTCLOUD-AGENT-2
--> object-group network GROUP-PRODUCT-TESTING-UAT
--> object-group network Server-Agents-Daemon
--> object-group network ALL-LPI-RSA
--> object-group network UK-DEV-SERVERS
-->  network-object object p750-Curtiz
--> object-group network ASDA-VIA-TNS-CORTEX
--> object-group network CORTEX-BASTIONS
--> object-group network EMEA-BIGFIX-RELAY
--> object-group network BIGFIX-RELAY-CORE-TOOLS
--> object-group network AIX-LPARS-UBM
--> object-group network UK-OFFICE-VPN-NETWORKS
--> object-group network ALL-FIS-BOKS
--> object-group network GROUP-FIS-INDIA
--> object-group network ITM-DESTINATION-SERVERS
--> object-group network GROUP-DPP-DEV-TEST-JBOSS
--> object-group network GROUP-FIS-INDIA-ONE
--> object-group network GROUP-DEV-WEB-DB-UK
--> object-group network GROUP-DEV-TEST
--> object-group network GROUP-UAT-SERVERS
--> object-group network GROUP-INDIA-CORP-SOURCES
--> object-group network CORP-BASTION-RANGE
--> object-group network VISA-LINK-SIMULATORS
--> object-group network customer_internet_sources
--> object-group network GROUP-PRODUCT-TESTING-UAT-2
--> object-group network LOGRYTHM-COLLECTORS-UBD-TRANSAXPAY
--> object-group network SAMA-WEBSOCKET-GROUP
--> object-group network AIX-LPAR-GROUP
--> object-group network Cortex-test-workstations
--> object-group network BC-LPI-PROXY
--> object-group network IST-WEB-DEV-GROUP
--> object-group network SCC-REVERSE-PROXY
--> object-group network GROUP-FIS-INDIA-TWO
--> object-group network TEST-JBOSS-SERVERS-GROUP
--> object-group network UK-AIX-LPAR-GROUP
--> object-group network JBOSS-PANORAMA-INCOMING
--> object-group network IST-DEV-GROUP
--> object-group network AMS-BASTION-SERVERS
--> object-group network EMEA-HPSA-SERVERS
--> object-group network GROUP-HP-AUTOMATION
--> object-group network IST-PDT-DEV-SOURCES
--> object-group network Group-Sqid-proxy
--> object-group network IST-ORACLE-GROUP
--> object-group network CORTEX-CUSTOMER-TEST-SERVER
--> object-group network GROUP-CARDS-UK
--> object-group network IND-CHN-RCC-VLAN
--> object-group network DEV-LPAR-DPP
--> object-group network CORTEXT-DEV-HSM
--> object-group network LPAR-DPP-DEVELOPMENT
--> object-group network LOGRYTHM-COLLECTORS-UBD
--> object-group network RED-HAT-ACBS
--> object-group network GROUP-BIGFIX-RELAY-EMEA
--> object-group network CCS-PROJECT
--> object-group network BC-SCC-LOGRYTHM-COLLECTORS
--> object-group network ORACLE-DEV-LPAR
--> object-group network CORTEX-TEST-SERVER
--> object-group network Adam_VDI
--> object-group network group-DDLP-Cortex-sources
--> object-group network BEDFORD-VPN-SUBNETS
--> object-group network PAYMENT-PRODUCT-DEV-SERVERS
--> object-group network MCAFEE-EPO-AGENTS
--> object-group network MI-TEST-SERVERS
--> object-group network Client-Xpress-TouchPoint
--> object-group network HSPA-TOOLS
--> object-group network group_dev-meta-Server
--> object-group network MAA-CHN-LAN-SUBNET
--> object-group network Bit9_servers
--> object-group network DPP-CORTEX-SERVERS
--> object-group network RHEL-BDOC-PROD-BASTION
--> object-group network KINGSLEY-MERCHANT-SOLUTION
--> object-group network LONDON_VISA_SERVERS
--> object-group network LONDON_MASTERCARD_SERVERS
--> object-group network RSA_BOKS_servers
--> object-group network RSA_BOKS_SERVERS_NEW
--> object-group network GROUP-MASTER-CARD
--> object-group network MV-BASTION-CONSOLE
--> object-group network Cortex_Servers
--> object-group network BASTION_SERVER
--> object-group network DNS_SERVERS
--> object-group network PROXY_SERVERS
--> object-group network GROUP-BIGFIX-RELAY-ULE-ULW
--> object-group network NTP_SERVERS
--> object-group network BOKS_AGENT_MASTER
--> object-group network BC-SCC-SMART-CLOUD-SERVERS
--> object-group network MILWAUKEE-ENTERPRISE-BASTION
--> object-group network SB-APP-DB
--> object-group network IDP-DIH-APP
--> object-group network Vocalink_sce_Pmouth
--> object-group network CORTEX_DST
--> object-group network VORMETRIC-MGMT-SERVERS
--> object-group network API-GOHENRY
--> object-group network DOMAIN-CONTROLLER-US
--> object-group network group-ntp-servers
--> object-group network CYBER-ARK-DPP-CLIENTS
--> object-group network CYBER-ARK-US-SERVERS
--> object-group network NFS-SERVERS
--> object-group network PHX-DC
--> object-group network SG-PIP-PUNE
--> object-group network UBD_UBS_BASTION
--> object-group network DM_INLINE_NETWORK_78
--> object-group network DM_INLINE_NETWORK_79
--> object-group network DM_INLINE_NETWORK_81
--> object-group network DM_INLINE_NETWORK_82
--> object-group network DM_INLINE_NETWORK_83
--> object-group network CORTEX_SERVERS
--> object-group network SMTP-SERVERS
--> object-group network DM_INLINE_NETWORK_84
--> object-group network DM_INLINE_NETWORK_86
--> object-group network DM_INLINE_NETWORK_85
--> object-group network DM_INLINE_NETWORK_87
--> object-group network DM_INLINE_NETWORK_88
--> object-group network GLOBAL-PROTECT-VPN-SOURCES
--> object-group network TARGETS-WITHIN-DPP-TEST-ENV
--> object-group network DM_INLINE_NETWORK_89
--> object-group network DM_INLINE_NETWORK_91
--> object-group network DM_INLINE_NETWORK_90
--> object-group network DM_INLINE_NETWORK_92
--> object-group network Proxy_bedford-SCC
--> object-group network DM_INLINE_NETWORK_93
--> object-group network jira_fis_dev
--> object-group network DM_INLINE_NETWORK_94
--> object-group network DM_INLINE_NETWORK_95
--> object-group network DM_INLINE_NETWORK_96
--> object-group network DM_INLINE_NETWORK_97
--> object-group network DM_INLINE_NETWORK_98
--> object-group network DM_INLINE_NETWORK_99
--> object-group network Prisma-Source
--> object-group network Prisma-Destination
--> object-group network Cortex_License
--> object-group network grp_IDP-Panorama
--> object-group network DM_INLINE_NETWORK_100
--> object-group network DM_INLINE_NETWORK_101
--> object-group network grp_cortex-test
--> object-group network REDHAT-CENTRAL-PATCHING
--> object-group network India_Dev_hosts
--> object-group network Sodexo-AWS-Source
--> object-group network UCE_targets
--> object-group network BOKS_RSA_clients
--> object-group network DM_INLINE_NETWORK_102
--> object-group network DM_INLINE_NETWORK_103
--> object-group network DM_INLINE_NETWORK_104
--> object-group network DM_INLINE_NETWORK_106
--> object-group network DM_INLINE_NETWORK_105
--> object-group network CipherTrust_Manager
--> object-group network JENKINS_NYCE_SERVERS
--> object-group network MEMENTO-WEBSERVICES
--> object-group network DM_INLINE_NETWORK_107
--> object-group network CORTEX-TARGETS-US
--> object-group network grp_Birmingham_hosts
--> object-group network NTP-SERVERS-2
--> object-group network WP-NAT-OVERLOAD-SOURCES
--> object-group network DM_INLINE_NETWORK_108
--> object-group network CORTEX2-DST
--> object-group network DM_INLINE_NETWORK_109
--> object-group network CYBERARK-INSTANCES
--> object-group network grp_Birmingham_hosts-1
--> object-group network grp_UBD-UBS-legacy-tls-core-tools-1
--> object-group network grp_192.168.2_servers-1
--> object-group network grp_192.168.2_NAT_servers-1
--> object-group network DM_INLINE_NETWORK_110
--> 
--> UK-BI-DPP-TEST-FW02# 
