package testsnippets;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

    public class Bug513761_TreeVirtualCheck {
    	static final Collection<String> S = Stream.of(1,2,3,11,22,33).map(i -> "" + i).collect(Collectors.toList());
    	public static void main(String[] args) throws Throwable {
            Shell s = new Shell();
            s.setLayout(new FillLayout());
            CheckboxTreeViewer v = new CheckboxTreeViewer(new Tree(s, SWT.MULTI | SWT.FULL_SELECTION
                    | SWT.BORDER | SWT.VIRTUAL | SWT.CHECK));
            // This works
//            CheckboxTreeViewer v = new CheckboxTreeViewer(new Tree(s, SWT.MULTI | SWT.FULL_SELECTION
//                    | SWT.BORDER | SWT.CHECK));

            TreeViewerColumn cc = new TreeViewerColumn(v, SWT.LEFT);
            TreeViewerColumn c1 = new TreeViewerColumn(v, SWT.LEFT);

            PixelConverter pc = new PixelConverter(v.getControl());
            cc.getColumn().setWidth(pc.convertWidthInCharsToPixels(15));
            c1.getColumn().setWidth(pc.convertWidthInCharsToPixels(20));

            cc.setLabelProvider(new ColumnLabelProvider());
            c1.setLabelProvider(new ColumnLabelProvider() {

                @Override
                public Image getImage(Object element) {
                    return ImageDescriptor.createFromURL(getClass().getResource("dummy")).createImage();
                }
            });
            // with styled provider checkbox in the first column doesn't render but still responds to clicks!
            c1.setLabelProvider(new StyledCellLabelProvider() {

                @Override
                public void update(ViewerCell cell) {
                    cell.setText(cell.getElement().toString());
                    cell.setImage(ImageDescriptor.createFromURL(getClass().getResource("dummy")).createImage());
                    super.update(cell);
                }
            });

            v.setContentProvider(new ITreeContentProvider() {

                @Override
                public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
                }

                @Override
                public void dispose() {
                }

                @Override
                public boolean hasChildren(Object element) {
                    return ((String) element).length() == 1;
                }

                @Override
                public Object getParent(Object element) {
                    String s = (String) element;
                    return s.length() > 1 ? s.substring(0, s.length() - 1) : null;
                }

                @Override
                public Object[] getElements(Object inputElement) {
                    Collection<?> l = (Collection<?>) inputElement;
                    return l.stream().filter(o -> ((String) o).length() <= 1).toArray();
                }

                @Override
                public Object[] getChildren(Object parentElement) {
                    String el = (String) parentElement;
                    return S.stream().filter(s -> s.startsWith(el) && s.length() != el.length()).toArray();
                }
            });

            v.setInput(S);
            s.setSize(400, 200);
            s.open();
            while(!s.isDisposed()) {
                if (!s.getDisplay().readAndDispatch()) {
                    s.getDisplay().sleep();
                }
            }
        }	
    }
