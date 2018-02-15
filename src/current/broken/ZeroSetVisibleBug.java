package current.broken;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ZeroSetVisibleBug {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		Text text = new Text(shell, SWT.None);
		text.setText("hello");
		text.setVisible(true);
		shell.setSize(300, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
