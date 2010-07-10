package at.ac.tuwien.ifs.qse.tdd.wizard;

import org.eclipse.jdt.junit.wizards.NewTestCaseWizardPageOne;
import org.eclipse.jdt.junit.wizards.NewTestCaseWizardPageTwo;
import org.eclipse.swt.widgets.Composite;

public class TddNewTestCaseWizardPageOne extends NewTestCaseWizardPageOne {

	private String typeName;
	
	public TddNewTestCaseWizardPageOne(NewTestCaseWizardPageTwo page2,String typeName) {
		super(page2);
		this.typeName = typeName;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		this.setTypeName(typeName, true);
	}
	
	
	
	
}
