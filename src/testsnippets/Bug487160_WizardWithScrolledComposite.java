package testsnippets;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/*
 * NOTE: this snippet has a JFace/Platform UI dependency. If you would like to
 * make use of this snippet, please checkout the necessary sources and modify
 * the classpath manually.
 */

/**
 * Creates and opens a wizard dialog with two simple wizard pages.
 */
public class Bug487160_WizardWithScrolledComposite {

	static class FirstWizardPage extends WizardPage {

		protected FirstWizardPage() {
			super("First", "First Page", null);
		}

		@Override
		public void createControl(Composite parent) {
			ScrolledComposite sc = new ScrolledComposite(parent, SWT.VERTICAL);
			sc.setExpandVertical(true);
			sc.setExpandHorizontal(true);
			Composite composite = new Composite(sc, SWT.NONE);
			Label label = new Label(composite, SWT.NONE);
			label.setText("Enter a number between 0 and 9:");
			new Text(composite, SWT.BORDER);

			GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(composite);
			sc.setContent(composite);
			Point p = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			sc.setSize(p);
			sc.setMinSize(p);
			setControl(sc);
		}
	}

	static class SecondWizardPage extends WizardPage {
		protected SecondWizardPage() {
			super("Second", "Second Page", null);
		}

		@Override
		public void createControl(Composite parent) {
			ScrolledComposite sc = new ScrolledComposite(parent, SWT.VERTICAL);
			sc.setExpandVertical(true);
			sc.setExpandHorizontal(true);
			Composite composite = new Composite(sc, SWT.NONE);
			Label label = new Label(composite, SWT.NONE);
			label.setText("Enter a date:");
			new Text(composite, SWT.BORDER);

			GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(composite);

			sc.setContent(composite);
			Point p = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			sc.setSize(p);
			sc.setMinSize(p);

			setControl(sc);
		}
	}

	static class SampleWizard extends Wizard {

		@Override
		public void addPages() {
			addPage(new FirstWizardPage());
			addPage(new SecondWizardPage());
		}

		@Override
		public String getWindowTitle() {
			return "Data Binding Snippet014";
		}

		@Override
		public boolean performFinish() {
			return true;
		}

	}

	public static void main(String[] args) {
		Display display = Display.getCurrent();
		final Shell shell = new Shell(display);
		shell.close();
		IWizard wizard = new SampleWizard();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
		// The SWT event loop
		while (dialog.getShell() != null && !dialog.getShell().isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}

