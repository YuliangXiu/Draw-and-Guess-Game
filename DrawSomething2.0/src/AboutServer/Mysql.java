package AboutServer;

import java.sql.*;

//关于SQL的下载安装与配置，参考马士兵视频教学
//java中对于SQL的操作语句，参考http://blog.sina.com.cn/s/blog_4c5ec41a010007an.html

public class Mysql {
	String Driver = "com.mysql.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/game_information";
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	String sql = null;

	public static void main(String[] args) {
		Mysql test = new Mysql();
		test.selectQuestions();
	}

	// Send the register message into SQL

	public void insertPlayerMessage(String name, String password) {
		sql = "insert player_information(playername, password, online) "
				+ "values('" + name + "', '" + password + "', '" + 0 + "')";
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(URL, "root", "2013");
			st = con.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			System.out.println("Syntax Error:" + e.getMessage());
		}
		exitMySQL();
	}

	// Judge whether the IDname exists inSQL

	public boolean checkPlayerName(String userName) {
		sql = "select playername from player_information where playername = '"
				+ userName + "';";
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL, "root", "2013");
			st = con.createStatement();
			rs = st.executeQuery(sql);
			String checkName = "";
			while (rs.next()) {
				checkName = rs.getString("playername");
			}
			if (checkName.equals(userName))
				return true;
			else
				return false;
		} catch (SQLException e) {
			System.out.println("Syntax Error: " + e.getMessage());
		}
		exitMySQL();
		return false;
	}

	// Judge whether the password is right,if it is right ,return true

	public boolean checkPlayerPassword(String userName, String password) {
		sql = "select * from player_information where playername = '"
				+ userName + "';";
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL, "root", "2013");
			st = con.createStatement();
			rs = st.executeQuery(sql);
			String checkPassword = "";
			while (rs.next()) {
				checkPassword = rs.getString("password");
			}
			if (checkPassword.equals(password))
				return true;
			else
				return false;
		} catch (SQLException e) {
			System.out.println("Syntax Error:  " + e.getMessage());
		}
		exitMySQL();
		return false;
	}

	// Judge whether the ID is Online
	// ??? The function is not achieved

	public boolean checkIsOnline(String userName) {
		sql = "select * from player_information where playername = '"
				+ userName + "';";
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL, "root", "2013");
			st = con.createStatement();
			rs = st.executeQuery(sql);
			String isOnline = "";
			while (rs.next()) {
				isOnline = rs.getString("online");
			}
			if (isOnline.equals("1"))
				return true;
			else
				return false;
		} catch (SQLException e) {
			System.out.println("Syntax Error: " + e.getMessage());
		}
		exitMySQL();
		return false;
	}

	// Operate the point and send the message into SQL

	public void addPoint(String playername) {
		sql = "select * from player_information where playername = '"
				+ playername + "';";
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL, "root", "2013");
			st = con.createStatement();
			rs = st.executeQuery(sql);
			String point = null;
			while (rs.next()) {
				point = rs.getString("point");
			}
			int newPoint = Integer.parseInt(point) + 1;

			sql = "update player_information set point = '" + newPoint
					+ "' where playername = '" + playername + "';";
			st.execute(sql);

		} catch (SQLException e) {
			System.out.println("Syntax Error:  " + e.getMessage());
		}
		exitMySQL();
	}

	// ??? Set the ID's online state,the function is not achieved.

	public void setIsOnline(String userName, boolean isOnline) {
		int i = 0;
		if (isOnline) {
			i = 1;
		}
		sql = "update player_information set online = '" + i
				+ "' where playername = '" + userName + "';";
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL, "root", "2013");
			st = con.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			System.out.println("Syntax Error:" + e.getMessage());
		}
		exitMySQL();
	}

	// Select the questions and return questions

	String[] selectQuestions() {
		sql = "SELECT * FROM game_questions ORDER BY RAND()  LIMIT 1";
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL, "root", "2013");
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String q1 = rs.getString("questionOne");
				String q2 = rs.getString("questionTwo");
				String q3 = rs.getString("questionThree");
				String q4 = rs.getString("questionForth");
				String[] questions = { q1, q2, q3, q4 };
				return questions;
			}
		} catch (SQLException e) {
			System.out.println("Syntax Error:" + e.getMessage());
		}
		return null;
	}

	// Get and send player's message

	String selectPlayerMessage(String playerName) {
		sql = "select * from player_information where playername = '"
				+ playerName + "';";
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL, "root", "2013");
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String playerMessage = rs.getString("playername");
				return playerMessage;
			}
		} catch (SQLException e) {
			System.out.println("Syntax Error:" + e.getMessage());
		}
		return null;

	}

	// Connect and execute SQL

	void executeSQL(String sql) {
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(URL, "root", "2013");
			st = con.createStatement();
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Syntax Error: " + e.getMessage());
		}
	}

	// Close the SQL connection

	void exitMySQL() {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
