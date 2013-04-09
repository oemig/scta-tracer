package net.oemig.scta.tracer.question;

import java.io.Serializable;

import net.oemig.scta.model.data.QuestionType;

public class Question implements Serializable {

	private static final long serialVersionUID = -3044284258835050020L;

	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String correctAnswer;
	/**
	 * One of {@link QuestionType}
	 */
	private QuestionType type;

	public Question(String newQuestion, String newAnswer1, String newAnswer2,
			String newAnswer3, String newCorrectAnswer, QuestionType newType) {
		this.question = newQuestion;
		this.answer1 = newAnswer1;
		this.answer2 = newAnswer2;
		this.answer3 = newAnswer3;
		this.correctAnswer = newCorrectAnswer;
		this.type = newType;

	}

	public String get() {
		return question;
	}

	public String getAnswer1() {
		return answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * One of {@link QuestionType}
	 * @return {@link QuestionType}
	 */
	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

}
