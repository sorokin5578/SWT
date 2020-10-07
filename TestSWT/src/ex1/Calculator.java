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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
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

	static Group group;
	static Group group2;

	static Text text1;
	static Text text2;
	static Text historyText;

	static Combo combo;

	static Button buttonCalcButton;
	static Button buttonOnFly;

	static Label labelOnFly;
	static Label labelResult;
	static Label labelResultText;

	public static void main(String[] args) {

		folder = new TabFolder(shell, SWT.NONE);

		// Tab 1
		tab1 = new TabItem(folder, SWT.NONE);
		tab1.setText("Calculator");

		// Tab 2
		tab2 = new TabItem(folder, SWT.NONE);
		tab2.setText("History");
		tab2.setToolTipText("Contains the list of previous calculations");

		// group for first tab
		group = new Group(folder, SWT.NONE);

		// group2 for second tab
		group2 = new Group(folder, SWT.NONE);

		// text1
		text1 = new Text(group, SWT.BORDER | SWT.LEFT);
		text1.setBounds(10, 20, 100, 23);
		text1.setMessage("first operand");

		// text2
		text2 = new Text(group, SWT.BORDER | SWT.LEFT);
		text2.setBounds(180, 20, 100, 23);
		text2.setMessage("second operand");

		// text for history of operations
		historyText = new Text(group2, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY);
		historyText.setBounds(10, 10, 270, 200);

		// combo
		combo = new Combo(group, SWT.READ_ONLY | SWT.DROP_DOWN);
		combo.setItems(items);
		combo.setToolTipText("select operation");
		combo.setBounds(120, 20, 50, 23);

		labelOnFly = new Label(group, SWT.NONE);
		labelOnFly.setText("Calculate on the fly");
		labelOnFly.setBounds(30, 154, 120, 24);

		labelResult = new Label(group, SWT.BORDER);
		labelResult.setBounds(100, 185, 170, 24);

		labelResultText = new Label(group, SWT.NONE);
		labelResultText.setText("Result:");
		labelResultText.setBounds(60, 188, 50, 24);

		// button for calculation
		buttonCalcButton = new Button(group, SWT.PUSH);
		buttonCalcButton.setText("Calculate");
		buttonCalcButton.setBounds(170, 151, 100, 24);
		buttonCalcButton.addSelectionListener(new MySelectionListener());

		// button for fly check
		buttonOnFly = new Button(group, SWT.CHECK);
		buttonOnFly.setBounds(10, 150, 20, 24);
		buttonOnFly.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button source = (Button) e.getSource();
				if (source.getSelection()) {
					buttonCalcButton.setEnabled(false);
				} else {
					buttonCalcButton.setEnabled(true);
				}
			}
		});

		tab1.setControl(group);

		tab2.setControl(group2);

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
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		newShell.setBounds(dimension.width/2-157, dimension.height/2-150, 315, 300);
		newShell.setLayout(new FillLayout());
		return newShell;
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
	}
}