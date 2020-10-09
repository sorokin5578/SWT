package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;





public class MyGridTest {
	
	private static String[] items = { "+", "-", "*", "/" };

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);

        // create a new GridLayout with two columns
        // of different size
        GridLayout layout = new GridLayout(3, true);

        // set the layout to the shell
        shell.setLayout(layout);

        GridData data = new GridData(SWT.FILL, SWT.FILL, true,true,1,1);
        // create a label and a button
        Text text1 = new Text(shell, SWT.NONE);
        text1.setMessage("text1");
        text1.setLayoutData(data);
        
        Combo combo = new Combo(shell, SWT.READ_ONLY | SWT.DROP_DOWN);
		combo.setItems(items);
		combo.setToolTipText("select operation");
		combo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1,1));
        
        Text text2 = new Text(shell, SWT.NONE);
        text2.setMessage("text2");
        text2.setLayoutData(data);
        
        
//        Label labelSepar = new Label(shell, SWT.NONE);
//        labelSepar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3,1));
//        
//        Label labelSepar2 = new Label(shell, SWT.NONE);
//        labelSepar2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3,1));
//        
//        Label labelSepar3 = new Label(shell, SWT.NONE);
//        labelSepar3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3,1));
//        
//        Label labelSepar4 = new Label(shell, SWT.NONE);
//        labelSepar4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3,1));
        

        Composite composite3 = new Composite(shell, SWT.NONE);
		composite3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3,1));
		
        
        Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,1));
		composite.setLayout(new GridLayout(2, false));
		
		// button for fly check
		Button buttonOnFly = new Button(composite, SWT.CHECK);
		
		Label label = new Label(composite, SWT.NONE);
		label.setText("Calculate on the fly");
		
		
		Button buttonCalc = new Button(shell, SWT.PUSH);
		buttonCalc.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1,1));
		buttonCalc.setText("Calculate");
		
	
        Composite composite2 = new Composite(shell, SWT.NONE);
        composite2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3,1));
        composite2.setLayout(new GridLayout(2, false));
		
		Label label2 = new Label(composite2, SWT.NONE);
		label2.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1,1));
		label2.setText("Result:");
		
		Label result = new Label(composite2, SWT.BORDER);
		result.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1,1));

        
        
//        // create a new label that will span two columns
//        label = new Label(shell, SWT.BORDER);
//        label.setText("This is a label");
        // create new layout data
//        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
//        label.setLayoutData(data);
//
//        // create a new label which is used as a separator
//        label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
//
//        // create new layout data
//        data = new GridData(SWT.FILL, SWT.TOP, true, false);
//        data.horizontalSpan = 2;
//        label.setLayoutData(data);
//
//        // creates a push button
//        Button b = new Button(shell, SWT.PUSH);
//        b.setText("New Button");
//
//        data = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
//        b.setLayoutData(data);
//
//         // create a spinner with min value 0 and max value 1000
//        Spinner spinner = new Spinner(shell, SWT.READ_ONLY);
//        spinner.setMinimum(0);
//        spinner.setMaximum(1000);
//        spinner.setSelection(500);
//        spinner.setIncrement(1);
//        spinner.setPageIncrement(100);
//        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//        gridData.widthHint = SWT.DEFAULT;
//        gridData.heightHint = SWT.DEFAULT;
//        gridData.horizontalSpan = 2;
//        spinner.setLayoutData(gridData);
//
//        Composite composite = new Composite(shell, SWT.BORDER);
//        gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//        gridData.horizontalSpan = 2;
//        composite.setLayoutData(gridData);
//        composite.setLayout(new GridLayout(1, false));
//
//        Text txtTest = new Text(composite, SWT.NONE);
//        txtTest.setText("Testing");
//        gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//        txtTest.setLayoutData(gridData);
//
//        Text txtMoreTests = new Text(composite, SWT.NONE);
//        txtMoreTests.setText("Another test");
//
//        Group group = new Group(shell, SWT.NONE);
//        group.setText("This is my group");
//        gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//        gridData.horizontalSpan = 2;
//        group.setLayoutData(gridData);
//        group.setLayout(new RowLayout(SWT.VERTICAL));
//        Text txtAnotherTest = new Text(group, SWT.NONE);
//        txtAnotherTest.setText("Another test");
//
//        // Children of this widget should get a fixed size
//        Composite fixedElements = new Composite(shell, SWT.BORDER);
//        gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//        gridData.horizontalSpan = 2;
//        fixedElements.setLayoutData(gridData);
//        fixedElements.setLayout(new GridLayout(2, false));
//        Label label2 = new Label(fixedElements, SWT.BORDER);
//        GridData layoutData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
//        layoutData.widthHint=200;
//        label2.setLayoutData(layoutData);
//        label2.setText("Fixed");
//        Label label3 = new Label(fixedElements, SWT.BORDER);
//        GridData layoutData2 = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
//        layoutData2.widthHint=20;
//        label3.setLayoutData(layoutData2);
//        label3.setText("Small but still fixed");



        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

}
