package at.ac.tuwien.ifs.qse.tdd.dialog;

import org.eclipse.jdt.ui.actions.OpenNewClassWizardAction;
import org.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import at.ac.tuwien.ifs.qse.tdd.Activator;
import at.ac.tuwien.ifs.qse.tdd.finder.TestFinder.FILETYPE;
import at.ac.tuwien.ifs.qse.tdd.preferences.PreferenceConstants;
import at.ac.tuwien.ifs.qse.tdd.wizard.TddTestCaseWizard;

/**
 * Use this Dialog to show a warning. Especially for 
 * class/test no found errors.</p>
 * If the preference P_SHOW_WARNING is not set no warning will be shown"
 */
public class TddErrorDialog extends Dialog {

	private Label label;
	private Label image;
	private String fileName;
	private boolean warningOn = true;
	FILETYPE type = null;


	public TddErrorDialog(Shell parentShell,String fileName,FILETYPE type) {
		super(parentShell);
		this.fileName = fileName;
		warningOn  =  Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_WARNING);
		this.type = type;
	}



	@Override
	public int open() {
		if(warningOn) {
			return super.open();
		} else {
			return Dialog.OK;
		}

	}


	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		final String buttonText;
		if(type.equals(FILETYPE.CLASS)) {
			buttonText = "Create Class";
		} else {
			buttonText = "Create Test";
		}

		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		button.setText(buttonText);	

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Tdd-Warning notification");
		shell.setImage(getSWTImage(SWT.ICON_WARNING));
		shell.setSize(400, 130);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		composite.setLayout(new FormLayout());

		FormData data = new FormData();
		data.left = new FormAttachment(0,20);
		data.top = new FormAttachment(0,10);
		image = new Label(composite, SWT.FLAT);
		image.setLayoutData(data);
		image.setImage(getSWTImage(SWT.ICON_WARNING));

		data = new FormData();
		data.left = new FormAttachment(image,5);
		data.top = new FormAttachment(0,20);
		label = new Label(composite, SWT.FLAT);
		label.setLayoutData(data);

		if(type.equals(FILETYPE.CLASS)) {
			label.setText("For the test file "+ this.fileName + " could no class be found!");
		} else {
			label.setText("For the file "+ this.fileName + " could no test be found!");
		}

		return composite;
	}

	@Override
	public void okPressed() {

		if(type.equals(FILETYPE.CLASS)) {
			OpenNewClassWizardAction action = new OpenNewClassWizardAction();
			NewClassWizardPage page = new NewClassWizardPage();
			page.init(null);
			page.setTypeName(fileName, true);	
			action.setConfiguredWizardPage(page);
			action.run();	
		} else {
			TddTestCaseWizard wizard = new TddTestCaseWizard(fileName);
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
			dialog.open();
		}

		close();
	}

	private Image getSWTImage(final int imageID) {
		Shell shell = getShell();
		final Display display;
		if (shell == null || shell.isDisposed()) {
			shell = getParentShell();
		}
		if (shell == null || shell.isDisposed()) {
			display = Display.getCurrent();
		} else {
			display = shell.getDisplay();
		}

		final Image[] image = new Image[1];
		display.syncExec(new Runnable() {
			public void run() {
				image[0] = display.getSystemImage(imageID);
			}
		});

		return image[0];

	}


}
