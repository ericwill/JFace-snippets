package testsnippets;

import java.util.Map.Entry;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.sun.nio.file.SensitivityWatchEventModifier;

/*
 * NOTE: this snippet has a JFace/Platform UI dependency. If you would like to
 * make use of this snippet, please checkout the necessary sources and modify
 * the classpath manually.
 */

/**
 * Creates and opens a wizard dialog with two simple wizard pages.
 */
public class Bug487757_EmptyScrolledComposite {
	
	/* Snippet bits */
	private static void makeSWTSnippet() {
		Test t = new Test();
		t.shell.setLayout(new FillLayout());
		
		// Snippet selection. 
		int val = 2;
		
		if (val == 1) {
		    Composite c1 = new Composite(t.shell, SWT.BORDER);
		    c1.setLayout(new FillLayout());
		    Button b1 = new Button(c1, SWT.PUSH);
		    b1.setText("button inside composite");
		    c1.setVisible(false);
		    c1.setVisible(true);
		    
		    t.shell.layout();
		    
		    System.out.println("Shell size : " + t.shell.getSize().toString());
		    System.out.println("Comp size : " + c1.getSize().toString());
		    System.out.println("Button size: " + b1.getSize().toString());
		    
	    } else if (val == 2){
		    ScrolledComposite c2 = new ScrolledComposite(t.shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		    Color color = new Color(t.display, 255,127, 0);
		    c2.setBackground(color);
		    
		    Button b2 = new Button(c2, SWT.PUSH);
		    b2.setText("expanding button");
		    c2.setContent(b2);
		    c2.setExpandHorizontal(true);
		    c2.setExpandVertical(true);
		   
		    
		    System.out.println("SComp size : " + c2.getSize().toString());
		    //t.shell.setSize(300, 300);

		    c2.setVisible(false);		// After these two lines, size_allocate in control doesn't allocate properly.     
		    c2.setVisible(true);
		    
		    Composite c1 = new Composite(t.shell, SWT.BORDER);
		    c1.setLayout(new FillLayout());
		    Button b3 = new Button(c1, SWT.PUSH);
		    b3.setText("meh");
		    
		    t.shell.layout();
		    
		    System.out.println("Shell size : " + t.shell.getSize().toString());
		    System.out.println("SComp size : " + c2.getSize().toString());
		    System.out.println("Button size: " + b2.getSize().toString());	    
	    }
		
	    t.displayLoop();
	    
	}

	
	public static void main(String[] args) {
		// Config selection:
		int to_run = 2;
		
		// Execution:
		if (to_run == 1)
			makeWizzard();
		else if (to_run == 2)
			makeSWTSnippet();
	}

	/* Wizzard bits */
		static class WizardPageOne extends WizardPage {
	
			public WizardPageOne(String pageName, String title, ImageDescriptor titleImage) {
				super(pageName, title, titleImage);
			}
	
			public WizardPageOne(String pageName) {
				super(pageName);
			}
			
			@Override
			public void createControl(Composite parent) {
	
				// Using Scrolled Composite ===> Gtk3: Button does not fill Scrolled composite. Gtk2: Button fills Scrolled composite.
//				ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
//				sc.setExpandHorizontal(true);
//				sc.setExpandVertical(true);
//				Button button = new Button(sc, SWT.PUSH);
//				button.setText("hello world!");
//				sc.setContent(button);
//				setControl(sc);
				
				// Original jFace From layout.
	     	FormToolkit toolkit = new FormToolkit(parent.getDisplay());
				ScrolledForm form = toolkit.createScrolledForm(parent);
				System.out.println("hello world");
				form.setText("Sample form");
				//form.getBody().setLayout(new GridLayout());
				//toolkit.createButton(form.getBody(), "Checkbox", SWT.CHECK);
				form.reflow(true);
				setControl(form);     /// <<< Note
			}
		}

		static class SampleWizard extends Wizard {
		
			@Override
			public void addPages() {			
				addPage(new WizardPageOne("First page"));
			}
		
			@Override
			public String getWindowTitle() {
				return "title meh.";
			}
		
			@Override
			public boolean performFinish() {
				return true;
			}
		
		}

		private static void makeWizzard() {
			Display display = Display.getCurrent();
			final Shell shell = new Shell(display);
			
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