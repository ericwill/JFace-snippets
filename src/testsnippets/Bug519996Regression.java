package testsnippets;

import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Bug519996Regression {

	public static void main(String[] args) {
		Shell shell = new Shell();
		RowLayoutFactory.fillDefaults().applyTo(shell);
		CCombo combo = new CCombo(shell, SWT.BORDER);
		CCombo combo1 = new CCombo(shell, SWT.BORDER);
			 
		Button button = new Button(shell, SWT.PUSH);
		button.setText("focus and close");
		button.addSelectionListener(new SelectionAdapter() {
		 @Override
		 public void widgetSelected(SelectionEvent e) {
		  combo.setFocus();
		  shell.close();
		 }
		});
				 
		shell.layout();
		shell.open();
		
		Display display = shell.getDisplay();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}

}
