package AboutClient;

/**实现Serializable空接口，为了让对象序列化
 * 便于后续IO传输的时候利用套接字
 */

import java.io.Serializable;

public class GameMessage implements Serializable {
	private String playerName;
	private String password;
	private String correctQuestion;
	private String[] questions;
	private String[] playerMessage;

	private String answer;

	public GameMessage() {

	}

	public void setPlayerName(String userName) {
		this.playerName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setQuestions(String[] questions) {
		this.questions = questions;
	}

	public void setCorrectQuestion(String correctQuestion) {
		this.correctQuestion = correctQuestion;
	}

	public void setPlayerMessage(String[] playerMessage) {
		this.playerMessage = playerMessage;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getPassword() {
		return password;
	}

	public String getCorrectQuestion()

	{
		return correctQuestion;
	}

	public String[] getQusetions() {
		return questions;
	}

	public String[] getPlayerMessage() {
		return playerMessage;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}
}
