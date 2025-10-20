////////////
//
// Written by : Troy Lee  (troy.lee@fisglobal.com)  2/17/2022
//
// https://resolve.fnfis.com/resolve/service/wiki/view/CIO_NORA_NETWORK/FirewallDecomm
// https://resolveuat.fnfis.com:8443/resolve/service/wiki/view/CIO_NORA_NETWORK/FirewallDecomm
//
// https://resolveuat.fnfis.com:8443/resolve/jsp/rsclient.jsp#RS.wiki.Main/name=CIO_NORA_NETWORK.FirewallDecommStatus
// https://resolveuat.fnfis.com:8443/resolve/service/wiki/view/CIO_NORA_NETWORK/FirewallDecommStatus


var sysidInc = 1;
document.body.innerHTML = "";

Ext.onReady(function() {
	

	var topLeft = Ext.create('Ext.panel.Panel', {
	    width: 350,
	    height: 230,
	    margin: '15 30 15 15',
	    border: false,
	    html: [
			'<img style="width:120px;height:60px" src="https://paymentweek.com/wp-content/uploads/2015/06/fis.png">',
			'<p style="font-family: Helvetica; font-size: 18px">',
		       'Firewall Decommission Automation',
		    '</p>',
		    '<ul style="font-family: Helvetica; font-size: 12px">',
		       '<li>',
		          'Fill out the RITM and add IP / Server entries before submitting in this Human Guided Automation. ',
		       '</li>',
		       '<li>',
		          'Feel free to <a href="mailto:nss_automation@fisglobal.com">Contact</a> us with questions.',
		       '</li>',
		       '<li>',
		          'Open this <a href="https://resolveuat.fnfis.com:8443/resolve/service/wiki/view/CIO_NORA_NETWORK/FirewallDecommStatus">link</a> for status.',
		       '</li>',		       
		    '</ul>'
		].join(' '),
	});

	var ritmNumber = Ext.create('Ext.form.field.Text', {
	    id: 'ritmNumber',
	    fieldLabel: 'RITM Number',
	    labelWidth: 100,
	    width: 380,
		labelStyle: 'font-family: Helvetica; font-size: 12px;',
	    margin: '60 0 4 0'
	});
	
	var emailAddress = Ext.create('Ext.form.field.Text', {
	    id: 'emailAddress',
	    fieldLabel: 'Your Email',
	    labelWidth: 100,
	    width: 380,
	    value: '@fisglobal.com',
		labelStyle: 'font-family: Helvetica; font-size: 12px;',
	    margin: '0 0 4 0'
	});

	var taskCheckbox = Ext.create('Ext.form.field.Checkbox', {
	    id: 'taskCheckbox',
	    fieldLabel: 'Create business validation task',
	    labelWidth: 100,
	    width: 380,
		labelStyle: 'font-family: Helvetica; font-size: 12px;',
	    margin: '0 0 4 0',
	    labelAlign: 'left'
	});	

    var addSubmit = Ext.create('Ext.Button', {
        text: 'Run Automation',
        margin: '10 0 0 0',
        width: 130,
        style: 'background-color: #bcf1cb; color:black; border-color: #4cbb51',
        handler: function() {
	    	
			//Ext.getCmp('topRight').setLoading('Adding');
			
			//var data = [];
			//var myStore = Ext.getCmp('dbGrid').getStore();
			
			//myStore.each(function(rec)
			//{
			//   data.push(rec.data);
			//}
			//);
			Ext.getCmp("AddButton").handler.call(Ext.getCmp("AddButton").scope);
			
			//var newColumns = [];
			var myStore = Ext.data.StoreManager.get('dbStore').getRange()
			
			//console.log("mystore",myStore)
			
			var jsonArr = [];
			myStore.each( function (model) {
			    jsonArr.push(model.data);
			});
			
			//Ext.Object.each(myStore, function(key, value, self) 
			//{
			//	console.log("value",value)
			//	newColumns.push(
			//		{
			//			'ipaddr':value.raw.ipaddr,
			//			'devicename':value.raw.devicename,
			//		}
			//	)
			//});
			
			// These next two lines are pointless, but gives the 'feel' of the runbook starting  
			Ext.getCmp('dbPanel').setLoading('Executing Automation');
			setTimeout(() => { Ext.getCmp('dbPanel').setLoading(false); 
			
			console.log("jsonArr",jsonArr)
			Ext.Msg.alert('Success', 'Successfully executed runbook.  Please look to your email and the RITM for status.');
			Ext.Ajax.request({
		        url: '/resolve/service/runbook/execute',
		        timeout: 1800000,
		        params: {
		            WIKI: 'CIO_NORA_NETWORK.FirewallDecomm',
		            USERID: '$wikiUser.getUsername()',
		            PROBLEMID: 'NEW',
		            //TEST: Ext.getCmp('dbGrid').getStore().getRange(),
		            GRID: Ext.JSON.encode(jsonArr),
		            RITM: ritmNumber.getValue(),
		            EMAIL: emailAddress.getValue(),
		            VALTASK: taskCheckbox.getValue(),
		            
		            //ACTIONTYPE: 'ADD',
					//APPLICATIONNAME: applicationName.getValue(),
					//DESTINATIONIP: destinationIp.getValue(),
					//PORTNUMBER: portNumber.getValue(),
					//PROTOCOLTYPE: protocolType.getValue(),
					//ADDRESSSET: addressSet.getValue(),
					VERBOSE: true,
					AJAXCALL: true,
		            WSDATA_FLAG: true            
		        },
		        success: function(response, ops) {
		        	
					//Ext.getCmp('topRight').setLoading(false)
					
					//Ext.getCmp('applicationName').setValue("")
					//Ext.getCmp('destinationIp').setValue("")
					//Ext.getCmp('portNumber').setValue("")
					//Ext.getCmp('protocolType').setValue("")
					//Ext.getCmp('addressSet').setValue("")					
					
				   	//var searchTerm = Ext.getCmp('SearchBox').getValue()
					//populateTableSearch(searchTerm);
		
				//	iframePanel.contentWindow.location.reload();
					
					//window.open("https://resolveuat.fnfis.com:8443/resolve/service/wiki/view/CIO_NORA_NETWORK/FirewallDecommStatus")
		        },
		        failure: function(response, ops) {

		        	//Ext.Msg.alert('Error', 'Failure to get data from runbook.');
		        	//Ext.getCmp('topRight').setLoading(false)
		        //	iframePanel.contentWindow.location.reload();
		        }        
			});
				
			}, 1000);
			
        }
    });

	var topRightButtons = Ext.create('Ext.panel.Panel', {
		layout: 'hbox',
		items: [
			addSubmit,
			//clearButton
		]
	});
	
	var topRight = Ext.create('Ext.panel.Panel', {
		id: 'topRight',
	    width: 540,
	    height: 230,
	    margin: 15,
	    layout: {
		    type: 'vbox',
		    align: 'right'
		},
	    items: [
			ritmNumber,
			emailAddress,
			taskCheckbox,
	    	topRightButtons
	    ],
	    border: false,
	});


	var topPanel = Ext.create('Ext.panel.Panel', {
        xtype: 'panel',
        region: 'north',
        layout: 'hbox',
        defaults: {
            width: '100%',
            height: '100%',
        },
		items: [topLeft, topRight]
	});


    var dbStore = Ext.create('Ext.data.Store', {
        id: 'dbStore',
        fields: [{
        	name: 'SYSID',
        	mapping: 'sysid'
        }, {
            name: 'IPADDR',
            mapping: 'ipaddr'
        }, {
            name: 'DEVICENAME',
            mapping: 'devicename'
        }, {
            name: 'STATUS',
            mapping: 'status'
        }],
    });	
    
    var dbPanel = Ext.create('Ext.panel.Panel', {
        title: '',
        id: 'dbPanel',
        width: 1000,
        header: { height: 0 },
        layout: {
            type: 'vbox',
            align: 'stretch'
        },        
        style: { borderBottom: '1px solid #cccccc' },  
        items: [{
            xtype: 'grid',
            id: 'dbGrid',
            overflowY: 'auto',
            flex: 1,
            store: dbStore,
            columns: [{
                text: 'IP Address',
                dataIndex: 'IPADDR',
                flex: 2,
                renderer: colRenderer
            }, {
                text: 'Device Name',
                dataIndex: 'DEVICENAME',
                flex: 2,
                renderer: colRenderer
            }, {
                width: 65,
                height: 18,
                renderer: function(val, meta, rec) {
                	var sysidToDelete = rec.data.sysid
                	var value = '<input type="button" style="height:17px;font-size:10px;background-color: #bcf1cb; color:black; border-color: #88e28c" onClick="deleteEntry(\'' + meta.rowIndex + '\')"  value="delete">'
                	return value  
                }
            }]
        }],
		tbar: [{
            xtype: 'textfield',
            fieldLabel: 'IP Address',
            id: 'ipaddress',
            labelWidth: 80,
            margin: '2 10 2 2',
            width: 250,  
        }, {
            xtype: 'textfield',
            fieldLabel: 'Device CI',
            id: 'device',
            labelWidth: 80,
            margin: 2,
            width: 250,
            listeners : {
                'render' : function(cmp) 
                {
                    cmp.getEl().on('keypress', function(e) 
                    {
                        if (e.getKey() == e.ENTER) 
                        {
							Ext.getCmp("AddButton").handler.call(Ext.getCmp("AddButton").scope);
							Ext.getCmp('ipaddress').focus(false, 200);
                        }
                    });
                }
            }
        }, {
            xtype: 'button',
            width: 60,
            text: 'Add',
            id: 'AddButton',
            margin: '2 2 2 2',
            style: 'background-color: #bcf1cb; color:black; border-color: #4cbb51',
            handler: function() { 
            	if(Ext.getCmp('ipaddress').getValue())
            	{
	            	dbStore.insert(0, {SYSID:sysidInc.toString(), IPADDR: Ext.getCmp('ipaddress').getValue(), DEVICENAME: Ext.getCmp('device').getValue(), STATUS: 'UNPROCESSED'});
	            	dbStore.sync()
	            	
	            	sysidInc = sysidInc + 1
            	}
            	Ext.getCmp('ipaddress').setValue("")
            	Ext.getCmp('device').setValue("")
            }
        }]
    });

	
	var bottomPanel = Ext.create('Ext.panel.Panel', {
		id: 'bottomPanel',
        xtype: 'panel',
		region: 'south',
        layout: 'hbox',
        defaults: {
            width: '100%',
            height: 690
        },        
		items: [dbPanel]
	});

    Ext.create('Ext.container.Viewport', {
        items: [topPanel, bottomPanel]
    });

});


