package testsnippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

/*
 * NOTE: this snippet has a JFace/Platform UI dependency. If you would like to
 * make use of this snippet, please checkout the necessary sources and modify
 * the classpath manually.
 */

public class Bug478442_HyperLinkRenderingTest {
	public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());

        SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL);
        ScrolledComposite sc = new ScrolledComposite(sashForm, SWT.H_SCROLL | SWT.V_SCROLL);
        Composite composite = new Composite(sc, SWT.NONE);
        composite.setLayout(new FillLayout());

        ImageHyperlink imageLink = new ImageHyperlink(composite, SWT.NONE);
        imageLink.setText("Some long long long long long long long long string");
        imageLink.setOrientation(SWT.RIGHT_TO_LEFT);

        sc.setContent(composite);
        sc.setMinSize(400, 30);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        StyledText styledText = new StyledText(sashForm, SWT.NONE);
        styledText.setText("Right Panel");

        shell.setSize(600, 120);
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    } 
}
