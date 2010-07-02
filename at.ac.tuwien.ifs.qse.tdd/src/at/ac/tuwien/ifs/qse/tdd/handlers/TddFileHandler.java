package at.ac.tuwien.ifs.qse.tdd.handlers;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

import at.ac.tuwien.ifs.qse.tdd.exception.IHandleException;
import at.ac.tuwien.ifs.qse.tdd.exception.SearchException;

/**
 * 
 * Abstract class which extracts the ICompilationUnit from the selected source.
 * </p>
 * The following functions have to be implemented:
 * </p>
 * <ul>
 * <li>{@link IHandleException#handleException(SearchException,String)}</li>
 * <li>{@link AbstractHandler#execute(ExecutionEvent)}</li>
 * </ul> 
 */
public abstract class TddFileHandler extends AbstractHandler implements IElementUpdater,IHandleException {

	private ISelectionService service = null;
	private UIElement element;

	@SuppressWarnings("unchecked")
	@Override
	public void updateElement(UIElement element, Map parameters) {
		service  = (ISelectionService)element.getServiceLocator().getService(ISelectionService.class);	
		this.element = element;
	}

	/**
	 * Get the ISelectionService set by the function {@link TddFileHandler#updateElement(UIElement,Map)}
	 * @return ISelectionService
	 */
	public ISelectionService getService(){
		return service;
	}
		
	/**
	 * Get the selected UIElement
	 * @return UIElement
	 */
	public UIElement getElement() {
		return element;
	}

	/**
	 * Extract the compilation unit from the current selection
	 * @return ICompilationUnit
	 */
	@SuppressWarnings("unchecked")
	public ICompilationUnit getCompilationUnit(){
		
		
		if(service == null)
			return null;
		
		ICompilationUnit unit = null;

		//Check if selection is from ICompilationUnit
		if(service != null) {
			ISelection selection = service.getSelection();
			if (selection instanceof TreeSelection) {
				for (Iterator it = ((TreeSelection) selection).iterator(); it.hasNext();) {
					Object el = it.next();
					if (el instanceof ICompilationUnit) {
						unit = (ICompilationUnit) el;
					} 
				}
			}
		}

		if(unit == null) {

			IWorkbench workbench = PlatformUI.getWorkbench(); 
			if (workbench == null) 
				return null;

			IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow(); 
			IWorkbenchPage page = activeWorkbenchWindow.getActivePage();

			if(page.getActiveEditor() == null || page.getActiveEditor().getEditorInput() == null) {
				return null;
			}
			IJavaElement element = JavaUI.getEditorInputJavaElement(page.getActiveEditor().getEditorInput());

			if (element instanceof ICompilationUnit) {
				unit = (ICompilationUnit) element;
			} else {
				return null;
			}
		}

		return unit;

	}









}
