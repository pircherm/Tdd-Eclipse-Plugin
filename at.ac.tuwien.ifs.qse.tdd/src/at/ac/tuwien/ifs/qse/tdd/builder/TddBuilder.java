package at.ac.tuwien.ifs.qse.tdd.builder;


import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;

import at.ac.tuwien.ifs.qse.tdd.Activator;
import at.ac.tuwien.ifs.qse.tdd.dialog.TddErrorDialog;
import at.ac.tuwien.ifs.qse.tdd.exception.IHandleException;
import at.ac.tuwien.ifs.qse.tdd.exception.NoTestFound;
import at.ac.tuwien.ifs.qse.tdd.exception.SearchException;
import at.ac.tuwien.ifs.qse.tdd.finder.CoverageBuilderVisitor;
import at.ac.tuwien.ifs.qse.tdd.finder.CoverageExecuter;
import at.ac.tuwien.ifs.qse.tdd.finder.TestFinder;
import at.ac.tuwien.ifs.qse.tdd.finder.TestFinder.FILETYPE;
import at.ac.tuwien.ifs.qse.tdd.preferences.PreferenceConstants;


/**
 *	This is the tdd builder which will be called when the project will be build.
 *  The plugin react to incremental build and to full build. </p>  
 */
public class TddBuilder extends IncrementalProjectBuilder implements IHandleException{

	public static final String BUILDER_ID = "at.ac.tuwien.ifs.qse.tdd.tddBuilder";
	
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)throws CoreException {
		
		String executeOn = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_EXECUTEON);
		if(executeOn == null)
			return null;


		if (kind == FULL_BUILD) {
			if(executeOn.equals(PreferenceConstants.P_EXECUTEON_ALL) ||executeOn.equals(PreferenceConstants.P_EXECUTEON_BUILD)){

			}
		} else {
			if(executeOn.equals(PreferenceConstants.P_EXECUTEON_ALL) ||executeOn.equals(PreferenceConstants.P_EXECUTEON_INC)){
				IResourceDelta delta = getDelta(getProject());
				if (delta == null) {

				} else {
					incrementalBuild(delta, monitor);
				}
			}
		}
		return null;
	}

		
	/**
	 * Start point of the Tdd Plugin
	 * 	
	 * </p>
	 * <ul>
	 * <li>Find the TestClass</li>
	 * <li>Execute the coverage analyse</li>
	 * </ul> 
	 * @param delta contains the changed Resources
	 * @param monitor
	 * @throws CoreException
	 */
	protected void incrementalBuild(IResourceDelta delta,IProgressMonitor monitor) throws CoreException {
		
		
		CoverageBuilderVisitor coverageVisitor = new CoverageBuilderVisitor();
	    delta.accept(coverageVisitor);
	    

	    if (coverageVisitor.getFileList().size() != 1) {
	      return;
	    }
	   	    
	    TestFinder finder = new TestFinder();
	    CoverageExecuter executer = new CoverageExecuter();
	    

	    IFile file = (IFile) coverageVisitor.getFileList().get(0);

	    String fileName= null;
	    try {
	    	fileName = file.getName();
	    	
	    	if(finder.getTypeOfSearchName(fileName).equals(FILETYPE.TESTCLASS)){
	    		return;
	    	}
	    	
	    	//Get TestName
	    	String testName = finder.buildTestClassName(file.getName());
			//Search the associated TestFile
	    	IType type = finder.search(testName, delta.getResource().getProject());
	    	
	    	//execute the coverage
	    	executer.executeFileCoverage(type);
	    } catch (SearchException e) {
			handleException(e,fileName);
		}  

	}
	
	@Override
	public void handleException(final SearchException exc,final String fileName){
	
		Display.getDefault().asyncExec(new Runnable() {
		     public void run() {
		    	  Dialog dialog = null;
		    	  if (exc instanceof NoTestFound) {
		  			dialog = new TddErrorDialog(Display.getDefault().getActiveShell(),fileName,FILETYPE.TESTCLASS);
		  			dialog.open();
			  	  } else {
			  			
			  	  }
		     }
		}); 
	}
	
}
