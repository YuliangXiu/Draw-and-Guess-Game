package AboutServer;

import java.io.*;
import java.net.*;
import java.util.*;

import AboutClient.GameMessage;
import AboutClient.ImageManage;
import AboutRegister.LoginMessage;

public class PlayServer {

	private static int portNo = 8888;
	ServerSocket gameServer = null;
	Socket gameSocket = null;
	Mysql sqlConnect = null;
	// Save Player message
	ArrayList<GameClient> gameClients = new ArrayList<GameClient>();
	private String[] playerMessages = new String[3];
	private boolean isStart = false;
	private static int isClientTurn = 0;// Control which player start to paint
	private static int isPreClientTurn = 0;

	PlayServer() {
		serverConnect();
	}

	// Connect to the SQL and start server
	private void serverConnect() {
		try {
			gameServer = new ServerSocket(portNo);
			System.out.println("��ҿͻ����Ѿ�����");
			sqlConnect = new Mysql();
			System.out.println("��ҿͻ��˳ɹ��������ݿ�.");
			isStart = true;
			while (isStart) {
				gameSocket = gameServer.accept();
				GameClient c = new GameClient(gameSocket);
				System.out.println("��ң�һ������Ҽ���");
				new Thread(c).start();
			}
		} catch (IOException e) {
			System.out
					.println("��ң��˿��ظ�����");
		} finally {
			try {
				gameServer.close();
			} catch (IOException e) {
				System.out
						.println("��ң�δ֪����");
			}
		}
	}

	class GameClient implements Runnable {
		private ObjectInputStream dis = null;
		private ObjectOutputStream dos = null;
		private String playerName = null;
		private boolean startGame = false;
		private boolean isConnect = false;
		private Socket cSocket = null;
		private int clientID = 1;

		GameClient(Socket clientSocket) {
			this.cSocket = clientSocket;
			clientConnect();
		}

		public void run() {
			// Judge User message
			checkUserIsAvailable();
			// Get User message
			clientID = gameClients.indexOf(this);
			String playerMessage = sqlConnect.selectPlayerMessage(playerName);
			playerMessages[clientID] = playerMessage;

			String command;// ������������ܣ�Ȼ����ݾ�������ִ�з�����IO������

			while (isConnect) {
				try {
					command = dis.readUTF();
					System.out.println(command);
					analyze(command);
				} catch (IOException e) {
					System.out
							.println("��ң�����Ѿ��˳���Ϸ");
					clientExit();
				} catch (ClassNotFoundException e) {
					System.out.println("��ң��Ҳ���class");
				}
			}

		}

		// ������������ʼ��ͻ���֮�䴫������
		private void clientConnect() {
			try {
				dis = new ObjectInputStream(cSocket.getInputStream());
				dos = new ObjectOutputStream(cSocket.getOutputStream());
				isConnect = true;
			} catch (IOException e) {
				isConnect = false;
				System.out.println("��ң��Բ��������Ӳ�����������");
				clientExit();
			}
		}

		// Check message
		private void checkUserIsAvailable() {
			try {
				while (startGame == false) {
					System.out
							.println("������������֤�����Ϣ�����Ժ󡣡�������");
					LoginMessage lm = (LoginMessage) dis.readObject();
					String player = lm.getUserName();
					String password = lm.getPassword();
					boolean isAvailable = sqlConnect.checkPlayerPassword(
							player, password);
					lm.setIsAvailable(isAvailable);
					dos.writeObject(lm);
					// Checking is over
					if (isAvailable == true) {
						gameClients.add(this);
						startGame = true;
						this.playerName = player;
					}
				}
			} catch (IOException e) {
				System.out.println("��ң�����Ѿ��رճ���");
				clientExit();
			} catch (ClassNotFoundException e) {
				System.out
						.println("��ң�û���ҵ���½��Ϣ����");
				clientExit();
			}
		}

