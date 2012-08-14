

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
 
public class SWTLabel {
 
public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell(display);
 
	Label label = new Label(shell, SWT.BORDER);
	label.setSize(100,30);
	label.setLocation(50, 50);
	label.setText("I am a Label");
 
	Label shadow_sep_h = new Label(shell, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
	shadow_sep_h.setBounds(50,80,100,50);
 
	Label shadow_sep_v = new Label(shell, SWT.SEPARATOR | SWT.SHADOW_IN | SWT.VERTICAL);
	shadow_sep_v.setBounds(50,100,5,100);
 
 
	shell.setSize(300,300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}