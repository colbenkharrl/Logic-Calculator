//	Colben Kharrl, March 18, 2016. Truth Table Generator.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WholeFrame extends JFrame {
	
//- - - INSTANCE UI VARIABLES - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private final int APPLET_WIDTH, APPLET_HEIGHT;
	private JPanel equationPanel, inputPanel, truthTablePanel;
	private JTLabel equationLabel;
	private JTextField equationText;
	private JButton equateButton;
	private ActionListener listener;
	private int gridRows, gridColumns;	
	private Equation currentEquation;
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - -
	
//- - - CONSTRUCTOR - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public WholeFrame() {

		getContentPane().setBackground(Color.red);
		APPLET_WIDTH = 600;
		APPLET_HEIGHT = 800;
		equationPanel = createEquationPanel();
		currentEquation = new Equation(equationText.getText());
		truthTablePanel = createTruthTablePanel(currentEquation);
		add(equationPanel, BorderLayout.NORTH);
		add(truthTablePanel, BorderLayout.CENTER);
		setSize(APPLET_WIDTH, APPLET_HEIGHT);
		
	}
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - -	
	
//- - - UI PANELS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private JPanel createEquationPanel() {
		JPanel returnPanel = new JPanel(new BorderLayout());
		inputPanel = new JPanel(new GridLayout(2, 1));
		inputPanel.setBackground(new Color(194, 200, 199));
		returnPanel.setBackground(new Color(194, 200, 199));
		equationLabel = new JTLabel("Input function here:", new Color(194, 200, 199), Color.black);
		equationText = new JTextField("a'b+ab'");
		inputPanel.add(equationLabel);
		inputPanel.add(equationText);
		equateButton = new JButton("Equate!");
		listener = new ButtonListener();
		equateButton.addActionListener(listener);
		returnPanel.add(inputPanel, BorderLayout.CENTER);
		returnPanel.add(equateButton, BorderLayout.EAST);
		return returnPanel;
	}
	
	private JPanel createTruthTablePanel(Equation inputEquation) {

		gridColumns = inputEquation.getVariableNumber() + 1;
		gridRows = (int) Math.pow(2, inputEquation.getVariableNumber()) + 1;	
		JPanel returnPanel = new JPanel(new GridLayout(gridRows, gridColumns));
		returnPanel.setBackground(new Color(237, 233, 218));	
		for (int i = 0; i < inputEquation.getVariableNumber(); i++) {
			
			returnPanel.add(new JTLabel(inputEquation.getVariables().get(i).getCharValue().toString(), new Color(95, 125, 144), Color.white));
		}
		returnPanel.add(new JTLabel("Output", new Color(95, 125, 144), Color.white));
		for (int i = 0; i < (int) Math.pow(2, inputEquation.getVariableNumber()); i++) {
			
			String binaryValueRow = currentEquation.binaryValue(i);
			for (int j = 0; j < inputEquation.getVariableNumber(); j++) {
				
				returnPanel.add(new JTLabel(Character.toString(binaryValueRow.charAt(j)), new Color(226, 238, 241), Color.black));
			}
			returnPanel.add(new JTLabel(Integer.toString(currentEquation.solve(binaryValueRow)), new Color(211, 209, 249), Color.black));
		}
		return returnPanel;
	}
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - -
	
//- - - LISTENERS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (Equation.isParsable(equationText.getText())) {
				currentEquation = new Equation(equationText.getText());
				remove(truthTablePanel);
				truthTablePanel = new JPanel();
				truthTablePanel = createTruthTablePanel(currentEquation);
				add(truthTablePanel, BorderLayout.CENTER);
				equationLabel.setForeground(Color.black);
				equationLabel.setText("Input function here:");
				revalidate();
				repaint();
			} else {
				equationLabel.setForeground(Color.red);
				equationLabel.setText("Unparsable String");
			}
		}
	}
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - -
}
