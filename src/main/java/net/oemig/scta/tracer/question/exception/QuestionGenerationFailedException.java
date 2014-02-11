package net.oemig.scta.tracer.question.exception;

public class QuestionGenerationFailedException extends QuestionException {

	private static final long serialVersionUID = -5073914738440554827L;
	
	public QuestionGenerationFailedException(String aReason) {
		super("Question generation failed: "+aReason);
	}

}
