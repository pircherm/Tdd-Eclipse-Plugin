package at.ac.tuwien.ifs.qse.tdd.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import at.ac.tuwien.ifs.qse.tdd.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_SCOPE,PreferenceConstants.P_SCOPE_PROJECT);
		store.setDefault(PreferenceConstants.P_PREFIX,"Test");
		store.setDefault(PreferenceConstants.P_SUFFIX,"");
		store.setDefault(PreferenceConstants.P_EXECUTEON,PreferenceConstants.P_EXECUTEON_INC);
		store.setDefault(PreferenceConstants.P_SHOW_WARNING,true);
	}

}
