package testsnippets;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class Bug480922_ComboBoxCellEditor {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));

		Table table = new Table(shell, SWT.BORDER);
		TableViewer tableViewer = new TableViewer(table);
		TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewer.setContentProvider(new ArrayContentProvider());
		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(1));
		table.setLayout(tableLayout);
		tableViewer.setLabelProvider(new ComboLabelProvider());
		column.setEditingSupport(new ComboEditingSupport(column.getViewer()));
		tableViewer.setInput(new String[] { "Row 1" });

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).span(2, 1).grab(true, true).applyTo(table);

		Label l = new Label(shell, SWT.NONE);
		l.setText("Enter Text Here:");

		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(l);

		Text t = new Text(shell, SWT.BORDER);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(t);

		shell.setSize(600, 400);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}	

	public static class ComboLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object arg0, int arg1) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return (String) element;
			default:
				return "";
			}
		}
	}

	public static class ComboEditingSupport extends EditingSupport {
		private ComboBoxViewerCellEditor cellEditor = null;

		private ComboEditingSupport(ColumnViewer viewer) {
			super(viewer);
			cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
			cellEditor.setLabelProvider(new LabelProvider());
			cellEditor.setContentProvider(new ArrayContentProvider());
			cellEditor.setInput(new String[] { "Value1", "Value2", "Value3" });
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return cellEditor;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			if (element instanceof String) {
				return (String) element;
			}

			return null;
		}

		@Override
		protected void setValue(Object arg0, Object arg1) {
		}
	}
}
