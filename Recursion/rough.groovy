////////////
// VIP Decommission Automation Front Page (ExtJS)
//
// Mirrors the layout and structure of Troy Lee's Firewall Decomm page
// Fields: VIP Name, Device Names, Backend Server, Route and Prefix List, Phase 1 Script
////////////

document.body.innerHTML = "";

Ext.onReady(function() {

    var sysidInc = 1;

    var topLeft = Ext.create('Ext.panel.Panel', {
        width: 350,
        height: 230,
        margin: '15 30 15 15',
        border: false,
        html: [
            '<img style="width:120px;height:60px" src="https://paymentweek.com/wp-content/uploads/2015/06/fis.png">',
            '<p style="font-family: Helvetica; font-size: 18px">',
            'VIP Decommission Automation',
            '</p>',
            '<ul style="font-family: Helvetica; font-size: 12px">',
            '<li>Fill out all fields and upload the Phase 1 script (.txt)</li>',
            '<li><a href="mailto:nss_automation@fisglobal.com">Contact us</a> with questions.</li>',
            '</ul>'
        ].join(' ')
    });

    function createTextField(id, label) {
        return Ext.create('Ext.form.field.Text', {
            id: id,
            fieldLabel: label,
            labelWidth: 150,
            width: 450,
            labelStyle: 'font-family: Helvetica; font-size: 12px;',
            margin: '10 0 4 0'
        });
    }

    var vipNameField = createTextField('vipNames', 'VIP Name(s) (comma-separated)');
    var deviceNamesField = createTextField('deviceNames', 'Device Name(s)');
    var backendServerField = createTextField('backendServers', 'Backend Server(s)');
    var routePrefixField = createTextField('routePrefix', 'Route and Prefix List');

    var phase1FileInput = Ext.create('Ext.form.field.File', {
        id: 'phase1Script',
        fieldLabel: 'Phase 1 Script (.txt)',
        labelWidth: 150,
        width: 450,
        labelStyle: 'font-family: Helvetica; font-size: 12px;',
        margin: '10 0 4 0',
        buttonText: 'Browse...'
    });

    var fileContentDisplay = Ext.create('Ext.form.field.TextArea', {
        id: 'fileDisplay',
        fieldLabel: 'Script Preview',
        labelAlign: 'top',
        width: 450,
        height: 150,
        readOnly: true,
        margin: '10 0 4 0'
    });

    phase1FileInput.on('change', function(fileInput, value, eOpts) {
        var file = fileInput.fileInputEl.dom.files[0];
        if (file && file.name.endsWith('.txt')) {
            var reader = new FileReader();
            reader.onload = function(e) {
                fileContentDisplay.setValue(e.target.result);
            };
            reader.readAsText(file);
        } else {
            fileContentDisplay.setValue('Only .txt files are supported.');
        }
    });

    var runAutomationBtn = Ext.create('Ext.Button', {
        text: 'Run Automation',
        width: 130,
        style: 'background-color: #bcf1cb; color:black; border-color: #4cbb51',
        handler: function() {
            var vipList = vipNameField.getValue().split(',').map(s => s.trim()).filter(s => s);
            if (vipList.length > 25) {
                Ext.Msg.alert('Validation Error', 'You can only enter up to 25 VIPs.');
                return;
            }

            Ext.Msg.alert('Submitted', 'Automation triggered successfully. Data will be processed.');
            console.log({
                vipNames: vipList,
                deviceNames: deviceNamesField.getValue(),
                backendServers: backendServerField.getValue(),
                routePrefix: routePrefixField.getValue(),
                scriptText: fileContentDisplay.getValue()
            });
        }
    });

    var topRight = Ext.create('Ext.panel.Panel', {
        width: 540,
        height: 340,
        margin: 15,
        layout: 'vbox',
        items: [
            vipNameField,
            deviceNamesField,
            backendServerField,
            routePrefixField,
            phase1FileInput,
            fileContentDisplay,
            runAutomationBtn
        ],
        border: false
    });

    var topPanel = Ext.create('Ext.panel.Panel', {
        xtype: 'panel',
        region: 'north',
        layout: 'hbox',
        items: [topLeft, topRight]
    });

    Ext.create('Ext.container.Viewport', {
        layout: 'fit',
        items: [topPanel]
    });
});
