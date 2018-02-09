/*******************************************************************************
 * Copyright (c) 2018 Red Hat and others. All rights reserved.
 * The contents of this file are made available under the terms
 * of the GNU Lesser General Public License (LGPL) Version 2.1 that
 * accompanies this distribution (lgpl-v21.txt).  The LGPL is also
 * available at http://www.gnu.org/licenses/lgpl.html.  If the version
 * of the LGPL at http://www.gnu.org is different to the version of
 * the LGPL accompanying this distribution and there is any conflict
 * between the two license versions, the terms of the LGPL accompanying
 * this distribution shall govern.
 *
 * Contributors:
 *     Red Hat - initial API and implementation
 *******************************************************************************/
package current.broken;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

// Bug510803_broken_ExpandBar.java
// Editing behaviour is strange on Gtk3.
// Snippet is incomplete thou, it needs to be updated to use proper tableEditor instead of a loose Text widget.

public class ExpandBar_Table_Editing_regression_SWT {

	public static void main(String[] args) {
		Shell shell = shellSetup();

		final ExpandBar bar = new ExpandBar (shell, SWT.V_SCROLL);
		ExpandItem item0 = new ExpandItem (bar, SWT.NONE, 0);
		item0.setText("Re-parenting breakage");


		Table table = new Table(bar, SWT.NONE);
		TableItem tableItem = new TableItem(table, SWT.None);
		tableItem.setText("Item 1");
		TableItem tableItem2 = new TableItem(table, SWT.None);
		tableItem2.setText("Item 2");
//
//		// Notes:
//		// - Seems to occur with any control, not just Text. (Tested with Button also)
		final Text cellEditorText = new Text(table, SWT.SINGLE); // Note,
		cellEditorText.setText("Hello world");
		cellEditorText.setFocus();

//
//		// Listeners that make typing into Table edit controls. Useful to test
		// functionality, but errors occur without the listeners also.
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				cellEditorText.setFocus();
			};
		});

		cellEditorText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				TableItem selectedItem = table.getSelection()[0];
				selectedItem.setText(selectedItem.getText() + e.character);
			};
		});

		// Location of setControl() method call has an impact.
		// If it's before 'Text' creation, no errors are thrown into the
		// console
		item0.setHeight(table.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item0.setControl(table);


		mainEventLoop(shell);
	}

	private static Shell shellSetup() {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		return shell;
	}

	private static void mainEventLoop(Shell shell) {
		Display display = shell.getDisplay();
		shell.open();
		shell.setSize(200, 300);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
