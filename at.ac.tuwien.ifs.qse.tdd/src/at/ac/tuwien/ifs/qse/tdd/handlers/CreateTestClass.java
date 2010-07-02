package at.ac.tuwien.ifs.qse.tdd.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.ui.actions.OpenNewClassWizardAction;
import org.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import at.ac.tuwien.ifs.qse.tdd.exception.SearchException;
import at.ac.tuwien.ifs.qse.tdd.finder.TestFinder;
import at.ac.tuwien.ifs.qse.tdd.wizard.TddTestCaseWizard;


/**
 * Handler for the createTestClassCommand
 */
public class CreateTestClass extends TddFileHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		final ICompilationUnit unit = getCompilationUnit();
		
		if(unit == null)
			return null;
		
		TestFinder finder = new TestFinder();
		
		if(finder.getTypeOfSearchName(unit.getElementName()).equals(TestFinder.FILETYPE.CLASS)) {
			createTestClass(finder.buildTestClassName(unit.getElementName()));
		} else {
			createClass(finder.buildClassName(unit.getElementName()));
		}
		
		return null;
	}

	
	private void createTestClass(String fileName){
		TddTestCaseWizard wizard = new TddTestCaseWizard(fileName);
		if (getService().getSelection() instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) getService().getSelection();
			wizard.init(null, sel);
		}
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		dialog.open();
	
	}

	private void createClass(String fileName){
		OpenNewClassWizardAction action = new OpenNewClassWizardAction();
		NewClassWizardPage page = new NewClassWizardPage();
		if (getService().getSelection() instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) getService().getSelection();
			page.init(sel);
		} else {
			page.init(null);
		}		
		page.setTypeName(fileName, true);	
		action.setConfiguredWizardPage(page);
		action.run();	
	}

	@Override
	public void handleException(SearchException exc, String fileName) {
				
	}



}
