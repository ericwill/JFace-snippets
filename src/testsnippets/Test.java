package testsnippets;

import java.lang.management.ManagementFactory;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.gtk.OS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Test {

	Display display;
	Shell shell;
	
	public Test() {
		this.display = new Display ();
	    this.shell = new Shell (display);
	    
	    shell.setText(System.getProperty("sun.java.command") + " " +  Test.getGtkVersion());
	}
	
	static public void fillImage(Image image, int swtSystemColor) {
		GC gc = new GC(image);
		gc.setBackground(Display.getCurrent().getSystemColor(swtSystemColor));
		gc.fillRectangle(0, 0, 24, 24);
		gc.dispose();
	}
	
	Test setShellFullScreen() {
		Rectangle rect = this.display.getBounds();
		this.shell.setBounds(rect);
		return this;
	}
	
	void displayLoop() {
 	    shell.open();
	    while (!shell.isDisposed ()) {
	        if (!display.readAndDispatch ()) display.sleep ();
	    }
	    display.dispose ();
	}
	
	
	static String getGtkVersion () {
		return "Gtk: " + OS.gtk_major_version() + "." + OS.gtk_minor_version() + "." + OS.gtk_micro_version() + "  pid:"
				+ ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	}
	
}
