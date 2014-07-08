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
	 * ��ʼ������
	 */
	private void init() {
		setTitle("ע����Ϣ");
		setBounds(250, 100, 300, 500);
		ImageIcon login = new ImageIcon("login.jpg");
		// ���ñ���
		//ImageIcon enBg = new ImageIcon("CreateInterfaceimage.jpg");
		JLabel label = new JLabel(login);
		label.setBounds(0, 0, login.getIconWidth(),login.getIconHeight());
		getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		((JPanel) getContentPane()).setOpaque(false);

		JPanel regPanel = new JPanel();
		regPanel.setLayout(null);

		IDLab = new JLabel("���û���:");
		IDLab.setFont(new Font("����", Font.BOLD, 13));
		IDjtf = new JTextField();
		passwordLab = new JLabel("�趨����:");
		passwordLab.setFont(new Font("����", Font.BOLD, 13));
		passwordjtf = new JPasswordField();
		passwordAgainLab = new JLabel("�ظ�����:");
		passwordAgainLab.setFont(new Font("����", Font.BOLD, 13));
		passwordAgainjtf = new JPasswordField();

		IDLab.setBounds(50, 290, 100, 20);
		IDjtf.setBounds(130, 290, 100, 20);
		passwordLab.setBounds(50, 340, 100, 20);
		passwordjtf.setBounds(130, 340, 100, 20);
		passwordAgainLab.setBounds(50, 390, 100, 20);
		passwordAgainjtf.setBounds(130, 390, 100, 20);

		JButton ensureBtn = new JButton("ȷ��");
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
				JOptionPane.showMessageDialog(this, "�û�������Ϊ��!����������");
			} else if (password.trim().equals("")) {
				JOptionPane.showMessageDialog(this, "���벻��Ϊ��!����������");
				passwordAgainjtf.setText("");
			} else if (passwordAgain.equals(password) == false) {
				JOptionPane.showMessageDialog(this, "������������벻һ��,����������");
				passwordjtf.setText("");
				passwordAgainjtf.setText("");
			} else if (regNet.checkUserName(userName) == true) {
				JOptionPane.showMessageDialog(this, "�û����Ѿ�����,�����������û���");
				IDjtf.setText("");
			} else {
				regNet.insertUserMessage(userName, password);
				JOptionPane.showMessageDialog(this, "ע��ɹ�!���¼��Ϸ!");
				regNet.exit();
				setVisible(false);
			}
		}
	}
}