function copyEntryAbove(rowIdx) {
	
	var rec = Ext.getCmp('dbGrid').getStore().getAt(rowIdx);

	Ext.getCmp('applicationName').setValue(rec.data.appname)
	Ext.getCmp('destinationIp').setValue(rec.data.destip)
	Ext.getCmp('portNumber').setValue(rec.data.portnum)
	Ext.getCmp('protocolType').setValue(rec.data.porttype)
	Ext.getCmp('addressSet').setValue(rec.data.addrset)	
}


function deleteEntry(rowIdx) {
	
//	console.log(rowIdx)
	var store = Ext.getCmp('dbGrid').getStore();
	store.removeAt(rowIdx);
	store.sync()
	
}


function populateTable() {
	
	var dbStoreData = [];
	Ext.getCmp('dbPanel').setLoading('Loading.');
	
	Ext.Ajax.request({
        url: '/resolve/service/runbook/execute',
        timeout: 1800000,
        params: {
            WIKI: 'CIO_NORA_NETWORK.Firewall_MatchApplication_Wiki',
            USERID: '$wikiUser.getUsername()',
            PROBLEMID: 'NEW',
            ACTIONTYPE: 'POPULATE',
            VERBOSE: true,
            AJAXCALL: true,
            WSDATA_FLAG: true            
        },
        success: function(response, ops) {
            var response = Ext.JSON.decode(response.responseText, true);
			var wsData = response.WSDATA
			
			var dbData = wsData.dbData
			for(var i = 0, len = dbData.length; i < len; i++)
			{
	            dbStoreData.push({
	            	sysid:    dbData[i].sys_id,
	                appname:  dbData[i].u_application,
	                destip:   dbData[i].u_dest_ip,
	                portnum:  dbData[i].u_dest_port,
	                porttype: dbData[i].u_protocol,
	                addrset:  dbData[i].u_addr_set
	            })
			}			

       		Ext.data.StoreManager.lookup('dbStore').loadData(dbStoreData,false); 

			Ext.getCmp('dbPanel').setLoading(false)
        },
        failure: function(response, ops) {
        	console.log(response)
        	Ext.Msg.alert('Error', 'Failure to get data from runbook.');

        	Ext.getCmp('dbPanel').setLoading(false)
        }        
	});
}


