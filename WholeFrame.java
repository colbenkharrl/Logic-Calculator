import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WholeFrame extends JFrame {
	
	final int APPLET_WIDTH = 600;
	final int APPLET_HEIGHT = 800;
//	2 top-level panels
	private JPanel equationPanel;
//	Components of equation panel
	private JPanel inputPanel;
	private JTLabel equationLabel;
	private JTextField equationText;
	private JButton equateButton;
	private ActionListener listener;
//	Panels to add to tabbed panel
	private JPanel truthTablePanel;
	private int gridRows, gridColumns;	
//	Equation controller object
	private Equation currentEquation;
	
	public WholeFrame() {

		getContentPane().setBackground(Color.red);
		equationPanel = new JPanel(new BorderLayout());
		inputPanel = new JPanel(new GridLayout(2, 1));
		inputPanel.setBackground(new Color(194, 200, 199));
		equationPanel.setBackground(new Color(194, 200, 199));
		equationLabel = new JTLabel("Input function here:", new Color(194, 200, 199), Color.black);
		equationText = new JTextField("a'b+ab'");
		inputPanel.add(equationLabel);
		inputPanel.add(equationText);
		equateButton = new JButton("Equate!");
		listener = new ButtonListener();
		equateButton.addActionListener(listener);
		equationPanel.add(inputPanel, BorderLayout.CENTER);
		equationPanel.add(equateButton, BorderLayout.EAST);
		currentEquation = new Equation(equationText.getText());
		truthTablePanel = createTruthTablePanel(currentEquation);
		add(equationPanel, BorderLayout.NORTH);
		add(truthTablePanel, BorderLayout.CENTER);
		setSize(APPLET_WIDTH, APPLET_HEIGHT);
		
	}
	
	private JPanel createTruthTablePanel(Equation inputEquation) {

		gridColumns = inputEquation.getVariableNumber() + 1;
		gridRows = (int) Math.pow(2, inputEquation.getVariableNumber()) + 1;	
		JPanel returnPanel = new JPanel(new GridLayout(gridRows, gridColumns));
		returnPanel.setBackground(new Color(237, 233, 218));	
		for (int i = 0; i < inputEquation.getVariableNumber(); i++) {
			
			returnPanel.add(new JTLabel(inputEquation.getVariables().get(i).getCharValue().toString(), new Color(247, 244, 176), Color.black));
		}
		returnPanel.add(new JTLabel("Output", new Color(247, 244, 176), Color.black));
		for (int i = 0; i < (int) Math.pow(2, inputEquation.getVariableNumber()); i++) {
			
			String binaryValueRow = currentEquation.binaryValue(i);
			for (int j = 0; j < inputEquation.getVariableNumber(); j++) {
				
//				System.out.println(Character.toString(binaryValueRow.charAt(j)));
				returnPanel.add(new JTLabel(Character.toString(binaryValueRow.charAt(j)), new Color(237, 233, 218), Color.black));
			}
			returnPanel.add(new JTLabel(Integer.toString(currentEquation.solveForInputString(binaryValueRow)), new Color(247, 208, 198), Color.black));
		}
		System.out.println(currentEquation.canonicalSOP());
		System.out.println(currentEquation.canonicalPOS());
		return returnPanel;
	}

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
}