		// When User is exit , the server should do something
		private void clientExit() {
			isConnect = false;
			gameClients.remove(this);
			System.out.println("ʣ�µ���ҿͻ�������:"
					+ gameClients.size());
			try {
				System.out.println("��� " + playerName + "  �Ѵ���");
				if (dis != null)
					dis.close();
				if (dos != null)
					dos.close();
				if (cSocket != null)
					cSocket.close();
			} catch (IOException e) {
				System.out.println("������ :socket�Ѿ��������");
			}
		}

		// �����ݹ����������֮���ڴ˴����д���
		private void analyze(String gameCommand) throws IOException,
				ClassNotFoundException {
			if (gameCommand.equals("ready")) {
				sendGameInit();
			} else if (gameCommand.equals("sendPicture")) {
				sendPicture();
			} else if (gameCommand.equals("chat")) {
				sendChatMessage();
			} else if (gameCommand.equals("sendPlayerMessage")) {
				sendPlayerMessage();
			} else if (gameCommand.equals("sendQuestions")) {
				sendQuestions();
			} else if (gameCommand.equals("sendAnswer")) {
				sendAnswer();
			}
			// else if (gameCommand.equals("addPoint")) {
			// addPoint();
			// }
		}

		// add point
		// The function has not achieved.

		// private void addPoint() {
		// GameClient draw = gameClients.get(isPreClientTurn);
		// GameClient guess = gameClients.get(clientID);
		// String drawName = draw.playerName;
		// String guessName = guess.playerName;
		// }

		// Control the start of game
		private void sendGameInit() throws IOException {
			// When the number of player is larger than 2,game begin.
			if (gameClients.size() >= 2) {
				sendQuestions();
				sendPlayerMessage();
				GameClient c = gameClients.get(isClientTurn);
				c.sendIsTurn();
			}
		}

		// send chat message

		private void sendChatMessage() throws IOException {
			String chatMessage = dis.readUTF();
			for (GameClient gc : gameClients) {
				gc.dos.writeUTF("chat");
				gc.dos.writeUTF(chatMessage);
				gc.dos.flush();
				System.out.println(chatMessage);
			}
		}

		// send image

		private void sendPicture() throws IOException, ClassNotFoundException {
			ImageManage monitor = (ImageManage) dis.readObject();
			for (GameClient gc : gameClients) {
				gc.dos.writeUTF("resivePicture");
				gc.dos.writeObject(monitor);
			}
		}

		// send player message

		private void sendPlayerMessage() throws IOException {
			GameMessage gm = new GameMessage();
			gm.setPlayerMessage(playerMessages);
			for (GameClient c : gameClients) {
				c.dos.writeUTF("resiveUserMessage");
				c.dos.writeObject(gm);
				System.out.println("have send UserMessage");
			}
		}

		// send questions

		private void sendQuestions() throws IOException {
			String[] questions = sqlConnect.selectQuestions();
			GameMessage gm = new GameMessage();
			gm.setQuestions(questions);

			for (GameClient c : gameClients) {
				c.dos.writeUTF("resiveQuestions");
				c.dos.writeObject(gm);
				System.out.println("have send questions");
			}
		}

		// send answer

		private void sendAnswer() throws IOException {
			String answer = dis.readUTF();
			System.out.println(answer);
			for (GameClient gc : gameClients) {
				gc.dos.writeUTF("resiveAnswer");
				gc.dos.flush();
				gc.dos.writeUTF(answer);
				gc.dos.flush();
				System.out.println("��ͼ���");
			}

			// Control who is draw and who is guess

			isPreClientTurn = isClientTurn;
			isClientTurn++;
			if (isClientTurn >= gameClients.size()) {
				isClientTurn = 0;
			}
			GameClient c = gameClients.get(isClientTurn);
			c.sendIsTurn();
			sendQuestions();
		}

		private void sendIsTurn() throws IOException {
			if (clientID == isClientTurn) {
				dos.writeUTF("isTurn");
				dos.flush();
				System.out.println("clientID for " + clientID + " turn");
			}
		}
	}
}