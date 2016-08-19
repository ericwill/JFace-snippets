package testsnippets;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/*
 * NOTE: this snippet has a JFace/Platform UI dependency. If you would like to
 * make use of this snippet, please checkout the necessary sources and modify
 * the classpath manually.
 */

public class Bug491218_GroupControlDecorationsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();

		Shell s = testCase();
		s.open();
		while (!s.isDisposed()) {
			if( !display.readAndDispatch() ) {
				 display.sleep();
			}
		}
	}

	private static Shell testCase() {
		Shell parent = new Shell(Display.getCurrent(), SWT.DIALOG_TRIM);
		parent.setLayout(new FillLayout());
		{
			Composite g = new Composite(parent, SWT.BORDER);
			g.setLayout(new GridLayout(2, false));

			Label l = new Label(g, SWT.NONE);
			l.setText("Hi");

			Text t = new Text(g, SWT.BORDER);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalIndent = 8;
			t.setLayoutData(gd);

			ControlDecoration controlDecoration = new ControlDecoration(t, SWT.LEFT | SWT.CENTER);
			FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
			controlDecoration.setImage(fieldDecoration.getImage());
			controlDecoration.show();
		}
		{
			Composite g = new Group(parent, SWT.BORDER);
			g.setLayout(new GridLayout(2, false));

			Label l = new Label(g, SWT.NONE);
			l.setText("Hi");

			Text t = new Text(g, SWT.BORDER);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalIndent = 8;
			t.setLayoutData(gd);

			ControlDecoration controlDecoration = new ControlDecoration(t, SWT.LEFT | SWT.CENTER);
			FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
			controlDecoration.setImage(fieldDecoration.getImage());
			controlDecoration.show();
		}

		return parent;
	}

}