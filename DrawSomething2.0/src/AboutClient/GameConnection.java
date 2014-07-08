package AboutClient;

import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import AboutRegister.LoginMessage;

//充当总客户端的角色


public class GameConnection implements Runnable {
	public static int portNo = 8888;
	private InetAddress address = null;
	private static Socket serverSocket = null;
	ObjectOutputStream dos = null;
	ObjectInputStream dis = null;

	private boolean isConnect = false;

	private GameInterface gameinterface = null;
	private DrawPanel drawpanel = null;
	private PlayerPanel messagepanel = null;
	private ChatPanel chatpanel = null;
	private boolean isTurn = false;
	private boolean isGuessTurn = true;

	public GameConnection() {
		connect();
	}

	public void run() {
		while (isConnect) {
			try {
				String command = dis.readUTF();
				try {
					analyze(command);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				exit();
			}
		}
	}

	public void startGame() {
		Thread thread = new Thread(this);
		thread.start();
	}

	void connect() {
		try {
			address = InetAddress.getByName("localhost");
			serverSocket = new Socket(address, portNo);
			dos = new ObjectOutputStream(serverSocket.getOutputStream());
			dis = new ObjectInputStream(serverSocket.getInputStream());
			isConnect = true;
			System.out.println("成功连接到服务器");
		} catch (IOException e) {
			isConnect = false;
			JOptionPane.showMessageDialog(null, "无法连接到服务器");
			System.exit(0);
		}
	}

	private void analyze(String command) throws Exception {
		System.out.println(command);
		if (command.equals("resivePicture")) {
			resivePicture();
		} else if (command.equals("chat")) {
			resiveChatMessage();
		} else if (command.equals("resiveUserMessage")) {
			resivePlayerMessage();
		} else if (command.equals("resiveQuestions")) {
			resiveQuestions();
		} else if (command.equals("resiveAnswer")) {
			resiveAnswer();
		} else if (command.equals("isTurn")) {
			isMyTurn();
			isTurn = true;
			isGuessTurn = false;
			drawpanel.isTurn = true;
		}
	}

	private void isMyTurn() {
		JOptionPane.showMessageDialog(null, "轮到你了");
	}

	private void exit() {
		System.out.println("Close Client");
		isConnect = false;
		try {
			if (dis != null)
				dis.close();
			if (dos != null)
				dos.close();
			if (serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			}
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public LoginMessage checkLoginMessage(LoginMessage lm) {
		try {
			dos.writeObject(lm);
			LoginMessage fbLm = (LoginMessage) dis.readObject();
			return fbLm;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	void sendPicture(BufferedImage image) {
		try {
			if (isTurn) {
				dos.writeUTF("sendPicture");
				ImageManage monitor = new ImageManage(image);
				dos.writeObject(monitor);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendIsReady() {
		try {
			dos.writeUTF("ready");
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendQuestions() {
		try {
			dos.writeUTF("sendQuestions");
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendChatMessage(String chatMessage) {
		try {
			dos.writeUTF("chat");
			dos.flush();
			dos.writeUTF(chatMessage);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//目前未实现，故先注掉
//	public void sendAddPoint() {
//		try {
//			dos.writeUTF("addPoint");
//			dos.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	void sendPlayerMessage() {
		try {
			dos.writeUTF("sendPlayerMessage");
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void sendAnswer(String answer) {
		try {
			if (isTurn) {
				dos.writeUTF("sendAnswer");
				dos.flush();
				dos.writeUTF(answer);
				dos.flush();

				isTurn = false;
				drawpanel.isTurn = false;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void resivePicture() throws IOException, ClassNotFoundException {
		ImageManage monitor = (ImageManage) dis.readObject();

		BufferedImage image = monitor.getBufferedImage();
		if (!isTurn) {
			drawpanel.image = image;
			drawpanel.repaint();
		}
	}

	private void resiveQuestions() throws IOException, ClassNotFoundException {
		GameMessage gm = (GameMessage) dis.readObject();
		String[] questions = gm.getQusetions();
		drawpanel.q1.setText(questions[0]);
		drawpanel.q2.setText(questions[1]);
		drawpanel.q3.setText(questions[2]);
		drawpanel.q4.setText(questions[3]);
		System.out.println("questions is really");
	}

	private void resiveChatMessage() {
		try {
			String chatMessage = dis.readUTF();
			System.out.println(chatMessage);
			String chatMessages = chatpanel.chatJTA.getText();
			chatpanel.chatJTA.setText(chatMessages + chatMessage + "\n");
		} catch (IOException e) {
			System.out.println("Can't get chat message!");
		}
	}

	private void resivePlayerMessage() throws IOException,
			ClassNotFoundException {
		GameMessage gm = (GameMessage) dis.readObject();
		String[] pMessage = gm.getPlayerMessage();
		String pMessages = "";
		for (String p : pMessage)
			pMessages = pMessages + "\n" + p;
		messagepanel.messageArea.setText(pMessages.trim());
	}

	private void resiveAnswer() throws IOException {
		String answer = dis.readUTF();
		drawpanel.answer = answer;
		System.out.println(answer);
		if (isGuessTurn) {
			drawpanel.guess();
		}
		isGuessTurn = true;
	}

	public void setGamePad(GameInterface gameinterface) {
		this.gameinterface = gameinterface;
		this.drawpanel = gameinterface.myDrawPanel;
		this.messagepanel = gameinterface.messagePanel;
		this.chatpanel = gameinterface.chatPanel;
	}
}
