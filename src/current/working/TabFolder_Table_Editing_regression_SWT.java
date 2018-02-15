package current.working;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;


/**
  510803 – [GTK3] regression in editing capabilities (seen in change method refactoring wizard)
  https://bugs.eclipse.org/bugs/show_bug.cgi?id=510803

 * TODO explain.
 *
 */
public class TabFolder_Table_Editing_regression_SWT {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		final TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("Hello tab");

		final Table table = new Table(tabFolder, SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		TableColumn column1 = new TableColumn(table, SWT.NONE);
		for (int i = 0; i < 10; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText("Item " + i);
		}
		column1.pack();

		final TableEditor tableEditor = new TableEditor(table);
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.grabHorizontal = true;
		tableEditor.minimumWidth = 50;

		final Text tableEditorControl = new Text(table, SWT.NONE);
		// Location of setControl() method call has an impact.
		// If it's before 'Text tableEditorControl = new Text(table, SWT.NONE);', then things work.
		tabItem.setControl(table);
		
		tableEditorControl.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
		tableEditorControl.setForeground(display.getSystemColor(SWT.COLOR_RED));
		tableEditorControl.setText("Click some row");
		tableEditorControl.addModifyListener(me -> {
			if (tableEditor.getItem() != null) {
				table.getSelection()[0].setText(tableEditorControl.getText());
			}
		});
		tableEditor.setEditor(tableEditorControl);
		tableEditorControl.setVisible(false);
		
		System.out.println("text size:" + tableEditorControl.getBounds());
		tableEditor.setColumn(0);

		table.addSelectionListener(widgetSelectedAdapter(e -> {
				TableItem item = (TableItem) e.item;
				if (item == null) return;
				tableEditorControl.setVisible(true);
				tableEditorControl.setText(item.getText());
//				tableEditorControl.selectAll();
				tableEditorControl.setFocus();
				tableEditor.setItem(item);
			}));


		shell.setSize(300, 300);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
