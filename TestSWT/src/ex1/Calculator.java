package ex1;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;


public class Calculator {

	private static String[] items = { "+", "-", "*", "/" };

	private static List<String> historyList = new ArrayList<>();

	static Display display = new Display();

	static Shell shell = getShell();

	static TabFolder folder;

	static TabItem tab1;
	static TabItem tab2;

	static Composite compositeForTab1;
	static Composite separatorComposite;
	static Composite compositeForSecondLevel;
	static Composite compositeForThirdLevel;
	static Composite compositeForTab2;

	static GridData gridDataForFirstLevel;

	static Text text1;
	static Text text2;
	static Text historyText;

	static Combo combo;

	static Button buttonCalc;
	static Button buttonOnFly;

	static Label labelOnFly;
	static Label labelResult;
	static Label labelResultText;
	

	public static void main(String[] args) {

		folder = new TabFolder(shell, SWT.NONE);
		
		
		// Tab 1
		getTab1(folder);

		// Tab 2
		getTab2(folder);

		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	// get new instance of shell
	private static Shell getShell() {


		Shell newShell = new Shell(display);
		newShell.setText("SWT Calculator");
		newShell.setImage(Display.getDefault().getSystemImage(SWT.ICON_INFORMATION));
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		newShell.setBounds(dimension.width / 2 - 157, dimension.height / 2 - 150, 315, 300);
		newShell.setLayout(new FillLayout());
		return newShell;
	}
	
	


	private static void getTab1(TabFolder tabFolder) {
		tab1 = new TabItem(tabFolder, SWT.NONE);
		tab1.setText("Calculator");

		// composite for tab1
		compositeForTab1 = new Composite(tabFolder, SWT.NONE);
		compositeForTab1.setLayout(new GridLayout(3, true));

		//
		// First level
		//

		// grid data for first level
		gridDataForFirstLevel = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);

		// text1
		text1 = new Text(compositeForTab1, SWT.BORDER);
		text1.setLayoutData(gridDataForFirstLevel);
		text1.setMessage("first operand");

		// combo
		combo = new Combo(compositeForTab1, SWT.READ_ONLY | SWT.DROP_DOWN);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		combo.setItems(items);
		combo.setToolTipText("select operation");

		// text2
		text2 = new Text(compositeForTab1, SWT.BORDER);
		text2.setLayoutData(gridDataForFirstLevel);
		text2.setMessage("second operand");

		//
		// End of First level
		//

		separatorComposite = new Composite(compositeForTab1, SWT.NONE);
		separatorComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		//
		// Second level
		//

		// composite for second level
		compositeForSecondLevel = new Composite(compositeForTab1, SWT.NONE);
		compositeForSecondLevel.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, true, 2, 1));
		compositeForSecondLevel.setLayout(new GridLayout(2, false));

		// button for fly check
		buttonOnFly = new Button(compositeForSecondLevel, SWT.CHECK);
		buttonOnFly.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button source = (Button) e.getSource();
				if (source.getSelection()) {
					buttonCalc.setEnabled(false);
				} else {
					buttonCalc.setEnabled(true);
				}
			}
		});

		// label for "on fly"
		labelOnFly = new Label(compositeForSecondLevel, SWT.NONE);
		labelOnFly.setText("Calculate on the fly");

		// button for calculation
		buttonCalc = new Button(compositeForTab1, SWT.PUSH);
		buttonCalc.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
		buttonCalc.setText("Calculate");
		buttonCalc.addSelectionListener(new MySelectionListener());

		//
		// End of Second level
		//

		//
		// Third level
		//

		// composite for third level
		compositeForThirdLevel = new Composite(compositeForTab1, SWT.NONE);
		compositeForThirdLevel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 3, 1));
		compositeForThirdLevel.setLayout(new GridLayout(2, false));

		// label for print "Result:"
		labelResultText = new Label(compositeForThirdLevel, SWT.NONE);
		labelResultText.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		labelResultText.setText("Result:");

		// label for print result
		labelResult = new Label(compositeForThirdLevel, SWT.BORDER | SWT.RIGHT);
		labelResult.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

		//
		// End of Third level
		//
		tab1.setControl(compositeForTab1);
	}

	private static void getTab2(TabFolder tabFolder) {
		tab2 = new TabItem(tabFolder, SWT.NONE);
		tab2.setText("History");
		tab2.setToolTipText("Contains the list of previous calculations");

		// composite for tab2
		compositeForTab2 = new Composite(tabFolder, SWT.NONE);
		compositeForTab2.setLayout(new GridLayout(1, true));

		// text for history of operations
		historyText = new Text(compositeForTab2, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		historyText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tab2.setControl(compositeForTab2);
	}


	// my implementation of SelectionListener
	private static class MySelectionListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (text1.getText() != null && text1.getText().length() != 0 && text2.getText() != null
					&& text2.getText().length() != 0 && combo.getSelectionIndex() >= 0) {
				double oper1;
				double oper2;
				double res;

				try {
					oper1 = Double.parseDouble(text1.getText());
					oper2 = Double.parseDouble(text2.getText());
				} catch (Exception exception) {
					labelResult.setText("Something went wrong");
					return;
				}

				switch (combo.getSelectionIndex()) {
				case 0:
					res = oper1 + oper2;
					makeAnswer(combo, historyText, labelResult, oper1, oper2, res);
					break;
				case 1:
					res = oper1 - oper2;
					makeAnswer(combo, historyText, labelResult, oper1, oper2, res);
					break;
				case 2:
					res = oper1 * oper2;
					makeAnswer(combo, historyText, labelResult, oper1, oper2, res);
					break;
				case 3:
					if (oper2 != 0) {
						res = oper1 / oper2;
						makeAnswer(combo, historyText, labelResult, oper1, oper2, res);
					} else {
						labelResult.setText("Division by zero");
					}
					break;
				default:
					break;
				}
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// empty
		}

		private static void makeAnswer(Combo combo, Text historyText, Label labelResult, double oper1, double oper2,
				double res) {
			DecimalFormat format = new DecimalFormat("#,###.#####");
			historyList.add(format.format(oper1) + " " + items[combo.getSelectionIndex()] + " " + format.format(oper2)
					+ " = " + format.format(res));
			historyText.setText(listToString(historyList));
			labelResult.setText(format.format(res));
		}
		
		// get string from reverse list
		private static String listToString(List<String> list) {
			final String separator = System.lineSeparator();
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = list.size() - 1; i >= 0; i--) {
				stringBuilder.append(list.get(i)).append(separator).append(separator);
			}
			return stringBuilder.toString().trim();
		}
	}
}