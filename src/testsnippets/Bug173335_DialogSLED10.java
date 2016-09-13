package testsnippets;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Bug173335_DialogSLED10 {
	static Display display = new Display();
	static Shell shell = new Shell(display);
	static Button button = null;
	static Shell dropDownShell = null;
	
	public static void main(String[] args) {
		
		shell.setLayout(new RowLayout());
		
		// create the drop down shell
		dropDownShell = new Shell(shell, SWT.ON_TOP | SWT.NO_TRIM | SWT.DROP_DOWN);
		dropDownShell.setLayout(new RowLayout());
		//dropDownShell.setText("Text on drop down shell");
		dropDownShell.setVisible(false);
		dropDownShell.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				Shell dShell = null;
				Display d = Display.getDefault();
				
				if (d != null) {
					dShell = d.getActiveShell();
				}
				InputDialog dialog = new InputDialog(dShell, "Input Dialog Title", 
													"Input dialog label", 
													"label text", null);   
				dialog.open() ; 
			}			
		});
		
		// create the button
		button = new Button(shell, SWT.PUSH);
		button.setText("Open");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (dropDownShell.isVisible()) {
					hideDropDown();
				}
				else {
					showDropDown();
				}
			}			
		});
				
		
		shell.setSize(300, 300);
		shell.addDisposeListener(new DisposeListener () {
			public void widgetDisposed(DisposeEvent e) {
				if (dropDownShell != null && !dropDownShell.isDisposed()) {
					dropDownShell.dispose();
					dropDownShell = null;
				}
			}
		});
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	private static void showDropDown() {
		if (dropDownShell != null && !dropDownShell.isDisposed()) {
			dropDownShell.setText("This is a drop down shell");
			dropDownShell.setSize(100, 200);
			Rectangle buttonRect = button.getBounds();
			Point p = button.getParent().toDisplay(new Point(buttonRect.x, buttonRect.y + buttonRect.height));
			dropDownShell.setLocation(p.x, p.y);
			dropDownShell.setVisible(true);
			dropDownShell.setFocus();
		}		
	}
	
	private static void hideDropDown() {
		dropDownShell.setVisible(false);
	}
}
