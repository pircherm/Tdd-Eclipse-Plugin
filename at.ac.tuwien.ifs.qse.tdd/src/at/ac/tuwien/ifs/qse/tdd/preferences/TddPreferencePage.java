package at.ac.tuwien.ifs.qse.tdd.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferenceLinkArea;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;


import at.ac.tuwien.ifs.qse.tdd.Activator;


public class TddPreferencePage	extends FieldEditorPreferencePage 	implements IWorkbenchPreferencePage {
	
	private static final String CODE_COVERAGE_PAGE = "com.mountainminds.eclemma.ui.preferencePages.CoverageGeneral";
	private static final String JUNIT_COVERAGE_PAGE = "org.eclipse.jdt.junit.preferences";

	public TddPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("General settings for the Tdd-Plugin:");
	}
	
	
	public void createFieldEditors() {
		//Scope		
		addField(new ComboFieldEditor(PreferenceConstants.P_SCOPE, "&Search scope", 
				new String[][] { { "Project", PreferenceConstants.P_SCOPE_PROJECT }, 
				{"Workspace", PreferenceConstants.P_SCOPE_WORKSPACE }/*, {"Directory", PreferenceConstants.P_SCOPE_DIRECTORY}*/}
				,getFieldEditorParent()));
		
		//TestName conventions
		StringFieldEditor editorPrefix = new StringFieldEditor(PreferenceConstants.P_PREFIX, "Test &prefix:", getFieldEditorParent());
		StringFieldEditor editorSuffix = new StringFieldEditor(PreferenceConstants.P_SUFFIX, "Test &suffix:", getFieldEditorParent()); 
				
		addField(editorPrefix);
		addField(editorSuffix);
		
		addSpacer(getFieldEditorParent());
		//Generate Wizard
		addField(new BooleanFieldEditor(PreferenceConstants.P_SHOW_WARNING,"Show class not found warnings",getFieldEditorParent()));
			
		addSpacer(getFieldEditorParent());
		//Execute on
		addField(new RadioGroupFieldEditor(PreferenceConstants.P_EXECUTEON,
			"Execute on:",1,
			new String[][] { { "&Incremental build", PreferenceConstants.P_EXECUTEON_INC }, {"&Build all",PreferenceConstants.P_EXECUTEON_BUILD
				},{"&All", PreferenceConstants.P_EXECUTEON_ALL }
			}, getFieldEditorParent()));
		
		
		//Code coverage link
		addSpacer(getFieldEditorParent());
		addLink(getFieldEditorParent(), "See <a>EclEmma-Plugin</a> for coverage settings.",CODE_COVERAGE_PAGE);
		addLink(getFieldEditorParent(), "See <a>JUnit-Plugin</a> for test settings.",JUNIT_COVERAGE_PAGE);
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}


	private void addSpacer(final Composite parent) {
		addLabel(parent, ""); 
	}

	private void addLabel(final Composite parent, final String text) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		final GridData gd = new GridData();
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);
	}

	private void addLink(final Composite parent, final String text,   String target) {
		final PreferenceLinkArea link = new PreferenceLinkArea(parent, SWT.NONE,target, text, (IWorkbenchPreferenceContainer) getContainer(), null);
		final GridData gd = new GridData();
		gd.horizontalSpan = 2;
		link.getControl().setLayoutData(gd);
	}

}