function populateTableSearch(searchTerm) {
	
	var dbStoreData = [];
	Ext.getCmp('dbPanel').setLoading('Loading.');
	
	Ext.Ajax.request({
        url: '/resolve/service/runbook/execute',
        timeout: 1800000,
        params: {
            WIKI: 'CIO_NORA_NETWORK.Firewall_MatchApplication_Wiki',
            USERID: '$wikiUser.getUsername()',
            PROBLEMID: 'NEW',
            ACTIONTYPE: 'POPULATE',
            SEARCHTERM: searchTerm,
            VERBOSE: true,
            AJAXCALL: true,
            WSDATA_FLAG: true            
        },
        success: function(response, ops) {
            var response = Ext.JSON.decode(response.responseText, true);
			var wsData = response.WSDATA
			
			var cntr = 0
			var dbData = wsData.dbData
			for(var i = 0, len = dbData.length; i < len; i++)
			{
				if(typeof dbData[i].u_application !== 'undefined')
				{
		            dbStoreData.push({
		            	sysid:    dbData[i].sys_id,
		                appname:  dbData[i].u_application,
		                destip:   dbData[i].u_dest_ip,
		                portnum:  dbData[i].u_dest_port,
		                porttype: dbData[i].u_protocol,
		                addrset:  dbData[i].u_addr_set
		            })
		            cntr++;
				}
			}			
			
			if(cntr == 0)
			{
				Ext.Msg.alert('Error', 'No matching entries.');
			}
			
       		Ext.data.StoreManager.lookup('dbStore').loadData(dbStoreData,false); 

			Ext.getCmp('dbPanel').setLoading(false)
        },
        failure: function(response, ops) {
        	console.log(response)
        	Ext.Msg.alert('Error', 'Dataset too large, refine your search.');

        	Ext.getCmp('dbPanel').setLoading(false)
        }        
	});
}


