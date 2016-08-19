package testsnippets;


import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
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
public class Bug487160_ScrolledCompositeShowHide {

		public static void main(String[] args) {
//			
//			Display display = new Display ();
//		    Shell shell = new Shell (display);
//		    shell.setLayout(new FillLayout());
//
//		    // this button has a minimum size of 400 x 400. If the window is resized to be big
//		    // enough to show more than 400 x 400, the button will grow in size. If the window
//		    // is made too small to show 400 x 400, scrollbars will appear.
//		    ScrolledComposite c2 = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
//		    
//		    Composite composite = new Composite(c2, SWT.NONE);
//		    
//		    
//		    
//		    Button b2 = new Button(composite, SWT.PUSH);
//		    b2.setText("expanding button");
//		    c2.setContent(b2);
//		    c2.setExpandHorizontal(true);
//		    c2.setExpandVertical(true);
//		    c2.setMinSize(50, 50);
//
//		    shell.setSize(600, 300);
//		    shell.open ();
//		    
//		    while (!shell.isDisposed ()) {
//		        if (!display.readAndDispatch ()) display.sleep ();
//		    }
//		    display.dispose ();
//			
//			
			
		Display display = new Display();
		final Shell shell = new Shell(display);
		

		
		
		final ScrolledComposite sc1 = new ScrolledComposite(shell, SWT.VERTICAL);
		{
			
			sc1.setExpandVertical(true);
			sc1.setExpandHorizontal(true);
			Composite composite = new Composite(sc1, SWT.NONE);
			
			GridData data;
			data = new GridData(GridData.FILL_HORIZONTAL);
			Label label = new Label(composite, SWT.NONE);
			label.setText("Enter a number between 0 and 9:");
			label.setLayoutData(data);

			
			new Text(composite, SWT.BORDER).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
			GridLayout gridLayout = new GridLayout(2, false);
			gridLayout.marginWidth = 5;
			gridLayout.marginHeight = 5;
			gridLayout.verticalSpacing = 0;
			gridLayout.horizontalSpacing = 0;
			composite.setLayout(gridLayout);
			
			data = new GridData(GridData.FILL_HORIZONTAL);
			composite.setLayoutData(data);
			
			
			GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(composite);
			sc1.setContent(composite);
			Point p = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
//			Point p = new Point (259, 42);
			sc1.setSize(p);
			sc1.setMinSize(p);
		}
		
		final ScrolledComposite sc2 = new ScrolledComposite(shell, SWT.VERTICAL);
		{
			sc2.setExpandVertical(true);
			sc2.setExpandHorizontal(true);
			Composite composite = new Composite(sc2, SWT.NONE);
			
			
			GridData data;
			Label label = new Label(composite, SWT.NONE);
			label.setText("Enter a date:");
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			new Text(composite, SWT.BORDER).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			GridLayout gridLayout = new GridLayout(2, false);
			gridLayout.marginWidth = 5;
			gridLayout.marginHeight = 5;
			gridLayout.verticalSpacing = 0;
			gridLayout.horizontalSpacing = 0;
			composite.setLayout(gridLayout);
			
			data = new GridData(GridData.FILL_HORIZONTAL);
			composite.setLayoutData(data);
			
	
			GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(composite);

			sc2.setContent(composite);
			Point p = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
//			Point p = new Point (259, 42);
			sc2.setSize(p);
			sc2.setMinSize(p);
		}
		
		shell.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {}
			
			@Override
			public void mouseDown(MouseEvent e) {
				sc1.setVisible(false);
				sc2.setVisible(true);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
		
		
		
//		shell.setSize(500, 200);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
