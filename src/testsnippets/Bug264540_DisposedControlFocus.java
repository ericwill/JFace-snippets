package testsnippets;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

/**
 * To reproduce:
 * <ol>
 * <li>Run this class.</li>
 * <li>Edit one of the strings in the table in the left tab.</li>
 * <li>With the cell editor still active, click on the right tab.</li>
 * </ol>
 * An SWTException ("Widget is disposed") results.
 * @author ejj
 */
public class Bug264540_DisposedControlFocus {

  private final List<String> _strings = new ArrayList<String>(Arrays.asList("Arthur", "Ford", "Trillian", "Zaphod"));

  private final IStructuredContentProvider _contentProvider = new IStructuredContentProvider() {
    public void dispose() {
    }
    public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
    }
    public Object[] getElements(final Object inputElement) {
      return _strings.toArray();
    }
  };

  private final ICellModifier _cellModifier = new ICellModifier() {
    public boolean canModify(final Object element, final String property) {
      return true;
    }
    public Object getValue(final Object element, final String property) {
      return element;
    }
    public void modify(final Object element, final String property, final Object value) {
      String oldValue = ((TableItem) element).getText();
      int index = _strings.indexOf(oldValue);
      if (index >= 0) {
        _strings.set(index, (String) value);
        _table.refresh();
        _disposableControl.dispose();
        createDisposableControl();
      }
    }
  };

  private TableViewer _table;

  private Composite _disposableControlParent;

  private Button _disposableControl;

  public static void main(final String[] args) {
    new Bug264540_DisposedControlFocus().show();
  }

  public void show() {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());

    CTabFolder tabFolder = new CTabFolder(shell, SWT.HORIZONTAL | SWT.BOTTOM);
    CTabItem tab1 = new CTabItem(tabFolder, SWT.NONE);
    tab1.setText("Edit one of these values");
    _table = createTable(tabFolder);
    tab1.setControl(_table.getControl());

    CTabItem tab2 = new CTabItem(tabFolder, SWT.NONE);
    tab2.setText("Now click here");
    _disposableControlParent = new Composite(tabFolder, SWT.NONE);
    _disposableControlParent.setLayout(new FillLayout());
    tab2.setControl(_disposableControlParent);
    createDisposableControl();

    shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }

  private TableViewer createTable(final Composite parent) {
    TableViewer viewer = new TableViewer(parent);
    viewer.setCellEditors(new CellEditor[] {new TextCellEditor(viewer.getTable())});
    viewer.setColumnProperties(new String[] {"foo"});
    viewer.setCellModifier(_cellModifier);
    viewer.setContentProvider(_contentProvider);
    viewer.setLabelProvider(new LabelProvider());
    viewer.setInput(new Object());
    return viewer;
  }

  private void createDisposableControl() {
    _disposableControl = new Button(_disposableControlParent, SWT.PUSH);
    _disposableControl.setText("If you click me, nothing will happen.");
  }
}