function colRenderer(currentValue, metaData, record, rowIdx, colIdx, store) {

    var value     = currentValue;
    var column    = this.columns[colIdx];
    var metrics   = Ext.create('Ext.util.TextMetrics');
    var textWidth = metrics.getWidth(value);
    var colWidth  = column.getWidth();

    if(textWidth >= colWidth) { metaData.tdAttr = 'data-qtip="' + value + '"'; }

    if(column.dataIndex == 'severity')
    {
        value = severityText[currentValue] || currentValue;
    }

    return value;
}


//
// wont be using this, but dont want to remove it.
// example of csv -> store
/*
function populateTableNoExe() {
	
	var dbStoreData = [];
	Ext.getCmp('dbPanel').setLoading('Loading');

	Ext.Ajax.request({
		method: 'GET',
		url: '/resolve/service/worksheet/getActive',
		success: function(response) {
       	
            var response = Ext.JSON.decode(response.responseText, true);
			var wsData = response.WSDATA
			
			console.log(response)

			var thePageSize
			var file = "/resolve/doc/FWMatchApplication1.csv"
			
			var rawFile = new XMLHttpRequest();
			rawFile.open("GET", file, true);
			rawFile.onreadystatechange = function ()
			{
				if(rawFile.readyState === 4)
				{
					if(rawFile.status === 200 || rawFile.status == 0)
					{
						var allText = rawFile.responseText;
						allText = allText.split('</br>')
						thePageSize = allText.length
						
						for(var i = 0, len = allText.length; i < len; i++)
						{
							
							var tmp = allText[i].split(',')
							
				            dbStoreData.push({
				            	sysid:    tmp[0],
				                appname:  tmp[1],
				                destip:   tmp[2],
				                portnum:  tmp[3],
				                porttype: tmp[4],
				                addrset:  tmp[5]
				            })
						}

			            //Ext.getStore('dbStore').reload({start:0, limit: thePageSize});
						//Ext.apply(Ext.getStore('dbStore'), {pageSize: thePageSize});
						
		        		this.doSmallWait = new Ext.util.DelayedTask(function(){
			        		Ext.data.StoreManager.lookup('dbStore').loadData(dbStoreData,false); 
		        		}, this);
		        		this.doSmallWait.delay(500);			            

						// Manually update the paging toolbar.
						//Ext.getStore('dbStore').totalCount = thePageSize;
						//me.pagingToolbar.onLoad();
						
			            Ext.getCmp('dbPanel').setLoading(false)							
					}
				}
			}
			rawFile.send(null);
            
        }      
	});
}
