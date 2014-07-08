package AboutRegister;

import java.io.*;
import java.net.*;

import javax.swing.*;

public class RegistConnect {
	public static int portNo = 8889;
	private InetAddress address = null;
	private static Socket serverSocket = null;
	private static String separate = ":";

	String command = null;
	BufferedReader br = null;
	PrintWriter pw = null;

	private boolean isConnection;

	public static void main(String[] args) {
		RegistConnect n = new RegistConnect();
	}

	public RegistConnect() {
		init();
	}

	void init() {
		try {
			address = InetAddress.getByName("localhost");
			serverSocket = new Socket(address, portNo);
			br = new BufferedReader(new InputStreamReader(serverSocket
					.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					serverSocket.getOutputStream())), true);
			isConnection = true;
		} catch (IOException e) {
			isConnection = false;
			JOptionPane.showMessageDialog(null, "无法连接到服务器");
			System.exit(0);
		}
	}

	// Check whether the User name is exist.

	boolean checkUserName(String userName) {
		String command = "checkUserName";
		pw.println(command + separate + userName);
		try {
			String str = br.readLine();
			if (str.equals("true")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Can't connect to Server");
		}
		return false;
	}

	// add message

	void insertUserMessage(String name, String password) {
		String command = "insertUserMessage";
		pw.println(command + separate + name + separate + password);
	}

	// close the connection of client and server

	void exit() {
		System.out.println("Close the client");
		try {
			pw.println("exit");
			if (pw != null)
				pw.close();
			if (br != null)
				br.close();
			if (serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
