package AboutClient;

import java.awt.*;
import javax.swing.*;

//�û���Ϣ��panel

public class PlayerPanel extends JPanel {
	JTextArea messageArea = null;
	String b = "                    ";

	String userName = null;
	GameConnection gameNet = null;

	public PlayerPanel(String userName, GameConnection gameNet) {
		this.userName = userName;
		this.gameNet = gameNet;

		initComponent();
	}

	// Initialize Interface

	private void initComponent() {
		setLayout(new BorderLayout());
		JScrollPane messageScrollPane = new JScrollPane();
		messageArea = new JTextArea();
		messageArea.setEditable(false);
		messageArea.setOpaque(true);

		String s = "�û���" + b + "����";
		JLabel userMessageLab = new JLabel(s);
		add(userMessageLab, BorderLayout.NORTH);
		add(messageScrollPane, BorderLayout.CENTER);
		messageScrollPane.getViewport().add(messageArea, null);
		messageScrollPane.getViewport().setOpaque(true);
		messageScrollPane.setOpaque(true);
	}
}
