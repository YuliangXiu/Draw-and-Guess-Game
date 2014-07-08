package AboutRegister;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import AboutClient.GameConnection;
import AboutClient.GameInterface;

public class LoginFrame extends JFrame implements ActionListener {
	JTextField userField;
	private JTextField passwordField;
	private GameConnection gameNet;

	public static void main(String[] args) {
		LoginFrame login = new LoginFrame();
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setVisible(true);
	}

	public LoginFrame() {
		initComponent();
		gameNet = new GameConnection();
	}

	// Initialize Interface

	private void initComponent() {

		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\DS\\DrawSomething2.0\\draw.png"));
		setTitle("欢迎来到“你画我猜”，请完成注册后登陆游戏！");
		setBounds(150, 20, 500, 400);
		setResizable(false);

		// set login image
		ImageIcon loginImage = new ImageIcon("loginimage.jpg");
		JLabel imgLabel = new JLabel(loginImage);
		getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		imgLabel.setBounds(0, 0, 500, 400);
		Container loginContainer = getContentPane();
		((JPanel) loginContainer).setOpaque(false);

		// set button and other component
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(null);

		JLabel userLabel = new JLabel("    用户名");
		JLabel passwordLabel = new JLabel("     密码");
		userField = new JTextField();
		passwordField = new JPasswordField();

		// setLayout
		userLabel.setBounds(230, 80, 140, 40);
		userLabel.setBackground(Color.ORANGE);
		userField.setBounds(340, 80, 140, 40);
		passwordLabel.setBounds(230, 110, 140, 40);
		passwordLabel.setBackground(Color.ORANGE);
		passwordField.setBounds(340, 110, 140, 40);

		JButton startButton = new JButton("开 始 游 戏");
		JButton helpButton = new JButton("游 戏 说 明");
		JButton enrollButton = new JButton("注 册 帐 号");
		
		userField.setFont(new Font("楷体_GB2312", Font.BOLD, 20));
		userField.setFont(new Font("楷体_GB2312", Font.BOLD, 20));
		userLabel.setFont(new Font("楷体_GB2312", Font.BOLD, 20));
		passwordLabel.setFont(new Font("楷体_GB2312", Font.BOLD, 20));
		startButton.setFont(new Font("楷体_GB2312", Font.BOLD, 15));
		startButton.setBackground(Color.orange);
		helpButton.setFont(new Font("楷体_GB2312", Font.BOLD, 15));
		helpButton.setBackground(Color.orange);
		enrollButton.setFont(new Font("楷体_GB2312", Font.BOLD, 15));
		enrollButton.setBackground(Color.orange);
		
		startButton.setBounds(10, 300, 130, 30);
		helpButton.setBounds(180,300, 130, 30);
		enrollButton.setBounds(350, 300, 130,30);

		// set listener
		startButton.setActionCommand("start");
		enrollButton.setActionCommand("enroll");
		helpButton.setActionCommand("help");

		startButton.addActionListener(this);
		enrollButton.addActionListener(this);
		helpButton.addActionListener(this);

		// add component
		loginPanel.add(userLabel);
		loginPanel.add(userField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);

		loginPanel.add(startButton);
		loginPanel.add(helpButton);
		loginPanel.add(enrollButton);

		loginPanel.setOpaque(false);

		loginContainer.add(loginPanel);
	}

	// set event
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("start")) {
			String userName = userField.getText();
			String password = passwordField.getText();
			if (password.trim().equals("")) {
				JOptionPane.showMessageDialog(LoginFrame.this,
						"Please Input password");
			} else {
				// push the login message into the class LoginMessage
				LoginMessage lm = new LoginMessage(userName, password);
				LoginMessage fbLm = gameNet.checkLoginMessage(lm);
				if (fbLm.getIsAvailable() == false) {
					JOptionPane.showMessageDialog(this,
							"IDname or password is wrong!");
				} else if (fbLm.getIsOnline() == true) {
					JOptionPane.showMessageDialog(this, "The ID is online");
				} else {
					JOptionPane.showMessageDialog(this, "Welcome Game!");
					gameNet.startGame();
					GameInterface gp = new GameInterface(userName, gameNet);
					this.setVisible(false);
				}
			}
		} else if (cmd.equals("enroll")) {
			// open the RegisterInterface
			RegistInterface enroll = new RegistInterface();
		} else if (cmd.equals("help")) {
			JFrame helpFrame = new JFrame("Instruction");
			helpFrame.setBounds(400, 70, 600, 455);
			ImageIcon helpImg = new ImageIcon("regulation.jpg");
			JLabel helpLabel = new JLabel(helpImg);
			helpFrame.add(helpLabel);
			helpFrame.setVisible(true);
			helpFrame.setResizable(false);
		}
	}
}
