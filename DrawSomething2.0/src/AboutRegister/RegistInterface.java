package AboutRegister;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

// CreateID

public class RegistInterface extends JFrame implements ActionListener {

	RegistConnect regNet = null;

	JLabel IDLab, passwordLab, passwordAgainLab;
	JTextField IDjtf, passwordjtf, passwordAgainjtf;

//	public static void main(String[] args) {
//		RegistInterface test = new RegistInterface();
//	}

	public RegistInterface() {
		regNet = new RegistConnect();
		init();
	}

	/**
	 * 初始化界面
	 */
	private void init() {
		setTitle("注册信息");
		setBounds(250, 100, 300, 500);
		ImageIcon login = new ImageIcon("login.jpg");
		// 设置背景
		//ImageIcon enBg = new ImageIcon("CreateInterfaceimage.jpg");
		JLabel label = new JLabel(login);
		label.setBounds(0, 0, login.getIconWidth(),login.getIconHeight());
		getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		((JPanel) getContentPane()).setOpaque(false);

		JPanel regPanel = new JPanel();
		regPanel.setLayout(null);

		IDLab = new JLabel("新用户名:");
		IDLab.setFont(new Font("黑体", Font.BOLD, 13));
		IDjtf = new JTextField();
		passwordLab = new JLabel("设定密码:");
		passwordLab.setFont(new Font("黑体", Font.BOLD, 13));
		passwordjtf = new JPasswordField();
		passwordAgainLab = new JLabel("重复密码:");
		passwordAgainLab.setFont(new Font("黑体", Font.BOLD, 13));
		passwordAgainjtf = new JPasswordField();

		IDLab.setBounds(50, 290, 100, 20);
		IDjtf.setBounds(130, 290, 100, 20);
		passwordLab.setBounds(50, 340, 100, 20);
		passwordjtf.setBounds(130, 340, 100, 20);
		passwordAgainLab.setBounds(50, 390, 100, 20);
		passwordAgainjtf.setBounds(130, 390, 100, 20);

		JButton ensureBtn = new JButton("确定");
		ensureBtn.setBounds(120, 430, 60, 30);
		ensureBtn.setBackground(Color.yellow);
		ensureBtn.setActionCommand("ensure");
		ensureBtn.addActionListener(this);
		regPanel.add(ensureBtn);

		regPanel.add(IDLab);
		regPanel.add(IDjtf);
		regPanel.add(passwordLab);
		regPanel.add(passwordjtf);
		regPanel.add(passwordAgainLab);
		regPanel.add(passwordAgainjtf);

		regPanel.setOpaque(false);
		add(regPanel);

		setVisible(true);
		setResizable(false);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				regNet.exit();
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("ensure")) {
			String userName = IDjtf.getText();
			String password = passwordjtf.getText();
			String passwordAgain = passwordAgainjtf.getText();

			if (userName.trim().equals("")) {
				JOptionPane.showMessageDialog(this, "用户名不能为空!请重新输入");
			} else if (password.trim().equals("")) {
				JOptionPane.showMessageDialog(this, "密码不能为空!请重新输入");
				passwordAgainjtf.setText("");
			} else if (passwordAgain.equals(password) == false) {
				JOptionPane.showMessageDialog(this, "两次输入的密码不一致,请重新输入");
				passwordjtf.setText("");
				passwordAgainjtf.setText("");
			} else if (regNet.checkUserName(userName) == true) {
				JOptionPane.showMessageDialog(this, "用户名已经存在,请重新输入用户名");
				IDjtf.setText("");
			} else {
				regNet.insertUserMessage(userName, password);
				JOptionPane.showMessageDialog(this, "注册成功!请登录游戏!");
				regNet.exit();
				setVisible(false);
			}
		}
	}
}
