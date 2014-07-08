package AboutClient;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/**���첿�ֵ�panel���ּ��¼�
 * ����panel�ֿ�д�������岼��
 */

public class ChatPanel extends JPanel implements ActionListener {
	GameConnection gameNet = null;

	String playerName = null;

	JScrollPane chatJSP = null;
	JTextArea chatJTA = null;
	JTextField chatJTF = null;
	JButton chatBtn = null;

	public ChatPanel(String playerName, GameConnection gameNet) {

		super();
		this.playerName = playerName;
		this.gameNet = gameNet;

		initComponent();
	}

	private void initComponent() {
		setLayout(null);
		setOpaque(false);

		chatJSP = new JScrollPane();
		chatJTA = new JTextArea();
		chatJTA.setOpaque(false);
		chatJTA.setLineWrap(true);
		chatJTA.setWrapStyleWord(true);
		chatJTA.setEditable(false);
		chatJSP.getViewport().add(chatJTA);
		chatJSP.getViewport().setOpaque(false);
		chatJSP.setOpaque(false);

		chatJTF = new JTextField();
		chatJTF.setActionCommand("sendMessage");
		chatJTF.addActionListener(this);

		chatBtn = new JButton("����");
		chatBtn.setActionCommand("sendMessage");
		chatBtn.addActionListener(this);

		chatJSP.setBounds(0, 0, 264, 304);
		chatJTF.setBounds(0, 310, 200, 37);
		chatBtn.setBounds(202, 310, 62, 37);

		add(chatJSP);
		add(chatJTF);
		add(chatBtn);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("sendMessage")) {
			String chatMessage = chatJTF.getText();
			gameNet.sendChatMessage(playerName + " : " + chatMessage);
			chatJTF.setText("");
		} else {
			JOptionPane.showMessageDialog(null, "���������δ֪����!");
		}
	}
}
