package at.ac.tuwien.ifs.qse.tdd.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;

import at.ac.tuwien.ifs.qse.tdd.dialog.TddErrorDialog;
import at.ac.tuwien.ifs.qse.tdd.exception.SearchException;
import at.ac.tuwien.ifs.qse.tdd.finder.TestFinder;
import at.ac.tuwien.ifs.qse.tdd.finder.TestFinder.FILETYPE;

/**
 * Handler for the jumoToTestCommand
 */
public class JumpToTest extends TddFileHandler {

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		final ICompilationUnit unit = getCompilationUnit();		
		
		if(unit == null)
			return null;
		
		IType type = null;
		TestFinder finder = new TestFinder();
		String searchName = "";
		
		try {
			if(finder.getTypeOfSearchName(unit.getElementName()).equals(TestFinder.FILETYPE.CLASS)) {
				searchName = finder.buildTestClassName(unit.getElementName());
			} else {
				searchName = finder.buildClassName(unit.getElementName());
			}
			
			type = finder.search(searchName,unit.getCorrespondingResource().getProject());
			
			if(type == null){
				handleException(null, searchName);
			}		
		
			JavaUI.openInEditor(type, true, true);
			
		} catch (JavaModelException e) {
			return null;
		} catch (SearchException e) {
			handleException(e, unit.getElementName());
			return null;
		} catch (PartInitException e) {
			return null;
		} 
	
		return null;
	
	}

	public void handleException(final SearchException exc,final String fileName){
		TestFinder finder = new TestFinder();
		final FILETYPE type = finder.getTypeOfSearchName(fileName);
		Display.getDefault().asyncExec(new Runnable() {
		     public void run() {
		    	 Dialog dialog = null;
		    	 dialog = new TddErrorDialog(Display.getDefault().getActiveShell(),fileName,type);
		  		 dialog.open();
			 }
		}); 
	}

	
	
}
