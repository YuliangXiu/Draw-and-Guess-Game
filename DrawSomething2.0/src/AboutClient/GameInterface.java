package AboutClient;

import java.awt.*;
import javax.swing.*;

public class GameInterface extends JFrame {
	private String playername = null;
	GameConnection gameNet = null;

	DrawPanel myDrawPanel = null;
	PlayerPanel messagePanel = null;
	ChatPanel chatPanel = null;

	public GameInterface(String playername, GameConnection gameNet) {
		this.playername = playername;
		this.gameNet = gameNet;

		initComponent();

		gameNet.setGamePad(this);
		gameNet.sendIsReady();
	}

	// Initialize Interface

	private void initComponent() {

		setTitle("Äã»­ÎÒ²Â");
		setBounds(50, 100, 800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		// set background
		// http://wenku.baidu.com/view/3db3c2e7524de518964b7d6a.html
		
		ImageIcon drawImg = new ImageIcon("Gameimage.jpg");
		JLabel imgLabel = new JLabel(drawImg);
		imgLabel.setBounds(0, 0, drawImg.getIconWidth(), drawImg
				.getIconHeight());
		getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		Container drawCont = getContentPane();
		drawCont.setLayout(null);
		((JPanel) drawCont).setOpaque(false);

		myDrawPanel = new DrawPanel(this, playername, gameNet);
		messagePanel = new PlayerPanel(playername, gameNet);
		chatPanel = new ChatPanel(playername, gameNet);
		myDrawPanel.setBounds(15,15, 496, 441);
		messagePanel.setBounds(515, 15, 264, 100);
		chatPanel.setBounds(515, 110, 268, 350);

		drawCont.add(myDrawPanel);
		drawCont.add(messagePanel);
		drawCont.add(chatPanel);

		validate();
	}
}
