import java.awt.Color;
import javax.swing.*;
import javax.swing.border.*;

public class JTLabel extends JLabel {

	public JTLabel(String title, Color backgroundColor, Color textColor) {
		super(title);
		setHorizontalAlignment(JLabel.CENTER);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		setOpaque(true);
		setBackground(backgroundColor);
		setForeground(textColor);
	}
}
