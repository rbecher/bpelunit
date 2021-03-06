package org.bpelunit.framework.client.eclipse.dialog.field;

import org.bpelunit.framework.control.ext.IBPELDeployer;
import org.bpelunit.framework.control.util.ExtensionRegistry;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

public class DeployerOptionModifyListener implements ModifyListener {

	private SelectionField fKeyField;
	private TextField fValueField;
	private Class<? extends IBPELDeployer> fDeployerClass;

	public DeployerOptionModifyListener(SelectionField keyField,
			TextField valueField, Class<? extends IBPELDeployer> deployerClass) {
		super();
		this.fKeyField = keyField;
		this.fValueField = valueField;
		this.fDeployerClass = deployerClass;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		String currentValue = fValueField.getSelection();
		if (currentValue == null || "".equals(currentValue)) {

			String key = fKeyField.getSelection();
			String value = ExtensionRegistry.getDefaultValueFor(fDeployerClass,
					key);

			fValueField.setText(value);
		}
	}

}
