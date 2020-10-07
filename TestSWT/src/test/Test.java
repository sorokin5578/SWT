package test;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.*;
public class Test {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		
		shell.setSize(450,300);
		shell.setText("App");
		Button button = new Button(shell, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				System.out.println("Hello");
			}
		});
		button.setBounds(84, 103, 164, 25);
		button.setText("Hello");
		
		shell.open();

		// run the event loop as long as the window is open
		while (!shell.isDisposed()) {
		    // read the next OS event queue and transfer it to a SWT event
		    if (!display.readAndDispatch())
		     {
		    // if there are currently no other OS event to process
		    // sleep until the next OS event is available
		        display.sleep();
		     }
		}

		// disposes all associated windows and their components
		display.dispose();
	}
}
