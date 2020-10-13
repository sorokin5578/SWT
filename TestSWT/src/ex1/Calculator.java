package ex1;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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



/**
 * @author Illia
 * @version 1.3
 * 
 * Class for create a calculator with using SWT
 */
public class Calculator {

	private final Shell shell;

	private List<String> historyList = new ArrayList<>();

	private Text firstOperandText;
	private Text secondOperandText;
	private Text historyText;

	private Combo menuWithOperationsCombo;

	private Button calculateButton;
	private Button onFlyButton;

	private Label resultFieldLabel;

	public Calculator(Shell shell) {
		this.shell = shell;
		initCalculator();
		
	}

	private void initCalculator() {
		initShell();
		TabFolder mainFolder = new TabFolder(shell, SWT.NONE);

		createCalculateTabItem(mainFolder);

		createHistoryTabItem(mainFolder);
	}

	private void initShell() {
		shell.setText("SWT Calculator");
		shell.setImage(Display.getDefault().getSystemImage(SWT.ICON_INFORMATION));
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		shell.setBounds(dimension.width / 2 - 157, dimension.height / 2 - 150, 315, 300);
		shell.setLayout(new FillLayout());
	}

	private void createCalculateTabItem(TabFolder tabFolder) {
		TabItem calculateTabItem = new TabItem(tabFolder, SWT.NONE);
		calculateTabItem.setText("Calculator");

		Composite compositeForCalculateTab = new Composite(tabFolder, SWT.NONE);
		compositeForCalculateTab.setLayout(new GridLayout(3, true));

		//
		// First level
		//

		GridData gridDataForFirstLevel = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);

		firstOperandText = new Text(compositeForCalculateTab, SWT.BORDER);
		firstOperandText.setLayoutData(gridDataForFirstLevel);
		firstOperandText.setMessage("first operand");
		firstOperandText.setToolTipText("Enter an integer or floating point number");
		firstOperandText.addModifyListener(new CustomModifyListenerForTextOfOperand());

