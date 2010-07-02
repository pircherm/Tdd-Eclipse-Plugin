package at.ac.tuwien.ifs.qse.tdd.finder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mountainminds.eclemma.ui.launching.CoverageLaunchShortcut;

/**
 * The coverage executer launch the coverage analysis. He access the api of eclEmma. 
 */
public class CoverageExecuter {

	public static final String SHORT_CUT = "org.eclipse.jdt.junit.junitShortcut";
	public static final String LAUNCH_MODE = "coverage";
	
	/**
	 * Starts coverage for the IType.
	 * @param lwType should include a class file which is a Junit test.
	 */
	public void executeFileCoverage(final IType type) {
	    
		Display.getDefault().asyncExec(new Runnable() {
	      
	      public void run() {
	       	try {
	          ICompilationUnit lwCompilationUnit = type.getCompilationUnit();

	          CoverageLaunchShortcut launchShortcut = new CoverageLaunchShortcut();
	          //Define which shortcut should be used
	          launchShortcut.setInitializationData(null, null,SHORT_CUT); //$NON-NLS-1$
	          
	          ISelection selection = new StructuredSelection(lwCompilationUnit);
	          
	          //Launch the coverage
	          launchShortcut.launch(selection, LAUNCH_MODE); //$NON-NLS-1$
	        } catch (CoreException ex) {
	        	
	        } 
	      }
	    }); 

	  }
	 
	/**
	 * Start the coverage for the currently selected Editor
	 */
	public void executeEditorCoverage() {
		   

		    Display.getDefault().asyncExec(new Runnable() {
		      /*
		       * (non-Javadoc)
		       * 
		       * @see java.lang.Runnable#run()
		       */
		      public void run() {
		        IWorkbenchWindow iWorkbenchWindow = PlatformUI.getWorkbench()
		            .getActiveWorkbenchWindow();
		        if (iWorkbenchWindow == null) {
		          return;
		        }
		        IWorkbenchPage iworkbenchpage = iWorkbenchWindow.getActivePage();
		        if (iworkbenchpage == null) {
		          return;
		        }
		        IEditorPart iEditorpart = iworkbenchpage.getActiveEditor();

		        CoverageLaunchShortcut launchShortcut = new CoverageLaunchShortcut();
		        try {
		          launchShortcut.setInitializationData(null, null, "org.eclipse.jdt.junit.junitShortcut"); //$NON-NLS-1$
		        } catch (CoreException e) {
		          
		        }
		        launchShortcut.launch(iEditorpart, "coverage");
		       
		      }
		    });
		    
		  }
	
}
