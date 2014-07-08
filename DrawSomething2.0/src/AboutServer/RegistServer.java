package AboutServer;

import java.io.*;
import java.net.*;
import java.util.*;

//The CreateServer is used to register and send the message into SQL

public class RegistServer {
	private static int portNo = 8889;
	ServerSocket regServer = null;
	Socket clientSocket = null;
	Mysql sqlConnect;
	Vector<RegClient> regClients = new Vector<RegClient>();

	private boolean isStart = false;// Control the start of register server
	private static int clientNumber = 0;

	RegistServer() {
		try {
			regServer = new ServerSocket(portNo);
			System.out.println("服务器已经开启");
			sqlConnect = new Mysql();
			System.out.println("服务器成功连接数据库");
			isStart = true;
			while (isStart) {
				clientSocket = regServer.accept();
				System.out.println("服务器：一个玩家加入游戏");
				RegClient c = new RegClient(clientSocket);
				regClients.addElement(c);
				new Thread(c).start();
				System.out.println("服务器：玩家数目是——"
						+ ++clientNumber);
			}
		} catch (IOException e) {
			System.out
					.println("服务器：端口重复错误！");
		} finally {
			try {
				regServer.close();
			} catch (IOException e) {
				System.out.println("服务器：未知错误！");
				e.printStackTrace();
			}
		}
	}

	// The register User's client is connected CreateServer

	class RegClient implements Runnable {

		BufferedReader br = null;
		PrintWriter pw = null;
		private Socket cSocket;

		private boolean isConnect = false;

		// Judge whether the client is connected to CreatServer

		RegClient(Socket clientSocket) {
			this.cSocket = clientSocket;
			try {
				// set IO
				br = new BufferedReader(new InputStreamReader(clientSocket
						.getInputStream()));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						clientSocket.getOutputStream())), true);
				isConnect = true;
			} catch (IOException e) {
				isConnect = false;
				e.printStackTrace();
			}

		}

		public void run() {
			try {
				String str = null;
				while ((str = br.readLine()) != null) {
					String socketCommand[] = str.split(":");
					// 因为在CreateConnection中的方法有“：”，故调用split方法,去除指定符号
					analyze(socketCommand);
				}
			} catch (IOException e) {
			} finally {
				try {
					if (br != null)
						br.close();
					if (pw != null)
						pw.close();
					if (cSocket != null)
						cSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 汇总命令，集中处理，与GameServer方法形式类似

		private void analyze(String[] socketCommand) {
			String[] strs = socketCommand;
			String cmd = strs[0];

			if (cmd.equals("checkUserName")) {
				boolean b1 = sqlConnect.checkPlayerName(strs[1]);

				pw.println(b1);

			} else if (cmd.equals("insertUserMessage")) {
				sqlConnect.insertPlayerMessage(strs[1], strs[2]);

			} else if (cmd.equals("exit")) {
				try {
					if (br != null)
						br.close();
					if (pw != null)
						pw.close();
					if (cSocket != null) {
						cSocket.close();
						cSocket = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