		menuWithOperationsCombo = new Combo(compositeForCalculateTab, SWT.READ_ONLY | SWT.DROP_DOWN);
		menuWithOperationsCombo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		menuWithOperationsCombo.setItems(Arrays.copyOf(Arrays.stream(Operation.values()).map(x -> x.title).toArray(),
				Operation.values().length, String[].class));
		menuWithOperationsCombo.setToolTipText("select operation");
		menuWithOperationsCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (onFlyButton.getSelection()) {
					doCalculate();
				}
			}
		});

		secondOperandText = new Text(compositeForCalculateTab, SWT.BORDER);
		secondOperandText.setLayoutData(gridDataForFirstLevel);
		secondOperandText.setMessage("second operand");
		secondOperandText.setToolTipText("Enter an integer or floating point number");
		secondOperandText.addModifyListener(new CustomModifyListenerForTextOfOperand());

		//
		// End of First level
		//

		Composite separatorComposite = new Composite(compositeForCalculateTab, SWT.NONE);
		separatorComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		//
		// Second level
		//

		Composite compositeForSecondLevel = new Composite(compositeForCalculateTab, SWT.NONE);
		compositeForSecondLevel.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, true, 2, 1));
		compositeForSecondLevel.setLayout(new GridLayout(2, false));

		onFlyButton = new Button(compositeForSecondLevel, SWT.CHECK);
		onFlyButton.setToolTipText("Automatic calculate without clicking the Calculate button");
		onFlyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button source = (Button) e.getSource();
				if (source.getSelection()) {
					calculateButton.setEnabled(false);
					doCalculate();
				} else {
					calculateButton.setEnabled(true);
				}
			}
		});

		Label onFlyLabel = new Label(compositeForSecondLevel, SWT.NONE);
		onFlyLabel.setText("Calculate on the fly");

		calculateButton = new Button(compositeForCalculateTab, SWT.PUSH);
		calculateButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
		calculateButton.setText("Calculate");
		calculateButton.addSelectionListener(new CustomerSelectionListenerForCalculateButton());

		//
		// End of Second level
		//

		//
		// Third level
		//

		Composite compositeForThirdLevel = new Composite(compositeForCalculateTab, SWT.NONE);
		compositeForThirdLevel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 3, 1));
		compositeForThirdLevel.setLayout(new GridLayout(2, false));

		Label resultTextLabel = new Label(compositeForThirdLevel, SWT.NONE);
		resultTextLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		resultTextLabel.setText("Result:");

		resultFieldLabel = new Label(compositeForThirdLevel, SWT.BORDER | SWT.RIGHT);
		resultFieldLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

		//
		// End of Third level
		//
		calculateTabItem.setControl(compositeForCalculateTab);
	}

	private void createHistoryTabItem(TabFolder tabFolder) {
		TabItem historyTabItem = new TabItem(tabFolder, SWT.NONE);
		historyTabItem.setText("History");
		historyTabItem.setToolTipText("Contains the list of previous calculations");

		Composite compositeForHistoryTab = new Composite(tabFolder, SWT.NONE);
		compositeForHistoryTab.setLayout(new GridLayout(1, true));

		historyText = new Text(compositeForHistoryTab,
				SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		historyText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		historyTabItem.setControl(compositeForHistoryTab);
	}

	private void makeAnswerForHistoryList(Combo combo, Text historyText, Label labelResult, double oper1, double oper2,
			double res) {
		DecimalFormat format = new DecimalFormat("#,###.########");
		historyList.add(format.format(oper1) + " " + menuWithOperationsCombo.getText() + " " + format.format(oper2)
				+ " = " +  format.format(res));
		historyText.setText(listToString(historyList));
		labelResult.setText(format.format(res));
	}

	private String listToString(List<String> list) {
		List<String> copyList=new ArrayList<>();
		copyList.addAll(list);
		final String separator = System.lineSeparator();
		Collections.reverse(copyList);
		return copyList.stream().collect(Collectors.joining(separator+separator)).trim();
	}
	
	private boolean isInputDataValid(String input) {
		Pattern p = Pattern.compile("(^[\\+\\-]?[0-9]*[.]?[0-9]+$)|(^[\\+\\-]?[0-9]+[.]?$)");
		Matcher matcher = p.matcher(input);
		return matcher.find();
	}

	private void doCalculate() {
		if (firstOperandText.getText() != null && firstOperandText.getText().length() != 0
				&& secondOperandText.getText() != null && secondOperandText.getText().length() != 0
				&& menuWithOperationsCombo.getSelectionIndex() >= 0) {
			double oper1;
			double oper2;
			double res;
			try {
				if(isInputDataValid(firstOperandText.getText())&&isInputDataValid(secondOperandText.getText())) {
				oper1 = Double.parseDouble(firstOperandText.getText());
				oper2 = Double.parseDouble(secondOperandText.getText());
				}else {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException exception) {
				resultFieldLabel.setText("Wrong input. Insert the number!");
				return;
			}

			String operation = menuWithOperationsCombo.getText();
			if (Operation.ADD.titleEqualsTo(operation)) {
				res = oper1 + oper2;
				makeAnswerForHistoryList(menuWithOperationsCombo, historyText, resultFieldLabel, oper1, oper2, res);
			} else if (Operation.SUB.titleEqualsTo(operation)) {
				res = oper1 - oper2;
				makeAnswerForHistoryList(menuWithOperationsCombo, historyText, resultFieldLabel, oper1, oper2, res);
			} else if (Operation.MULT.titleEqualsTo(operation)) {
				res = oper1 * oper2;
				makeAnswerForHistoryList(menuWithOperationsCombo, historyText, resultFieldLabel, oper1, oper2, res);
			} else if (Operation.DIV.titleEqualsTo(operation)) {
				if (oper2 != 0) {
					res = oper1 / oper2;
					makeAnswerForHistoryList(menuWithOperationsCombo, historyText, resultFieldLabel, oper1, oper2, res);
				} else {
					resultFieldLabel.setText("Division by zero");
				}
			}
		}
	}

	private class CustomerSelectionListenerForCalculateButton implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			doCalculate();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// empty
		}
	}

	private class CustomModifyListenerForTextOfOperand implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			Text text = (Text) e.getSource();
			if (isInputDataValid(text.getText()) || text.getText().length() == 0) {
				text.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				resultFieldLabel.setText("");
			} else {
				text.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				resultFieldLabel.setText("Wrong input. Insert the number!");
			}

			if (onFlyButton.getSelection()) {
				doCalculate();
			}
		}

	}

	
	/**
	 * Enum for initializing arithmetic operations. The title contains the arithmetic operation symbol.
	 */
	private enum Operation {
		ADD("+"), SUB("-"), MULT("*"), DIV("/");

		/**
		 * Contain title of element
		 */
		private String title;

		/**
		 * @param title field which must contain title of element
		 */
		Operation(String title) {
			this.title = title;
		}

		/**
		 * Checking if title and value are equal
		 * @param value which to be compared against the enum title
		 * @return returns true if the value is equal to the title, returns false if the value is not equal to the title
		 */
		public boolean titleEqualsTo(String value) {
			return title.equals(value);
		}
	}
}