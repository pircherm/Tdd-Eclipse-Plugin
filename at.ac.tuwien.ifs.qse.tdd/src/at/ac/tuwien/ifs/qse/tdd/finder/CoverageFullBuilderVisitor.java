package at.ac.tuwien.ifs.qse.tdd.finder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * This visitor collects the found source files witch ends with .java <br>
 * The collected IFiles can be taken with the function getFileList
 */
public class CoverageFullBuilderVisitor implements IResourceVisitor {

	/**
	 * The collected IFiles
	 */
  private List<IFile> fileList = new ArrayList<IFile>();

  /**
   * This function will be called for every found ressource
   */
  public boolean visit(IResource resource) throws CoreException {
    
   
    if (resource instanceof IFile && resource.getName().endsWith(".java")) { 
      IFile file = (IFile) resource;
      fileList.add(file);
      return false; //Abort the search of other visits
    }
    return true;
  }

  /**
   * Get the collected IFiles
   * @return
   */
  public List<IFile> getFileList() {
    return fileList;
  }

}
