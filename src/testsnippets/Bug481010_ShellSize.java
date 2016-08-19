package testsnippets;

import org.eclipse.swt.internal.gtk.OS;

public class Bug481010_ShellSize {

	public static void main(String[] args) {
		OS.gtk_init_check(new long[0], new long[0]);
		/* create a new window */
		long window = OS.gtk_window_new(OS.GTK_WINDOW_TOPLEVEL);
		

		long button = OS.gtk_button_new();
		long labelHandle = OS.gtk_label_new (OS.ascii("Hello world"));
		OS.gtk_container_add(button, labelHandle);
		OS.gtk_container_add(window, button);
		OS.gtk_widget_show(labelHandle);
		OS.gtk_widget_show(button);
		
		OS.gtk_widget_show(window);
		OS.gtk_window_resize(window, 400, 400);
		
		
		OS.gtk_main();
	}

}
