////////////
// VIP Decommission Automation Front Page (ExtJS)
////////////

document.body.innerHTML = "";

Ext.onReady(function() {

    var uploadedFileContent = '';

    var topLeft = Ext.create('Ext.panel.Panel', {
        border: false,
        bodyPadding: 10,
        html: [
            '<div style="text-align: center; margin-bottom: 10px">',
            '<p style="font-family: Helvetica; font-size: 20px; font-weight: bold; margin: 0;">',
            'VIP Decommission Automation',
            '</p>',
            '<ul style="font-family: Helvetica; font-size: 12px; text-align: left; margin: 10px auto 0 auto; max-width: 600px;">',
            '<li>Fill out all fields and upload the Phase 1 script (.txt)</li>',
            '<li><a href="mailto:nss_automation@fisglobal.com">Contact us</a> with questions.</li>',
            '</ul>',
            '</div>'
        ].join(' ')
    });

    function createTextField(id, label) {
        return Ext.create('Ext.form.field.Text', {
            id: id,
            fieldLabel: label,
            labelWidth: 180,
            width: 500,
            labelStyle: 'font-family: Helvetica; font-size: 13px;',
            margin: '10 0 8 0'
        });
    }

    var emailField = createTextField('userEmail', 'Your Email');
    var ritmField = createTextField('ritmNumber', 'RITM Number');
    var vipNameField = createTextField('vipNames', 'VIP Name(s) (comma-separated)');
    var deviceNamesField = createTextField('deviceNames', 'Device Name(s)');
    var backendServerField = createTextField('backendServers', 'Backend Server(s)');
    var routePrefixField = createTextField('routePrefix', 'Route and Prefix List');

    var phase1FileInput = Ext.create('Ext.form.field.File', {
        id: 'phase1Script',
        fieldLabel: 'Phase 1 Script (.txt)',
        labelWidth: 180,
        width: 500,
        labelStyle: 'font-family: Helvetica; font-size: 13px;',
        margin: '10 0 8 0',
        buttonText: 'Browse...'
    });

    var deleteFileBtn = Ext.create('Ext.Button', {
        text: 'Remove File',
        hidden: true,
        margin: '0 0 10 0',
        handler: function() {
            uploadedFileContent = '';
            fileContentDisplay.setValue('');
            phase1FileInput.reset();
            deleteFileBtn.hide();
        }
    });

    var fileContentDisplay = Ext.create('Ext.form.field.TextArea', {
        id: 'fileDisplay',
        fieldLabel: 'Script Preview',
        labelAlign: 'top',
        width: 500,
        height: 440,
        readOnly: true,
        scrollable: true,
        style: 'font-family: Courier New; font-size: 12px;',
        margin: '10',
        autoScroll: true
    });

    phase1FileInput.on('change', function(fileInput, value, eOpts) {
        var file = fileInput.fileInputEl.dom.files[0];
        if (file && file.name.endsWith('.txt')) {
            var reader = new FileReader();
            reader.onload = function(e) {
                uploadedFileContent = e.target.result;
                fileContentDisplay.setValue(uploadedFileContent);
                deleteFileBtn.show();
            };
            reader.readAsText(file);
        } else {
            uploadedFileContent = '';
            fileContentDisplay.setValue('Only .txt files are supported.');
            deleteFileBtn.hide();
        }
    });

    var runAutomationBtn = Ext.create('Ext.Button', {
        text: 'Run Automation',
        width: 180,
        style: 'background-color: #4caf50; color:white; border: none; font-weight: bold;',
        margin: '10 0 10 0',
        handler: function() {
            var vipList = vipNameField.getValue().split(',').map(s => s.trim()).filter(s => s);
            if (vipList.length > 25) {
                Ext.Msg.alert('Validation Error', 'You can only enter up to 25 VIPs.');
                return;
            }

            var dataPayload = {
                VIP_NAMES: vipNameField.getValue(),
                DEVICE_NAMES: deviceNamesField.getValue(),
                BACKEND_SERVERS: backendServerField.getValue(),
                ROUTE_PREFIX_LIST: routePrefixField.getValue(),
                PHASE1_SCRIPT: uploadedFileContent,
                EMAIL: emailField.getValue(),
                RITM: ritmField.getValue(),
                VERBOSE: true,
                AJAXCALL: true,
                WSDATA_FLAG: true
            };

            Ext.getBody().mask('Executing Automation...');

            Ext.Ajax.request({
                url: '/resolve/service/runbook/execute',
                timeout: 1800000,
                params: Object.assign({
                    WIKI: 'CIO_NORA_NETWORK.LB_VIP_DECOM_PRE_REVIEW',
                    USERID: '$wikiUser.getUsername()',
                    PROBLEMID: 'NEW'
                }, dataPayload),
                success: function(response, opts) {
                    Ext.getBody().unmask();
                    Ext.Msg.alert('Success', 'Successfully executed runbook. Please check your email or status dashboard.');
                },
                failure: function(response, opts) {
                    Ext.getBody().unmask();
                    Ext.Msg.alert('Failure', 'Runbook execution failed. Please try again or contact support.');
                }
            });
        }
    });

    var leftPanel = Ext.create('Ext.panel.Panel', {
        width: 550,
        layout: 'vbox',
        padding: 20,
        items: [
            emailField,
            ritmField,
            vipNameField,
            deviceNamesField,
            backendServerField,
            routePrefixField,
            phase1FileInput,
            deleteFileBtn,
            runAutomationBtn
        ]
    });

    var mainPanel = Ext.create('Ext.panel.Panel', {
        layout: 'hbox',
        scrollable: true,
        bodyPadding: 10,
        items: [leftPanel, fileContentDisplay]
    });

    Ext.create('Ext.container.Viewport', {
        layout: {
            type: 'vbox',
            align: 'center',
            pack: 'start'
        },
        scrollable: true,
        items: [topLeft, mainPanel]
    });
});
