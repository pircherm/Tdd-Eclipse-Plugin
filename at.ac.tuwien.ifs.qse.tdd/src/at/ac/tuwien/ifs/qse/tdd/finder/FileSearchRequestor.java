package at.ac.tuwien.ifs.qse.tdd.finder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchRequestor;

/**
 * 
 * The Requestor collects the founded class files from type IType. The list of the result can be read
 * with getTypeList.
 */
public class FileSearchRequestor extends SearchRequestor {

	
  private List<IType> typeList = new ArrayList<IType>();

  
  @Override
  public void acceptSearchMatch(SearchMatch match) throws CoreException {
    if (match.getElement() instanceof IType) {
      IType type = (IType) match.getElement();
      typeList.add(type);
    }
  }

  /**
   * Get the results of the search
   * @return the result list
   */
  public List<IType> getTypeList() {
    return typeList;
  }

  public void setTypeList(List<IType>  typeList) {
    this.typeList = typeList;
  }

}
