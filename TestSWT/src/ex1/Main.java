package ex1;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);

		new Calculator(shell);
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
