package project.ks.com.posts.bean;

public class Answer extends BaseBean {


	public Answer() {
	}

	public Answer(String questionId, String answer, User answerBy, String date) {
		this.questionId = questionId;
		this.answer = answer;
		this.answerBy = answerBy;
		this.date = date;
	}

	private String questionId;
	
	private String answer;
	
	private User answerBy;
	
	private String date;

	public String getAnswer() {
		return answer;
	}

	public User getAnswerBy() {
		return answerBy;
	}

	public String getDate() {
		return date;
	}
	
	public String getQuestionId() {
		return questionId;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setAnswerBy(User answerBy) {
		this.answerBy = answerBy;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	
}
